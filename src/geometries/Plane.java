package geometries;

import primitives.*;
import java.util.List;
import static primitives.Util.*;

/**
 * Plane class that implements geometry interface, is used to present a plane
 * @author Oria and Hila 
 */
public class Plane extends Geometry implements Intersectable
{
	Point3D q0;
	Vector normal;

	/**
	 * A constructor for Plane that gets a point3D and a normal vector and sets the new values of the two fields
	 * @param q0 the center point of the plane
	 * @param normal the normal vector of the plane
	 */
	public Plane(Point3D q0, Vector normal) 
	{
		this.q0 = q0;
		this.normal = normal;
	}

	/**
	 * A constructor for Plane that gets three points, calculates the normal vector of the plane and sets all fields
	 * @param a the center point of the plane
	 * @param b the first point to calculate the normal vector of the plane
	 * @param c the second point to calculate the normal vector of the plane
	 */
	public Plane(Point3D a , Point3D b, Point3D c)
	{
		this.q0= a;
		
		Vector v1 = b.subtract(a);
		Vector v2 = c.subtract(a);
		this.normal= v1.crossProduct(v2).normalize();
	}
	
	/**
	 * Get function for the q0 field
	 * @return q0 the center point of the calling plane
	 */
	public Point3D getQ0() 
	{
		return q0;
	}

	/**
	 * Get function for the normal field
	 * @return normal the normal of the calling plane
	 */
	public Vector getNormal() 
	{
		return normal;
	}

	/**
	 * An override of the toString function for the Plane class
	 */
	@Override
	public String toString() {
		return "Plane (point=" + q0 + ", normal=" + normal + ")";
	}

	/**
	 * An override of the getNormal function for the Plane class
	 * @param p the starting point of the normal vector 
	 * @return The normal vector (filed) of the calling plane object
	 */
	@Override
	public Vector getNormal(Point3D p) 
	{
		return this.normal;
	}

	/**
	 * An override of the findIntsersections function for the Plane class
	 * @param ray the ray we check all intersections with
	 * @return A list of all the intersection points found between the plane and the sent ray
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) 
	{
		Point3D p0 = ray.getP0();
		Vector v = ray.getDir();
		Vector n = normal;
		
		if(q0.equals(p0))
			return null;
		
		Vector p0q0 = q0.subtract(p0);
		
		double numerator = alignZero(n.dotProduct(p0q0));
		double denominator = alignZero(n.dotProduct(v));
		
		if(isZero(numerator))
			return null;
		if(isZero(denominator))
			return null;
		
		double t = numerator/denominator;
		
		if(t <= 0)
			return null;
		
		Point3D p=ray.getPoint(t);
		GeoPoint point = new GeoPoint(this, p);
		return List.of(point);
	}
}