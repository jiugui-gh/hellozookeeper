package chapter05.part3_1;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

//创建一个最基本的Zookeeper会话实例

public class Zookeeper_Constructor_Usage_Simple implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    
    public static void main(String[] args) throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181,localhost:2182,localhost:2183",5000,new Zookeeper_Constructor_Usage_Simple());
        
        System.out.println(zooKeeper.getState());
        
        try {
            System.out.println(connectedSemaphore.getCount());
            connectedSemaphore.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("zooKeeper session established.");
    }
    @Override
    public void process(WatchedEvent event) {
        // TODO Auto-generated method stub
        System.out.println("Receice watched event: " + event);
        if(KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
            System.out.println(connectedSemaphore.getCount());
        }
    }

}
