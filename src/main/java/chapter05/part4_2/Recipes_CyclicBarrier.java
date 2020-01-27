package chapter05.part4_2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Recipes_CyclicBarrier {

    public static CyclicBarrier barrier = new CyclicBarrier(3);
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(new Thread(new Runner("1号选手")));
        executor.submit(new Thread(new Runner("2号选手")));
        executor.submit(new Thread(new Runner("3号选手")));
        executor.shutdown();
    }
}
class Runner implements Runnable{
    private String name;
    
    public Runner(String name) {
        super();
        this.name = name;
    }


    @Override
    public void run() {
        // TODO Auto-generated method stub
        System.out.println(name + " 准备好了。");
        try {
            Recipes_CyclicBarrier.barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(name + " 起跑");
    }
    
}
