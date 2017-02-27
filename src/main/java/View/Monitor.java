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
        LinkedBlockingQueue<ThreadPlanner> threadPlanners = new LinkedBlockingQueue<>();
        int timeQuantum = 30;
        Random rand = new Random();
        for(int i = 0; i < 3; i++){
            LinkedBlockingQueue<Thread> threads = new LinkedBlockingQueue<>();
            for(int j = 0; j < rand.nextInt(5) + 1; j++){
                threads.add(new Thread(rand.nextInt(5) + 15, j));
            }
            threads.addAll(threads);
            threadPlanners.add(new ThreadPlanner(threads, timeQuantum/threads.size()));
            processes.add(new Process(i, threads));
        }
        ProcessPlanner planner = new ProcessPlanner(timeQuantum, processes);
        while(!planner.isEmpty()){
            try {
                planner.makeWork();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
