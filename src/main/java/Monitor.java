import java.util.Random;

/**
 * Created by andreyzaytsev on 21.02.17.
 */
public class Monitor {

    public static void main(String[] args) throws InterruptedException {
        Thread testThread = new Thread(10);
        Random rnd = new Random();
        for(int i = 0; i < 1; i++){
            testThread.addNewProcess(new Process(i, rnd.nextInt(10) + 20));
        }
        while (testThread.threadIsNotOver()){
            testThread.work();
            System.out.println(testThread.getStateInfo());
        }
    }

}
