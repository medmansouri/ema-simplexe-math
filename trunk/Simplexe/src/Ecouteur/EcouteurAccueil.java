package Ecouteur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Interface.*;

public class EcouteurAccueil implements ActionListener{
	
	private InterfaceAccueil accueil;
	private InterfaceContraintes contraintes;
	
	public EcouteurAccueil(InterfaceAccueil accueil) {
		// TODO Auto-generated constructor stub
		this.accueil=accueil;		
	}

       public void actionPerformed(ActionEvent e)
	{
		//On récupe le nombre de contraintes et de variables saisies par l'utilisateur
		int nbContraintes=(accueil.getNumContraintes());
		int nbVariables=(accueil.getNumVariables());
		System.out.println("Nombre de contraintes : " + nbContraintes);
		System.out.println("Nombre de variables : " + nbVariables);
		// On ferme la fenêtre accueil ...
		this.accueil.closeAccueil();
		//On affiche une nouvelle fenêtre, pour que l'utilisateur rentre les coefficients des variables
		contraintes = new InterfaceContraintes(nbContraintes, nbVariables);

	}
}
