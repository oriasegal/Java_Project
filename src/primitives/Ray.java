package primitives;

import static primitives.Util.*;
import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * Ray class is used to present a ray with head point and direction vector
 * @author Hilla and Oria 
 */
public class Ray 
{
	Point3D p0;
	Vector dir;
	private static final double DELTA= 0.1;
	
	/**
	 * A constructor for Ray that gets the point and vector 
	 * Uses the Vector's and Point3D's constructors
	 * @param P0 the starting point of the ray
	 * @param DIR the direction vector of the ray
	 */
	public Ray(Point3D P0, Vector DIR)
	{
		this.dir= new Vector(DIR.normalized().getHead());
		this.p0 =new Point3D(P0.x.coord, P0.y.coord, P0.z.coord);	
	}

	/**
	 * A constructor for Ray that gets the point and vector 
	 * Uses the Vector's and Point3D's constructors
	 * @param P0 the starting point of the ray
	 * @param DIR the direction vector of the ray
	 * @param P0 the starting point of the ray
	 */
	public Ray(Point3D P0, Vector DIR, Vector normal)
	{
		this.dir= new Vector(DIR.getHead()).normalized();
		//Point3D head= new Point3D(P0.x.coord, P0.y.coord, P0.z.coord);
		double sign= normal.dotProduct(DIR);
		//this.p0 =head;
		if (sign>0)
			this.p0 =P0.add(normal.scale(DELTA));
		else 
			this.p0 =P0.add(normal.scale(-DELTA));

	}
	
	
	/**
	 * Get function for the p0 field
	 * @return Point3D p0
	 */
	public Point3D getP0() 
	{
		return p0;
	}
	
	/**
	 * Get function for the dir field
	 * @return Vector dir
	 */
	public Vector getDir() 
	{
		return dir;
	}
	
	/**
	 * A function that gets a double value p and returns the top point of the matching ray 
	 * @param p the sent parameter for point calculations
	 * @return Point3D the top point of the matching calculated ray
	 */
	public Point3D getPoint(double p)
	{
        if (isZero(p))
            return  p0;
        return p0.add(dir.scale(p));
    }
	
	/**
	 * Calculates the target point according to the sent distance from the head point of the ray
	 * @param distance
	 * @return Point 3D the target point 
	 */
	public Point3D getTargetPoint(double distance)
	{
		return isZero(distance) ? p0 : p0.add(dir.scale(distance));
	}
	
	/**
	 * An override of the equals function for the Ray class
	 * @param obj a Ray object
	 * @return a boolean answer
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() == obj.getClass())
		{
			Ray other = (Ray) obj;
			if (dir == null && other.dir != null)
			{
				return false;
			}
			if (p0 == null && other.p0 != null)
				return false;
			else if (!dir.equals(other.dir))
				return false;
			else if (!p0.equals(other.p0))
				return false;
			
			return true;
		}
		
		return false;
	}

	/**
	 * An override of the toString function for the Ray class
	 */
	@Override
	public String toString() {
		return "Ray [p0=" + p0 + ", dir=" + dir + "]";
	}
	
	/**
	 * The findClosestPoint function gets a list of points and returns the closets point to the top of the calling ray 
	 * @param points a list of points
	 * @return Point3D the closest point
	 */
	public Point3D findClosestPoint(List<Point3D> points)
	{
		double min = Double.MAX_VALUE;
		int place = 0;
		
		if(points == null)
			return null;
		
		for(int i = 0; i < points.size(); i++)
		{
			if(min > this.p0.distance(points.get(i)))
			{
				min = this.p0.distance(points.get(i));
				place = i;
			}
		}
		
		return points.get(place);
	}
	
	/**
	 * The getClosestGeoPoint function gets a list of geo-points and returns the closets geo-point to the top of the calling ray 
	 * @param points a list of geo-points
	 * @return Point3D the closest geo-point
	 */
	public GeoPoint getClosestGeoPoint(List<GeoPoint> points)
	{
		double min = Double.MAX_VALUE;
		int place = 0;
		
		if(points.size() == 0)
			return null;
		
		for(int i = 0; i < points.size(); i++)
		{
			if(min > this.p0.distance(points.get(i).point))
			{
				min = this.p0.distance(points.get(i).point);
				place = i;
			}
		}
		
		return points.get(place);
	}
}