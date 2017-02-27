package Controller;

import Interface.IPlanner;
import Model.Process;
import Model.States;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by andreyzaytsev on 26.02.17.
 */
public class ProcessPlanner implements IPlanner {

    private LinkedBlockingQueue<Process> processes;

    private Process activeProcess;

    private int timeQuantum;

    private int activeProcessWorkTime = 0;

    private States state;

    public ProcessPlanner(int timeQuantum, LinkedBlockingQueue<Process> processes){
        this.timeQuantum = timeQuantum;
        this.processes = processes;
    }

    public Process getActiveProcess(){
        return activeProcess;
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
            System.out.print("Process " + activeProcess.getId() + " is changed to");
            change();
            System.out.println(" Process" + activeProcess.getId());
            state = States.PROCESS_CHANGED;
            return;
        }
        if(!isOver()){
            System.out.print("Process " + activeProcess.getId() + " is working. " + processes.size() + " processes are waiting");
            activeProcessWorkTime++;
            state = States.PROCESS_WORKING;
        } else {
            System.out.println("Process " + activeProcess.getId() + " has ended");
            end();
            state = States.PROCESS_ENDED;
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
        if(processes.isEmpty() && activeProcess == null){
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
        activeProcessWorkTime = 0;
        activeProcess = processes.poll();
    }
}
