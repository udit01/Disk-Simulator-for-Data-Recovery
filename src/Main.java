public class Main {

    static int OIN_LIMIT = 10;

    // Model and Operation Iteration numbers
    static int MIN = 0 , OIN = 0;

    public static void main(String[] args) {
        System.out.println("Hello World!");

        // Initializing 256 MB memory
        Memory memory = new Memory(256, 256);

        // Initializing performance evaluator
        PerformanceEvaluator pe = new PerformanceEvaluator();

        // Initializing IO Generator
        IOgen io = new IOgen();

        // Initializing RL Module
         RL rl = new RL(4, 9);

        while(rl.epsilon > 0.0003){
            IOgen.ACTION action = io.op(memory);
            OIN++;
            System.out.println("OIN: " + OIN);
            System.out.println("Memory Util : " + memory.mem_util + " %");

            double p = pe.memoryPerformance(memory);

            System.out.println("Num Current Files : " + pe.num_current_files);
            System.out.println("Num Deleted Files : " + pe.num_deleted_files);
            System.out.println("Num Obsolete Files : " + (memory.fileList.size()-pe.num_deleted_files-pe.num_current_files) );
            System.out.println("Performance: " + p);

            if(io.isStable(memory) && (OIN % OIN_LIMIT == 0)){
                rl.run(p);
                memory.lambda = rl.state[0];
                memory.sigma = rl.state[1];
                memory.rho = rl.state[2];
                memory.mu = rl.state[3];
                MIN++;
                System.out.println("MIN: " + MIN);
            }

            System.out.println();
        }
    }
}
