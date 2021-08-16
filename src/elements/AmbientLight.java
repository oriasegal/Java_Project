package elements;

import primitives.Color;

/**
 * AmbientLight class, extends Light
 * @author Hilla and Oria
 */
public class AmbientLight extends Light 
{
	/**
	 * The AmbientLight constructor, sets a value for the intensity field
	 * @param IA for the color of the light
	 * @param kA for the intensity of the light
	 */
	public AmbientLight(Color IA, double kA) 
	{
		super(IA.scale(kA));
	}
	
	/**
	 * The AmbientLight default constructor
	 */
	public AmbientLight()
	{
		super(Color.BLACK);		
	}
}
