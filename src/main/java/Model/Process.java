package Model;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by andreyzaytsev on 26.02.17.
 */
public class Process {

    private int id;

    private MemoryPage[] virtualMemory;

    private LinkedBlockingQueue<Thread> threads;

    public Process(int id, LinkedBlockingQueue<Thread> threads, MemoryPage[] virtualMemory){
        this.id = id;
        this.threads = threads;
        this.virtualMemory = virtualMemory;
    }

    public int getId(){
        return this.id;
    }

    public LinkedBlockingQueue<Thread> getThreads(){
        return threads;
    }

    public MemoryPage[] getVirtualMemory(){
        return virtualMemory;
    }

    
}
