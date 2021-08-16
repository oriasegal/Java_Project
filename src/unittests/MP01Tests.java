package unittests;

import org.junit.Test;

import elements.*;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.*;
import scene.Scene;

public class MP01Tests 
{

	@Test
	public void MP01TestWithout() 
	{
	
		Scene scene = new Scene.SceneBuilder().setName("MP01 test without").setBackground(new Color(135,206,235)).build();
	
		Camera camera = new Camera(new Point3D(-5000, 0, 0), new Vector(1, 0, 0), new Vector(0, 1, 0)) 
				.setViewPlaneSize(200, 200).setDistance(5300);
	
		scene.lights.add( 
				new SpotLight(new Color(200, 0, 0), new Point3D(30, -0, -60), new Vector(1, 0, 2), 1, 0.00005,0.00003));
		scene.lights.add( 
				new PointLight(new Color(200,200,200), new Point3D(-30, 50, 60), 1, 0.00005, 0.00003));
		scene.lights.add(new SpotLight(new Color(150, 150, 150), new Point3D(60, 50, 0), new Vector(0, 0, -1), 1, 4E-5, 2E-7));		
				

		scene.geometries.add( 
								
				//****sky****
				new Triangle(new Point3D(70, 180, -220), //A
						new Point3D(70, 180, 220), //B
						new Point3D(70, -200, 0)) //C
						.setEmission(new Color(135,206,235)) 
						.setMaterial(new Material.MaterialBuilder().build()),
						
						
				//****sea****
				new Sphere(new Point3D(20, -60, -100), 40) 
				.setEmission(new Color(java.awt.Color.blue))
				.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
	
				new Sphere(new Point3D(20, -60, -60), 40) 
				.setEmission(new Color(java.awt.Color.blue))
				.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
	
				new Sphere(new Point3D(30, -60, 20), 40) 
				.setEmission(new Color(java.awt.Color.blue))
				.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
	
				new Sphere(new Point3D(25, -60, -20), 40) 
				.setEmission(new Color(java.awt.Color.blue))
				.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
	
				new Sphere(new Point3D(30, -60, 60), 40) 
				.setEmission(new Color(java.awt.Color.blue))
				.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
			
				new Sphere(new Point3D(20, -60, 100), 40) 
				.setEmission(new Color(java.awt.Color.blue))
				.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
	
				
				//****sun****
				new Sphere(new Point3D(20, 90, -70), 35) 
					.setEmission(new Color(java.awt.Color.yellow))
					.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
		
				
				//****boat****
					
				//****orange****
				//right grey
				new Triangle(new Point3D(20, 30, 70), //A
						new Point3D(20, 23 ,22), //B
						new Point3D(20, 12, 30)) //C
						.setEmission(new Color(128,128,128)) 
						.setMaterial(new Material.MaterialBuilder().build()),
						
				//****orange****
				//left grey
				new Triangle(new Point3D(20, 30, -70), //A
					new Point3D(20, 12, -30), //B
					new Point3D(20, 23 ,-22)) //C
					.setEmission(new Color(128,128,128)) 
					.setMaterial(new Material.MaterialBuilder().build()),
				
				//****purple****
				//right center
				new Triangle(new Point3D(20, 55, 0.25), //A
						new Point3D(20, 0, 0.25), //B
						new Point3D(20, 12, 30)) //C
						.setEmission(new Color(211,211,211)) 
						.setMaterial(new Material.MaterialBuilder().build()),
				
				//****purple****
				//left center
				new Triangle(new Point3D(20, 55, - 0.25), //A
					new Point3D(20, 12, -30), //B
					new Point3D(20, 0, - 0.25)) //C
				.setEmission(new Color(211,211,211)) 
						.setMaterial(new Material.MaterialBuilder().build()),
						
				//****red****
				// big right
				new Triangle(new Point3D(20, -25, 40), //A
							new Point3D(20, 30, 70), //B
							new Point3D(20, 0, 0)) //C
							.setEmission(new Color(255,255,255))
							.setMaterial(new Material.MaterialBuilder().build()),				
				
				//****bordo****
				//big left
				new Triangle(new Point3D(20, -25, -40), //A
							new Point3D(20, 0, 0), //B
							new Point3D(20, 30, -70)) //C
							.setEmission(new Color(220,220,220))
							.setMaterial(new Material.MaterialBuilder().build()),
						
				//****blue****		
				//the grey base
				new Triangle(new Point3D(20, 0, 0), //A
							new Point3D(20, -25, -40), //B
							new Point3D(20, -25, 40)) //C
							.setEmission(new Color(169,169,169)) 
							.setMaterial(new Material.MaterialBuilder().setKR(0.5).build()));
			

			Render render = new Render() 
					.setImageWriter(new ImageWriter("MP01Without", 500, 500)) 
					.setCamera(camera) 
					.setRayTracer(new RayTracerBasic(scene));
			render.renderImage();
			render.writeToImage();
	
	}
	
	@Test
	public void MP01TestWithDOF() 
	{
		Scene scene = new Scene.SceneBuilder().setName("MP01 test with depth of field").setBackground(new Color(135,206,235)).build();
	
		Camera camera = new Camera(new Point3D(-5000, 0, 0), new Vector(1, 0, 0), new Vector(0, 1, 0), 100, 400, true, 25) 
				.setViewPlaneSize(300, 300).setDistance(4500);
		
		scene.lights.add( 
				new SpotLight(new Color(200, 0, 0), new Point3D(30, -0, -60), new Vector(1, 0, 2), 1, 0.00005,0.00003));
		scene.lights.add( 
				new PointLight(new Color(200,200,200), new Point3D(-30, 50, 60), 1, 0.00005, 0.00003));
		scene.lights.add(new SpotLight(new Color(150, 150, 150), new Point3D(60, 50, 0), new Vector(0, 0, -1), 1, 4E-5, 2E-7));		
				

		scene.geometries.add( 
										
				//****sea****
				new Sphere(new Point3D(-50, -60, -100), 40) 
				.setEmission(new Color(java.awt.Color.blue))
				.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
	
				new Sphere(new Point3D(-50, -60, -60), 40) 
				.setEmission(new Color(java.awt.Color.blue))
				.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
	
				new Sphere(new Point3D(-50, -60, 20), 40) 
				.setEmission(new Color(java.awt.Color.blue))
				.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
	
				new Sphere(new Point3D(-50, -60, -20), 40) 
				.setEmission(new Color(java.awt.Color.blue))
				.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
	
				new Sphere(new Point3D(-50, -60, 60), 40) 
				.setEmission(new Color(java.awt.Color.blue))
				.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
			
				new Sphere(new Point3D(-50, -60, 100), 40) 
				.setEmission(new Color(java.awt.Color.blue))
				.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
	
				
				//****sun****
				new Sphere(new Point3D(50, 90, -70), 45) 
					.setEmission(new Color(java.awt.Color.yellow))
					.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
		
				
				//****boat****
					
				//****orange****
				//right grey
				new Triangle(new Point3D(20, 30, 70), //A
						new Point3D(20, 23 ,22), //B
						new Point3D(20, 12, 30)) //C
						.setEmission(new Color(128,128,128)) 
						.setMaterial(new Material.MaterialBuilder().build()),
				
				//****orange****
				//left grey
				new Triangle(new Point3D(20, 30, -70), //A
					new Point3D(20, 12, -30), //B
					new Point3D(20, 23 ,-22)) //C
					.setEmission(new Color(128,128,128)) 
					.setMaterial(new Material.MaterialBuilder().build()),
				
				//****purple****
				//right center
				new Triangle(new Point3D(20, 55, 0.25), //A
						new Point3D(20, 0, 0.25), //B
						new Point3D(20, 12, 30)) //C
						.setEmission(new Color(211,211,211)) 
						.setMaterial(new Material.MaterialBuilder().build()),
				
				//****purple****
				//left center
				new Triangle(new Point3D(20, 55, - 0.25), //A
					new Point3D(20, 12, -30), //B
					new Point3D(20, 0, - 0.25)) //C
				.setEmission(new Color(211,211,211)) 
						.setMaterial(new Material.MaterialBuilder().build()),
		
				//****red****
				// big right
				new Triangle(new Point3D(20, -25, 40), //A
							new Point3D(20, 30, 70), //B
							new Point3D(20, 0, 0)) //C
							.setEmission(new Color(255,255,255)) 
							.setMaterial(new Material.MaterialBuilder().build()),				
				
				//****bordo****
				//big left
				new Triangle(new Point3D(20, -25, -40), //A
							new Point3D(20, 0, 0), //B
							new Point3D(20, 30, -70)) //C
							.setEmission(new Color(220,220,220)) 
							.setMaterial(new Material.MaterialBuilder().build()),
			
				//****blue****		
				//the grey base
				new Triangle(new Point3D(20, 0, 0), //A
							new Point3D(20, -25, -40), //B
							new Point3D(20, -25, 40)) //C
							.setEmission(new Color(169,169,169)) 
							.setMaterial(new Material.MaterialBuilder().setKR(0.5).build()));

			Render render = new Render() 
					.setImageWriter(new ImageWriter("MP01WithDepthOfField", 500, 500)) 
					.setCamera(camera) 
					.setRayTracer(new RayTracerBasic(scene));
			render.renderImage();
			render.writeToImage();
	}
	
	
	@Test
	public void MP01TestWithSuperSampling() 
	{
		Scene scene = new Scene.SceneBuilder().setName("MP01 test with super sampling").setBackground(new Color(135,206,235)).build();
				
		Camera camera = new Camera(new Point3D(-5000, 0, 0), new Vector(1, 0, 0), new Vector(0, -1, 0)) 
				.setViewPlaneSize(300, 300).setDistance(5700).setNumberOfRays(100).setSuperS(true);
		
		scene.lights.add( 
				new SpotLight(new Color(200, 0, 0), new Point3D(30, -0, -60), new Vector(1, 0, 2), 1, 0.00005,0.00003));
		scene.lights.add( 
				new PointLight(new Color(200,200,200), new Point3D(-30, 50, 60), 1, 0.00005, 0.00003));
		scene.lights.add(new SpotLight(new Color(150, 150, 150), new Point3D(60, 50, 0), new Vector(0, 0, -1), 1, 4E-5, 2E-7));		
				

		scene.geometries.add( 
												
				//****sea****
				new Sphere(new Point3D(-50, -130, 150), 70) 
				.setEmission(new Color(java.awt.Color.blue))
				.setMaterial(new Material.MaterialBuilder().setKD(0.4).setKR(0).setKS(1).setNShininess(50).build()),
				
				new Sphere(new Point3D(-50, -130, 90), 70) 
				.setEmission(new Color(java.awt.Color.blue))
				.setMaterial(new Material.MaterialBuilder().setKD(0.4).setKR(0).setKS(1).setNShininess(50).build()),
				
				new Sphere(new Point3D(-50, -130, 30), 70) 
				.setEmission(new Color(java.awt.Color.blue))
				.setMaterial(new Material.MaterialBuilder().setKD(0.4).setKR(0).setKS(1).setNShininess(50).build()),
	
				new Sphere(new Point3D(-50, -130, -30), 70) 
				.setEmission(new Color(java.awt.Color.blue))
				.setMaterial(new Material.MaterialBuilder().setKD(0.4).setKR(0).setKS(1).setNShininess(50).build()),

				new Sphere(new Point3D(-50, -130, -90), 70) 
				.setEmission(new Color(java.awt.Color.blue))
				.setMaterial(new Material.MaterialBuilder().setKD(0.4).setKR(0).setKS(1).setNShininess(50).build()),
				
				new Sphere(new Point3D(-50, -130, -150), 70) 
				.setEmission(new Color(java.awt.Color.blue))
				.setMaterial(new Material.MaterialBuilder().setKD(0.4).setKR(0).setKS(1).setNShininess(50).build()),
				
				
				//****sun****
				new Sphere(new Point3D(50, 130, -100), 55) 
					.setEmission(new Color(java.awt.Color.yellow))
					.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
					
					
				//****cloud****
				new Sphere(new Point3D(50, 80, 55), 12) 
					.setEmission(new Color(244, 243, 239))
					.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
					
				new Sphere(new Point3D(50, 80, 75), 12) 
					.setEmission(new Color(244, 243, 239))
					.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
					
				new Sphere(new Point3D(50, 80, 95), 12) 
					.setEmission(new Color(244, 243, 239))
					.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
					
				new Sphere(new Point3D(50, 80, 115), 12) 
					.setEmission(new Color(244, 243, 239))
					.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
					
				new Sphere(new Point3D(50, 100, 100), 18) 
					.setEmission(new Color(244, 243, 239))
					.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
					
				new Sphere(new Point3D(50, 85, 80), 12) 
					.setEmission(new Color(244, 243, 239))
					.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
					
				new Sphere(new Point3D(50, 100, 70), 15) 
					.setEmission(new Color(244, 243, 239))
					.setMaterial(new Material.MaterialBuilder().setKD(0.6).setKR(0).setKS(1).setNShininess(80).build()),
		
				
				//****boat****
				
				//****orange****
				//right grey
				new Triangle(new Point3D(20, -10, 70), //A
						new Point3D(20, -17 ,22), //B
						new Point3D(20, -28, 30)) //C
						.setEmission(new Color(128,128,128))  
						.setMaterial(new Material.MaterialBuilder().build()),
				
				//****orange****
				//left grey
				new Triangle(new Point3D(20, -10, -70), //A
					new Point3D(20, -28, -30), //B
					new Point3D(20, -17 ,-22)) //C
					.setEmission(new Color(128,128,128)) 
					.setMaterial(new Material.MaterialBuilder().build()),
				
				//****purple****
				//right center
				new Triangle(new Point3D(20, 15, 0.25), //A
						new Point3D(20, -40, 0.25), //B
						new Point3D(20, -28, 30)) //C
						.setEmission(new Color(211,211,211)) 
						.setMaterial(new Material.MaterialBuilder().build()),
				
				//****purple****
				//left center
				new Triangle(new Point3D(20, 15, - 0.25), //A
					new Point3D(20, -28, -30), //B
					new Point3D(20, -40, - 0.25)) //C
				.setEmission(new Color(211,211,211))
						.setMaterial(new Material.MaterialBuilder().build()),
						
				//****red****
				// big right
				new Triangle(new Point3D(20, -65, 40), //A
							new Point3D(20, -10, 70), //B
							new Point3D(20, -40, 0)) //C
							.setEmission(new Color(220,220,220))
							.setMaterial(new Material.MaterialBuilder().build()),	
							
				//****bordo****
				//big left
				new Triangle(new Point3D(20, -65, -40), //A
							new Point3D(20, -40, 0), //B
							new Point3D(20, -10, -70)) //C
							.setEmission(new Color(255,255,255)) 
							.setMaterial(new Material.MaterialBuilder().build()),
							
				//****blue****	
				//the grey base
				new Triangle(new Point3D(20, -40, 0), //A
							new Point3D(20, -65, -40), //B
							new Point3D(20, -65, 40)) //C
							.setEmission(new Color(169,169,169)) 
							.setMaterial(new Material.MaterialBuilder().setKR(0.5).build()),
							
							
				//****flag****
				new Triangle(new Point3D(20, 15, 0.25), //A
							new Point3D(20, 30, 0.25), //B
							new Point3D(20, 22.5, -20)) //C
							.setEmission(new Color(java.awt.Color.RED)) 
							.setMaterial(new Material.MaterialBuilder().setKR(0.5).build()));

		


			Render render = new Render() 
					.setImageWriter(new ImageWriter("MP01WithSuperSampling", 500, 500)) 
					.setCamera(camera) 
					.setRayTracer(new RayTracerBasic(scene));
			render.renderImage();
			render.writeToImage();
	}
}