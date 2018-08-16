import java.io.PrintWriter;

public class Main {

    static int OIN_LIMIT = 100;

    // Model and Operation Iteration numbers
    static int MIN = 0 , OIN = 0;

    // Cumulative performance
    static double cumulativePerformance = 0.0;

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");

        // Initializing 256 MB memory
        Memory memory = new Memory(16, 16);

        // Initializing performance evaluator
        PerformanceEvaluator pe = new PerformanceEvaluator();

        // Initializing IO Generator
        IOgen io = new IOgen();

        // Initializing RL Module
        RL rl = new RL(4, 9);

        PrintWriter rlWriter = new PrintWriter("Model-iterations.txt", "UTF-8");
        PrintWriter ioWriter = new PrintWriter("IO-iterations.txt", "UTF-8");

        while(rl.epsilon > 0.0003){
            IOgen.ACTION action = io.op(memory);

//            switch (action){
//                case NO_OP: ioWriter.println("Action : No Operation"); break;
//                case CREATE: ioWriter.println("Action : Create file, Num Blocks : " + io.numBlocks + ", LF : " + io.lf); break;
//                case READ_WRITE: ioWriter.println("Action : Read Write File"); break;
//                case DELETE: ioWriter.println("Action : Delete File");
//            }


            OIN++;
//            ioWriter.println("OIN: " + OIN);
//            ioWriter.println("Memory Util : " + memory.mem_util + " %");

            double p = pe.memoryPerformance(memory);

            if(!Double.isNaN(p)){
                cumulativePerformance += p;
            }

//            ioWriter.println("Num Current Files : " + pe.num_current_files);
//            ioWriter.println("Num Deleted Files : " + pe.num_deleted_files);
//            ioWriter.println("Num Obsolete Files : " + (memory.totalCreatedFiles-pe.num_deleted_files-pe.num_current_files) );
//            ioWriter.println("Performance: " + p);

            if(io.isStable(memory) && (OIN % OIN_LIMIT == 0)){
                rl.run(cumulativePerformance/OIN_LIMIT);

//                memory = new Memory(16, 16);
//                io = new IOgen();

                memory.lambda = rl.state[0];
                memory.sigma = rl.state[1];
                memory.rho = rl.state[2];
                memory.mu = rl.state[3];
                MIN++;
                rlWriter.println("MIN: " + MIN);
                rlWriter.println("Epsilon : " + rl.epsilon);
                rlWriter.println("Action : " + rl.a);
                rlWriter.print("State : ");
                for (int i = 0; i < 4; i++) {
                    rlWriter.print(rl.state[i] + ", ");
                }
                rlWriter.println();
                rlWriter.println("Cumulative performance : " + cumulativePerformance/OIN_LIMIT);
                rlWriter.println("\n");

                if(MIN % 10000 == 0 && MIN > 0){
                    System.out.println(rl.epsilon);
                    System.out.println("Iterations completed : " + MIN );
                    System.out.println("Last Action : " + rl.a);
                    System.out.println("Performance : " + (cumulativePerformance/OIN_LIMIT) + "\n");
                }

                cumulativePerformance = 0;
            }


        }

        rlWriter.close();
        ioWriter.close();
    }
}
