package chapter05.part4_2;

import java.io.IOException;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
//使用Fluent风格的API接口来创建一个ZooKeeper客户端
public class Create_Session_Sample_fluent {

    public static void main(String[] args) throws IOException {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                               .connectString("localhost:2181")
                               .sessionTimeoutMs(5000)
                               .retryPolicy(retryPolicy)
                               .build();
        client.start();
        System.in.read();
    }
    
}
