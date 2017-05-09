package Model;

/**
 * Created by andreyzaytsev on 13.03.17.
 */
public class MemoryPage {

    private int id;

    private boolean isFull  = false;

    public MemoryPage(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setFull(boolean status){
        isFull = status;
    }

    public boolean isFull(){
        return isFull;
    }

}
