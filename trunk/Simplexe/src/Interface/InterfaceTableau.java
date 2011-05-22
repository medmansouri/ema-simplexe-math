package Interface;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.text.NumberFormat;

import Ecouteur.*;
import Modele.Matrice;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class InterfaceTableau extends JFrame{


        private Matrice matrice;
	private JButton boutonSuite=new JButton("Iteration suivante");
   	public JFrame maFenetre=new JFrame("Simplexe - TOMIO & NAVARRO");
        private InterfaceContraintes ihmContraintes;

	public InterfaceTableau(Matrice matrice, InterfaceContraintes ihmContraintes)
	{
                this.matrice = matrice;
                this.ihmContraintes = ihmContraintes;
		constructionAffichage();
	}

        public InterfaceTableau(Matrice matrice)
        {
            this.matrice=matrice;
            constructionAffichage();
        }

        private void constructionAffichage()
        {


            //Pannel Nord
            JPanel panelNord = new JPanel();
            JLabel nomVariable = new JLabel("  ");
            panelNord.add(nomVariable);
            panelNord.setLayout(new GridLayout(1, matrice.getMatriceNomVariable().length));
           
            for(int k=0;k<matrice.getMatriceNomVariable().length;k++)
            {
                nomVariable = new JLabel(matrice.getMatriceNomVariable()[k]);
                panelNord.add(nomVariable);
            }

            //Pannel Ouest
            /*JPanel panelOuest = new JPanel();
            JLabel nomVariableBase;
            panelNord.setLayout(new GridLayout(matrice.getMatriceNomVariableBase().length, 1));

            for(int k=0;k<matrice.getMatriceNomVariableBase().length;k++)
            {
                nomVariableBase = new JLabel(matrice.getMatriceNomVariableBase()[k]);
                panelOuest.add(nomVariableBase);
            }*/
            
            //Pannel centre
            JTable maJTable = new JTable();
            JPanel panelCentre = new JPanel();
            Object[][] contenu = new Object[matrice.getMatrice().length+1][matrice.getMatrice()[0].length+1];

           //On remplit le contenu
            for (int i=0; i<matrice.getMatriceNomVariable().length;i++)
            {
                contenu[0][i+1] = matrice.getMatriceNomVariable()[i];
            }
            for (int j=0; j<matrice.getMatriceNomVariableBase().length;j++)
            {
                contenu[j+1][0] = matrice.getMatriceNomVariableBase()[j];
            }
            for(int i=0; i<matrice.getMatrice().length;i++)
            {
                for(int j=0; j<matrice.getMatrice()[0].length;j++)
                {
                    contenu[i+1][j+1] = matrice.getMatrice()[i][j];
                }
            }
         
            String[] header = new String[matrice.getMatriceNomVariable().length+2];
            header[0] = "colonne1"; // etc
            maJTable.setModel(new DefaultTableModel(contenu,header));
            panelCentre.add(maJTable);
            
            //panel sud
            JPanel panelSud = new JPanel();
            
            panelSud.add(boutonSuite);
            
            
            //maFenetre.add(panelNord,BorderLayout.NORTH);
            //maFenetre.add(panelOuest,BorderLayout.WEST);
            maFenetre.add(panelCentre,BorderLayout.CENTER);
            maFenetre.add(panelSud,BorderLayout.SOUTH);
          
            boutonSuite.addActionListener(new EcouteurTableau(this, matrice, ihmContraintes));
            //boutonMethode2.addActionListener(new EcouteurContraintes(this, nbContraintes, nbVariables));
            maFenetre.pack();
            maFenetre.setVisible(true);
        }


	public void closeTableau()
	{
		maFenetre.setVisible(false);
	}



}
