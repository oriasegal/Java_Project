package scene;

import primitives.Color;

import java.util.LinkedList;
import java.util.List;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;

/**
 * Scene class
 * @author Hilla and Oria
 */
public class Scene {

	public String name;
	public Color background = Color.BLACK;
	public AmbientLight ambientLight = new AmbientLight(new Color(0, 0, 0), 0);
	public Geometries geometries = new Geometries();
	public List<LightSource> lights = new LinkedList<LightSource>();
	
	/**
	 * The Scene constructor, gets a SceneBuilder object (an inner class that implements the Builder design pattern) and turns it into a Scene object by copying the matching fields
	 * @param builder an object of the inner class that implements the Builder design pattern
	 */
	public Scene(SceneBuilder builder) 
	{
		name = builder.name;
		background= builder.background;
		ambientLight= builder.ambientLight;
        geometries = builder.geometries;
        lights = builder.lights;
	}

	/**
	 * SceneBuilder class
	 * @author Hilla and Oria
	 */
	public static class SceneBuilder 
	{
		public String name;
		public Color background = Color.BLACK;
		public AmbientLight ambientLight = new AmbientLight(new Color(0, 0, 0), 0);
		public Geometries geometries = new Geometries(); 
		public List<LightSource> lights = new LinkedList<LightSource>();
		
		/**
		 * A default constructor to the SceneBuilder class
		 */
		public SceneBuilder() {}
		
		/**
		 * The function sets the sent name as the scene's (SceneBuilder) name and returns the updated version of the calling SceneBuilder object
		 * @param Name the sent name
		 * @return this
		 */
		public SceneBuilder setName(String Name)
		{
			this.name = Name;
			return this;
		}
		
		/**
		 * The function sets the sent background as the scene's (SceneBuilder) background color and returns the updated version of the calling SceneBuilder object
		 * @param Background the sent background
		 * @return this
		 */
		public SceneBuilder setBackground(Color Background)
		{
			this.background = Background;
			return this;
		}
		
		/**
		 * The function sets the sent ambientLight as the scene's (SceneBuilder) ambientLight and returns the updated version of the calling SceneBuilder object
		 * @param _AmbientLight the sent ambientLight
		 * @return this
		 */
		public SceneBuilder setAmbientLight(AmbientLight _AmbientLight)
		{
			this.ambientLight = _AmbientLight;
			return this;
		}
		
		/**
		 * The function sets the sent geometries as the scene's (SceneBuilder) geometries and returns the updated version of the calling SceneBuilder object
		 * @param _Geometries the sent geometries
		 * @return this
		 */
		public SceneBuilder setGeometries(Geometries _Geometries)
		{
			this.geometries = _Geometries;
			return this;
		}
		
		/**
		 * The function sets the sent lights list as the scene's (SceneBuilder) lights list and returns the updated version of the calling SceneBuilder object
		 * @param _Lights the sent lights list
		 * @return this
		 */
		public SceneBuilder setLights(List<LightSource> _Lights)
		{
			this.lights = _Lights;
			return this;
		}
		
		/**
		 * This function makes the connection between the inner class SceneBuilder and the main class Scene, calls the Scene's constructor with the SceneBuilder object 
		 * @return Scene new object
		 */
		public Scene build()
		{
			return new Scene(this);
		}
	}
}