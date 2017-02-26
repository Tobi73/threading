package Model;

import java.util.concurrent.TimeUnit;

/**
 * Created by andreyzaytsev on 26.02.17.
 */
public class Thread {

    private int timeToWork;

    private int id;

    public Thread(int timeToWork, int id){
        this.timeToWork = timeToWork;
        this.id = id;
    }

    public void work() throws InterruptedException {
        TimeUnit.SECONDS.wait(1);
        timeToWork--;
    }

    public boolean isOver(){
        if(timeToWork == 0){
            return true;
        } else {
            return false;
        }
    }

    public int getTimeToWork(){
        return this.timeToWork;
    }

    public void setTimeToWork(int timeToWork){
        this.timeToWork = timeToWork;
    }

}
