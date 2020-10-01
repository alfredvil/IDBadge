package co.com.model;

import java.util.Observable;

import co.com.spreadsheet.ReaderHelper;

public class BadgeTemplateModel extends Observable {

	private String templateFile;
	private String csvFile;
	private String csvFileDelimiter;
	private String csvFileEncoding;
	private String inputFolder;
	private String outputFolder;
	private String roleColumnName;
	private String nameColumnName;
	private String idColumnName;
	private String typeIDColumnName;
	private String rhColumnName;
	private String imageColumnName;

	public BadgeTemplateModel() {
		csvFileDelimiter = ",";
		csvFileEncoding = "UTF-8";
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
		String encoding = ReaderHelper.guessCharset(csvFile);
		if( encoding != null ) {
			setCsvFileEncoding(encoding);
		}
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

	public void setCsvFileEncoding(String csvFileEncoding) {
		synchronized (this) {
			this.csvFileEncoding = csvFileEncoding;
		}
		setChanged();
		notifyObservers();
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

	public boolean isModelComplete() {
		return isFieldValid(templateFile) && isFieldValid(csvFile) && isFieldValid(csvFileDelimiter)
				&& isFieldValid(csvFileEncoding) && isFieldValid(inputFolder) && isFieldValid(outputFolder);
	}

	private boolean isFieldValid(String field) {
		return field != null && !field.isEmpty();
	}
}
