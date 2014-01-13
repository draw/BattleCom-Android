package net.euroboxonline.battlecom;

import java.io.Serializable;

/**
 * @author Draw
 * @version $Revision: 1.4 $ $Date: 2013-10-08 14:08:52 $
 *
 */
public class Location implements Serializable
{

    private long id;
    
    private double latitude = 51.069175;    //51.07224    51.06611
    private double longitude = -0.864745;    //-0.87317    -0.85632

    public Location( long id, double latitude, double longitude )
    {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId()
    {
        return id;
    }
    
    /**
     * @return the latitude
     */
    public final double getLatitude()
    {
        return latitude;
    }

    
    /**
     * @return the longitude
     */
    public final double getLongitude()
    {
        return longitude;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }
}
