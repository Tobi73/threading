import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by andreyzaytsev on 20.02.17.
 */
public class Process {

    private int id;

    private int timeQuantum;

    public Process(int id, int timeQuantum){
        this.id = id;
        this.timeQuantum = timeQuantum;
    }

    public int getId(){
        return this.id;
    }

    public int getTimeQuantum(){
        return this.timeQuantum;
    }

    public void setTimeQuantum(int timeQuantum){
        this.timeQuantum = timeQuantum;
    }

    public void work() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
    }


}
