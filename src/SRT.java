/**
 * The last of four scheduling algorithms in this project.
 * Preempts by burst time.
 * @since 0.9 {@code May 9th}
 * @author Joshua Levin
 */
public class SRT {
    /**
     * Simulates the prescribed algorithm on the set of tasks.
     * @param tasks The set of tasks to be simulated.
     */
    public static void EventSim(Schedule queue) {
        Schedule tasks = new Schedule(queue);
        Schedule fin = new Schedule(0, Task::processID);
        Schedule ready = new Schedule(0, Task::burstT);

        int simClock = 0;

        ready.add(tasks.poll());         // add first task
        
        while (!ready.isEmpty())         // PREEMPTIVE MAIN LOOP
        {
            Task t = ready.poll();       // schedule task & update clock
            simClock = Math.max(t.arrival, simClock);

            if (t.resp < 0) t.resp = simClock; // set response time

            // add tasks that arrive before current execution finishes
            while (tasks.nextBefore(simClock + t.burst)) {
                if (t.resp < 0) t.resp = simClock; // set response time
                
                Task nt = tasks.poll();
                
                t.burst -= nt.arrival - simClock;           // update burst
                simClock = Math.max(nt.arrival, simClock);  // update clock
                
                if (nt.burst < t.burst) {
                    ready.add(t);               // put back in the queue
                    t = nt;                     // 
                } else ready.add(nt);           // or put back other task
            }

            if (t.resp < 0) t.resp = simClock;  // set response time

            simClock += t.burst;                // apply execution time
            t.comp = simClock;                  // set turnaround time
            t.turn = t.comp - t.arrival;        // set completion time
            t.resp = t.resp - t.arrival;        // set response time
            t.wait = t.turn - t.exe;            // set wait time
            fin.add(t);                         // mark task finished!
        }

        fin.report("SHORTEST REMAINING TIME NEXT");
    }
}
