package elements;

import primitives.Color;

/**
 * Light class (abstract)
 * @author Hilla and Oria
 */
public abstract class Light 
{
	private Color intensity; 

	/**
	 * Get function for the intensity field
	 * @return this.intensity
	 */
	public Color getIntensity() 
	{
		return intensity;
	}

	/**
	 * the Light constructor, sets the value of the intensity
	 * @param Intensity the sent intensity for the light
	 */
	protected Light(Color Intensity) 
	{
		this.intensity = new Color(Intensity);
	}
}