import java.util.concurrent.Semaphore;

public class Main {
    // https://www.geeksforgeeks.org/dining-philosopher-problem-using-semaphores/
    // http://openbook.rheinwerk-verlag.de/java7/1507_02_001.html

    public static final int NUMBER_OF_PHILOSOPHERS = 5;
    public static final int NUMBER_OF_ROUNDS = 5;

    public static Semaphore[] chopsticks;
    private static Thread[] philosophers;

    public static void main(String[] args) {

        chopsticks = new Semaphore[NUMBER_OF_PHILOSOPHERS];
        philosophers = new Thread[NUMBER_OF_PHILOSOPHERS];
        for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
            chopsticks[i] = new Semaphore(1);
        }

        for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
            philosophers[i] = new Thread(new Philosopher(i));
            philosophers[i].start();
            System.out.printf("Starting philosopher %d on %d%n", i, philosophers[i].getId());
        }

        for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
            try {
                philosophers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("Waiting for philosopher %d%n", i);
        }

        System.out.println("done");
    }
}
