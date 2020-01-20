package chapter05.part4_1;

import org.I0Itec.zkclient.ZkClient;

public class Del_Data_Sample {

    public static void main(String[] args) {
        String path = "/zk-book";
        ZkClient zkClient = new ZkClient("localhost:2181",5000);
        zkClient.createPersistent(path,"");
        zkClient.createPersistent(path + "/c1" + "");
        zkClient.deleteRecursive(path);
    }
}
