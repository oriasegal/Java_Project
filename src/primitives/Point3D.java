package primitives;
/**
 * Point3D class is used to present a point with 3 coordinates
 * @author Oria and Hila 
 */
public class Point3D 
{
	final Coordinate x;
	final Coordinate y;
	final Coordinate z;
	
	public static final Point3D ZERO= new Point3D(0,0,0);

	/**
	 * A constructor for Point3D that gets 3 double values and sets them in the right fields
	 * @param X first coordinate, x
	 * @param Y second coordinate, y
	 * @param Z third coordinate, z
	 */
	public Point3D (double X, double Y, double Z) 
	{
		this.x= new Coordinate(X);
		this.y= new Coordinate(Y);
		this.z= new Coordinate(Z);
	}
	
	/**
	 * Get function for the x field
	 * @return Coordinate x
	 */
	public Coordinate getX() 
	{
		return x;
	}
	
	/**
	 * Get function for the y field
	 * @return Coordinate y
	 */
	public Coordinate getY() 
	{
		return y;
	}
	
	/**
	 * Get function for the z field
	 * @return Coordinate z
	 */
	public Coordinate getZ() 
	{
		return z;
	}
	
	/**
	 * Add function that gets a sent vector and returns a new point with adding the vector's head by coordinates to the calling point's values
	 * @param v uses it's head point
	 * @return Point3D after adding the vectoor's head point's coordinates
	 */
	public Point3D add(Vector v)
	{
		return new Point3D (this.x.coord + v.getHead().x.coord, this.y.coord + v.getHead().y.coord, this.z.coord + v.getHead().z.coord);	
	}
	
	/**
	 * Subtracts a sent point p from vector's head by it's coordinates
	 * @param p the point that will be subtracted for the sent point's coordinates
	 * @return Vector a new vector that equals to the subtraction of the two points
	 */
	public Vector subtract (Point3D p)
	{
		return new Vector(new Point3D(x.coord-p.x.coord, y.coord- p.y.coord, z.coord- p.z.coord));
	}
	
	/**
	 * Subtracts a sent vector v from point by it's coordinates
	 * @param v the vector that will be subtracted 
	 * @return Point3D a new point that equals to the subtraction of the point and the vector
	 */
	public Point3D subtract(Vector v) 
	{
        return new Point3D(this.x.coord - v.getHead().x.coord, this.y.coord - v.getHead().y.coord, this.z.coord - v.getHead().z.coord);
    }
	
	/**
	 * Calculate the squared distance between two points
	 * @param p the second point of the distance calculation (first one is he calling point)
	 * @return double Distance the calculated distance between the two points
	 */
	public double distanceSquared (Point3D p)
	{
		return ((p.x.coord-this.x.coord)*(p.x.coord-this.x.coord) + (p.y.coord-this.y.coord)*(p.y.coord-this.y.coord) + (p.z.coord-this.z.coord)*(p.z.coord-this.z.coord));
	}
	
	/**
	 * Calculate the distance between two points
	 * @param p the second point of the distance calculation (first one is he calling point)
	 * @return double Distance the calculated distance between the two points
	 */
	public double distance (Point3D p)
	{
		return Math.sqrt( this.distanceSquared(p) );
	}

	/**
	 * An override of the equals function for the Point3D class
	 * @param obj a Point3D object
	 * @return a boolean answer
	 */
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() == obj.getClass())
		{
			Point3D other = (Point3D) obj;
			if (other.x == null || other.y == null || other.z == null) 
				return false;
			if (x.equals(other.x) &&  y.equals(other.y) && z.equals(other.z))
				return true;
		}
		
		return false;	
	}

	/**
	 * An override of the toString function for the Point3D class
	 */
	@Override
	public String toString() {
		return "Point= (" + x + " ," + y + ", " + z + ")";
	}
	
	
}