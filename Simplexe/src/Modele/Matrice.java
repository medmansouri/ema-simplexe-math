package Modele;

import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JViewport;

import Interface.InterfaceContraintes;

public class Matrice {
	
	private int nbContraintes;
	private int nbVariables;
	private int nbVariablesEcart;
	private float[][] matrice;
	private String[] matriceNomVariable;
	private String[] matriceNomVariableBase;
	private InterfaceContraintes ihmContrainte;
	private int jMaxDerniereLigne; //Num�ro de colone de la valeur max
	private float pivot;  //Valeur du pivot
	private int iPivot; //Num�ro de ligne du pivot
	private int jPivot; //Num�ro de colone du pivot

	
	public Matrice(int nbContraintes, int nbVariables, InterfaceContraintes ihmContrainte) 
	{
		this.nbContraintes=nbContraintes;
		this.nbVariables=nbVariables;
		this.nbVariablesEcart=nbContraintes;
		this.ihmContrainte=ihmContrainte;
		matrice = new float[nbContraintes+1][nbVariables+nbVariablesEcart+1];
	}
	
	//Remplissage de la matrice avec les valeurs des coefficients + matrice identit� + matrice result + matrice fonction eco
	public void remplirMatrice()
	{
		int i=0;
		while (i<ihmContrainte.getTabVariables().size())
		{
			for(int j=0;j<nbContraintes;j++)
			{
				for(int k=0;k<nbVariables;k++)
				{
					this.matrice[j][k]=Integer.parseInt(ihmContrainte.getTabVariables().get(i).getText());
					i++;
				}
			}
		}
		
		//On ajoute la matrice identit� a la matrice des coefficients		
		float matriceIdentite[][] = new float[nbVariablesEcart][nbVariablesEcart];
		matriceIdentite = creationMatriceIdentite();
		for(i=0; i<matriceIdentite.length;i++)
		{
			for(int j=0; j<matriceIdentite.length;j++)
			{
				matrice[i][nbVariables+j]=matriceIdentite[i][j];	
				
			}			
		}
		
		//Ajout de la matrice maximisant
		float matriceResult[] = creationMatriceMaximisant();
		for(i=0;i<matriceResult.length;i++)
		{
			matrice[i][matrice[0].length-1]=matriceResult[i];
		}
		
		//Ajout de la matrice de la fonction �conomique
		float matriceFonctionEco[] = creationMatriceFonctionEco();
		for(i=0;i<matriceFonctionEco.length;i++)
		{
			matrice[matrice.length-1][i]=matriceFonctionEco[i];
		}		
	}

	//Fonction pour resoudre le probleme par la premiere methode
	public void resolutionProbleme()
	{
		float max = chercheMax();
		if(max > 0)
		{
			cherchePivot();
			soustractionLigne();
			divisionLignePivot();
			changementVariableBase();
			afficheMatrice();			
		}
		System.out.println("Fin du probleme");
		
	}
	
	//Cr�ation de la matrice identit�
	public float[][] creationMatriceIdentite()
	{
	    float matriceIdentite[][] = new float[nbVariablesEcart][nbVariablesEcart];

	    for (int i=0; i<nbVariablesEcart ; i++)
	    {
	      for (int j=0; j<nbVariablesEcart; j++)
	      {
	    	  if (i==j) 	      
	    		  matriceIdentite[i][j]=1;
	    	  else
	    		  matriceIdentite[i][j]=0;
	      }
	    }
	     System.out.println("matrice identit� :");
	    for (int i=0; i<matriceIdentite.length ; i++)
	    {
	    	for (int j=0; j<matriceIdentite[0].length  ; j++)
	    	{
	    		System.out.print(matriceIdentite[i][j]+" ");
	    	}
	        System.out.println();
	    }
	    
	    return matriceIdentite;
	  }
	
	//Creation de la matrice result
	public float[] creationMatriceMaximisant()
	{
		float matriceResult[] = new float[nbContraintes];
		
		for(int i=0;i<nbContraintes;i++)
		{
				matriceResult[i]=Integer.parseInt(ihmContrainte.getTabMaximisant().get(i).getText());					
		}
		for(int i=0;i<nbContraintes;i++)
		{
				System.out.println(matriceResult[i]);
		}
				
		return matriceResult;
	}
	
	//Cr�ation de la matrice de la fonction �conomique
	public float[] creationMatriceFonctionEco()
	{
		float matriceFonctionEco[] = new float[matrice[0].length];

		for(int i=0; i<nbVariables; i++)
		{
			matriceFonctionEco[i]=Integer.parseInt(ihmContrainte.getTabFonctionEco().get(i).getText());
		}
		for (int j=nbVariables;j<matrice[0].length-1;j++)
		{
			matriceFonctionEco[j]=0;
		}
		
		for(int k=0;k<matriceFonctionEco.length;k++)
		{
				System.out.println(matriceFonctionEco[k]);
		}
		
		return matriceFonctionEco;
	}
		
	//Cr�ation nom variable valeur d'�cart	
	public String[] creationMatriceNomVariableEcart()
	{
		String[] matriceNomVariableEcart = new String[nbVariablesEcart];
		String nomVariableEcart;
		
		for (int i=0;i<nbVariablesEcart;i++)
		{
			nomVariableEcart="u"+i;
			matriceNomVariableEcart[i]=nomVariableEcart;
		}
		
		return matriceNomVariableEcart;
	}
	
	//Cr�ation de la matrice avec les noms de variables
	public void creationMatriceNomVariable()
	{
		matriceNomVariable = new String[matrice[0].length-1];
		String[] matriceNomVariableEcart = creationMatriceNomVariableEcart();
		
		//Ajout des noms des variables
		for(int i=0;i<ihmContrainte.getTabNomVariable().size();i++)
		{
			matriceNomVariable[i]=ihmContrainte.getTabNomVariable().get(i).getText();
		}		
		
		//Ajout des noms des variables d'�cart
		int i=0;
		for(int j=ihmContrainte.getTabNomVariable().size();j<matriceNomVariable.length;j++)
		{
			matriceNomVariable[j]=matriceNomVariableEcart[i];
			i++;
		}
				
		for(int k=0;k<matrice[0].length-1;k++)
		{
			System.out.println("Nom de variable : " + matriceNomVariable[k]);
		}
	}
	
	//Cr�ation de la matrice avec les noms de variable appartenant � la base
	public void creationMatriceNomVariableBase()
	{
		matriceNomVariableBase = creationMatriceNomVariableEcart();
		System.out.println("Matrice des variables de bases : ");
		for(int k=0;k<matriceNomVariableBase.length;k++)
		{
			System.out.println("Nom de variable : " + matriceNomVariableBase[k]);
		}
	}
		
	//Fonction pour afficher la matrice
	public void afficheMatrice()
	{		
	    for (int i=0; i<matrice.length ; i++)
	    {
	    	for (int j=0; j<matrice[0].length  ; j++)
	    	{
	    		System.out.print(matrice[i][j]+" ");
	    	}
	        System.out.println();
	    }
	}
	
	//Cherche le maximum sur la derni�re ligne
	public float chercheMax()
	{
		float maximum = 0;		
		int tailleMatrice = matrice.length;
		
		for(int j=0;j<matrice[0].length;j++)
		{
			if (matrice[tailleMatrice-1][j] > maximum)
			{
				maximum = matrice[tailleMatrice-1][j];
				jMaxDerniereLigne=j;
			}
		}
		System.out.println("La colone est : " + jMaxDerniereLigne);
		return maximum;
	}
	
	//Cherche le pivot 1ere methode
	public void cherchePivot()
	{
		float calculPivot;
		float calcul;
		calculPivot = (float)(matrice[0][matrice[0].length-1])/(matrice[0][jMaxDerniereLigne]);
		pivot=matrice[0][jMaxDerniereLigne];
		iPivot=0;
		jPivot=jMaxDerniereLigne;
		for (int i=1;i<matrice.length-1;i++)
		{
			calcul= (float)(matrice[i][matrice[0].length-1])/(matrice[i][jMaxDerniereLigne]);
			System.out.println("Calcul :" + calcul);
			if(calcul<calculPivot)
			{
				calculPivot=calcul;
				pivot=matrice[i][jMaxDerniereLigne];
				iPivot=i;
				jPivot=jMaxDerniereLigne;
			}
		}
		System.out.println("Pivot : " + pivot);
		System.out.println("iPivot : " + iPivot);
		System.out.println("jPivot : " + jPivot);
	}

        //Fonction qui cherche le pivot pour la deuxième méthode
        public void cherchePivot2()
        {
            float iPivotTemp;
            float jPivotTemp;

            for (int i=0; i<matrice.length;i++)
            {
                for (int j=0; j<matrice[0].length;j++)
                {

                }
            }
        }
	
	//Fonction qui divise la ligne du pivot par la valeur du pivot
	public void divisionLignePivot()
	{
		for(int j=0; j<matrice[0].length;j++)
		{
			matrice[iPivot][j]=(matrice[iPivot][j])/pivot;
		}
	}
	
	//Fonction qui soustrait chaque ligne � la ligne du pivot multipli� par un coefficient
	public void soustractionLigne()
	{
		for (int i=0;i<matrice.length;i++)
		{
			if(i!=iPivot)
			{
				float coeffSoustraction = (matrice[i][jPivot])/pivot;
				System.out.println("Le coefficient de soustraction est :"+coeffSoustraction);
				for (int j=0;j<matrice[0].length;j++)
				{
					System.out.println("L'�lement � soustraire est : " + matrice[iPivot][j]);
					matrice[i][j]= (matrice[i][j])-(coeffSoustraction*matrice[iPivot][j]);
				}
			}
		}
	}
	
	//Fonction qui fait entrer un nom de variable dans la base
	public void changementVariableBase()
	{
		matriceNomVariableBase[iPivot]=matriceNomVariable[jPivot];
	}
}
