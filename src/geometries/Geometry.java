package geometries;

import java.util.List;

import primitives.*;

/**
 * Geometry interface for all the geometry shapes in the program
 * @author Oria and Hila 
 */
public abstract class Geometry implements Intersectable
{
	protected  Color emission = Color.BLACK;
	private Material material;

	/**
	 * Get function for the emission field
	 * @return emission the value of the emission lighting of the calling geometry
	 */
	public Color getEmission() 
	{
		return this.emission;
	}
	
	/**
	 * Get function for the material field
	 * @return material the value of the material of the calling geometry
	 */
	public Material getMaterial() 
	{
		return this.material;
	}

	/**
	 * Set function for the emission field
	 * @param Emission the value of the emission lighting of the calling geometry
	 * @return this
	 */
	public Geometry setEmission(Color Emission) 
	{
		this.emission = Emission;
		return this;
	}
	
	/**
	 * Set function for the material field
	 * @param  _Material the value of the material of the calling geometry
	 * @return this
	 */
	public Geometry setMaterial(Material _Material) 
	{
		this.material = _Material;
		return this;
	}

	/**
	 * A getNormal function that will be implemented (override) in all the geometric classes in the program 
	 * @param p the starting point of the normal vector
	 * @return Normal to the implementing geometry
	 */
	public abstract Vector getNormal(Point3D p);
}