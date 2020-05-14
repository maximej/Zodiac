/*
 * Ni aimer, ni haïr : voilà la moitié de toute sagesse.  
 * Ne rien dire et ne rien croire : voilà l'autre.
 */
package obj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DateFormat;
import java.util.Date;

/**
 * JavaDoc de la classe Graphic
 *
 * @author cda413 --> Maxime J. Richard @ CDA1910.313
 */
public interface Graphic {

    static int LONGUEURTRAIT = 120;
    static String PREFIXETRAIT = "++ -- ";
    static String SUFIXETRAIT = " -- ++";
    static String SYMBOL = "-";
    static String INTROBR = " ++ -- > ";
    static int PAUSE = 100;

    public static String donnerDate() {
        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        return shortDateFormat.format(new Date());
    }

    public static String imprimerHeure(int longueur) {
        String heure = PREFIXETRAIT;
        DateFormat shortTimeFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT);
        //    System.out.println(shortTimeFormat.format(new Date()));
        heure += shortTimeFormat.format(new Date()) + "  ";
        while (heure.length() < longueur - SUFIXETRAIT.length()) {
            heure += SYMBOL;
        }
        heure += SUFIXETRAIT;
        // System.out.println(heure);

        return heure;
    }

    public static String imprimerTrait() {
        String texte = PREFIXETRAIT;
        while (texte.length() < LONGUEURTRAIT - SUFIXETRAIT.length()) {
            texte += SYMBOL;
        }
        texte += SUFIXETRAIT + "\n";
        System.out.print(texte);

        return texte;
    }

    public static String imprimerTraitBR() {
        String texte = PREFIXETRAIT;
        while (texte.length() < LONGUEURTRAIT - SUFIXETRAIT.length()) {
            texte += SYMBOL;
        }
        texte += SUFIXETRAIT + INTROBR;
        System.out.print(texte);

        return texte;
    }

    public static String imprimerTexte(String titre) {
        String texte = PREFIXETRAIT;
        texte += titre + "  ";
        while (texte.length() < LONGUEURTRAIT - SUFIXETRAIT.length()) {
            texte += SYMBOL;
        }
        texte += SUFIXETRAIT;
        //  System.out.println(texte);
        System.out.println(texte);
        return texte;
    }

    public static String imprimerTexteBR(String titre) {
        String texte = PREFIXETRAIT;
        texte += titre + "  ";
        while (texte.length() < LONGUEURTRAIT - SUFIXETRAIT.length()) {
            texte += SYMBOL;
        }
        texte += SUFIXETRAIT + INTROBR;
        //  System.out.println(texte);
        System.out.print(texte);
        return texte;
    }

    public static void animerCadre(String texte) throws InterruptedException {
        String[] lines = texte.split(System.getProperty("line.separator"));
        for (String l : lines) {
       //     System.out.println(l);
            Thread.sleep(PAUSE);
        }
    }

    public static String encadrerTexte(String texte) throws InterruptedException {
        StringBuilder cadre = new StringBuilder();
        int longueurDeLigne = LONGUEURTRAIT - (PREFIXETRAIT.length() + SUFIXETRAIT.length());
        //  cadre.append(imprimerTrait());
        int lignes = (int) texte.length() / longueurDeLigne;
        for (int i = 0; i < lignes + 1; i++) {
            int max = 0;
            String completion = "";
            if (longueurDeLigne * (i + 1) < texte.length()) {
                max = longueurDeLigne * (i + 1);
            } else {
                max = texte.length();
                completion += " ";
                for (int j = 0; j < (longueurDeLigne * (i + 1) - max - 1); j++) {
                    completion += SYMBOL;
                }

            }
            cadre.append(PREFIXETRAIT).append(texte.substring(longueurDeLigne * i, max)).append(completion).append(SUFIXETRAIT).append("\n");
        }
        cadre.append(imprimerTrait());
               //     Thread.sleep(PAUSE);

        System.out.print(cadre.toString());
        return cadre.toString();
    }

    public static void imprimerTitre() {
        imprimerTrait();
        System.out.println(imprimerHeure(LONGUEURTRAIT));
        imprimerTrait();
        imprimerTexte("\n _____          _ _           ");
        imprimerTexte("/ _  / ___   __| (_) __ _  ___ ");
        imprimerTexte("\\// / / _ \\ / _` | |/ _` |/ __|");
        imprimerTexte(" / //\\ (_) | (_| | | (_| | (__ ");
        imprimerTexte("/____/\\___/ \\__,_|_|\\__,_|\\___|");
        imprimerTexte("                               \n");
        imprimerTrait();

    }
}
