package tools;

import java.util.HashMap;

/**
 * 
 * permet de log plus facilement qu'avec System.out.println()
 * 
 */
public class Log
{
    public enum tag
    {
        IAMANAGER, JEU, CACHE, VALUES, IA, IADUMMY, STATS, ERREUR
    }

    private static HashMap<tag, Boolean> isPrintable = new HashMap<>();

    private static boolean               printTag    = true;

    // changer ici pour activer/désactiver les logs suivant les tags
    public static void printablePredef()
    {
        isPrintable.put(tag.IAMANAGER, false);
        isPrintable.put(tag.JEU, true);
        isPrintable.put(tag.CACHE, false);
        isPrintable.put(tag.VALUES, false);
        isPrintable.put(tag.IADUMMY, false);
        isPrintable.put(tag.IA, true);
        isPrintable.put(tag.STATS, true);
        isPrintable.put(tag.ERREUR, true);
    }

    public static void print(String text)
    {
        System.out.println(text);
    }

    public static void print(String tag, String text)
    {
        if (printTag)
        {
            System.out.println("[" + tag + "] " + text);
        }
        else
        {
            System.out.println(text);
        }
    }

    public static void print(tag tag, String text)
    {
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

}
