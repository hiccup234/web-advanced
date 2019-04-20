package top.hiccup.db.mysql;

/**
 * 传统数据库管理系统的事务隔离级别
 *
 * 【读未提交：Read uncommitted】
 * 一个事务能够看到其他事务尚未提交的修改，允许脏读出现。
 *
 * 【读已提交：Read committed】
 * 事务能够看到的数据都是其他事务已经提交的修改，但是无法保证再次读取时能获取到同样的数据，即允许其他事务并发修改数据，
 * 不会出现脏读，但是允许不可重复读和幻象读（Phantom Read）。
 *
 * 【可重复读：Repeatable reads】
 * 保证同一个事务多次读取的数据都是一致的，是MySQL InnoDB存储引擎的默认隔离级别，MySQL在可重复读级别不会出现幻象读
 * （通过多版本并发控制MVCC：Multiversion Concurrency Control），但是SQL标准的隔离级别还是会出现幻读问题
 *
 * 【串行化：Serializable】
 * 并发的事务之间时串行化的，通常意味着读取需要获取共享锁，更新需要获取排他锁（读写锁），MySQL实现中，如果SQL语句中含有Where，
 * 则还会获取区间锁（GAP锁，可重复读级别中默认也会使用）
 *
 *======================================================================================================================
 * 注意：MySQL默认的事务隔离级别是“可重复读”，而其他大多数数据库默认级别是“读已提交”（如Oracle、SQLServer等），
 * 但是互联网项目一般都会修改默认级别为“读已提交”，可以在Navicat里开两个查询窗口（Session）来验证。
 *
 * Q: 为什么MYSQL的默认级别是“可重复读”呢？
 * A: 由于历史原因，主从复制都是基于binlog的，而binlog有三种模式：
 *      1、statement:记录的是修改SQL语句
 *      2、row：记录的是每行实际数据的变更
 *      3、mixed：statement和row模式的混合
 * Mysql在5.0版本以前，binlog只支持statement格式！而这种格式在读已提交(Read committed)级别下主从复制是有bug的。
 * （如果事务1先执行条件删除，还未提交，此时事务2插入某条符合事务1条件的数据并提交，然后事务1再提交，
 *  那么主从复制的时候binlog中按commit先后记录的是先插后删，而主库则是先删后插，会导致主从不一致的问题，
 *  因此Mysql将可重复读(Repeatable Read)作为默认的隔离级别）
 * 5.1版本后，binlog的格式改为了row，基于数据行复制，便不会再出现这个bug了。
 *
 * 互联网项目中一般都不用“读未提交”和“串行化”，所以一般都是在“读已提交”RC和“可重复读”RR中做选择，为什么选择RC呢?
 *      1、在RR级别下存在间隙锁（GAP），导致出现死锁的几率比RC大很多
 *      2、在RR隔离级别下，条件列未命中索引会锁表，而在RC隔离级别下，只锁行
 *      3、在RC隔离级别下，半一致性读(semi-consistent)特性增加了update操作的并发性
 *
 * // 查看事务隔离级别
 * select @@tx_isolation;
 * //1）read uncommitted : 读取尚未提交的数据
 * //2）read committed：读取已经提交的数据 ：可以解决脏读 ---- oracle默认的
 * //3）repeatable read：重读读取：可以解决脏读 和 不可重复读 ---mysql默认的
 * //4）serializable：串行化：可以解决 脏读 不可重复读 和 幻读---相当于锁表
 * // 重新设置
 * set session transaction isolation level read committed
 *
 * SESSION_1
 * begin;
 * update conf_const t set t.const_key = "aaa" where t.id = 1;
 * commit;
 *
 * SESSION_2
 * begin;
 * select * from conf_const;
 * // 先执行前两句，再执行SESSION_1，然后执行后两句
 * select * from conf_const;
 * commit;
 *======================================================================================================================
 *
 * @author wenhy
 * @date 2019/4/12
 */
public class IsolationLevel {
}
