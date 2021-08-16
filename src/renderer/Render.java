package renderer;

import java.util.List;
import java.util.MissingResourceException;

import elements.Camera;
import primitives.Color;
import primitives.Ray;

/**
 * Render class
 * @author Hilla and Oria
 */
public class Render 
{
		private ImageWriter image = null;
		private Camera camera = null;
		private RayTracerBase rayTracer = null;

		private static final String RESOURCE_ERROR = "Renderer resource not set";
		private static final String RENDER_CLASS = "Render";
		private static final String IMAGE_WRITER_COMPONENT = "Image writer";
		private static final String CAMERA_COMPONENT = "Camera";
		private static final String RAY_TRACER_COMPONENT = "Ray tracer";

		private int threadsCount = 0;
		private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
		private boolean print = false; // printing progress percentage
		
		private boolean adaptiveSuperSampling;
		private int MAX_ADAPTIVERECURSION = 4;

		/**
		 * A default constructor to the Render class
		 */
		public Render() {}

		/**
		 * The function sets the sent image as the render's image and returns the updated version of the calling render object
		 * @param image the sent image
		 * @return this
		 */
		public Render setImageWriter(ImageWriter image) 
		{
			this.image = image;
			return this;
		}
		
		/**
		 * The function sets the sent camera as the render's camera and returns the updated version of the calling render object
		 * @param _Camera the sent camera
		 * @return this
		 */
		public Render setCamera(Camera _Camera) 
		{
			this.camera = _Camera;
			return this;
		}

		/**
		 * The function sets the sent rayTracer as the render's rayTracer and returns the updated version of the calling render object
		 * @param _RayTracer, the sent rayTracer
		 * @return this
		 */
		public Render setRayTracer(RayTracerBase _RayTracer) 
		{
			this.rayTracer = _RayTracer;
			return this;
		}

		/**
		 * Set multi-threading <br>
		 * - if the parameter is 0 - number of cores less 2 is taken
		 * 
		 * @param threads number of threads
		 * @return the Render object itself
		 */
		public Render setMultithreading(int threads) 
		{
			if (threads < 0)
				throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
			if (threads != 0)
				this.threadsCount = threads;
			else 
			{
				int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
				this.threadsCount = cores <= 2 ? 1 : cores;
			}
			return this;
		}
		
		/**
		 * Set debug printing on
		 * 
		 * @return the Render object itself
		 */
		public Render setDebugPrint() 
		{
			print = true;
			return this;
		}
		
		public boolean getAdaptiveSuperSampling()
		{
			return this.adaptiveSuperSampling;
		}
		
		public Render setAdaptiveSuperSampling(boolean value)
		{
			this.adaptiveSuperSampling = value;
			return this;
		}
		
		
		/**
		 * Pixel is an internal helper class whose objects are associated with a Render
		 * object that they are generated in scope of. It is used for multithreading in
		 * the Renderer and for follow up its progress.<br/>
		 * There is a main follow up object and several secondary objects - one in each
		 * thread.
		 * 
		 * @author Dan
		 *
		 */
		private class Pixel 
		{
			private long maxRows = 0;
			private long maxCols = 0;
			private long pixels = 0;
			public volatile int row = 0;
			public volatile int col = -1;
			private long counter = 0;
			private int percents = 0;
			private long nextCounter = 0;

			/**
			 * The constructor for initializing the main follow up Pixel object
			 * 
			 * @param maxRows the amount of pixel rows
			 * @param maxCols the amount of pixel columns
			 */
			public Pixel(int maxRows, int maxCols) 
			{
				this.maxRows = maxRows;
				this.maxCols = maxCols;
				this.pixels = (long) maxRows * maxCols;
				this.nextCounter = this.pixels / 100;
				if (Render.this.print)
					System.out.printf("\r %02d%%", this.percents);
			}

			/**
			 * Default constructor for secondary Pixel objects
			 */
			public Pixel() {}

			/**
			 * Internal function for thread-safe manipulating of main follow up Pixel object
			 * - this function is critical section for all the threads, and main Pixel
			 * object data is the shared data of this critical section.<br/>
			 * The function provides next pixel number each call.
			 * 
			 * @param target target secondary Pixel object to copy the row/column of the
			 *               next pixel
			 * @return the progress percentage for follow up: if it is 0 - nothing to print,
			 *         if it is -1 - the task is finished, any other value - the progress
			 *         percentage (only when it changes)
			 */
			private synchronized int nextP(Pixel target) 
			{
				++col;
				++this.counter;
				if (col < this.maxCols) 
				{
					target.row = this.row;
					target.col = this.col;
					if (Render.this.print && this.counter == this.nextCounter) 
					{
						++this.percents;
						this.nextCounter = this.pixels * (this.percents + 1) / 100;
						return this.percents;
					}
					return 0;
				}
				++row;
				if (row < this.maxRows) 
				{
					col = 0;
					target.row = this.row;
					target.col = this.col;
					if (Render.this.print && this.counter == this.nextCounter) 
					{
						++this.percents;
						this.nextCounter = this.pixels * (this.percents + 1) / 100;
						return this.percents;
					}
					return 0;
				}
				return -1;
			}

			/**
			 * Public function for getting next pixel number into secondary Pixel object.
			 * The function prints also progress percentage in the console window.
			 * 
			 * @param target target secondary Pixel object to copy the row/column of the
			 *               next pixel
			 * @return true if the work still in progress, -1 if it's done
			 */
			public boolean nextPixel(Pixel target) 
			{
				int percent = nextP(target);
				if (Render.this.print && percent > 0)
					synchronized (this) 
					{
						notifyAll();
					}
				if (percent >= 0)
					return true;
				if (Render.this.print)
					synchronized (this) 
					{
						notifyAll();
					}
				return false;
			}

			/**
			 * Debug print of progress percentage - must be run from the main thread
			 */
			public void print() 
			{
				if (Render.this.print)
					while (this.percents < 100)
						try 
						{
							synchronized (this) 
							{
								wait();
							}
							System.out.printf("\r %02d%%", this.percents);
							System.out.flush();
						} 
						catch (Exception e) {}
			}
			
		} //end of Pixel class
		
		
		
		/**
		 * This function renders image's pixel color map from the scene included with
		 * the Renderer object - with multi-threading
		 */
		private void renderImageThreaded() 
		{
			final int nX = image.getNx();
			final int nY = image.getNy();
			final Pixel thePixel = new Pixel(nY, nX); //new Pixel(nX, nY);
			
			// Generate threads
			Thread[] threads = new Thread[threadsCount];
			
			for (int i = threadsCount - 1; i >= 0; --i) 
			{
				threads[i] = new Thread(() -> 
				{
					Pixel pixel = new Pixel();
					
					while (thePixel.nextPixel(pixel))
					{
						if(this.getAdaptiveSuperSampling()) //using super sampling and using threads and using adaptive super sampling
						{
							Color co = rayTracer.constructManyRaysUsingASSRecursion(camera, nX, nY, pixel.col, pixel.row, -camera.getWidth()/nX, -camera.getHeight()/nY, -camera.getWidth()/nX, -camera.getHeight()/nY, MAX_ADAPTIVERECURSION);
							this.image.writePixel(pixel.col, pixel.row, co);
						}
						
						else //using super sampling and using threads, not using adaptive super sampling
						{
							List<Ray> rays = camera.constructManyRaysUsingSuperSampling(nX, nY, pixel.col, pixel.row );
							Color co= rayTracer.traceRays(rays, camera);
							this.image.writePixel(pixel.col, pixel.row, co);
						}
					}
				});
			}
			
			// Start threads
			for (Thread thread : threads)
				thread.start();

			// Print percents on the console
			thePixel.print();

			// Ensure all threads have finished
			for (Thread thread : threads)
				try 
				{
					thread.join();
				} 
				catch (Exception e) {}

			if (print)
				System.out.print("\r100%");
		}

		
		/**
		 * A function that renders an image by going throw all pixels of the calling render's image and calculating the color by the ray that goes throw it
		 * @exception MissingResourceException in all cases that the field of the calling render object is null
		 * @exception UnsupportedOperationException when can't implement the render
		 */
		public void renderImage()
		{
			try 
			{
				 if (image == null) 
		                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
		            
				 if (camera == null) 
		                throw new MissingResourceException("missing resource", Camera.class.getName(), "");
		            
				 if (rayTracer == null) 
		                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
		            

				int nX= image.getNx();
				int nY= image.getNy();
				
				if (threadsCount == 0)
				{
					for(int i = 0; i < nY; i++)
					{
						for(int j = 0; j < nX; j++)
						{
							if (camera.getSuperSampling()) //using super sampling, not using threads
							{
								if(this.getAdaptiveSuperSampling()) //using super sampling and using threads and using adaptive super sampling
								{
									Color co = rayTracer.constructManyRaysUsingASSRecursion(camera, nX, nY, j, i, -camera.getWidth()/nX, -camera.getHeight()/nY, -camera.getWidth()/nX, -camera.getHeight()/nY, MAX_ADAPTIVERECURSION);
									this.image.writePixel(j, i, co);
								}
								
								else
								{
									List<Ray> rays = camera.constructManyRaysUsingSuperSampling(nX, nY, j, i );
									Color co = rayTracer.traceRays(rays, camera);
									this.image.writePixel(j, i, co);
								}
							}
							
							else //not using super sampling and not using threads
							{
								Ray ray = camera.constructRayThroughPixel(nX, nY, j, i );
								Color co = rayTracer.traceRay(ray);
								this.image.writePixel(j, i, co);
							}
		
						}
					}
				}
				
				else //using super sampling and using threads
					renderImageThreaded();
				
			}
			
			catch (MissingResourceException e) {
	            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
	        }
		}
		
		/**
		 * A function that print the grid of the final image to the sent render object (without changing the original sent image)
		 * @param interval will help know where to place the grid
		 * @param color the color of the grid
		 * @exception MissingResourceException when the render's image field is null
		 */
		public void printGrid(int interval, Color color)
		{
			try
			{	if (image == null)
	                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
			
				int nX= image.getNx();
				int nY= image.getNy();
				for(int i = 0; i < nY; i++)
				{
					for(int j = 0; j < nX; j++)
					{
						if (i % interval == 0 || j % interval == 0 )
							image.writePixel(j, i, color);
					}
				}
			}
			
			catch(MissingResourceException e)
			{
				System.out.println(e);
			}
		}
		
		/**
		 * A function that draws the image for the calling render object (by using the ImageWriter writeToImage function)
		 * @exception MissingResourceException when the render's image field is null
		 */
		public void writeToImage()
		{
			try
			{
				if (image == null) 
	                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
	            
				image.writeToImage();
			}
			
			catch(MissingResourceException e)
			{
				System.out.println(e);
			}
		}
}