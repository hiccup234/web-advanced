package top.hiccup;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * ZooKeeperClient
 *
 * @author wenhy
 * @date 2017/12/23
 */
public class ZooKeeperClient {

    public static void waitUntilConnected(ZooKeeper testZooKeeper, CountDownLatch testLatch) {
        if(testZooKeeper.getState() == ZooKeeper.States.CONNECTING) {
            try {
                testLatch.await();
            } catch (InterruptedException err) {
                System.out.println("Latch exception");
            }
        }
    }

    static class ConnectedWatcher implements Watcher {
        private CountDownLatch connectedLatch;

        ConnectedWatcher(CountDownLatch connectedLatch) {
            // CountDownLatch实例初始化时设为1
            this.connectedLatch = connectedLatch;

        }

        @Override
        public void process(WatchedEvent event) {
            System.out.println("ZK事件！");
            if (event.getState() == Event.KeeperState.SyncConnected) {

                connectedLatch.countDown(); /* ZK连接成功时，计数器由1减为0 */

            }
        }
    }

    public static void main(String[] args) {
        try {

            CountDownLatch sampleLatch = new CountDownLatch(1);
            Watcher sampleWatcher = new ConnectedWatcher(sampleLatch);

            ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 5000, sampleWatcher);

            waitUntilConnected(zk, sampleLatch);    /* 只有当ZK链接成功（状态为 SyncConnected)时，此函数调用才结束 */

//            Stat s = zk.exists("/root/childNode", false);
//            if(null != s) {
//                zk.delete("/root/childNode", -1);
//            }
//            s = null;
//            s = zk.exists("/root", false);
//            if(null != s) {
//                zk.delete("/root", -1);
//            }

//            zk.create("/root", "client1Data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//            zk.create("/root/childNode", "childClient1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            List<String> rootChildNode = zk.getChildren("/root", true);

            byte[] childNode = zk.getData("/root/childNode", true, null);
            Stat stat = zk.setData("/root/childNode", "client1Data2".getBytes(), -1);

            zk.delete("/root/childNode", -1);

            zk.close();

            int i = 1;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

}
