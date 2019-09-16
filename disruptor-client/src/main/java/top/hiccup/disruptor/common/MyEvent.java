package top.hiccup.disruptor.common;

/**
 * Created by wenhy on 2018/1/9.
 */
public class MyEvent {

    private long eventId;
    private String eventName;

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
