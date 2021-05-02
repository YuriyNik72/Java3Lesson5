import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {

    private static int CARS_COUNT;
    private static boolean winnerFound;

    static {
        CARS_COUNT = 1;
    }

    private Race race;
    private int speed;
    private String name;
    private CyclicBarrier cb;


    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }


    public Car(Race race, int speed, CyclicBarrier cb) {
        this.race = race;
        this.speed = speed;
        this.name = "Участник #" + CARS_COUNT;
        this.cb = cb;
        CARS_COUNT++;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");

            cb.await();
            cb.await();
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }
            checkWinner(this);
            cb.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        private static synchronized void checkWinner(Car c) {
        if (!winnerFound) {
            System.out.println(c.name + " - WIN");
            winnerFound = true;
        }
    }
}
