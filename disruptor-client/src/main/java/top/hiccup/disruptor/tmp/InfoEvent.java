package top.hiccup.disruptor.tmp;

import java.io.Serializable;

public class InfoEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String value;

    public InfoEvent() {

    }

    public InfoEvent(long id, String value) {
        this.id = id;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
