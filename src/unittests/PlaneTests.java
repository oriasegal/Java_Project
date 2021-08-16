package unittests;

import static org.junit.Assert.*;

import org.junit.Test;
import static primitives.Util.isZero;
import java.util.List;

import primitives.*;
import geometries.*;
import geometries.Intersectable.GeoPoint;

/**
 * Testing Plane Class
 * @author Hilla and Oria
 */
public class PlaneTests {

	/**
     * Test method for getNormal
     */
    @Test
    public void testGetNormal() 
    {
    	Plane plane = new Plane(new Point3D(1,2,3),new Point3D(1,1,1), new Point3D(2,2,2));
    	
        // ============ Equivalence Partitions Tests ==============
        // Test Case 1: Simple test
        assertEquals(plane.getNormal(new Point3D(1,2,3)),new Vector(1,-2,1).normalize());
        
        //Test Case 2: Makes sure that the length of the normal is 1
        assertEquals("Wrong normal for plane (not equal to 1)", 1d, plane.getNormal(new Point3D(1,2,3)).length(), 0.00001);
    }
    
    /**
     * Test method for Constructor
     */
    @Test
    public void testPlane()
    {
    	Point3D a = new Point3D(1,2,3);
    	Point3D b = new Point3D(1,1,1);
    	Point3D c = new Point3D(2,2,2);
    	
    	// =============== Boundary Values Tests ==================
    	
    	//Test Case 1: Makes sure the first two sent points aren't the same
    	assertTrue("Worng point input- the first two sent points are the same", !((a.getX().getCoord() == b.getX().getCoord()) && (a.getY().getCoord() == b.getY().getCoord()) && (a.getZ().getCoord() == b.getZ().getCoord())));
    	
    	//Test Case 2: Makes sure the three sent points aren't on the same line (if all points are on one line the cross product will be equal to the zero vector)
    	Vector AB = new Vector(new Point3D(b.getX().getCoord()-a.getX().getCoord(), b.getY().getCoord()-a.getY().getCoord(), b.getZ().getCoord()-a.getZ().getCoord()));
    	Vector AC = new Vector(new Point3D(c.getX().getCoord()-a.getX().getCoord(), c.getY().getCoord()-a.getY().getCoord(), c.getZ().getCoord()-a.getZ().getCoord()));

    	Vector u = AB.crossProduct(AC);
    	
    	assertTrue("Wrong point input- all points are on one line", !isZero(u.getHead().getX().getCoord()) && !isZero(u.getHead().getY().getCoord()) && !isZero(u.getHead().getZ().getCoord()) && !isZero(u.length()));	
    }
    
    /**
     * Test method for findIntersections 
     */
    @Test
    public void testFindIntersections()
    {
    	Plane plane = new Plane(new Point3D(3,2,1),new Point3D(3,1,1), new Point3D(0,0,1));
    	
    	// ============ Equivalence Partitions Tests ==============
    	
    	//Test Case 1: Ray intersects the plane
    	assertEquals("Wrong plane intersection (test case 1)", List.of(new GeoPoint(plane, new Point3D(1,0,1))), plane.findGeoIntersections(new Ray(new Point3D(1,0,3), new Vector(0,0,-1))));
    	
    	//Test Case 2: Ray doesn't intersect the plane
    	assertNull("Wrong plane intersection (test case 2)", plane.findGeoIntersections(new Ray(new Point3D(0,0,3), new Vector(0,1,0))));
    	
    	// =============== Boundary Values Tests ==================
    	
    	//Test Case 3: Ray is parallel to the plane, included in the plane
    	assertNull("Wrong plane intersection (test case 3)", plane.findGeoIntersections(new Ray(new Point3D(0,0,1), new Vector(1,0.5,0))));
    	
    	//Test Case 4: Ray is parallel to the plane, not included in the plane
    	assertNull("Wrong plane intersection (test case 4)", plane.findGeoIntersections(new Ray(new Point3D(0,0,0), new Vector(1,0.5,0))));
    	
    	//Test Case 5: Ray is orthogonal to the plane, p0 is before the plane
    	assertEquals("Wrong plane intersection (test case 5)", List.of(new GeoPoint(plane, new Point3D(2,1,1))), plane.findGeoIntersections(new Ray(new Point3D(2,1,0), new Vector(0,0,1))));
    	
    	//Test Case 6: Ray is orthogonal to the plane, p0 is after the plane
    	assertNull("Wrong plane intersection (test case 6)", plane.findGeoIntersections(new Ray(new Point3D(2,1,2), new Vector(0,0,1))));
    	
    	//Test Case 7: Ray is orthogonal to the plane, p0 is in the plane
    	assertNull("Wrong plane intersection (test case 7)", plane.findGeoIntersections(new Ray(new Point3D(2,1,1), new Vector(0,0,1)))); 
    	
    	//Test Case 8: Ray is neither orthogonal nor parallel and begins at the plane
    	assertNull("Wrong plane intersection (test case 7)", plane.findGeoIntersections(new Ray(new Point3D(2,1,1), new Vector(0,0,1))));
    	
    	//Test Case 9: Ray is neither orthogonal nor parallel to the plane and begins in Q
    	//             Q is the point of the plane, in this case Q=p0 the start point of v0 of the plane.
    	assertNull("Wrong plane intersection (test case 9)", plane.findGeoIntersections(new Ray(new Point3D(3,2,1), new Vector(0,0,1))));
    }
}
