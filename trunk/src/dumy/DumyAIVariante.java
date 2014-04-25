package dumy;

import java.util.ArrayList;

import tools.Log;
import tools.Log.tag;

import core.ressources.Constantes.typeBatiment;
import core.ressources.Constantes.typeRessource;

import api.IA.AbstractIA;
import api.IA.InfosBase;
import api.ressources.Environement;

// exactement comme l'autre, juste l'ordre de construction n'est pas le même
public class DumyAIVariante extends AbstractIA
{

    public DumyAIVariante()
    {}

    /*
     * (non-Javadoc)
     * 
     * @see api.AbstractIA#nouveauTour(java.util.ArrayList)
     */
    @Override
    public void nouveauTour(ArrayList<InfosBase> a)
    {
        // on parcours toutes les bases ... pour l'instant c'est facile y'en a qu'une, mais bon quand même.
        for (InfosBase infosBase : a)
        {

            // si y'a déjà une construction en cours on ne fait rien...
            if (infosBase.constructionEnCours == typeBatiment.NONE)
            {
                // maintenant on va choisir quel construction on va faire

                // si on a un soucis de pop, on va faire une ferme en prioritée.
                if (Environement.get().getProd(typeRessource.POPULATION, infosBase) < Environement.get().getCoutPopGeneral(infosBase))
                {
                    // System.out.println("soucis pop");
                    if (Environement.get().isConstructionPossible(typeBatiment.FERME, infosBase))
                    {
                        infosBase.constructionEnCours = typeBatiment.FERME;
                        Log.print(tag.IA, "construction ferme");
                    }
                    else
                    {
                        Log.print(tag.IA, "construction ferme en attente");
                        // si on a pas les ressources, on attand de les avoir....
                    }

                }
                else
                {
                    // pas de soucis de pop, alors on construit autre chose!
                    if (Environement.get().isConstructionPossible(typeBatiment.MINE, infosBase))
                    {
                        infosBase.constructionEnCours = typeBatiment.MINE;
                        Log.print(tag.IA, "construction mine");
                    }
                    else if (Environement.get().isConstructionPossible(typeBatiment.BUCHERON, infosBase))
                    {
                        infosBase.constructionEnCours = typeBatiment.BUCHERON;
                        Log.print(tag.IA, "construction bucheron");
                    }
                    else if (Environement.get().isConstructionPossible(typeBatiment.CARRIERE, infosBase))
                    {
                        infosBase.constructionEnCours = typeBatiment.CARRIERE;
                        Log.print(tag.IA, "construction carriere");
                    }
                    else
                    {
                        // on ne peut rien construire... on attend en espérant qu'on produit assez pour construire quelque chose après...
                        Log.print(tag.IA, "construction rien du tout");
                    }

                }

            }

            // on s'occupe de la gestion de la pop... une gestion très simple est déjà caclulé pour nous alors on l'utilise.
            infosBase.popBucheron = Environement.get().getDummyRepartitionPop(infosBase).get(typeBatiment.BUCHERON);
            infosBase.popCarriere = Environement.get().getDummyRepartitionPop(infosBase).get(typeBatiment.CARRIERE);
            infosBase.popMine = Environement.get().getDummyRepartitionPop(infosBase).get(typeBatiment.MINE);
            // ça remplit tout les batiments avec le bon nombre si on a assez de pop, sinon ça applique un ratio égal à tous les batiments

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see api.AbstractIA#getName()
     */
    @Override
    public String getName()
    {
        return "DuMy super kikou super AI";
    }

}
