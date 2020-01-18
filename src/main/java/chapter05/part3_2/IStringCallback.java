package chapter05.part3_2;

import org.apache.zookeeper.AsyncCallback;

public class IStringCallback implements AsyncCallback.StringCallback{

    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        // TODO Auto-generated method stub
        System.out.println("Create path result: [" + rc + ", " + path + ", " + ctx + ", real path name: " + name);
    }
    
}