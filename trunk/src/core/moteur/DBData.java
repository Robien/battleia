/**
 * 
 */
package core.moteur;

import java.util.HashMap;

import tools.Log;
import tools.Log.tag;

import api.IA.InfosBase;
import api.ressources.Environement;
import core.ressources.Constantes;
import core.ressources.Constantes.typeBatiment;
import core.ressources.Constantes.typeRessource;

/**
 * @author tbxf1355
 * 
 */
public class DBData
{

    public HashMap<Constantes.typeBatiment, Boolean>                                    isConstructible  = new HashMap<Constantes.typeBatiment, Boolean>();
    public HashMap<Constantes.typeBatiment, HashMap<Constantes.typeRessource, Integer>> cout             = new HashMap<Constantes.typeBatiment, HashMap<typeRessource, Integer>>();
    public HashMap<Constantes.typeBatiment, Integer>                                    coutPop          = new HashMap<Constantes.typeBatiment, Integer>();
    public HashMap<Constantes.typeRessource, Integer>                                   prod             = new HashMap<>();
    public HashMap<Constantes.typeRessource, Integer>                                   prodNext         = new HashMap<>();

    public int                                                                          coutPopCurrent;
    public float                                                                        ratioProdMoyen;
    public HashMap<Constantes.typeBatiment, Integer>                                    dummyRepartition = new HashMap<>();

    public DBData(InfosBase base)
    {
        Log.print(tag.CACHE, "debut");
        // System.out.println("[cache] isConstructible");
        for (Constantes.typeBatiment batiment : Constantes.typeBatiment.values())
        {
            isConstructible.put(batiment, Environement.get().RAWisConstructionPossible(batiment, base));
        }
        // System.out.println("[cache] prod&prodNext");
        for (Constantes.typeRessource res : Constantes.typeRessource.values())
        {
            prod.put(res, Environement.get().RAWgetProd(res, base));
            prodNext.put(res, Environement.get().RAWgetProdNextLvl(res, base));
        }

        // System.out.println("[cache] cout");
        for (Constantes.typeBatiment batiment : Constantes.typeBatiment.values())
        {
            HashMap<Constantes.typeRessource, Integer> coutRes = new HashMap<>();
            for (Constantes.typeRessource res : Constantes.typeRessource.values())
            {
                coutRes.put(res, Environement.get().RAWgetCoutAmelioration(batiment, res, base));
            }
            cout.put(batiment, coutRes);
        }
        // System.out.println("[cache] coutpop");
        for (Constantes.typeBatiment batiment : Constantes.typeBatiment.values())
        {
            coutPop.put(batiment, Environement.get().RAWgetCoutPop(batiment, base));
        }

        // System.out.println("[cache] gestion dummy de la pop");
        // calulons la gestion dummy de la pop
        coutPopCurrent = Environement.get().RAWgetCoutPopGeneral(base);
        if (coutPopCurrent == 0)
        {
            ratioProdMoyen = 1;
        }
        else
        {
            ratioProdMoyen = (float) prod.get(typeRessource.POPULATION) / (float) coutPopCurrent;
            if (ratioProdMoyen > 1)
            {
                ratioProdMoyen = 1;
            }
        }

        int popRestant = prod.get(typeRessource.POPULATION);

        for (Constantes.typeBatiment batiment : Constantes.typeBatiment.values())
        {
            int ut = (int) (Environement.get().RAWgetCoutPop(batiment, base) * ratioProdMoyen);
            popRestant -= ut;
            dummyRepartition.put(batiment, ut);
        }

        // si popRestant est negatif, alors c'est qu'il y a eu un soucis. Donc boucle infinie ! mouhahaha
        while (popRestant != 0)
        {
            int poprestantdebut = popRestant;
            // System.out.println("[cache] reste : " + popRestant);

            for (Constantes.typeBatiment batiment : Constantes.typeBatiment.values())
            {
                if (popRestant != 0)
                {
                    if (dummyRepartition.get(batiment) < Environement.get().RAWgetCoutPop(batiment, base))
                    {
                        dummyRepartition.put(batiment, dummyRepartition.get(batiment) + 1);
                        popRestant--;
                    }
                }
            }
            if (popRestant == poprestantdebut)
            {
                popRestant = 0;
            }
        }
        Log.print(tag.CACHE, "resultat dummy " + dummyRepartition.get(typeBatiment.BUCHERON));
        Log.print(tag.CACHE, "resultat dummy " + dummyRepartition.get(typeBatiment.CARRIERE));
        Log.print(tag.CACHE, "resultat dummy " + dummyRepartition.get(typeBatiment.MINE));
        Log.print(tag.CACHE, "fin");

    }
}
