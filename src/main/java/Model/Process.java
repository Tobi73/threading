package Model;
import Controller.ThreadPlanner;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by andreyzaytsev on 26.02.17.
 */
public class Process {

    private int id;

    private LinkedBlockingQueue<Thread> threads;

    public Process(int id, LinkedBlockingQueue<Thread> threads){
        this.id = id;
        this.threads = threads;
    }

    public int getId(){
        return this.id;
    }

    public LinkedBlockingQueue<Thread> getThreads(){
        return threads;
    }

    
}
