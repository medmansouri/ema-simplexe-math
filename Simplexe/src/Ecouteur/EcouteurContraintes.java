package Ecouteur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Interface.InterfaceAccueil;
import Interface.InterfaceContraintes;
import Interface.InterfaceIteration;
import Modele.Matrice;

public class EcouteurContraintes implements ActionListener{

	public InterfaceContraintes ihmContraintes;
        private InterfaceIteration iteration;
	private int nbContraintes;
	private int nbVariables;
	private int nbVariablesEcart=0;
	private Matrice matrice;
	
	public EcouteurContraintes(InterfaceContraintes interfacesContraintes, int nbContraintes, int nbVariables) 
	{
		this.ihmContraintes=interfacesContraintes;
		this.nbContraintes=nbContraintes;
		this.nbVariables=nbVariables;
	}

	public void actionPerformed(ActionEvent e)
	{
		matrice = new Matrice(nbContraintes, nbVariables, ihmContraintes);

		//matrice.creationMatriceNomVariable();
		//matrice.creationMatriceNomVariableBase();		
                //matrice.creationMatriceNomVariable();
		matrice.remplirMatrice();
		matrice.afficheMatrice();
                //matrice.cherchePivot2();
                //matrice.resolutionProblemeMethode1();
		matrice.resolutionProblemeMethode2();
                

		this.ihmContraintes.closeContrainte();
		//On affiche une nouvelle fenêtre, où l'utilisateur voient les iterations du problème
		iteration = new InterfaceIteration(matrice);
	}
}
