package chapter05.part4_2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class Recipes_NoLock {

    public static void main(String[] args) {
        final CountDownLatch down = new CountDownLatch(1);
        
        for(int i = 0; i < 10; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        down.await();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                    String format = sdf.format(new Date());
                    System.out.println("生成的订单式： " + format);
                }
                
            }).start();
        }
        down.countDown();
    }
}
