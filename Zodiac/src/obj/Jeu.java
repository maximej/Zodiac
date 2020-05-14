/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import com.oracle.jrockit.jfr.DataType;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author max
 */
public class Jeu {

    private ArrayList<Path> mesEnigmes;

    private String jeuPath;
    private String dataLocation = "/src/data";
    private String lisezMoi = "/src/LisezMoi.txt";

    public Jeu() {
        setJeuPath(System.getProperty("user.dir"));
        setMesEnigmes(lireFichiers());
    }

    public Jeu(String jeuPath) {
        setJeuPath(jeuPath);
        setMesEnigmes(lireFichiers());

    }

    public String getJeuPath() {
        return jeuPath;
    }

    public void setJeuPath(String jeuPath) {
        this.jeuPath = jeuPath;
    }

    public ArrayList<Path> getMesEnigmes() {
        return mesEnigmes;
    }

    public void setMesEnigmes(ArrayList<Path> mesEnigmes) {
        this.mesEnigmes = mesEnigmes;
    }

    public ArrayList<Path> lireFichiers() {
        ArrayList<Path> mesFichiers = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(Paths.get(jeuPath + dataLocation))) {
            List<String> result = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());
            //           result.forEach(System.out::println);
            for (String r : result) {
                if (r.endsWith(".txt")) {
                    mesFichiers.add(Paths.get(r));
                }
            }

            //    result.forEach((n) -> mesFichiers.add(Paths.get(n)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mesFichiers;
    }

    public void JouerEnigme(Path fichierEnigme, BufferedReader br) throws IOException, InterruptedException {
        ArrayList<Socle> mesSocles = new ArrayList<>();
        ArrayList<Buste> mesBustes = new ArrayList<>();
        List<String> allLines = Files.readAllLines(Paths.get(fichierEnigme.toString()));
        for (String l : allLines) {
            String[] tokens = l.split("=");
            if (tokens.length > 1) {
                Buste b = new Buste(tokens[0]);
                mesBustes.add(b);
                Socle s = new Socle(tokens[0], tokens[1]);
                mesSocles.add(s);
            } else if (tokens.length < 2) {
                Graphic.imprimerTexte("Une ligne du fichier à été omise. Editez [" + fichierEnigme.toString() + "].");
            }
        }
        mesBustes.toString();
        mesSocles.toString();

        if (mesBustes.size() == mesSocles.size()) {
            Enigme monEnigme = new Enigme(mesSocles, mesBustes);
            monEnigme.resoudreEnigme(br);
        } else {
            Graphic.imprimerTexte("Le fichier demandé n'est pas jouable. Consultez LisezMoi.txt");
        }

    }

    public void lireFichierTxt(String path) {
        List<String> records = new ArrayList<String>();
        try {
            
            BufferedReader reader = new BufferedReader(new InputStreamReader (new FileInputStream(path), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                records.add(line);
                Graphic.imprimerTexte(line);
                Thread.sleep(100);
            }
            reader.close();
            //Graphic.encadrerTexte(records.toString());
        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", path);
            e.printStackTrace();
        }
    }

    public void ecrireEnigme(String path, BufferedReader br) throws IOException, InterruptedException {
        String fileSeparator = System.getProperty("file.separator");
        Graphic.imprimerTrait();
        Graphic.imprimerTexteBR("Tapez le nom de votre énigme : ");
        String nom = br.readLine();
        while (nom.length() == 0) {
            nom = br.readLine();
        }
        String nouvelleEnigme = jeuPath + dataLocation + fileSeparator + nom + ".txt";
        File file = new File(nouvelleEnigme);
        try {
            if (file.createNewFile()) {
                Graphic.imprimerTexteBR("Combien de socles comporte votré énigme ? (min. 3)");
                int socles = 3;
                try {
                    socles = Integer.valueOf(br.readLine());
                } catch (NumberFormatException n) {
                    Graphic.encadrerTexte("Vous devez taper un nombre, au moins 3.");
                }
                if (socles < 3) {
                    Graphic.animerCadre(Graphic.encadrerTexte("VOUS DEVEZ FAIRE UNE ENIGME AVEC AU MOINS 3 SOCLES !!!"));
                    socles = 3;
                }
                Writer output = new BufferedWriter(new FileWriter(nouvelleEnigme, true));

                for (int i = 1; i <= socles; i++) {
                    Graphic.imprimerTexteBR("[" + i + ".1] Tapez le nom de la statuette n°" + i + " : ");
                    String buste = br.readLine();
                    Graphic.imprimerTexteBR("[" + i + ".2] Tapez la description de la statuette de " + buste + " : ");
                    String socle = br.readLine();
                    output.append(buste + "=" + socle);
                    output.append(System.getProperty("line.separator"));
                }
                output.close();

                Graphic.imprimerTrait();
                Graphic.imprimerTexte("Vous avez créé une nouvelle énigme à " + socles + " statuettes : " + nom.toUpperCase());
                Graphic.imprimerTexte("Pour la modifier ou la supprimer, utilisez le fichier : ");
                Graphic.imprimerTexte(nouvelleEnigme);
                Graphic.imprimerTrait();

            } else {
                Graphic.encadrerTexte("Le fichier [" + nouvelleEnigme + "] n'a pas été créé. Il existe peut-être déja, ou vous n'avez pas les droits en écriture.");
            }
        } catch (IOException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        lireFichierTxt(nouvelleEnigme);

    }

    public StringBuilder creerMenuJeu() {
        int tailleMenu = 0;

        StringBuilder menu = new StringBuilder();
        for (Path p : mesEnigmes) {
            tailleMenu += 1;
            String nom = p.getFileName().toString().toUpperCase();
            menu.append("[" + tailleMenu + "] Jouer à l'énigme : " + nom.substring(0, nom.length() - 4)).append(System.getProperty("line.separator"));
        }
        tailleMenu += 1;
        menu.append("[" + tailleMenu + "] Créer une nouvelle enigme").append(System.getProperty("line.separator"));
        tailleMenu += 1;
        menu.append("[" + tailleMenu + "] Afficher LisezMoi.txt").append(System.getProperty("line.separator"));
        tailleMenu += 1;
        menu.append("[" + tailleMenu + "] Quitter le jeu").append(System.getProperty("line.separator"));

        return menu;
    }

    public void utiliserMenuJeu(BufferedReader br) throws IOException, InterruptedException {
        int tailleMenu = 0;
        int saisi = 0;
        StringBuilder menu = new StringBuilder(creerMenuJeu());
        tailleMenu = menu.toString().split(System.getProperty("line.separator")).length;

        while (saisi != tailleMenu) {
            setMesEnigmes(lireFichiers());

            menu = new StringBuilder(creerMenuJeu());
            tailleMenu = menu.toString().split(System.getProperty("line.separator")).length;
            // Impression du Menu Principal
            Graphic.imprimerTitre();
            Graphic.imprimerTrait();
            String[] lines = menu.toString().split(System.getProperty("line.separator"));
            for (String line : lines) {
                Graphic.imprimerTexte(line);
            }
            Graphic.imprimerTraitBR();
            try {
                saisi = Integer.parseInt(br.readLine());
            } catch (Exception e) {

            }
            if (saisi == tailleMenu - 1) {
                lireFichierTxt(jeuPath + lisezMoi);
            } else if (saisi == tailleMenu - 2) {

                ecrireEnigme(jeuPath + dataLocation, br);
            } else if (saisi <= mesEnigmes.size() && saisi != 0) {
                JouerEnigme(mesEnigmes.get(saisi - 1), br);
            }

        }
    }
;

}
