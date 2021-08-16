package geometries;

import primitives.Ray;
import primitives.Point3D;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Intersectable interface for all the intersections with the shapes in the program
 * @author Oria and Hila 
 */
public interface Intersectable 
{
	/**
	 * A findIntsersections function that will be implemented (override) in all the geometric classes in the program 
	 * @param ray the ray we check all intersections with
	 * @return A list of all the intersection points found
	 */
	default List<Point3D> findIntersections(Ray ray) 
	{
	    var geoList = findGeoIntersections(ray);
	    return geoList == null ? null
	                           : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
	}

	
	/**
	 * A findGeoIntersections function that will be implemented (override) in all the geometric classes in the program 
	 * @param ray the ray we check all intersections with
	 * @return A list of all the intersection geo-points found
	 */
	public List<GeoPoint> findGeoIntersections(Ray ray);
	
	public static class GeoPoint 
	{
	    public Geometry geometry;
	    public Point3D point;
	    
	    /**
		 * A constructor for GeoPoint that gets a Geometry and a Point3D and sets the new values of the two fields
		 * @param geo the geometry
		 * @param p the point on the geometry
		 */
		public GeoPoint(Geometry geo, Point3D p) 
		{
			this.geometry = geo;
			this.point = p;
		}
		
		/**
		 * sets the value of the geometry
		 * @param g
		 */
		public void setGeometry(Geometry g)
        {
            geometry = g;
        }

		/**
		 * An override of the equals function for the GeoPoint class
		 * @param obj a GeoPoint object
		 * @return a boolean answer
		 */
		@Override
		public boolean equals(Object obj) 
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			
			if (getClass() != obj.getClass())
				return false;
			
			GeoPoint other = (GeoPoint) obj;
			
			if (geometry == null) 
			{
				if (other.geometry != null)
					return false;
			} 
			else if (!geometry.equals(other.geometry))
				return false;
			
			if (point == null) 
			{
				if (other.point != null)
					return false;
			} 
			else if (!point.equals(other.point))
				return false;
			return true;
		}
	}
}
