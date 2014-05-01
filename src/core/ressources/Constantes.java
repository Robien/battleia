package core.ressources;

import java.util.Random;

import tools.Log;

/**
 * cette structure contient toutes les infos constantes pour le jeu
 * il ne faut pas l'utiliser directement, mais la classe Environement
 * Par contre pour faire des tests, il est conseillé de changer les valeurs
 */
public class Constantes
{
    public enum typeBatiment
    {
        NONE, BUCHERON, CARRIERE, MINE, FERME
    }

    public enum typeRessource
    {
        BOIS, PIERRE, METAL, POPULATION, TEMPS
    }

    // general
    public static float         proportionAugmentation      = 1.2f;
    public static final boolean random                      = false;
    public static final int     sizePrecalcul               = 100;  // 0 = sans precalcul

    // ressource de depart
    public static int           departBois                  = 100;
    public static int           departPierre                = 0;
    public static int           departMetal                 = 0;

    // bucheron
    public static int           coutBoisBucheron            = 20;
    public static int           coutPierreBucheron          = 0;
    public static int           coutMetalBucheron           = 0;
    public static int           coutPopBucheron             = 1;
    public static int           tempsDeConstructionBucheron = 1;
    public static int           prodBois                    = 1;

    // Carriere
    public static int           coutBoisCarriere            = 30;
    public static int           coutPierreCarriere          = 0;
    public static int           coutMetalCarriere           = 0;
    public static int           coutPopCarriere             = 1;
    public static int           tempsDeConstructionCarriere = 1;
    public static int           prodPierre                  = 1;

    // Mine
    public static int           coutBoisMine                = 50;
    public static int           coutPierreMine              = 30;
    public static int           coutMetalMine               = 0;
    public static int           coutPopMine                 = 2;
    public static int           tempsDeConstructionMine     = 1;
    public static int           prodMetal                   = 1;

    // Ferme
    public static int           coutBoisFerme               = 1;
    public static int           coutPierreFerme             = 0;
    public static int           coutMetalFerme              = 0;
    public static int           tempsDeConstructionFerme    = 2;
    public static int           prodPop                     = 5;

    private static Values       values;

    public static Values getValues()
    {
        return values;
    }

    // un singleton, ça serait mieux quand même
    // /** Holder to make the singleton */
    // private static class SingletonHolder
    // {
    // private final static Constantes instance = new Constantes();
    // }
    //
    // /**
    // * @return the unique instance of the CameraManager
    // */
    // public static Constantes get()
    // {
    // return SingletonHolder.instance;
    // }
    //
    // /**
    // * The constructor is private because you need to use get() to have the unique instance of this class
    // */
    // private Constantes()
    // {}

    public static int getRandInt(Random r)
    {
        return (r.nextInt(100) + 1);
    }

    public static void init()
    {
        Random r = new Random();
        r.setSeed(System.currentTimeMillis());

        if (random)
        {
            // general
            proportionAugmentation = 1 + r.nextFloat() * 2;

            // bucheron
            coutBoisBucheron = coutBoisBucheron * getRandInt(r);
            coutPierreBucheron = 0;
            coutMetalBucheron = 0;
            coutPopBucheron = coutPopBucheron * getRandInt(r);
            tempsDeConstructionBucheron = tempsDeConstructionBucheron * getRandInt(r);
            prodBois = prodBois * getRandInt(r);

            // Carriere
            coutBoisCarriere = coutBoisCarriere * getRandInt(r);
            coutPierreCarriere = 0;
            coutMetalCarriere = 0;
            coutPopCarriere = coutPopCarriere * getRandInt(r);
            tempsDeConstructionCarriere = tempsDeConstructionCarriere * getRandInt(r);
            prodPierre = prodPierre * getRandInt(r);

            // Mine
            coutBoisMine = coutBoisMine * getRandInt(r);
            coutPierreMine = coutPierreMine * getRandInt(r);
            coutMetalMine = 0;
            coutPopMine = coutPopMine * getRandInt(r);
            tempsDeConstructionMine = tempsDeConstructionMine * getRandInt(r);
            prodMetal = prodMetal * getRandInt(r);

            // Ferme
            coutBoisFerme = coutBoisFerme * getRandInt(r);
            coutPierreFerme = 0;
            coutMetalFerme = 0;
            tempsDeConstructionFerme = tempsDeConstructionFerme * getRandInt(r);
            prodPop = prodPop * getRandInt(r);
            
            
            departBois = (int) (coutBoisBucheron+departBois*r.nextFloat() * 10);
            departMetal = (int) (coutMetalBucheron+departMetal*r.nextFloat() * 10);
            departPierre = (int) (coutPierreBucheron+departPierre*r.nextFloat() * 10);
            
            print();
        }

        values = new Values(sizePrecalcul);
    }

    public static int getCout(typeBatiment batiment, int lvl, typeRessource ressource)
    {
        if (lvl < 0)
        {
            return 0;
        }
        switch (batiment)
        {
        case BUCHERON:
            switch (ressource)
            {
            case BOIS:
                return getValue(lvl, coutBoisBucheron);
            case METAL:
                return getValue(lvl, coutMetalBucheron);
            case PIERRE:
                return getValue(lvl, coutPierreBucheron);
            case POPULATION:
                return getValue(lvl, coutPopBucheron);
            case TEMPS:
                return getValue(lvl, tempsDeConstructionBucheron);
            }
            break;
        case CARRIERE:
            switch (ressource)
            {
            case BOIS:
                return getValue(lvl, coutBoisCarriere);
            case METAL:
                return getValue(lvl, coutMetalCarriere);
            case PIERRE:
                return getValue(lvl, coutPierreCarriere);
            case POPULATION:
                return getValue(lvl, coutPopCarriere);
            case TEMPS:
                return getValue(lvl, tempsDeConstructionCarriere);
            }
            break;
        case FERME:
            switch (ressource)
            {
            case BOIS:
                return getValue(lvl, coutBoisFerme);
            case METAL:
                return getValue(lvl, coutMetalFerme);
            case PIERRE:
                return getValue(lvl, coutPierreFerme);
            case POPULATION:
                return 0;
            case TEMPS:
                return getValue(lvl, tempsDeConstructionFerme);
            }
            break;
        case MINE:
            switch (ressource)
            {
            case BOIS:
                return getValue(lvl, coutBoisMine);
            case METAL:
                return getValue(lvl, coutMetalMine);
            case PIERRE:
                return getValue(lvl, coutPierreMine);
            case POPULATION:
                return getValue(lvl, coutPopMine);
            case TEMPS:
                return getValue(lvl, tempsDeConstructionMine);
            }
            break;

        default:
            return 0;
        }

        return 0;
    }

    public static typeBatiment getBatimentOfRessources(typeRessource ressource)
    {
        switch (ressource)
        {
        case BOIS:
            return typeBatiment.BUCHERON;
        case METAL:
            return typeBatiment.MINE;
        case PIERRE:
            return typeBatiment.CARRIERE;
        case POPULATION:
            return typeBatiment.FERME;
        case TEMPS:
            return typeBatiment.NONE;
        default:
            return typeBatiment.NONE;
        }
    }

    public static int getProd(int lvl, typeRessource ressource)
    {
    	if (lvl == 0 && ressource != typeRessource.POPULATION)
    	{
    		return 0;
    	}
        switch (ressource)
        {
        case BOIS:
            return getValue(lvl, prodBois);
        case METAL:
            return getValue(lvl, prodMetal);
        case PIERRE:
            return getValue(lvl, prodPierre);
        case POPULATION:
            return getValue(lvl, prodPop);
        case TEMPS:
            return 0;
        default:
            return 0;
        }
    }

    public static float getProdFloat(int lvl, typeRessource ressource)
    {
    	if (lvl == 0 && ressource != typeRessource.POPULATION)
    	{
    		return 0;
    	}

        switch (ressource)
        {
        case BOIS:
            return getValueFloat(lvl, prodBois);
        case METAL:
            return getValueFloat(lvl, prodMetal);
        case PIERRE:
            return getValueFloat(lvl, prodPierre);
        case POPULATION:
            return getValue(lvl, prodPop);
        case TEMPS:
            return 0;
        default:
            return 0;
        }
    }

    public static int getProd(typeBatiment batiment, int lvl, typeRessource ressource)
    {
if (lvl == 0 && batiment != typeBatiment.FERME)
{
	return 0;
}
        switch (batiment)
        {
        case BUCHERON:
            switch (ressource)
            {
            case BOIS:
                return getValue(lvl, prodBois);
            case METAL:
                return 0;
            case PIERRE:
                return 0;
            case POPULATION:
                return 0;
            case TEMPS:
                return 0;
            }
            break;
        case CARRIERE:
            switch (ressource)
            {
            case BOIS:
                return 0;
            case METAL:
                return 0;
            case PIERRE:
                return getValue(lvl, prodPierre);
            case POPULATION:
                return 0;
            case TEMPS:
                return 0;
            }
            break;
        case FERME:
            switch (ressource)
            {
            case BOIS:
                return 0;
            case METAL:
                return 0;
            case PIERRE:
                return 0;
            case POPULATION:
                return getValue(lvl, prodPop);
            case TEMPS:
                return 0;
            }
            break;
        case MINE:
            switch (ressource)
            {
            case BOIS:
                return 0;
            case METAL:
                return getValue(lvl, prodMetal);
            case PIERRE:
                return 0;
            case POPULATION:
                return 0;
            case TEMPS:
                return 0;
            }
            break;

        default:
            return 0;
        }

        return 0;
    }

    public static int getValue(int lvl, int val)
    {
        return (int) ((float) val * Math.pow(proportionAugmentation, lvl));
    }

    public static float getValueFloat(int lvl, int val)
    {
        return (float) (val * Math.pow(proportionAugmentation, lvl));
    }

    public static boolean isConstructionPossible(typeBatiment batiment, int lvlCourrant, int bois, int pierre, int metal)
    {
        return ((bois >= getCout(batiment, lvlCourrant, typeRessource.BOIS)) && (pierre >= getCout(batiment, lvlCourrant, typeRessource.PIERRE)) && (metal >= getCout(
                batiment, lvlCourrant, typeRessource.METAL)));
    }
    
    public static void print()
    {
    	Log.print("=== CONSTANTES ===");
    	Log.print("= couts =");
    	Log.print("Bois/Pierre/Metal/Population/temps");
    	Log.print("Bucherons\t" + coutBoisBucheron + "/" + coutPierreBucheron + "/" + coutMetalBucheron + "/"+coutPopBucheron + "/"+tempsDeConstructionBucheron);
    	Log.print("Carriere\t" + coutBoisCarriere + "/" + coutPierreCarriere + "/" + coutMetalCarriere+"/"+coutPopCarriere+ "/"+tempsDeConstructionCarriere);
    	Log.print("Mine\t\t" + coutBoisMine + "/" + coutPierreMine + "/" + coutMetalMine+"/"+coutPopMine+ "/"+tempsDeConstructionMine);
    	Log.print("Ferme\t\t" + coutBoisFerme + "/" + coutPierreFerme+ "/" + coutMetalFerme+"/0"+ "/"+tempsDeConstructionFerme);
    	Log.print("= production =");
    	Log.print(prodBois + "/" + prodPierre + "/" + prodMetal + "/"+prodPop);
    	Log.print("= misc =");
    	Log.print("proportion d'augmentation\t" + proportionAugmentation);
    	
    	
    }

}
