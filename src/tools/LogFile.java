package tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import tools.Log.tag;

import api.IA.AbstractIA;
import api.IA.InfosBase;

/**
 * sauvegarde les stats dans un fichier.
 * 
 */
public class LogFile
{

    private String                        path      = "log/";

    private HashMap<AbstractIA, File>     filesIA   = new HashMap<>();
    private HashMap<InfosBase, File>      filesBase = new HashMap<>();
    private HashMap<File, BufferedWriter> buffers   = new HashMap<>();

    public LogFile()
    {}

    public void add(AbstractIA ia)
    {
        boolean possible = true;
        for (AbstractIA ai2 : filesIA.keySet())
        {
            if (ia.getName() == ai2.getName())
            {
                Log.print(tag.ERREUR, "Attention, 2 IA avec le même nom ! c'est mal !");
                Log.print(tag.ERREUR, "Pour la peine, je ne print des logs seulement pour une des " + ai2.getName());
                possible = false;
            }
        }
        if (possible)
        {
            filesIA.put(ia, createFile(ia.getName()));
        }
    }

    public void add(InfosBase base)
    {
        filesBase.put(base, createFile("baseID:" + base.idBase));
    }

    public void writeStats(AbstractIA ia, int bois, int pierre, int metal, int pop, int lvlBucheron, int lvlCarriere, int lvlMine, int lvlFerme,
            long temps)
    {
        writeStats(buffers.get(filesIA.get(ia)), bois, pierre, metal, pop, lvlBucheron, lvlCarriere, lvlMine, lvlFerme, temps);
    }

    public void writeStats(InfosBase base, int bois, int pierre, int metal, int pop, int lvlBucheron, int lvlCarriere, int lvlMine, int lvlFerme,
            long temps)
    {
        writeStats(buffers.get(filesBase.get(base)), bois, pierre, metal, pop, lvlBucheron, lvlCarriere, lvlMine, lvlFerme, temps);
    }

    public void writeStats(AbstractIA ia, InfosToPrint infos)
    {
        writeStats(buffers.get(filesIA.get(ia)), infos);
    }

    public void writeStats(InfosBase base, InfosToPrint infos)
    {
        writeStats(buffers.get(filesBase.get(base)), infos);
    }

    private void writeStats(BufferedWriter w, int bois, int pierre, int metal, int pop, int lvlBucheron, int lvlCarriere, int lvlMine, int lvlFerme,
            long temps)
    {
        try
        {
            w.write(bois + "/" + pierre + "/" + metal + "/" + pop + "/" + lvlBucheron + "/" + lvlCarriere + "/" + lvlMine + "/" + lvlFerme + "/"
                    + temps + "\n");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void writeStats(BufferedWriter w, InfosToPrint infoToPrint)
    {
        try
        {
            w.write(infoToPrint.toString() + "\n");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void close()
    {
        for (File f : filesIA.values())
        {
            try
            {
                buffers.get(f).flush();
                buffers.get(f).close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private File createFile(String name)
    {
        name = name.replace(" ", "_").replaceAll("[\\W\\d]", ".");
        File f = new File(path + "IA." + name + ".txt");

        if (f.exists())
        {
            f.delete();
        }

        try
        {
            f.createNewFile();
            buffers.put(f, new BufferedWriter(new FileWriter(f)));
            return f;

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
