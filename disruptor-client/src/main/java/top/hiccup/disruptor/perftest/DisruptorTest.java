package top.hiccup.disruptor.perftest;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import top.hiccup.disruptor.common.*;

import java.nio.ByteBuffer;
import java.util.concurrent.*;

/**
 * 线程间队列Disruptor性能测试类
 *
 * @author wenhy
 * @date 2019/9/12
 */
public class DisruptorTest {

    public static long startTime;

    private static final int size = 1 << 15;

    public static void main(String[] args) throws Exception {
        // 创建事件对象工厂
        EventFactory eventFactory = new MyEventFactory();

//        ExecutorService executorService = Common.getExecutorService();
        // TODO 优化点：创建事件能不能通过拷贝的方式呢？如果每次都new一个对象肯定很慢（只是创建队列的时候慢）
        // TODO 单生产者其实性能更高（省去了线程切换）
//        Disruptor<MyEvent> disruptor =
//                new Disruptor<MyEvent>(eventFactory, Common.QUEUE_SIZE, executorService, ProducerType.SINGLE, new YieldingWaitStrategy());

        Disruptor<MyEvent> disruptor = new Disruptor<MyEvent>(eventFactory, size, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new YieldingWaitStrategy());
//        Disruptor<MyEvent> disruptor = new Disruptor<MyEvent>(eventFactory, Common.QUEUE_SIZE, new ThreadFactory() {
//            @Override
//            public Thread newThread(Runnable r) {
//                return new Thread();
//            }
//        }, ProducerType.SINGLE, new YieldingWaitStrategy());

        // 注册消费事件的处理器
//        disruptor.handleEventsWith(new MyEventHandler());
        disruptor.handleEventsWithWorkerPool(new MyWorkHandler());

        // 启动Disruptor
        disruptor.start();



        startTime = System.currentTimeMillis();
        new Thread(()-> {
            RingBuffer<MyEvent> innerRingBuffer = disruptor.getRingBuffer();
            for (int i = 0; i <= 6_000_000; i++) {
                long seq = innerRingBuffer.next();
                MyEvent myEvent = innerRingBuffer.get(seq);
                myEvent.setEventId(i);
                myEvent.setEventName("事件：" + i);
                innerRingBuffer.publish(seq);
            }
        }).start();



        // disruptor发布事件
//        RingBuffer<MyEvent> ringBuffer = disruptor.getRingBuffer();
//        MyEventProducer producer = new MyEventProducer(ringBuffer);
//
//        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
//        startTime = System.currentTimeMillis();
//        for (long a = 0; a <= Common.DATA_SIZE; a++) {
//            byteBuffer.putLong(0, a);
//            producer.onData(byteBuffer);
//        }
        // 关闭disruptor，该方法会堵塞，直至所有的事件都得到处理。
//        disruptor.shutdown();
//        executorService.shutdown();
    }
}
