package elements;

import primitives.Point3D;
import primitives.Vector;
import primitives.Color;

/**
 * PointLight class, extends Light implements LightSource
 * @author Hilla and Oria
 */
public class PointLight extends Light implements LightSource
{
	private Point3D position;
	private double kC= 1;
	private double kL= 0;
	private double kQ= 0;
	
	/**
	 * the PointLight constructor, uses the Light's constructor and sets the values of the position, kC, kL and kQ
	 * @param c the color
	 * @param Position the position of the light
	 * @param KC 
	 * @param KL
	 * @param KQ
	 */
	public PointLight(Color c, Point3D Position, double KC, double KL, double  KQ) 
	{
		super(c);
		
		this.position = Position;
		this.kC = KC;
		this.kL = KL;
		this.kQ = KQ ;
	}

	/**
	 * a set function for the position field
	 * @param position 
	 * @return this
	 */
	public PointLight setPosition(Point3D position) 
	{
		this.position = position;
		return this;
	}
	
	/**
	 * a set function for the kC field
	 * @param kC 
	 * @return this
	 */
	public PointLight setkC(double kC) 
	{
		this.kC = kC;
		return this;
	}

	/**
	 * a set function for the kL field
	 * @param kL 
	 * @return this
	 */
	public PointLight setkL(double kL) 
	{
		this.kL = kL;
		return this;
	}

	/**
	 * a set function for the position field
	 * @param kQ
	 */
	public void setkQ(double kQ) 
	{
		this.kQ = kQ;
	}

	/**
	 * An override to the getIntensity function from the abstract class Light
	 * @param p the point we're checking the intensity of
	 * @return the intensity at the sent point
	 */
	@Override
	public Color getIntensity(Point3D p) 
	{
		double distanceSquared = p.distanceSquared(position);
		double d = p.distance(position);
		return getIntensity().reduce(kC + d * kL + distanceSquared * kQ);
	}

	/**
	 * An override to the getL function from the abstract class Light
	 * @param p the point we're checking the L vector to
	 * @return the L vector normalized 
	 */
	@Override
	public Vector getL(Point3D p) 
	{
		if(p.equals(position))
			return null;
		else
			return p.subtract(position).normalized();
	}
	
	/**
	 * An override to the getDistance function from the lightsource interface
	 * @param p the point we're checking the distance 
	 * @return the distance between the points
	 */

	@Override
	public double getDistance(Point3D p) {
		return this.position.distance(p);
		
	}
}