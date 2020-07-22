import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;

import co.com.spreadsheet.ReaderHelper;

public class main {

	public static void main(String[] args) {
		
		System.out.println(Charset.availableCharsets().keySet());
		
		/*
		ReaderHelper readerCSV = new ReaderHelper("/home/lucho/Desktop/Bibi/CarneFilarmonica/lista.csv", true, ",","x-MacRoman");
		for(int row=0;row<readerCSV.getNumberRows();row++) {
			System.out.println(readerCSV.getField(row, "RH"));
		}
		
		int width = 963; // width of the image
		int height = 640; // height of the image

		// For storing image in RAM
		BufferedImage image = null;

		// READ IMAGE
		try {
			File input_file = new File("/home/lucho/Desktop/Bibi/CarneFilarmonica/ImgIniciales/2.jpeg"); // image file path

			/*
			 * create an object of BufferedImage type and pass as parameter the width,
			 * height and image int type.TYPE_INT_ARGB means that we are representing the
			 * Alpha, Red, Green and Blue component of the image pixel using 8 bit integer
			 * value.
			 */
		/*
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

			// Reading input file
			image = ImageIO.read(input_file);
			
			System.out.println("Reading complete.");
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}

		// WRITE IMAGE
		try {
			// Output file path
			File output_file = new File("/home/lucho/Desktop/Bibi/CarneFilarmonica/Test/2.jgp");

			// Writing to file taking type and path as
			ImageIO.write(image, "jpg", output_file);

			System.out.println("Writing complete.");
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}*/
	}

}
