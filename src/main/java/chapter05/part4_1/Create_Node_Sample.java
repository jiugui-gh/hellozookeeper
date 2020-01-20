package chapter05.part4_1;

import org.I0Itec.zkclient.ZkClient;

public class Create_Node_Sample {

    public static void main(String[] args) {
        
        ZkClient zkClient = new ZkClient("localhost:2181",5000);
        String path = "/zk-book/c1";
        // 设置为true 为创建父节点  持久节点
        zkClient.createPersistent(path, true);
    }
}
