package chapter05.part3_1;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class Zookeeper_Constructor_Usage_With_SID_PASSWD implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    
    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181",5000,new Zookeeper_Constructor_Usage_With_SID_PASSWD());
        
        connectedSemaphore.await();
        
        long sessionId = zooKeeper.getSessionId();
        byte[] passwd = zooKeeper.getSessionPasswd();
    
        System.out.println(sessionId);
        for(int i=0;i<passwd.length;i++) {
            System.out.print(passwd[i] + " ");
        }
        // 使用违法的sessIonId 和 sessionPasswd
        /*zooKeeper = new ZooKeeper("localhost:2181",
                5000,
                new Zookeeper_Constructor_Usage_With_SID_PASSWD(),
                1l,
                "test".getBytes());*/
        
        // 使用正确的sessionID 和 sessionPasswd
        System.out.println("------------------");
       /* zooKeeper = new ZooKeeper("localhost:2181",
                5000,
                new Zookeeper_Constructor_Usage_With_SID_PASSWD(),
                sessionId,
                passwd);*/
        
        System.in.read();
       // Thread.sleep(50000);
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
