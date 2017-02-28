package Controller;

import Interface.IPlanner;
import Model.*;
import Model.Process;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by andreyzaytsev on 26.02.17.
 */
public class ProcessPlanner implements IPlanner {

    private LinkedBlockingQueue<Process> processes;

    private Process activeProcess;

    private ThreadPlanner planner;

    private int timeQuantum;

    public ProcessPlanner(int timeQuantum, LinkedBlockingQueue<Process> processes, ThreadPlanner planner){
        this.timeQuantum = timeQuantum;
        this.processes = processes;
        this.planner = planner;
    }

    @Override
    public boolean isReadyForChange() {
        //if(activeProcessWorkTime == timeQuantum){
        //    return true;
        //}
        return false;
    }

    @Override
    public void makeWork() throws InterruptedException {
        if(activeProcess == null){
            activeProcess = processes.poll();
        }
        if(isReadyForChange()){
            System.out.print("Process " + activeProcess.getId() + " is changed to");
            change();
            System.out.println(" Process" + activeProcess.getId());
            return;
        }
        if(!isOver()){
            System.out.print("Process " + activeProcess.getId() + " is working. " + processes.size() + " processes are waiting");
            //activeProcess.
            //planner.makeWork();
        } else {
            System.out.println("Process " + activeProcess.getId() + " has ended");
            end();
        }
    }

    @Override
    public boolean isOver() {
        //if(activeProcess.isOver()){
        //    return true;
        //} else {
        //    return false;
        //}
        return false;
    }

    @Override
    public boolean isEmpty() {
        if(processes.isEmpty() && activeProcess == null){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void change() {
        processes.add(activeProcess);
        activeProcess = processes.poll();
    }

    @Override
    public void end() {
        activeProcess = processes.poll();
    }
}
