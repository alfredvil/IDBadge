package co.com.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class BadgeTextModel {

	String text;
	int posY;
	Font font;
	Color color;

	public BadgeTextModel() {
	}

	public BadgeTextModel(String text, int posY, Font font, Color color) {
		super();
		this.text = text;
		this.posY = posY;
		this.font = font;
		this.color = color;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getTextWidth() {
		if (font == null || text == null || text.isEmpty()) {
			return 0;
		}

		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext fontRenderContext = new FontRenderContext(affinetransform, true, true);
		Rectangle2D rectangle = font.getStringBounds(text, fontRenderContext);

		return (int) rectangle.getWidth();
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
