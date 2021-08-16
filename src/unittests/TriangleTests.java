package unittests;

import static org.junit.Assert.*;

import org.junit.Test;
import primitives.*;
import geometries.*;
import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * Testing Triangle Class
 * @author Hilla and Oria
 */
public class TriangleTests {

	/**
     * Test method for findIntersections
     */
    @Test
    public void testFindIntersections()
    {
		Triangle triangle = new Triangle(new Point3D(3,2,1),new Point3D(3,1,1), new Point3D(0,0,1));
		
    	// ============ Equivalence Partitions Tests ==============
    	
		//Test Case 1: Inside triangle
    	assertEquals("Wrong triangle intersection (test case 1)", List.of(new GeoPoint(triangle , new Point3D(1,0.5,1))), triangle.findGeoIntersections(new Ray(new Point3D(1,0.5,3), new Vector(0,0,-1))));
		
    	//Test Case 2: Outside against edge
    	assertNull("Wrong triangle intersection (test case 2)", triangle.findGeoIntersections(new Ray(new Point3D(3.5,0.75,1), new Vector(2,0.5,-1))));
    	
    	//Test Case 3: Outside against vertex
    	assertNull("Wrong triangle intersection (test case 3)", triangle.findGeoIntersections(new Ray(new Point3D(3.5,1.5,1), new Vector(2,0.85,-1))));
    	
		// =============== Boundary Values Tests ==================
    	
    	//Test Case 4: On edge (before the plane)
    	assertNull("Wrong triangle intersection (test case 4)", triangle.findGeoIntersections(new Ray(new Point3D(3,1.5,1), new Vector(2,1,-1))));
    	//is there is a point on the edge - assertEquals("Wrong triangle intersection (test case 4)", new Point3D(3,1.5,1), triangle.findIntsersections(new Ray(new Point3D(3,1.5,1), new Vector(2,1,-1))));
    	
    	//Test Case 5: In vertex (before the plane)
    	assertNull("Wrong triangle intersection (test case 5)", triangle.findGeoIntersections(new Ray(new Point3D(3,2,1), new Vector(2,1.35,-1))));
    	
    	//Test Case 6: On edge's continuation (before the plane)
    	assertNull("Wrong triangle intersection (test case 6)", triangle.findGeoIntersections(new Ray(new Point3D(3,2.5,1), new Vector(2,1.65,-1))));
	}

}
