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
	
	//Remplissage de la matrice avec les valeurs des coefficients + matrice identite + matrice result + matrice fonction eco
	public void remplirMatrice()
	{
		int i=0;
		while (i<ihmContrainte.getTabVariables().size())
		{
			for(int j=0;j<nbContraintes;j++)
			{
				for(int k=0;k<nbVariables;k++)
				{
                                    this.matriceDepart[j][k]=(float) ((Number)ihmContrainte.getTabVariables().get(i).getValue()).doubleValue();
                                    //this.matriceDepart[j][k]=Integer.parseInt(ihmContrainte.getTabVariables().get(i).getText());
                                    i++;
				}
			}
		}
		
		//On ajoute la matrice identite a la matrice des coefficients
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
	public void resolutionProblemeMethode1()
	{
            float max = chercheMax();
            while(max>0)
            {    
		cherchePivot();
		soustractionLigne();
		divisionLignePivot();
		changementVariableBase();
		//afficheMatrice();
                max = chercheMax();		
            }
            System.out.println("Fin du probleme");		
	}
        
        public void resolutionProblemeMethode2()
	{
            float max = chercheMax();
            while(max>0)
            {    
		cherchePivot2();
		soustractionLigne();
		divisionLignePivot();
		changementVariableBase();
		//afficheMatrice();
                max = chercheMax();		
            }
            System.out.println("Fin du probleme");		
	}
	
	//Creation de la matrice identite
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
		
		return matriceResult;
	}
	
	//Creation de la matrice de la fonction �conomique
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
		
		return matriceFonctionEco;
	}
		
	//Creation nom variable valeur d'�cart	
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
	
	//Creation de la matrice avec les noms de variables
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
			
	}
	
	//Creation de la matrice avec les noms de variable appartenant � la base
	public void creationMatriceNomVariableBase()
	{
		matriceNomVariableBase = creationMatriceNomVariableEcart();

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
	
	//Cherche le maximum sur la derniere ligne
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
                        if(calcul<calculPivot)
			{
				calculPivot=calcul;
				pivot.setValeur(matrice[i][jMaxDerniereLigne]);
				pivot.setLigne(i);
				pivot.setColonne(jMaxDerniereLigne);
			}
		}
	}

        //Fonction qui cherche le pivot pour la deuxième méthode
        public void cherchePivot2()
        {
            elementMatrice pivotTemp = null;
            float valeurRapport=9999;
            float valeurMultiplie=9999;
            ArrayList<elementMatrice> tabPivotTemp = new ArrayList<elementMatrice> ();
            ArrayList<Float> tabValeurMultiple = new ArrayList<Float> ();
            
            for (int j=0; j<matrice[0].length;j++)
            {
                valeurRapport = 9999;
                if (matrice[matrice.length-1][j] > 0)
                {                                       
                    for (int i=0; i<matrice.length-1;i++)
                    {
                        if(matrice[i][matrice[0].length-1] / matrice[i][j] < valeurRapport)
                        {
                           valeurRapport = matrice[i][matrice[0].length-1] / matrice[i][j];
                           pivotTemp = new elementMatrice();
                           pivotTemp.setValeur(matrice[i][j]);
                           pivotTemp.setLigne(i);
                           pivotTemp.setColonne(j);
                           valeurMultiplie = valeurRapport*matrice[matrice.length-1][j];                         
                        }
                    }                  
                    tabPivotTemp.add(pivotTemp);            
                    tabValeurMultiple.add(valeurMultiplie);
                }
            }
            
            valeurMultiplie = tabValeurMultiple.get(0);
            pivot.setValeur(tabPivotTemp.get(0).getValeur());
            pivot.setLigne(tabPivotTemp.get(0).getLigne());
            pivot.setColonne(tabPivotTemp.get(0).getColonne());
                        
            for(int i=1; i<tabValeurMultiple.size(); i++)
            {
                if(tabValeurMultiple.get(i) > valeurMultiplie)
                {
                    valeurMultiplie = tabValeurMultiple.get(i);
                    pivot.setValeur(tabPivotTemp.get(i).getValeur());
                    pivot.setLigne(tabPivotTemp.get(i).getLigne());
                    pivot.setColonne(tabPivotTemp.get(i).getColonne());
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
				for (int j=0;j<matrice[0].length;j++)
				{
					matrice[i][j]= (matrice[i][j])-(coeffSoustraction*matrice[pivot.getLigne()][j]);
				}
			}
		}
	}
	
	//Fonction qui fait entrer un nom de variable dans la base
	public void changementVariableBase()
	{
            matriceNomVariableBase[pivot.getLigne()]=matriceNomVariable[pivot.getColonne()];
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
