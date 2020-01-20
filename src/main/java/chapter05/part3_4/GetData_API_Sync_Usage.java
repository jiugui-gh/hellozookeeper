package chapter05.part3_4;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

// ZooKeeper ApI获取节点数据内容，使用同步（sync）接口
public class GetData_API_Sync_Usage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;
    private static Stat stat = new Stat();    
    
    
    public static void main(String[] args) throws Exception {
        String path = "/zk-book";
        zk = new ZooKeeper("localhost:2181",5000,new GetData_API_Sync_Usage());
        
        connectedSemaphore.await();
        
        zk.create(path, "abc".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println(new String(zk.getData(path, true, stat)));
        System.out.println(stat.getCzxid() + "," + stat.getMzxid() + "," +stat.getVersion());
        
        zk.setData(path, "abc".getBytes(), -1);
        
        System.in.read();
        
    }
    
    @Override
    public void process(WatchedEvent event) {
        // TODO Auto-generated method stub

       if(KeeperState.SyncConnected == event.getState()) {
           if(event.getType() == EventType.None) {
               connectedSemaphore.countDown();
           }else if(EventType.NodeDataChanged == event.getType()) {
               try {
                System.out.println(new String(zk.getData(event.getPath(), true, stat)));
                System.out.println(stat.getCzxid() + "," +
                                    stat.getMzxid() + "," + 
                                    stat.getVersion());
                
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
