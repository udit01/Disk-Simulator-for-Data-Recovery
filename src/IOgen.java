public interface IOgen {

    // Operation type
    int op = 0; // 0 - No op, 1 - create, 2 - read/write, 3 - delete

    // File size for create op
    int numBlocks = 0;

    // Random file index for ops 2 and 3
    int fileIndex = 0;

    // Operation iteration counter
    int OIC = 0;

    // Generate IO op
    int op();

}
