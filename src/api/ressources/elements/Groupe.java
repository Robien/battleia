package api.ressources.elements;

import api.IA.InfosBase;
import api.ressources.Environement;
import core.ressources.Constantes.typeUnite;

public class Groupe
{
    private int[]      units;
    private Case[][]   autour                 = null;
    private Case       destination            = null;
    private int        tempsRestant           = 0;

    private final int  id;
    private static int nbGroupe               = 0;

    private int        tempsEcouleDeplacement = 0;

    public Groupe()
    {
        id = nbGroupe++;
        init();
    }

    public Groupe(Case[][] position)
    {
        id = nbGroupe++;
        init();
        autour = position;
    }

    public Groupe(InfosBase info)
    {
        id = nbGroupe++;
        init();
        autour = info.caseAutourBase;
    }

    public Groupe(Groupe g)
    {
        id = g.id;
        init();
        for (typeUnite u : typeUnite.values())
        {
            units[u.ordinal()] = g.getNbUnite(u);
        }
        autour = g.autour;
        destination = g.destination;
    }

    public Groupe(Groupe g, InfosBase info)
    {
        id = g.id;
        init();
        for (typeUnite u : typeUnite.values())
        {
            units[u.ordinal()] = g.getNbUnite(u);
        }
        autour = info.caseAutourBase;
        destination = g.destination;
    }

    private void init()
    {
        units = new int[typeUnite.values().length];
    }

    public int getNbUnite(typeUnite type)
    {
        return units[type.ordinal()];
    }

    public void setNbUnite(typeUnite type, int nb)
    {
        units[type.ordinal()] = nb;
    }

    public Case[][] getEnvirons()
    {
        return autour;
    }

    public Case getPosition()
    {
        return autour[1][1];
    }

    public void setDestination(Case destination)
    {
        this.destination = destination;
    }

    public void setDestination(int posX, int posY)
    {
        this.destination = new Case(posX, posY);
    }

    /**
     * Le type de la Case n'est pas toujours correct
     * 
     * @return la destination du groupe
     */
    public Case getDestination()
    {
        return destination;
    }

    /**
     * Si le groupe est nouveau le temps restant n'as pas encore été calculé et retourne donc 0
     * Sauf si la fonction calculTempsRestant à été lancé.
     * 
     * @return
     */
    public int getTempsRestant()
    {
        return tempsRestant;
    }

    public int calculTempsRestant()
    {
        if (autour == null || destination == null)
        {
            return 0;
        }
        else
        {
            tempsRestant = (int) (Environement.get().getDistance(autour[1][1], destination) / Environement.get().getVitesseGroupe(this));
            return tempsRestant;
        }
    }

    public int getId()
    {
        return id;
    }

    /**
     * combien d'unit en plus dans le groupe par rapport à g
     * 
     * @param g
     * @return
     */
    public int[] diff(Groupe g)
    {
        int[] dif = units.clone();
        for (int i = 0; i < dif.length; i++)
        {
            dif[i] -= g.units[i];
        }

        return dif;
    }

    /**
     * vous n'avez pas besoin de lancer cette fonction...
     */
    public boolean avancerDUnTour()
    {
        if (getDestination() == null || getPosition() == null)
        {
            return false;
        }
        if (!getDestination().isSameAs(getPosition()))
        {
            tempsEcouleDeplacement++;
            if (tempsEcouleDeplacement >= Environement.get().getVitesseGroupe(this))
            {
                int diffX = Math.abs(getDestination().getPosX() - getPosition().getPosX());
                int diffY = Math.abs(getDestination().getPosY() - getPosition().getPosY());
                if (diffX > diffY)
                {
                    if (getDestination().getPosX() > getPosition().getPosX())
                    {
                        autour[1][1] = autour[2][1];
                    }
                    else
                    {
                        autour[1][1] = autour[0][1];
                    }
                }
                else
                {
                    if (getDestination().getPosY() > getPosition().getPosY())
                    {
                        autour[1][1] = autour[1][2];
                    }
                    else
                    {
                        autour[1][1] = autour[1][0];
                    }
                }
                return true;
            }
        }

        return false;
    }

    /**
     * vous avez interet à être balèze pour utiliser cette méthode ...
     * 
     * @param autour
     */
    public void setAutour(Case[][] autour)
    {
        this.autour = autour;
    }

}
