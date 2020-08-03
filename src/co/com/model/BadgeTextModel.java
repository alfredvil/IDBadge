package co.com.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Observable;

public class BadgeTextModel extends Observable {

	private String text;
	private int posY;
	private Font font;
	private Color color;
	private Rectangle2D rectangle;

	public BadgeTextModel() {
		Font fontMuseoSans;
		try {
			fontMuseoSans = Font.createFont(Font.TRUETYPE_FONT,
					BadgeTextModel.class.getClassLoader().getResourceAsStream("resources/fonts/MuseoSans_700.otf"));
			this.font = fontMuseoSans.deriveFont(28f);
		} catch (Exception e) {
		}
		this.color = new Color(104, 57, 114);
	}

	public BadgeTextModel(String text, int posY, Font font, Color color) {
		super();
		this.text = text;
		this.posY = posY;
		this.font = font;
		this.color = color;
		refreshRectangle();
	}

	public synchronized String getText() {
		return text;
	}

	public void setText(String text) {
		synchronized (this) {
			this.text = text;
			refreshRectangle();
		}
		setChanged();
		notifyObservers();
	}

	public synchronized int getTextWidth() {
		if(rectangle==null) {
			return -1;
		}
		return (int) rectangle.getWidth();
	}

	public synchronized int getTextHeight() {
		if(rectangle==null) {
			return -1;
		}
		return (int) rectangle.getHeight();
	}

	public synchronized int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		synchronized (this) {
			this.posY = posY;
		}
		setChanged();
		notifyObservers();
	}

	public synchronized Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		synchronized (this) {
			this.font = font;
			refreshRectangle();
		}
		setChanged();
		notifyObservers();
	}

	public synchronized Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		synchronized (this) {
			this.color = color;
		}
		setChanged();
		notifyObservers();
	}

	public synchronized String getFontDescription() {
		if( this.font != null ) {
			return this.font.getFamily() + " - " + this.font.getSize();
		}
		return "";
	}
	
	private void refreshRectangle() {
		if (font != null && text!=null && !text.isEmpty()) {
			FontRenderContext fontRenderContext = new FontRenderContext(new AffineTransform(), true, false);
			rectangle = font.getStringBounds(text, fontRenderContext);
		}
	}
}