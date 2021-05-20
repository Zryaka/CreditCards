package ru.sber.cards.utilities;

import java.util.Random;

public class RandomAccount {

    public static String createNewAcoount() {
        Random rand = new Random();
        String account = "";
        for (int i = 0;i < 20; i++){
            int n = rand.nextInt(10) + 0;
            account += Integer.toString(n);
        }
        return account;
    }
}
