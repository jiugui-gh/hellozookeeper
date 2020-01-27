package chapter05.part4_2;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class NodeCache_Sample {

    static String path = "/zk-book/nodecache";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectionTimeoutMs(5000)
            .connectString("localhost:2181")
            .sessionTimeoutMs(50000)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();
    
    public static void main(String[] args) throws Exception {
        
        client.start();
       /* client.create()
              .creatingParentsIfNeeded()
              .withMode(CreateMode.EPHEMERAL)
              .forPath(path, "init".getBytes());*/
        final NodeCache cache = new NodeCache(client,path,false);
        cache.start(false);
        cache.getListenable().addListener(new NodeCacheListener() {
            
            @Override
            public void nodeChanged() throws Exception {
                // TODO Auto-generated method stub
                System.out.println("Node data update, new data :" + 
                new String(cache.getCurrentData().getData()));
                
            }
        });
        client.create()
        .creatingParentsIfNeeded()
        .withMode(CreateMode.EPHEMERAL)
        .forPath(path, "init".getBytes());
        client.setData().forPath(path,"data".getBytes());
        Thread.sleep(1000);
        client.delete().deletingChildrenIfNeeded().forPath(path);
        System.in.read();
        cache.close();
    } 
}
