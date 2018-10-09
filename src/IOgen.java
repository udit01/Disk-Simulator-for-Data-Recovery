import java.util.*;

public class IOgen {

    public

    static int MAX_UTIL = 85;
    static int MIN_UTIL = 80;
    static int FILE_SIZE_LIMIT = 20;
    static int PERCENTAGE_LINKED_FILES = 20;

    // Random op integer
    int op; // 0 - no op, 1 - create, 2 - read/write, 3 - delete
    Random rand;

    // File size for create op, and linking factor
    int numBlocks = 0;
    int lf = 0;

    // Random file index for ops 2 and 3
    int fileIndex = 0;

    // Operation iteration counter
    int OIC = 0;

    int counter;

    // Action
    static enum ACTION{
        NO_OP,  // No operation
        CREATE,  // Create file
        DELETE, // Delete file
        READ_WRITE // Read or Write file
    }
    ACTION fileAction;

    // New IO Generator
    IOgen(){
        rand = new Random();
        op = 0;
        counter = 0;
    }

    // Validation action
    boolean validAction(int a, Memory memory){
        switch (a){
            case 1:
                if(memory.mem_util > MAX_UTIL) {
                    return false;
                }
                break;
            case 2:
                if(memory.currentFileList.isEmpty()){
                    return false;
                }
                break;
            case 3:
                if(memory.currentFileList.isEmpty()) {
                    return false;
                }
                break;
        }

        return true;
    }

    // Memory now stable
    boolean isStable(Memory memory){
        if(memory.mem_util >= MIN_UTIL){
            return true;
        }
        return false;
    }

    // Generate IO op
    ACTION op(Memory memory){
        assert (memory.mem_util <= 100 && memory.mem_util >= 0);

        OIC++;

        int range = (memory.mem_util < MIN_UTIL) ? 3: 4;
        op = (memory.currentFileList.isEmpty()) ? 1: rand.nextInt(range);

//        if(memory.mem_util < 90){
//            op = 1;
//        }
//        else{
//            if(op == 1){
//                op = 3;
//            }
//            else{
//                op = 1;
//            }
//        }

        while(!validAction(op, memory)){
            op = rand.nextInt(range);
        }

        //System.out.println("Num IOGen files : " + this.fileIndices.size());
        ACTION a = ACTION.NO_OP;

        switch (op){
            case 0 :
                a = ACTION.NO_OP;
                //System.out.println("Action : No Operation");
                break;
            case 1 :
                a = ACTION.CREATE;
                numBlocks = rand.nextInt(FILE_SIZE_LIMIT) + 10; //10;
                lf = (rand.nextInt(100/PERCENTAGE_LINKED_FILES) > 0) ? 0: 1; //(counter == 0) ? 1 : 0;
                //counter = (counter +1)%5;
                memory.createFile(lf, numBlocks);
                //System.out.println("Action : Create file, Num Blocks : " + numBlocks + ", LF : " + lf);
                break;
            case 2 :
                a = ACTION.READ_WRITE;
                fileIndex = rand.nextInt(memory.currentFileList.size());
                memory.readWriteFile(fileIndex);
                //System.out.println("Action : Read Write File");
                break;
            case 3 :
                a = ACTION.DELETE;
                fileIndex = rand.nextInt(memory.currentFileList.size()); //0;
                memory.deleteFile(fileIndex);
                //System.out.println("Action : Delete File");
                break;
        }

        return a;
    }

}
