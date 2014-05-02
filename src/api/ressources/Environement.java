/**
 * 
 */
package api.ressources;

import java.util.HashMap;

import api.IA.InfosBase;
import core.moteur.DBCache;
import core.moteur.IAManager;
import core.ressources.Constantes;
import core.ressources.Constantes.typeBatiment;
import core.ressources.Constantes.typeRessource;
import core.ressources.Values;

/**
 * cette classe contiend tout ce qu'il faut savoir sur l'environement.
 * Il y a aussi ici les valeurs précalculé pour éviter que les IA le fassent elles mêmes
 * les méthodes dont le nom commence par RAW sont des méthodes qui font vraiment le calcul, sinon c'est précalculé.
 * C'est une classe singleton
 */
public class Environement
{

    private DBCache cache;

    /** Holder to make the singleton */
    private static class SingletonHolder
    {
        private final static Environement instance = new Environement();
    }

    /**
     * @return the unique instance of Environement
     */
    public static Environement get()
    {
        return SingletonHolder.instance;
    }

    /**
     * The constructor is private because you need to use get() to have the unique instance of this class
     */
    private Environement()
    {}

    public boolean RAWisConstructionPossible(Constantes.typeBatiment batiment, InfosBase base)
    {
        return Constantes.isConstructionPossible(batiment, base.getLvl(batiment), base.quantiteBois, base.quantitePierre, base.quantiteMetal);
    }

    public boolean RAWisConstructionPossible(typeBatiment batiment, int lvlCourrant, int bois, int pierre, int metal)
    {
        return Constantes.isConstructionPossible(batiment, lvlCourrant, bois, pierre, metal);
    }

    public int RAWgetProd(Constantes.typeRessource res, InfosBase base)
    {
        return Constantes.getProd(base.getLvl(getBatimentOfRessources(res)), res);
    }

    public float RAWgetProdFloat(Constantes.typeRessource res, InfosBase base)
    {
        return Constantes.getProdFloat(base.getLvl(getBatimentOfRessources(res)), res);
    }

    public int RAWgetProdNextLvl(Constantes.typeRessource res, InfosBase base)
    {
        return Constantes.getProd(base.getLvl(getBatimentOfRessources(res)) + 1, res);
    }

    /**
     * retourne si avec les ressources actuelles la construction est possible.
     * Pour plus de précision il est possible d'utiliser la méthode RAW qui prend en paramètre les ressources directement
     * @param batiment
     * @param base
     * @return
     */
    public boolean isConstructionPossible(Constantes.typeBatiment batiment, InfosBase base)
    {
        return cache.getData(base).isConstructible.get(batiment);
    }

    /**
     * retourne combien la base produit de cette ressource par tour
     * @param res
     * @param base
     * @return
     */
    public int getProd(Constantes.typeRessource res, InfosBase base)
    {
        return cache.getData(base).prod.get(res);
    }

    /**
     * retourne la production qu'un batiment aurait si il était augmenté d'un niveau.
     * pour plus de valures, cf getValuesPrecalcule
     * @param res
     * @param base
     * @return
     */
    public int getProdNextLvl(Constantes.typeRessource res, InfosBase base)
    {
        return cache.getData(base).prodNext.get(res);
    }

    /**
     *  retourne le batiment qui produit la ressource
     *  retourne NONE si incohérent
     * @param ressource
     * @return
     */
    public typeBatiment getBatimentOfRessources(typeRessource ressource)
    {
        return Constantes.getBatimentOfRessources(ressource);
    }

    public int RAWgetCoutAmelioration(typeBatiment batiment, typeRessource ressource, InfosBase base)
    {
        return Constantes.getCout(batiment, base.getLvl(batiment), ressource);
    }

    /**
     * retourne le cout pour améliorer le batiment passé en paramètre. C'est possible de mettre TEMPS pour connaitre le temps de construction de
     * batiment
     * 
     * @param batiment
     * @param ressource
     * @param base
     * @return
     */
    public int getCoutAmelioration(typeBatiment batiment, typeRessource ressource, InfosBase base)
    {
        return cache.getData(base).cout.get(batiment).get(ressource);
    }

    public int RAWgetCoutPopGeneral(InfosBase base)
    {
        int cout = 0;
        for (typeBatiment bat : typeBatiment.values())
        {
            cout += RAWgetCoutPop(bat, base);
        }
        return cout;
    }

    /**
     * retourne le cout en pop de tout les batiments cumulés
     * 
     * @param base
     * @return
     */
    public int getCoutPopGeneral(InfosBase base)
    {
        return cache.getData(base).coutPopCurrent;
    }

    public int RAWgetCoutPop(typeBatiment batiment, InfosBase base)
    {
        return Constantes.getCout(batiment, base.getLvl(batiment) - 1, typeRessource.POPULATION);
    }

    public int getCoutPop(typeBatiment batiment, InfosBase base)
    {
        return cache.getData(base).coutPop.get(batiment);
    }

    /**
     * ça c'est pour l'IAManager ;)
     * 
     * @param cache
     */
    public void setCache(DBCache cache)
    {
        this.cache = cache;
    }

    public HashMap<typeBatiment, Integer> getDummyRepartitionPop(InfosBase base)
    {
        return cache.getData(base).dummyRepartition;
    }

    /**
     * retourne l'objet Values qui contient des infos précalculés.
     */
    public Values getValuePrecalcule()
    {
        return Constantes.getValues();
    }

    public int getMetalForWin()
    {
        return IAManager.getMetalForWin();
    }

    public int getNbTourMax()
    {
        return IAManager.getNbTourMax();
    }

}
