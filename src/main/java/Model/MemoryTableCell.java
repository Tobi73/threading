package Model;

/**
 * Created by andreyzaytsev on 22.03.17.
 */
public class MemoryTableCell {

    private MemoryPage virtualListLink;

    private MemoryPage memoryPageLink;

    private boolean presenceFlag;

    private int workTime;

    public MemoryTableCell(MemoryPage virtualLink, MemoryPage physicalLink, boolean presenceFlag){
        this.virtualListLink = virtualLink;
        this.memoryPageLink = physicalLink;
        this.presenceFlag = presenceFlag;
        this.workTime = 0;
    }

    public MemoryPage getVirtualListLink(){
        return virtualListLink;
    }

    public MemoryPage getMemoryPageLink(){
        return memoryPageLink;
    }

    public void setMemoryPageLink(MemoryPage list){
        this.memoryPageLink = list;
    }

    public boolean isPresent(){
        return presenceFlag;
    }

    public void setPresenceFlag(boolean presence){
        presenceFlag = presence;
    }

    public void incWorkTime(){
        workTime++;
    }

    public void resetWorkTime(){
        workTime = 0;
    }

    public int getWorkTime(){
        return workTime;
    }

    public boolean getPresenceFlag(){
        return this.presenceFlag;
    }


}
