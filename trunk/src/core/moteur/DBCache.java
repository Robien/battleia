package core.moteur;

import java.util.HashMap;

import api.IA.AbstractIA;
import api.IA.InfosBase;
import api.ressources.elements.Groupe;

/**
 * Cette classe contiend les données utile pour chaque base pour un tour donné
 * 
 */
public class DBCache
{

    private HashMap<AbstractIA, HashMap<Integer, Groupe>> groupes = new HashMap<>();
    private HashMap<InfosBase, DBData>                    datas;

    public DBCache()
    {
        purge();
    }

    public void purge()
    {
        datas = new HashMap<>();
        groupes = new HashMap<>();
    }

    public void addInfoBase(InfosBase base)
    {
        datas.put(base, new DBData(base));
    }

    public void addGroupes(HashMap<Integer, Groupe> groupes, AbstractIA ia)
    {
        this.groupes.put(ia, groupes);
    }

    public DBData getData(InfosBase base)
    {
        return datas.get(base);
    }

    public HashMap<Integer, Groupe> getGroupes(AbstractIA ia)
    {
        return groupes.get(ia);
    }

}
