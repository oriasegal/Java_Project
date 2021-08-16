package unittests;

import org.junit.Test;

import elements.*;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests 
{
	private Scene scene = new Scene.SceneBuilder().setName("Test scene").build();

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheres() 
	{
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) 
				.setViewPlaneSize(150, 150).setDistance(1000);

		scene.geometries.add( 
				new Sphere(new Point3D(0, 0, -50), 50) 
						.setEmission(new Color(java.awt.Color.BLUE))
						.setMaterial(new Material.MaterialBuilder().setKD(0.4).setKS(0.3).setNShininess(100).setKT(0.3).build()),
				new Sphere(new Point3D(0, 0, -50), 25) 
						.setEmission(new Color(java.awt.Color.RED)) 
						.setMaterial(new Material.MaterialBuilder().setKD(0.5).setKS(0.5).setNShininess(100).build()));
		scene.lights.add( 
				new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500), new Vector(-1, -1, -2), 1, 0.0004, 0.0000006));

		Render render = new Render() //
				.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point3D(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(2500, 2500).setDistance(10000); //

		scene.ambientLight = new AmbientLight(new Color(255, 255, 255), 0.1);

		scene.geometries.add( //
				new Sphere(new Point3D(-950, -900, -1000), 400) //
						.setEmission(new Color(0, 0, 100)) //
						.setMaterial(new Material.MaterialBuilder().setKD(0.25).setKS(0.25).setNShininess(20).setKT(0.5).build()),
				new Sphere(new Point3D(-950, -900, -1000), 200) //
						.setEmission(new Color(100, 20, 20)) //
						.setMaterial(new Material.MaterialBuilder().setKD(0.25).setKS(0.25).setNShininess(20).build()),
				new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
						new Point3D(670, 670, 3000)) //
								.setEmission(new Color(20, 20, 20)) //
								.setMaterial(new Material.MaterialBuilder().setKR(1).build()),
				new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
						new Point3D(-1500, -1500, -2000)) //
								.setEmission(new Color(20, 20, 20)) //
								.setMaterial(new Material.MaterialBuilder().setKR(0.5).build()));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, -750, -150), new Vector(-1, -1, -4), 1, 0.00001, 0.000005));

		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) 
				.setViewPlaneSize(200, 200).setDistance(1000);

		scene.ambientLight = new AmbientLight(new Color(java.awt.Color.WHITE), 0.15);

		scene.geometries.add( 
				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) 
						.setMaterial(new Material.MaterialBuilder().setKD(0.5).setKS(0.5).setNShininess(60).build()),
				new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) 
						.setMaterial(new Material.MaterialBuilder().setKD(0.5).setKS(0.5).setNShininess(60).build()),
				new Sphere(new Point3D(60, 50, -50), 30) 
						.setEmission(new Color(java.awt.Color.BLUE)) 
						.setMaterial(new Material.MaterialBuilder().setKD(0.2).setKS(0.2).setNShininess(30).setKT(0.6).build()));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1), 1, 4E-5, 2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}
	
	@Test
	public void new2() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) 
				.setViewPlaneSize(200, 200).setDistance(1000);

		scene.ambientLight = new AmbientLight(new Color(java.awt.Color.WHITE), 0.15);

		scene.geometries.add( 
				new Triangle(new Point3D(-180, -180, -150), new Point3D(180, -180, -155), new Point3D(105, 105, -170)) 
						.setMaterial(new Material.MaterialBuilder().setKD(0.5).setKS(0.5).setNShininess(60).build()),
				new Triangle(new Point3D(-180, -180, -150), new Point3D(-180, 180, -155), new Point3D(105, 105, -170)) 
						.setMaterial(new Material.MaterialBuilder().setKD(0.5).setKS(0.5).setNShininess(60).build()),
				//Small
				new Sphere(new Point3D(50, 50, -115), 10) //
				.setEmission(new Color(java.awt.Color.RED)) //
				.setMaterial(new Material.MaterialBuilder().setKD(0.5).setKS(0.5).setNShininess(30).setKT(0.3).build()),
			
				//Medium		
				new Sphere(new Point3D(0, 0, -115), 20) //
				.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material.MaterialBuilder().setKD(0.5).setKS(0.5).setNShininess(30).setKT(0.3).build()),
				//Large		
				new Sphere(new Point3D(-60, -60, -135), 30) //
				.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material.MaterialBuilder().setKD(0.5).setKS(0.5).setNShininess(50).setKT(0.3).build()));
					
		scene.lights.add(new SpotLight(new Color(150, 150, 150), new Point3D(60, 50, 0), new Vector(0, 0, -1), 1, 4E-5, 2E-7));

		ImageWriter imageWriter = new ImageWriter("new2", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}
	
}