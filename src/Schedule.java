import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.Function;

/**
 * The {@code Schedule} class implements {@link PriorityQueue} to define
 * a set of tasks that can be sorted by a given parameter.
 * @author Joshua Levin
 * @since 0.8 {@code May 8th}
 */
public class Schedule extends PriorityQueue<Task> {

    /**
     * This constructor populates the schedule with a provided queue.
     * @param queue The set of tasks to add
     */
    public Schedule(Collection<Task> queue) {
        super(queue);
    }

    /**
     * This constructor populates the schedule with {@code n}
     * randomly generated tasks and orders them by a key.
     * @param n The number of tasks to generate (initial capacity)
     * @param key The comparator-defining value function
     */
    public Schedule(int n, Function<Task, Integer> key) {
        super(Comparator.comparing(key));
        for (int i = 0; i < n; i++) this.add(new Task());
    }//*/
    
    /**
     * This constructor populates the schedule with the provided
     * set of tasks and orders them by a key.
     * @param queue The list of tasks to add
     * @param key The comparator-defining value function
     */
    public Schedule(Collection<Task> queue, Function<Task, Integer> key) {
        super(Comparator.comparing(key));
        this.addAll(queue);
    }

    /**
     * The {@code Midterm} method recreates the midterm test case.
     * @return A schedule containing the five midterm tasks
     */
    public static Schedule Midterm() {
        Schedule midterm = new Schedule(0, Task::arrivalT);
        midterm.add(new Task(1, 0, 15));
        midterm.add(new Task(2, 2, 2));
        midterm.add(new Task(3, 3, 14));
        midterm.add(new Task(4, 6, 10));
        midterm.add(new Task(5, 10, 2));
        return midterm;
    }//*/

    /**
     * Returns whether the next task would arrive by the given time.
     * Returns false if the queue is empty.
     * @param period The period of time a task can enter the CPU
     * @return {@code true} if the next task arrives during the period, or
     *        {@code false} if the otherwise or if the queue is empty
     */
    public boolean nextBefore(int period) {
        return this.isEmpty() ? false : this.peek().arrival <= period;
    }
    
    /**
     * Returns whether the next task would execute faster than the given time.
     * Returns false if the queue is empty.
     * @param time The execution time to test against
     * @return {@code true} if the next task would be faster, or
     *        {@code false} if the otherwise or if the queue is empty
     */
    public boolean nextFaster(int time) {
        return this.isEmpty() ? false : this.peek().burst < time;
    }

    /**
     * The {@code report} method sums the simulation times of a
     * {@code Schedule} and prints its average times to the screen.
     * @param name The name of the scheduling method
     */
    public void report(String name) {
        Schedule report = new Schedule(this, Task::processID);
        int CT=0, TT=0, WT=0, RT=0; // sums
        int size = report.size(); // size

        System.out.printf("\n%s - avg of %d tasks\n", name, size);
        System.out.println(" COMP\tTURN\tWAIT\tRESP");

        while (!report.isEmpty()) {
            Task t = report.poll();
            CT += t.comp; // sum variables
            TT += t.turn;
            WT += t.wait;
            RT += t.resp;
            /*System.out.printf(" %3d\t%3d\t%3d\t%3d\n",
                t.comp, t.turn, t.wait, t.resp);//*/
            t.burst = t.exe;
            t.comp = 0;
            t.turn = 0;
            t.wait = 0;
            t.resp = -1; // reset variables for next algorithm
        }

        // print resulting averages
        System.out.println(" ---------------------------");
        System.out.printf(" %3d\t%3d\t%3d\t%3d\n",
                        CT/size, TT/size, WT/size, RT/size);
    }//*/

    /**
     * Prints the schedule to the console.
     */
    public void show() {
        Schedule queue = new Schedule(this, Task::processID);
        System.out.println("\n   TASK SCHEDULE");
        System.out.println  (" ID  ARRIVAL  BURST");
        
        while (!queue.isEmpty()) { Task t = queue.poll();
            System.out.printf("%3d   %3d     %3d\n",
                            t.process, t.arrival, t.burst);
        }
    }
}