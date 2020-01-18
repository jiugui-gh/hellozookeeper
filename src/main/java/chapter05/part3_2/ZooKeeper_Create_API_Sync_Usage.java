package chapter05.part3_2;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
// 使用同步api 创建节点
public class ZooKeeper_Create_API_Sync_Usage implements Watcher {

    public static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    
    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181",5000,new ZooKeeper_Create_API_Sync_Usage());
        
        connectedSemaphore.await();
        
        String path1 = zooKeeper.create("/zk-test-ephemeral-", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    
        System.out.println("Success create zone: " + path1);
        
        String path2 = zooKeeper.create("/zk-test-ephemeral-", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    
        System.out.println("Success create zone: " + path2);
    
        System.in.read();
    }
    
    @Override
    public void process(WatchedEvent event) {
        // TODO Auto-generated method stub
        if(KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
        }
    }

}
