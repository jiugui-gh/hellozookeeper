package chapter05.part4_1;

import java.io.IOException;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

// 获取节点数据
public class Get_Data_Sample {

    public static void main(String[] args) throws InterruptedException, IOException {
        String path = "/zk-book";
        ZkClient zkClient = new ZkClient("localhost:2181", 5000);
     
        
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            
            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                // TODO Auto-generated method stub
                System.out.println("Node " + dataPath + " deleted");
            }
            
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                // TODO Auto-generated method stub
                System.out.println("Node " + dataPath + " change, new data:" + data);
            }
        });
        zkClient.createEphemeral(path, "123");
        Object readData = zkClient.readData(path);
        System.out.println(readData);
        zkClient.writeData(path, "456");
        Thread.sleep(1000);
        zkClient.delete(path);
        
        System.in.read();
    }
}
