package geometries;

import primitives.*;
import java.util.List;

/**
 * Tube class that implements geometry interface, is used to present a Tube
 * @author Hilla and Oria 
 */
public class Tube extends Geometry implements Intersectable
{
	Ray axisRay;
	double radius;
		
	/**
	 * A constructor for Tube that gets a Ray and a radius and sets all values in the right fields
	 * @param axisRay the middle ray of the tube 
	 * @param radius the radius of the tube
	 */
	public Tube(Ray axisRay, double radius) 
	{
		this.axisRay = axisRay;
		this.radius = radius;
	}

	/**
	 * Get function for the axisRay field
	 * @return Ray axisRay
	 */
	public Ray getAxisRay() 
	{
		return axisRay;
	}

	/**
	 * Get function for the radius field
	 * @return double radius
	 */
	public double getRadius() 
	{
		return radius;
	}

	/**
	 * An override of the toString function for the Tube class
	 */
	@Override
	public String toString() {
		return "Tube (axisRay=" + axisRay + ", radius=" + radius + ")";
	}

	/**
	 * An override of the getNormal function for the Tube class
	 * @param p the starting point of the normal vector 
	 * @return The normal vector of the calling tube object 
	 */
	@Override
	public Vector getNormal(Point3D p)
	{
		Point3D p0 = this.axisRay.getP0();
		Vector v = this.axisRay.getDir();
		
		//t = v*(p-p0)
		double t = p.subtract(p0).dotProduct(v);
		
		//O = p0 + t*v
		Point3D O = p0.add(v.scale(t));
		
		//n = normalize(P-O)
		return p.subtract(O).normalize();
	}

	/**
	 * An override of the findIntsersections function for the Tube class
	 * @param ray the ray we check all intersections with
	 * @return null
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) 
	{
		return null;
	}
}