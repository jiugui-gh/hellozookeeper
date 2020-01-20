package chapter05.part4_1;

import org.I0Itec.zkclient.ZkClient;

public class Exist_Node_Sample {

    public static void main(String[] args) {
        String path = "/zk-book";
        ZkClient zkClient = new ZkClient("localhost:2181", 2000);
        System.out.println("Node" + path + " exists " + zkClient.exists(path));
    }
}
