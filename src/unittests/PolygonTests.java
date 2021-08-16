package unittests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.*;
import geometries.Intersectable.GeoPoint;
import primitives.*;

/**
 * Testing Polygons
 * 
 * @author Dan
 *
 */
public class PolygonTests {

    /**
     * Test method for Polygon constructor
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException e) {}

        // TC03: Not in the same plane
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException e) {}

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException e) {}

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertix on a side");
        } catch (IllegalArgumentException e) {}

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

        // TC12: Colocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

    }

    /**
     * Test method for getNormal
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                new Point3D(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to trinagle", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
    }
    
    /**
     * Test method for findIntersections
     */
    @Test
    public void testFindIntersections()
    {
    	Polygon polygon = new Polygon(new Point3D(3,2,1),new Point3D(3,1,1), new Point3D(0,0,1));
    	
    	// ============ Equivalence Partitions Tests ==============
    	
		//Test Case 1: Inside polygon
    	//assertEquals("Wrong polygon intersection (test case 1)", List.of(new GeoPoint(polygon, new Point3D(1,0.5,1))), polygon.findGeoIntersections(new Ray(new Point3D(1,0.5,3), new Vector(0,0,-1))));
		
    	//Test Case 2: Outside against edge
    	assertNull("Wrong polygon intersection (test case 2)", polygon.findGeoIntersections(new Ray(new Point3D(3.5,0.75,1), new Vector(2,0.5,-1))));
    	
    	//Test Case 3: Outside against vertex
    	assertNull("Wrong polygon intersection (test case 3)", polygon.findGeoIntersections(new Ray(new Point3D(3.5,1.5,1), new Vector(2,0.85,-1))));
    	
		// =============== Boundary Values Tests ==================
    	
    	//Test Case 4: On edge (before the plane)
    	assertNull("Wrong polygon intersection (test case 4)", polygon.findGeoIntersections(new Ray(new Point3D(3,1.5,1), new Vector(2,1,-1))));
    	//is there is a point on the edge - assertEquals("Wrong triangle intersection (test case 4)", new Point3D(3,1.5,1), triangle.findIntsersections(new Ray(new Point3D(3,1.5,1), new Vector(2,1,-1))));
    	
    	//Test Case 5: In vertex (before the plane)
    	assertNull("Wrong polygon intersection (test case 5)", polygon.findGeoIntersections(new Ray(new Point3D(3,2,1), new Vector(2,1.35,-1))));
    	
    	//Test Case 6: On edge's continuation (before the plane)
    	assertNull("Wrong polygon intersection (test case 6)", polygon.findGeoIntersections(new Ray(new Point3D(3,2.5,1), new Vector(2,1.65,-1))));
	}
}
