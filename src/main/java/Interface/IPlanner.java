package Interface;

/**
 * Created by andreyzaytsev on 26.02.17.
 */
public interface IPlanner {

    boolean isReadyForChange();

    void makeWork() throws InterruptedException;

    boolean isOver();

    boolean isEmpty();

    void change();

    void end();

}
