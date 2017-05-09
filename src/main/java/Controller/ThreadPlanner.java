package Controller;

import Interface.IPlanner;
import Model.Thread;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by andreyzaytsev on 26.02.17.
 */
public class ThreadPlanner implements IPlanner {

    private LinkedBlockingQueue<Thread> threads;

    private Thread activeThread;

    private int threadTimeQuantum;

    private int activeThreadWorkTime = 0;

    public String infoState = "Initializing";

    public void setThreadsToPlan(LinkedBlockingQueue<Thread> threads, int processTimeQuantum){
        this.threads = threads;
        int threadsNum = threads.size();
        if(activeThread != null){
            threadsNum++;
        }
        threadTimeQuantum = processTimeQuantum/threadsNum;
        if(threadTimeQuantum == 0){
            threadTimeQuantum = 1;
        }
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
            infoState = "/Thread " + activeThread.getId() + " was changed to ";
            change();
            infoState+= "Thread " + activeThread.getId();
            return;
        }
        if(!isOver()){
            activeThreadWorkTime++;
            infoState = "/Thread " + activeThread.getId() + " is working (" + activeThread.getTimeToWork() + "). ";
            infoState+=threads.size() + " threads are waiting. Working time - (" + activeThreadWorkTime + "/" + threadTimeQuantum + ")";
            activeThread.work();
        } else {
            infoState = "/Thread " + activeThread.getId() + " has ended";
            end();
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
