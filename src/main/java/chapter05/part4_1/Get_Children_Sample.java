package chapter05.part4_1;

import java.io.IOException;
import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

public class Get_Children_Sample {

    public static void main(String[] args) throws InterruptedException, IOException {
        String path = "/zk-book";
        ZkClient zkClient = new ZkClient("localhost:2181",5000);
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                // TODO Auto-generated method stub
                System.out.println(parentPath + " 's child change , currentChilds : " + currentChilds);
            }
        });
        
        zkClient.createPersistent(path);
        Thread.sleep(1000);
        System.out.println(zkClient.getChildren(path));
        Thread.sleep(1000);
        zkClient.createPersistent(path + "/c1");
        Thread.sleep(1000);
        zkClient.delete(path + "/c1");
        Thread.sleep(1000);
        zkClient.delete(path);
        System.in.read();
    }
    
}
