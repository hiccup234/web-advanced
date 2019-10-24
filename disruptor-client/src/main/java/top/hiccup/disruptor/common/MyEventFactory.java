package top.hiccup.disruptor.common;

import com.lmax.disruptor.EventFactory;

/**
 * Created by wenhy on 2018/1/9.
 */
public class MyEventFactory implements EventFactory<MyEvent> {

    /**
     * 创建Disruptor的时候会调用这个方法来 fill RingBuffer
     *
     * @return
     */
    @Override
    public MyEvent newInstance() {
        return new MyEvent();
    }
}
