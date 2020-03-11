package Task25;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/*The main class, which contains Slot-Machine logic.*/
public class SlotMachine {
    public static void main(String[] args) {

        System.out.println("Welcome to slot machine.\nPlease type your name, age and budget.");

        Player player = new Player();
        Manager manager = Manager.getInstance();
        Game game = new Game();
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

        if (manager.acceptPlayer(player)) {

            player.budget -= Game.GAME_COST;
            ArrayList<Integer> allRows = game.playGame();

            Runnable task1 = () -> {
                System.out.println("\n"+allRows.stream().limit(3).map(Object::toString).collect(Collectors.joining("|")));
            };
            Runnable task2 = () -> {
                System.out.println(allRows.stream().skip(3).limit(3).map(Object::toString).collect(Collectors.joining("|")));
            };
            Runnable task3 = () -> {
                System.out.println(allRows.stream().skip(6).limit(3).map(Object::toString).collect(Collectors.joining("|")));
            };

            try {
                ses.schedule(task1, 1, TimeUnit.SECONDS);
                ses.schedule(task2, 2, TimeUnit.SECONDS);
                ses.schedule(task3, 3, TimeUnit.SECONDS);
                Thread.sleep(4000);
                if (game.containsForWinnerRow()) {
                    System.out.print("\nYou are the Lucky one, " + player.name + "!");
                    player.budget += Game.GAME_COST * 100;
                } else
                    System.out.print("\nYou will win in the next time, buddy!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.print("\nNow you budget is " + player.budget + ".");
                ses.shutdown();
            }

        } else
            System.out.print("\nДоступ к игре запрещен");
    }
}

/*Class of the Player person.*/
class Player {
    String name;
    int age;
    int budget;
    private Scanner scanner = new Scanner((System.in));

    Player() {
        try {
            this.name = scanner.nextLine();
            this.age = scanner.nextInt();
            this.budget = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.print("\n" +
                    "You entered the incorrectly data!" +
                    "\nRestart the game and try to enter correctly information, please.");
        } finally {
            scanner.close();
        }
    }
}

/*The Manager class, which will allow or restrict players to the game. */
class Manager {
    private static Manager manager;

    static synchronized Manager getInstance() {
        if (manager == null)
            manager = new Manager();
        return manager;
    }

    boolean acceptPlayer(Player player) {
        return player.age >= 21 && player.budget >= Game.GAME_COST;
    }
}

/*The Game class, which will prepare list of random numbers for game`s field and check for a win it the game.*/
class Game {
    static final int GAME_COST = 30;
    private ArrayList<Integer> fields = new ArrayList<Integer>(9);

    ArrayList<Integer> playGame() {
        for (int a = 0; a < 9; a++) {
            fields.add((int) ((Math.random() * 10)));
        }
        return fields;
    }

    boolean containsForWinnerRow() {
        return fields.stream().limit(3).distinct().count() == 1 ||
                fields.stream().skip(3).limit(3).distinct().count() == 1 ||
                fields.stream().skip(6).limit(3).distinct().count() == 1;
    }
}