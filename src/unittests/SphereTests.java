package unittests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import static primitives.Util.isZero;

import java.util.List;

import primitives.*;
import geometries.*;
import geometries.Intersectable.GeoPoint;

/**
 * Testing Sphere Class
 * @author Hilla and Oria
 */
public class SphereTests {

	/**
	 * Test method for getNormal
	 */
	@Test
	public void testGetNormal() 
	{	
        // ============ Equivalence Partitions Tests ==============
		
        // Test Case 1: Simple test
		Sphere sphere = new Sphere (new Point3D(0, 0, 1), 1.0);
		assertEquals("Bad normal to Sphere", new Vector(0,0,1), sphere.getNormal(new Point3D(0, 0, 2)));
	}

	/**
     * Test method for findIntersections
     */
    @Test
    public void testFindIntersections()
    {
    	Sphere sphere = new Sphere(new Point3D(0,0,1), 2.0);
    	
    	// ============ Equivalence Partitions Tests ==============
    	
    	//Test Case 1: Ray starts before the sphere, two intersection points
    	assertEquals("Wrong sphere intersection (test case 1)", List.of(new GeoPoint(sphere , new Point3D(2,0,1)), new GeoPoint(sphere , new Point3D(0,0,3))), sphere.findGeoIntersections(new Ray(new Point3D(3,0,0), new Vector(-1,0,1))));
    	
    	//Test Case 2: Ray starts before the sphere, no intersection points
    	assertNull("Wrong sphere intersection (test case 2)", sphere.findGeoIntersections(new Ray(new Point3D(3,0,0), new Vector(0,0,1))));
    	
    	//Test Case 3: Ray starts before the sphere and points to the other side, no intersection points
    	assertNull("Wrong sphere intersection (test case 3)", sphere.findGeoIntersections(new Ray(new Point3D(3,0,0), new Vector(1,0,-1))));
    	
    	//Test Case 4: Ray starts inside the sphere, one intersection point
    	assertEquals("Wrong sphere intersection (test case 4)", List.of(new GeoPoint(sphere, new Point3D(0,0,3))), sphere.findGeoIntersections(new Ray(new Point3D(0,0,2), new Vector(0,0,1))));
    	
		// =============== Boundary Values Tests ==================
    	
    	//Test Case 5: Ray starts on the edge of the sphere, no intersection point - GROUP 1
    	assertNull("Wrong sphere intersection (test case 5)", sphere.findGeoIntersections(new Ray(new Point3D(-1.75,0,2), new Vector(-1,0,0))));
    	
    	//Test Case 6: Ray starts on the edge of the sphere, one intersection point - GROUP 1
    	assertEquals("Wrong sphere intersection (test case 6)", List.of(new GeoPoint(sphere, new Point3D(-2,0,1))), sphere.findGeoIntersections(new Ray(new Point3D(2,0,1), new Vector(-1,0,0))));
    	
    	//Test Case 7: Ray crosses the center of the sphere, starts on edge, no intersection point - GROUP 2 
    	assertNull("Wrong sphere intersection (test case 7)", sphere.findGeoIntersections(new Ray(new Point3D(-2,0,1), new Vector(-1,0,0))));
    	
    	//Test Case 8: Ray crosses the center of the sphere, starts in center, one intersection point - GROUP 2 
    	assertEquals("Wrong sphere intersection (test case 8)", List.of(new GeoPoint(sphere, new Point3D(-2,0,1))), sphere.findGeoIntersections(new Ray(new Point3D(0,0,1), new Vector(-1,0,0))));
    	
    	//Test Case 9: Ray crosses the center of the sphere, starts after edge, no intersection point - GROUP 2 
    	assertNull("Wrong sphere intersection (test case 9)", sphere.findGeoIntersections(new Ray(new Point3D(-4,0,1), new Vector(-1,0,0))));
    	
    	//Test Case 10: Ray crosses the center of the sphere, starts on edge, one intersection point - GROUP 2 
    	assertEquals("Wrong sphere intersection (test case 10)", List.of(new GeoPoint(sphere, new Point3D(-2,0,1))), sphere.findGeoIntersections(new Ray(new Point3D(2,0,1), new Vector(-1,0,0))));
    	
    	//Test Case 11: Ray crosses the center of the sphere, starts in sphere, one intersection point - GROUP 2 
    	assertEquals("Wrong sphere intersection (test case 11)", List.of(new GeoPoint(sphere, new Point3D(-2,0,1))), sphere.findGeoIntersections(new Ray(new Point3D(-1,0,1), new Vector(-1,0,0))));
    	
    	//Test Case 12: Ray crosses the center of the sphere, starts before edge, two intersection point - GROUP 2
    	assertEquals("Wrong sphere intersection (test case 12)", List.of(new GeoPoint(sphere, new Point3D(2,0,1)), new GeoPoint(sphere, new Point3D(-2,0,1))), sphere.findGeoIntersections(new Ray(new Point3D(3,0,1), new Vector(-1,0,0))));
    	
    	//Test Case 13: Ray is tangent to the sphere, starts before tangent, no intersection point - GROUP 3
    	assertNull("Wrong sphere intersection (test case 13)", sphere.findGeoIntersections(new Ray(new Point3D(3,0,3), new Vector(-1,0,0))));
    	
    	//Test Case 14: Ray is tangent to the sphere, starts on tangent, no intersection point - GROUP 3
    	assertNull("Wrong sphere intersection (test case 14)", sphere.findGeoIntersections(new Ray(new Point3D(-2,0,1), new Vector(0,0,1))));
    	
    	//Test Case 15: Ray is tangent to the sphere, starts after tangent, no intersection point - GROUP 3
    	assertNull("Wrong sphere intersection (test case 15)", sphere.findGeoIntersections(new Ray(new Point3D(-2,0,4), new Vector(0,0,1))));
    	
    	//Test Case 16: Ray is vertical to the sphere, no intersection point - GROUP 4
    	assertNull("Wrong sphere intersection (test case 15)", sphere.findGeoIntersections(new Ray(new Point3D(-4,0,4), new Vector(0,0,1))));
    	
    }
	
}