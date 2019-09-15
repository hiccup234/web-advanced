package top.hiccup.disruptor.test;

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
 * 线程间队列Disruptor
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
        Disruptor<MyEvent> disruptor =
                new Disruptor<MyEvent>(eventFactory, Common.QUEUE_SIZE, executorService, ProducerType.MULTI, new YieldingWaitStrategy());
        // 注册消费事件的处理器
        disruptor.handleEventsWith(new MyEventHandler());
        // 启动Disruptor
        disruptor.start();
        // disruptor发布事件
        RingBuffer<MyEvent> ringBuffer = disruptor.getRingBuffer();
        MyEventProducer producer = new MyEventProducer(ringBuffer);

        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        long startTime = System.currentTimeMillis();
        for (long l = 0; l <= Common.DATA_SIZE; l++) {
            byteBuffer.putLong(0, l);
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
