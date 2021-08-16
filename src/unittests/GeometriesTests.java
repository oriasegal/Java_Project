package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import geometries.*;
import geometries.Intersectable.GeoPoint;
import primitives.*;

/**
 * Testing Geometries Class
 * @author Hilla and Oria
 */
public class GeometriesTests {

	/**
	 * Test method for findIntersections
	 */
	@Test
	public void testFindIntersections() 
	{
        // ============ Equivalence Partitions Tests ==============
    	
		//Test Case 1: empty collection
		Geometries geos= new Geometries();
		List<GeoPoint> result = new ArrayList<GeoPoint>();
		result= geos.findGeoIntersections(new Ray(new Point3D(1,1,1), new Vector(2, 2, 2)));
		List empty1 = new ArrayList<>();
		assertEquals("Wrong geometries intersection (test case 1)", empty1, result);
		
		//Test Case 2: there's no intersection
		geos.add(
				new Plane(new Point3D(3,2,1),new Point3D(3,1,1), new Point3D(0,0,1)),
				new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(-1, 1, 1)),
				new Triangle(new Point3D(3,2,1),new Point3D(3,1,1), new Point3D(0,0,1)),
				new Sphere(new Point3D(0,0,1), 2.0),
				new Tube(new Ray(new Point3D(0, 0, 1), new Vector(0, 0, -1)), 1.0),
				new Cylinder(new Ray(new Point3D(0, 0, 1), new Vector(0, 0, -1)), 1.0, 5.0)	
				);		
		result = geos.findGeoIntersections(new Ray(new Point3D(-2,0,1), new Vector(-1,-1,-1)));
		List empty2 = new ArrayList<>();
		assertEquals("Wrong geometries intersection (test case 2)", empty2, result);
		
		//Test Case 3: there's exactly one geometry intersection
		result = geos.findGeoIntersections(new Ray(new Point3D(2,0,3), new Vector(1,0,-1)));
		assertEquals("Wrong geometries intersection (test case 3)", 1, result.size());

		//Test Case 4: there're a few geometries intersection
		result = geos.findGeoIntersections(new Ray(new Point3D(0,-1,2), new Vector(0,0,-1)));
		assertEquals("Wrong geometries intersection (test case 4)", 2, result.size());
		
		//Test Case 5: all the geometries are in the intersection
		result = geos.findGeoIntersections(new Ray(new Point3D(1,0,3), new Vector(0,0,-1)));
		assertEquals("Wrong geometries intersection (test case 5)", 3, result.size());
	}
}