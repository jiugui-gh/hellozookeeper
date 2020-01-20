package chapter05.part3_7;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
//使用含权限信息的ZooKeeper会话创建数据节点
public class AuthSample {

    final static String PATH = "/zk-book-auth_test";
    
    public static void main(String[] args){
        try {
            ZooKeeper zooKeeper = new ZooKeeper("localhost:2181",5000,null);
            zooKeeper.addAuthInfo("digest", "foo:true".getBytes());
            zooKeeper.create(PATH, "init".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
            System.in.read();
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
