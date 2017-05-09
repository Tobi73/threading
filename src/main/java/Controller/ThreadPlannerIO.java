package Controller;

import java.util.Random;

/**
 * Created by gman0_000 on 09.05.2017.
 */
public abstract class ThreadPlannerIO extends ThreadPlanner {

    protected int ioLockChance;
    protected int ioLockRandTime;
    protected int timeCounter;
    protected Random rand;

    public abstract boolean emulateIOProcessing() throws Exception;

}
