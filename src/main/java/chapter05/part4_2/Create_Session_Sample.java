package chapter05.part4_2;

import java.io.IOException;
import java.util.Random;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

public class Create_Session_Sample {

    public static void main(String[] args) throws IOException {
        
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181",5000,3000, retryPolicy);
        client.start();
        System.in.read();
    }
    public static int getSleepTimeMs(int retryCount, long elapsedTimeMs,int baseSleepTimeMs ,Random random,int maxSleepMs)
    {
        // copied from Hadoop's RetryPolicies.java
        int sleepMs = baseSleepTimeMs * Math.max(1, random.nextInt(1 << (retryCount + 1)));
        if ( sleepMs > maxSleepMs )
        {
            System.out.println(String.format("Sleep extension too large (%d). Pinning to %d", sleepMs, maxSleepMs));
            sleepMs = maxSleepMs;
            System.out.println(sleepMs);
        }
        return sleepMs;
    }
    @Test
    public void testTime() {
        long start = System.currentTimeMillis();
        for(int i = 0; i < Integer.MAX_VALUE ; i++) {
            long end = System.currentTimeMillis();
            Create_Session_Sample.getSleepTimeMs(i, end - start, 1000, new Random(), 5000);
        }
        
    }
}
