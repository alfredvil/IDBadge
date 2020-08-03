package co.com.model;

import java.util.Observable;

public class BadgeImageModel extends Observable {

	private String templateFile;
	int posXImgTmpl;
	int posYImgTmpl;
	int widthImgTmpl;
	int heightImgTmpl;

	public BadgeImageModel() {
	}

	public BadgeImageModel(String templateFile, int posXImgTmpl, int posYImgTmpl, int widthImgTmpl, int heightImgTmpl) {
		super();
		this.templateFile = templateFile;
		this.posXImgTmpl = posXImgTmpl;
		this.posYImgTmpl = posYImgTmpl;
		this.widthImgTmpl = widthImgTmpl;
		this.heightImgTmpl = heightImgTmpl;
	}

	public synchronized String getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(String templateFile) {
		synchronized (this) {
			this.templateFile = templateFile;
		}
		setChanged();
		notifyObservers();
	}

	public synchronized int getPosXImgTmpl() {
		return posXImgTmpl;
	}

	public void setPosXImgTmpl(int posXImgTmpl) {
		synchronized (this) {
			this.posXImgTmpl = posXImgTmpl;
		}
		setChanged();
		notifyObservers();
	}

	public synchronized int getPosYImgTmpl() {
		return posYImgTmpl;
	}

	public void setPosYImgTmpl(int posYImgTmpl) {
		synchronized (this) {
			this.posYImgTmpl = posYImgTmpl;
		}
		setChanged();
		notifyObservers();
	}

	public synchronized int getWidthImgTmpl() {
		return widthImgTmpl;
	}

	public void setWidthImgTmpl(int widthImgTmpl) {
		synchronized (this) {
			this.widthImgTmpl = widthImgTmpl;
		}
		setChanged();
		notifyObservers();
	}

	public int getHeightImgTmpl() {
		return heightImgTmpl;
	}

	public void setHeightImgTmpl(int heightImgTmpl) {
		synchronized (this) {
			this.heightImgTmpl = heightImgTmpl;
		}
		setChanged();
		notifyObservers();
	}
	
	public void setPositionImageTemplate(int posX, int posY) {
		synchronized (this) {
			this.posXImgTmpl = posX;
			this.posYImgTmpl = posY;
		}
		setChanged();
		notifyObservers();
	}
	
	public void setSizeImageTemplate(int width, int height) {
		synchronized (this) {
			this.widthImgTmpl = width;
			this.heightImgTmpl = height;
		}
		setChanged();
		notifyObservers();
	}
}
