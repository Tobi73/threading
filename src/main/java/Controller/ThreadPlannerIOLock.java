package Controller;

import Model.MemoryPage;
import Model.Process;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by gman0_000 on 09.05.2017.
 */
public class ThreadPlannerIOLock extends ThreadPlannerIO {


    public ThreadPlannerIOLock(int ioLockChance, int ioLockRandTime){
        this.ioLockChance = ioLockChance;
        this.ioLockRandTime = ioLockRandTime;
        this.rand = new Random();
        this.timeCounter = 0;
    }

    @Override
    public void makeWork() throws InterruptedException {
        if(activeThread == null){
            activeThread = threads.poll();
        }
        if(isReadyForChange()){
            infoState = "/Thread " + activeThread.getId() + " was changed to ";
            change();
            infoState+= "Thread " + activeThread.getId();
            return;
        }
        if(!isOver()){
            activeThreadWorkTime++;
            infoState = "/Thread " + activeThread.getId() + " is working (" + activeThread.getTimeToWork() + "). ";
            infoState+=threads.size() + " threads are waiting. Working time - (" + activeThreadWorkTime + "/" + threadTimeQuantum + ")";
            activeThread.work();
            if(emulateIOProcessing()){
                infoState+= System.lineSeparator() + "IO Operation was initiated during thread work...";
            }
        } else {
            infoState = "/Thread " + activeThread.getId() + " has ended";
            end();
        }
    }

    @Override
    public boolean emulateIOProcessing() throws InterruptedException {
        int randResult = rand.nextInt(ioLockChance);
        if(randResult + 1 == ioLockChance){
            int lockTime = rand.nextInt(ioLockRandTime);
            timeCounter += lockTime;
//            TimeUnit.SECONDS.sleep(lockTime);
            return true;
        } else{
            timeCounter++;
            return false;
        }
    }

    public int getTimeCounter(){
        return timeCounter;
    }

}
