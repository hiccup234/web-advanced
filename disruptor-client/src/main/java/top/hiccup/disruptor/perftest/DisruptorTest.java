package top.hiccup.disruptor.perftest;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import top.hiccup.disruptor.common.MyEvent;
import top.hiccup.disruptor.common.MyEventProducer;
import top.hiccup.disruptor.common.MyEventFactory;
import top.hiccup.disruptor.common.MyEventHandler;

import java.nio.ByteBuffer;
import java.util.concurrent.*;

/**
 * 线程间队列Disruptor性能测试类
 *
 * @author wenhy
 * @date 2019/9/12
 */
public class DisruptorTest {

    public static Object lock = new Object();
    public static volatile boolean running = true;

    public static void main(String[] args) throws Exception {
        // 创建事件对象工厂
        EventFactory eventFactory = new MyEventFactory();
        ExecutorService executorService = Common.getExecutorService();
        // TODO 优化点：创建事件能不能通过拷贝的方式呢？如果每次都new一个对象肯定很慢
        // TODO 单生产者其实性能更高（省去了线程切换）
        Disruptor<MyEvent> disruptor =
                new Disruptor<MyEvent>(eventFactory, Common.QUEUE_SIZE, executorService, ProducerType.SINGLE, new BusySpinWaitStrategy());
        // 注册消费事件的处理器
        disruptor.handleEventsWith(new MyEventHandler());
        // 启动Disruptor
        disruptor.start();

        // disruptor发布事件
        RingBuffer<MyEvent> ringBuffer = disruptor.getRingBuffer();
        MyEventProducer producer = new MyEventProducer(ringBuffer);

        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        long startTime = System.currentTimeMillis();
        for (long a = 0; a <= Common.DATA_SIZE; a+=6) {
            byteBuffer.putLong(0, a);
            producer.onData(byteBuffer);
        }
        long costTime = System.currentTimeMillis() - startTime;

        synchronized (lock) {
            while (running) {
                lock.wait();
            }
        }
        System.out.println(costTime);
        //关闭disruptor，该方法会堵塞，直至所有的事件都得到处理。
        disruptor.shutdown();
        executorService.shutdown();
    }
}
