import java.util.Iterator;

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

        for(File f : memory.currentFileList){
            if(f.fileState == File.STATE.USED){
                slm += f.getSlm();
            }
            else{
                throw new java.lang.RuntimeException("Performance Evaluator : currentFileList has files that are not USED");
            }
        }

        Iterator<File> iter = memory.deletedFileList.iterator();
        while (iter.hasNext()) {
            File p = iter.next();
            if (p.fileState == File.STATE.OBSOLETE) iter.remove();
        }

        for(File f : memory.deletedFileList){
            if(f.fileState == File.STATE.DELETED){
                rr += f.getRecoveryRatio() * (f.uf + 1);
            }
            else{
                throw new java.lang.RuntimeException("Performance Evaluator : deletedfFileList has files that are not DELETED");
            }
        }

        num_current_files = memory.currentFileList.size();
        num_deleted_files = memory.deletedFileList.size();

        performance = (100.0 * rr/num_deleted_files) - (slm/num_current_files);

        return performance;
    }

}
