package chapter05.part3_1;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class Zookeeper_Constructor_Usage_With_SID_PASSWD_Test  implements Watcher{

 private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    
    public static void main(String[] args) throws IOException, InterruptedException {
        
        long sessionId = 72058469294080021L;
        byte[] passwd = new byte[] {15,-84,-117,107,123,66,-42,30,-98,71,-77,-3,27,-118,-65,127};
    
    
        
        // 使用正确的sessionID 和 sessionPasswd
        System.out.println("------------------");
        @SuppressWarnings("unused")
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181",
                5000,
                new Zookeeper_Constructor_Usage_With_SID_PASSWD(),
                sessionId,
                passwd);
        connectedSemaphore.await();
        System.in.read();
    }
    @Override
    public void process(WatchedEvent event) {
        // TODO Auto-generated method stub
        System.out.println("Receive watched event: " + event );
        if(KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
        }
    }

}
