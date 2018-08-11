import java.util.*;

public class IOgen {

    static int MAX_UTIL = 95;
    static int MIN_UTIL = 80;
    static int FILE_SIZE_LIMIT = 50;

    // List of file indices
    ArrayList<Integer> fileIndices;

    // Random op integer
    int op; // 0 - no op, 1 - create, 2 - read/write, 3 - delete
    int rand;

    // File size for create op, and linking factor
    int numBlocks = 0;
    int lf = 0;

    // Random file index for ops 2 and 3
    int fileIndex = 0;

    // Operation iteration counter
    int OIC = 0;

    // Action
    enum ACTION{
        NO_OP,  // No operation
        CREATE,  // Create file
        DELETE, // Delete file
        READ_WRITE // Read or Write file
    }
    ACTION fileAction;

    // New IO Generator
    IOgen(){
        fileIndices = new ArrayList<>();
        stable = false;
        rand = new Random<>();
        op = 0;
    }

    // Validation action
    boolean validAction(int a, double util){
        switch (a){
            case 1:
                if(util > MAX_UTIL) {
                    return false;
                }
            case 2:
                if(this.fileIndices.isEmpty()){
                    return false;
                }
            case 3:
                if(this.fileIndices.isEmpty()) {
                    return false;
                }
        }

        return true;
    }

    // Memory now stable
    boolean isStable(Memory memory){
        if(memory.mem_util >= 80){
            return true;
        }
        return false;
    }

    // Generate IO op
    ACTION op(Memory memory){
        assert (memory.mem_util <= 100 && memory.mem_util >= 0);

        OIC++;

        int range = (mem_util < 80.0) ? 3: 4;
        int op = rand.nextInt(range);

        while(!validAction(op, mem_util)){
            op = rand.nextInt(range);
        }

        ACTION a;

        switch (op){
            case 0 :
                a = ACTION.NO_OP;
            case 1 :
                a = ACTION.CREATE;
                numBlocks = rand.nextInt(FILE_SIZE_LIMIT);
                lf = (rand.nextInt(5) > 0) ? 0: 1;
                this.fileIndices.add(memory.createFile(lf, numBlocks));
            case 2 :
                a = ACTION.READ_WRITE;
                fileIndex = rand.nextInt(this.fileIndices.size());
                memory.readWriteFile(fileIndex);
            case 3 :
                a = ACTION.DELETE;
                fileIndex = rand.nextInt(this.fileIndices.size());
                memory.deleteFile(fileIndex);
                this.fileIndices.remove(fileIndex);
        }

        return ACTION;
    }

}
