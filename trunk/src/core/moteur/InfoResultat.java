package core.moteur;

public class InfoResultat implements Comparable<InfoResultat>
{

    public Long  metal              = 0l;
    public float pointClassement    = 0;
    public int   nb1erePlace        = 0;
    public int   nb2erePlace        = 0;
    public int   nb3erePlace        = 0;
    public int   nbDisqualification = 0;

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
