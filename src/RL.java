public interface RL {

    // RL computational parameters
    double gamma = 0.75; // Look-ahead weight
    double alpha = 0.20; // Forgetfulness weight

    // Exponential decay factor
    double epsilon = 1.0;

    // State parameters
    int lambda = 0; // History Factor
    int sigma = 0;  // Usage Factor
    int rho = 0;    // Spatial Factor
    int mu = 0;     // Linking Factor

    // State parameter bounds
    int lower = 0;
    int upper = 9;

    // State-action space
    int num_states = 10000;
    int num_actions = 9;

    // Q state-action table
    double Q[][] = new double[num_states][num_actions];

    // State index
    int s = 0;
    int sPrime = 0;

    // Performance characteristics
    double performanceNew = 0;
    double performanceOld = 0;
    double deltaPerformance = 0;

    // Main Loop parameters
    double r = 0.0;
    int a = 0;
    double lookAheadValue = 0.0;
    double sample = 0.0;

}
