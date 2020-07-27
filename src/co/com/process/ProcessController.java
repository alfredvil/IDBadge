package co.com.process;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import co.com.image.ImageHelper;
import co.com.model.BadgeResultModel;
import co.com.model.BadgeTemplateModel;
import co.com.model.BadgeTextModel;
import co.com.spreadsheet.ReaderHelper;

public class ProcessController extends Thread {
	private static ResourceBundle BUNDLE = ResourceBundle.getBundle("resources.messages", Locale.getDefault());

	private BadgeTemplateModel dataModel;
	private BadgeResultModel resultModel;
	private ReaderHelper readerHelper;

	public ProcessController(BadgeTemplateModel dataModel, BadgeResultModel resultModel) {
		super();
		this.readerHelper = new ReaderHelper();
		this.dataModel = dataModel;
		this.resultModel = resultModel;
	}

	public void run() {
		System.out.println("ProcessController running");

		if (this.dataModel.isModelComplete()) {
			try {
				this.resultModel.init();
				this.readerHelper.initialize(this.dataModel.getCsvFile(), true, this.dataModel.getCsvFileDelimiter(),
						this.dataModel.getCsvFileEncoding());
				this.resultModel.setTotalRecords(this.readerHelper.getNumberRows());
				int validRecords = 0;
				int errorRecords = 0;
				this.resultModel.setValidRecords(validRecords);
				this.resultModel.setErrorRecords(errorRecords);
				// Opening the template file
				File templateFile = new File(this.dataModel.getTemplateFile());
				BufferedImage templateBufImage = ImageHelper.loadImage(templateFile);
				Font fntRole = Font.createFont(Font.TRUETYPE_FONT,getClass().getClassLoader().getResourceAsStream("resources/fonts/MuseoSans_700.otf"));
				fntRole = fntRole.deriveFont(28f);
				Font fntName = Font.createFont(Font.TRUETYPE_FONT,getClass().getClassLoader().getResourceAsStream("resources/fonts/MuseoSansCondensed-700.ttf"));
				fntName = fntName.deriveFont(44f);
				Color color = new Color(104, 57, 114);

				for (int row = 0; row < this.readerHelper.getNumberRows(); row++) {
					String name = this.readerHelper.getField(row, this.dataModel.getNameColumnName());
					String id = this.readerHelper.getField(row, this.dataModel.getIdColumnName());
					String typeID = this.readerHelper.getField(row, this.dataModel.getTypeIDColumnName());
					String role = this.readerHelper.getField(row, this.dataModel.getRoleColumnName());
					String rh = this.readerHelper.getField(row, this.dataModel.getRhColumnName());
					String img = this.readerHelper.getField(row, this.dataModel.getImageColumnName());
					try {
						// Opening record image
						File imageFile = new File(this.dataModel.getInputFolder() + File.separatorChar + img);
						BufferedImage imageFrom = ImageHelper.loadImage(imageFile);
						// Fitting record image to the defined size
						imageFrom = ImageHelper.fitToSizeImage(imageFrom, dataModel.getWidthImgTmpl(),dataModel.getHeightImgTmpl());
						BufferedImage imageTo = ImageHelper.deepCopy(templateBufImage);
						// Pasting record image into the template
						ImageHelper.copyImageTo(imageFrom, imageTo, dataModel.getPosXImgTmpl(),
								dataModel.getPosYImgTmpl());

						// Printing texts
						BadgeTextModel nameTM = new BadgeTextModel(name, this.dataModel.getPosYNameTmpl(), fntName, color);
						BadgeTextModel cargoTM = new BadgeTextModel(role, this.dataModel.getPosYRoleTmpl(), fntRole, color);
						String idAndRH = typeID + " " + id + "		RH " + rh;
						BadgeTextModel idAndRHTM = new BadgeTextModel(idAndRH, this.dataModel.getPosYIDAndRHTmpl(), fntName, color);
						ImageHelper.printStringArrayTo(imageTo, new BadgeTextModel[] { nameTM, cargoTM, idAndRHTM });

						// Saving image in the output folder
						String fileOutputName = imageFile.getName().substring(0, imageFile.getName().lastIndexOf("."));
						fileOutputName = dataModel.getOutputFolder() + File.separatorChar + fileOutputName + ".png";
						File outputFile = new File(fileOutputName);
						// Writing to file taking type and path as
						ImageIO.write(imageTo, "png", outputFile);

						// Printing to the log textArea
						String message = MessageFormat.format(BUNDLE.getString("valid_log"), img, name, role);
						this.resultModel.addToLog(message);
						this.resultModel.setValidRecords(++validRecords);
					} catch (Exception e) {
						e.printStackTrace();
						// Printing to the log textArea
						String message = MessageFormat.format(BUNDLE.getString("error_log"), img, name, role);
						this.resultModel.addToLog(message);
						this.resultModel.setErrorRecords(++errorRecords);
					}
				}
				this.resultModel.setTotalRecords(this.readerHelper.getNumberRows());
			} catch (Exception e) {
				e.printStackTrace();
				String message = MessageFormat.format(BUNDLE.getString("error_processing_log"), this.dataModel.getCsvFile());
				this.resultModel.addToLog(message);
			}
		}
		System.out.println("ProcessController finished");
	}
}
