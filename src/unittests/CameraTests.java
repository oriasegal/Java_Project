package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import elements.Camera;
import primitives.*;

/**
 * Testing Camera Class
 * @author Dan
 */
public class CameraTests {

	/**
	 * Test method for
	 * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
	 */
	@Test
	public void testConstructRayThroughPixel() 
	{
		Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)).setDistance(10);

		// ============ Equivalence Partitions Tests ==============
		
		//Test Case 1: 3X3 Corner (0,0)
		assertEquals("Wrong camera-ray-pixel intersection (test case 1)", new Ray(Point3D.ZERO, new Vector(-2, -2, 10)), camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 0, 0));

		//Test Case 2: 4X4 Corner (0,0)
		assertEquals("Wrong camera-ray-pixel intersection (test case 2)", new Ray(Point3D.ZERO, new Vector(-3, -3, 10)), camera.setViewPlaneSize(8, 8).constructRayThroughPixel(4, 4, 0, 0));

		//Test Case 3: 4X4 Side (0,1)
		assertEquals("Wrong camera-ray-pixel intersection (test case 3)", new Ray(Point3D.ZERO, new Vector(-1, -3, 10)), camera.setViewPlaneSize(8, 8).constructRayThroughPixel(4, 4, 1, 0));

		//Test Case 4: 4X4 Inside (1,1)
		assertEquals("Wrong camera-ray-pixel intersection (test case 4)", new Ray(Point3D.ZERO, new Vector(-1, -1, 10)), camera.setViewPlaneSize(8, 8).constructRayThroughPixel(4, 4, 1, 1));

		// =============== Boundary Values Tests ==================
		
		//Test Case 5: 3X3 Center (1,1)
		assertEquals("Wrong camera-ray-pixel intersection (test case 5)", new Ray(Point3D.ZERO, new Vector(0, 0, 10)), camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 1, 1));

		//Test Case 6: 3X3 Center of Upper Side (0,1)
		assertEquals("Wrong camera-ray-pixel intersection (test case 6)", new Ray(Point3D.ZERO, new Vector(0, -2, 10)), camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 1, 0));

		//Test Case 7: 3X3 Center of Left Side (1,0)
		assertEquals("Wrong camera-ray-pixel intersection (test case 7)", new Ray(Point3D.ZERO, new Vector(-2, 0, 10)), camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 0, 1));

	}

}
