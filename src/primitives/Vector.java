package primitives;
/**
 * Vector class is used to present a vector with head point
 * @author Hilla and Oria 
 */
public class Vector 
{
	private Point3D head;

	/**
	 * A constructor for Vector that gets the head point and checks that it's not the zero vector
	 * @param Head the head point of the vector
	 * @exception IllegalArgumentException when the vector is the zero vector
	 */
	public Vector(Point3D Head)
	{
		if(Head.equals(Point3D.ZERO))
			throw new IllegalArgumentException("Error. This is zero vector.");
		else this.head = Head;
	}
	
	/**
	 * A constructor for Vector that gets the head point by coordinates and checks that it's not the zero vector
	 * @param X first coordinate of the point
	 * @param Y second coordinate of the point
	 * @param Z third coordinate of the point
	 * @exception IllegalArgumentException when the vector is the zero vector
	 */
	public Vector(double X, double Y, double Z)
	{
		if(X==0 && Y==0 && Z==0)
			throw new IllegalArgumentException("Error. This is zero vector.");
		else this.head= new Point3D(X,Y,Z);
	}
	
	/**
	 * Get function for the head field
	 * @return Point3D head
	 */
	public Point3D getHead() 
	{
		return head;
	}

	/**
	 * Adds two vectors by their head point values 
	 * @param v the second vector of the calculations (the first one is the calling vector)
	 * @return Vector the result of adding the two vectors together
	 */
	public Vector add(Vector v)
	{ 
		return new Vector(this.head.add(v)); 
	}
	
	/**
	 * Subtracts two vectors by their head values 
	 * @param v the second vector of the calculations (the first one is the calling vector)
	 * @return Vector the result of subtracting one vector from the other
	 */
	public Vector subtract(Vector v)
	{
		return new Vector(this.head.subtract(v.head).head);
	}
	
	/**
	 * Multiplies a vector by a sent double (scalar multiplication)
	 * @param d the value by which we scale
	 * @return Vector the result of scaling the calling vector
	 */	
	public Vector scale(double d)
	{
		return new Vector(new Point3D(this.head.x.coord *d, this.head.y.coord *d ,this.head.z.coord *d));
	}
	
	/**
	 * Calculates the dot product between two vectors
	 * @param v the second vector of the calculations (the first one is the calling vector)
	 * @return double the dot product between the two vectors
	 */	
	public double dotProduct(Vector v)
	{
		return this.head.x.coord *v.head.x.coord + this.head.y.coord *v.head.y.coord + this.head.z.coord *v.head.z.coord;
	}
	
	/**
	 * Calculates cross product between two vectors
	 * Checks if the vectors are parallel
	 * @param v the second vector of the calculations (the first one is the calling vector)
	 * @exception IllegalArgumentException when the vectors are parallel
	 * @return Vector the cross product between the two vectors
	 */	
	public Vector crossProduct(Vector v)
	{
		double X= (this.head.y.coord * v.head.z.coord) - (this.head.z.coord * v.head.y.coord);
		double Y= (this.head.z.coord * v.head.x.coord) - (this.head.x.coord * v.head.z.coord);
		double Z= (this.head.x.coord * v.head.y.coord)- (this.head.y.coord * v.head.x.coord);
		return new Vector(new Point3D(X,Y,Z));
	}
	
	/**
	 * Calculates the squared length of a vector
	 * @return double the result of the squared length of the calling vector
	 */	
	public double lengthSquared()
	{
		return this.head.x.coord*this.head.x.coord + this.head.y.coord*this.head.y.coord + this.head.z.coord*this.head.z.coord;
	}

	/**
	 * Calculates the length of a vector
	 * @return double d the result of the length of the calling vector
	 */	
	public double length()
	{
		return Math.sqrt(this.lengthSquared());
	}
	
	/**
	 * Normalizes the vector and return the vector itself but updated
	 * @return Vector v the calling vector after changes
	 */	
	public Vector normalize()
	{
		this.head= new Point3D(this.head.x.coord / this.length(), this.head.y.coord / this.length(), this.head.z.coord / this.length());;
		return this;
	}

	/**
	 * Normalizes the vector and return a new vector (uses the normalize function)
	 * @return Vector v a new vector- the normalized version of the calling vector
	 */	 
	public Vector normalized()
	{
		return new Vector(this.normalize().head);
	}
	
	/**
	 * An override of the equals function for the Vector class
	 * @param obj a Vector object
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
				Vector other = (Vector) obj;
				if (head == null && other.head != null) 	
				{
					return false;
				}
				else if (!head.equals(other.head))
						return false;
				return true;
			}
		
		return false;
	}

	/**
	 * An override of the toString function for the Vector class
	 */
	@Override
	public String toString() {
		return "Vector starts at " + this.head.toString();
	}
}