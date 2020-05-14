/*
 * Ni aimer, ni haïr : voilà la moitié de toute sagesse.  
 * Ne rien dire et ne rien croire : voilà l'autre.
 */
package appli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import obj.*;

/**
 * JavaDoc de la classe ApppliMain001
 *
 * @author cda413 --> Maxime J. Richard @ CDA1910.313
 */
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        Jeu monJeu = new Jeu();
        monJeu.utiliserMenuJeu(br);

        br.close();
        isr.close();
    }
}
