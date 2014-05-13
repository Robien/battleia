package core.ressources;

import java.io.Serializable;
import java.util.HashMap;

import core.ressources.Constantes.typeBatiment;
import core.ressources.Constantes.typeRessource;

public class SerializableConstantes implements Serializable
{

	private static final long serialVersionUID = -7540667650858359921L;
	public SerializableConstantes()
	{}
	
	public HashMap<typeBatiment, HashMap<typeRessource, Float>> proportionAugmentation      = new HashMap<>();
    public HashMap<typeRessource, Float>                        proportionAugmentationProd  = new HashMap<>();
    
	 // ressource de depart
    public int                                                  departBois                  = 0;
    public int                                                  departPierre                = 0;
    public int                                                  departMetal                 = 0;

    // bucheron
    public int                                                  coutBoisBucheron            = 0;
    public int                                                  coutPierreBucheron          = 0;
    public int                                                  coutMetalBucheron           = 0;
    public int                                                  coutPopBucheron             = 1;
    public int                                                  tempsDeConstructionBucheron = 1;
    public int                                                  prodBois                    = 1;

    // Carriere
    public int                                                  coutBoisCarriere            = 0;
    public int                                                  coutPierreCarriere          = 0;
    public int                                                  coutMetalCarriere           = 0;
    public int                                                  coutPopCarriere             = 0;
    public int                                                  tempsDeConstructionCarriere = 0;
    public int                                                  prodPierre                  = 0;

    // Mine
    public int                                                  coutBoisMine                = 0;
    public int                                                  coutPierreMine              = 0;
    public int                                                  coutMetalMine               = 0;
    public int                                                  coutPopMine                 = 0;
    public int                                                  tempsDeConstructionMine     = 0;
    public int                                                  prodMetal                   = 0;

    // Ferme
    public int                                                  coutBoisFerme               = 0;
    public int                                                  coutPierreFerme             = 0;
    public int                                                  coutMetalFerme              = 0;
    public int                                                  tempsDeConstructionFerme    = 0;
    public int                                                  prodPop                     = 0;

}