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

public class JPanelImage extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9182926349993472897L;
	private BufferedImage image;
	private int posMouseX, posMouseY;
	private BadgeTemplateModel model;

	public JPanelImage(BadgeTemplateModel model) {
		posMouseX = 0;
		posMouseY = 0;
		this.model = model;
		this.model.addObserver(this);
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
		if (image != null) {
			Graphics2D g2 = (Graphics2D) g;
			g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
			if (posMouseX >= 0 && posMouseX <= image.getWidth() && posMouseY >= 0 || posMouseY <= image.getHeight()) {
				String position = "(" + posMouseX + "," + posMouseY + ")";
				g2.setColor(Color.RED);
				g2.drawString(position, posMouseX, posMouseY - 3);
			}
			int posX = model.getPosXImgTmpl();
			int posY = model.getPosYImgTmpl();
			if (posX >= 0 && posY >= 0) {
				g.drawRect(posX, posY, model.getWidthImgTmpl(), model.getHeightImgTmpl());
			}
		}
	}

	public void setImage(BufferedImage image) {
		this.image = image;
		repaint();
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Observer2: " + o);
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
			model.deleteObserver(source);
			model.setSizeImageTemplate(e.getX(), e.getY());
			model.addObserver(source);
			repaint();
		}
	}

	private class MouseHandler implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			posMouseX = e.getX();
			posMouseY = e.getY();
			JPanelImage source = (JPanelImage) e.getSource();
			model.deleteObserver(source);
			model.setPositionImageTemplate(e.getX(), e.getY());
			model.addObserver(source);
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
