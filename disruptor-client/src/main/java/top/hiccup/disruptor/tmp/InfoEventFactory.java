package top.hiccup.disruptor.tmp;

import com.lmax.disruptor.EventFactory;

public class InfoEventFactory implements EventFactory<InfoEvent> {
    @Override
    public InfoEvent newInstance() {
        return new InfoEvent();
    }

}
