import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class File {

    // Block list
    HashSet<Block> blockList ;//= new ArrayList<>();

    enum STATE{ USED, DELETED, OBSOLETE;}
    STATE fileState;

    File(HashSet<Block> block_list){
        this.blockList = block_list;
        this.fileState = STATE.USED;
    }

    void deleteFile(){
        this.fileState = STATE.DELETED;
        //deallocate file
    }

    void deleteBlock(Block b){

        assert this.blockList.contains(b);

        this.blockList.remove(b);

        if (this.blockList.size() == 0){
            this.fileState = STATE.OBSOLETE;
        }
    }


}
