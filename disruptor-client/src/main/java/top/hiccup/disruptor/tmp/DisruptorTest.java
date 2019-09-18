package top.hiccup.disruptor.tmp;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

public class DisruptorTest {
    public static int infoNum = 500_0000;
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        InfoEventFactory factory = new InfoEventFactory();
        int ringBufferSize = 65536; //数据缓冲区的大小 必须为2的次幂

        /**
         *
         *  factory，定义的事件工厂
         *  ringBufferSize，环形队列RingBuffer的大小，必须是2的N次方
         *  ProducerType，生产者线程的设置，当你只有一个生产者线程时设置为 ProducerType.SINGLE，多个生产者线程ProducerType.MULTI
         *  waitStrategy，消费者的等待策略
         *
         */
        final Disruptor<InfoEvent> disruptor = new Disruptor<InfoEvent>(factory, ringBufferSize,
                DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new YieldingWaitStrategy());

        InfoEventConsumer consumer = new InfoEventConsumer();
        disruptor.handleEventsWithWorkerPool(consumer);
        disruptor.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RingBuffer<InfoEvent> ringBuffer = disruptor.getRingBuffer();
                for (int i = 0; i < infoNum; i++) {
                    long seq = ringBuffer.next();
                    InfoEvent infoEvent = ringBuffer.get(seq);
                    infoEvent.setId(i);
                    infoEvent.setValue("info" + i);
                    ringBuffer.publish(seq);
                }
            }
        }).start();
    }
}