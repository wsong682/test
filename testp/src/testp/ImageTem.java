package testp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageTem {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		BufferedImage bi = new BufferedImage(216,387,BufferedImage.TYPE_INT_ARGB_PRE);
		String templateDir = "C:\\Users\\DELL\\Desktop\\temima\\";
		Graphics2D graphics = (Graphics2D) bi.getGraphics();
		for(int i=1;i<=8;i++) {
			fillByTemplate(graphics,rgbColor(Math.random()),templateDir+i+".png");
			ImageIO.write(bi,"png",new FileOutputStream(templateDir+"res"+i+".png"));
		}
		
		ImageIO.write(bi,"png",new FileOutputStream(templateDir+"res.png"));
		
	}
	
	public static void fillByTemplate(Graphics2D graphics,int[] rgb,String path) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new File(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int width = bi.getWidth();
		int height = bi.getHeight();
		int minx = bi.getMinX();
		int miny = bi.getMinY();
//		System.out.println("width=" + width + ",height=" + height + ".");
//		System.out.println("minx=" + minx + ",miniy=" + miny + ".");
		Raster raster = bi.getData();
		Color color = new Color(rgb[0],rgb[1],rgb[2]);
		for (int i = minx; i < width; i++) {
			for (int j = miny; j < height; j++) {
				raster.getPixel(i, j, rgb);
				if(rgb[0]>10||rgb[1]>10||rgb[2]>10) {
					graphics.setColor(color);
					graphics.drawRect(i, j, 1, 1);
				}
			}
		}
	}
	
	public static int[] rgbColor(double ra) {
		Color oldColor = new Color(248,255,0);//初始颜色
		Color newColor = new Color(255,0,0);//结束颜色 
		int r = (int) (oldColor.getRed() + (newColor.getRed() - oldColor.getRed())*ra);
		int g = (int) (oldColor.getGreen() + (newColor.getGreen() - oldColor.getGreen())*ra);
		int b = (int) (oldColor.getBlue() + (newColor.getBlue() - oldColor.getBlue())*ra);
		int[] rgb = {r,g,b};
		return rgb;
	}
}
