package tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import api.IA.AbstractIA;
import api.IA.InfosBase;

/**
 * 
 * sauvegarde les stats dans un fichier.
 * 
 */
public class LogFile
{

    private HashMap<AbstractIA, File>     filesIA   = new HashMap<>();
    private HashMap<InfosBase, File>      filesBase = new HashMap<>();
    private HashMap<File, BufferedWriter> buffers   = new HashMap<>();

    private String                        path      = "log/";

    public LogFile()
    {}

    public void add(AbstractIA ia)
    {
        filesIA.put(ia, createFile(ia.getName()));
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
