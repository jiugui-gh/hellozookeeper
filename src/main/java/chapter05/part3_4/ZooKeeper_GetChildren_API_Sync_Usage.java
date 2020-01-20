package chapter05.part3_4;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
// 使用同步API获取子节点列表
public class ZooKeeper_GetChildren_API_Sync_Usage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;
    
    public static void main(String[] args) throws Exception {
        String path = "/zk-book";
        zk = new ZooKeeper("localhost:2181", 5000, new ZooKeeper_GetChildren_API_Sync_Usage());
        
        connectedSemaphore.await();
        // 持久节点
        zk.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        // 临时节点
        zk.create(path + "/c1", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        
        List<String> childrenList = zk.getChildren(path, true);
        System.out.println(childrenList);
    
     // 临时节点
        zk.create(path + "/c2", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zk.create(path + "/c3", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.in.read();
    }
    @Override
    public void process(WatchedEvent event) {
        // TODO Auto-generated method stub
       if(KeeperState.SyncConnected == event.getState()) {
           if(EventType.None == event.getType() && null == event.getPath()) {
               // 第一个节点 的事件
               connectedSemaphore.countDown();
           }else if(event.getType() == EventType.NodeChildrenChanged) {
               try {
                System.out.println("ReGet Child:" + zk.getChildren(event.getPath(), true));
            } catch (KeeperException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           }
       }
        
    }

}
