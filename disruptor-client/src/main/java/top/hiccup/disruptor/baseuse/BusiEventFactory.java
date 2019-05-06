package top.hiccup.disruptor.baseuse;

import com.lmax.disruptor.EventFactory;

/**
 * Created by wenhy on 2018/1/9.
 */
public class BusiEventFactory implements EventFactory<BusiEvent>{

    public BusiEvent newInstance() {
        return new BusiEvent();
    }

}
