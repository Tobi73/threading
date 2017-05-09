package Model;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by andreyzaytsev on 26.02.17.
 */
public class Thread {

    private int timeToWork;

    private UUID id;

    public Thread(int timeToWork, UUID id){
        this.timeToWork = timeToWork;
        this.id = id;
    }

    public UUID getId(){
        return id;
    }

    public void work() throws InterruptedException {
//        TimeUnit.SECONDS.sleep(1);
        timeToWork--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Thread)) return false;

        Thread thread = (Thread) o;

        if (getTimeToWork() != thread.getTimeToWork()) return false;
        return getId() == thread.getId();
    }

    @Override
    public int hashCode() {
        int result = getTimeToWork();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        return result;
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


}
