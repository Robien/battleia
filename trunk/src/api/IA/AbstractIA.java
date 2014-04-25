package api.IA;

import java.util.ArrayList;

/**
 * classe à redéfinir pour créer son IA.
 * Il faut penser à en ajouter une instance dans la classe main après, sinon il ne va rien se passer ...
 */
public abstract class AbstractIA
{

    public abstract void nouveauTour(ArrayList<InfosBase> a);

    public abstract String getName();

}
