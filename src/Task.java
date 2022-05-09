import java.util.Random;
/**
 * A simple class that holds task data for simulation and analysis.
 * @since  0.1 {@code Feb 23rd}
 * @author Joshua Levin
 * @see {@link Main
 * @see {@link Schedule}
 */
public class Task {
    private static int count = 0;
    private static Random rand = new Random(); // uniform distribution
    public static double lambdA = 4.2; // average arrival time
    public static double lambdB = 8.6; // average burst time

    // simulation variables & getters for the comparator
    int process;
    int processID() { return process; }
    int arrival;
    int arrivalT() { return arrival; }
    int burst;
    int burstT() { return burst; }
    int exe; // total CPU time needed, used interchangeably as remaining time

    // analysis variables
    int comp;
    int turn;
    int wait;
    int resp = -1;
    
    /**
     * The default constructor makes use of {@code inverse transform sampling}
     * to randomly generate possion-distributed arrival and burst times.
     * <p> The processID increments with the static {@code count} variable.
     */
    Task() {
        process = ++count;
        arrival = (int)Math.round(-Math.log(1.0 - rand.nextDouble()) * lambdA);
        burst   = (int)Math.round(-Math.log(1.0 - rand.nextDouble()) * lambdB);
        exe = burst;
    }

    /**
     * The parameterized constructor is used solely for the midterm test case.
     * @param id The processID
     * @param at The arrival time
     * @param bt The burst time
     */
    Task(int id, int at, int bt) {
        process = id;
        arrival = at;
        exe = burst = bt;
    }
}
