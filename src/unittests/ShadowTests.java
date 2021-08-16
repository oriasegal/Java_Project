package unittests;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Testing basic shadows
 * 
 * @author Dan
 */
public class ShadowTests {
	private Scene scene = new Scene.SceneBuilder().setName("Test scene").build();
	private Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setViewPlaneSize(200, 200).setDistance(1000);

	
	
	/**
	 * Produce a picture of a sphere and triangle with point light and shade
	 */
	@Test
	public void sphereTriangleInitial() {
		scene.geometries.add( //
				new Sphere(new Point3D(0, 0, -200), 60 ) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material.MaterialBuilder().setKD(0.5).setKS(0.5).setNShininess(30).build()), //
				new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material.MaterialBuilder().setKD(0.5).setKS(0.5).setNShininess(30).build()) //
		);
		scene.lights.add( //add the 3 k
				new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3), 1, 1E-5, 1.5E-7));

		Render render = new Render(). //
				setImageWriter(new ImageWriter("shadowSphereTriangleInitial", 400, 400)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}


	/**
	 * Produce a picture of a sphere and triangle with point light and shade
	 */
	@Test
	public void sphereTriangle1() {
		scene.geometries.add( //
				new Sphere(new Point3D(0, 0, -200), 60 ) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material.MaterialBuilder().setKD(0.5).setKS(0.5).setNShininess(30).build()), //
				new Triangle(new Point3D(-62, -35, 0), new Point3D(-32, -65, 0), new Point3D(-61, -63, -4)) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material.MaterialBuilder().setKD(0.5).setKS(0.5).setNShininess(30).build()) //
		);
		scene.lights.add( //add the 3 k
				new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3), 1, 1E-5, 1.5E-7));

		Render render = new Render(). //
				setImageWriter(new ImageWriter("shadowSphereTriangle1", 400, 400)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}


	/**
	 * Produce a picture of a sphere and triangle with point light and shade
	 */
	@Test
	public void sphereTriangle2() {
		scene.geometries.add( //
				new Sphere(new Point3D(0, 0, -200), 60 ) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material.MaterialBuilder().setKD(0.5).setKS(0.5).setNShininess(30).build()), //
				new Triangle(new Point3D(-49, -21, 0), new Point3D(-19, -51, 0), new Point3D(-48, -51, -4)) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material.MaterialBuilder().setKD(0.5).setKS(0.5).setNShininess(30).build()) //
		);
		scene.lights.add( //add the 3 k
				new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3), 1, 1E-5, 1.5E-7));

		Render render = new Render(). //
				setImageWriter(new ImageWriter("shadowSphereTriangle2", 400, 400)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}


	/**
	 * Produce a picture of a sphere and triangle with point light and shade
	 */
	@Test
	public void sphereTriangle3() {
		scene.geometries.add( //
				new Sphere(new Point3D(0, 0, -200), 60 ) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material.MaterialBuilder().setKD(0.5).setKS(0.5).setNShininess(30).build()), //
				new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material.MaterialBuilder().setKD(0.5).setKS(0.5).setNShininess(30).build()) //
		);
		scene.lights.add( //add the 3 k
				new SpotLight(new Color(400, 240, 0), new Point3D(-85, -85, 130), new Vector(1, 1, -3), 1, 1E-5, 1.5E-7));

		Render render = new Render(). //
				setImageWriter(new ImageWriter("shadowSphereTriangle3", 400, 400)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}


	/**
	 * Produce a picture of a sphere and triangle with point light and shade
	 */
	@Test
	public void sphereTriangle4() {
		scene.geometries.add( //
				new Sphere(new Point3D(0, 0, -200), 60 ) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material.MaterialBuilder().setKD(0.5).setKS(0.5).setNShininess(30).build()), //
				new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material.MaterialBuilder().setKD(0.5).setKS(0.5).setNShininess(30).build()) //
		);
		scene.lights.add( //add the 3 k
				new SpotLight(new Color(400, 240, 0), new Point3D(-75, -75, 70), new Vector(1, 1, -3), 1, 1E-5, 1.5E-7));

		Render render = new Render(). //
				setImageWriter(new ImageWriter("shadowSphereTriangle4", 400, 400)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a Sphere
	 * producing a shading
	 */
	@Test
	public void trianglesSphere() {
		scene.ambientLight= new AmbientLight(new Color(java.awt.Color.WHITE), 0.15);

		scene.geometries.add( //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
						.setMaterial(new Material.MaterialBuilder().setKS(0.8).setNShininess(60).build()), //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
						.setMaterial(new Material.MaterialBuilder().setKS(0.8).setNShininess(60).build()), //
				new Sphere(new Point3D(0, 0, -115), 30) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material.MaterialBuilder().setKD(0.5).setKS(0.5).setNShininess(30).build()) //
		);
		scene.lights.add( //
				new SpotLight(new Color(700, 400, 400), new Point3D(40, 40, 115), new Vector(-1, -1, -4), 1 ,4E-4, 2E-5));

		Render render = new Render() //
				.setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}
}