package chapter05.part3_2;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
// 使用异步接口  创建节点
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class ZooKeeper_Create_API_ASync_Usage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181",5000,new ZooKeeper_Create_API_ASync_Usage());
        
        connectedSemaphore.await();
        
        zooKeeper.create("/zk-test-ephemeral-", "".getBytes(), Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL, new IStringCallback(), "I am context.");
        
        zooKeeper.create("/zk-test-ephemeral-", "".getBytes(), Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL, new IStringCallback(), "I am context.");
        
        zooKeeper.create("/zk-test-ephemeral-", "".getBytes(), Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL, new IStringCallback(), "I am context.");
    
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
