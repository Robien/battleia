package core.moteur;

public class InfoResultat implements Comparable<InfoResultat>
{

    public Long  metal           = 0l;
    public float pointClassement = 0;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(InfoResultat o)
    {
        return (int) (pointClassement - o.pointClassement);
    }

}
