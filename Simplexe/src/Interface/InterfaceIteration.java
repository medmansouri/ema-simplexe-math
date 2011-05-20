package Interface;

import java.util.*;
import javax.swing.*;

import Ecouteur.EcouteurAccueil;
import Ecouteur.EcouteurContraintes;
import Modele.Matrice;
import Modele.elementMatrice;
import java.awt.BorderLayout;
import java.awt.TextArea;

/*
 * 
 * Classe InterfaceIteration
 * 
 */
public class InterfaceIteration extends JFrame{
	private static final String TITRE = "Tableaux des iterations";
	private static final int W=753, H=600;
	private static final int X=200, Y=120;
        public ArrayList<elementMatrice> pivotIterations = new ArrayList<elementMatrice> ();
        public ArrayList<float[][]> matriceCentreIterations = new ArrayList<float[][]> ();
        public ArrayList<String> matriceNomVariable = new ArrayList<String> ();
        public ArrayList<String> matriceNomVariableBase = new ArrayList<String> ();

	
	public InterfaceIteration(Matrice nouvelleMatrice)
	{
		super();
		build();//On initialise notre fenetre
		//afficheIterations(nouvelleMatrice);
	}
	
	private void build()
	{
		setTitle(TITRE); //On donne un titre � l'application
		setSize(W, H); //On donne une taille � notre fen�tre
		setLocation(X,Y); // On donne une position o� devra apparaite la fen�tre
		TextArea iterationGauche  = new TextArea("Test Gauche",5,50);
                this.getContentPane().add(iterationGauche,BorderLayout.WEST);
                TextArea iterationDroite  = new TextArea("Test Droite",5,50);
                this.getContentPane().add(iterationDroite,BorderLayout.EAST);
                setResizable(true); //On permet le redimensionnement
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit � l'application de se fermer lors du clic sur la croix
		setVisible(true); // On la rend visible
	}
	
	private void afficheIterations(Matrice maMatrice)
	{
	    for (int i=0; i<maMatrice.getMatrice().length ; i++)
	    {
	    	for (int j=0; j<maMatrice.getMatrice()[0].length  ; j++)
	    	{
	    		System.out.print(maMatrice.getMatrice()[i][j]+" ");
	    	}
	        System.out.println();
	    }
	}

        public void setMatriceCentreIterations(ArrayList<float[][]> matriceCentreIterations) {
            this.matriceCentreIterations = matriceCentreIterations;
        }

        public void setMatriceNomVariable(ArrayList<String> matriceNomVariable) {
            this.matriceNomVariable = matriceNomVariable;
        }

        public void setMatriceNomVariableBase(ArrayList<String> matriceNomVariableBase) {
            this.matriceNomVariableBase = matriceNomVariableBase;
        }

        public void setPivotIterations(ArrayList<elementMatrice> pivotIterations) {
            this.pivotIterations = pivotIterations;
        }
}
