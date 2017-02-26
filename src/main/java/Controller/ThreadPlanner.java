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

    public ThreadPlanner(LinkedBlockingQueue<Thread> threads, int processTimeQuantum){
        this.threads = threads;
        this.threadTimeQuantum = processTimeQuantum/threads.size();
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
            change();
        }
        if(!isOver()){
            activeThread.work();
            activeThreadWorkTime++;
        } else {
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
        if(threads.isEmpty()){
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
