import java.util.Random;

public class Philosopher extends Thread {
    private final int philosopherNumber;

    public Philosopher(int philosopherNumber) {
        this.philosopherNumber = philosopherNumber;
    }

    @Override
    public void run() {
        super.run();

        for (int round = 0; round < Main.NUMBER_OF_ROUNDS; round++) {
            try {

                System.out.printf("Philosopher %d starts round %d%n", philosopherNumber, round);

                Think(philosopherNumber);
                PickUp(philosopherNumber);
                Eat(philosopherNumber);
                PutDown(philosopherNumber);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(3000); // seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Philosopher %d done on thread: %d%n",
                philosopherNumber,
                Thread.currentThread().getId());
    }

    void Think(int philosopherNumber) throws InterruptedException {
        Random random = new Random(System.currentTimeMillis());
        int sleepTime = (random.nextInt(100) % 3 + 1) * 100;
        System.out.printf("Philosopher %d will think for %d micro-seconds%n",
                philosopherNumber,
                sleepTime);
        Thread.sleep(sleepTime);
    }

    void PickUp(int philosopherNumber) throws InterruptedException {
        int right = (philosopherNumber + 1) % Main.NUMBER_OF_PHILOSOPHERS;
        int left = (philosopherNumber + Main.NUMBER_OF_PHILOSOPHERS) % Main.NUMBER_OF_PHILOSOPHERS;

        int first = philosopherNumber % 2 == 0 ? right : left;
        int second = philosopherNumber % 2 == 0 ? left : right;

        System.out.printf("Philosopher %d is waiting to pick up chopstick %d%n", philosopherNumber, first);

        Main.chopsticks[first].acquire();
        System.out.printf("Philosopher %d picked up chopstick %d%n", philosopherNumber, first);

        System.out.printf("Philosopher %d is waiting to pick up chopstick %d%n", philosopherNumber, second);
        Main.chopsticks[second].acquire();
        System.out.printf("Philosopher %d picked up chopstick %d%n", philosopherNumber, second);
    }

    void Eat(int philosopherNumber) throws InterruptedException {
        Random random = new Random(System.currentTimeMillis());

        int eatTime = (random.nextInt(100) % 3 + 1) * 100;
        System.out.printf("Philosopher %d will eat for %d micro-seconds%n", philosopherNumber, eatTime);
        Thread.sleep(eatTime);
    }

    void PutDown(int philosopherNumber) {
        System.out.printf("Philosopher %d will will put down her chopsticks%n", philosopherNumber);
        Main.chopsticks[(philosopherNumber + 1) % Main.NUMBER_OF_PHILOSOPHERS].release();
        Main.chopsticks[(philosopherNumber + Main.NUMBER_OF_PHILOSOPHERS) % Main.NUMBER_OF_PHILOSOPHERS].release();
    }
}
