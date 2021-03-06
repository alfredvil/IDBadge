import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import org.drjekyll.fontchooser.FontDialog;

import co.com.image.ImageHelper;
import co.com.model.BadgeTextModel;
import co.com.spreadsheet.ReaderHelper;

public class main {

	public static void main(String[] args) throws Exception {
		
		FontDialog dialog = new FontDialog( );
		dialog.setTitle("Font Dialog Example");
		dialog.setModal(true);
		//null, , true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		if (!dialog.isCancelSelected()) {
		  System.out.printf("Selected font is: %s%n", dialog.getSelectedFont());
		}
		System.out.printf("FinalSelected font is: %s%n", dialog.getSelectedFont());
		Color color = JColorChooser.showDialog(null, "Choose a color", new Color(104, 57, 114));
		System.out.printf("Selected color is: %s%n", color);
				
		//System.out.println(Charset.availableCharsets().keySet());
		/*
		ReaderHelper readerCSV = new ReaderHelper("/home/lucho/Desktop/Bibi/CarneFilarmonica/lista.csv", true, ",","x-MacRoman");
		for(int row=0;row<readerCSV.getNumberRows();row++) {
			System.out.println(readerCSV.getField(row, "RH"));
		}
		
		int width = 963; // width of the image
		int height = 640; // height of the image
		
		// For storing image in RAM
		
		BufferedImage imageFrom = null;
		BufferedImage imageTo = null;
		BufferedImage imageFinal = null;

		// READ IMAGE
		try {
			File input_file_from = new File("/home/lucho/Desktop/Bibi/CarneFilarmonica/ImgIniciales/2.jpeg"); // image file path
			File input_file_to = new File("/home/lucho/Desktop/Bibi/CarneFilarmonica/template.png"); // image file path
			imageFrom = ImageHelper.loadImage(input_file_from);
			imageFrom = ImageHelper.fitToSizeImage(imageFrom, 263, 287);
			imageTo = ImageHelper.loadImage(input_file_to);
			imageTo = ImageHelper.deepCopy(imageTo);
			ImageHelper.copyImageTo(imageFrom, imageTo, 186, 209);
			
			
			Font font = Font.createFont(Font.TRUETYPE_FONT, main.class.getClassLoader().getResourceAsStream("resources/fonts/MuseoSans_700.otf"));  
			font = font.deriveFont(28f);
			BadgeTextModel textModel = new BadgeTextModel();
			textModel.setText("THIS IS A TEST");
			textModel.setFont(font);
			textModel.setPosY(595);
			textModel.setColor(new Color(104, 57, 114));
			ImageHelper.printStringArrayTo(imageTo, new BadgeTextModel[]{textModel});
			
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}

		// WRITE IMAGE
		try {
			// Output file path
			File output_file = new File("/home/lucho/Desktop/Bibi/CarneFilarmonica/TEMP/SALIDA.png");

			// Writing to file taking type and path as
			ImageIO.write(imageTo, "png", output_file);

			System.out.println("Writing complete.");
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
		*/
	}
}
