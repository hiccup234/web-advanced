package top.hiccup.disruptor.common;

import com.lmax.disruptor.WorkHandler;
import top.hiccup.disruptor.perftest.Common;
import top.hiccup.disruptor.perftest.DisruptorTest;

/**
 * F
 *
 * @author wenhy
 * @date 2019/9/18
 */
public class MyWorkHandler implements WorkHandler<MyEvent> {

    private long count = 0;

    @Override
    public void onEvent(MyEvent myEvent) throws Exception {
        count++;
        if (count == Common.DATA_SIZE) {
            System.out.println(System.currentTimeMillis() - DisruptorTest.startTime);
        }
    }
}
