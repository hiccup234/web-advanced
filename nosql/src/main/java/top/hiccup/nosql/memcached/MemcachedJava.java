package top.hiccup.nosql.memcached;

import java.net.InetSocketAddress;
import java.util.concurrent.Future;

import net.spy.memcached.CASResponse;
import net.spy.memcached.CASValue;
import net.spy.memcached.MemcachedClient;

/**
 * Memcached客户端测试类
 *
 * Memcached的IO处理支持多线程
 *
 * @author wenhy
 * @date 2018/12/21
 */
public class MemcachedJava {
    public static void main(String[] args) {
        try {
            // 本地连接 Memcached 服务，默认端口为11211
            MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
            System.out.println("Connection to Memcached server successful");
            // 添加数据
            Future fo = mcc.set("key1", 900, "Free Education");

            // 打印状态
            System.out.println("set status:" + fo.get());

            // 输出
            System.out.println("key1 value in cache - " + mcc.get("key1"));

            // 添加
            fo = mcc.add("key1", 900, "memcached");

            // 打印状态
            System.out.println("add status:" + fo.get());

            // 添加新key
            fo = mcc.add("codingground", 900, "All Free Compilers");

            // 打印状态
            System.out.println("add status:" + fo.get());

            // 输出
            System.out.println("codingground value in cache - " + mcc.get("codingground"));


            // 添加新的 key
            fo = mcc.replace("key1", 900, "Largest Tutorials' Library");

            // 输出执行 set 方法后的状态
            System.out.println("replace status:" + fo.get());

            // 获取键对应的值
            System.out.println("key1 value in cache - " + mcc.get("key1"));


//            // 对存在的key进行数据添加操作
//            fo = mcc.append("key1", 900, " for All");
//
//            // 输出执行 set 方法后的状态
//            System.out.println("append status:" + fo.get());
//
//            // 获取键对应的值
//            System.out.println("key1 value in cache - " + mcc.get("codingground"));


//            // 对存在的key进行数据添加操作
//            fo = mcc.prepend("key1", 900, "Free ");
//
//            // 输出执行 set 方法后的状态
//            System.out.println("prepend status:" + fo.get());
//
//            // 获取键对应的值
//            System.out.println("key1 value in cache - " + mcc.get("codingground"));


            // 通过 gets 方法获取 CAS token（令牌）
            CASValue casValue = mcc.gets("key1");

            // 输出 CAS token（令牌） 值
            System.out.println("CAS token - " + casValue);

            // 尝试使用cas方法来更新数据
            CASResponse casresp = mcc.cas("key1", casValue.getCas(), 900, "Largest Tutorials-Library");

            // 输出 CAS 响应信息
            System.out.println("CAS Response - " + casresp);

            // 输出值
            System.out.println("key1 value in cache - " + mcc.get("key1"));


            // 通过 gets 方法获取 CAS token（令牌）
            CASValue casValue2 = mcc.gets("key1");

            // 输出 CAS token（令牌） 值
            System.out.println("CAS value in cache - " + casValue2);


            // 对存在的key进行数据添加操作
            fo = mcc.delete("key1");

            // 输出执行 delete 方法后的状态
            System.out.println("delete status:" + fo.get());

            // 获取键对应的值
            System.out.println("key1 value in cache - " + mcc.get("codingground"));


            // 添加数字值
            fo = mcc.set("number", 900, "1000");

            // 自增并输出
            System.out.println("value in cache after increment - " + mcc.incr("number", 111));

            // 自减并输出
            System.out.println("value in cache after decrement - " + mcc.decr("number", 112));


            // 关闭连接
            mcc.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}