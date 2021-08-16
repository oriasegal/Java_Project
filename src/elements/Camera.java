package elements;

import primitives.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static primitives.Util.isZero;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Camera class
 * @author Hilla and Oria
 */
public class Camera {

	final private Point3D p0;
	final private Vector vUp;
	final private Vector vTo;
	final private Vector vRight;
	
	private double width;
	private double height;
	private double distance;
	
	private double apertureSize; 
	private double focalDistance;
	private int numberOfRays;
	private boolean depthOfField; 
	private boolean superSampling; 

	/**
	 * Get function for the width field
	 * @return double width
	 */
	public double getWidth() 
	{
		return width;
	}

	/**
	 * Get function for the height field
	 * @return double height
	 */
	public double getHeight() 
	{
		return height;
	}

	/**
	 * Get function for the distance field
	 * @return double distance
	 */
	public double getDistance() 
	{
		return distance;
	}
	
	/**
	 * Get function for the apertureSize field
	 * @return double apertureSize
	 */
	public double getApertureSize() 
	{
		return apertureSize;
	}
	
	/**
	 * Get function for the focalDistance field
	 * @return double focalDistance
	 */
	public double getFocalDistance() 
	{
		return focalDistance;
	}
	
	/**
	 * Get function for the numberOfRays field
	 * @return int numberOfRays
	 */
	public int getNumberOfRays() 
	{
		return numberOfRays;
	}
	
	/**
	 * Get function for super Sampling
	 * @return int superSampling
	 */
	public boolean getSuperSampling() 
	{
		return superSampling;
	}
	
	
	
	/**
	 * The Camera basic constructor, uses the newer constructor
	 * @param P0 for the middle point of the camera
	 * @param VTo for the vTo vector that goes forward from the camera
	 * @param VUp for the vUp vector that goes up from the camera
	 * @exception IllegalArgumentException if vUp and vTo aren't orthogonal
	 */
	public Camera(Point3D P0, Vector VTo, Vector VUp) 
	{
		this(P0, VTo, VUp, 1, 1, false); //calls new ctor and adds the default values to apertureSize, focalDistance and depthOfField
	}
	
	/**
	 * The Camera constructor, sets a value for all fields- p0, vUp, vTo, vRight, width, height, distance, apertureSize, focalDistance, depthOfField
	 * (not using the field numberOfRays)
	 * @param P0 for the middle point of the camera
	 * @param VTo for the vTo vector that goes forward from the camera
	 * @param VUp for the vUp vector that goes up from the camera
	 * @param ApertureSize for the size of the double aperture
	 * @param FocalDistance for the size of the focal distance of the focal plane
	 * @param DepthOfField for knowing if we're using the depthOfField algorithm or not
	 * @exception IllegalArgumentException if vUp and vTo aren't orthogonal
	 */
	public Camera(Point3D P0, Vector VTo, Vector VUp, double ApertureSize, double FocalDistance, boolean DepthOfField) 
	{
		p0 = P0;
		
		if(!isZero(VUp.dotProduct(VTo)))
		{
			throw new IllegalArgumentException("vUp and vTo aren't orthogonal.");
		}
		
		vUp = VUp.normalized();
		vTo = VTo.normalized();
		vRight = vTo.crossProduct(vUp).normalize();
		
		width = 1;
		height = 1;
		distance = 5;
		
		apertureSize = ApertureSize;
		focalDistance = FocalDistance;
		depthOfField = DepthOfField;
	}
	
	/**
	 * The Camera constructor, sets a value for all fields- p0, vUp, vTo, vRight, width, height, distance, apertureSize, focalDistance, depthOfField, numberOfRays
	 * @param P0 for the middle point of the camera
	 * @param VTo for the vTo vector that goes forward from the camera
	 * @param VUp for the vUp vector that goes up from the camera
	 * @param ApertureSize for the size of the double aperture
	 * @param FocalDistance for the size of the focal distance of the focal plane
	 * @param DepthOfField for knowing if we're using the depthOfField algorithm or not
	 * @param NumberOfRays of the amount of rays
	 * @exception IllegalArgumentException if vUp and vTo aren't orthogonal
	 */
	public Camera(Point3D P0, Vector VTo, Vector VUp, double ApertureSize, double FocalDistance, boolean DepthOfField, int NumberOfRays) 
	{
		p0 = P0;
		
		if(!isZero(VUp.dotProduct(VTo)))
		{
			throw new IllegalArgumentException("vUp and vTo aren't orthogonal.");
		}
		
		vUp = VUp.normalized();
		vTo = VTo.normalized();
		vRight = vTo.crossProduct(vUp).normalize();
		
		width = 1;
		height = 1;
		distance = 5;
		
		apertureSize = ApertureSize;
		focalDistance = FocalDistance;
		numberOfRays = NumberOfRays;
		depthOfField = DepthOfField;
	}
	
	/**
	 * Sets the size of the view plane of the camera
	 * @param Width for the width of the view plane
	 * @param Height for the height of the view plane
	 * @return this
	 */
	public Camera setViewPlaneSize(double Width, double Height)
	{
		width = Width;
		height = Height;
		return this;
	}
	
	/**
	 * Sets the distance between the view plane and the camera
	 * @param Distance between the view plane and the camera
	 * @return this
	 */
	public Camera setDistance(double Distance)
	{
		distance = Distance;
		return this;
	}
	
	/**
	 * Sets the size of the number of the rays to construct through the plane
	 * @param num for the number of the rays
	 * @return this
	 */
	public Camera setNumberOfRays(int num)
	{
		numberOfRays= num;
		return this;
	}
	
	/**
	 * Sets if we construct few rays through the plane with super sampling
	 * @param flag for true- use the super sampling
	 * @return this
	 */
	public Camera setSuperS(boolean flag)
	{
		superSampling= flag;
		return this;
	}
	
	/**
	 * Checks if we're using the depth of field algorithm by returning the value of the depthOfField field
	 * @return this.depthOfField
	 */
	public boolean isDepthOfField() 
	{
	    return this.depthOfField;
	}
	
	/**
	 * Calculates the focal point of the sent ray with the focal plane of the camera
	 * @param ray the ray we're calculating the focal point for
	 * @return Point3D the focal point of the sent ray and the focal plane of the camera
	 */
	public Point3D calcFocalPoint(Ray ray)
	{
		 double cosine = vTo.dotProduct(ray.getDir()); //The cosine of the angle between the vTo vector and the direction vector of ray from pixel
	     double distance = this.getFocalDistance() / cosine; //The distance from the camera to the focal point by using the law of cosines
	     return ray.getTargetPoint(distance); //Find focal point for the ray and the distance from camera.
	}

	
	/**
	 * Constructs *ONE* ray from the camera through a given pixel
	 * @param nX number of pixels in one row in the view plane (width)
	 * @param nY number of pixels in one column in the view plane (height)
	 * @param j the y coordinate of the pixel
	 * @param i the x coordinate of the pixel
	 * @return ray that goes from the camera and through the pixel
	 */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i)
	{
		
		Point3D PC= p0.add(vTo.scale(distance));
		
		double Rx= width/nX;
		double Ry= height/nY;
		
		double Yi= -(i-(nY-1)/2d)*Ry;
		double Xj= (j-(nX-1)/2d)*Rx;
	
		Point3D pij= PC;
		
		if (isZero(Xj)&& isZero(Yi))
			return new Ray(p0, pij.subtract(p0));
		
		if (isZero(Xj)) {
			pij = pij.add(vUp.scale(Yi));
	        return new Ray(p0, pij.subtract(p0));
	    }
		
	    if (isZero(Yi)) {
	    	pij = pij.add(vRight.scale(Xj));
	        return new Ray(p0, pij.subtract(p0));
	    }
	
	    pij = pij.add(vRight.scale(Xj));
	    pij = pij.add(vUp.scale(Yi));
	    return new Ray(p0, pij.subtract(p0));
		
	}
    
    /**
	 * Constructs *MANY* rays from the camera through a given pixel, uses the constructManyRaysUsingDOF function
	 * @param nX number of pixels in one row in the view plane (width)
	 * @param nY number of pixels in one column in the view plane (height)
	 * @param j the y coordinate of the pixel
	 * @param i the x coordinate of the pixel
	 * @return List<Ray> a list of all the rays that go from the camera and through the pixel with the DOF algorithm
	 */
    public List<Ray> constructManyRaysThroughPixel(int nX, int nY, int j, int i)
    {
    	Ray firstRay = constructRayThroughPixel(nX, nY, j, i); //constructs the first ray using the original function to construct one ray (above)
    	
    	//if we're not using the depthOfField algorithm returns the first ray as a list
    	if(this.depthOfField)
    		constructManyRaysUsingDOF(firstRay, distance); //uses the constructManyRaysUsingDOF function to construct all rays and returns them all as one list
    	if (this.superSampling)
    		constructManyRaysUsingSuperSampling(nX, nY, j, i);
    	
    	return List.of(firstRay);
    }

    /**
     * Constructs *MANY* rays through a pixel by using the DOF algorithm
     * @param FirstRay
     * @param Distance
     * @return List<Ray> a list of all the rays that were constructed
     */
    public List<Ray> constructManyRaysUsingDOF(Ray FirstRay, double Distance)
    {
    	Point3D focalPoint = this.calcFocalPoint(FirstRay); 
    	
    	Point3D lowerRightCorner = p0.subtract(vRight.scale(apertureSize / 2d)).subtract(vUp.scale(apertureSize / 2d));
    	
    	List<Ray> rays = new ArrayList<Ray>();
    	for(int i = 0; i < numberOfRays; ++i)
    	{
    		for(int j = 0; j < numberOfRays; ++j)
    		{
    			Point3D p = lowerRightCorner.add(vRight.scale((0.5 + i) * (apertureSize / numberOfRays))).add(vUp.scale((0.5 + j) * (apertureSize / numberOfRays)));
    			Vector v = focalPoint.subtract(p);
    			rays.add(new Ray(p, v));
    		}
    	}
		
    	return rays;
    }
    
    /**
     * Constructs *MANY* rays through a pixel by using the super-sampling algorithm
     * @param nX the amount of pixels in width
     * @param nY the amount of pixels in height
     * @param j the specific placement on width
     * @param i the specific placement on height
     * @return List<Ray> the list of all constructed rays
     */
    public List<Ray> constructManyRaysUsingSuperSampling(int nX, int nY, int j, int i)
    {
    	
    	Random rand= new Random();
    	List<Ray> raysThroughPixel= new ArrayList<Ray>();
		
		Point3D PC= p0.add(vTo.scale(distance));

		double Rx= width/nX;	//The width of a pixel
		double Ry= height/nY;	//The height of a pixel
		
		double Yi= -(i-(nY-1)/2d)*Ry+ Ry/2d ;
		double Xj= (j-(nX-1)/2d)*Rx+ Rx/2d ;
		
		double minX= (j- nX/2d)* Rx;		//going left to the end of the view plain 
    	double minY= (i- nY/2d)* Ry;		//going down to the end of the view plain
    	double maxX= (j- nX/2d)* Rx+ Rx;	//going right to the end of the view plain and adding the width of a pixel
    	double maxY= (i- nY/2d)* Ry+ Ry;	//going up to the end of the view plain and adding the height of a pixel
		
    	Point3D pij= PC;	//initialize the starting pixel to the center 
		
		Vector vij= new Vector(pij.subtract(p0).getHead());
		
		Ray centerRay= new Ray(p0, vij.normalized());
		
		raysThroughPixel.add(centerRay);
		
		for(int k=0; k< numberOfRays-1; k++)
		{
			 pij= PC;
			
			 Yi= minY+ (maxY-minY) * rand.nextDouble();
			 Xj= minX+ (maxX-minX) * rand.nextDouble();

			
			if (isZero(Xj)&& isZero(Yi))
			{
				raysThroughPixel.add(new Ray(p0, pij.subtract(p0).normalized()));
				break;
			}	
			
			if (isZero(Xj)) 
			{
				pij = pij.add(vUp.scale(Yi));
				raysThroughPixel.add(new Ray(p0, pij.subtract(p0).normalized()));
				break;
		    }
			
		    if (isZero(Yi)) 
		    {
		    	pij = pij.add(vRight.scale(Xj));
		    	raysThroughPixel.add(new Ray(p0, pij.subtract(p0).normalized()));
		    	break;
		    }
		    
		    pij = pij.add(vRight.scale(Xj));
		    pij = pij.add(vUp.scale(Yi));
		    raysThroughPixel.add(new Ray(p0, pij.subtract(p0).normalized()));
		}

    	return raysThroughPixel;
    }
    
    /**
     * Calculates the new point to construct the rays using ASS from
     * @param PC the starting center point
     * @param Yi the specific placement of the current point (y)
     * @param Xj the specific placement of the current point (x)
     * @param placementX the new offset (x)
     * @param placementY the new offset (y)
     * @return Point3D the new calculated point for the recursion
     */
    public Point3D calcPoint(Point3D PC, double Yi, double Xj, double placementX, double placementY)
    {
    	Point3D point = PC;
    	double x = Xj + placementX;
    	double y = Yi - placementY; 

    	if (isZero(x))
    	{
    		point = point.add(vUp.scale(y));
    		return point;
    	}
        
        if (isZero(y))
        {
        	point = point.add(vRight.scale(x));
        	return point;
        }

    	point = point.add(vRight.scale(x));
    	point = point.add(vUp.scale(y));
    	return point;
    }
    
    /**
     * Constructs *MANY* rays using the adaptive super-sampling algorithm
     * @param nX the amount of pixels in width
     * @param nY the amount of pixels in height
     * @param j the specific placement on width
     * @param i the specific placement on height
     * @param topCornerX the max value of x
     * @param topCornerY the max value of y
     * @param bottomCornerX the min value of x
     * @param bottomCornerY the min value of y
     * @return List<Ray> the list of all constructed rays
     */
    public List<Ray> constructManyRaysUsingASS(int nX, int nY, int j, int i, double topCornerX, double topCornerY, double bottomCornerX, double bottomCornerY)
    {
        List<Ray> rays = new ArrayList<>();
        
        Point3D PC= p0.add(vTo.scale(distance)); //creating a point for the middle of the view plane

		double Rx= width/nX;	//The width of a pixel
		double Ry= height/nY;	//The height of a pixel
		
		double Yi= ((i - nY / 2d) * Ry + Ry / 2d); //-((i-(nY-1)/2d)*Ry); //+ Ry/2d
		double Xj= ((j - nX / 2d) * Rx + Rx / 2d); //(j-(nX-1)/2d)*Rx; //+ Rx/2d
		
		Point3D pij= PC;	//initialize the starting pixel to the center
    	

		if (isZero(Xj))
        {
        	pij = pij.add(vUp.scale(-Yi));
        }

		else if (isZero(Yi))
        {
            pij = pij.add(vRight.scale(Xj));
        }
        
        else
        {
        	pij = pij.add(vRight.scale(Xj));
            pij = pij.add(vUp.scale(-Yi));
        }
        
        Point3D Center =  calcPoint(PC, Yi, Xj,(topCornerX + bottomCornerX)/2, (topCornerY + bottomCornerY)/2);
        
        Point3D TL = calcPoint(PC, Yi, Xj, topCornerX, topCornerY);
        Point3D TR = calcPoint(PC, Yi, Xj, topCornerX, bottomCornerY);
        Point3D BR = calcPoint(PC, Yi, Xj, bottomCornerX, bottomCornerY);
        Point3D BL = calcPoint(PC, Yi, Xj, bottomCornerX, topCornerY);

        rays.add(new Ray(p0, new Vector(Center.subtract(p0).getHead())));
        rays.add(new Ray(p0, new Vector(TL.subtract(p0).getHead())));
        rays.add(new Ray(p0, new Vector(TR.subtract(p0).getHead())));
        rays.add(new Ray(p0, new Vector(BL.subtract(p0).getHead())));
        rays.add(new Ray(p0, new Vector(BR.subtract(p0).getHead())));
        
        return rays;
    }
}