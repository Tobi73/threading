package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreyzaytsev on 22.03.17.
 */
public class MemoryTable {


    private List<MemoryTableCell> memoryTableCells;

    private int processId;

    public MemoryTable(int processId){
        this.memoryTableCells = new ArrayList<>();
        this.processId = processId;
    }

    public List<MemoryTableCell> getMemoryTableCells(){
        return this.memoryTableCells;
    }


    public void allocateMemory(MemoryPage virtualMemory, MemoryPage emptyPage, boolean presence){
        memoryTableCells.add(new MemoryTableCell(virtualMemory, emptyPage, presence));
        emptyPage.setFull(true);
    }

    public boolean referList(int listId){
        MemoryTableCell referencedTableRow = memoryTableCells.get(listId);
        if(referencedTableRow.isPresent()){
            referencedTableRow.incWorkTime();
            return true;
        } else {
            return false;
        }
    }

    public int getProcessId(){
        return processId;
    }




}
