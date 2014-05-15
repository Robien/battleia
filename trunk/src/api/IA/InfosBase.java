package api.IA;

import java.util.ArrayList;

import core.elements.Case;
import core.moteur.TerrainManager;
import core.ressources.Constantes;
import core.ressources.InfosBaseMoteur;
import core.ressources.Constantes.typeBatiment;

// toutes les infos utiles sont stoqué dans cette structure.
// il y a aussi la place pour donner les instructions de constructions (regarder les champs qui ne sont pas finaux)
// les champs sont public (structure) mais il y aussi des getter... et du coup ben ça sert à rien!
public class InfosBase
{

    // permet de différencier une base d'une autre. Ce chiffre reste constant tout au long de la partie.
    public final int         idBase;

    // stock de la base
    public final int         quantiteBois;
    public final int         quantitePierre;
    public final int         quantiteMetal;

    public final int         population;

    // niveau de construction des batiments de la base
    public final int         lvlBucheron;
    public final int         lvlCarriere;
    public final int         lvlMine;
    public final int         lvlFerme;

    // champs à remplir...
    public int               popBucheron;
    public int               popCarriere;
    public int               popMine;

    public typeBatiment      constructionEnCours;
    public final int         tempsRestantConstruction;

    // ajouter ici des données qui seront ajoutées en fin de fichier de logs
    public ArrayList<Object> customValues = new ArrayList<>();

    // V2 --- ne pas prendre en compte !
    public Case              caseBase;
    public Case[][]          caseAutourBase;                  // caseAutourBase[1][1] = caseBase

    // retourne le niveau de construction du batiment batiment
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

    // constructeur avec tout les champs.

    public InfosBase(int idBase, int b, int p, int m, int pop, int lvlB, int lvlC, int lvlM, int lvlF, int popB, int popC, int popM,
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
        this.tempsRestantConstruction = tempsEcouleDepuisDebutConstruction;

    }

    // constructeur par copie.
    public InfosBase(InfosBase info)
    {
        this(info.idBase, info.quantiteBois, info.quantitePierre, info.quantiteMetal, info.population, info.lvlBucheron, info.lvlCarriere,
                info.lvlMine, info.lvlFerme, info.popBucheron, info.popCarriere, info.popMine, info.constructionEnCours,
                info.tempsRestantConstruction);
    }

    // constructeur par copie2.
    public InfosBase(InfosBaseMoteur info)
    {
        this(info.idBase, (int) info.quantiteBois, (int) info.quantitePierre, (int) info.quantiteMetal, info.population, info.lvlBucheron,
                info.lvlCarriere, info.lvlMine, info.lvlFerme, info.popBucheron, info.popCarriere, info.popMine, info.constructionEnCours,
                info.tempsEcouleDepuisDebutConstruction);
        caseBase = info.caseBase;
        caseAutourBase = TerrainManager.get().getCasesAutour(caseBase);
    }
}
