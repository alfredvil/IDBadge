package co.com.spreadsheet;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReaderHelper {
	String pathCSV;
	boolean hasHeader = false;
	String delimiter;
	String encoding;
	List<String[]> fileLines;
	Map<String, Integer> header;

	public ReaderHelper() {
		
	}

	public ReaderHelper(String pathCSV) {
		initialize(pathCSV, false, ",", "UTF-8");
	}

	public ReaderHelper(String pathCSV, boolean hasHeader, String delimiter, String encoding) {
		initialize(pathCSV, hasHeader, delimiter, encoding);
	}

	public void initialize(String pathCSV, boolean hasHeader, String delimiter, String encoding) {
		this.pathCSV = pathCSV;
		this.hasHeader = hasHeader;
		this.delimiter = delimiter;
		this.encoding = encoding;
		readLines();
	}

	private void readLines() {
		Path path = Paths.get(this.pathCSV);
		if (Files.exists(path)) {
			try {
				this.fileLines = Files.lines(path, Charset.forName(this.encoding)).map(l -> l.split(this.delimiter))
						.collect(Collectors.toList());
			} catch (IOException e) {
				return;
			}

			if (this.hasHeader) {
				this.header = new HashMap<>();
				String headerArray[] = this.fileLines.get(0);
				for (int i = 0; i < headerArray.length; i++) {
					this.header.put(headerArray[i], i);
				}
				this.fileLines.remove(0);
			}
		}
	}

	public String getField(int row, String column) {
		int columnIdx = this.header.get(column);
		return getField(row, columnIdx);
	}

	public String getField(int row, int column) {
		String fieldValue = null;
		try {
			fieldValue = this.fileLines.get(row)[column];
		} catch (Exception e) {

		}
		return fieldValue;
	}

	public List<String[]> getFileLines() {
		return this.fileLines;
	}

	public int getNumberRows() {
		return this.fileLines.size();
	}
}
