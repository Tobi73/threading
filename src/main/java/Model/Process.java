package Model;
import Controller.ThreadPlanner;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by andreyzaytsev on 26.02.17.
 */
public class Process {

    private int id;

    private ThreadPlanner planner;


    public Process(int id, int processTimeQuantum, LinkedBlockingQueue<Thread> threads){
        this.id = id;
        this.planner = new ThreadPlanner(threads, processTimeQuantum);
    }

    public int getId(){
        return this.id;
    }

    public void work() throws InterruptedException {
        planner.makeWork();
    }

    public boolean isOver(){
        if(planner.isEmpty()){
            return true;
        } else  {
            return false;
        }
    }


}
