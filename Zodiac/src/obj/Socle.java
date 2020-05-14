/*
 * Ni aimer, ni haïr : voilà la moitié de toute sagesse.  
 * Ne rien dire et ne rien croire : voilà l'autre.
 */

package obj;

import static obj.Graphic.LONGUEURTRAIT;

/**
 * JavaDoc de la classe Socle
 * @author cda413 --> Maxime J. Richard @ CDA1910.313
 */
public class Socle {
    
    private String description;
    private String nom;
    private Buste monBuste;

    public Socle() {
    }

    public Socle(String nom, Buste monBuste) {
        this.description = description;
        this.nom = nom;
        this.monBuste = monBuste;
    }

    public Socle(String nom, String description) {
        this.description = description;
        this.nom = nom;
        this.monBuste = monBuste;
    }
    
    public Socle(String description, String nom, Buste monBuste) {
        this.description = description;
        this.nom = nom;
        this.monBuste = monBuste;
    }

    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Buste getMonBuste() {
        return monBuste;
    }

    public void setMonBuste(Buste monBuste) {
        this.monBuste = monBuste;
    }
        
    
public void seDecrire(){
    
        System.out.println(Graphic.imprimerTrait());
        System.out.println(description);
        System.out.println(Graphic.imprimerTrait());

}    

}
