package Controller;

import Model.MemoryPage;
import Model.MemoryTable;
import Model.MemoryTableCell;
import Model.Process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by andreyzaytsev on 13.03.17.
 */
public class MemoryManager {

    private MemoryHandler memory;

    private MemoryHandler swap;

    private List<Process> processes = Collections.emptyList();

    private List<MemoryTable> tables = Collections.emptyList();

    private static String tableInfo = "Virtual|Presence|Physical|Time" + System.lineSeparator() +
            "---------------------------------------------------------------";



    private static String exceptionMessage = "Out of memory";

    public MemoryManager(MemoryPage[] memory, MemoryPage[] swap, List<Process> processes) throws Exception {
        this.memory = new MemoryHandler(memory);
        this.swap = new MemoryHandler(swap);
        this.processes = processes;
        this.tables = initTables();
        initialAllocation();
    }

    private List<MemoryTable> initTables(){
        List<MemoryTable> tables = new ArrayList<>();
        for(Process process : processes){
            tables.add(new MemoryTable(process.getId()));
        }
        return tables;
    }

    private void initialAllocation() throws Exception {
        for(Process process : processes){
            for(MemoryTable table: tables){
                if(process.getId() == table.getProcessId()){
                    tryAllocate(process.getVirtualMemory(), table);
                }
            }
        }
    }

    public void handlePageError(MemoryTable table, int listId){
        MemoryPage emptyList;
        MemoryTableCell memoryTableCellToRefer = table.getMemoryTableCells().get(listId);
        if ((emptyList = memory.tryGetEmptyPage()) != null) {
            memoryTableCellToRefer.setMemoryPageLink(emptyList);
        } else {
            MemoryTableCell leastUsedMemory = findOptimalList();
            leastUsedMemory.setPresenceFlag(false);
            memoryTableCellToRefer.setPresenceFlag(true);
            MemoryPage physicalMemoryPage = leastUsedMemory.getMemoryPageLink();
            MemoryPage swapMemoryPage = memoryTableCellToRefer.getMemoryPageLink();
            memoryTableCellToRefer.setMemoryPageLink(physicalMemoryPage);
            leastUsedMemory.setMemoryPageLink(swapMemoryPage);
        }
    }

    public void tryRefer(int activeProcessId, int listId){
        for(MemoryTable table : tables){
            if(activeProcessId == table.getProcessId()){
                if(!table.referList(listId)){
                    handlePageError(table, listId);
                    return;
                } else {
                    return;
                }
            }
        }
    }

    public String showTable(int activeProcessId, int referencedListId){
        StringBuilder dataToReturn = new StringBuilder();
        dataToReturn.append(tableInfo + System.lineSeparator());
        for(MemoryTable table : tables){
            if(activeProcessId == table.getProcessId()){
                List<MemoryTableCell> memoryTableCells = table.getMemoryTableCells();
                for(int i = 0; i < memoryTableCells.size(); i++){
                    MemoryTableCell cell = memoryTableCells.get(i);
                    if(i == referencedListId){
                        dataToReturn.append("* " + getCellData(cell));
                    } else {
                        dataToReturn.append(getCellData(cell));
                    }
                }
            }
        }
        return dataToReturn.toString();
    }

    public String getCellData(MemoryTableCell cell){
        return cell.getVirtualListLink().getId() + "\t|\t"
                + cell.getPresenceFlag() + "\t|\t"
                + cell.getMemoryPageLink().getId()
                + "\t|\t" + cell.getWorkTime()
                + System.lineSeparator();
    }

    public MemoryTableCell findOptimalList(){
        boolean isFirstToCheck = false;
        int minWorkTime = 0;
        MemoryTableCell leastUsedMemory = null;
        for(MemoryTable table: tables){
            for(MemoryTableCell tableUnit: table.getMemoryTableCells()){
                if(tableUnit.isPresent() && !isFirstToCheck){
                    minWorkTime = tableUnit.getWorkTime();
                    leastUsedMemory = tableUnit;
                    isFirstToCheck = true;
                }
                if(tableUnit.isPresent() && tableUnit.getWorkTime() <= minWorkTime){
                    minWorkTime = tableUnit.getWorkTime();
                    leastUsedMemory = tableUnit;
                }
            }
        }
        return leastUsedMemory;
    }

    public void tryAllocate(MemoryPage[] virtualMemory, MemoryTable table) throws Exception {
        for(int i = 0; i < virtualMemory.length; i++){
            MemoryPage emptyPage;
            if((emptyPage = memory.tryGetEmptyPage()) != null){
                table.allocateMemory(virtualMemory[i], emptyPage, true);
                continue;
            }
            if((emptyPage = swap.tryGetEmptyPage()) != null){
                table.allocateMemory(virtualMemory[i], emptyPage, false);
                continue;
            }
            throw new Exception(exceptionMessage);
        }
        return;
    }

    public void emptyPages(int processId){
        for(MemoryTable memoryTable: tables){
            if(memoryTable.getProcessId() == processId){
                for(MemoryTableCell cell: memoryTable.getMemoryTableCells()){
                    cell.getMemoryPageLink().setFull(false);
                }
            }
        }

    }



}
