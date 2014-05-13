package tools;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import core.ressources.Constantes;
import core.ressources.SerializableConstantes;

//methode pour sauvegarder des lancements
public class BackupConstantes
{
    private String                        _path      	= "log/";
    private String                        _fileName     = "lastConstant.ser";
    private boolean                       init     		=	false;

	public SerializableConstantes constantesValues;
	
	public BackupConstantes()
    {
		constantesValues = new  SerializableConstantes();
    }

    public BackupConstantes(String filename)
    {
    	_fileName = filename;
    	constantesValues = new  SerializableConstantes();
    }
	
    public SerializableConstantes getConstantesValues()
    {
    	if(!init)
    	{loadConstantes();}
    	return constantesValues;
    }
    
    public void saveConstantes()
    {
    	saveConstantes(_fileName);
    }
    
    public void saveConstantes(String namefile)
    {
    	constantesValues.coutBoisBucheron = Constantes.get().coutBoisBucheron;
    	constantesValues.coutPierreBucheron = Constantes.get().coutPierreBucheron;
    	constantesValues.coutMetalBucheron = Constantes.get().coutMetalBucheron;
    	constantesValues.coutPopBucheron = Constantes.get().coutPopBucheron;
    	constantesValues.tempsDeConstructionBucheron = Constantes.get().tempsDeConstructionBucheron;
    	constantesValues.prodBois = Constantes.get().prodBois;

        // Carriere
    	constantesValues.coutBoisCarriere = Constantes.get().coutBoisCarriere;
    	constantesValues.coutPierreCarriere = Constantes.get().coutPierreCarriere;
    	constantesValues.coutMetalCarriere = Constantes.get().coutMetalCarriere;
    	constantesValues.coutPopCarriere = Constantes.get().coutPopCarriere;
    	constantesValues.tempsDeConstructionCarriere = Constantes.get().tempsDeConstructionCarriere;
    	constantesValues.prodPierre = Constantes.get().prodPierre;

        // Mine
    	constantesValues.coutBoisMine = Constantes.get().coutBoisMine;
    	constantesValues.coutPierreMine = Constantes.get().coutPierreMine ;
    	constantesValues.coutMetalMine = Constantes.get().coutMetalMine;
    	constantesValues.coutPopMine = Constantes.get().coutPopMine ;
    	constantesValues.tempsDeConstructionMine = Constantes.get().tempsDeConstructionMine;
    	constantesValues.prodMetal = Constantes.get().prodMetal;

        // Ferme
    	constantesValues.coutBoisFerme = Constantes.get().coutBoisFerme;
    	constantesValues.coutPierreFerme = Constantes.get().coutPierreFerme;
    	constantesValues.coutMetalFerme = Constantes.get().coutMetalFerme;
    	constantesValues.tempsDeConstructionFerme = Constantes.get().tempsDeConstructionFerme;
    	constantesValues.prodPop = Constantes.get().prodPop;

    	constantesValues.departBois = Constantes.get().departBois ;
    	constantesValues.departMetal = Constantes.get().departMetal;
    	constantesValues.departPierre = Constantes.get().departPierre;
    	
    	constantesValues.proportionAugmentation = Constantes.get().proportionAugmentation;
    	constantesValues.proportionAugmentationProd = Constantes.get().proportionAugmentationProd;
    	
    	//sauvegarde des constantes dans un fichier
        try {
	        	FileOutputStream fichier = new FileOutputStream(_path+namefile);
	        	ObjectOutputStream oos = new ObjectOutputStream(fichier);
	        	try {
					// sérialisation : écriture de l'objet dans le flux de sortie
	        		
	        		oos.writeObject(constantesValues);
					// on vide le tampon
					oos.flush();
					System.out.println("Les constantes on été sauvegardé : "+_path+namefile);
				} finally {
					//fermeture des flux
					try {
						oos.close();
					} finally {
						fichier.close();
					}
				}
        	}
        	catch (java.io.IOException e) {
        	e.printStackTrace();
        	}
    }
    
    public void loadConstantes()
    {
    	loadConstantes(_fileName);
    }
    
	public void loadConstantes(String namefile)
    { 	
		init = true;
    	try {
	    		FileInputStream fichier = new FileInputStream(_path+namefile);
	    		ObjectInputStream ois = new ObjectInputStream(fichier);
	    		constantesValues = (SerializableConstantes) ois.readObject();
	    		ois.close();
    		}
    		catch (java.io.IOException e) {
    		e.printStackTrace();
    		}
    		catch (ClassNotFoundException e) {
    		e.printStackTrace();
    		}
    }
    
}
