package core.ressources;

import java.util.HashMap;
import java.util.Random;

import core.ConstantesDeJeu;
import core.ConstantesDeJeu.e_saveState;
import tools.BackupGameManager;
import tools.Log;
import tools.Log.tag;

/**
 * cette structure contient toutes les infos constantes pour le jeu
 * il ne faut pas l'utiliser directement, mais la classe Environement à la place
 * Par contre pour faire des tests, il est conseillé de changer les valeurs pour tester différents scenarii
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
    public HashMap<typeBatiment, HashMap<typeRessource, Float>> proportionAugmentation      = new HashMap<>();
    public HashMap<typeRessource, Float>                        proportionAugmentationProd  = new HashMap<>();
    public float                                                proportionAugmentationBase  = 1.2f;
    public BackupGameManager                                     backupManager               = new BackupGameManager();
    public final int                                            sizePrecalcul               = 100;                   // 0 = sans precalcul

    // ressource de depart
    public int                                                  departBois                  = 100;
    public int                                                  departPierre                = 0;
    public int                                                  departMetal                 = 0;

    // bucheron
    public int                                                  coutBoisBucheron            = 20;
    public int                                                  coutPierreBucheron          = 0;
    public int                                                  coutMetalBucheron           = 0;
    public int                                                  coutPopBucheron             = 1;
    public int                                                  tempsDeConstructionBucheron = 1;
    public int                                                  prodBois                    = 1;

    // Carriere
    public int                                                  coutBoisCarriere            = 30;
    public int                                                  coutPierreCarriere          = 0;
    public int                                                  coutMetalCarriere           = 0;
    public int                                                  coutPopCarriere             = 1;
    public int                                                  tempsDeConstructionCarriere = 1;
    public int                                                  prodPierre                  = 1;

    // Mine
    public int                                                  coutBoisMine                = 50;
    public int                                                  coutPierreMine              = 30;
    public int                                                  coutMetalMine               = 0;
    public int                                                  coutPopMine                 = 2;
    public int                                                  tempsDeConstructionMine     = 1;
    public int                                                  prodMetal                   = 1;

    // Ferme
    public int                                                  coutBoisFerme               = 1;
    public int                                                  coutPierreFerme             = 0;
    public int                                                  coutMetalFerme              = 0;
    public int                                                  tempsDeConstructionFerme    = 2;
    public int                                                  prodPop                     = 5;

    private Values                                              values;

    public Values getValues()
    {
        return values;
    }

    /** Holder to make the singleton */
    private static class SingletonHolder
    {
        private static Constantes instance = new Constantes();
    }

    /**
     * @return the unique instance of the CameraManager
     */
    public static Constantes get()
    {
        return SingletonHolder.instance;
    }

    /**
     * The constructor is private because you need to use get() to have the unique instance of this class
     */
    private Constantes()
    {}

    public static void init()
    {
        SingletonHolder.instance = new Constantes();
        SingletonHolder.instance.initInstance();
    }

    public int getRandInt(Random r)
    {
        return (r.nextInt(100) + 1);
    }

    private void initInstance()
    {
        Random r = new Random();
        long seed = System.currentTimeMillis();
        if (ConstantesDeJeu.random && ConstantesDeJeu.saveState == e_saveState.LOAD)
        {
        	Log.print(tag.CONSTANTES, "Restauration de la sauvegarde : " + ConstantesDeJeu.indexSavedSeedUse);
        	seed = backupManager.getSeed(ConstantesDeJeu.indexSavedSeedUse);
        }
        r.setSeed(seed);
       
        for (typeBatiment bat : typeBatiment.values())
        {
            HashMap<typeRessource, Float> tmp = new HashMap<>();
            for (typeRessource res : typeRessource.values())
            {
                tmp.put(res, proportionAugmentationBase);
            }
            proportionAugmentation.put(bat, tmp);
        }
        for (typeRessource res : typeRessource.values())
        {
            proportionAugmentationProd.put(res, proportionAugmentationBase);
        }

        if (ConstantesDeJeu.random)
        {
        	Log.print(tag.CONSTANTES, "ID du générateur de nombre aléatoire utilisé: " + seed);
        	if (ConstantesDeJeu.saveState == e_saveState.SAVE_ALL )
            {
        		Log.print(tag.CONSTANTES, "l'ID du générateur de nombre aléatoire a été sauvegardé: " + ConstantesDeJeu.indexSavedSeedUse);
                backupManager.saveSeed(seed);
            }
        	
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

            departBois = (int) (coutBoisBucheron + departBois * r.nextFloat() * 10);
            departMetal = (int) (coutMetalBucheron + departMetal * r.nextFloat() * 10);
            departPierre = (int) (coutPierreBucheron + departPierre * r.nextFloat() * 10);

            for (typeBatiment bat : typeBatiment.values())
            {
                for (typeRessource res : typeRessource.values())
                {
                    proportionAugmentation.get(bat).put(res, 1 + r.nextFloat() * 2);
                }
            }

            for (typeRessource res : typeRessource.values())
            {
                proportionAugmentationProd.put(res, 1 + r.nextFloat() * 2);
            }
            print();

            
        }

        

        values = new Values(sizePrecalcul);
    }

    public int getCout(typeBatiment batiment, int lvl, typeRessource ressource)
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
                return getValue(lvl, coutBoisBucheron, batiment, ressource);
            case METAL:
                return getValue(lvl, coutMetalBucheron, batiment, ressource);
            case PIERRE:
                return getValue(lvl, coutPierreBucheron, batiment, ressource);
            case POPULATION:
                return getValue(lvl, coutPopBucheron, batiment, ressource);
            case TEMPS:
                return getValue(lvl, tempsDeConstructionBucheron, batiment, ressource);
            }
            break;
        case CARRIERE:
            switch (ressource)
            {
            case BOIS:
                return getValue(lvl, coutBoisCarriere, batiment, ressource);
            case METAL:
                return getValue(lvl, coutMetalCarriere, batiment, ressource);
            case PIERRE:
                return getValue(lvl, coutPierreCarriere, batiment, ressource);
            case POPULATION:
                return getValue(lvl, coutPopCarriere, batiment, ressource);
            case TEMPS:
                return getValue(lvl, tempsDeConstructionCarriere, batiment, ressource);
            }
            break;
        case FERME:
            switch (ressource)
            {
            case BOIS:
                return getValue(lvl, coutBoisFerme, batiment, ressource);
            case METAL:
                return getValue(lvl, coutMetalFerme, batiment, ressource);
            case PIERRE:
                return getValue(lvl, coutPierreFerme, batiment, ressource);
            case POPULATION:
                return 0;
            case TEMPS:
                return getValue(lvl, tempsDeConstructionFerme, batiment, ressource);
            }
            break;
        case MINE:
            switch (ressource)
            {
            case BOIS:
                return getValue(lvl, coutBoisMine, batiment, ressource);
            case METAL:
                return getValue(lvl, coutMetalMine, batiment, ressource);
            case PIERRE:
                return getValue(lvl, coutPierreMine, batiment, ressource);
            case POPULATION:
                return getValue(lvl, coutPopMine, batiment, ressource);
            case TEMPS:
                return getValue(lvl, tempsDeConstructionMine, batiment, ressource);
            }
            break;

        default:
            return 0;
        }

        return 0;
    }

    public typeBatiment getBatimentOfRessources(typeRessource ressource)
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

    public int getProd(int lvl, typeRessource ressource)
    {
        if (lvl == 0 && ressource != typeRessource.POPULATION)
        {
            return 0;
        }
        switch (ressource)
        {
        case BOIS:
            return getValueProd(lvl, prodBois, typeRessource.BOIS);
        case METAL:
            return getValueProd(lvl, prodMetal, typeRessource.METAL);
        case PIERRE:
            return getValueProd(lvl, prodPierre, typeRessource.PIERRE);
        case POPULATION:
            return getValueProd(lvl, prodPop, typeRessource.POPULATION);
        case TEMPS:
            return 0;
        default:
            return 0;
        }
    }

    public float getProdFloat(int lvl, typeRessource ressource)
    {
        if (lvl == 0 && ressource != typeRessource.POPULATION)
        {
            return 0;
        }

        switch (ressource)
        {
        case BOIS:
            return getValueProdFloat(lvl, prodBois, typeRessource.BOIS);
        case METAL:
            return getValueProdFloat(lvl, prodMetal, typeRessource.METAL);
        case PIERRE:
            return getValueProdFloat(lvl, prodPierre, typeRessource.PIERRE);
        case POPULATION:
            return getValueProd(lvl, prodPop, typeRessource.POPULATION);
        case TEMPS:
            return 0;
        default:
            return 0;
        }
    }

    public int getProd(typeBatiment batiment, int lvl, typeRessource ressource)
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
                return getValueProd(lvl, prodBois, typeRessource.BOIS);
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
                return getValueProd(lvl, prodPierre, typeRessource.PIERRE);
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
                return getValueProd(lvl, prodPop, typeRessource.POPULATION);
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
                return getValueProd(lvl, prodMetal, typeRessource.METAL);
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

    public int getValue(int lvl, int val, typeBatiment bat, typeRessource res)
    {
        return (int) getValueFloat(lvl, val, bat, res);
    }

    public int getValueProd(int lvl, int val, typeRessource res)
    {
        return (int) getValueProdFloat(lvl, val, res);
    }

    public float getValueFloat(int lvl, int val, typeBatiment bat, typeRessource res)
    {
        return (float) (val * Math.pow(proportionAugmentation.get(bat).get(res), lvl));
    }

    public float getValueProdFloat(int lvl, int val, typeRessource res)
    {
        return (float) (val * Math.pow(proportionAugmentationProd.get(res), lvl));
    }

    public boolean isConstructionPossible(typeBatiment batiment, int lvlCourrant, int bois, int pierre, int metal)
    {
        return ((bois >= getCout(batiment, lvlCourrant, typeRessource.BOIS)) && (pierre >= getCout(batiment, lvlCourrant, typeRessource.PIERRE)) && (metal >= getCout(
                batiment, lvlCourrant, typeRessource.METAL)));
    }

    public void print()
    {
        Log.print(tag.CONSTANTES, "=== CONSTANTES ===");
        Log.print(tag.CONSTANTES, "= couts =");
        Log.print(tag.CONSTANTES, "Bois/Pierre/Metal/Population/temps");
        Log.print(tag.CONSTANTES, "Bucherons\t" + coutBoisBucheron + "/" + coutPierreBucheron + "/" + coutMetalBucheron + "/" + coutPopBucheron + "/"
                + tempsDeConstructionBucheron);
        Log.print(tag.CONSTANTES, "Carriere\t" + coutBoisCarriere + "/" + coutPierreCarriere + "/" + coutMetalCarriere + "/" + coutPopCarriere + "/"
                + tempsDeConstructionCarriere);
        Log.print(tag.CONSTANTES, "Mine\t\t" + coutBoisMine + "/" + coutPierreMine + "/" + coutMetalMine + "/" + coutPopMine + "/"
                + tempsDeConstructionMine);
        Log.print(tag.CONSTANTES, "Ferme\t\t" + coutBoisFerme + "/" + coutPierreFerme + "/" + coutMetalFerme + "/0" + "/" + tempsDeConstructionFerme);
        Log.print(tag.CONSTANTES, "= production =");
        Log.print(tag.CONSTANTES, prodBois + "/" + prodPierre + "/" + prodMetal + "/" + prodPop);
        Log.print(tag.CONSTANTES, "= Ressources départ =");
        Log.print(tag.CONSTANTES, departBois + "/" + departPierre + "/" + departMetal);
        Log.print(tag.CONSTANTES, "= misc =");
        Log.print(tag.CONSTANTES, "proportion d'augmentation\t" + proportionAugmentationBase);

        for (typeBatiment bat : typeBatiment.values())
        {
            for (typeRessource res : typeRessource.values())
            {
                if (getCout(bat, 0, res) != 0)
                {
                    Log.print(tag.CONSTANTES, "augmentation " + bat.toString() + " - " + res.toString() + " : "
                            + proportionAugmentation.get(bat).get(res));
                }
            }
        }

        for (typeRessource res : typeRessource.values())
        {
            if (res != typeRessource.TEMPS)
            {
                Log.print(tag.CONSTANTES, "augmentation production " + res.toString() + " : " + proportionAugmentationProd.get(res));
            }
        }

    }

}
