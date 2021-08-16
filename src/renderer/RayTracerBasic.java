package renderer;

import java.util.List;

import elements.Camera;
import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;
import primitives.Util;

/**
 * RayTracerBasic class 
 * @author Hilla and Oria 
 */
public class RayTracerBasic extends RayTracerBase 
{
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private static final double INITIAL_K = 1.0;

	/**
	 * A constructor for RayTracerBasic that gets a Scene and sets it value in the right field by sending it to the super constructor
	 * @param scene the scene where the ray will go throw
	 */
	public RayTracerBasic(Scene scene) 
	{
		super(scene);	
	}
	
	/**
	 * A function that calculates the diffusive value 
	 * @param kd 
	 * @param nl
	 * @param intens
	 * @return diffusive value 
	 */
	private Color calcDiffusive(double kd, double nl, Color intens)
    {
        if (nl < 0) nl = -nl;
        return intens.scale(nl * kd); 
    }
	
	/**
	 * A function that calculates the specular value 
	 * @param ks
	 * @param l
	 * @param n
	 * @param nl
	 * @param v
	 * @param nShininess
	 * @param intens
	 * @return specular value 
	 */
	private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color intens)
    {
        Vector R = l.add(n.scale(-2 * nl)).normalized(); // nl must not be zero!
        
        double minusVR = -Util.alignZero(R.dotProduct(v));
        
        if (minusVR <= 0)
        	return Color.BLACK; // view from direction opposite to r vector

        return intens.scale(ks * Math.pow(minusVR, nShininess));
    }
	
	/**
	 * A function that calculates the reflection value 
	 * @param n
	 * @param p
	 * @param ray
	 * @return reflection value 
	 */
	private Ray calcReflection(Vector n, Point3D p, Ray ray)
    {
		 //r= v-2*(v*n)*n
		Vector normal = n.normalized(); 
		Vector direction =ray.getDir().normalized();
		Vector r = normal.scale((-2)* direction.dotProduct(normal)); //-2*(v*n)*n
        r = r.add(direction);	//adding v
        return new Ray(p, r, n);
    }
	
	/**
	 * A function that calculates the refraction value 
	 * @param n
	 * @param p
	 * @param ray
	 * @return refraction value 
	 */
	private Ray calcRefraction(Vector n, Point3D p, Ray ray)
    {
		Vector direction =ray.getDir().normalized();
		return new Ray(p, direction, n);
    }
	
	/**
	 * A function that calculates all the local effects that a ray has on a point
	 * @param point the geo-point we're checking
	 * @param ray the ray that hit the point on the geometry
	 * @return the total value of the local effects on the point
	 */
	
	private Color calcLocalEffects(GeoPoint point, Ray ray, double k)
	{
		Vector v = ray.getDir().normalized(); 
		Vector n = point.geometry.getNormal(point.point).normalized();
		
		double nv = Util.alignZero(n.dotProduct(v));
		if (nv == 0) 
			return Color.BLACK;
		
		Material material = point.geometry.getMaterial();
		int nShininess = material.nShininess;
		double kd = material.kD;
		double ks = material.kS;
		Color color = Color.BLACK;
		
		for (LightSource lightSource : scene.lights) 
		{
			Vector l = lightSource.getL(point.point).normalized();
			double nl = Util.alignZero(n.dotProduct(l));
			
			if (nl * nv > 0) // sign(nl) == sing(nv)  otherwise there's no camera's effect
			{ 
				double ktr = transparency(lightSource, l, n, point);
				if (ktr * k > MIN_CALC_COLOR_K) {
				Color lightIntensity = lightSource.getIntensity(point.point).scale(ktr);	//Il
				color = color.add(calcDiffusive(kd, nl, lightIntensity), calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
				
				}
			}
		}
		
		return color;
	}
	
	/**
	 * A function that calculates all the global effects that a ray has on a point
	 * @param geopoint the geo-point we're checking
	 * @param level the recursive level of the func
	 * @param k the 
	 * @return the total value of the local effects on the point
	 */
	private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, double k)
	{
		Color color = Color.BLACK;
		Material material = geopoint.geometry.getMaterial();
		double kr = material.kR, kkr = k * kr; 
		if (kkr > MIN_CALC_COLOR_K) {
			Ray reflectedRay = calcReflection(geopoint.geometry.getNormal(geopoint.point), geopoint.point, ray);
			GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
			if (reflectedPoint != null)
			color = color.add(calcColor(reflectedPoint, reflectedRay, level-1, kkr).scale(kr));
		}
		
		double kt = material.kT, kkt = k * kt;
		if (kkt > MIN_CALC_COLOR_K) {
			Ray refractedRay = calcRefraction(geopoint.geometry.getNormal(geopoint.point), geopoint.point, ray);
			GeoPoint refractedPoint = findClosestIntersection(refractedRay);
			if (refractedPoint != null)
			color = color.add(calcColor(refractedPoint, refractedRay, level-1, kkt).scale(kt));
		}
		return color;
		
	}
	
	/**
	 * Calculating the color of the point that the ray hits the shape recursively, returns the ambient light of the scene
	 * @param point the hitting point on the shape
	 * @param r the ray
	 * @param level the hitting point on the shape
	 * @param k 
	 * @return Color the ambient light of the scene
	 */
	public Color calcColor(GeoPoint point, Ray r, int level, double k)
	{
		Color color =point.geometry.getEmission().add(calcLocalEffects(point, r, k));;
		return 1 == level ? color : color.add(calcGlobalEffects(point, r, level, k));
		
	}
	
	/**
	 * Calculating the color of the point that the ray hits the shape, returns the ambient light of the scene
	 * @param closestPoint the hitting point on the shape
	 * @param r the ray
	 * @return Color the ambient light of the scene
	 */
	private Color calcColor(GeoPoint closestPoint, Ray ray) 
	{
		return calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
		.add(scene.ambientLight.getIntensity());
	}
	
	/**
	 * An updated version of calcColor- uses all colors of all intersections with all rays in the sent list to find the final color of the point
	 * @param rays the list of rays we use to calculate the color
	 * @param camera will tell us if we're using the DOF algorithm or not
	 * @return Color the final color of the point
	 */
	public Color calcColor(List<Ray> rays, Camera camera)
	{
		//without DOF
        if(camera.isDepthOfField() == false && camera.getSuperSampling()== false)
        {
            GeoPoint closestPoint= findClosestIntersection(rays.get(0));
            return calcColor(closestPoint, rays.get(0));
        }
        
		//with DOF or Super-Sampling, or both
        Color color;
        int numberOfRays = rays.size();
        double Red = 0;
        double Green = 0;
        double Blue = 0;
        
        //for each ray in the list calculates the value of R, G and B and at the end returns the final color
        for (Ray ray: rays)
        {
            GeoPoint closestPoint = findClosestIntersection(ray);
    		color = closestPoint == null ? this.scene.background : calcColor(closestPoint, ray);
            Red += color.getColor().getRed();
            Green += color.getColor().getGreen();
            Blue += color.getColor().getBlue();
        }
        
        return new Color(Red / numberOfRays, Green / numberOfRays, Blue / numberOfRays);
	}
	
	/**
	 * Constructing a beam of rays from the camera into the view plane and to the shapes in the scene.
	 * @param camera 
	 * @param nX
	 * @param nY
	 * @param j
	 * @param i
	 * @param topCornerX
	 * @param topCornerY
	 * @param bottomCornerX
	 * @param bottomCornerY
	 * @param levelOfRecurtion
	 * @return Color the color of the pixel 
	 */
	public Color constructManyRaysUsingASSRecursion(Camera camera, int nX, int nY, int j, int i, double topCornerX, double topCornerY, double bottomCornerX, double bottomCornerY, int levelOfRecurtion)
	{
        List<Ray> rays = camera.constructManyRaysUsingASS(nX, nY, j, i, topCornerX, topCornerY, bottomCornerX, bottomCornerY);
        
        Color centerColor;
        Color TLColor ;
        Color TRColor ;
        Color BLColor ;
        Color BRColor ;
        
        Ray center = rays.get(0); //the center

        if(findClosestIntersection(center) == null)
        	centerColor = scene.background;
        
        else
            centerColor = calcColor(findClosestIntersection(center), center);

        
        Ray TL = rays.get(1);
        
        if(findClosestIntersection(TL) == null)
        	TLColor = scene.background;
        
        else 
        	TLColor = calcColor(findClosestIntersection(TL), TL);

        
        Ray TR = rays.get(2);
        
        if(findClosestIntersection(TR) == null)
            TRColor = scene.background;
        
        else
        	TRColor = calcColor(findClosestIntersection(TR), TR);

        
        Ray BL = rays.get(3);
        
        if(findClosestIntersection(BL) == null)
            BLColor = scene.background;
        
        else
            BLColor = calcColor(findClosestIntersection(BL), BL);
        

        Ray BR = rays.get(4);
        
        if(findClosestIntersection(BR) == null)
            BRColor = scene.background;
        
        else
            BRColor = calcColor(findClosestIntersection(BR), BR);
        

        if (levelOfRecurtion == 1)
        { 
            centerColor = centerColor.add(TLColor, TRColor, BLColor, BRColor);
            return centerColor.reduce(5); 
        }

        if (TLColor.equals(centerColor) && TRColor.equals(centerColor) && BLColor.equals(centerColor) && BRColor.equals(centerColor))
            return centerColor;

        else
        {	
            if (!TLColor.equals(centerColor))
            	TLColor = constructManyRaysUsingASSRecursion(camera, nX, nY, j, i, topCornerX, topCornerY, ((topCornerX + bottomCornerX)/2), (topCornerY + bottomCornerY)/2, levelOfRecurtion - 1);
            
            if (!TRColor.equals(centerColor))//if the top right region is not the same like the center - create subpixel
            	TRColor = constructManyRaysUsingASSRecursion(camera, nX, nY, j, i, topCornerX, ((topCornerY + bottomCornerY)/2), ((topCornerX + bottomCornerX)/2), bottomCornerY, levelOfRecurtion - 1);
            
            if (!BLColor.equals(centerColor))//if the bottom left region is not the same like the center - create subpixel
            	BLColor = constructManyRaysUsingASSRecursion(camera, nX, nY, j, i, (topCornerX + bottomCornerX)/2 , topCornerY, bottomCornerX, (topCornerY + bottomCornerY)/2, levelOfRecurtion - 1);
            
            if (!BRColor.equals(centerColor))//if the bottom right region is not the same like the center - create subpixel
            	BRColor = constructManyRaysUsingASSRecursion(camera, nX, nY, j, i, (topCornerX + bottomCornerX) / 2, ((topCornerY + bottomCornerY)/2), bottomCornerX, bottomCornerY, levelOfRecurtion - 1);
            
        }
        
        centerColor = centerColor.add(TLColor, TRColor, BLColor, BRColor);
        return centerColor.reduce(5); 
	}
	
	/**
	 * An implementation of the abstract function in RayTracerBase that calculates the color of the point the ray hits the shape
	 * @param r the hitting ray
	 * @return Color the color of the point the ray hits the shape
	 */
	public Color traceRay(Ray r)
	{
		GeoPoint closestPoint = findClosestIntersection(r);
		return closestPoint == null ? Color.BLACK : calcColor(closestPoint, r);
	}
	
	/**
	 * An updated version of the traceRay function- uses all rays in the sent list to calculate the color
	 * @param rays a list of rays to calculate the color from
	 * @param camera the camera that tells us if we're using the DOF algorithm or not
	 * @return Color the final calculated color of the point
	 */
	public Color traceRays(List<Ray> rays, Camera camera)
	{
		return calcColor(rays, camera);
	}
	
	
	/**
	 * An implementation of the abstract function in RayTracerBase that calculates the color of the point the ray hits the shape
	 * @param light the source light
	 * @param l the Vector from the light
	 * @param n the normal
	 * @param gp point
	 * @return true if there is no geometry- not shaded
	 */
	private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geoPoint)
	{
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(geoPoint.point, lightDirection, n);	//ray from the point to the light
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		if (intersections == null) return true;
		double lightDistance = light.getDistance(geoPoint.point);
		for (GeoPoint gp : intersections) {
			if (Util.alignZero(gp.point.distance(geoPoint.point)-lightDistance)<= 0 &&
					gp.geometry.getMaterial().kT == 0)	//KT=0
				return false;
		}
		return true;
		
	}
	
	/**
	 * An improvement of the function unshaded that calculates the color of the point the ray hits the shape
	 * @param l the Vector from the light
	 * @param n the normal
	 * @param gp point
	 * @return the value of the accumulated kt that calculated in the func, and 0 or 1 in special cases
	 */
	private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) 
	{
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(geopoint.point, lightDirection, n);	//ray from the point to the light
		double lightDistance = light.getDistance(geopoint.point);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		
		if (intersections == null) return 1.0;	//unshaded there is no intersection
		double ktr = 1.0;
		for (GeoPoint gp : intersections) {
			if (Util.alignZero(gp.point.distance(geopoint.point)-lightDistance)<= 0)
				{
				ktr *= gp.geometry.getMaterial().kT;
				if (ktr < MIN_CALC_COLOR_K) return 0.0; //the value is too little
				}
		}
		return ktr;
	}
	
	/**
	 * A combination  of the findGeoIntersections and from the Intersectable interface and the func findClosestPoint from Ray class
	 * @param ray will find all intersections with this ray
	 * @return A GeoPoint of the closet intersection point of the received ray
	 */	
	private GeoPoint findClosestIntersection(Ray ray) 
	{
		List <GeoPoint> points = scene.geometries.findGeoIntersections(ray);
		if (points== null)
			return null;
		return ray.getClosestGeoPoint(points);	
		
	}
	
}