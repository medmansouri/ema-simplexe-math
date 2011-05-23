/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modele;

/**
 *
 * @author Nicolas
 */
public class Matrice {

    private float[][] matrice;
    private int nbLignes = 0;
    private int nbColonnes = 0;

    public Matrice(int nbLignes, int nbColonnes)
    {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.matrice=new float[nbLignes][nbColonnes];
    }

    public float[][] getMatrice() {
        return matrice;
    }

    public int getNbColonnes() {
        return nbColonnes;
    }

    public int getNbLignes() {
        return nbLignes;
    }

    public void setMatrice(float[][] matrice) {
        this.matrice = matrice;
    }

    public void setNbColonnes(int nbColonnes) {
        this.nbColonnes = nbColonnes;
    }

    public void setNbLignes(int nbLignes) {
        this.nbLignes = nbLignes;
    }

    




}
