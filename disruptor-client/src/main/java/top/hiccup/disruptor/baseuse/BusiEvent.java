package top.hiccup.disruptor.baseuse;

/**
 * Created by wenhy on 2018/1/9.
 */
public class BusiEvent {

    private Long busiEventId;
    private String busiEventName;

    public Long getBusiEventId() {
        return busiEventId;
    }

    public void setBusiEventId(Long busiEventId) {
        this.busiEventId = busiEventId;
    }

    public String getBusiEventName() {
        return busiEventName;
    }

    public void setBusiEventName(String busiEventName) {
        this.busiEventName = busiEventName;
    }

}
