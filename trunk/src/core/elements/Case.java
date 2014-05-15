package core.elements;

import tools.Log;
import tools.Log.tag;

public class Case
{

    public enum etatCase
    {
        LIBRE, OCCUPE, INHABITABLE, ESPACE
    }

    // position de la case
    private final int      posX;
    private final int      posY;

    // etat de la case
    private final etatCase etat;

    // id de la base et de l'IA sur la case si etat == OCCUPE
    private final int      base;
    private final int      ia;

    public Case(int posX, int posY, etatCase etat)
    {
        this.posX = posX;
        this.posY = posY;
        this.etat = etat;
        if (etat == etatCase.OCCUPE)
        {
            Log.print(tag.ERREUR, "création d'une case occupé sans base associé !");
        }
        base = 0;
        ia = 0;
    }

    public Case(int posX, int posY, int idBase, int idIa)
    {
        this.posX = posX;
        this.posY = posY;
        this.etat = etatCase.OCCUPE;
        base = idBase;
        ia = idIa;
    }

    public Case()
    {
        this.posX = 0;
        this.posY = 0;
        this.etat = etatCase.ESPACE;
        base = 0;
        ia = 0;
    }

    public Case(int x, int y)
    {
        this.posX = x;
        this.posY = y;
        this.etat = etatCase.ESPACE;
        base = 0;
        ia = 0;
    }

    /**
     * @return the posX
     */
    public int getPosX()
    {
        return posX;
    }

    /**
     * @return the posY
     */
    public int getPosY()
    {
        return posY;
    }

    /**
     * @return the etat
     */
    public etatCase getEtat()
    {
        return etat;
    }

    /**
     * @return the base
     */
    public int getBaseID()
    {
        return base;
    }

    /**
     * @return the ia
     */
    public int getIaID()
    {
        return ia;
    }

    /**
     * C'est les coordonnées les plus mal chaussé !
     * Hahahaha qu'est-ce qu'on rigole, hein ?
     * 
     * @return
     */
    public String getCoordonneesPrintable()
    {
        return "[" + getPosX() + ":" + getPosY() + "]";
    }

    public boolean isColonisable()
    {
        return etat == etatCase.LIBRE;
    }

    public boolean isMarchable()
    {
        return etat == etatCase.LIBRE || etat == etatCase.INHABITABLE;
    }

}
