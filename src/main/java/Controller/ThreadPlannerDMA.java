package Controller;

import Model.Thread;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by gman0_000 on 09.05.2017.
 */
public class ThreadPlannerDMA extends ThreadPlannerIOLock {

    private static Map<Thread, Integer> DMAMap;

    public static Map<Thread, Integer> getDMAInstance(){
        if(DMAMap == null){
            DMAMap = new HashMap<>();
            return DMAMap;
        } else return DMAMap;
    }

    public ThreadPlannerDMA(int ioLockChance, int ioLockRandTime) {
        super(ioLockChance, ioLockRandTime);
        DMAMap = getDMAInstance();
    }

    @Override
    public boolean isReadyForChange() {
        if(activeThreadWorkTime == threadTimeQuantum || DMAMap.containsKey(activeThread)){
            return true;
        }
        return false;
    }

    @Override
    public void makeWork() throws InterruptedException {
        if(activeThread == null){
            activeThread = threads.poll();
        }
        if(isReadyForChange()){
            infoState = "/Thread " + activeThread.getId() + " was changed to ";
            change();
            DMAWork();
            timeCounter++;
            infoState+= "Thread " + activeThread.getId();
            return;
        }
        if(!isOver()){
            activeThreadWorkTime++;
            infoState = "/Thread " + activeThread.getId() + " is working (" + activeThread.getTimeToWork() + "). ";
            infoState+=threads.size() + " threads are waiting. Working time - (" + activeThreadWorkTime + "/" + threadTimeQuantum + ")";
            activeThread.work();
            DMAWork();
            if(emulateIOProcessing()){
                infoState+= System.lineSeparator() + "IO Operation was initiated during thread work...";
            }
        } else {
            infoState = "/Thread " + activeThread.getId() + " has ended";
            end();
        }
    }

    @Override
    public boolean emulateIOProcessing(){
        int randResult = rand.nextInt(ioLockChance);
        if(randResult + 1 == ioLockChance){
            int lockTime = rand.nextInt(ioLockRandTime);
            DMAMap.put(activeThread, lockTime);
            timeCounter++;
            return true;
        } else{
            timeCounter++;
            return false;
        }
    }

    public void DMAWork(){
        Iterator<Thread> threads = DMAMap.keySet().iterator();
        Map<Thread, Integer> tempMap = new HashMap<>();
        while(threads.hasNext()){
            Thread thread = threads.next();
            int previousValue = DMAMap.get(thread);
            threads.remove();
            if(previousValue - 1 > 0){
                tempMap.put(thread, previousValue - 1);
            }
        }
        DMAMap.putAll(tempMap);
    }

    public boolean areAllThreadsBusy(LinkedBlockingQueue<Thread> threads){
        for(Thread thread : threads){
            if(!DMAMap.containsKey(thread)){
                return false;
            }
        }
        return true;
    }
}
