package Ecouteur;

import Interface.InterfaceAccueil;
import Interface.InterfaceContraintes;

import Interface.InterfaceTableau;
import Modele.Matrice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurTableau implements ActionListener{

        private Matrice matrice;
      private InterfaceTableau ihmTableau;
      private InterfaceAccueil ihmAccueil;
           


	public EcouteurTableau(InterfaceTableau ihmTableau, Matrice matrice)
	{
            this.ihmTableau = ihmTableau;
            this.matrice = matrice;
          
            
	}

	public void actionPerformed(ActionEvent e)
	{
            if (e.getSource() == ihmTableau.getBoutonNouveau())
            {
                this.ihmTableau.closeTableau();
                ihmAccueil = new InterfaceAccueil();
            } 
            else
            {
                float max = matrice.chercheMax();
                if (max > 0)
                {

                    if (matrice.getNumMethode() == 1)
                    {

                        matrice.resolutionProblemeMethode1();
                    }
                    else if (matrice.getNumMethode() == 2) {
                        matrice.resolutionProblemeMethode2();
                    }
                     this.ihmTableau.closeTableau();
                    
                    ihmTableau = new InterfaceTableau(matrice);              


                } else
                {
                    ihmTableau.affichePopUp();
                }
            }           
	
	}
}