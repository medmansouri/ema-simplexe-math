package Interface;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import Ecouteur.*;
import Modele.Matrice;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class InterfaceTableau extends JFrame{


        private Matrice matrice;
	private JButton boutonSuite=new JButton("Iteration suivante");
   	public JFrame maFenetre=new JFrame("Simplexe - TOMIO & NAVARRO");
        private JTextArea affichage;
       

        public InterfaceTableau(Matrice matrice)
        {
            this.matrice=matrice;
            constructionAffichage();
        }

        private void constructionAffichage()
        {


            //Pannel Nord
            JPanel panelNord = new JPanel();
            JLabel titre = new JLabel("Itération " + matrice.getNbIteration());
            panelNord.add(titre);


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

            //Panel est
            JPanel panelEst = new JPanel();
            affichage=new JTextArea(matrice.toString());
            affichage.setPreferredSize(new Dimension(300,250));
            panelEst.add(affichage);
            
            maFenetre.add(panelNord,BorderLayout.NORTH);
            maFenetre.add(panelEst,BorderLayout.EAST);
            maFenetre.add(panelCentre,BorderLayout.CENTER);
            maFenetre.add(panelSud,BorderLayout.SOUTH);
          
            boutonSuite.addActionListener(new EcouteurTableau(this, matrice));

            maFenetre.pack();
            maFenetre.setVisible(true);
        }


	public void closeTableau()
	{
		maFenetre.setVisible(false);
	}

        public void affichePopUp()
        {
            JOptionPane.showMessageDialog(this, "Plus d'itération possible");
        }






}
