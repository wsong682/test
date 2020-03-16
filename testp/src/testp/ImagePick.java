package testp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

public class ImagePick {

	public static void main(String[] args) throws Exception {
		getImagePixel("C:\\Users\\DELL\\Desktop\\SF6.png");
	}

	public static void getImagePixel(String image) throws Exception {
		List<int[]> rgbs = new ArrayList<>();
		File file = new File(image);
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int width = bi.getWidth();
		int height = bi.getHeight();
		int minx = bi.getMinX();
		int miny = bi.getMinY();
		System.out.println("width=" + width + ",height=" + height + ".");
		System.out.println("minx=" + minx + ",miniy=" + miny + ".");
		Raster raster = bi.getData();
		BufferedImage bi2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = (Graphics2D) bi2.getGraphics();
		for (int i = minx; i < width; i++) {
			for (int j = miny; j < height; j++) {
//				int pixel = bi.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
				int[] rgb = new int[3];
				raster.getPixel(i, j, rgb);
//				rgb[0] = (pixel & 0xff0000) >> 16;
//				rgb[1] = (pixel & 0xff00) >> 8;
//				rgb[2] = (pixel & 0xff);
//				 String c = rgb[0]+","+rgb[1]+","+rgb[2];
				int dis = 0;
				boolean contains = false;
				Integer p = null;
				for (int k = 0; k < rgbs.size(); k++) {
					dis = (int) Math.pow(rgbs.get(k)[0] - rgb[0], 2) + (int) Math.pow(rgbs.get(k)[1] - rgb[1], 2)
							+ (int) Math.pow(rgbs.get(k)[2] - rgb[2], 2);
					if (Math.abs(dis) < 50) {
						contains = true;
						p = k;
						// System.out.println(rgbs.get(k)[0]);
						break;
					}
				}
				if (!contains) {
					// System.out.println(dis);
					// System.out.println(contains);
					graphics.setColor(new Color(rgb[0],rgb[1],rgb[2]));
					graphics.drawRect(i, j, 1, 1);
					rgbs.add(rgb);
				}else {
//					bi.setRGB(i, j, 1, 1, rgbs.get(p), 0, 0);
					rgb = rgbs.get(p);
					graphics.setColor(new Color(rgb[0],rgb[1],rgb[2]));
					graphics.drawRect(i, j, 1, 1);
				}
				// if(!rgbs.contains(c)) {
				// rgbs.add(rgb);
				// }
				// System.out.println("i=" + i + ",j=" + j + ":(" + rgb[0] + "," + rgb[1] + ","
				// + rgb[2] + ")");
			}
		}
		bi.setData(raster);
		for (int i = 0; i < rgbs.size(); i++) {
			int[] rgb = rgbs.get(i);
			String c = rgb[0] + "," + rgb[1] + "," + rgb[2];
			System.out.println(c);
		}
		System.out.println(rgbs.size());
		ImageIO.write(bi2,"png",new FileOutputStream("E:\\运动分析资料\\simple.png"));//保存图片 JPEG表示保存格式
//		saveImage(bi,"E:\\运动分析资料\\simple.png");
	}

	public static Boolean saveImage(BufferedImage productImage, String path) {
		try {
			File outputFile = new File(path);
			if (!outputFile.exists()) {
				outputFile.getParentFile().mkdirs();
				Boolean isSuccess = outputFile.createNewFile();
				if (!isSuccess) {
					return false;
				}
			}

			Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("png");
			ImageWriter writer = iter.next();
			ImageWriteParam iwp = writer.getDefaultWriteParam();
//			iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//			iwp.setCompressionQuality(quality);

			writer.setOutput(ImageIO.createImageOutputStream(outputFile));
			writer.write(null, new IIOImage(productImage, null, null), iwp);
			writer.dispose();

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
