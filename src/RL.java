import java.util.Arrays;
import java.util.Random;

import static java.lang.Double.NEGATIVE_INFINITY;

public class RL {

    // RL computational parameters
    private static double gamma = 0.75; // Look-ahead weight
    private static double alpha = 0.20; // Forgetfulness weight

    // RL convergence parameters
    private static int readDelay = 10;
    private static double explorationMinutes = 1;
    private double explorationConst = (explorationMinutes*60.0)/((readDelay)/1000.0);

    // Exponential decay factor
    public double epsilon = 1.0;

    // State parameters
    public int[] state;

    // State-action space
    private int num_params;
    private int range;

    private int num_states;
    private int num_actions;

    // Q state-action table
    private double Q[][];

    // State index
    private int s;
    private int sPrime;

    // Performance characteristics
    double performanceNew = 0;
    double performanceOld = 0;
    double deltaPerformance = 0;

    // Main Loop parameters
    double r = 0.0;
    int a = 0;
    double lookAheadValue = 0.0;
    double sample = 0.0;

    // Random integer generator
    private Random rand;

    // Model Iteraction Counter
    private int MIC = 0;

    RL(int num_params_inp, int range_inp){
        this.num_params = num_params_inp;
        this.range = range_inp;

        this.num_states = (int) Math.pow(range, num_params);
        this.num_actions = 1 + (2 * num_params);

        System.out.println("Number of states : " + this.num_states);
        System.out.println("Number of actions : " + this.num_actions);

        this.state = new int[num_params];
        this.Q = new double[num_states][num_actions];

        this.epsilon = 1.0;

        this.s = 0;
        this.sPrime = 0;

        this.rand = new Random();
    }

    // Initialize Q
    void initializeQ() {
        for (int i = 0; i < this.num_states; i++) {
            for (int j = 0; j < this.num_actions; j++) {
                this.Q[i][j] = 10.0;               //Initialize to a positive number to represent optimism over all state-actions
            }
        }
    }

    // Get action
    int getAction(){
        double valMax = NEGATIVE_INFINITY;
        double val;
        int aMax = 0;
        double randVal;
        int[] allowedActions = new int[this.num_actions];
        Arrays.fill(allowedActions, -1);
        allowedActions[0] = 1;

        boolean randomActionFound = false;
        val = Q[s][0];

        // Find the optimal action, and exclude that take you outside the allowed state space
        if (val > valMax) {
            valMax = val;
            aMax = 0;
        }

        for (int i = 0; i < num_params; i++) {
            if (state[i] + 1 < range) {
                allowedActions[2 * i + 1] = 1;
                val = Q[s][2 * i + 1];
                if (val > valMax) {
                    valMax = val;
                    aMax = 2 * i + 1;
                }
            }
            if (state[i] > 0) {
                allowedActions[2 * i + 2] = 1;
                val = Q[s][2 * i + 2];
                if (val > valMax) {
                    valMax = val;
                    aMax = 2 * i + 2;
                }
            }
        }

        // Implement epsilon greedy algorithm
        if((double) this.rand.nextInt(101) < (1.0 - epsilon)*100.0){
            this.a = aMax;
        }
        else {
            while (!randomActionFound) {
                this.a = this.rand.nextInt(this.num_actions);
                if (allowedActions[this.a] == 1) {
                    randomActionFound = true;
                }
            }
        }

        return(this.a);
    }

    //Given a and the global(s) find the next state.  Also keep track of the individual joint indexes s1 and s2.
    private void setSPrime(int action) {
        if (action == 0) {
            // NONE
            sPrime = s;
        }
        else {
            int power = (action - 1) / 2;
            int absolute = (int) Math.pow(range, power);
            if (action % 2 == 1) {
                sPrime = s + absolute;
                state[power] = state[power] + 1;
            }
            else {
                sPrime = s - absolute;
                state[power] = state[power] - 1;
            }
        }
    }

    // Analyze performance
    double getDeltaPerformance(double pf) {
        this.performanceNew = pf;
        this.deltaPerformance = this.performanceNew - this.performanceOld;
        this.performanceOld = this.performanceNew;
        return this.deltaPerformance;
    }

    //Get max over a' of Q(s',a'), but be careful not to look at actions which take the agent outside of the allowed state space
    double getLookAhead(){
        double valMax = NEGATIVE_INFINITY;
        double val;

        val = Q[sPrime][0];
        if (val > valMax) {
            valMax = val;
        }

        for (int i = 0; i < num_params; i++) {

            if (state[i] + 1 < range) {
                val = Q[sPrime][2 * i + 1];
                if (val > valMax) {
                    valMax = val;
                }
            }

            if (state[i] > 0) {
                val = Q[sPrime][2 * i + 2];
                if (val > valMax) {
                    valMax = val;
                }
            }
        }

        return valMax;
    }

    //print Q
    void printQ() {
        System.out.println("Q is: ");
        for (int i = 0; i < num_states; i++) {
            System.out.print(i + "\t\t\t");
            for (int j = 0; j < num_actions; j++) {
                System.out.print(this.Q[i][j] + "\t \t \t");
            }
            System.out.println();
        }
    }

    // Main loop
    void run(double performance){
        this.MIC++;

        System.out.println("Iteration Number : " + this.MIC);
        System.out.println("Performance : " + performance);
        this.epsilon = Math.exp(-(double) this.MIC / this.explorationConst);
        this.getAction();

        this.setSPrime(this.a);

        System.out.println("Action : " + this.a);

        this.r = this.getDeltaPerformance(performance);
        this.lookAheadValue = this.getLookAhead();

        this.sample = r + this.gamma * lookAheadValue;
        this.Q[s][a] = this.Q[s][a] + this.alpha * (this.sample - this.Q[s][a]);
        this.s = this.sPrime;

        System.out.print("State : ");
        for (int i = 0; i < num_params; i++) {
            System.out.print(this.state[i] + ", ");
        }
        System.out.println("\n");

        if (this.MIC == 2) {
            initializeQ();
        }

        // this.printQ();

    }

//    public static void main(String[] args){
//        RL test = new RL(2, 4);
//        int input;
//        System.out.println("Running Simulation");
//        System.out.println(test.epsilon);
//        while (test.epsilon > 0.0003 && test.epsilon <= 1.0) {
//            System.out.println("Running Simulation");
//            input = 100 - (int) (Math.pow((test.state[0] - 1), 2) + Math.pow((test.state[1] - 3), 2));
//            test.run(input);
//        }
//
//    }

}
