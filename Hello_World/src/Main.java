import java.util.Random;


public class Main {
    public static void main(String[] args) {
        Random rand = new Random();
        if(args.length > 0)
            System.out.print(args[rand.nextInt(3)] + "\n");
        System.out.print("Hello" + " " + "World");
    }
}