package chapter05.part3_7;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

// 删除节点的权限控制
public class AuthSample_Delete {

    final static String PATH  = "/zk-book-auth_test";
    final static String PATH2 = "/zk-book-auth_test/child";
    
    public static void main(String[] args) {
        try {
            ZooKeeper zk1 = new ZooKeeper("localhost:2181",5000,null);
            zk1.addAuthInfo("digest", "foo:true".getBytes());
            zk1.create(PATH, "init".getBytes(), Ids.CREATOR_ALL_ACL , CreateMode.PERSISTENT);
            zk1.create(PATH2, "init".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
            
            try {
                ZooKeeper zk2 = new ZooKeeper("localhost:2181",5000,null);
                try {
                    System.out.println("获取子节点" + zk2.getData(PATH2, false, null));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("获取子节点失败" + e.getMessage());
                }
                zk2.delete(PATH2, -1);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("节点删除失败" + e.getMessage());
            }
            
            ZooKeeper zk3 = new ZooKeeper("localhost:2181",5000,null);
            zk3.addAuthInfo("digest", "foo:true".getBytes());
            zk3.delete(PATH2, -1);
            System.out.println("成功删除节点" + PATH2);
            
            ZooKeeper zk4 = new ZooKeeper("localhost:2181",5000,null);
            zk4.delete(PATH, -1);
            System.out.println("成功删除节点" + PATH);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
