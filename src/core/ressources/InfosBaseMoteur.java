package core.ressources;

import java.util.HashMap;

import api.IA.AbstractIA;
import api.IA.InfosBase;
import api.ressources.elements.Case;
import api.ressources.elements.Groupe;
import core.ressources.Constantes.typeBatiment;
import core.ressources.Constantes.typeUnite;

public class InfosBaseMoteur
{

    public AbstractIA               ia               = null;
    public int                      idBase;

    public float                    quantiteBois     = 0;
    public float                    quantitePierre   = 0;
    public float                    quantiteMetal    = 0;

    public int                      population;

    public int                      lvlBucheron;
    public int                      lvlCarriere;
    public int                      lvlMine;
    public int                      lvlFerme;

    public int                      popBucheron;
    public int                      popCarriere;
    public int                      popMine;

    public typeBatiment             constructionEnCours;
    public int                      tempsEcouleDepuisDebutConstruction;

    public InfosBase                rel              = null;

    public int                      timeConstruct    = 0;
    public int                      timePasConstruct = 0;

    // V2 --- ne pas prendre en compte !
    public Case                     caseBase;
    public HashMap<Integer, Groupe> groupes          = new HashMap<>();

    public int                      peonEnVoyage     = 0;

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
    public float getQuantiteBois()
    {
        return quantiteBois;
    }

    /**
     * @return the quantitePierre
     */
    public float getQuantitePierre()
    {
        return quantitePierre;
    }

    /**
     * @return the quantiteMetal
     */
    public float getQuantiteMetal()
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

    public InfosBaseMoteur(int idBase, float b, float p, float m, int pop, int lvlB, int lvlC, int lvlM, int lvlF, int popB, int popC, int popM,
            typeBatiment constructionEnCours, int tempsEcouleDepuisDebutConstruction, AbstractIA ia)
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

        this.ia = ia;

    }

    // constructeur par copie.
    public InfosBaseMoteur(InfosBaseMoteur info)
    {
        this(info.idBase, info.quantiteBois, info.quantitePierre, info.quantiteMetal, info.population, info.lvlBucheron, info.lvlCarriere,
                info.lvlMine, info.lvlFerme, info.popBucheron, info.popCarriere, info.popMine, info.constructionEnCours,
                info.tempsEcouleDepuisDebutConstruction, info.ia);
    }

    public void addGroupe(Groupe g)
    {
        groupes.put(g.getId(), g);
        peonEnVoyage += g.getNbUnite(typeUnite.PEON);
    }
}
