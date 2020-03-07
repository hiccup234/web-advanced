package top.hiccup.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import top.hiccup.disruptor.common.MyEvent;
import top.hiccup.disruptor.common.MyEventFactory;
import top.hiccup.disruptor.common.MyEventHandler;
import top.hiccup.disruptor.perftest.Common;

/**
 * Disruptor简单使用示例
 *
 * Created by wenhy on 2018/1/9.
 */
public class SampleTest {

    public static void main(String[] args) throws Exception {
        // 创建事件工厂
        EventFactory eventFactory = new MyEventFactory();
        // 指定RingBuffer大小，必须是2的N次方，类似HashMap的capacity（bufferSize must be a power of 2：Integer.bitCount）
        int ringBufferSize = 32;

        // 最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现。
        WaitStrategy blockingWaitStrategy = new BlockingWaitStrategy();
        // 性能表现跟BlockingWaitStrategy差不多，对CPU的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景。
        WaitStrategy sleepingWaitStrategy = new SleepingWaitStrategy();
        // 性能最好，适合用于低延迟的系统。适合性能要求极高且事件处理线程数小于CPU逻辑核心数的场景，如CPU开启超线程特性。
        WaitStrategy yieldingWaitStrategy = new YieldingWaitStrategy();

        // TODO 优化点：创建事件能不能通过拷贝的方式呢？（创建Disruptor的时候就会用eventFactory填充整个循环队列）
        // TODO 单生产者其实性能更高（省去了线程切换）
        // 创建disruptor队列，ProducerType.SINGLE：单生产者；ProducerType.MULTI：多个生产者
        Disruptor<MyEvent> disruptor = new Disruptor<>(eventFactory, ringBufferSize,
                                                DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new YieldingWaitStrategy());
//                                                DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());

        // 注册消费事件的处理器
        disruptor.handleEventsWith(new MyEventHandler(null));
//        disruptor.handleEventsWithWorkerPool(new MyWorkHandler());
        // 启动Disruptor
        disruptor.start();

        // 异步线程生产数据
        new Thread(() -> {
            RingBuffer<MyEvent> innerRingBuffer = disruptor.getRingBuffer();
            for (long a = 0; a <= Common.DATA_SIZE; a++) {
                long seq = innerRingBuffer.next();
                try {
                    MyEvent myEvent = innerRingBuffer.get(seq);
                    myEvent.setEventId(a);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 一定要记得：在finally里面publish，某个seq未发布会影响后续所有seq的消费
                    innerRingBuffer.publish(seq);
                }

//                // 3.0提供了新的发布事件的API，来防止出现上面的问题
//                innerRingBuffer.publishEvent((event, sequence, eventId) -> event.setEventId(eventId), a);
            }
        }).start();

        // 关闭disruptor，该方法会堵塞直至所有的事件都得到处理。
        disruptor.shutdown();
    }
}
