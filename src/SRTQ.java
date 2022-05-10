/**
 * The next idea for a scheduling algorithm in this project.
 * Preempts by burst time.
 * @since 0.9 {@code May 9th}
 * @author Joshua Levin
 */
public class SRTQ {
    /**
     * This is a new kind of algorithm that runs like {@link SRT},
     * but uses the time quantum of {@link RR}!
     * @param queue The set of tasks to be simulated.
     * @param quantum The time limit for a task in the CPU.
     */
    public static void EventSim(Schedule queue, int quantum) {
        Schedule tasks = new Schedule(queue);
        Schedule ready = new Schedule(0, Task::burstT);
        int simClock = 0;

        ready.add(tasks.poll());         // add first task
        
        while (!ready.isEmpty())         // PREEMPTIVE MAIN LOOP
        {
            Task t = ready.poll();       // schedule task & update clock
            simClock = Math.max(t.arrival, simClock);

            if (t.resp < 0) t.resp = simClock; // set response time

            // SRT section!
            while (tasks.nextBefore(simClock + t.burst)) {
                if (t.resp < 0) t.resp = simClock; // set response time
                
                Task nt = tasks.poll();
                
                t.burst -= nt.arrival - simClock;           // update burst
                simClock = Math.max(nt.arrival, simClock);  // update clock
                
                if (nt.burst < t.burst) {
                    ready.add(t);               // put old back in the queue
                    t = nt;                     // replace old with new
                } else ready.add(nt);           // else put back new task
            }

            if (t.resp < 0) t.resp = simClock;  // set response time

            // RR section!
            if (t.burst > quantum) {     // apply execution time -> unfinished
                simClock += quantum;            // update clock
                t.burst -= quantum;             // update burst
                tasks.add(t);                   // add to the next queue
            }
            else {                       // apply execution time -> finished
                simClock += t.burst;            // apply execution time
                t.comp = simClock;              // set turnaround time
                t.turn = t.comp - t.arrival;    // set completion time
                t.resp = t.resp - t.arrival;    // set response time
                t.wait = t.turn - t.exe;        // set wait time
            }
        }
        queue.report("SHORTEST REMAINING TIME");
    }
}
