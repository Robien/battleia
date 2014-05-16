package core.moteur;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import tools.Log;
import tools.Log.tag;
import api.ressources.elements.Case;
import api.ressources.elements.Case.etatCase;
import core.ressources.Constantes;
import core.ressources.InfosBaseMoteur;

public class TerrainManager
{

    private Case[][] terrain;

    /** Holder to make the singleton */
    private static class SingletonHolder
    {
        private static TerrainManager instance = new TerrainManager();
    }

    /**
     * @return the unique instance of TerrainManager
     */
    public static TerrainManager get()
    {
        return SingletonHolder.instance;
    }

    /**
     * The constructor is private because you need to use get() to have the unique instance of this class
     */
    private TerrainManager()
    {}

    /**
     * Construit un terrain et positionne les bases des IA dedans.
     * 
     * @param bases
     */
    public void build(Collection<ArrayList<InfosBaseMoteur>> bases)
    {

        int nbBase = 0;
        ArrayList<InfosBaseMoteur> basesRandom = new ArrayList<>();
        for (ArrayList<InfosBaseMoteur> arrayList : bases)
        {
            nbBase += arrayList.size();
            basesRandom.addAll((arrayList));
        }

        // TODO : mettre un boolean
        // TODO : sauvgarde d'état ?
        // TODO : mettre les bases de la même IA proche les unes des autres ? Pour l'instant ça va y'a qu'une base par IA en début de partie
        Collections.shuffle(basesRandom);

        int cote = (int) Math.ceil(Math.sqrt(nbBase));
        int diff = cote * cote - nbBase;

        terrain = new Case[Constantes.get().tailleX][Constantes.get().tailleY];

        for (int i = 0; i < terrain.length; i++)
        {
            for (int j = 0; j < terrain[i].length; j++)
            {
                terrain[i][j] = new Case(i, j, etatCase.LIBRE);
            }

        }

        int numBase = 0;
        int compteLine = 0;
        ArrayList<Integer> arrayi = getSpaceForItt(cote);
        for (Integer i : arrayi)
        {
            int posX = (i + 1) * terrain.length / (cote + 1);
            int redParCote = diff / (cote - compteLine);
            compteLine++;
            ArrayList<Integer> arrayj = getSpaceForItt(cote - redParCote);
            for (Integer j : arrayj)
            {

                int posY = (j + 1) * terrain[i].length / (cote - redParCote + 1);

                terrain[posX][posY] = new Case(posX, posY, basesRandom.get(numBase).idBase, basesRandom.get(numBase).ia.id);
                basesRandom.get(numBase).caseBase = terrain[posX][posY];
                numBase++;

            }
            diff -= redParCote;

        }
        if (diff != 0)
        {
            Log.print(tag.ERREUR, "Aie aie aie, carramba !");
        }

        // TODO: print les logs plus tard ...
        // for (InfosBaseMoteur infosBaseMoteur : basesRandom)
        // {
        // Log.print(tag.JEU, "base id " + infosBaseMoteur.idBase + " " + infosBaseMoteur.caseBase.getCoordonneesPrintable());
        // }

        // print();

    }

    /**
     * Et si je ne commentais pas cette fonction bizarre et je laissais les gens essayer de comprendre se que ça fait ?
     * 
     * @param sizePlusOne
     * @return
     */
    private ArrayList<Integer> getSpaceForItt(int sizePlusOne)
    {
        ArrayList<Integer> array = new ArrayList<>();
        boolean sens = true;
        int valueMin = 0;
        int valueMax = sizePlusOne - 1;
        for (int i = 0; i < sizePlusOne; i++)
        {
            if (sens)
            {
                array.add(valueMin);
                valueMin++;
            }
            else
            {
                array.add(valueMax);
                valueMax--;
            }
            sens = !sens;
        }

        return array;
    }

    public void print()
    {
        for (int i = 0; i < terrain.length; i += 1)
        {
            for (int j = 0; j < terrain[i].length; j += 1)
            {
                switch (terrain[i][j].getEtat())
                {
                case OCCUPE:
                    System.out.print("X");
                    break;

                default:
                    System.out.print(".");
                    break;
                }
            }

            System.out.println();
        }
    }

    public ArrayList<Case> getListeCasesAutour(Case c)
    {
        ArrayList<Case> cases = new ArrayList<>();
        for (int i = -1; i < 1; i++)
        {
            for (int j = -1; j < 1; j++)
            {
                if (i != 0 && j != 0)
                {
                    if (c.getPosX() + i < 0 || c.getPosX() + i > terrain.length || c.getPosY() + j < 0 || c.getPosY() + j > terrain[0].length)
                    {

                    }
                    else
                    {
                        cases.add(terrain[c.getPosX() + i][c.getPosY() + j]);
                    }
                }
            }

        }
        return cases;
    }

    public Case[][] getCasesAutour(Case c)
    {
        Case[][] cases = new Case[3][3];
        for (int i = -1; i < 1; i++)
        {
            for (int j = -1; j < 1; j++)
            {
                if (i != 0 && j != 0)
                {
                    if (c.getPosX() + i < 0 || c.getPosX() + i > terrain.length || c.getPosY() + j < 0 || c.getPosY() + j > terrain[0].length)
                    {
                        cases[i + 1][j + 1] = new Case(c.getPosX() + i, c.getPosY() + j);
                        // constructeur par défaut = case hors-terrain
                    }
                    else
                    {
                        cases[i + 1][j + 1] = (terrain[c.getPosX() + i][c.getPosY() + j]);
                    }
                }
                else
                {
                    cases[0][0] = c;
                }
            }

        }
        return cases;
    }

}
