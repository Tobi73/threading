package Model;
import Controller.ThreadPlanner;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by andreyzaytsev on 26.02.17.
 */
public class Process {

    private int id;

    private LinkedBlockingQueue<Thread> threads;

    private Thread activeThread;

    public Process(int id, LinkedBlockingQueue<Thread> threads){
        this.id = id;
        this.threads = threads;
    }

    public int getId(){
        return this.id;
    }

    public void setActiveThread(Thread thread){
        activeThread = thread;
    }

    public Thread getActiveThread(){
        return activeThread;
    }

    public boolean isOver(){
        if(threads.isEmpty()){
            return true;
        } else  {
            return false;
        }
    }

    
}
