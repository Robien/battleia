package tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import tools.Log.tag;
import core.ressources.SerializableSavedSeed;

// methode pour sauvegarder des lancements
public class BackupGameManager
{
    private String                _path     = "log/";
    private String                _fileName = "lastConstant.ser";

    public SerializableSavedSeed seedList;

    public BackupGameManager()
    {
    	loadConstantes();
    }

    public BackupGameManager(String filename)
    {
        _fileName = filename;
        loadConstantes();
    }


    public long getSeed(int index)
    {
    	return seedList.getSeed(index);
    }
    
    public long getSeed()
    {
    	return seedList.getSeed(0);
    }
    
    public long getSize()
    {
    	return seedList.getSize();
    }
    
    public void saveSeed(long seed)
    {
    	seedList.addSeed(seed);
    	serialize();
    }

    private void serialize()
    {
    	// sauvegarde des constantes dans un fichier
        try
        {
            FileOutputStream fichier = new FileOutputStream(_path + _fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fichier);
            try
            {
                // sérialisation : écriture de l'objet dans le flux de sortie

                oos.writeObject(seedList);
                // on vide le tampon
                oos.flush();
                Log.print(tag.ERREUR, "Le fichier de sauvegarde à été modifié : " + _path + _fileName);
            }
            catch (java.io.IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                // fermeture des flux
                try
                {
                    oos.close();
                }
                finally
                {
                    fichier.close();
                }
            }
        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private void creatSerialFile()
    {
    	// sauvegarde des constantes dans un fichier
        try
        {
            FileOutputStream fichier = new FileOutputStream(_path + _fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fichier);
            try
            {
                // sérialisation : écriture de l'objet dans le flux de sortie

                oos.writeObject(seedList);
                // on vide le tampon
                oos.flush();
                Log.print(tag.ERREUR, "Le fichier de sauvegarde à été créé : " + _path + _fileName);
            }
            catch (java.io.IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                // fermeture des flux
                try
                {
                    oos.close();
                }
                finally
                {
                    fichier.close();
                }
            }
        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private void loadConstantes()
	{
    	FileInputStream fichier;
		try
		{
			fichier = new FileInputStream(_path + _fileName);
			ObjectInputStream ois = new ObjectInputStream(fichier);
			try
			{
				seedList = (SerializableSavedSeed) ois.readObject();
			}catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			} finally
			{
				// fermeture des flux
				try
				{
					ois.close();
				} finally
				{
					fichier.close();
				}
			}
		} catch (java.io.IOException e)
		{
			Log.print(tag.ERREUR, "Aucun fichier de sauvegarde, il sera crée : " + _path + _fileName);
			seedList = new SerializableSavedSeed();
			creatSerialFile();
		}  

	}

}
