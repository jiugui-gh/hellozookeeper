package chapter05.part3_7;

import java.io.IOException;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

public class AuthSample_Get2 {

    final static String PATH = "/zk-book-auth_test";
    
    public static void main(String[] args) {
        
        try {
            ZooKeeper zk1 = new ZooKeeper("localhost:2181",5000,null);
            zk1.addAuthInfo("digest", "abc:true".getBytes());
            zk1.create(PATH, "init".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
            
            ZooKeeper zk2 = new ZooKeeper("localhost:2181",5000,null);
            zk2.addAuthInfo("digest", "abc:true".getBytes());
            System.out.println(zk2.getData(PATH, false, null));
            
           /* ZooKeeper zk4 = new ZooKeeper("localhost:2181",5000,null);
            System.out.println(zk4.getData(PATH, false, null));*/
            
            ZooKeeper zk3 = new ZooKeeper("localhost:2181",5000,null);
            zk3.addAuthInfo("digest", "abc:false".getBytes());
            System.out.println(zk3.getData(PATH, false, null));
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeeperException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
