package chapter05.part4_2;

import java.io.IOException;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Recipes_MasterSelect {

    static String master_path = "/curator_recipes_master_path";
    
    static CuratorFramework client = CuratorFrameworkFactory.builder()
                                            .connectionTimeoutMs(5000)
                                            .connectString("localhost:2181")
                                            .sessionTimeoutMs(50000)
                                            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                            .build();
    public static void main(String[] args) throws IOException {
        client.start();
        
        LeaderSelector selector = new LeaderSelector(client, master_path, new LeaderSelectorListenerAdapter() {

            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                // TODO Auto-generated method stub
                System.out.println("成为Master角色");
                Thread.sleep(3000);
                System.out.println("完成Master操作,释放Master权利");
            }
            
        });
        selector.autoRequeue();
        selector.start();
        //selector.close();
        System.in.read();
    }
}
