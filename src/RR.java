import java.util.ArrayList;

/**
 * A more advanced version of FCFS. Execution is still in order of arrival
 * time, but now allows preemptive based on a set time quantum. The trade off
 * is small time quantums introduce more context switches and add overhead.
 * @since  0.4 {@code Apr 25th}
 * @author Joshua Levin
 */
public class RR {
    /**
     * Simulates the prescribed algorithm on the set of tasks.
     * @param queue The set of tasks to be simulated
     * @param quantum The time limit for a task in the CPU
     * */
    public static void EventSim(Schedule queue, int quantum) {
        Schedule tasks = new Schedule(queue);
        ArrayList<Task> ready = new ArrayList<>();
        int simClock = 0;
        boolean fin;                  // whether the tasks has finished or not
        
        ready.add(tasks.poll());      // add first task
        
        while (!ready.isEmpty())         // PREEMPTIVE MAIN LOOP
        {
            Task t = ready.get(0);       // schedule task & update clock
            simClock = Math.max(t.arrival, simClock);

            if (t.resp < 0) t.resp = simClock; // set response time only if not set

            if (t.burst > quantum) {     // apply execution time -> unfinished
                simClock += quantum;            // update clock
                t.burst -= quantum;             // update burst
                fin = false;                    // continue
            }
            else {                       // apply execution time -> finished
                simClock += t.burst;            // update clock
                t.comp = simClock;              // set turnaround time
                t.turn = t.comp - t.arrival;    // set completion time
                t.resp = t.resp - t.arrival;    // set response time
                t.wait = t.turn - t.exe;        // set wait time
                fin = true;                     // mark task finished
            }
            
            ready.remove(t);             // remove task from the CPU
            
            while(tasks.nextBefore(simClock))
                ready.add(tasks.poll()); // fetch all ready tasks
            
            if (!fin) ready.add(t);      // add back of queue if unfinished
        }

        queue.report("ROUND ROBIN"); // print report
    }//*/
}