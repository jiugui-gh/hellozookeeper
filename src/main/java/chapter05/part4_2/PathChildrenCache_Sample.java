package chapter05.part4_2;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class PathChildrenCache_Sample {
    
    static String path = "/zk-book/test";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectionTimeoutMs(5000)
            .connectString("localhost:2181")
            .sessionTimeoutMs(50000)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();
    
    public static void main(String[] args) throws Exception {
        client.start();
        PathChildrenCache cache = new PathChildrenCache(client, path, true);
        cache.start(StartMode.NORMAL);
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                // TODO Auto-generated method stub
                switch(event.getType()) {
                case CHILD_ADDED :
                    System.out.println("CHILD_ADDED" + event.getData().getPath());
                    System.out.println(cache.getCurrentData());
                    break;
                case CHILD_UPDATED :
                    System.out.println("CHILD_UPDATED" + event.getData().getPath());
                    System.out.println(cache.getCurrentData());
                    break;
                case CHILD_REMOVED :
                    System.out.println("CHILD_REMOVED" + event.getData().getPath());
                    System.out.println(cache.getCurrentData());
                    break;
                case INITIALIZED :
                    System.out.println("INITIALIZED," + event.getData().getPath());
                    break;
                default :
                    break;
                }
            }
        });
        
        client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path);
        Thread.sleep(1000);
        client.create().withMode(CreateMode.PERSISTENT).forPath(path + "/c1");
        // 二级节点不会促发事件
        client.create().withMode(CreateMode.EPHEMERAL).forPath(path + "/c1/c2");
        Thread.sleep(1000);
        client.setData().forPath(path + "/c1","newData".getBytes());
        Thread.sleep(1000);
        client.delete().deletingChildrenIfNeeded().forPath(path + "/c1");
        Thread.sleep(1000);
        client.delete().forPath(path);
        System.in.read();
        cache.close();
    }
}
