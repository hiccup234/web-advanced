package top.hiccup.disruptor.common;

import com.lmax.disruptor.EventHandler;
import top.hiccup.disruptor.test.DisruptorTest;

/**
 * Created by wenhy on 2018/1/9.
 */
public class MyEventHandler implements EventHandler<MyEvent> {

    @Override
    public void onEvent(MyEvent myEvent, long sequence, boolean endOfBatch) throws Exception {
//        System.out.println("接收到：" + event.getEventName());
        if (myEvent.getEventId() == 2_000_000) {
            DisruptorTest.running = false;
            synchronized (DisruptorTest.lock) {
                DisruptorTest.lock.notifyAll();
            }
        }
    }
}
