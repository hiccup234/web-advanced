
## 高性能Disruptor总结

    Disruptor是一种内存消息队列，与Kafka不同的，Disruptor是线程与线程之间消息传递的队列，比Java中的内存消息队列ArrayBlockingQueue性能高出一个数量级。
    
    在Apache Storm、Camel、Log4j2等中广泛应用。
    
## Disruptor的数据结构

    队列实现的数据结构一般有两种：1、数组  2、链表
    无界队列一般用链表实现，但有界队列的应用场景更加广泛，毕竟机器的内存是有限的，无界队列占用的内存数量不可控，容易造成OOM。
    有界队列一般采用循环数组（循环队列）实现，来解决出入队时数据搬移的问题。
    
    对于高并发情况下的add和pull方法，Disruptor没有采用加锁或者CAS操作来将并行改成串行执行，而是采用类似Java堆中线程分配对象的本地线程分配缓冲（Thread Local Allocation Buffer, TLAB）
    Disruptor中生产者线程在向队列添加数据前预先申请连续的n个存储单元（加锁同步的），申请之后就可以独享这段内存了，消费者类似，也是申请独享的连续空间，
    但是如果第一个线程预申请的空间未写完，则第二个线程的空间即使有数据也无法读取。    
    
    实际上Disruptor采用了RingBuffer和AvailableBuffer这两种数据结构。
   
    
    
    
public class Queue {
  private Long[] data;
  private int size = 0, head = 0, tail = 0;
  public Queue(int size) {
    this.data = new Long[size];
    this.size = size;
  }

  public boolean add(Long element) {
    if ((tail + 1) % size == head) return false;
    data[tail] = element;
    tail = (tail + 1) % size;
    return true;
  }

  public Long poll() {
    if (head == tail) return null;
    long ret = data[head];
    head = (head + 1) % size;
    return ret;
  }
}

public class Producer {
  private Queue queue;
  public Producer(Queue queue) {
    this.queue = queue;
  }

  public void produce(Long data) throws InterruptedException {
    while (!queue.add(data)) {
      Thread.sleep(100);
    }
  }
}

public class Consumer {
  private Queue queue;
  public Consumer(Queue queue) {
    this.queue = queue;
  }

  public void comsume() throws InterruptedException {
    while (true) {
      Long data = queue.poll();
      if (data == null) {
        Thread.sleep(100);
      } else {
        // TODO:... 消费数据的业务逻辑...
      }
    }
  }
}
