/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

/**
 *
 * @author Nicolas
 */
public class elementMatrice {
    
    private int ligne;
    private int colonne;
    private float valeur;

    public elementMatrice(int ligne, int colonne) {
        this.ligne = ligne;
        this.colonne = colonne;
    }

    public elementMatrice() {
    }
        

    public int getColonne() {
        return colonne;
    }

    public int getLigne() {
        return ligne;
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    public void setValeur(float valeur) {
        this.valeur = valeur;
    }

    public float getValeur() {
        return valeur;
    }
    
    
            
}
