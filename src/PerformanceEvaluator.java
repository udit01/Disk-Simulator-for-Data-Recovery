public class PerformanceEvaluator {

    // Recovery ratio of files
    double rr = 1.0;

    // SLM of files
    double slm = 0.0;

    // Number of deleted files
    int num_deleted_files = 0;

    // Current number of files in disk
    int num_current_files = 0;

    // Performance function
    double performance = 1.0;

    // Return performance of memory
    double memoryPerformance(Memory memory){

        num_current_files = 0;
        num_deleted_files = 0;
        slm = 0;
        rr = 0;

        for(File f : memory.fileList){
            if(f.fileState == File.STATE.USED){
                slm += f.getSLM();
                num_current_files++;
            }
            else if(f.fileState == File.STATE.DELETED){
                rr += f.getRecoveryRatio();
                num_deleted_files++;
            }
        }

        performance = (rr/num_deleted_files) - (slm/num_current_files);

        return performance;
    }

}
