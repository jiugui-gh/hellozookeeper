package chapter05.part4_2;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

// 使用Curator删除节点
public class Del_Data_Sample {

    static String path = "/zk-book/c1";
    
    static CuratorFramework client = CuratorFrameworkFactory.builder()
                                       .connectString("localhost:2181")
                                       .connectionTimeoutMs(1000)
                                       .sessionTimeoutMs(5000)
                                       .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                       .build();
    
    public static void main(String[] args) throws Exception {
        client.start();
        client.create().creatingParentsIfNeeded()
        .withMode(CreateMode.EPHEMERAL)
        .forPath(path,"init".getBytes());
        
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath(path);
        System.out.println(stat.getVersion());
        /*client.delete().deletingChildrenIfNeeded()
                        .withVersion(stat.getVersion()).forPath(path);*/
        path = "/zk-book";
        client.delete().guaranteed().withVersion(stat.getVersion()).forPath(path);
    }
}
