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
    private int      posX;
    private int      posY;

    // etat de la case
    private etatCase etat;

    // id de la base sur la case si etat == OCCUPE
    private int      base;

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
    }

    public Case(int posX, int posY, int idBase)
    {
        this.posX = posX;
        this.posY = posY;
        this.etat = etatCase.OCCUPE;
        base = idBase;
    }

    public Case()
    {
        this.posX = 0;
        this.posY = 0;
        this.etat = etatCase.ESPACE;
        base = 0;
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
     * C'est les coordonnées les plus mal chaussé !
     * Hahahaha qu'est-ce qu'on rigole, hein ?
     * 
     * @return
     */
    public String getCoordonneesPrintable()
    {
        return "[" + getPosX() + ":" + getPosY() + "]";
    }

}
