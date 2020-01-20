package chapter05.part3_3;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class Delete_API_Sync_Usage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk;
    
    public static void main(String[] args) throws Exception {
    
        String path = "/zk-book";
        zk = new ZooKeeper("localhost:2181",5000,new Delete_API_Sync_Usage());
        
        connectedSemaphore.await();
      //  zk.exists(path, true);
        zk.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("创建节点");
        Thread.sleep(1000);
        zk.delete(path, -1);
        System.out.println("删除节点");
        System.in.read();
    }
    
    @Override
    public void process(WatchedEvent event) {
        // TODO Auto-generated method stub

        if(KeeperState.SyncConnected == event.getState()) {
            if(EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
                
            }
            System.out.println("死货");
           /* try {
                zk.exists(event.getPath(), true);
            } catch (KeeperException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
            System.out.println(event.getType());
        }
    }

    
}
