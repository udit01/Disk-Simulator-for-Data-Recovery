import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Memory {

    // Memory size parameters
    int width ;
    int height ;

    //blocks
    Block[][] blocks;

    // Block heap
    PriorityQueue<Block> unsedBlocks;
    PriorityQueue<Block> usedBlocks;

    //List of all files
    ArrayList<File> fileList;


    Memory(int w, int h){
        this.width = w;
        this.height = h;
        this.blocks = new Block[w][h];

        for (int i = 0; i < w; i++){
            for(int j = 0; j < h; j++){
                Block b_ = this.blocks[i][j];
                b_.i = i; b_.j = j;
            }
        }
        this.fileList = new ArrayList<>();
        //put all blocks in unused heap now
        this.usedBlocks = new PriorityQueue<>(w*h);
        this.unsedBlocks = new PriorityQueue<>(w*h);
//        for ()
    }

    int createFile(int link_factor, int num_blocs){

        HashSet<Block> block_list = new HashSet<>(num_blocs);

        for(int i = 0; i < num_blocs; i++){
            Block b = this.unsedBlocks.poll();

            if(b == null){
                throw new java.lang.RuntimeException("Memory Full!");
            }

            block_list.add(b);
            this.usedBlocks.add(b);
        }

        //if enough blocks
        File f = new File(block_list, link_factor);

        this.fileList.add(f);

        for(Block bl : f.blockList){

            File old_parent = bl.parentFile;

            if (old_parent != null){
                old_parent.deleteBlock(bl);
            }

            bl.allocate(f, link_factor);
        }

        //the index of this file
        return (fileList.size() - 1);
    }

    void deleteFile(int fileIndex){
        File cf = this.fileList.get(fileIndex);
        cf.deleteFile();
    }

    // Update factors of all blocks per transaction
    void update(){

    }

    // Give block with maximum priority score
//    Block giveMaxPriority(){
//
//    }

    // Memory Usage percentage
//    int memUsage(){
//
//    }

}
