/**
 * 
 */
package core.ressources;

import api.IA.InfosBase;
import core.ressources.Constantes;
import core.ressources.Constantes.typeBatiment;

/**
 * @author tbxf1355
 * 
 */
public class InfosBaseMoteur
{

    public int          idBase;

    public int          quantiteBois;
    public int          quantitePierre;
    public int          quantiteMetal;

    public int          population;

    public int          lvlBucheron;
    public int          lvlCarriere;
    public int          lvlMine;
    public int          lvlFerme;

    public int          popBucheron;
    public int          popCarriere;
    public int          popMine;

    public typeBatiment constructionEnCours;
    public int          tempsEcouleDepuisDebutConstruction;

    public InfosBase    rel = null;

    /**
     * @return the popBucheron
     */
    public int getPopBucheron()
    {
        return popBucheron;
    }

    /**
     * @param popBucheron
     *            the popBucheron to set
     */
    public void setPopBucheron(int popBucheron)
    {
        this.popBucheron = popBucheron;
    }

    /**
     * @return the popCarriere
     */
    public int getPopCarriere()
    {
        return popCarriere;
    }

    /**
     * @param popCarriere
     *            the popCarriere to set
     */
    public void setPopCarriere(int popCarriere)
    {
        this.popCarriere = popCarriere;
    }

    /**
     * @return the popMine
     */
    public int getPopMine()
    {
        return popMine;
    }

    /**
     * @param popMine
     *            the popMine to set
     */
    public void setPopMine(int popMine)
    {
        this.popMine = popMine;
    }

    /**
     * @return the constructionEnCours
     */
    public typeBatiment getConstructionEnCours()
    {
        return constructionEnCours;
    }

    /**
     * @param constructionEnCours
     *            the constructionEnCours to set
     */
    public void setConstructionEnCours(typeBatiment constructionEnCours)
    {
        this.constructionEnCours = constructionEnCours;
    }

    /**
     * @return the idBase
     */
    public int getIdBase()
    {
        return idBase;
    }

    /**
     * @return the quantiteBois
     */
    public int getQuantiteBois()
    {
        return quantiteBois;
    }

    /**
     * @return the quantitePierre
     */
    public int getQuantitePierre()
    {
        return quantitePierre;
    }

    /**
     * @return the quantiteMetal
     */
    public int getQuantiteMetal()
    {
        return quantiteMetal;
    }

    /**
     * @return the population
     */
    public int getPopulation()
    {
        return population;
    }

    /**
     * @return the lvlBucheron
     */
    public int getLvlBucheron()
    {
        return lvlBucheron;
    }

    /**
     * @return the lvlCarriere
     */
    public int getLvlCarriere()
    {
        return lvlCarriere;
    }

    /**
     * @return the lvlMine
     */
    public int getLvlMine()
    {
        return lvlMine;
    }

    /**
     * @return the lvlFerme
     */
    public int getLvlFerme()
    {
        return lvlFerme;
    }

    public int getLvl(Constantes.typeBatiment batiment)
    {
        switch (batiment)
        {
        case BUCHERON:
            return lvlBucheron;
        case CARRIERE:
            return lvlCarriere;
        case FERME:
            return lvlFerme;
        case MINE:
            return lvlMine;
        case NONE:
            return 0;
        default:
            return 0;
        }
    }

    public void addLvl(Constantes.typeBatiment batiment)
    {
        switch (batiment)
        {
        case BUCHERON:
            lvlBucheron++;
            break;
        case CARRIERE:
            lvlCarriere++;
            break;
        case FERME:
            lvlFerme++;
            break;
        case MINE:
            lvlMine++;
            break;
        default:
            break;
        }
    }

    // constructeur avec tout les champs.

    public InfosBaseMoteur(int idBase, int b, int p, int m, int pop, int lvlB, int lvlC, int lvlM, int lvlF, int popB, int popC, int popM,
            typeBatiment constructionEnCours, int tempsEcouleDepuisDebutConstruction)
    {
        this.idBase = idBase;

        this.quantiteBois = b;
        this.quantitePierre = p;
        this.quantiteMetal = m;

        this.population = pop;

        this.lvlBucheron = lvlB;
        this.lvlCarriere = lvlC;
        this.lvlMine = lvlM;
        this.lvlFerme = lvlF;

        this.popBucheron = popB;
        this.popCarriere = popC;
        this.popMine = popM;

        this.constructionEnCours = constructionEnCours;
        this.tempsEcouleDepuisDebutConstruction = tempsEcouleDepuisDebutConstruction;

    }

    // constructeur par copie.
    public InfosBaseMoteur(InfosBaseMoteur info)
    {
        this(info.idBase, info.quantiteBois, info.quantitePierre, info.quantiteMetal, info.population, info.lvlBucheron, info.lvlCarriere,
                info.lvlMine, info.lvlFerme, info.popBucheron, info.popCarriere, info.popMine, info.constructionEnCours,
                info.tempsEcouleDepuisDebutConstruction);
    }
}
