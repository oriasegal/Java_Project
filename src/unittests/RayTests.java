package unittests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import geometries.Intersectable.GeoPoint;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Testing Ray Class
 * @author Hilla and Oria
 */
public class RayTests 
{
	/**
     * Test method for findClosestPoint
     */
	@Test
	public void findClosestPointTest() 
	{
		Ray ray = new Ray(new Point3D(0, 0, 10), new Vector(1, 10, -100));
        
		// ============ Equivalence Partitions Tests ==============

        // Test Case 1: The closest point is in the middle of the list
		List<Point3D> points1 = new LinkedList<Point3D>();
		points1.add(new Point3D(1, 1, -100));
		points1.add(new Point3D(-1, 1, -99));
		points1.add(new Point3D(0, 2, -10));
		points1.add(new Point3D(0.5, 0, -100));
		assertEquals("Wrong find closest point. (test case 1)", new Point3D(0, 2, -10), ray.findClosestPoint(points1));
        
		// =============== Boundary Values Tests ==================
		
		// Test Case 2: No points in the list
		List<Point3D> points2 = null;
		assertNull("Wrong find closest point. (test case 2)", ray.findClosestPoint(points2));
		
		// Test Case 3: The closest point is the first point of the list
		List<Point3D> points3 = new LinkedList<Point3D>();
		points3.add(new Point3D(0, 2, -10));
		points3.add(new Point3D(-1, 1, -99));
		points3.add(new Point3D(1, 1, -100));
		points3.add(new Point3D(0.5, 0, -100));
		assertEquals("Wrong find closest point. (test case 3)", new Point3D(0, 2, -10), ray.findClosestPoint(points3));
		
		// Test Case 4: The closest point is the last point of the list
		List<Point3D> points4 = new LinkedList<Point3D>();
		points4.add(new Point3D(1, 1, -100));
		points4.add(new Point3D(0.5, 0, -100));
		points4.add(new Point3D(-1, 1, -99));
		points4.add(new Point3D(0, 2, -10));
		assertEquals("Wrong find closest point. (test case 4)", new Point3D(0, 2, -10), ray.findClosestPoint(points4));
	}
	
	/**
     * Test method for getClosestGeoPoint
     */
	@Test
	public void getClosestGeoPointTest() 
	{
		Ray ray = new Ray(new Point3D(0, 0, 10), new Vector(1, 10, -100));
        
		// ============ Equivalence Partitions Tests ==============

        // Test Case 1: The closest point is in the middle of the list
		List<GeoPoint> points1 = new LinkedList<GeoPoint>();
		points1.add(new GeoPoint(null , new Point3D(1, 1, -100)));
		points1.add(new GeoPoint(null , new Point3D(-1, 1, -99)));
		points1.add(new GeoPoint(null , new Point3D(0, 2, -10)));
		points1.add(new GeoPoint(null , new Point3D(0.5, 0, -100)));
		assertEquals("Wrong find closest point. (test case 1)", new GeoPoint(null , new Point3D(0, 2, -10)), ray.getClosestGeoPoint(points1));
        
		// =============== Boundary Values Tests ==================
		
		// Test Case 2: No points in the list
		List<GeoPoint> points2 = null;
		assertNull("Wrong find closest point. (test case 2)", ray.getClosestGeoPoint(points2));
		
		// Test Case 3: The closest point is the first point of the list
		List<GeoPoint> points3 = new LinkedList<GeoPoint>();
		points3.add(new GeoPoint(null , new Point3D(0, 2, -10)));
		points3.add(new GeoPoint(null , new Point3D(-1, 1, -99)));
		points3.add(new GeoPoint(null , new Point3D(1, 1, -100)));
		points3.add(new GeoPoint(null , new Point3D(0.5, 0, -100)));
		assertEquals("Wrong find closest point. (test case 3)", new GeoPoint(null , new Point3D(0, 2, -10)), ray.getClosestGeoPoint(points3));
		
		// Test Case 4: The closest point is the last point of the list
		List<GeoPoint> points4 = new LinkedList<GeoPoint>();
		points4.add(new GeoPoint(null , new Point3D(1, 1, -100)));
		points4.add(new GeoPoint(null , new Point3D(0.5, 0, -100)));
		points4.add(new GeoPoint(null , new Point3D(-1, 1, -99)));
		points4.add(new GeoPoint(null , new Point3D(0, 2, -10)));
		assertEquals("Wrong find closest point. (test case 4)", new GeoPoint(null , new Point3D(0, 2, -10)), ray.getClosestGeoPoint(points4));
	}

}
