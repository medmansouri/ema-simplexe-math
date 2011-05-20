package Interface;

import java.util.*;
import javax.swing.*;

import Ecouteur.EcouteurAccueil;
import Ecouteur.EcouteurContraintes;
import Modele.Matrice;

/*
 * 
 * Classe InterfaceIteration
 * 
 */
public class InterfaceIteration extends JFrame{
	private static final String TITRE = "Tableaux des iterations";
	private static final int W=358, H=300;
	private static final int X=200, Y=120;
	
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
}
