import java.util.Random;

public class Snake extends Obstacle {
    public Snake() {
        super("Yılan", 4, randomDamage(), 12, 0);
    }

    public static int randomDamage() {
        int randomDamage;
        Random random = new Random(3);
        return randomDamage = random.nextInt(7);
    }
}
