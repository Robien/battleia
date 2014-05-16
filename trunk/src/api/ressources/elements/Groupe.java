package api.ressources.elements;

import api.ressources.Environement;
import core.ressources.Constantes.typeUnite;

public class Groupe
{
    private int[]    units;
    private Case[][] autour       = null;
    private Case     destination  = null;
    private int      tempsRestant = 0;

    public Groupe()
    {
        init();
    }

    public Groupe(Case[][] position)
    {
        init();
        autour = position;
    }

    public Groupe(Groupe g)
    {
        init();
        for (typeUnite u : typeUnite.values())
        {
            units[u.ordinal()] = g.getNbUnite(u);
        }
        autour = g.autour;
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

}
