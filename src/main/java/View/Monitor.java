package View;


import Controller.MemoryManager;
import Controller.ThreadPlanner;
import Model.MemoryPage;
import Model.Thread;
import Model.Process;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by andreyzaytsev on 26.02.17.
 */
public class Monitor {

    /*
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
    */

    public static void main(String[] args){
        List<Process> processes = new ArrayList<>();
        List<Thread> allThreads = new ArrayList<>();
        int timeQuantum = 5;
        int workTime = 0;
        Random rand = new Random();
        for(int i = 0; i < 3; i++){
            LinkedBlockingQueue<Thread> threads = new LinkedBlockingQueue<>();
            for(int j = 0; j < rand.nextInt(5) + 1; j++){
                threads.add(new Thread(rand.nextInt(5) + 1, j));
            }
            allThreads.addAll(threads);
            MemoryPage[] virtualMemory = new MemoryPage[rand.nextInt(15) + 5];
            for(int j = 0; j < virtualMemory.length; j++){
                virtualMemory[j] = new MemoryPage(j);
            }
            processes.add(new Process(i, threads, virtualMemory));
        }
        MemoryPage[] physicalMemory = new MemoryPage[20];
        for(int i = 0; i < physicalMemory.length; i++){
            physicalMemory[i] = new MemoryPage(i);
        }
        MemoryPage[] swap = new MemoryPage[40];
        for(int i = 0; i < swap.length; i++){
            swap[i] = new MemoryPage(physicalMemory.length + i);
        }
        try {
            MemoryManager manager = new MemoryManager(physicalMemory, swap, processes);
            ThreadPlanner planner = new ThreadPlanner();
            while (!processes.isEmpty()){
                Iterator<Process> iterator = processes.iterator();
                while(iterator.hasNext()){
                    Process process = iterator.next();
                    System.out.println(" Process " + process.getId());
                    planner.setThreadsToPlan(process.getThreads(), timeQuantum);
                    while (!planner.isEmpty() && timeQuantum != workTime){
                        int referencedListId = rand.nextInt(process.getVirtualMemory().length);
                        planner.makeWork();
                        System.out.print("Process " + process.getId() + " is working (" + workTime + "/" + timeQuantum + ")");
                        manager.tryRefer(process.getId(), referencedListId);
                        System.out.println(planner.infoState);
                        System.out.println(manager.showTable(process.getId(), referencedListId));
                        TimeUnit.SECONDS.sleep(1);
                        workTime++;
                    }
                    workTime=0;
                    if (planner.isEmpty()){
                        System.out.println("Process " + process.getId() + " has ended");
                        iterator.remove();
                        manager.emptyPages(process.getId());
                        continue;
                    }
                    System.out.print("Process " + process.getId() + " was changed to");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
