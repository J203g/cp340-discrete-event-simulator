/**
 * The simplest scheduling algorithm. Non-preemptive, execution in order of
 * arrival time. This scheduling method experiences the <b>Convoy Effect</b>,
 * where short tasks have to wait for really long tasks to finish.
 * @since  0.2 {@code Feb 25th}
 * @author Joshua Levin
 */
public class FCFS {
    /**
     * Simulates the prescribed algorithm on the set of tasks
     * @param queue The set of tasks to be simulated
     */
    public static void EventSim(Schedule queue) {
        int simClock = 0;

        for (Task t : queue)                      // NON-PREEMPTIVE MAIN LOOP
        {
            simClock = Math.max(t.arrival, simClock);  // update clock

            t.resp = simClock - t.arrival;             // set response time
            simClock += t.burst;                       // apply execution time
            
            t.comp = simClock;                         // set completion time
            t.turn = t.comp - t.arrival;               // set turnaround time
            t.wait = t.turn - t.burst;                 // set wait time
        }

        queue.report("FIRST COME FIRST SERVE");
    }
}