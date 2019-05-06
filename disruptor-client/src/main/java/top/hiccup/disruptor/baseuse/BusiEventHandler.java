package top.hiccup.disruptor.baseuse;

import com.lmax.disruptor.EventHandler;

/**
 * Created by wenhy on 2018/1/9.
 */
public class BusiEventHandler implements EventHandler<BusiEvent> {

    public void onEvent(BusiEvent busiEvent, long l, boolean b) throws Exception {
        System.out.println(busiEvent.getBusiEventName());
    }
}
