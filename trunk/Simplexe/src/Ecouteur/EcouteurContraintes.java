package Ecouteur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Interface.InterfaceAccueil;
import Interface.InterfaceContraintes;

import Interface.InterfaceTableau;
import Modele.Simplexe;
import java.util.ArrayList;

public class EcouteurContraintes implements ActionListener{

	public InterfaceContraintes ihmContraintes;
        private InterfaceTableau ihmTableau;
	private int nbContraintes;
	private int nbVariables;
	private int nbVariablesAuxiliare=0;
        private Simplexe matrice;
        private ArrayList<Integer> tabLigneVariableAuxilliare;
        
	
	public EcouteurContraintes(InterfaceContraintes interfacesContraintes, int nbContraintes, int nbVariables) 
	{
		this.ihmContraintes=interfacesContraintes;
		this.nbContraintes=nbContraintes;
		this.nbVariables=nbVariables;
	}

	public void actionPerformed(ActionEvent e)
	{
		tabLigneVariableAuxilliare = new ArrayList<Integer>();
                for (int i=0; i<ihmContraintes.getTabSigne().size();i++)
                {
                    if(ihmContraintes.getTabSigne().get(i).getSelectedItem() == ">=")
                    {
                        nbVariablesAuxiliare++;
                        tabLigneVariableAuxilliare.add(i);
                    }
                }

                System.out.println("Nombre Variable auxiliare : " + nbVariablesAuxiliare);

                if(nbVariablesAuxiliare==0)
                {
                    matrice = new Simplexe(nbContraintes, nbVariables, ihmContraintes);
                    matrice.creationMatriceNomVariable();
                    matrice.creationMatriceNomVariableBase();
                    matrice.remplirMatrice();
                }
                else
                {
                    matrice = new Simplexe(nbContraintes, nbVariables, nbVariablesAuxiliare,ihmContraintes, this);
                    matrice.remplirMatrice();
                    matrice.afficheMatrice(matrice.getMatrice());
                    
                }
                if(e.getSource()== ihmContraintes.getBoutonMethode1())
                {                   
                    matrice.setNumMethode(1);
                }
                else if(e.getSource() == ihmContraintes.getBoutonMethode2())
                {
                    matrice.setNumMethode(2);                    
                }                           
		                
		this.ihmContraintes.closeContrainte();
		//On affiche une nouvelle fenêtre, où l'utilisateur voient les iterations du problème
		ihmTableau = new InterfaceTableau(matrice);
                
	}

    public ArrayList<Integer> getTabLigneVariableAuxilliare() {
        return tabLigneVariableAuxilliare;
    }


    

        
}
