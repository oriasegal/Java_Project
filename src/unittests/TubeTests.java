package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Tube Class
 * @author Hilla and Oria
 */
public class TubeTests 
{
	/**
	 * Test method for 
	 * {@link geometries.Tube#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		
		Tube tube = new Tube (new Ray(new Point3D(0, 0, 1), new Vector(0, -1, 0)), 1.0);
		Vector tubeNormal = tube.getNormal(new Point3D(0, 0.5, 2)).normalize();
		
        // ============ Equivalence Partitions Tests ==============
		
        // Test Case 1: Simple test
		assertEquals("Wrong tube normal", new Vector(0, 0, 1), tubeNormal);	
		
		// =============== Boundary Values Tests ==================
		
		//Test Case 2: The normal isn't orthogonal to tube
		assertEquals("Wrong tube normal- normal isn't orthogonal to tube", 0d, tubeNormal.dotProduct(tube.getAxisRay().getDir()), 0.00001);
		
		//Test Case 3: Checking the head point of the ray (of the tube) with the head point of the optional normal (two options)
		assertTrue("Wrong tube normal", new Vector(0, 0, 1).equals(tubeNormal) || new Vector(0, 0, -1).equals(tubeNormal));
	}
}
