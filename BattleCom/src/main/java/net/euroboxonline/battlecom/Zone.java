/**
 * 
 */
package net.euroboxonline.battlecom;

import java.io.Serializable;
import java.util.List;


/**
 * @author Draw
 * @version $Revision: 1.4 $ $Date: 2013-10-08 14:08:52 $
 *
 */
public class Zone implements Serializable
{

    private long id;

    private String description;

    private List<Location> coordinates = null;
    
    public Zone( long id, String description, List<Location> coordinates )
    {
        this.id = id;
        this.description = description;
        this.coordinates = coordinates;
    }

    public long getId()
    {
        return this.id;
    }
    
    public String getDescription()
    {
        return this.description;
    }
    
    public List<Location> getCoordinates()
    {
        return this.coordinates;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setCoordinates(List<Location> coordinates)
    {
        this.coordinates = coordinates;
    }

}
