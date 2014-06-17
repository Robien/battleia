package core.moteur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import tools.Log;
import tools.Log.tag;
import api.IA.AbstractIA;
import core.ConstantesDeJeu;
import core.ressources.InfosBaseMoteur;

public class Resultats
{

    // vous pouvez entrer ici le nombre de parties que vous prévoyez de simuler pour avoir de meilleurs messages de logs
    // à vous après de faire réélement ce nombre de simulations !
    public int                                                     nbParties  = 0;

    private ArrayList<String>                                      ias        = new ArrayList<>();
    private HashMap<String, InfoResultat>                          iaResultat = new HashMap<>();
    private ArrayList<HashMap<String, ArrayList<InfosBaseMoteur>>> bases      = new ArrayList<>();
    private ArrayList<HashMap<String, Long>>                       temps      = new ArrayList<>();

    private static long                                            timeDebut  = System.currentTimeMillis();

    /** Holder to make the singleton */
    private static class SingletonHolder
    {
        private final static Resultats instance = new Resultats();
    }

    /**
     * @return the unique instance of Resultats
     */
    public static Resultats get()
    {
        return SingletonHolder.instance;
    }

    private Resultats()
    {}

    public void init(ArrayList<AbstractIA> ias)
    {
        if (this.ias.size() == 0)
        {
            for (AbstractIA ia : ias)
            {
                this.ias.add(ia.getName());
                this.iaResultat.put(ia.getName(), new InfoResultat());
            }
        }
    }

    public void addResult(HashMap<AbstractIA, ArrayList<InfosBaseMoteur>> bases, HashMap<AbstractIA, Long> temps)
    {
        if (nbParties != 0 && nbParties != 1)
        {
            boolean logMute = ConstantesDeJeu.mute;
            ConstantesDeJeu.mute = false;

            float time = (System.currentTimeMillis() - timeDebut) / 1000f;
            time = (nbParties - this.bases.size() - 1) * time / ((float) this.bases.size() + 1f);
            Log.print(tag.JEU, "fin partie " + (this.bases.size() + 1) + "/" + nbParties + " (" + (100 * (this.bases.size() + 1) / nbParties)
                    + "% aprox :" + (int) time + "s)");
            ConstantesDeJeu.mute = logMute;
        }
        else
        {
            Log.print(tag.JEU, "fin partie " + (this.bases.size() + 1));
        }
        HashMap<String, ArrayList<InfosBaseMoteur>> infos = new HashMap<>();
        for (AbstractIA ia : bases.keySet())
        {
            // ArrayList<InfosBaseMoteur> infosTmp = new ArrayList<>();
            // for (InfosBaseMoteur info : bases.get(ia))
            // {
            // infosTmp.add(new InfosBaseMoteur(info));
            // System.out.println(info.quantiteMetal);
            // }
            infos.put(ia.getName(), bases.get(ia));
        }
        this.bases.add(infos);

        HashMap<String, Long> t = new HashMap<>();

        for (AbstractIA ia : temps.keySet())
        {
            t.put(ia.getName(), temps.get(ia));
        }

        this.temps.add(t);
    }

    public void compute()
    {
        boolean logMute = ConstantesDeJeu.mute;
        ConstantesDeJeu.mute = false;
        if (bases.size() != 1)
        {
            Log.print(tag.JEU, "Résultats de " + bases.size() + " parties :");
        }

        for (int i = 0; i < bases.size(); i++)
        {
            HashMap<String, Long> allMetal = new HashMap<>();
            for (String ia : ias)
            {
                long metal = 0;
                // Log.print("IA " + ia);
                for (InfosBaseMoteur base : bases.get(i).get(ia))
                {
                    // Log.print("+ " + base.quantiteMetal);
                    metal += (long) base.quantiteMetal;
                }
                allMetal.put(ia, metal);
                // Log.print("= " + metal);
            }
            allMetal = sortByValue(allMetal);
            int pos = allMetal.size();
            for (String ia : allMetal.keySet())
            {
                iaResultat.get(ia).metal += allMetal.get(ia);
                if (pos == 1)
                {
                    iaResultat.get(ia).nb1erePlace++;
                }
                else if (pos == 2)
                {
                    iaResultat.get(ia).nb2erePlace++;
                }
                else if (pos == 3)
                {
                    iaResultat.get(ia).nb3erePlace++;
                }
                if (bases.get(i).get(ia).get(0).ia.isDiqualifie)
                {
                    iaResultat.get(ia).nbDisqualification++;
                }
                // else
                // {
                // }
                iaResultat.get(ia).pointClassement += 100f / (float) pos;
                pos--;

            }
        }

        iaResultat = reverseSortByValue(iaResultat);
        int maxSize = 0;
        for (String ia : iaResultat.keySet())
        {
            maxSize = Math.max(maxSize, ia.length());
        }
        int pos = 0;
        for (String ia : iaResultat.keySet())
        {
            String tmp = ia;
            for (int i = 0; i < maxSize - ia.length(); i++)
            {
                tmp += " ";
            }
            if (bases.size() != 1)
            {
                Log.print(
                        tag.JEU,
                        (++pos)
                                + " -  "
                                + tmp
                                + "\t points : "
                                + (int) iaResultat.get(ia).pointClassement
                                + "\t total Metal : "
                                + iaResultat.get(ia).metal
                                + "\t ["
                                + iaResultat.get(ia).nb1erePlace
                                + "/"
                                + iaResultat.get(ia).nb2erePlace
                                + "/"
                                + iaResultat.get(ia).nb3erePlace
                                + "]\t["
                                + (int) (100 * (float) iaResultat.get(ia).nb1erePlace / (float) bases.size())
                                + "%/"
                                + (int) (100 * (float) iaResultat.get(ia).nb2erePlace / (float) bases.size())
                                + "%/"
                                + (int) (100 * (float) iaResultat.get(ia).nb3erePlace / (float) bases.size())
                                + "%]\t (podium :"
                                + (iaResultat.get(ia).nb1erePlace + iaResultat.get(ia).nb2erePlace + iaResultat.get(ia).nb3erePlace)
                                + "="
                                + (int) (100 * (float) (iaResultat.get(ia).nb1erePlace + iaResultat.get(ia).nb2erePlace + iaResultat.get(ia).nb3erePlace) / (float) bases
                                        .size()) + "%) disqualification :" + iaResultat.get(ia).nbDisqualification + " ("
                                + (int) (100 * (float) iaResultat.get(ia).nbDisqualification / (float) bases.size()) + "%)");
            }
            else
            {
                Log.print(tag.JEU, (++pos) + " -  " + tmp + "\t total Metal : " + iaResultat.get(ia).metal);

            }
        }
        ConstantesDeJeu.mute = logMute;

    }

    private <K, V extends Comparable<? super V>> HashMap<K, V> sortByValue(Map<K, V> map)
    {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>()
        {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        HashMap<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    private <K, V extends Comparable<? super V>> HashMap<K, V> reverseSortByValue(Map<K, V> map)
    {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>()
        {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        HashMap<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

}
