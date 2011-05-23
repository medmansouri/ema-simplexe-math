package Interface;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import Ecouteur.*;
import Modele.Simplexe;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class InterfaceTableau extends JFrame{


        private Simplexe matrice;
	private JButton boutonSuite=new JButton("Iteration suivante");
        private JButton boutonNouveau = new JButton("Nouveau problème");
        private JButton boutonAutreMethode = new JButton("Résoudre le même problème avec une autre méthode");
   	public JFrame maFenetre=new JFrame("Simplexe - TOMIO & NAVARRO");
        private JTextArea affichage;
       

        public InterfaceTableau(Simplexe matrice)
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
            Object[][] contenu = new Object[matrice.getMatrice().getNbLignes()+1][matrice.getMatrice().getNbColonnes()+1];

           //On remplit le contenu
            for (int i=0; i<matrice.getMatriceNomVariable().length;i++)
            {
                contenu[0][i+1] = matrice.getMatriceNomVariable()[i];
            }
            for (int j=0; j<matrice.getMatriceNomVariableBase().length;j++)
            {
                contenu[j+1][0] = matrice.getMatriceNomVariableBase()[j];
            }
            for(int i=0; i<matrice.getMatrice().getMatrice().length;i++)
            {
                for(int j=0; j<matrice.getMatrice().getMatrice()[0].length;j++)
                {
                    contenu[i+1][j+1] = matrice.getMatrice().getMatrice()[i][j];
                }
            }         
            String[] header = new String[matrice.getMatriceNomVariable().length+2];
            maJTable.setModel(new DefaultTableModel(contenu,header));
            panelCentre.add(maJTable);
            
            //panel sud
            JPanel panelSud = new JPanel();
            panelSud.setLayout((new GridLayout(1,3)));
            boutonAutreMethode.setVisible(false);
            panelSud.add(boutonSuite);
            panelSud.add(boutonAutreMethode);
            panelSud.add(boutonNouveau);
          
            
            //Panel est
            JPanel panelEst = new JPanel();
            if(matrice.chercheMax(matrice.getMatrice()) > 0)
            {
                affichage=new JTextArea(matrice.toString());
            }
            else
            {
                affichage = new JTextArea(matrice.toStringFin());
                boutonSuite.setVisible(false);
                boutonAutreMethode.setVisible(true);
            }
            affichage.setPreferredSize(new Dimension(300,250));
            panelEst.add(affichage);
            
            //Ajout de panel a la fenêtre
            maFenetre.add(panelNord,BorderLayout.NORTH);
            maFenetre.add(panelEst,BorderLayout.EAST);
            maFenetre.add(panelCentre,BorderLayout.CENTER);
            maFenetre.add(panelSud,BorderLayout.SOUTH);

            //Déclaraation des écouteurs
            EcouteurTableau ecouteur = new EcouteurTableau(this, matrice);
            boutonSuite.addActionListener(ecouteur);
            boutonNouveau.addActionListener(ecouteur);
            boutonAutreMethode.addActionListener(ecouteur);

            //On affiche la fenêtre
            maFenetre.pack();
            maFenetre.setVisible(true);
        }


	public void closeTableau()
	{
		maFenetre.setVisible(false);
	}


    public JTextArea getAffichage() {
        return affichage;
    }

    public JButton getBoutonNouveau() {
        return boutonNouveau;
    }

    public JButton getBoutonSuite() {
        return boutonSuite;
    }

    public JButton getBoutonAutreMethode() {
        return boutonAutreMethode;
    }

    

    

    

        






}
