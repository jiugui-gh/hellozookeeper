package chapter05.part3_6;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class Exist_API_Sync_Usage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk;
    
    public static void main(String[] args) throws Exception {
        String path = "/zk-book";
        zk = new ZooKeeper("localhost:2181", 
                5000, //
                new Exist_API_Sync_Usage());
        connectedSemaphore.await();
        
        zk.exists(path, true);
        
        zk.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        
        zk.setData(path, "123".getBytes(), -1);
        
        zk.create( path+"/c1", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT );
        
        zk.delete( path+"/c1", -1 );
        
        zk.delete( path, -1 );
        
        System.in.read();
    }
    @Override
    public void process(WatchedEvent event) {
        // TODO Auto-generated method stub
        try {
            if(KeeperState.SyncConnected == event.getState()) {
                if(event.getType() == EventType.None) {
                    connectedSemaphore.countDown();
                }else if(event.getType() == EventType.NodeCreated) {
                    System.out.println("Node(" + event.getPath() + ")Created");
                    zk.exists( event.getPath(), true );
                }else if(event.getType() == EventType.NodeDeleted) {
                    System.out.println("Node(" + event.getPath() + ")Deleted");
                    zk.exists( event.getPath(), true );
                }else if(event.getType() == EventType.NodeDataChanged) {
                    System.out.println("Node(" + event.getPath() + ")DataChanged");
                    zk.exists( event.getPath(), true );
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }

}
