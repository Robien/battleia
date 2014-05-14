package core.ressources;

import java.io.Serializable;
import java.util.ArrayList;

import tools.Log;
import tools.Log.tag;

public class SerializableSavedSeed implements Serializable
{

	private static final long 	serialVersionUID	= -7540667650858359923L;

	public transient final int 	_sizeMax			= 15;
	public ArrayList<Long> 		savedSeed			= new ArrayList<Long>();

	public SerializableSavedSeed()
	{
	}

	// add a seed to the list of saved seed
	public void addSeed(long newseed)
	{
		// add the seed to the begining of the list
		if (savedSeed.size() < _sizeMax)
		{
			savedSeed.add(0, newseed);
		} else
		{
			savedSeed.remove(savedSeed.size() - 1);
			savedSeed.add(0, newseed);
		}
	}

	// get the seed at the position 'index' in the list of saved seed
	public long getSeed(int index)
	{
		if (savedSeed.size() <= 0)
		{
			Log.print(tag.ERREUR, "Try to acces to a empty list of saved seed, the answer must be 42");
			return 42; // erreur no seed save
		} else if (index < savedSeed.size() && index >= 0)
		{
			return savedSeed.get(index); // return the seed of index
		} else
		{
			// return the last seed because enter a invalid index
			return savedSeed.get(0);
		}
	}

	public String toString()
	{
		String myString = "Random Seed :\n";
		for (int i = 0; i < savedSeed.size(); i++)
		{
			myString += savedSeed.get(i).toString() + "\n";
		}
		return myString;
	}

	public int getSize()
	{
		return savedSeed.size();
	}
}