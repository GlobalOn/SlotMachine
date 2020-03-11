package Task1;

import java.util.Scanner;

public class FirstTask {
    public static void main(String[] args) {

        Ticket ticket = new Ticket();
        LuckyForMoscow luckyForMoscow = new LuckyForMoscow();
        LuckyForSaintP luckyForSaintP = new LuckyForSaintP();

        if (luckyForMoscow.isLucky(ticket) && luckyForSaintP.isLucky(ticket)) {
            System.out.println("Это точно счастливый билет!");
        } else System.out.println("В этот раз не повезло, дружище!");
        System.out.println();
    }
}

interface LuckyNumbers {
    boolean isLucky(Ticket ticket);
}

class Ticket {
    int[] numbers = new int[6];

    Ticket() {
        for (int i = 0; i < this.numbers.length; i++) {
            numbers[i] = new Scanner(System.in).nextInt();
        }

    }
}

class LuckyForMoscow implements LuckyNumbers {
    public boolean isLucky(Ticket ticket) {
        return ticket.numbers[0] + ticket.numbers[1] + ticket.numbers[2] ==
                ticket.numbers[3] + ticket.numbers[4] + ticket.numbers[5];
    }
}

class LuckyForSaintP implements LuckyNumbers {
    public boolean isLucky(Ticket ticket) {
        return ticket.numbers[0] + ticket.numbers[2] + ticket.numbers[4] ==
                ticket.numbers[1] + ticket.numbers[3] + ticket.numbers[5];
    }
}