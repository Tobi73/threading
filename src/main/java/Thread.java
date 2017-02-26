import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by andreyzaytsev on 20.02.17.
 */



public class Thread {


    private int timeToWork;
    private int id;

    /*

    private Queue<Process> processQueue;
    private Process processAtWork;
    private int maxTimeQuantum;
    private int counter = 0;
    private String stateInfo;
    private States state = States.PROCESS_START;

    public Thread(int maxTimeQuantum){
        this.processQueue = new LinkedBlockingQueue<>();
        this.maxTimeQuantum = maxTimeQuantum;
    }

    public void addNewProcess(Process proc){
        this.processQueue.add(proc);
    }

    public String getStateInfo(){
        if(state == States.PROCESS_START){
            return "Process with ID" + processAtWork.getId() + " has started. Time quantum - " + processAtWork.getTimeQuantum();
        }
        if(state == States.PROCESS_CHANGED){
            return "Process with ID" + processAtWork.getId() + " has begun it's work. Time quantum - " + processAtWork.getTimeQuantum();
        }
        if(state == States.PROCESS_WORKING){
            return  "Process with ID" + processAtWork.getId() + " is working. Remaining time (" + counter + "/" + processAtWork.getTimeQuantum() + ")";
        }
        if(state == States.PROCESS_TIMELIMIT){
            return "Last process has reached it's time limit. Remaining time quantum";
        }
        if(state == States.PROCESS_ENDED){
            return "Process with has finished it's work.";
        }
        return null;
    }

    public boolean act() throws InterruptedException {
        if(isReadyToAct()){
            if(processAtWork == null){
                processAtWork = processQueue.poll();
                stateInfo = "New process will begin - ID " + processAtWork.getId() + "(time quantum:" + processAtWork.getTimeQuantum() + ")";
            }
            if(counter == processAtWork.getTimeQuantum()){
                stateInfo = "The process has finished it's work - ID " + processAtWork.getId();
                processAtWork = null;
                counter = 0;
                return true;
            }
            if(counter == maxTimeQuantum) {
                stateInfo = "The process time has reached it's limit - ID " + processAtWork.getId();
                int newTimeQuantum = processAtWork.getTimeQuantum() - counter;
                counter = 0;
                processAtWork.setTimeQuantum(newTimeQuantum);
                processQueue.add(processAtWork);
                processAtWork = null;
                return true;
            }
            stateInfo = "The process is at work - ID " + processAtWork.getId() + "(time:" + counter + ")";
            counter++;
            processAtWork.work();
            return true;
        } else {
            return false;
        }
    }

    public boolean threadIsNotOver(){
        if(isReadyToAct()){
            if(processAtWork == null){
                state = States.PROCESS_START;
                return true;
            }
            if(counter == processAtWork.getTimeQuantum()){
                state = States.PROCESS_CHANGED;
                return true;
            }
            if(counter == maxTimeQuantum) {
                state = States.PROCESS_TIMELIMIT;
                return true;
            }
            state = States.PROCESS_WORKING;
            return true;
        } else {
            return false;
        }
    }


    public void work() throws InterruptedException {
        if(state == States.PROCESS_START){
            processAtWork = processQueue.poll();
        }
        if(state == States.PROCESS_CHANGED){
            processAtWork = processQueue.poll();
        }
        if(state == States.PROCESS_WORKING){
            counter++;
            processAtWork.work();
        }
        if(state == States.PROCESS_TIMELIMIT){
            int newTimeQuantum = processAtWork.getTimeQuantum() - counter;
            counter = 0;
            processAtWork.setTimeQuantum(newTimeQuantum);
            processQueue.add(processAtWork);
            processAtWork = null;
        }
        if(state == States.PROCESS_ENDED){
            processAtWork = null;
            counter = 0;
        }
    }


    public boolean isReadyToAct(){
        if(!processQueue.isEmpty() || processAtWork != null){
            return true;
        } else {
            return false;
        }
    }

    */
    public Thread(int id, int timeToWork){
        this.timeToWork = timeToWork;
        this.id = id;
    }


    public boolean work() throws InterruptedException {
        if(timeToWork != 0){
            timeToWork--;
            TimeUnit.SECONDS.wait(1);
            return true;
        } else {
            return false;
        }
    }


}
