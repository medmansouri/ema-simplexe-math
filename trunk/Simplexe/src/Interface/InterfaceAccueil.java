package Interface;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Ecouteur.*;

public class InterfaceAccueil extends JFrame{

	public JFrame maFenetre=new JFrame("Simplexe - TOMIO & NAVARRO");
	private JPanel panel=new JPanel();
	private JLabel contrainte=new JLabel("Entrez le nombre de contraintes : ");
	private JLabel variable=new JLabel ("Entrez le nombre de variables : ");
	private JTextField numContraintes=new JTextField();
	private JTextField numVariables=new JTextField();
	public JButton boutonEntrer=new JButton("Entrer");

	public InterfaceAccueil()
	{
		// TODO Auto-generated constructor stub

		panel.setLayout((new GridLayout(3,2)));
		panel.add(contrainte);
		panel.add(numContraintes);
		panel.add(variable);
		panel.add(numVariables);
		panel.add(boutonEntrer);
		maFenetre.add(panel);

		boutonEntrer.addActionListener(new EcouteurAccueil(this));

		maFenetre.pack();
		maFenetre.setVisible(true);
	}

	public void closeAccueil()
	{
		maFenetre.setVisible(false);
	}

	public int getNumContraintes()
	{
		return Integer.parseInt(numContraintes.getText());
	}

	public int getNumVariables()
	{
		return Integer.parseInt(numVariables.getText());
	}



}
