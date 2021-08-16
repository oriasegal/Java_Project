package geometries;

import primitives.*;
import java.util.List;

import geometries.Intersectable.GeoPoint;

import static primitives.Util.*;

/**
 * Sphere class that implements geometry interface, is used to present a Sphere
 * @author Hilla and Oria 
 */
public class Sphere extends Geometry implements Intersectable
{
	Point3D center;
	double radius;
	
	/**
	 * A constructor for Sphere that gets a point3D and a radius and sets the values in the right fields
	 * @param center the center point of the sphere
	 * @param radius the radius of the sphere
	 */
	public Sphere(Point3D center, double radius) 
	{
		this.center = center;
		this.radius = radius;
	}

	/**
	 * Get function for the center field
	 * @return Point3D center
	 */
	public Point3D getCenter() 
	{
		return center;
	}

	/**
	 * Get function for the radius field
	 * @return double radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * An override of the toString function for the Sphere class
	 */
	@Override
	public String toString() {
		return "Sphere (center=" + center + ", radius=" + radius + ")";
	}

	/**
	 * An override of the getNormal function for the Sphere class
	 * @param p the starting point of the normal vector 
	 * @return The normal vector (filed) of the calling sphere object
	 */
	@Override
	public Vector getNormal(Point3D p)
	{
		//n = normalize(P-O)
		return p.subtract(this.center).normalize();
	}

	/**
	 * An override of the findIntsersections function for the Sphere class
	 * @param ray the ray we check all intersections with
	 * @return A list of all the intersection points found between the sphere and the sent ray
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) 
	{
		Point3D p0 = ray.getP0();
		Vector v = ray.getDir();
		
		//GeoPoint geoCenter = new GeoPoint(this, center);
		
		if(p0.equals(center))
		{
			//geoCenter.point = center.add(v.scale(radius));
			return List.of(new GeoPoint(this, center.add(v.scale(radius))));
		}
		
		Vector u = center.subtract(p0);
		double tm = alignZero(v.dotProduct(u));
		double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));
		
		if (d >= radius)
			return null;
		
		double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        
        if (t1 > 0 && t2 > 0)
        {
          GeoPoint P1 = new GeoPoint(this, ray.getPoint(t1));
    	  GeoPoint P2 = new GeoPoint(this, ray.getPoint(t2));
          return List.of(P1, P2);
        }
        
        if (t1 > 0) 
        {
          GeoPoint P1 = new GeoPoint(this, ray.getPoint(t1));
          return List.of(P1);
        }
        
	    if (t2 > 0) 
	    {
	    	GeoPoint P2 = new GeoPoint(this, ray.getPoint(t2)); 
	        return List.of(P2);
	    }
      
	    return null;
	}
}