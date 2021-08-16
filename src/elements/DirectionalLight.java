package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * DirectionalLight class, extends Light implements LightSource
 * @author Hilla and Oria
 */
public class DirectionalLight extends Light implements LightSource
{
	private Vector direction;
	
	/**
	 * the DirectionalLight constructor, uses the Light's constructor and sets the direction vector's value
	 * @param c
	 * @param Direction
	 */
	public DirectionalLight(Color c, Vector Direction) 
	{
		super(c);
		this.direction = Direction;
	}

	/**
	 * An override to the getIntensity function from the abstract class Light
	 * @param p the point we're checking the intensity of
	 * @return the intensity at the sent point
	 */
	@Override
	public Color getIntensity(Point3D p)
	{
		return this.getIntensity();
	}
	
	/**
	 * An override to the getL function from the abstract class Light
	 * @param p the point we're checking the L vector to
	 * @return the L vector normalized 
	 */
	@Override
	public Vector getL(Point3D p)
	{
		return this.direction.normalized();
	}
	
	/**
	 * An override to the getDistance function from the interface lightSource
	 * @param p the point we're checking the distance
	 * @return the distance between the points, a constant 
	 */
	
	@Override
	public double getDistance(Point3D p) 
	{
		return Double.POSITIVE_INFINITY;
	}
}