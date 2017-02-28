package View;


import Controller.ProcessPlanner;
import Controller.ThreadPlanner;
import Model.Thread;
import Model.Process;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by andreyzaytsev on 26.02.17.
 */
public class Monitor {

    public static void main(String[] args) {
        LinkedBlockingQueue<Process> processes = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<Thread> allThreads = new LinkedBlockingQueue<>();
        int timeQuantum = 15;
        Random rand = new Random();
        for(int i = 0; i < 3; i++){
            LinkedBlockingQueue<Thread> threads = new LinkedBlockingQueue<>();
            for(int j = 0; j < rand.nextInt(5) + 1; j++){
                threads.add(new Thread(rand.nextInt(5) + 15, j));
            }
            allThreads.addAll(threads);
            processes.add(new Process(i, threads));
        }
        ThreadPlanner planner = new ThreadPlanner(timeQuantum);
        for(Process process : processes){
            planner.setThreadsToPlan(process.getThreads());
            while (!planner.isEmpty()){
                try {
                    System.out.print("Process " + process.getId() + " is working");
                    planner.makeWork();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Process " + process.getId() + " has ended");
        }

    }


}
