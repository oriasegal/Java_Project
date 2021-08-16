package geometries;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Cylinder class that extends Tube, is used to present a Cylinder
 * @author Oria and Hila 
 */
public class Cylinder extends Tube
{
	double height;
	
	/**
	 * A constructor for Cylinder that gets a Ray, height and a radius
	 * @param axisRay the middle ray of the tube (sent to super)
	 * @param radius the radius of the tube (sent to super)
	 * @param height the height of the cylinder
	 */
	public Cylinder(Ray axisRay, double radius, double height) {
		super(axisRay, radius);
		this.height = height;
	}

	/**
	 * Get function for the height field
	 * @return height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * An override of the toString function for the Cylinder class
	 */
	@Override
	public String toString() {
		return "Cylinder (height=" + height + ")";
	}

	/**
	 * An override of the getNormal function for the Cylinder class
	 * @param p the starting point of the normal vector (sent to the getNormal of the super) 
	 * @return The normal vector calculated by the getNormal of the super
	 */
	@Override
	public Vector getNormal(Point3D p)
	{
		return super.getNormal(p);
	}
}