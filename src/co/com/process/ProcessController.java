package co.com.process;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import co.com.image.ImageHelper;
import co.com.model.BadgeImageModel;
import co.com.model.BadgeResultModel;
import co.com.model.BadgeTemplateModel;
import co.com.model.BadgeTextModel;
import co.com.spreadsheet.ReaderHelper;

public class ProcessController extends Thread {
	private static ResourceBundle BUNDLE = ResourceBundle.getBundle("resources.messages", Locale.getDefault());

	private BadgeTemplateModel dataModel;
	private BadgeImageModel imageModel;
	private BadgeTextModel textModels[];
	private BadgeResultModel resultModel;
	private ReaderHelper readerHelper;

	public ProcessController(BadgeTemplateModel dataModel, BadgeImageModel imageModel, BadgeTextModel textModels[], BadgeResultModel resultModel) {
		super();
		this.readerHelper = new ReaderHelper();
		this.dataModel = dataModel;
		this.imageModel = imageModel;
		this.textModels = textModels;
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
						imageFrom = ImageHelper.fitToSizeImage(imageFrom, imageModel.getWidthImgTmpl(),imageModel.getHeightImgTmpl());
						BufferedImage imageTo = ImageHelper.deepCopy(templateBufImage);
						// Pasting record image into the template
						ImageHelper.copyImageTo(imageFrom, imageTo, imageModel.getPosXImgTmpl(),
								imageModel.getPosYImgTmpl());

						// Printing texts
						BadgeTextModel cargoTM = new BadgeTextModel(role, this.textModels[0].getPosY(), this.textModels[0].getFont(), this.textModels[0].getColor());
						BadgeTextModel nameTM = new BadgeTextModel(name, this.textModels[1].getPosY(), this.textModels[1].getFont(), this.textModels[1].getColor());
						String idAndRH = typeID + " " + id.replaceAll("\"", "") + "    RH " + rh;
						BadgeTextModel idAndRHTM = new BadgeTextModel(idAndRH, this.textModels[2].getPosY(), this.textModels[2].getFont(), this.textModels[2].getColor());
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
