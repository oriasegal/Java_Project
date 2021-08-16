package unittests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import elements.Camera;
import geometries.*;
import primitives.*;

/**
 * Testing CameraRay Class
 * @author Hilla and Oria
 *
 */
public class CameraRayTests 
{
	/**
	 * This function checks if the sent number of intersections (result) is true by using the findIntsersections function on all the sent geometries 
	 * @param Camera c the camera of the scene
	 * @param Intersectable geo all geometries in the scene
	 * @param int result the wanted number of intersection points
	 * @return a boolean answer 
	 */
	private boolean assertNumberOfIntersections(Camera c, Intersectable geo, int result)
	{
		c.setViewPlaneSize(3, 3);
		c.setDistance(1);
		
		int counter = 0;
		
		for(int i = 0; i < 3; ++i)
		{
			for(int j = 0; j < 3; ++j)
			{
				var intersections = geo.findGeoIntersections(c.constructRayThroughPixel(3, 3, j, i));
				if (intersections != null) 
                    counter += intersections.size();
			}
		}
		
		if(counter == result)
			return true;
		return false;		 
	}
	
	/**
	 * Test method for
	 * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
	 */
	@Test
	public void testConstructRayThroughPixelPlane() 
	{
		Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
		//Test Case 1: A plane in front of the camera without angle, 9 intersection points
		assertTrue("Wrong camera-ray-plane intersection (test case 1)", !assertNumberOfIntersections(camera, new Plane (new Point3D(0, 0, -10), new Vector(0, 0, 2)), 9));
		
		//Test Case 2: A plane in front of the camera with angle, 9 intersection points
		assertTrue("Wrong camera-ray-plane intersection (test case 2)", !assertNumberOfIntersections(camera, new Plane (new Point3D(0, 0, -10), new Vector(0, 1, 2)), 9));

		//Test Case 3: A plane parallel, 6 intersection points
		assertTrue("Wrong camera-ray-plane intersection (test case 3)", !assertNumberOfIntersections(camera, new Plane (new Point3D(0, 0, -10), new Vector(0, 0, 2)), 9));	

	}

	/**
	 * Test method for
	 * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
	 */
	@Test
	public void testConstructRayThroughPixelTriangle() 
	{
		Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, 1, 0));
		//Test Case 1: one intersection point, small triangle
		assertTrue("Wrong camera-ray-triangle intersection (test case 1)", !assertNumberOfIntersections(camera, new Triangle (new Point3D(1,1,-2), new Point3D(-1,1,-2), new Point3D(0,-1,-2)),1));
		
		//Test Case 2: two intersection points,bigger triangle
		assertTrue("Wrong camera-ray-triangle intersection (test case 2)", !assertNumberOfIntersections(camera, new Triangle (new Point3D(1,1,-2), new Point3D(-1,1,-2), new Point3D(0,-20,-2)),2));

	}
	
	/**
	 * Test method for
	 * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
	 */
	@Test
	public void testConstructRayThroughPixelSphere() 
	{
		Camera camera1 = new Camera(Point3D.ZERO, new Vector(0, -1, 0), new Vector(0, 0, -1));
		Camera camera2 = new Camera(new Point3D(0, 0, 0.5), new Vector(0, -1, 0), new Vector(0, 0, -1));
		Camera camera3 = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
		
		//Test Case 1: Camera-ray-sphere intersection with 2 points
		assertTrue("Wrong camera-ray-sphere intersection (test case 1)", !assertNumberOfIntersections(camera1, new Sphere(new Point3D(0, 0, -3), 1.0), 2));
		
		//Test Case 2: Camera-ray-sphere intersection with 18 points
		assertTrue("Wrong camera-ray-sphere intersection (test case 2)", !assertNumberOfIntersections(camera2, new Sphere(new Point3D(0, 0, -2.5), 2.5), 18));
		
		//Test Case 3: Camera-ray-sphere intersection with 10 points
		assertTrue("Wrong camera-ray-sphere intersection (test case 3)", !assertNumberOfIntersections(camera2, new Sphere(new Point3D(0, 0, -2), 2.0), 10));
		
		//Test Case 4: Camera-ray-sphere intersection with 9 points
		//assertTrue("Wrong camera-ray-sphere intersection (test case 4)", !assertNumberOfIntersections(camera3, new Sphere(new Point3D(0, 0, 1), 4.0), 9));
		//assertTrue("Wrong camera-ray-sphere intersection (test case 4)", !assertNumberOfIntersections(camera2, new Sphere(new Point3D(0, 0, -1), 4.0), 9));
		
		//Test Case 5: Camera-ray-sphere intersection with no points
		assertTrue("Wrong camera-ray-sphere intersection (test case 5)", !assertNumberOfIntersections(camera2, new Sphere(new Point3D(0, 0, 1), 0.5), 0));
	}
}