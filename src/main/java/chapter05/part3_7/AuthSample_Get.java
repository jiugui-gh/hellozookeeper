package chapter05.part3_7;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class AuthSample_Get {

    final static String PATH = "/zk-book-auth_test";
    public static void main(String[] args) {
        try {
            ZooKeeper zk = new ZooKeeper("localhost:2181",5000,null);
            zk.addAuthInfo("digest", "foo:true".getBytes());
            zk.create(PATH, null, Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
            
            ZooKeeper zk1 = new ZooKeeper("localhost:2181",5000,null);
            //zk1.addAuthInfo("digest", "foo:true".getBytes());
            System.out.println(zk1.getData(PATH, false, null));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
