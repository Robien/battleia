package tools;

import api.ressources.Environement;
import core.ressources.InfosBaseMoteur;

public class InfosToPrint
{

    public InfosBaseMoteur      infos       = null;
    public double               time        = 0;
    public double               tempsCumule = 0;

    public float                prodBois    = 100f;
    public float                prodPierre  = 100f;
    public float                prodMetal   = 100f;

    private final static String delimiter   = "/";
    private String              res;

    public InfosToPrint()
    {}

    public InfosToPrint(InfosBaseMoteur infos)
    {
        this.infos = infos;
    }

    @Override
    public String toString()
    {
        try
        {
            construct();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
        return res;
    }

    private void construct() throws Exception
    {
        if (infos == null)
        {
            throw new Exception("Log Non Printable");
        }
        res = new String();
        add(infos.quantiteBois);
        add(infos.quantitePierre);
        add(infos.quantiteMetal);
        add(infos.rel.population);

        add(infos.lvlBucheron);
        add(infos.lvlCarriere);
        add(infos.lvlMine);
        add(infos.lvlFerme);

        add(time);
        add(tempsCumule);
        add(infos.timeConstruct);
        add(infos.timePasConstruct);

        add(infos.rel.popBucheron);
        add(infos.rel.popCarriere);
        add(infos.rel.popMine);
        // add(infos.rel.population - infos.rel.popBucheron - infos.rel.popCarriere - infos.rel.popMine);
        add(Environement.get().getCoutPopGeneral(infos.rel) - infos.rel.population); // negatif = chomage, positif = postes non pourvus

        add(prodBois);
        add(prodMetal);
        add(prodPierre);
        add((prodBois + prodMetal + prodPierre) / 3);
        if (infos.rel.customValues != null && infos.rel.customValues.size() != 0)
        {
            for (Object o : infos.rel.customValues)
            {
                add(o);
            }
        }
    }

    private void add(Object o)
    {
        if (res.length() == 0)
        {
            res += o.toString();
        }
        else
        {
            res += delimiter + o.toString();
        }
    }

}
