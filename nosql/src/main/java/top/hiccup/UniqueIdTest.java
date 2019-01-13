package top.hiccup;

/**
 * 全局唯一ID：
 *
 * 1、UUID：无序，不能保证递增，采用字符存储，查询传输慢
 *
 * 2、snowflake算法：twitter分布式ID生成算法，强依赖时钟，多台服务器时钟要同步
 *            1bit+41bit+10bit+10bit=62bit
 *            高位随机数+毫秒数+机器码（数据中心+机器ID）+10流水号
 * 3、MySql自增主键：假设有100台MySql数据库，则自增步长设置为100，如库A的id=100，200，300 库B的id=101，201，301
 *
 * 4、
 *
 * @author wenhy
 * @date 2019/1/13
 */
public class UniqueIdTest {
}
