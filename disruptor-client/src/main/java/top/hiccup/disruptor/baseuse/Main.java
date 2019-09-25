package top.hiccup.disruptor.baseuse;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import top.hiccup.disruptor.common.MyEvent;
import top.hiccup.disruptor.common.MyEventProducer;
import top.hiccup.disruptor.common.MyEventFactory;
import top.hiccup.disruptor.common.MyEventHandler;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wenhy on 2018/1/9.
 */
public class Main {

    /**
     * Disruptor简单使用示例
     */
    public static void main(String[] args) throws Exception {
        // 创建线程缓冲池
        ExecutorService executor = Executors.newCachedThreadPool();
        // 创建事件对象工厂
        MyEventFactory factory = new MyEventFactory();
        // 指定bufferSize，也就是RingBuffer大小，必须是2的N次方
        int ringBufferSize = 1024 * 1024;

         // BlockingWaitStrategy 是最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现
         WaitStrategy blockingWaitStrategy = new BlockingWaitStrategy();
         // SleepingWaitStrategy 的性能表现跟BlockingWaitStrategy差不多，对CPU的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景
         WaitStrategy sleepingWaitStrategy = new SleepingWaitStrategy();
         // YieldingWaitStrategy 的性能是最好的，适合用于低延迟的系统。
         // 在要求极高性能且事件处理线程数小于CPU逻辑核心数的场景中，推荐使用此策略；例如，CPU开启超线程的特性
         WaitStrategy yieldingWaitStrategy = new YieldingWaitStrategy();

        // 创建disruptor  ProducerType.SINGLE：只有一个生产者 ProducerType.MULTI：有多个生产者

        Disruptor<MyEvent> disruptor =
                new Disruptor<MyEvent>(factory, ringBufferSize, executor, ProducerType.SINGLE, new YieldingWaitStrategy());
        // 注册消费事件的方法
        disruptor.handleEventsWith(new MyEventHandler(null));

        // 启动disruptor
        disruptor.start();

        // disruptor发布事件
        RingBuffer<MyEvent> ringBuffer = disruptor.getRingBuffer();

        MyEventProducer producer = new MyEventProducer(ringBuffer);
        //LongEventProducerWithTranslator producer = new LongEventProducerWithTranslator(ringBuffer);
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        for(long l = 0; l<100; l++){
            byteBuffer.putLong(0, l);
            producer.onData(byteBuffer);
            //Thread.sleep(1000);
        }


        disruptor.shutdown();//关闭disruptor，该方法会堵塞，直至所有的事件都得到处理。
        executor.shutdown();//关闭disruptor使用的线程池；disruptor在shutdown时不会自动关闭executor。
    }

}
