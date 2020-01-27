package chapter05.part4_2;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Recipes_Barrier2 {
    static String barrier_path = "/curator_recipes_barrier_path";
    
    public static void main(String[] args) {
        
        for(int i = 0; i < 5; i ++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        CuratorFramework client = CuratorFrameworkFactory.builder()
                                .connectString("localhost:2181")
                                .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
                        client.start();
                        DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(client, barrier_path, 5);
                        Thread.sleep( Math.round(Math.random() * 3000) );
                        System.out.println(Thread.currentThread().getName() + "号进入barrier" );
                       
                        barrier.enter();
                        System.out.println("启动...");
                        Thread.sleep( Math.round(Math.random() * 3000) );
                        barrier.leave();
                        System.out.println("退出");
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                
            }).start();;
        }
    }
}
