package tools;

import java.util.HashMap;

import core.ConstantesDeJeu;

/**
 * 
 * permet de log plus facilement qu'avec System.out.println()
 * 
 */
public class Log
{
    public enum tag
    {
        IAMANAGER, CONSTANTES, JEU, CACHE, VALUES, IA, IADUMMY, STATS, ERREUR
    }

    private static HashMap<tag, Boolean> isPrintable = new HashMap<>();

    private static int                   nbErreur    = 0;

    // changer ici pour activer/désactiver les logs suivant les tags
    public static void printablePredef()
    {
        // il faut maintenant changer les préférences de logs dans la classe ConstantesDeJeu.java
        ConstantesDeJeu.printablePredef(isPrintable);
    }

    public static void print(String text)
    {
        if (!ConstantesDeJeu.mute)
        {
            System.out.println(text);
        }
    }

    public static void print(String tag, String text)
    {
        if (!ConstantesDeJeu.printOnlyGameInfo || tag == Log.tag.JEU.toString() || tag == Log.tag.ERREUR.toString())
        {

            if (ConstantesDeJeu.printTag)
            {
                print("[" + tag + "] " + text);
            }
            else
            {
                print(text);
            }
        }
    }

    public static void print(tag tag, String text)
    {
        if (tag == tools.Log.tag.ERREUR)
        {
            nbErreur++;
        }
        Boolean printable = isPrintable.get(tag);
        if (printable == null || printable == true)
        {
            print(tag.toString(), text);
        }
    }

    public static void printAllTag()
    {
        for (tag tags : tag.values())
        {
            isPrintable.put(tags, true);
        }
    }

    public static void printNoneTag()
    {
        for (tag tags : tag.values())
        {
            isPrintable.put(tags, false);
        }
    }

    public static void printTag(tag tag, boolean printable)
    {
        isPrintable.put(tag, printable);
    }

    public static void printNbErreur()
    {
        if (nbErreur != 0)
        {
            print("/!\\ Il y a eu " + nbErreur + " erreur(s) !");
        }
    }

}
