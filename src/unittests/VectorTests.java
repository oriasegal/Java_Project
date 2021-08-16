package unittests;

import static org.junit.Assert.*;

import org.junit.Test;
import static primitives.Util.isZero;

import primitives.*;

/**
 * Testing Vector Class
 * @author Hilla and Oria
 */
public class VectorTests {
	
	/**
	 * Test method for 
	 * {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	public void testAdd() 
	{
	    // ============ Equivalence Partitions Tests ==============
		
		// Test Case 1: Simple test
		assertEquals("Wrong vector add", new Vector(3,3,3), new Vector(1,1,1).add(new Vector (2,2,2)));
		
		// =============== Boundary Values Tests ==================
		
		// Test Case 2: test adding v + (-v)
		try 
		{
			new Vector (3, 3, 3).add(new Vector (-3,-3,-3));
			fail("The add function can't calculate v + (-v)");
		}
		catch (IllegalArgumentException e) {}
	}

	/**
	 * Test method for 
	 * {@link primitives.Vector#subtract(primitives.Vector)}.
	 */
	@Test
	public void testSubtract() 
	{
	    // ============ Equivalence Partitions Tests ==============
		
		// Test Case 1: Simple test
		assertEquals("Wrong vector subtract", new Vector(2, 2, 2), new Vector(3, 3, 3).subtract(new Vector (1, 1, 1)));
		
		// =============== Boundary Values Tests ==================
		
		// Test Case 2: test subtract v - v
		try 
		{
			new  Point3D(3, 3, 3).subtract(new Point3D (3,3,3));
			fail("The subtract function can't calculate v - v");
		}
		catch (IllegalArgumentException e) {}
	}

	/**
	 * Test method for 
	 * {@link primitives.Vector#scale(double)}.
	 */
	@Test
	public void testScale() 
	{
	    // ============ Equivalence Partitions Tests ==============
		
		// Test Case 1: Simple test
		assertEquals("Wrong vector scale", new Vector(4, 6, 8) ,new Vector(2,3,4).scale(2));
		
		// =============== Boundary Values Tests ==================
		
		// Test Case 2: test vector scale by 0
		try 
		{
			new  Vector(4, 6, 8).scale(0d);
			fail("The Scale vector function can't scale by 0.");
		} 
		catch (IllegalArgumentException e) {}
	}

	/**
	 * Test method for 
	 * {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	public void testDotProduct() 
	{
		// ============ Equivalence Partitions Tests ==============
		
		// Test Case 1: Simple test
		assertEquals("Wrong dot product", 18d ,new Vector(2,2,2).dotProduct(new Vector(3,3,3)), 0.00001);
		
		// =============== Boundary Values Tests ==================
		
		// Test Case 2: test vector dot product- two orthogonal vectors
		assertEquals("Dot product between two orthogonal vectors should be zero.", 0d ,new Vector(-1, -2, 0).dotProduct(new Vector(2, -1, 3)), 0.00001);
		
		//try 
		//{
			//new  Vector(4, 6, 8).scale(0d);
			//fail("The dot product function can't scale by 0 ");
		//} 
		//catch (IllegalArgumentException e) {}
	}

	/**
	 * Test method for 
	 * {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	public void testCrossProduct() 
	{
	    // ============ Equivalence Partitions Tests ==============
		
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(0, 3, -2);
		
		// Test Case 1: Check length of the cross product between two orthogonal vectors
		assertEquals("Wrong length of Cross product", v1.length() * v2.length(), (v1.crossProduct(v2)).length(), 0.00001 );
		
		// Test Case 2: Checks that the result of a cross product between two vectors is orthogonal to its operands
		Vector cross = v1.crossProduct(v2);
		assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(cross.dotProduct(v1)));	
		assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(cross.dotProduct(v2)));	
		
		// =============== Boundary Values Tests ==================
		
		// Test Case 3: test zero vector from cross product of co-lined vectors
		Vector v3 = new Vector (-2, -4, -6);
		assertThrows("Cross product for parallel vectors doesn't throw an exception.", IllegalArgumentException.class, () -> v1.crossProduct(v3));
	}

	/**
	 * Test method for 
	 * {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	public void testLengthSquared() 
	{
		// ============ Equivalence Partitions Tests ==============
		
		// Test Case 1: Simple test
		assertEquals("Wrong squared length", 12d, new Vector(2,2,2).lengthSquared(), 0.00001);
	}

	/**
	 * Test method for 
	 * {@link primitives.Vector#length()}.
	 */
	@Test
	public void testLength() 
	{
		// ============ Equivalence Partitions Tests ==============
		
		// Test Case 1: Simple test
		assertEquals("Wrong length", 5d, new Vector(4, 0, 3).length(), 0.00001);
	}

	/**
	 * Test method for 
	 * {@link primitives.Vector#normalize()}.
	 */
	@Test
	public void testNormalize() 
	{
		// ============ Equivalence Partitions Tests ==============
		
		// Test Case 1: Simple test- makes sure the length after normalization is 1
		assertEquals("Wrong normalize (length isn't 1)", 1d, new Vector(1, 2, 3).normalize().length(), 0.00001);
	}

	/**
	 * Test method for 
	 * {@link primitives.Vector#normalized()}.
	 */
	@Test
	public void testNormalized() 
	{
		// ============ Equivalence Partitions Tests ==============
		
		// Test Case 1: Simple test- makes sure the length after normalization is 1
		assertEquals("Wrong normalized (length isn't 1)", 1d, new Vector(1, 2, 3).normalized().length(), 0.000001);
	}
}