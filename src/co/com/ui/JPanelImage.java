package co.com.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import co.com.image.ImageHelper;
import co.com.model.BadgeImageModel;
import co.com.model.BadgeTextModel;

public class JPanelImage extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9182926349993472897L;
	private BufferedImage image;
	private int posMouseX, posMouseY;
	private BadgeImageModel imageModel;
	private BadgeTextModel textModels[];

	public JPanelImage(BadgeImageModel imageModel, BadgeTextModel textModels[]) {
		posMouseX = 0;
		posMouseY = 0;
		//Image Model
		this.imageModel = imageModel;
		this.imageModel.addObserver(this);
		//Text Models
		this.textModels = textModels;
		this.textModels[0].addObserver(this);//Role
		this.textModels[1].addObserver(this);//Name
		this.textModels[2].addObserver(this);//Id and RH
		
		addMouseMotionListener(new MouseMotionHandler());
		addMouseListener(new MouseHandler());
	}

	@Override
	public Dimension getPreferredSize() {
		if (this.image == null) {
			return new Dimension(0,0);
		}
		return new Dimension(this.image.getWidth(), this.image.getHeight());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if( image != null ) {
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
			if (posMouseX >= 0 && posMouseX <= image.getWidth() && posMouseY >= 0 || posMouseY <= image.getHeight()) {
				String position = "(" + posMouseX + "," + posMouseY + ")";
				g2.setColor(Color.RED);
				g2.drawString(position, posMouseX, posMouseY - 3);
			}
			int posX = imageModel.getPosXImgTmpl();
			int posY = imageModel.getPosYImgTmpl();
			if( posX >= 0 && posY >= 0 ) {
				g2.drawRect(posX, posY, imageModel.getWidthImgTmpl(), imageModel.getHeightImgTmpl());
			}
			
			ImageHelper.printStringArrayTo(g2, textModels, image.getWidth());
		}
	}

	public void setImage(BufferedImage image) {
		this.image = image;
		repaint();
	}

	/* (non-Javadoc)
	 * Repaint mouse position and image frame.
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

	private class MouseMotionHandler implements MouseMotionListener {
		@Override
		public void mouseMoved(MouseEvent e) {
			posMouseX = e.getX();
			posMouseY = e.getY();
			repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			posMouseX = e.getX();
			posMouseY = e.getY();
			JPanelImage source = (JPanelImage) e.getSource();
			imageModel.deleteObserver(source);
			imageModel.setSizeImageTemplate(e.getX(), e.getY());
			imageModel.addObserver(source);
			repaint();
		}
	}

	private class MouseHandler implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			posMouseX = e.getX();
			posMouseY = e.getY();
			JPanelImage source = (JPanelImage) e.getSource();
			imageModel.deleteObserver(source);
			imageModel.setPositionImageTemplate(e.getX(), e.getY());
			imageModel.addObserver(source);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			mouseClicked(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
			posMouseX = posMouseY = -1;
			repaint();
		}
	}
}
