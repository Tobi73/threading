package Controller;

import Interface.IPlanner;
import Model.States;
import Model.Thread;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by andreyzaytsev on 26.02.17.
 */
public class ThreadPlanner implements IPlanner {

    private LinkedBlockingQueue<Thread> threads;

    private Thread activeThread;

    private States state;

    private int threadTimeQuantum;

    private int activeThreadWorkTime = 0;

    public ThreadPlanner(int processTimeQuantum){
        this.threadTimeQuantum = processTimeQuantum;
    }

    public void setThreadsToPlan(LinkedBlockingQueue<Thread> threads){
        this.threads = threads;
    }

    public void setActiveThread(Thread thread){
        this.activeThread = thread;
    }

    public Thread getActiveThread(){
        return activeThread;
    }


    @Override
    public boolean isReadyForChange() {
        if(activeThreadWorkTime == threadTimeQuantum){
            return true;
        }
        return false;
    }

    @Override
    public void makeWork() throws InterruptedException {
        if(activeThread == null){
            activeThread = threads.poll();
        }
        if(isReadyForChange()){
            System.out.print("/Thread " + activeThread.getId() + " was changed to ");
            change();
            System.out.println("Thread " + activeThread.getId());
            state = States.PROCESS_CHANGED;
            return;
        }
        if(!isOver()){
            System.out.print("/Thread " + activeThread.getId() + " is working (" + activeThread.getTimeToWork() + "). ");
            System.out.println(threads.size() + " threads are waiting");
            activeThread.work();
            activeThreadWorkTime++;
            state = States.PROCESS_WORKING;
        } else {
            System.out.println("/Thread " + activeThread.getId() + " has ended");
            end();
            state = States.PROCESS_ENDED;
        }
    }

    @Override
    public boolean isOver() {
        if(activeThread.isOver()){
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        if(threads.isEmpty() && activeThread == null){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void change() {
        activeThreadWorkTime = 0;
        threads.add(activeThread);
        activeThread = threads.poll();
    }

    @Override
    public void end() {
        activeThreadWorkTime = 0;
        activeThread = threads.poll();
    }
}
