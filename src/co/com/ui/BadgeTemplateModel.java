package co.com.ui;

import java.util.Observable;

public class BadgeTemplateModel extends Observable {

	String templateFile;
	String csvFile;
	String csvFileDelimiter;
	String csvFileEncoding;
	String inputFolder;
	String outputFolder;
	int posXImgTmpl;
	int posYImgTmpl;
	int widthImgTmpl;
	int heightImgTmpl;
	int posYCargoTmpl;
	int posYNombreTmpl;
	int posYCedRHTmpl;

	public BadgeTemplateModel() {
		csvFileDelimiter = ",";
		csvFileEncoding = "UTF-8";
	}

	public synchronized String getTemplateFile() {
		return templateFile;
	}

	public synchronized void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

	public synchronized String getCsvFile() {
		return csvFile;
	}

	public synchronized void setCsvFile(String csvFile) {
		this.csvFile = csvFile;
	}

	public synchronized String getCsvFileDelimiter() {
		return csvFileDelimiter;
	}

	public synchronized void setCsvFileDelimiter(String csvFileDelimiter) {
		this.csvFileDelimiter = csvFileDelimiter;
	}

	public synchronized String getCsvFileEncoding() {
		return csvFileEncoding;
	}

	public synchronized void setCsvFileEncoding(String csvFileEncoding) {
		this.csvFileEncoding = csvFileEncoding;
	}

	public synchronized String getInputFolder() {
		return inputFolder;
	}

	public synchronized void setInputFolder(String inputFolder) {
		this.inputFolder = inputFolder;
	}

	public synchronized String getOutputFolder() {
		return outputFolder;
	}

	public synchronized void setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
	}

	public synchronized int getPosXImgTmpl() {
		return posXImgTmpl;
	}

	public void setPosXImgTmpl(Integer posXImgTmpl) {
		synchronized (this) {
			this.posXImgTmpl = posXImgTmpl;
		}
		setChanged();
		notifyObservers();
	}

	public synchronized int getPosYImgTmpl() {
		return posYImgTmpl;
	}

	public synchronized void setPosYImgTmpl(Integer posYImgTmpl) {
		synchronized (this) {
			this.posYImgTmpl = posYImgTmpl;
		}
		setChanged();
		notifyObservers();
	}

	public synchronized int getWidthImgTmpl() {
		return widthImgTmpl;
	}

	public synchronized void setWidthImgTmpl(Integer widthImgTmpl) {
		synchronized (this) {
			this.widthImgTmpl = widthImgTmpl;
		}
		setChanged();
		notifyObservers();
	}

	public synchronized int getHeightImgTmpl() {
		return heightImgTmpl;
	}

	public synchronized void setHeightImgTmpl(Integer heightImgTmpl) {
		synchronized (this) {
			this.heightImgTmpl = heightImgTmpl;
		}
		setChanged();
		notifyObservers();
	}

	public synchronized int getPosYCargoTmpl() {
		return posYCargoTmpl;
	}

	public synchronized void setPosYCargoTmpl(Integer posYCargoTmpl) {
		synchronized (this) {
			this.posYCargoTmpl = posYCargoTmpl;
		}
		setChanged();
		notifyObservers();
	}

	public synchronized int getPosYNombreTmpl() {
		return posYNombreTmpl;
	}

	public void setPosYNombreTmpl(Integer posYNombreTmpl) {
		synchronized (this) {
			this.posYNombreTmpl = posYNombreTmpl;
		}
		setChanged();
		notifyObservers();
	}

	public synchronized int getPosYCedRHTmpl() {
		return posYCedRHTmpl;
	}

	public void setPosYCedRHTmpl(Integer posYCedRHTmpl) {
		synchronized (this) {
			this.posYCedRHTmpl = posYCedRHTmpl;
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
	
	public void setSizeImageTemplate(int posX2, int posY2) {
		synchronized (this) {
			this.widthImgTmpl = Math.abs(this.posXImgTmpl - posX2);
			this.heightImgTmpl = Math.abs(this.posYImgTmpl - posY2);
		}
		setChanged();
		notifyObservers();
	}
}
