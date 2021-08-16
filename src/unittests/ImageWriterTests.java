package unittests;

import org.junit.Test;

import primitives.Color;
import renderer.ImageWriter;

/**
 * Testing ImageWriter Class
 * @author Hilla and Oria
 */
public class ImageWriterTests 
{
	/**
	 * Test method for ImageWriter#writeToImage()}.
	 */
    @Test
    public void testWriteToImage() 
    {
        ImageWriter imageWriter = new ImageWriter("testImageWriter",800,500);
        for (int i = 0; i < 800; i++) 
        {
            for (int j = 0; j < 500; j++) 
            {
                // 800/16 = 50
                if (i % 50 == 0)
                    imageWriter.writePixel(i, j, Color.WHITE);
                
                // 500/10 = 50
                else if (j % 50 == 0) 
                    imageWriter.writePixel(i, j, Color.WHITE);
                
                else 
                    imageWriter.writePixel(i, j, Color.RED);
            }
        }
        
        imageWriter.writeToImage();
    }
}