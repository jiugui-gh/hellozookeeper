package chapter05.part4_1;

import org.I0Itec.zkclient.ZkClient;

public class Create_Session_Sample {

    public static void main(String[] args) {
        ZkClient zkCilent = new ZkClient("localhost:2181",5000);
        System.out.println("ZooKeeper session setablished" + zkCilent);
    }
}
