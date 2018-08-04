public interface Memory {

    // Memory size parameters
    int width = 256;
    int height = 256;

    // Block list
    Block memList[][] = new Block[height][width];

    // Initialize all blocks
    void init();

    // Update factors of all blocks per transaction
    void update();

    // Give block with maximum priority score
    Block giveMaxPriority();

    // Memory Usage percentage
    int memUsage();

}
