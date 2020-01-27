package chapter05.part4_2;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

// 使用Curator更新数据内容
public class Set_Data_Sample {

    static String path = "/zk-book";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
                                       .connectString("localhost:2181")
                                       .connectionTimeoutMs(5000)
                                       .sessionTimeoutMs(50000)
                                       .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                       .build();
    
    public static void main(String[] args) throws Exception {
        client.start();
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(path,"init".getBytes());
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath(path);
        stat =  client.setData().withVersion(stat.getVersion()).forPath(path,"new".getBytes());
        System.out.println("Success set node for : " + path +  ", new version : " + stat.getVersion());
      
        try {
            client.setData().withVersion(stat.getVersion()).forPath(path,"123".getBytes());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Fail set node due to " + e.getMessage());
        }
    }
            
}
