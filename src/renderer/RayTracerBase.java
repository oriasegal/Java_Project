package renderer;

import java.util.List;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * RayTracerBase class 
 * @author Hilla and Oria 
 */
public abstract class RayTracerBase 
{

	protected Scene scene;
	
	/**
	 * A constructor for RayTracerBase that gets a Scene and sets it value in the right field
	 * @param _Scene the scene where the ray will go throw
	 */
	public RayTracerBase(Scene _Scene) 
	{
		scene = _Scene;
	}

	/**
	 * An abstract function that will be implemented by all extending classes (RayTracerBasic in our case)
	 * @param r the ray that goes throw the scene
	 * @return Color the color of the shape the ray hits
	 */
	public abstract Color traceRay(Ray r);
	
	/**
	 * An abstract function that will be implemented by all extending classes (RayTracerBasic in our case)
	 * @param rays the list of rays 
	 * @param camera the camera that the rays will be constructed from
	 * @return Color the color of the shape the ray hits
	 */
	public abstract Color traceRays(List<Ray> rays, Camera camera);
	
	/**
	 * An abstract function that will be implemented by all extending classes (RayTracerBasic in our case) 
	 * @param camera the camera that the rays will be constructed from
	 * @param nX
	 * @param nY
	 * @param j
	 * @param i
	 * @param topCornerX
	 * @param topCornerY
	 * @param bottomCornerX
	 * @param bottomCornerY
	 * @param levelOfRecurtion 
	 * @return Color the color of the shape the ray hits
	 */
	public abstract Color constructManyRaysUsingASSRecursion(Camera camera, int nX, int nY, int j, int i, double topCornerX, double topCornerY, double bottomCornerX, double bottomCornerY, int levelOfRecurtion);
}