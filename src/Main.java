public class Main {

    static int OIN_LIMIT = 10;

    // Model and Operation Iteration numbers
    static int MIN, OIN;

    public static void main(String[] args) {
        System.out.println("Hello World!");

        // Initializing 256 MB memory
        Memory memory = new Memory(256, 256);

        // Initializing performance evaluator
        PerformanceEvaluator pe = new PerformanceEvaluator();

        // Initializing IO Generator
        IOgen io = new IOgen();

        // Initializing RL Module
        // RL rl = new RL();

        while(true){
            IOgen.ACTION action = io.op(memory);
            OIN++;

            System.out.println("OIN: " + OIN);

            if(io.isStable(memory) && OIN%OIN_LIMIT == 0){
                double p = pe.memoryPerformance(memory);
                MIN++;
                System.out.println("MIN: " + MIN);
                System.out.println("Performance: " + p);


            }
        }
    }
}
