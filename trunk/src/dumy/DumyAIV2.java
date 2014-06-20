package dumy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import tools.Log;
import tools.Log.tag;
import api.IA.AbstractIA;
import api.IA.InfosBase;
import api.ressources.Environement;
import api.ressources.elements.Case;
import api.ressources.elements.Groupe;
import core.ressources.Constantes.typeBatiment;
import core.ressources.Constantes.typeUnite;

public class DumyAIV2 extends AbstractIA
{

    private HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<>();
    private Random                                      r   = new Random(System.currentTimeMillis());

    public DumyAIV2()
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
                if (base.population < Environement.get().getCoutPopGeneral(base))
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

                    else if (Environement.get().isConstructionPossible(typeBatiment.CARRIERE, base))
                    {
                        base.constructionEnCours = typeBatiment.CARRIERE;
                        Log.print(tag.IADUMMY, "construction carriere");
                    }
                    else if (Environement.get().isConstructionPossible(typeBatiment.BUCHERON, base))
                    {
                        base.constructionEnCours = typeBatiment.BUCHERON;
                        Log.print(tag.IADUMMY, "construction bucheron");
                    }
                    else
                    {
                        // on ne peut rien construire... on attend en espérant qu'on produit assez pour construire quelque chose après...
                        Log.print(tag.IADUMMY, "construction rien du tout");
                    }

                }

            }

            if (base.population - Environement.get().getCoutPopGeneral(base) > 10 && base.quantiteMetal > 1000)
            {
                Groupe g = new Groupe(base);
                g.setNbUnite(typeUnite.PEON, 1);
                base.nouveauxGroupes.add(g);
            }

            for (Groupe g : Environement.get().getGroupes(this).values())
            {
                if (g.getDestination() != null)
                {
                    if (g.getDestination().isSameAs(g.getDestination()))
                    {
                        g.setDestination(getNextCase(g.getEnvirons()));
                        setStatus(g.getDestination(), 1);
                    }
                }
                else
                {
                    g.setDestination(getNextCase(g.getEnvirons()));
                    setStatus(g.getDestination(), 1);
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
        return "DuMy V2";
    }

    public int getStatus(Case c)
    {
        return getStatus(c.getPosX(), c.getPosY());
    }

    public int getStatus(int x, int y)
    {
        HashMap<Integer, Integer> ligne = map.get(x);
        if (ligne == null)
        {
            map.put(x, new HashMap<Integer, Integer>());
            ligne = map.get(x);
            ligne.put(y, 0);
            return 0;
        }
        else
        {
            Integer caseMap = ligne.get(y);
            if (caseMap == null)
            {
                ligne.put(y, 0);
                return 0;
            }
            else
            {
                return caseMap;
            }
        }
    }

    public void setStatus(Case c, int status)
    {
        setStatus(c.getPosX(), c.getPosY(), status);
    }

    public void setStatus(int x, int y, int status)
    {
        map.get(x).put(y, status);
    }

    public Case getNextCase(Case[][] autour)
    {
        if (autour[0][1].isMarchable() && getStatus(autour[0][1]) == 0)
        {
            return autour[0][1];
        }
        else if (autour[1][0].isMarchable() && getStatus(autour[1][0]) == 0)
        {
            return autour[1][0];
        }
        else if (autour[2][1].isMarchable() && getStatus(autour[2][1]) == 0)
        {
            return autour[2][1];
        }
        else if (autour[1][2].isMarchable() && getStatus(autour[1][2]) == 0)
        {
            return autour[1][2];
        }
        else
        {

            int int1 = Math.abs(r.nextInt()) % 3;
            int int2 = Math.abs(r.nextInt()) % 3;

            while (!autour[int1][int2].isMarchable())
            {
                int1 = Math.abs(r.nextInt()) % 3;
                int2 = Math.abs(r.nextInt()) % 3;

            }

            return autour[int1][int2];

            // if (autour[0][1].isMarchable())
            // {
            // return autour[0][1];
            // }
            // else if (autour[1][0].isMarchable())
            // {
            // return autour[1][0];
            // }
            // else if (autour[2][1].isMarchable())
            // {
            // return autour[2][1];
            // }
            // else if (autour[1][2].isMarchable())
            // {
            // return autour[1][2];
            // }
            // else
            // {
            // Log.print("V2", "ouais mais là aussi c'est pas de chance !");
            // return null;
            // }
        }
    }

}
