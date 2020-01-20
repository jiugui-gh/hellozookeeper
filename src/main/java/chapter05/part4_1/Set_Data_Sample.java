package chapter05.part4_1;

import org.I0Itec.zkclient.ZkClient;

public class Set_Data_Sample {

    public static void main(String[] args) throws InterruptedException {
        String path = "/zk-book";
        ZkClient zkClient = new ZkClient("localhost:2181", 2000);
        zkClient.createEphemeral(path,new Integer(1));
        Thread.sleep(5000);
        zkClient.writeData(path, new String("酒鬼"));
        // java.io.NotSerializableException: java.lang.Object 没有序列化
        // zkClient.writeData(path, new Object());
    }
}
