package Modele;

import java.util.ArrayList;


import Interface.InterfaceContraintes;

public final class Simplexe {
	
	private int nbContraintes;
	private int nbVariables;
	private int nbVariablesEcart;
        //private float[][] matriceDepart;
	//private float[][] matrice;
	private Matrice matriceDepart;
        private Matrice matrice;
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
                this.matriceDepart = new Matrice(nbContraintes+1,nbVariables+nbVariablesEcart+1);
                this.matrice = new Matrice(nbContraintes+1,nbVariables+nbVariablesEcart+1);
	}

        //Fonction pour resoudre le probleme par la premiere methode
	public void resolutionProblemeMethode1()
	{
            float max = chercheMax(this.matrice);

            if(max>0)
            {
		afficheMatrice(this.matriceDepart);
                cherchePivot(this.matrice);
		soustractionLigne(matrice);
		divisionLignePivot(matrice);
		changementVariableBase();
  		nbIteration++;
                
             }
             System.out.println("Fin du probleme");
	}

        public void resolutionProblemeMethode2()
	{
            float max = chercheMax(this.matrice);
            if(max>0)
            {
                cherchePivot2(this.matrice);
		soustractionLigne(this.matrice);
		divisionLignePivot(this.matrice);
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

                matrice.setMatrice(matriceDepartTemp);
                //this.matrice = matriceDepartTemp;
                matriceDepart.setMatrice(matriceDepartTemp);
                //this.matriceDepart = matriceDepartTemp;
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
		float matriceFonctionEco[] = new float[matriceDepart.getMatrice()[0].length];

		for(int i=0; i<nbVariables; i++)
		{
			matriceFonctionEco[i]=Integer.parseInt(ihmContrainte.getTabFonctionEco().get(i).getText());
		}
		for (int j=nbVariables;j<matriceDepart.getMatrice()[0].length-1;j++)
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
		matriceNomVariable = new String[matriceDepart.getMatrice()[0].length-1];
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
	public void afficheMatrice(Matrice maMatrice)
	{		
	    for (int i=0; i<maMatrice.getMatrice().length ; i++)
	    {
	    	for (int j=0; j<maMatrice.getMatrice()[0].length  ; j++)
	    	{
	    		System.out.print(maMatrice.getMatrice()[i][j]+" ");
	    	}
	        System.out.println();
	    }
	}
	
	//Cherche le maximum sur la derniere ligne
	public float chercheMax(Matrice maMatrice)
	{
		float maximum = 0;		
		int tailleMatrice = maMatrice.getMatrice().length;
		
		for(int j=0;j<maMatrice.getMatrice()[0].length;j++)
		{
			if (maMatrice.getMatrice()[tailleMatrice-1][j] > maximum)
			{
				maximum = maMatrice.getMatrice()[tailleMatrice-1][j];
				jMaxDerniereLigne=j;
			}
		}
   		return maximum;
	}
	
	//Cherche le pivot 1ere methode
	public void cherchePivot(Matrice maMatrice)
	{
		float calculPivot;
		float calcul;
                calculPivot = 9999;
                elementMatrice pivotTemp = null;
		for (int i=0;i<maMatrice.getMatrice().length-1;i++)
		{
			calcul= (float)(maMatrice.getMatrice()[i][maMatrice.getMatrice()[0].length-1])/(maMatrice.getMatrice()[i][jMaxDerniereLigne]);
                        if(calcul<calculPivot && calcul > 0)
			{
				calculPivot=calcul;
				pivotTemp = new elementMatrice();
                                pivotTemp.setColonne(jMaxDerniereLigne);
                                pivotTemp.setLigne(i);
                                pivotTemp.setValeur(maMatrice.getMatrice()[i][jMaxDerniereLigne]);
			}
		}
                this.pivot = pivotTemp;
	}

        //Fonction qui cherche le pivot pour la deuxième méthode
        public void cherchePivot2(Matrice maMatrice)
        {
            elementMatrice pivotTemp = null;
            float valeurRapport=9999;
            float valeurMultiplie=9999;
            ArrayList<elementMatrice> tabPivotTemp = new ArrayList<elementMatrice> ();
            ArrayList<Float> tabValeurMultiple = new ArrayList<Float> ();
            
            for (int j=0; j<maMatrice.getMatrice()[0].length;j++)
            {
                valeurRapport = 9999;
                if (maMatrice.getMatrice()[maMatrice.getMatrice().length-1][j] > 0)
                {                                       
                    for (int i=0; i<maMatrice.getMatrice().length-1;i++)
                    {
                        if(maMatrice.getMatrice()[i][maMatrice.getMatrice()[0].length-1] / maMatrice.getMatrice()[i][j] < valeurRapport)
                        {
                           valeurRapport = maMatrice.getMatrice()[i][maMatrice.getMatrice()[0].length-1] / maMatrice.getMatrice()[i][j];
                           pivotTemp = new elementMatrice();
                           pivotTemp.setValeur(maMatrice.getMatrice()[i][j]);
                           pivotTemp.setLigne(i);
                           pivotTemp.setColonne(j);
                           valeurMultiplie = valeurRapport*maMatrice.getMatrice()[maMatrice.getMatrice().length-1][j];
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
	public void divisionLignePivot(Matrice maMatrice)
	{
		for(int j=0; j<maMatrice.getMatrice()[0].length;j++)
		{
			maMatrice.getMatrice()[pivot.getLigne()][j]=(maMatrice.getMatrice()[pivot.getLigne()][j])/pivot.getValeur();
		}
	}
	
	//Fonction qui soustrait chaque ligne a la ligne du pivot multiplié par un coefficient
	public void soustractionLigne(Matrice maMatrice)
	{
		for (int i=0;i<maMatrice.getMatrice().length;i++)
		{
			if(i!=pivot.getLigne())
			{
				float coeffSoustraction = (maMatrice.getMatrice()[i][pivot.getColonne()])/pivot.getValeur();
				for (int j=0;j<maMatrice.getMatrice()[0].length;j++)
				{
					maMatrice.getMatrice()[i][j]= (maMatrice.getMatrice()[i][j])-(coeffSoustraction*maMatrice.getMatrice()[pivot.getLigne()][j]);
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
                cherchePivot(this.matrice);
            }
            else if (getNumMethode()==2)
            {
                cherchePivot2(this.matrice);
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
        public String valeurMaxi (Matrice maMatrice)
        {
            String texteValeurMax;
            float valeurMax = -(maMatrice.getMatrice()[maMatrice.getMatrice().length-1][maMatrice.getMatrice()[0].length-1]);
            texteValeurMax = "Le maximum est : " + valeurMax;
            return texteValeurMax;
        }

        //Affiche caractéristique matrice
        public String toString()
        {
            String resultat;
            resultat = pivot()+"\n"+variableBase()+"\n"+valeurMaxi(this.matrice);
            return resultat;
        }

        //Affiche caractéristique matrice fin
        public String toStringFin()
        {
            String resultat;

            resultat = "Il n'y a plus d'itération possible. Le programme est fini"
                    + "\n" + " Nombre d'itérations effectuées : " + getNbIteration()
                    + "\n" + valeurMaxi(this.matrice);

            return resultat;
        }


        /**
	 * Getteur matrice
	 */


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

    public Matrice getMatrice() {
        return matrice;
    }

    public Matrice getMatriceDepart() {
        return matriceDepart;
    }

    


    
    

    

    
        
}
