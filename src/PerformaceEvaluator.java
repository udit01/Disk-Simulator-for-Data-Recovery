public interface PerformaceEvaluator {

    // Recovery ratio per delete file
    double rr = 1.0;

    // Average Disk IO time
    int disk_io_time = 0;

    // Number of deleted files
    int num_deleted_files = 0;

    // Current number of files in disk
    int num_current_files = 0;

    // Performance function
    double performance = 1.0;

    // Update performance of memory
    void updatePerformance();

}
