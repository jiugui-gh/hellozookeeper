package chapter05.part3_5;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
//ZooKeeper API 更新节点数据内容，使用异步(async)接口。
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.ZooDefs.Ids;

public class SetData_API_ASync_Usage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk;
    
    public static void main(String[] args) throws Exception {
        String path = "/zk-book";
        zk = new ZooKeeper("localhost:2181", 
                5000, //
                new SetData_API_ASync_Usage());
        connectedSemaphore.await();
        
        zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL );
        zk.setData(path, "456".getBytes(), -1,new IStatCallback(),null);
        
        System.in.read();
    }
    @Override
    public void process(WatchedEvent event) {
        // TODO Auto-generated method stub
        if (KeeperState.SyncConnected == event.getState()) {
            if (EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
            }
        }
    }

}

class IStatCallback implements AsyncCallback.StatCallback{

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        // TODO Auto-generated method stub
        if(rc == 0) {
            System.out.println("SUCCESS");
        }
    }
    
}
