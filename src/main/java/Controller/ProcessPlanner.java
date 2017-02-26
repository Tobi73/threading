package Controller;

import Interface.IPlanner;
import Model.Process;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by andreyzaytsev on 26.02.17.
 */
public class ProcessPlanner implements IPlanner {

    private LinkedBlockingQueue<Process> processes;

    private Process activeProcess;

    private int timeQuantum;

    private int activeProcessWorkTime = 0;

    public ProcessPlanner(int timeQuantum, LinkedBlockingQueue<Process> processes){
        this.timeQuantum = timeQuantum;
        this.processes = processes;
    }

    @Override
    public boolean isReadyForChange() {
        if(activeProcessWorkTime == timeQuantum){
            return true;
        }
        return false;
    }

    @Override
    public void makeWork() throws InterruptedException {
        if(activeProcess == null){
            activeProcess = processes.poll();
        }
        if(isReadyForChange()){
            change();
        }
        if(!isOver()){
            activeProcessWorkTime++;
            activeProcess.work();
        } else {
            end();
        }
    }

    @Override
    public boolean isOver() {
        if(activeProcess.isOver()){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isEmpty() {
        if(processes.isEmpty()){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void change() {
        activeProcessWorkTime = 0;
        processes.add(activeProcess);
        activeProcess = processes.poll();
    }

    @Override
    public void end() {
        activeProcess = processes.poll();
    }
}
