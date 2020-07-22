package co.com.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.nio.charset.Charset;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JSplitPane;

public class BadgeWindow implements Observer {

	private JFrame frame;
	private JTextField templateImg;
	private JTextField folderImgs;
	private JTextField csvFile;
	private JComboBox<?> encoding;
	private JTextField folderResult;
	private JSplitPane panelDesign;
	JPanel panelPositions;
	JPanelImage panelImg;
	private JTextField posXImgTmpl;
	private JTextField posYImgTmpl;
	private JTextField fotoWidth;
	private JTextField fotoHeight;
	private JTextField posYCargo;
	private JTextField posYNombre;
	private JTextField posYCedRH;
	private int width = 650;
	private int height = 800;
	private BufferedImage sourceImg;
	private BadgeTemplateModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BadgeWindow window = new BadgeWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BadgeWindow() {
		model = new BadgeTemplateModel();
		this.model.addObserver(this);
		initialize();
	}

	@Override
	public void update(Observable o, Object arg) {
		this.posXImgTmpl.setText(""+model.getPosXImgTmpl());
		this.posYImgTmpl.setText(""+model.getPosYImgTmpl());
		this.fotoWidth.setText(""+model.getWidthImgTmpl());
		this.fotoHeight.setText(""+model.getHeightImgTmpl());
		this.posYCargo.setText(""+model.getPosYCargoTmpl());
		this.posYNombre.setText(""+model.getPosYNombreTmpl());
		this.posYCedRH.setText(""+model.getPosYCedRHTmpl());
		System.out.println("Observer1: "+o);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, width, height);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel panelConfig = new JPanel();
		tabbedPane.addTab("Configuración", null, panelConfig, null);
		panelConfig.setLayout(null);

		/* Entrada de datos */
		JPanel panelEntrada = new JPanel();
		panelEntrada.setBorder(
				new TitledBorder(null, "Datos de Entrada", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelEntrada.setBounds(5, 0, 625, 150);
		panelEntrada.setLayout(null);
		panelConfig.add(panelEntrada);

		JLabel lblTemplateImg = new JLabel("Img Template:");
		lblTemplateImg.setHorizontalAlignment(SwingConstants.LEFT);
		lblTemplateImg.setBounds(12, 23, 163, 17);
		panelEntrada.add(lblTemplateImg);

		templateImg = new JTextField();
		templateImg.setBounds(146, 23, 330, 19);
		templateImg.setColumns(10);
		templateImg.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				loadImage(new File(templateImg.getText()));
			}
		});
		panelEntrada.add(templateImg);

		JButton btnSelecTempl = new JButton("Seleccionar");
		btnSelecTempl.setFont(new Font("Dialog", Font.PLAIN, 10));
		btnSelecTempl.setBounds(488, 23, 89, 17);
		btnSelecTempl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectImgButtonActionPerformed(e);
			}
		});
		panelEntrada.add(btnSelecTempl);

		JLabel lblFolderImgs = new JLabel("Directorio Imgs:");
		lblFolderImgs.setHorizontalAlignment(SwingConstants.LEFT);
		lblFolderImgs.setBounds(12, 52, 134, 17);
		panelEntrada.add(lblFolderImgs);

		folderImgs = new JTextField();
		folderImgs.setColumns(10);
		folderImgs.setBounds(146, 52, 330, 19);
		panelEntrada.add(folderImgs);

		JButton btnSelecFoldImgs = new JButton("Seleccionar");
		btnSelecFoldImgs.setFont(new Font("Dialog", Font.PLAIN, 10));
		btnSelecFoldImgs.setBounds(488, 52, 89, 17);
		btnSelecFoldImgs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectFolderButtonActionPerformed(e);
			}
		});
		panelEntrada.add(btnSelecFoldImgs);

		JLabel lblCsvFile = new JLabel("Archivo CSV:");
		lblCsvFile.setHorizontalAlignment(SwingConstants.LEFT);
		lblCsvFile.setBounds(12, 81, 134, 17);
		panelEntrada.add(lblCsvFile);

		csvFile = new JTextField();
		csvFile.setColumns(10);
		csvFile.setBounds(146, 81, 330, 19);
		panelEntrada.add(csvFile);

		JButton btnSelecCSVFile = new JButton("Seleccionar");
		btnSelecCSVFile.setFont(new Font("Dialog", Font.PLAIN, 10));
		btnSelecCSVFile.setBounds(488, 81, 89, 17);
		btnSelecCSVFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectCSVButtonActionPerformed(e);
			}
		});
		panelEntrada.add(btnSelecCSVFile);

		JLabel lblEncoding = new JLabel("CSV Encoding:");
		lblEncoding.setHorizontalAlignment(SwingConstants.LEFT);
		lblEncoding.setBounds(12, 110, 134, 17);
		panelEntrada.add(lblEncoding);

		encoding = new JComboBox<Object>(Charset.availableCharsets().keySet().toArray());
		encoding.setBounds(146, 110, 182, 24);

		panelEntrada.add(encoding);

		/* Entrada de datos */

		/* Salida de datos */
		JPanel panelSalida = new JPanel();
		panelSalida.setBorder(
				new TitledBorder(null, "Datos de Resultados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelSalida.setBounds(5, 160, 625, 120);
		panelSalida.setLayout(null);
		panelConfig.add(panelSalida);

		JLabel lblDirSalida = new JLabel("Dir. de Salida");
		lblDirSalida.setHorizontalAlignment(SwingConstants.LEFT);
		lblDirSalida.setBounds(12, 25, 134, 17);
		panelSalida.add(lblDirSalida);

		folderResult = new JTextField();
		folderResult.setColumns(10);
		folderResult.setBounds(146, 25, 330, 19);
		panelSalida.add(folderResult);

		JButton btnSelecFoldResult = new JButton("Seleccionar");
		btnSelecFoldResult.setFont(new Font("Dialog", Font.PLAIN, 10));
		btnSelecFoldResult.setBounds(488, 25, 89, 17);
		btnSelecFoldResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectFolderResultButtonActionPerformed(e);
			}
		});
		panelSalida.add(btnSelecFoldResult);
		/* Entrada de Salida */

		/********* Diseño *********/
		panelPositions = new JPanel();
		panelPositions.setSize(width, height);
		panelPositions.setLayout(null);
		panelImg = new JPanelImage(model);
		JScrollPane scrollPane = new JScrollPane(panelImg,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setAutoscrolls(true);
		scrollPane.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				printImage();
			}
		});

		panelDesign = new JSplitPane(SwingConstants.VERTICAL, panelPositions, scrollPane);
		panelDesign.setOrientation(SwingConstants.VERTICAL);
		panelDesign.setResizeWeight(0.3);
		panelDesign.setContinuousLayout(true);
		tabbedPane.addTab("Diseño", null, panelDesign, null);

		/* Datos de la foto */
		int yMovPos = 0;
		int ancho = 150;

		JPanel panelFoto = new JPanel();
		panelFoto
				.setBorder(new TitledBorder(null, "Datos de Foto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelFoto.setBounds(5, yMovPos, 180, ancho);
		panelFoto.setLayout(null);
		panelPositions.add(panelFoto);

		JLabel lblFotoPosX = new JLabel("Posición X:");
		lblFotoPosX.setHorizontalAlignment(SwingConstants.LEFT);
		lblFotoPosX.setBounds(12, 25, 100, 17);
		panelFoto.add(lblFotoPosX);
		posXImgTmpl = new JTextField();
		posXImgTmpl.setColumns(10);
		posXImgTmpl.setBounds(106, 25, 50, 19);
		posXImgTmpl.getDocument().addDocumentListener(new BindingListener(model, "posXImgTmpl"));
		panelFoto.add(posXImgTmpl);

		JLabel lblFotoPosY = new JLabel("Posición Y:");
		lblFotoPosY.setHorizontalAlignment(SwingConstants.LEFT);
		lblFotoPosY.setBounds(12, 54, 100, 17);
		panelFoto.add(lblFotoPosY);
		posYImgTmpl = new JTextField();
		posYImgTmpl.setColumns(10);
		posYImgTmpl.setBounds(106, 54, 50, 19);
		posYImgTmpl.getDocument().addDocumentListener(new BindingListener(model, "posYImgTmpl"));
		panelFoto.add(posYImgTmpl);

		JLabel lblFotoAncho = new JLabel("Ancho:");
		lblFotoAncho.setHorizontalAlignment(SwingConstants.LEFT);
		lblFotoAncho.setBounds(12, 83, 100, 17);
		panelFoto.add(lblFotoAncho);
		fotoWidth = new JTextField();
		fotoWidth.setColumns(10);
		fotoWidth.setBounds(106, 83, 50, 19);
		fotoWidth.getDocument().addDocumentListener(new BindingListener(model, "widthImgTmpl"));
		panelFoto.add(fotoWidth);

		JLabel lblFotoAlto = new JLabel("Alto:");
		lblFotoAlto.setHorizontalAlignment(SwingConstants.LEFT);
		lblFotoAlto.setBounds(12, 112, 100, 17);
		panelFoto.add(lblFotoAlto);
		fotoHeight = new JTextField();
		fotoHeight.setColumns(10);
		fotoHeight.setBounds(106, 112, 50, 19);
		fotoHeight.getDocument().addDocumentListener(new BindingListener(model, "heightImgTmpl"));
		panelFoto.add(fotoHeight);

		yMovPos += ancho + 10;
		ancho = 60;
		JPanel panelCargo = new JPanel();
		panelCargo.setBorder(
				new TitledBorder(null, "Datos de Cargo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCargo.setBounds(5, yMovPos, 180, ancho);
		panelCargo.setLayout(null);
		panelPositions.add(panelCargo);

		JLabel lblPosyCargo = new JLabel("Posición Y:");
		lblPosyCargo.setHorizontalAlignment(SwingConstants.LEFT);
		lblPosyCargo.setBounds(12, 25, 100, 17);
		panelCargo.add(lblPosyCargo);
		posYCargo = new JTextField();
		posYCargo.setColumns(10);
		posYCargo.setBounds(106, 25, 50, 19);
		posYCargo.getDocument().addDocumentListener(new BindingListener(model, "posYCargoTmpl"));
		panelCargo.add(posYCargo);

		yMovPos += ancho + 10;
		JPanel panelNombre = new JPanel();
		panelNombre.setBorder(
				new TitledBorder(null, "Datos de Nombre", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelNombre.setBounds(5, yMovPos, 180, ancho);
		panelNombre.setLayout(null);
		panelPositions.add(panelNombre);

		JLabel lblPosyNombre = new JLabel("Posición Y:");
		lblPosyNombre.setHorizontalAlignment(SwingConstants.LEFT);
		lblPosyNombre.setBounds(12, 25, 100, 17);
		panelNombre.add(lblPosyNombre);
		posYNombre = new JTextField();
		posYNombre.setColumns(10);
		posYNombre.setBounds(106, 25, 50, 19);
		posYNombre.getDocument().addDocumentListener(new BindingListener(model, "posYNombreTmpl"));
		panelNombre.add(posYNombre);

		yMovPos += ancho + 10;
		JPanel panelCedRH = new JPanel();
		panelCedRH.setBorder(
				new TitledBorder(null, "Datos de Cédula y RH", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCedRH.setBounds(5, yMovPos, 180, ancho);
		panelCedRH.setLayout(null);
		panelPositions.add(panelCedRH);

		JLabel lblPosyCedRH = new JLabel("Posición Y:");
		lblPosyCedRH.setHorizontalAlignment(SwingConstants.LEFT);
		lblPosyCedRH.setBounds(12, 25, 100, 17);
		panelCedRH.add(lblPosyCedRH);
		posYCedRH = new JTextField();
		posYCedRH.setColumns(10);
		posYCedRH.setBounds(106, 25, 50, 19);
		posYCedRH.getDocument().addDocumentListener(new BindingListener(model, "posYCedRHTmpl"));
		panelCedRH.add(posYCedRH);
	}

	protected void selectImgButtonActionPerformed(ActionEvent e) {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images(jpg, jpeg, png)", "jpg", "jpeg", "png");
		File reference = openFileChooser(templateImg, JFileChooser.FILES_ONLY, filter);

		loadImage(reference);
	}

	protected void selectFolderButtonActionPerformed(ActionEvent e) {
		openFileChooser(folderImgs, JFileChooser.DIRECTORIES_ONLY, null);
	}

	protected void selectCSVButtonActionPerformed(ActionEvent e) {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV", "csv");
		openFileChooser(csvFile, JFileChooser.FILES_ONLY, filter);
	}

	protected void selectFolderResultButtonActionPerformed(ActionEvent e) {
		openFileChooser(folderResult, JFileChooser.DIRECTORIES_ONLY, null);
	}

	protected File openFileChooser(JTextField field, int optionChooser, FileNameExtensionFilter filter) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(filter);
		chooser.setFileSelectionMode(optionChooser);
		chooser.setAcceptAllFileFilterUsed(false);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			field.setText(chooser.getSelectedFile().getPath());
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
			return chooser.getSelectedFile();
		}
		return null;
	}

	private void loadImage(File reference) {
		if (reference == null || !reference.exists() || !reference.isFile()) {
			return;
		}
		try {
			sourceImg = ImageIO.read(reference);
			printImage();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void printImage() {
		if (sourceImg != null) {
			panelImg.setImage(sourceImg);
			panelImg.setSize(sourceImg.getWidth(), sourceImg.getHeight());
		}
	}
}
