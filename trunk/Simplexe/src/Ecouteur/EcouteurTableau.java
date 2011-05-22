package Ecouteur;

import Interface.InterfaceContraintes;

import Interface.InterfaceTableau;
import Modele.Matrice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurTableau implements ActionListener{

        private Matrice matrice;
      private InterfaceTableau ihmTableau;
           


	public EcouteurTableau(InterfaceTableau ihmTableau, Matrice matrice)
	{
            this.ihmTableau = ihmTableau;
            this.matrice = matrice;
          
            
	}

	public void actionPerformed(ActionEvent e)
	{
            float max = matrice.chercheMax();
            if (max > 0)
            {
                if (matrice.getNumMethode() == 1)
                {
                    matrice.resolutionProblemeMethode1();
                } 
                else if (matrice.getNumMethode() == 2)
                {
                    matrice.resolutionProblemeMethode2();
                }
                this.ihmTableau.closeTableau();
		//On affiche une nouvelle fenêtre, où l'utilisateur voient les iterations du problème
		//iteration = new InterfaceIteration(matrice);
                ihmTableau = new InterfaceTableau(matrice);
            }
            else
            {
                ihmTableau.affichePopUp();
            }
           
	
	}
}