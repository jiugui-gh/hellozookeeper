package chapter05.part4_2;

import java.io.IOException;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Create_Session_Sample_With_Namespace {

    public static void main(String[] args) throws IOException {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                                        .connectString("localhost:2181")
                                        .sessionTimeoutMs(50000)
                                        .connectionTimeoutMs(5000)
                                        .namespace("base")
                                        .retryPolicy(retryPolicy)
                                        .build();
        client.start();
        System.in.read();
    }
    
}
