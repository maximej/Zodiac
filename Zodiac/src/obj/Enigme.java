/*
 * Ni aimer, ni haïr : voilà la moitié de toute sagesse.  
 * Ne rien dire et ne rien croire : voilà l'autre.
 */
package obj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.Map.Entry;

/**
 * JavaDoc de la classe Enigme
 *
 * @author cda413 --> Maxime J. Richard @ CDA1910.313
 */
public class Enigme {

    private LinkedHashMap<String, Socle> monEnigme;
    private ArrayList<Socle> mesSocles;
    private ArrayList<Buste> mesBustes;
    private int correspondances;
    private int correspondancesPrecedentes;
    private int taille;
    private Instant debutDuJeu;

    public Enigme() {
    }

    public Enigme(ArrayList<Socle> mesSocles, ArrayList<Buste> mesBustes) {
        creerEnigme(mesSocles, mesBustes);
        setMesBustes(mesBustes);
        setMesSocles(mesSocles);
        setTaille();
    }

    public Enigme(LinkedHashMap<String, Socle> monEnigme) {
        this.monEnigme = monEnigme;
        ArrayList<Socle> soclesExtraits = new ArrayList<>();
        ArrayList<Buste> bustesExtraits = new ArrayList<>();

        for (Map.Entry<String, Socle> entry : monEnigme.entrySet()) {
            bustesExtraits.add(entry.getValue().getMonBuste());
            soclesExtraits.add(entry.getValue());
        }
        setMesBustes(bustesExtraits);
        setMesSocles(soclesExtraits);
        setTaille();

    }

    public LinkedHashMap<String, Socle> getMonEnigme() {
        return monEnigme;
    }

    public void setMonEnigme(LinkedHashMap<String, Socle> monEnigme) {
        this.monEnigme = monEnigme;

    }

    public ArrayList<Socle> getMesSocles() {
        return mesSocles;
    }

    public void setMesSocles(ArrayList<Socle> mesSocles) {
        this.mesSocles = mesSocles;
    }

    public ArrayList<Buste> getMesBustes() {
        return mesBustes;
    }

    public void setMesBustes(ArrayList<Buste> mesBustes) {
        this.mesBustes = mesBustes;
    }

    public int getCorrespondances() {
        return correspondances;
    }

    public void setCorrespondances(int correspondances) {
        this.correspondances = correspondances;
    }

    public int getCorrespondancesPrecedentes() {
        return correspondancesPrecedentes;
    }

    public void setCorrespondancesPrecedentes(int correspondancesPrecedentes) {
        this.correspondancesPrecedentes = correspondancesPrecedentes;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille() {
        this.taille = mesSocles.size();
    }

    public Instant getDebutDuJeu() {
        return debutDuJeu;
    }

    public void setDebutDuJeu(Instant debutDuJeu) {
        this.debutDuJeu = debutDuJeu;
    }

    public void creerEnigme(ArrayList<Socle> mesSocles, ArrayList<Buste> mesBustes) {
        if (mesSocles.size() != mesBustes.size()) {
            obj.Graphic.imprimerTexte("Erreur : Vous n'avez pas autant de socles que de bustes !");
        } else {
            Collections.shuffle(mesBustes);
            Collections.shuffle(mesSocles);

            LinkedHashMap<String, Socle> nouvelleEnigme = new LinkedHashMap<>();
            for (int i = 0; i < mesSocles.size(); i++) {
                mesSocles.get(i).setMonBuste(mesBustes.get(i));
                nouvelleEnigme.put(mesSocles.get(i).getMonBuste().getNom(), mesSocles.get(i));
            }
            setMonEnigme(nouvelleEnigme);
        }
    }

    public void majEnigme() {
        LinkedHashMap<String, Socle> nouvelleEnigme = new LinkedHashMap<>();
        for (Map.Entry<String, Socle> entry : monEnigme.entrySet()) {
            nouvelleEnigme.put(entry.getValue().getMonBuste().getNom(), entry.getValue());
        }
        setMonEnigme(nouvelleEnigme);
    }

    public void echangerBuste(BufferedReader br) throws IOException, InterruptedException {
        int compteur = 0;
        boolean premiereClef = false;
        boolean deuxiemeClef = false;
        boolean pcNum = false;
        boolean dcNum = false;
        String premierBuste = "";
        String deuxiemeBuste = "";

        while (premiereClef == false && compteur < 3) {
            compteur += 1;
            obj.Graphic.imprimerTexteBR("Tapez le nom ou le numéro de la première statuette :");
            premierBuste = br.readLine();
            if ((isInteger(premierBuste)) && (Integer.valueOf(premierBuste) <= taille) && (Integer.valueOf(premierBuste) > 0)) {
                premiereClef = true;
                pcNum = true;
            } else if (pcNum == false) {
                for (String clef : monEnigme.keySet()) {
                    if (clef.equalsIgnoreCase(premierBuste)) {
                        premierBuste = clef;
                        premiereClef = true;
                    }
                }
            }
            if (premiereClef == false) {
                obj.Graphic.imprimerTexte("Vous ne trouvez pas de statuette correspondante.");
            }
        }

        while (deuxiemeClef == false && compteur < 4) {
            compteur += 1;
            obj.Graphic.imprimerTexteBR("Tapez le nom ou le numéro de la deuxième statuette :");
            deuxiemeBuste = br.readLine();
            if ((isInteger(deuxiemeBuste)) && (Integer.valueOf(deuxiemeBuste) <= taille) && (Integer.valueOf(deuxiemeBuste) > 0)) {
                deuxiemeClef = true;
                dcNum = true;
            } else if (dcNum == false) {
                for (String clef : monEnigme.keySet()) {
                    if (clef.equalsIgnoreCase(deuxiemeBuste)) {
                        deuxiemeBuste = clef;
                        deuxiemeClef = true;
                    }
                }
            }
            if (deuxiemeClef == false) {
                obj.Graphic.imprimerTexte("Vous ne trouvez pas de statuette correspondante.");
            }
        }

        if (premiereClef && deuxiemeClef) {
            if (pcNum && dcNum) {
                Set<String> keySet = monEnigme.keySet();
                String[] keyArray = keySet.toArray(new String[keySet.size()]);
                String key1 = keyArray[Integer.valueOf(premierBuste) - 1];
                String key2 = keyArray[Integer.valueOf(deuxiemeBuste) - 1];
                Buste tempBuste = (monEnigme.get(key2).getMonBuste());
                monEnigme.get(key2).setMonBuste(monEnigme.get(key1).getMonBuste());
                monEnigme.get(key1).setMonBuste(tempBuste);
                obj.Graphic.encadrerTexte("Vous avez échangé " + key1 + " avec " + key2);
            } else if (!pcNum && !dcNum) {
                Buste tempBuste = (monEnigme.get(deuxiemeBuste).getMonBuste());
                monEnigme.get(deuxiemeBuste).setMonBuste(monEnigme.get(premierBuste).getMonBuste());
                monEnigme.get(premierBuste).setMonBuste(tempBuste);
                obj.Graphic.encadrerTexte("Vous avez échangé " + premierBuste + " avec " + deuxiemeBuste);
            } else if ((pcNum && !dcNum)) {
                Set<String> keySet = monEnigme.keySet();
                String[] keyArray = keySet.toArray(new String[keySet.size()]);
                String key1 = keyArray[Integer.valueOf(premierBuste) - 1];
                Buste tempBuste = (monEnigme.get(deuxiemeBuste).getMonBuste());
                monEnigme.get(deuxiemeBuste).setMonBuste(monEnigme.get(key1).getMonBuste());
                monEnigme.get(key1).setMonBuste(tempBuste);
                obj.Graphic.encadrerTexte("Vous avez échangé " + key1 + " avec " + deuxiemeBuste);
            } else if ((!pcNum && dcNum)) {
                Set<String> keySet = monEnigme.keySet();
                String[] keyArray = keySet.toArray(new String[keySet.size()]);
                String key2 = keyArray[Integer.valueOf(deuxiemeBuste) - 1];
                Buste tempBuste = (monEnigme.get(key2).getMonBuste());
                monEnigme.get(key2).setMonBuste(monEnigme.get(premierBuste).getMonBuste());
                monEnigme.get(premierBuste).setMonBuste(tempBuste);
                obj.Graphic.encadrerTexte("Vous avez échangé " + premierBuste + " avec " + key2);
            }
            majEnigme();
        } else if (!premiereClef || !deuxiemeClef) {
            obj.Graphic.encadrerTexte("Aucune statuette n'a bougé. Observez les statuettes afin d'obtenir leur nom exact.");
        }
    }

    public void observerSalle() throws InterruptedException {
        int compteur = 0;
        for (Map.Entry<String, Socle> entry : monEnigme.entrySet()) {
            compteur += 1;
            Graphic.animerCadre(obj.Graphic.encadrerTexte("-- [" + compteur + "] Une statuette de " + entry.getValue().getMonBuste().getNom() + " est posée sur un socle où vous lisez la description suivante : " + entry.getValue().getDescription()));
        }
    }

    public void observerBuste() throws InterruptedException {
        StringBuilder observation = new StringBuilder();
        observation.append("Vous voyez plusieurs statuettes autour de vous, en voici la liste :  ");
        for (Map.Entry<String, Socle> entry : monEnigme.entrySet()) {
            observation.append(entry.getValue().getMonBuste().getNom()).append(", ");
        }
        observation.deleteCharAt(observation.length() - 1);
        observation.deleteCharAt(observation.length() - 1);
        obj.Graphic.encadrerTexte(observation.toString());
    }

    public void observerSocle() throws InterruptedException {
        obj.Graphic.encadrerTexte("Dans la salle vous voyez un total de " + taille + " socles sur lesquels vous pouvez lire ces descriptions :");
        Thread.sleep(200);

        for (Map.Entry<String, Socle> entry : monEnigme.entrySet()) {
            obj.Graphic.encadrerTexte(entry.getValue().getDescription());
            Thread.sleep(100);

        };
        obj.Graphic.encadrerTexte("Les " + taille + " socles ont chacun une statuette posée sur eux.");

    }

    public void observerEnergie(int tours) throws InterruptedException {
        StringBuilder observation = new StringBuilder();

        if (tours < 2) {
            observation.append("La boule d'énergie crépite doucement. Cette masse electrique vous intrigue. Elle semble vivante et réagit de manière organique. ");
        } else if (correspondances > correspondancesPrecedentes) {
            observation.append("Il vous semble que la boule d'énergie a gagné en intensité, elle vibre d'une énergie plus forte et plus intense. ");
        } else if (correspondances == correspondancesPrecedentes) {
            observation.append("Vous ne pensez pas remarquer de différence de puissance dans la vibration de cette boule d'énergie. ");
        } else if (correspondances < correspondancesPrecedentes) {
            observation.append("Vous croyez remarquer que la boule d'énergie vibre avec moins de force, comme si elle avait perdue de sa lumière. ");
        }

        if (correspondances - correspondancesPrecedentes > 1) {
            observation.append("Un immense sentiment de fierté vous remplit quand vous pensez au dernier échange que vous avez fait. ");
        }

        if (tours > taille && correspondances > taille / 2) {
            observation.append("Malgré votre fatigue, sa vision vous encourage.  ");
        } else if (tours > taille && correspondances < taille / 2) {
            observation.append("Vous êtes desepéré et fatigué, vous avez froid et elle ne dégage pas beaucoup de chaleur..  ");
        } else if (tours < taille) {
            observation.append("Elle vibre différement selon les statuettes que vous touchez.  ");
        }

        if (correspondances >= taille - 2) {
            observation.append("Quelque chose vous dit que vous êtes à deux doigts de finir l'énigme. ");
        } else if (correspondances >= taille - 3) {
            observation.append("Vous vous laissez absorber par la lueur et sentez que vous êtes sur la voie de la solution. ");
        } else if (correspondances >= taille - 4) {
            observation.append("L'air semble se charger d'électricité statique, vous entendez quelques claquements secs. ");
        }

        if (correspondances >= taille / 2) {
            observation.append("La salle est claire et donne un sentiment chaleureux. ");
        } else if (correspondances < taille / 2) {
            observation.append("Il fait assez sombre autour de vous. Vous frisonnez un peu. ");
        }

        if (correspondances <= taille - 5) {
            observation.append("Vous vous sentez enervé et avez envie de tout mélanger. ");
        } else if (correspondances == 1) {
            observation.append("La lueur est très vacillante et semble sur le point de s'éteindre. ");
        } else if (correspondances == 0) {
            observation.append("Désormais, la lueur est si faible qu'elle disparait. ");
        }
        Graphic.animerCadre(Graphic.encadrerTexte(observation.toString()));
    }

    public void majCorrespondances() {
        setCorrespondancesPrecedentes(correspondances);
        setCorrespondances(compterCorrespondances());
    }

    public int compterCorrespondances() {
        int correspondance = 0;
        Iterator<Entry<String, Socle>> i = monEnigme.entrySet().iterator();
        while (i.hasNext()) {
            HashMap.Entry<String, Socle> pair = (Map.Entry<String, Socle>) i.next();
            if (pair.getValue().getNom().equalsIgnoreCase(pair.getValue().getMonBuste().getNom())) {
                correspondance += 1;
            }
        }
        return correspondance;
    }

    public boolean verifier() {
        correspondances = compterCorrespondances();
        boolean resolu = false;
        if ((correspondances) == monEnigme.size()) {
            resolu = true;
        }
        return resolu;
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public void resoudreEnigme(BufferedReader br) throws IOException, InterruptedException {
        int compteurTours = 0;
        obj.Graphic.imprimerTitre();
        Thread.sleep(500);

        commencerEnigme();
        Thread.sleep(1200);

        observerSalle();
        Thread.sleep(700);
        observerEnergie(compteurTours);
        int saisi = 0;

        boolean resolu = false;
        while (resolu == false) {
            compteurTours += 1;
            ecrireMenu();
            try {
                saisi = Integer.parseInt(br.readLine());
            } catch (Exception e) {

            }
            switch (saisi) {
                case 1:
                    echangerBuste(br);
                    majCorrespondances();
                    break;
                case 2:
                    observerSalle();
                    break;
                case 3:
                    observerEnergie(compteurTours);
                    break;
                case 4:
                    observerBuste();
                    break;
                case 5:
                    observerSocle();
                    break;
                case 6:
                    creerEnigme(mesSocles, mesBustes);
                    majCorrespondances();
                    observerSalle();
                    Graphic.animerCadre(Graphic.encadrerTexte("Vous mélangez toutes les statuettes entre elles dans une frénésie presque hystérique, éspérant que, par hasard, vous trouviez des correspondances. Vous devriez aller observer la boule d'énergie."));

                    break;
                case 1337:
                    resolu = true;
                    break;
            }
            if (resolu == false) {
                resolu = verifier();

            }
        }
        finirEnigme(compteurTours);
    }

    public void ecrireMenu() {
        obj.Graphic.imprimerTrait();
        obj.Graphic.imprimerTexte("++ -- Que voulez-vous faire ? -- ++");
        obj.Graphic.imprimerTexte("[1] Echanger deux statuettes");
        obj.Graphic.imprimerTexte("[2] Observer toute la salle");
        obj.Graphic.imprimerTexte("[3] Observer la boule d'energie");
        obj.Graphic.imprimerTexte("[4] Observer les noms des statuettes");
        obj.Graphic.imprimerTexte("[5] Observer les socles");
        obj.Graphic.imprimerTexte("[6] Mélanger les statuettes au hasard");
        obj.Graphic.imprimerTraitBR();
    }

    public void finirEnigme(int c) throws InterruptedException {
        String outro = "La boule d'énergie du centre de la salle se met à briller de plus en plus et les murs commencent à trembler. Les socles tournent sur eux mêmes et s'enfoncent dans le sol, tandis que de l'autre coté vous voyez de la lumière passer entre des pierres qui s'écartent. Soudain la boule d'énergie s'éteint en grand fracas. Une porte est ouverte et vous êtes libres de sortir.";
        String duree = "";
        Thread.sleep(500);
        Graphic.animerCadre(Graphic.encadrerTexte(outro));

        Instant finDujeu = (Instant.now());
        Duration diff = Duration.between(debutDuJeu, finDujeu);
        int heures = (int) diff.abs().toHours();
        if (heures > 0) {
            duree += heures + " heures ";
        }
        duree += diff.abs().toMinutes();
        duree += " minute";
        if (diff.abs().toMinutes() > 1) {
            duree += "s";
        }
        Graphic.imprimerTexte("Enigme finie en " + c + " tours et " + duree);
        Thread.sleep(2000);
    }

    public void commencerEnigme() throws InterruptedException {
        String intro = "Vous entrez dans une grande salle où se trouvent " + taille + " socles qui portent des statuettes. Au centre brille une faible boule d'énergie vacillante. Il vous semble que tout à été mélangé et que vous devriez remettre de l'ordre. D'ailleurs vous ne voyez rien d'autre à faire, puisqu'il n'y a pas de porte.";
        setDebutDuJeu(Instant.now());
        Graphic.animerCadre(Graphic.encadrerTexte(intro));
        Thread.sleep(2000);

        setCorrespondances(compterCorrespondances());
        majCorrespondances();
    }
}
