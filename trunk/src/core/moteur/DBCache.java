/**
 * 
 */
package core.moteur;

import java.util.HashMap;

import api.IA.InfosBase;

public class DBCache
{

    private HashMap<InfosBase, DBData> datas;

    public DBCache()
    {
        purge();
    }

    public void purge()
    {
        datas = new HashMap<>();
    }

    public void addInfoBase(InfosBase base)
    {
        datas.put(base, new DBData(base));
    }

    public DBData getData(InfosBase base)
    {
        return datas.get(base);
    }

}
