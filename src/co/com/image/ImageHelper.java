package co.com.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;

import javax.imageio.ImageIO;

import co.com.model.BadgeTextModel;

public class ImageHelper {

	public static BufferedImage loadImage(File reference) {
		if (reference == null || !reference.exists() || !reference.isFile()) {
			return null;
		}
		try {
			return ImageIO.read(reference);
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static BufferedImage deepCopy(BufferedImage image) {
		ColorModel cm = image.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public static BufferedImage fitToSizeImage(BufferedImage image, int width, int height) {
		Image tmp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return resized;
	}

	public static void copyImageTo(BufferedImage imageFrom, BufferedImage imageTo, int posX, int posY) {
		int pixels[] = imageFrom.getRGB(0, 0, imageFrom.getWidth(), imageFrom.getHeight(), null, 0,
				imageFrom.getWidth());
		imageTo.setRGB(posX, posY, imageFrom.getWidth(), imageFrom.getHeight(), pixels, 0, imageFrom.getWidth());
	}

	public static void printStringArrayTo(BufferedImage imageTo, BadgeTextModel textModelArray[]) {
		Graphics2D g2d = (Graphics2D) imageTo.getGraphics();
		printStringArrayTo(g2d, textModelArray,imageTo.getWidth());
	}

	public static void printStringArrayTo(Graphics2D g2d, BadgeTextModel textModelArray[], int imageWidth) {
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		
		for(BadgeTextModel textModel:textModelArray) {
			AttributedString text = new AttributedString(textModel.getText());
			text.addAttribute(TextAttribute.FONT, textModel.getFont(), 0, textModel.getText().length());
			text.addAttribute(TextAttribute.FOREGROUND, textModel.getColor(), 0, textModel.getText().length());
			int size = (imageWidth-textModel.getTextWidth())/2;
			g2d.drawString(text.getIterator(), size, textModel.getPosY()+textModel.getTextHeight()-8);
		}
		g2d.dispose();
	}
}
