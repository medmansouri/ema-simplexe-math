package Ecouteur;

import Interface.InterfaceContraintes;

import Interface.InterfaceTableau;
import Modele.Matrice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurTableau implements ActionListener{

        private Matrice matrice;
        private InterfaceContraintes ihmContraintes;
        private InterfaceTableau ihmTableau;


	public EcouteurTableau(InterfaceTableau ihmTableau, Matrice matrice, InterfaceContraintes ihmContraintes)
	{
            this.ihmTableau = ihmTableau;
            this.matrice = matrice;
            this.ihmContraintes = ihmContraintes;
	}

	public void actionPerformed(ActionEvent e)
	{
               if(e.getSource()== ihmContraintes.getBoutonMethode1())
                {
                    System.out.println("Resolution probleme 1");
                    matrice.resolutionProblemeMethode1();
                }
                else if(e.getSource() == ihmContraintes.getBoutonMethode2())
                {
                    matrice.resolutionProblemeMethode2();
                }
	

		this.ihmContraintes.closeContrainte();
		//On affiche une nouvelle fenêtre, où l'utilisateur voient les iterations du problème
		//iteration = new InterfaceIteration(matrice);
                ihmTableau = new InterfaceTableau(matrice);
	}
}