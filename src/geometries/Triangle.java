package geometries;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

/**
 * Triangle class that extends Polygon, and present a triangle shape
 * @author Hilla and Oria 
 */
public class Triangle extends Polygon 
{

	/**
	 * A constructor for Triangle that gets three points and uses the polygon's constructor
	 * @param a first point of triangle
	 * @param b second point of triangle
	 * @param c third point of triangle
	 */
	public Triangle (Point3D a, Point3D b, Point3D c)
	{
		super(a,b,c);
	}
	
	/**
	 * An override of the findIntsersections function for the Triangle class, uses the polygon's findIntsersections function
	 * @param ray the ray we check all intersections with
	 * @return A list of all the intersection points found between the triangle and the sent ray
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) 
	{
        List<GeoPoint> points = plane.findGeoIntersections(ray); //סעיף ז
		
		if(points == null)
			return null;
		
		Point3D P0 = ray.getP0();
		Point3D P1 = vertices.get(1);
		Point3D P2 = vertices.get(0);
		
        Vector v = ray.getDir().normalized();
        Vector v1 = P1.subtract(P0).normalized();
        Vector v2 = P2.subtract(P0).normalized();
        
        //checks first pair of vertices
        double sign = alignZero(v.dotProduct(v1.crossProduct(v2)));

        if (isZero(sign))
            return null;

        boolean positive = sign > 0;
        
        //checks all rest of vertices (if there are more)
        for(int i = vertices.size() - 1; i > 0; --i)
        {
        	v1 = v2;
            v2 = vertices.get(i).subtract(P0);

            sign = alignZero(v.dotProduct(v1.crossProduct(v2)));
            
            if (isZero(sign)) 
                return null;

            if (positive != (sign > 0)) 
                return null;
        }
        
        points.get(0).geometry = this;
        return points;
	}
}