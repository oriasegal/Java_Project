package elements;

import primitives.Point3D;
import primitives.Vector;
import primitives.Color;

/**
 * SpotLight class, extends PointLight
 * @author oria and Hilla
 *
 */
public class SpotLight extends PointLight
{
	private Vector direction;
	
	/**
	 * the spotLight constructor, uses the poitLight's constructor and sets the value if the direction vector
	 * @param c the color 
	 * @param Position the position of the spot light
	 * @param Direction the direction vector of the light (center)
	 * @param KC 
	 * @param KL
	 * @param KQ
	 */
	public SpotLight(Color c, Point3D Position, Vector Direction, double KC, double KL, double  KQ) 
	{
		super(c, Position, KC, KL, KQ);
		this.direction = Direction.normalized();
	}
	
	/**
	 * getIntensity function for the spotLight that overrides the pointLight's getIntensity function
	 * @param point the point we're checking the intensity for
	 * @return the value of intensity for the sent point according to spotLight use
	 */
	@Override
	public Color getIntensity(Point3D point)
	{
		Color pointIntensty = super.getIntensity(point);
		double projection = this.direction.dotProduct(getL(point));
		return pointIntensty.scale(Math.max(0, projection));
	}
}