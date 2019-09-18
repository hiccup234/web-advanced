package top.hiccup.disruptor.tmp;

import com.lmax.disruptor.WorkHandler;

public class InfoEventConsumer implements WorkHandler<InfoEvent> {
    private long startTime;
    private int cnt;

    public InfoEventConsumer() {
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void onEvent(InfoEvent event) throws Exception {
        // TODO Auto-generated method stub
        cnt++;

        if (cnt == DisruptorTest.infoNum) {
            long endTime = System.currentTimeMillis();
            System.out.println(" 消耗时间： " + (endTime - startTime) + "毫秒");
        }

    }
}