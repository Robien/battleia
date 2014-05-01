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

    @Override
    public void nouveauTour(ArrayList<InfosBase> infosBases)
    {
        // on parcours toutes les bases ... pour l'instant c'est facile y'en a qu'une, mais bon quand même.
        for (InfosBase base : infosBases)
        {

            // si y'a déjà une construction en cours on ne fait rien...
            if (base.constructionEnCours == typeBatiment.NONE)
            {
                // maintenant on va choisir quel construction on va faire

                // si on a un soucis de pop, on va faire une ferme en prioritée.
                if (Environement.get().getProd(typeRessource.POPULATION, base) < Environement.get().getCoutPopGeneral(base))
                {
                    // System.out.println("soucis pop");
                    if (Environement.get().isConstructionPossible(typeBatiment.FERME, base))
                    {
                        base.constructionEnCours = typeBatiment.FERME;
                        Log.print(tag.IADUMMY, "construction ferme");
                    }
                    else
                    {
                        Log.print(tag.IADUMMY, "construction ferme en attente");
                        // si on a pas les ressources, on attand de les avoir....
                    }

                }
                else
                {
                    // pas de soucis de pop, alors on construit autre chose!
                    if (Environement.get().isConstructionPossible(typeBatiment.MINE, base))
                    {
                        base.constructionEnCours = typeBatiment.MINE;
                        Log.print(tag.IADUMMY, "construction mine");
                    }
                    else if (Environement.get().isConstructionPossible(typeBatiment.BUCHERON, base))
                    {
                        base.constructionEnCours = typeBatiment.BUCHERON;
                        Log.print(tag.IADUMMY, "construction bucheron");
                    }
                    else if (Environement.get().isConstructionPossible(typeBatiment.CARRIERE, base))
                    {
                        base.constructionEnCours = typeBatiment.CARRIERE;
                        Log.print(tag.IADUMMY, "construction carriere");
                    }
                    else
                    {
                        // on ne peut rien construire... on attend en espérant qu'on produit assez pour construire quelque chose après...
                        Log.print(tag.IADUMMY, "construction rien du tout");
                    }

                }

            }

            // on s'occupe de la gestion de la pop... une gestion très simple est déjà calculée pour nous alors on l'utilise.
            base.popBucheron = Environement.get().getDummyRepartitionPop(base).get(typeBatiment.BUCHERON);
            base.popCarriere = Environement.get().getDummyRepartitionPop(base).get(typeBatiment.CARRIERE);
            base.popMine = Environement.get().getDummyRepartitionPop(base).get(typeBatiment.MINE);
            // ça remplit tout les batiments avec le bon nombre si on a assez de pop, sinon ça applique un ratio égal à tous les batiments

        }

    }

    @Override
    public String getName()
    {
        return "DuMy super kikou super AI";
    }

}
