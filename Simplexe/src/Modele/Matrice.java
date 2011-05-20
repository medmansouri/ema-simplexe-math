package Modele;

import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JViewport;

import Interface.InterfaceContraintes;

public class Matrice {
	
	private int nbContraintes;
	private int nbVariables;
	private int nbVariablesEcart;
        private float[][] matriceDepart;
	private float[][] matrice;
	private String[] matriceNomVariable;
	private String[] matriceNomVariableBase;
	private InterfaceContraintes ihmContrainte;
	private int jMaxDerniereLigne; //Numero de colone de la valeur max
        
	private elementMatrice pivot = new elementMatrice();


	
	public Matrice(int nbContraintes, int nbVariables, InterfaceContraintes ihmContrainte) 
	{
		this.nbContraintes=nbContraintes;
		this.nbVariables=nbVariables;
		this.nbVariablesEcart=nbContraintes;
		this.ihmContrainte=ihmContrainte;
		matriceDepart = new float[nbContraintes+1][nbVariables+nbVariablesEcart+1];
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
					this.matriceDepart[j][k]=Integer.parseInt(ihmContrainte.getTabVariables().get(i).getText());
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
				matriceDepart[i][nbVariables+j]=matriceIdentite[i][j];	
				
			}			
		}
		
		//Ajout de la matrice maximisant
		float matriceResult[] = creationMatriceMaximisant();
		for(i=0;i<matriceResult.length;i++)
		{
			matriceDepart[i][matriceDepart[0].length-1]=matriceResult[i];
		}
		
		//Ajout de la matrice de la fonction �conomique
		float matriceFonctionEco[] = creationMatriceFonctionEco();
		for(i=0;i<matriceFonctionEco.length;i++)
		{
			matriceDepart[matriceDepart.length-1][i]=matriceFonctionEco[i];
		}
                this.matrice = this.matriceDepart;
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
	     System.out.println("matrice identite :");
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
		float matriceFonctionEco[] = new float[matriceDepart[0].length];

		for(int i=0; i<nbVariables; i++)
		{
			matriceFonctionEco[i]=Integer.parseInt(ihmContrainte.getTabFonctionEco().get(i).getText());
		}
		for (int j=nbVariables;j<matriceDepart[0].length-1;j++)
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
		matriceNomVariable = new String[matriceDepart[0].length-1];
		String[] matriceNomVariableEcart = creationMatriceNomVariableEcart();
		
		//Ajout des noms des variables
		for(int i=0;i<ihmContrainte.getTabNomVariable().size();i++)
		{
			matriceNomVariable[i]=ihmContrainte.getTabNomVariable().get(i).getText();
		}		
		
		//Ajout des noms des variables d'ecart
		int i=0;
		for(int j=ihmContrainte.getTabNomVariable().size();j<matriceNomVariable.length;j++)
		{
			matriceNomVariable[j]=matriceNomVariableEcart[i];
			i++;
		}
				
		for(int k=0;k<matriceDepart[0].length-1;k++)
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
		pivot.setValeur(matrice[0][jMaxDerniereLigne]);
                pivot.setLigne(0);
		pivot.setColonne(jMaxDerniereLigne);
		for (int i=1;i<matrice.length-1;i++)
		{
			calcul= (float)(matrice[i][matrice[0].length-1])/(matrice[i][jMaxDerniereLigne]);
			System.out.println("Calcul :" + calcul);
			if(calcul<calculPivot)
			{
				calculPivot=calcul;
				pivot.setValeur(matrice[i][jMaxDerniereLigne]);
				pivot.setLigne(i);
				pivot.setColonne(jMaxDerniereLigne);
			}
		}
		System.out.println("Pivot : " + pivot.getValeur());
		System.out.println("iPivot : " + pivot.getLigne());
		System.out.println("jPivot : " + pivot.getColonne());
	}

        //Fonction qui cherche le pivot pour la deuxième méthode
        public void cherchePivot2()
        {
            float iPivotTemp;
            float jPivotTemp;
            float valeurRapport;
            float valeurTemp;
            float valeurMultiplie;
            
            
            for (int j=0; j<matrice[0].length;j++)
            {
                if (matrice[matrice.length-1][j] > 0)
                {
                    valeurRapport = matrice[0][matrice[0].length-1] / matrice[0][j];
                    valeurTemp = matrice[0][j];
                    valeurMultiplie = valeurTemp*matrice[matrice.length-1][j];
                    iPivotTemp=0;
                    jPivotTemp=j;
                    for (int i=1; i<matrice.length;i++)
                    {
                        if(matrice[i][matrice[0].length-1] / matrice[i][j] < valeurRapport)
                        {
                           valeurRapport = matrice[i][matrice[0].length-1] / matrice[i][j];
                           valeurTemp = matrice[i][j];
                           valeurMultiplie = valeurTemp*matrice[matrice.length-1][j];
                           iPivotTemp=i;
                           jPivotTemp=j;
                        }
                    }
                    
                }
            }
        }
	
	//Fonction qui divise la ligne du pivot par la valeur du pivot
	public void divisionLignePivot()
	{
		for(int j=0; j<matrice[0].length;j++)
		{
			matrice[pivot.getLigne()][j]=(matrice[pivot.getLigne()][j])/pivot.getValeur();
		}
	}
	
	//Fonction qui soustrait chaque ligne a la ligne du pivot multiplié par un coefficient
	public void soustractionLigne()
	{
		for (int i=0;i<matrice.length;i++)
		{
			if(i!=pivot.getLigne())
			{
				float coeffSoustraction = (matrice[i][pivot.getColonne()])/pivot.getValeur();
				System.out.println("Le coefficient de soustraction est :"+coeffSoustraction);
				for (int j=0;j<matrice[0].length;j++)
				{
					System.out.println("L'element a soustraire est : " + matrice[pivot.getLigne()][j]);
					matrice[i][j]= (matrice[i][j])-(coeffSoustraction*matrice[pivot.getLigne()][j]);
				}
			}
		}
	}
	
	//Fonction qui fait entrer un nom de variable dans la base
	public void changementVariableBase()
	{
            System.out.println("Changement variable de base : ");
            System.out.println("Ligne du pivot " + pivot.getLigne());
            System.out.println("Colonne du pivot " + pivot.getColonne());
            System.out.println("Variable de base : " + matriceNomVariableBase[pivot.getLigne()]);
            matriceNomVariableBase[pivot.getLigne()]=matriceNomVariable[pivot.getColonne()];
            System.out.println("Variable de base : " + matriceNomVariableBase[pivot.getLigne()]);
	}

        /**
	 * Getteur matrice
	 */
	public float[][] getMatrice() {
		return matrice;
	}

    public float[][] getMatriceDepart() {
        return matriceDepart;
    }
        
        
}
