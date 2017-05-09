package Controller;

import Model.MemoryPage;

/**
 * Created by andreyzaytsev on 24.03.17.
 */
public class MemoryHandler {

    private MemoryPage[] memoryPages;

    public MemoryHandler(MemoryPage[] memoryPages){
        this.memoryPages = memoryPages;
    }

    public MemoryPage tryGetEmptyPage(){
        for(int i = 0; i < memoryPages.length; i++){
            if(!memoryPages[i].isFull()){
                return memoryPages[i];
            }
        }
        return null;
    }

    public void emptyPage(int pageId){
        memoryPages[pageId].setFull(false);
    }


}
