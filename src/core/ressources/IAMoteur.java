/**
 * 
 */
package core.ressources;

import java.util.ArrayList;
import java.util.HashMap;

import core.moteur.TerrainManager;

import api.IA.AbstractIA;
import api.ressources.elements.Groupe;

/**
 * @author tbxf1355
 * 
 */
public class IAMoteur
{

    public AbstractIA        rel;
    public ArrayList<Groupe> groupes = new ArrayList<>();

    public IAMoteur(AbstractIA ia)
    {
        rel = ia;

    }

    public void avanceDUnTour(TerrainManager terrain)
    {

        for (Groupe g : groupes)
        {
            if (g.avancerDUnTour())
            {
                g.setAutour(terrain.getCasesAutour(g.getPosition()));
            }
        }
    }

    public HashMap<Integer, Groupe> getGroupesCopy(TerrainManager terrain)
    {
        HashMap<Integer, Groupe> res = new HashMap<>();

        for (Groupe g : groupes)
        {
            Groupe g2 = new Groupe(g);
            g2.setAutour(terrain.getCasesAutour(g.getPosition()));
            res.put(g.getId(), new Groupe(g));
        }

        return res;
    }
    
    public void majDestination(Groupe g)
    {
        for (Groupe g2 : groupes)
        {
            if (g2.getId() == g.getId())
            {
                g2.setDestination(g.getDestination());
            }
        }
    }

}
