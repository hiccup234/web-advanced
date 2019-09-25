package top.hiccup.disruptor.common;

import com.lmax.disruptor.WorkHandler;
import top.hiccup.disruptor.perftest.Common;
import top.hiccup.disruptor.perftest.PerfDisruptorTest;

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
            long costTime = System.currentTimeMillis() - PerfDisruptorTest.startTime;
            System.out.println("Disruptor耗时：" + costTime);
            long opsPerSecond = (Common.DATA_SIZE * 1000L) / costTime;
            System.out.println("Disruptor每秒吞吐量：" + opsPerSecond);
        }
    }
}
