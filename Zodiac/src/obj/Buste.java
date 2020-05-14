/*
 * Ni aimer, ni haïr : voilà la moitié de toute sagesse.  
 * Ne rien dire et ne rien croire : voilà l'autre.
 */
package obj;

/**
 * JavaDoc de la classe Bustes
 *
 * @author cda413 --> Maxime J. Richard @ CDA1910.313
 */
public class Buste implements obj.Graphic {

    private String art;
    private String nom;

    public Buste() {
    }

    public Buste(String nom) {
        setArt(nom);
        setNom(nom);
    }
    
    public Buste(String art, String nom) {
        this.art = art;
        this.nom = nom;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        String objArt = "Assets.Buste_tokens."+art+"Img";
        this.art = objArt;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        //this.nom = "Assets.Buste_tokens."+nom;
        this.nom = nom;
    }

    public void seDecrire() {
        //TO DO
        if (!(getArt().equals(getNom()))){
                    System.out.println(Graphic.imprimerTrait());
        System.out.println(art);
        }

        System.out.println(Graphic.imprimerTrait());
        System.out.println(nom);
        System.out.println(Graphic.imprimerTrait());

    }
}
