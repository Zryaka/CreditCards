package ru.sber.cards.utilities;

import java.util.Random;

public class RandomCard {

    public static String createNewCard() {
        Random rand = new Random();
        String  card= "";
        for (int i = 1; i <= 16; i++) {
            int n = rand.nextInt(10) + 0;
            card += Integer.toString(n);
           if((i!=0) && (i % 4 == 0)) {
               card += " ";
           }
        }
        return card;
    }
}
