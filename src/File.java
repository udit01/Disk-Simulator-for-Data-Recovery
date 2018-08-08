import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class File {

    // Block list
    HashSet<Block> blockList ;//= new ArrayList<>();

    enum STATE{ USED, DELETED, OBSOLETE;}
    STATE fileState;

    int linking_factor;


    File(HashSet<Block> block_list, int lf){
        this.blockList = block_list;
        this.fileState = STATE.USED;
        this.linking_factor = lf;
    }

    void deleteFile(){
        this.fileState = STATE.DELETED;
        //deallocate file
        for (Block b : this.blockList){
            //deallocate not delete because, deleteBlock if final delete
            b.deallocate();
        }
    }

    //delete this block from me becuase, allocating to someone else
    void deleteBlock(Block b){
        assert this.blockList.contains(b);

        this.blockList.remove(b);

        if (this.blockList.size() == 0){
            this.fileState = STATE.OBSOLETE;
        }
    }


}
