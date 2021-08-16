package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Cylinder Class
 * @author Hilla and Oria
 */
public class CylinderTests 
{
	/**
	 * Test method for getNormal
	 */
	@Test
	public void testGetNormal() {
		
		Cylinder cylinder = new Cylinder (new Ray(new Point3D(0, 0, 1), new Vector(0, -1, 0)), 1.0, 5.0);
		
        // ============ Equivalence Partitions Tests ==============
		
        // Test Case 1: First base
		assertEquals("Wrong tube normal", new Vector(0, 0, 1), cylinder.getNormal(new Point3D(0, 0, 1.5)).normalize());	
		
		// Test Case 2: Second base
		assertEquals("Wrong tube normal", new Vector(0, -5, 1), cylinder.getNormal(new Point3D(0, -5, 1.5)).normalize());	
	}
}
