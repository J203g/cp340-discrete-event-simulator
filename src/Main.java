/**
 * The main program for my Discrete Event Simulator program.
 * Development started in mid March, and finished early May.
 * Version control in GitHub, timeline listed in documentation.
 * @param count   {@code private} The number of tasks to generate.
 * @param quantum {@code private} The time quantum for round robin.
 * @version 1.0
 * @author Joshua Levin!
 * @since 0.1 {@code Feb 23rd} initial {@link Task} implementation
 * @since 0.2 {@code Feb 25th} working {@link FCFS} scheduler
 * @since 0.3 {@code Mar 25th} refactor and documentation
 * @since 0.4 {@code Apr 25th} working {@link RR} scheduler
 * @since 0.5 {@code Apr 26th} added {@code Stats} class to reduce complexity
 * @since 0.6 {@code Apr 27th} working {@link SJF} scheduler
 * @since 0.7 {@code May 1st} project refactoring and documentation
 * @since 0.8 {@code May 8th} {@code Stats} class replaced by {@link Schedule}
 * @since 0.9 {@code May 9th} working {@link STRN} scheduler
 * @since 1.0 {@code May 9th} Final version for submission.
 * @see {@link Task}
 * @see {@link Schedule}
 * @see {@link RR Round Robin}
 * @see {@link SJF Shortest Job First}
 * @see {@link FCFS First Come First Serve}
 * @see {@link SRT Shortest Remaining Time}
 */
public class Main {
    public static void main(String[] args) throws Exception {
        int count = 150;
        int quantum = 5;
        Task.lambdA = 4.2; // default 4.2, but can be anything you want
        Task.lambdB = 8.6; // default 8.6, but can be anything you want
        
        /* MIDTERM TASKS - the default test case, uncomment to test
        Schedule queue = Schedule.Midterm(); /*/

        // RANDOM TASKS - for post-development analysis
        Schedule queue = new Schedule(count, Task::arrivalT); //*/

        //queue.show(); // display tasks on start up

        System.out.println("\nSimulation start!");

        FCFS.EventSim(queue);           // 1) First Come First Serve
        RR .EventSim(queue, quantum);   // 2) Round Robin
        SJF.EventSim(queue);            // 3) Shortest Job First
        SRT.EventSim(queue);            // 4) Shortest Remaining Time

        System.out.println("\nSimulation over!\n");
    }//*/
}