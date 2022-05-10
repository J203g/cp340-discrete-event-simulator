/**
 * Very simple event simulator. Non-preemptive. Executes the job with least
 * runtime first. This simulatation experiences starvation, where long tasks
 * wait indefinitely.
 * @since  0.6 {@code Apr 27th}
 * @author Joshua Levin
 */
public class SJF {
    /**
     * Simulates the prescribed algorithm on the set of tasks.
     * @param tasks The set of tasks to be simulated
     */
    public static void EventSim(Schedule queue) {
        Schedule tasks = new Schedule(queue);
        Schedule ready = new Schedule(0, Task::burstT);
        int simClock = 0;
        
        ready.add(tasks.poll());          // add first task
        
        while (!ready.isEmpty())          // PREEMPTIVE MAIN LOOP
        {
            Task t = ready.poll();        // schedule task & update clock
            simClock = Math.max(t.arrival, simClock);
            
            t.resp = simClock - t.arrival;      // set response time
            simClock += t.exe;                  // apply execution time

            t.comp = simClock;                  // set turnaround time
            t.turn = t.comp - t.arrival;        // set completion time
            t.wait = t.turn - t.burst;          // set wait time

            while (tasks.nextBefore(simClock))  // add all ready tasks
                ready.add(tasks.poll());
        }

        queue.report("SHORTEST JOB FIRST"); // print report
    }
}
