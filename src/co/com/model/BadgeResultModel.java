package co.com.model;

import java.util.Observable;

public class BadgeResultModel extends Observable {

	int totalRecords;
	int validRecords;
	int errorRecords;
	StringBuilder log;

	public BadgeResultModel() {
		init();
	}
	
	public void init() {
		totalRecords = validRecords = errorRecords = 0;
		log = new StringBuilder();
	}

	public synchronized int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		synchronized (this) {
			this.totalRecords = totalRecords;
		}
		setChanged();
		notifyObservers();
	}

	public synchronized int getValidRecords() {
		return validRecords;
	}

	public void setValidRecords(int validRecords) {
		synchronized (this) {
			this.validRecords = validRecords;
		}
		setChanged();
		notifyObservers();
	}

	public synchronized int getErrorRecords() {
		return errorRecords;
	}

	public void setErrorRecords(int errorRecords) {
		synchronized (this) {
			this.errorRecords = errorRecords;
		}
		setChanged();
		notifyObservers();
	}

	public synchronized StringBuilder getLog() {
		return log;
	}

	public void setLog(StringBuilder log) {
		this.log = log;
	}

	public void addToLog(String text) {
		synchronized (this) {
			this.log.append(text+"\n");
		}
		setChanged();
		notifyObservers();
	}
}