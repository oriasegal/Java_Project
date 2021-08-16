package geometries;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Point3D;
import primitives.Ray;

/**
 * Geometries class, implements the Intersectable interface
 * @author Hilla and Oria
 */
public class Geometries implements Intersectable
{
	private ArrayList<Intersectable> geoIntersect;
	
	/**
	 * The Geometries default constructor, sets a new ArrayList for the geoIntersect field
	 */
	public Geometries()
	{
		geoIntersect = new ArrayList<Intersectable>();	//We chose an array list because we scan the list and just add items and don't delete
	}
	
	/**
	 * The Geometries constructor, adds the sent geometries to the geoIntersect field (an ArrayList)
	 * @param geometries zero or more geometries 
	 */
	public Geometries(Intersectable... geometries)
	{
		add(geometries);
	}
	
	/**
	 * The getGeoCollection function that returns the geoIntersect filed of the calling Geometries object
	 * @return this.geoIntersect
	 */
	public List<Intersectable> getGeoCollection()
	{
		return this.geoIntersect;
	}
	
	/**
	 * Add function that adds more geometries to the geoIntersect field of the calling Geometries object
	 * @param geometries zero or more geometries 
	 */
	public void add(Intersectable... geometries) //add an unknown amount of geometries to the geoIntersect list collection
	{
		Collections.addAll(geoIntersect, geometries);
	}
	
	/**
	 * An override of the findGeoIntersections from the Intersectable interface (that the class is implementing)
	 * @param ray will find all intersections with this ray
	 * @return A list of all the intersection geo-points found
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) 
	{
	        List<GeoPoint> result = null; //new ArrayList<GeoPoint>();
	        
	        for (Intersectable geo : geoIntersect)	//for each geometry in the collection
	        {
	            List<GeoPoint> pointInter = geo.findGeoIntersections(ray);	//find the intersection points with the received ray
	            if(pointInter != null)	// if there is a least one intersection point
	            {
	                //initialize the result collection, otherwise if there is no intersection point 
	            	// the collection will not be initialized
	                if(result == null)
	                {
	                	result = new LinkedList<>();
	                }
	                
                	result.addAll(pointInter);	//add the intersection points
	            }
	        }
	        
	        return result; 
	}
}