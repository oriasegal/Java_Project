package primitives;

/**
 * Material class
 * @author Hilla and Oria
 */
public class Material 
{
	public double kD = 0;
	public double kS = 0;
	public int nShininess = 0;
	public double kT = 0;
	public double kR = 0;
	
	/**
	 * The Material constructor, gets a MaterialBuilder object (an inner class that implements the Builder design pattern) and turns it into a Material object by copying the matching fields
	 * @param builder an object of the inner class that implements the Builder design pattern
	 */
	public Material(MaterialBuilder builder) 
	{
		kD = builder.kD;
		kS = builder.kS;
		nShininess = builder.nShininess;
		kT= builder.kT;
		kR= builder.kR;

	}
	
	/**
	 * MaterialBuilder class
	 * @author Hilla and Oria
	 */
	public static class MaterialBuilder
	{
		public double kD = 0;
		public double kS = 0;
		public int nShininess = 0;
		public double kT = 0;
		public double kR = 0;
		
		/**
		 * A default constructor to the MaterialBuilder class
		 */
		public MaterialBuilder() {}
		
		/**
		 * The function sets the sent kD as the scene's (MaterialBuilder) kD value and returns the updated version of the calling MaterialBuilder object
		 * @param KD the sent kD
		 * @return this
		 */
		public MaterialBuilder setKD(double KD)
		{
			this.kD = KD;
			return this;
		}

		/**
		 * The function sets the sent kS as the material's (MaterialBuilder) kS value and returns the updated version of the calling MaterialBuilder object
		 * @param KS the sent kS
		 * @return this
		 */
		public MaterialBuilder setKS(double KS)
		{
			this.kS = KS;
			return this;
		}
		
		/**
		 * The function sets the sent kT as the material's (MaterialBuilder) kT value and returns the updated version of the calling MaterialBuilder object
		 * @param KT the sent kT
		 * @return this
		 */
		
		public MaterialBuilder setKT(double KT) {
			this.kT = KT;
			return this;
		}

		/**
		 * The function sets the sent kR as the material's (MaterialBuilder) kR value and returns the updated version of the calling MaterialBuilder object
		 * @param KR the sent kR
		 * @return this
		 */
		
		public MaterialBuilder setKR(double KR) {
			this.kR = KR;
			return this;
		}

		/**
		 * The function sets the sent nShininess as the material's (MaterialBuilder) nShininess value and returns the updated version of the calling MaterialBuilder object
		 * @param NShininess the sent nShininess
		 * @return this
		 */
		public MaterialBuilder setNShininess(int NShininess)
		{
			this.nShininess = NShininess;
			return this;
		}
		
		/**
		 * This function makes the connection between the inner class MaterialBuilder and the main class Material, calls the Material's constructor with the MaterialBuilder object 
		 * @return Material new object
		 */
		public Material build()
		{
			return new Material(this);
		}	
	}
}