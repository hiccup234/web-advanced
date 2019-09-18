package top.hiccup.disruptor.common;

import java.io.Serializable;

/**
 * Created by wenhy on 2018/1/9.
 */
public class MyEvent implements Serializable {
    private static final long serialVersionUID = 1L;

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

    public MyEvent() {

    }

    public MyEvent(int a, String s) {
        eventId = a;
        eventName = s;
    }
}
