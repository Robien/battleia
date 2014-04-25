package api.IA;

import java.util.ArrayList;

/**
 * classe à redéfinir pour créer son IA.
 * Il faut penser à en ajouter une instance dans la classe main après, sinon il ne va rien se passer ...
 */
public abstract class AbstractIA
{

    // cette méthode est appelé à chaque nouveau tour
    // infosBases contient les infos sur les différentes bases controlé par l'IA. Pour l'instant le tableau contiend qu'un seul élément.
    // l'IA doit choisir quel action faire et l'inscrire directement dans "infosBases"
    // il faut choisir la construction à faire le cas échéant et donner la répartitions des travailleurs.
    public abstract void nouveauTour(ArrayList<InfosBase> infosBases);

    // cette methode doit juste retourner le nom de l'IA
    public abstract String getName();

}
