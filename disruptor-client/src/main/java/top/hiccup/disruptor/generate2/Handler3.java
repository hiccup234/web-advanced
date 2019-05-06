package top.hiccup.disruptor.generate2;

import com.lmax.disruptor.EventHandler;

public class Handler3 implements EventHandler<Trade> {

    public void onEvent(Trade event, long sequence,  boolean endOfBatch) throws Exception {
    	System.out.println("handler3: name: " + event.getName() + " , price: " + event.getPrice() + ";  instance: " + event.toString());
    }  
}
