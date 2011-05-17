package Ecouteur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Interface.InterfaceAccueil;
import Interface.InterfaceContraintes;
import Modele.Matrice;

public class EcouteurContraintes implements ActionListener{

	public InterfaceContraintes ihmContraintes;
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
		for(int i=0;i<ihmContraintes.getTabSigne().size();i++)
		{
			if(ihmContraintes.getTabSigne().get(i).getSelectedItem()=="<=" || ihmContraintes.getTabSigne().get(i).getSelectedItem()==">=")
			{
				nbVariablesEcart++;
			}
		}
		
		System.out.println("Nombre variables d'écart" + nbVariablesEcart);
	
		
		
		matrice = new Matrice(nbContraintes, nbVariables, nbVariablesEcart,ihmContraintes);
		//matrice.creationMatriceNomVariable();
		//matrice.creationMatriceNomVariableBase();
		matrice.remplirMatrice();
		matrice.afficheMatrice();
		//matrice.resolutionProbleme();
		

				
	}

}
