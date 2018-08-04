import java.util.ArrayList;
import java.util.List;

public interface Block {

    // Factors
    int hf = 0; // History Factor
    int uf = 1; // Usage Factor
    int sf = 0; // Spatial Factor
    int lf = 1; // Linking Factor

    // Used boolean
    boolean used = false;

    // Priority Score
    int pf = 0;

    // Update Priority Score
    void updatepf();

    // List of blocks that belong to the same file
    List<Block> fileBlocks = new ArrayList<>();

    // File number of this block (0 when unused)
    int filenumber = 0;

    // IO time
    int IO_time = 0;

}
