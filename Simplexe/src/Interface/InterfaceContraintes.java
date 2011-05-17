package Interface;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Ecouteur.EcouteurAccueil;
import Ecouteur.EcouteurContraintes;

public class InterfaceContraintes extends JFrame{

	private int nbContraintes;
	private int nbVariables;
	private ArrayList<JTextField> tabVariables;
	private ArrayList<JTextField> tabMaximisant;
	private ArrayList<JTextField> tabFonctionEco;
	private ArrayList<JLabel> tabNomVariable;
	private ArrayList<JComboBox> tabSigne;
	
	private JButton boutonEntrer=new JButton("Entrer");
	
	
	public InterfaceContraintes(int nbContraintes, int nbVariables) 
	{
		this.nbContraintes=nbContraintes;
		this.nbVariables=nbVariables;
		constructionAffichage();
	}
	
	private void constructionAffichage()
	{
		tabVariables = new ArrayList<JTextField>();
		tabMaximisant = new ArrayList<JTextField>();
		tabFonctionEco = new ArrayList<JTextField>();
		tabNomVariable = new ArrayList<JLabel>();
		tabSigne = new ArrayList<JComboBox>();
		
		//Pannel Nord
		JPanel panelNord = new JPanel();
		JLabel variableFonctionEco;
		JTextField valeurVariableFonctionEco;
		JLabel fonctionEco = new JLabel("[MAX]Z = ");
		panelNord.setLayout(new GridLayout(1, nbVariables+1));
		panelNord.add(fonctionEco);
		
		for(int k=0;k<nbVariables;k++)
		{
			variableFonctionEco = new JLabel("X"+k);
			valeurVariableFonctionEco= new JTextField();
			tabNomVariable.add(variableFonctionEco);
			tabFonctionEco.add(valeurVariableFonctionEco);
			panelNord.add(valeurVariableFonctionEco);
			panelNord.add(variableFonctionEco);
		}
				
		//Pannel centre
		JPanel panelCentre = new JPanel();
		panelCentre.setLayout((new GridLayout(nbContraintes,nbVariables+2)));
		JLabel variables;
		JTextField valeurVariables;
		JTextField result;
		JComboBox signe;
		
		
		for(int i=0;i<nbContraintes;i++)
		{
			for(int j=0;j<nbVariables;j++)
			{
				variables = new JLabel("X"+j);
				valeurVariables = new JTextField();				
				tabVariables.add(valeurVariables);
				panelCentre.add(valeurVariables);
				panelCentre.add(variables);
			}
			result = new JTextField();
			signe = new JComboBox();
			signe.addItem(">=");
			signe.addItem("=");
			signe.addItem("<=");
			tabSigne.add(signe);
			tabMaximisant.add(result);
			panelCentre.add(signe);
			panelCentre.add(result);
			
		}
		 
		this.getContentPane().add(panelNord,BorderLayout.NORTH);
		this.getContentPane().add(panelCentre,BorderLayout.CENTER);
		this.getContentPane().add(boutonEntrer,BorderLayout.SOUTH);
		boutonEntrer.addActionListener(new EcouteurContraintes(this, nbContraintes, nbVariables));
		this.pack();
		this.setVisible(true);
	}

	public ArrayList<JTextField> getTabVariables() 
	{
		return tabVariables;
	}
	
	public ArrayList<JTextField> getTabMaximisant() 
	{
		return tabMaximisant;
	}
	
	public ArrayList<JTextField> getTabFonctionEco() 
	{
		return tabFonctionEco;
	}
	
	public ArrayList<JLabel> getTabNomVariable() 
	{
		return tabNomVariable;
	}
	
	public ArrayList<JComboBox> getTabSigne() 
	{
		return tabSigne;
	}
}
