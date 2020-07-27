package co.com.model;

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
	int posYRoleTmpl;
	int posYNameTmpl;
	int posYIDAndRHTmpl;
	String roleColumnName;
	String nameColumnName;
	String idColumnName;
	String typeIDColumnName;
	String rhColumnName;
	String imageColumnName;

	public BadgeTemplateModel() {
		csvFileDelimiter = ",";
		csvFileEncoding = "UTF-8";
		posXImgTmpl = posYImgTmpl = widthImgTmpl = heightImgTmpl = posYRoleTmpl = posYNameTmpl = posYIDAndRHTmpl = -1;
		roleColumnName="Cargo";
		nameColumnName="Nombre";
		idColumnName="Cedula";
		typeIDColumnName="TipoCC";
		rhColumnName="RH";
		imageColumnName="Foto";
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

	public synchronized int getPosYRoleTmpl() {
		return posYRoleTmpl;
	}

	public synchronized void setPosYRoleTmpl(Integer posYRoleTmpl) {
		synchronized (this) {
			this.posYRoleTmpl = posYRoleTmpl;
		}
		setChanged();
		notifyObservers();
	}

	public synchronized int getPosYNameTmpl() {
		return posYNameTmpl;
	}

	public void setPosYNameTmpl(Integer posYNameTmpl) {
		synchronized (this) {
			this.posYNameTmpl = posYNameTmpl;
		}
		setChanged();
		notifyObservers();
	}

	public synchronized int getPosYIDAndRHTmpl() {
		return posYIDAndRHTmpl;
	}

	public void setPosYIDAndRHTmpl(Integer posYIDAndRHTmpl) {
		synchronized (this) {
			this.posYIDAndRHTmpl = posYIDAndRHTmpl;
		}
		setChanged();
		notifyObservers();
	}

	public String getRoleColumnName() {
		return roleColumnName;
	}

	public void setRoleColumnName(String roleColumnName) {
		this.roleColumnName = roleColumnName;
	}

	public String getNameColumnName() {
		return nameColumnName;
	}

	public void setNameColumnName(String nameColumnName) {
		this.nameColumnName = nameColumnName;
	}

	public String getIdColumnName() {
		return idColumnName;
	}

	public void setIdColumnName(String typeIDColumnName) {
		this.idColumnName = typeIDColumnName;
	}

	public String getTypeIDColumnName() {
		return typeIDColumnName;
	}

	public void setTypeIDColumnName(String typeIDColumnName) {
		this.typeIDColumnName = typeIDColumnName;
	}

	public String getRhColumnName() {
		return rhColumnName;
	}

	public void setRhColumnName(String rhColumnName) {
		this.rhColumnName = rhColumnName;
	}

	public String getImageColumnName() {
		return imageColumnName;
	}

	public void setImageColumnName(String imageColumnName) {
		this.imageColumnName = imageColumnName;
	}

	public void setPosYRoleTmpl(int posYRoleTmpl) {
		this.posYRoleTmpl = posYRoleTmpl;
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

	public boolean isModelComplete() {
		return isFieldValid(templateFile) && isFieldValid(csvFile) && isFieldValid(csvFileDelimiter)
				&& isFieldValid(csvFileEncoding) && isFieldValid(inputFolder) && isFieldValid(outputFolder)
				&& posXImgTmpl >= 0 && posYImgTmpl >= 0 && widthImgTmpl >= 0 && heightImgTmpl >= 0 && posYRoleTmpl >= 0
				&& posYNameTmpl >= 0 && posYIDAndRHTmpl >= 0;
	}

	private boolean isFieldValid(String field) {
		return field != null && !field.isEmpty();
	}
}
