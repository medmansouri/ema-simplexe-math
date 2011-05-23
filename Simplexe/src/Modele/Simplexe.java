package Modele;

import java.util.ArrayList;


import Interface.InterfaceContraintes;

public final class Simplexe {
	
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
        private int numMethode;
        private int nbIteration=0;


	
	public Simplexe(int nbContraintes, int nbVariables, InterfaceContraintes ihmContrainte)
	{
		this.nbContraintes=nbContraintes;
		this.nbVariables=nbVariables;
		this.nbVariablesEcart=nbContraintes;
		this.ihmContrainte=ihmContrainte;
                this.matriceDepart = new float[nbContraintes+1][nbVariables+nbVariablesEcart+1];
                this.matrice = new float[nbContraintes+1][nbVariables+nbVariablesEcart+1];
	}

        //Fonction pour resoudre le probleme par la premiere methode
	public void resolutionProblemeMethode1()
	{
            float max = chercheMax();

            if(max>0)
            {
		cherchePivot();
		soustractionLigne();
		divisionLignePivot();
		changementVariableBase();
  		nbIteration++;
                afficheMatriceDepart();
             }
             System.out.println("Fin du probleme");
	}

        public void resolutionProblemeMethode2()
	{
            float max = chercheMax();
            if(max>0)
            {
                afficheMatriceDepart();
		cherchePivot2();
		soustractionLigne();
		divisionLignePivot();
		changementVariableBase();
		nbIteration++;
            }
            System.out.println("Fin du probleme");
	}

	//Remplissage de la matrice avec les valeurs des coefficients + matrice identite + matrice result + matrice fonction eco
	public void remplirMatrice()
	{
		float matriceDepartTemp[][] = new float[nbContraintes+1][nbVariables+nbVariablesEcart+1];;
                int i=0;
		while (i<ihmContrainte.getTabVariables().size())
		{
			for(int j=0;j<nbContraintes;j++)
			{
				for(int k=0;k<nbVariables;k++)
				{
                                    matriceDepartTemp[j][k]=(float) ((Number)ihmContrainte.getTabVariables().get(i).getValue()).doubleValue();
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
				matriceDepartTemp[i][nbVariables+j]=matriceIdentite[i][j];	
				
			}			
		}
		
		//Ajout de la matrice maximisant
		float matriceResult[] = creationMatriceMaximisant();
		for(i=0;i<matriceResult.length;i++)
		{
			matriceDepartTemp[i][matriceDepartTemp[0].length-1]=matriceResult[i];
		}
		
		//Ajout de la matrice de la fonction �conomique
		float matriceFonctionEco[] = creationMatriceFonctionEco();
		for(i=0;i<matriceFonctionEco.length;i++)
		{
			matriceDepartTemp[matriceDepartTemp.length-1][i]=matriceFonctionEco[i];
		}
                this.matrice = matriceDepartTemp;
                this.matriceDepart = matriceDepartTemp;
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
	
	//Creation de la matrice avec les noms de variable appartenant a la base
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
        
        public void afficheMatriceDepart()
	{		
	    for (int i=0; i<matriceDepart.length ; i++)
	    {
	    	for (int j=0; j<matriceDepart[0].length  ; j++)
	    	{
	    		System.out.print(matriceDepart[i][j]+" ");
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
                calculPivot = 9999;
                elementMatrice pivotTemp = null;
		for (int i=0;i<this.matrice.length-1;i++)
		{
			calcul= (float)(this.matrice[i][this.matrice[0].length-1])/(this.matrice[i][jMaxDerniereLigne]);
                        if(calcul<calculPivot && calcul > 0)
			{
				calculPivot=calcul;
				pivotTemp = new elementMatrice();
                                pivotTemp.setColonne(jMaxDerniereLigne);
                                pivotTemp.setLigne(i);
                                pivotTemp.setValeur(this.matrice[i][jMaxDerniereLigne]);
			}
		}
                this.pivot = pivotTemp;
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
			this.matrice[pivot.getLigne()][j]=(this.matrice[pivot.getLigne()][j])/pivot.getValeur();
		}
	}
	
	//Fonction qui soustrait chaque ligne a la ligne du pivot multiplié par un coefficient
	public void soustractionLigne()
	{
		for (int i=0;i<this.matrice.length;i++)
		{
			if(i!=pivot.getLigne())
			{
				float coeffSoustraction = (this.matrice[i][pivot.getColonne()])/pivot.getValeur();
				for (int j=0;j<matrice[0].length;j++)
				{
					this.matrice[i][j]= (this.matrice[i][j])-(coeffSoustraction*this.matrice[pivot.getLigne()][j]);
				}
			}
		}
        }	
	//Fonction qui fait entrer un nom de variable dans la base
	public void changementVariableBase()
	{
            matriceNomVariableBase[pivot.getLigne()]=matriceNomVariable[pivot.getColonne()];
	}


        //Fonction pour afficher infos sur le pivot
        public String pivot()
        {
            String textePivot;
            if(getNumMethode()==1)
            {
                cherchePivot();
            }
            else if (getNumMethode()==2)
            {
                cherchePivot2();
            }

            
            textePivot = "Pivot : \n "
                    + "Valeur : " + this.pivot.getValeur() + "\n"
                    + "Ligne : " + this.pivot.getLigne() + "\n"
                    + "Colonne : " + this.pivot.getColonne();

            return textePivot;
        }

        //Fonction pour afficher les infos sur la variable qui entre dans la base
        public String variableBase ()
        {
            String texteVariableBase;

            texteVariableBase = "La variable qui rentre dans la base est : " + matriceNomVariable[pivot.getColonne()];

            return texteVariableBase;
        }

        //Fonction qui affiche les infos sur le max
        public String valeurMaxi ()
        {
            String texteValeurMax;
            float valeurMax = -(matrice[matrice.length-1][matrice[0].length-1]);
            texteValeurMax = "Le maximum est : " + valeurMax;
            return texteValeurMax;
        }

        //Affiche caractéristique matrice
        public String toString()
        {
            String resultat;
            resultat = pivot()+"\n"+variableBase()+"\n"+valeurMaxi(); 
            return resultat;
        }

        //Affiche caractéristique matrice fin
        public String toStringFin()
        {
            String resultat;

            resultat = "Il n'y a plus d'itération possible. Le programme est fini"
                    + "\n" + " Nombre d'itérations effectuées : " + getNbIteration()
                    + "\n" + valeurMaxi();

            return resultat;
        }


        /**
	 * Getteur matrice
	 */
	public float[][] getMatrice() {
		return matrice;
	}

    /*public float[][] getMatriceDepart() {
        return matriceDepart;
    }*/

    public String[] getMatriceNomVariable() {
        return matriceNomVariable;
    }

    public String[] getMatriceNomVariableBase() {
        return matriceNomVariableBase;
    }

    public int getNumMethode() {
        return numMethode;
    }

    public int getNbIteration() {
        return nbIteration;
    }

    
    /**
	 * Setter matrice
	 */

    public void setNumMethode(int numMethode) {
        this.numMethode = numMethode;
    }

    public void setMatrice(float[][] matrice) {
        this.matrice = matrice;
    }

    
    

    

    
        
}
