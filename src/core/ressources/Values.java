package core.ressources;

import java.util.ArrayList;
import java.util.HashMap;

import tools.Log;
import tools.Log.tag;

import core.ressources.Constantes.typeBatiment;
import core.ressources.Constantes.typeRessource;

/**
 * le cache de valeurs précalculés
 * 
 */
public class Values
{

    // taille des données précalculées
    private int                                                               mSize = 0;

    // les données ...
    private ArrayList<HashMap<typeRessource, Integer>>                        prod  = new ArrayList<>();
    private ArrayList<HashMap<typeBatiment, HashMap<typeRessource, Integer>>> cout  = new ArrayList<>();

    public Values(int size)
    {
        compute(size);

    }

    // return le nombre de valeurs déjà calculées
    public int getSize()
    {
        return mSize;
    }

    // retourne la quantité de res produit au niveau lvl du batiment relatif (bois : bucheron)
    public int getProd(typeRessource res, int lvl)
    {
        return prod.get(lvl).get(res);
    }

    // retourne le cout en ressources res pour augmenter la batiment au niveau lvl+1
    public int getCout(typeBatiment bat, typeRessource res, int lvl)
    {
        return cout.get(lvl).get(bat).get(res);
    }

    // calcule les valeurs de getSize à size.
    // les valeures sont précalculés par le manageur d'IA et normalement c'est large.
    // si vraiment vous êtes chaud, vous pouvez l'utiliser quand même...
    public void compute(int size)
    {
        Log.print(tag.VALUES, "compute depuis " + mSize + " jusqu'à " + size);
        long debut = System.nanoTime();
        for (int i = mSize; i < size; i++)
        {
            HashMap<typeRessource, Integer> prodTmp = new HashMap<>();
            HashMap<typeBatiment, HashMap<typeRessource, Integer>> coutTmp = new HashMap<>();
            for (typeRessource res : typeRessource.values())
            {
                prodTmp.put(res, Constantes.getProd(i, res));
            }
            prod.add(prodTmp);
            for (typeBatiment bat : typeBatiment.values())
            {
                HashMap<typeRessource, Integer> dico = new HashMap<>();
                for (typeRessource res : typeRessource.values())
                {
                    dico.put(res, Constantes.getCout(bat, i, res));
                }
                coutTmp.put(bat, dico);
            }
            cout.add(coutTmp);

        }
        Log.print(tag.VALUES, "fait en " + ((System.nanoTime() - debut) / 1000) + "us (soit " + ((System.nanoTime() - debut) / 1000 / (size - mSize))
                + "us par valeur)");
        mSize = Math.max(size, mSize);
    }

}
