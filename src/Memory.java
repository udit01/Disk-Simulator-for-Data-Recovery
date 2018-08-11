import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.*;

public class Memory {

    // Memory size parameters

    static int MAX_PARAM = 9;
    static int MIN_PARAM = 0;

    int width ;
    int height ;

    double mem_util;

    int lambda, sigma, rho, mu;

    //blocks
    Block[][] blocks;

    // Block heap
    PriorityQueue<Block> unsedBlocks;

    HashSet<Block> usedBlocks;
//    PriorityQueue<Block> usedBlocks;

    //List of all files
    ArrayList<File> fileList;


    Memory(int w, int h){
        this.width = w;
        this.height = h;
        this.blocks = new Block[w][h];

        this.mem_util = 0.0;

        this.lambda = 0;
        this.sigma = 1;
        this.rho = 0;
        this.mu = 1;

        for (int i = 0; i < w; i++){
            for(int j = 0; j < h; j++){
                this.blocks[i][j].i = i;
                this.blocks[i][j].j = j;
            }
        }

        this.fileList = new ArrayList<>();
        //put all blocks in unused heap now
        this.usedBlocks = new HashSet<>(w*h);

        this.unsedBlocks = new PriorityQueue<>(w*h, new BlockComparator());
//        for ()
        for (int i = 0; i < w; i++){
            for(int j = 0; j < h; j++){
                this.unsedBlocks.add(this.blocks[i][j]);
            }
        }
    }

    int createFile(int link_factor, int num_blocks){

        HashSet<Block> block_list = new HashSet<>(num_blocks);

        for(int i = 0; i < num_blocks; i++){
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

        //Refresh the memory pf, sf etc;
        this.refresh();
        //the index of this file
        return (fileList.size() - 1);
    }

    void deleteFile(int fileIndex){
        File cf = this.fileList.get(fileIndex);
        assert cf != null;
        cf.deleteFile();
        for(Block b : cf.blockList){
            this.usedBlocks.remove(b);
            this.unsedBlocks.add(b);
        }
        this.refresh();
    }

    // Update factors of all blocks per transaction

    void readWriteFile(int fileIndex){
        this.fileList.get(fileIndex).readWriteFile();
        this.refresh();
    }

    void updateSF(){
        // neightbouring blocks's pf's avg in sf of this block
        ArrayList<Pair<Integer, Integer>> directions = new ArrayList<Pair<Integer, Integer>>(8);

        directions.add(new Pair<>(-1,-1));
        directions.add(new Pair<>(-1, 0));
        directions.add(new Pair<>(-1, 1));

        directions.add(new Pair<>( 0,-1));
//        directions.add(new Pair<>( 0, 0));
        directions.add(new Pair<>( 0, 1));

        directions.add(new Pair<>(1,-1));
        directions.add(new Pair<>(1, 0));
        directions.add(new Pair<>(1, 1));

        int count = 0;
        double sum = 0.0;

        int newi = 0;
        int newj = 0;

        for (int i = 0; i < this.width; i++){
            for(int j = 0; j < this.height; j++){

                sum = 0.0;
                count = 0;

                for (int t = 0; t < directions.size(); t++ ){
                    newi = i + directions.get(t).getKey();
                    newj = i + directions.get(t).getValue();

                    if (newi >= 0 && newi < this.width && newj >= 0 && newj < this.height ){
                        sum += this.blocks[newi][newj].pf;
                        count++;
                    }
                }

                this.blocks[i][j].setSF((int)(sum/count));

            }
        }

    }

    void refresh(){
        this.updateSF();

        PriorityQueue<Block> newHeap = new PriorityQueue<>(this.width*this.height, new BlockComparator());

        for (int i = 0; i < this.width; i++){
            for(int j = 0; j < this.height; j++){
                Block b = this.blocks[i][j];
                b.updatepf(this.lambda, this.sigma, this.rho, this.mu);
                if (b.used == false){
                    newHeap.add(this.blocks[i][j]);
                }
            }
        }

        this.unsedBlocks = newHeap;

        this.mem_util = ((double)(this.usedBlocks.size()))/(this.width*this.height);

    }

    void updateParams(int lambda, int sigma, int rho, int mu){
        assert (lambda <= MAX_PARAM) && (lambda >= MIN_PARAM );
        assert (sigma  <= MAX_PARAM) && (sigma  >= MIN_PARAM );
        assert (rho    <= MAX_PARAM) && (rho    >= MIN_PARAM );
        assert (mu     <= MAX_PARAM) && (mu     >= MIN_PARAM );

        this.lambda = lambda;
        this.sigma = sigma;
        this.rho  = rho;
        this.mu = mu;

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
