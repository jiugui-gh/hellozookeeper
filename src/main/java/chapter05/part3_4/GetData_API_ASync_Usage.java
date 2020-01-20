package chapter05.part3_4;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.ZooDefs.Ids;

public class GetData_API_ASync_Usage implements Watcher {

    private static CountDownLatch connectedSemphore = new CountDownLatch(1);
    private static ZooKeeper zk;

    public static void main(String[] args) throws Exception {
        
        String path = "/zk-book";
        zk = new ZooKeeper("localhost:2181",5000,new GetData_API_ASync_Usage());
        
        connectedSemphore.await();
        
        zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        
        zk.getData(path, true,new IDataCallback(), null);
        zk.setData(path, "456".getBytes(), -1);
        
        System.in.read();
    }
    @Override
    public void process(WatchedEvent event) {
        // TODO Auto-generated method stub
        if(KeeperState.SyncConnected == event.getState()) {
            if(EventType.None == event.getType()) {
                connectedSemphore.countDown();
            }else if(EventType.NodeDataChanged == event.getType()) {
                zk.getData(event.getPath(), true, new IDataCallback(),null);
            }
        }
    }

}
class IDataCallback implements AsyncCallback.DataCallback{

    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        // TODO Auto-generated method stub
        System.out.println(rc + ", " + path + ", " + new String(data));
        System.out.println(stat.getCzxid() + ", " + 
                            stat.getMzxid() + ", " + 
                            stat.getVersion());
    }
    
}