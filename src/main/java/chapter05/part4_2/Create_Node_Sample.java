package chapter05.part4_2;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLBackgroundPathAndBytesable;
import org.apache.curator.framework.api.CreateBuilder;
import org.apache.curator.framework.api.ProtectACLCreateModePathAndBytesable;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class Create_Node_Sample {

  /*  public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                               .connectString("localhost:2181")
                               .connectionTimeoutMs(5000)
                               .sessionTimeoutMs(50000)
                               .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                               .build();
        client.start();
        
        String path = "/zk-book/c1";
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(path,"init".getBytes());
        
        System.in.read();
    }*/
    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                               .connectString("localhost:2181")
                               .connectionTimeoutMs(5000)
                               .sessionTimeoutMs(50000)
                               .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                               .build();
        client.start();
        
        String path = "/zk-book/c1";
        CreateBuilder create = client.create();
        ProtectACLCreateModePathAndBytesable<String> creatingParentsIfNeeded = create.creatingParentsIfNeeded();
        ACLBackgroundPathAndBytesable<String> withMode = creatingParentsIfNeeded.withMode(CreateMode.EPHEMERAL);
        String forPath = withMode.forPath(path,"init".getBytes());
        System.out.println(forPath);
        System.in.read();
    }
}
