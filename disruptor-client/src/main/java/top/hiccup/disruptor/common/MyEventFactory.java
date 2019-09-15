package top.hiccup.disruptor.common;

import com.lmax.disruptor.EventFactory;

/**
 * Created by wenhy on 2018/1/9.
 */
public class MyEventFactory implements EventFactory<MyEvent> {

    @Override
    public MyEvent newInstance() {
        return new MyEvent();
    }
}
