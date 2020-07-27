package co.com.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Font;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;

import co.com.image.ImageHelper;
import co.com.model.BadgeResultModel;
import co.com.model.BadgeTemplateModel;
import co.com.process.ProcessController;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JSplitPane;
import java.util.ResourceBundle;
import javax.swing.JProgressBar;

public class BadgeWindow implements Observer {
	private static ResourceBundle BUNDLE = ResourceBundle.getBundle("resources.messages", Locale.getDefault());

	private BufferedImage sourceImgage;
	private BadgeTemplateModel dataModel;
	private BadgeResultModel resultModel;
	private ProcessController processController;

	private JFrame frame;
	JTabbedPane tabbedPane;
	JPanel panelConfig;
	private JTextField templateImage;
	private JTextField folderImages;
	private JTextField csvFile;
	private JComboBox<?> encoding;
	private JTextField folderResult;
	private JSplitPane panelDesign;
	JPanel panelPositions;
	JPanelImage panelImage;
	private JTextField posXImgTmpl;
	private JTextField posYImgTmpl;
	private JTextField imageWidth;
	private JTextField imageHeight;
	private JTextField posYRole;
	private JTextField posYName;
	private JTextField posYIDAndRH;
	JPanel panelResults;
	private int width = 650;
	private int height = 800;
	private JLabel totalRecords;
	private JLabel validRecords;
	private JLabel errorRecords;
	private JTextArea textAreaLog;
	private JProgressBar progressBar; 
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
		this.dataModel = new BadgeTemplateModel();
		this.resultModel = new BadgeResultModel();
		this.dataModel.addObserver(this);
		this.resultModel.addObserver(this);
		this.processController = new ProcessController(dataModel, resultModel);
		initialize();
		loadProperties();
	}

	/*
	 * (non-Javadoc) Updates image fields. Updates result fields.
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof BadgeTemplateModel) {
			this.posXImgTmpl.setText("" + dataModel.getPosXImgTmpl());
			this.posYImgTmpl.setText("" + dataModel.getPosYImgTmpl());
			this.imageWidth.setText("" + dataModel.getWidthImgTmpl());
			this.imageHeight.setText("" + dataModel.getHeightImgTmpl());
			this.posYRole.setText("" + dataModel.getPosYRoleTmpl());
			this.posYName.setText("" + dataModel.getPosYNameTmpl());
			this.posYIDAndRH.setText("" + dataModel.getPosYIDAndRHTmpl());
		} else if (o instanceof BadgeResultModel) {
			this.totalRecords.setText("" + resultModel.getTotalRecords());
			this.errorRecords.setText("" + resultModel.getErrorRecords());
			this.validRecords.setText("" + resultModel.getValidRecords());
			this.progressBar.setMaximum(resultModel.getTotalRecords());
			int records=resultModel.getErrorRecords()+resultModel.getValidRecords();
			this.progressBar.setValue(records);
			if(records==resultModel.getTotalRecords()) {
				TitledBorder border = (TitledBorder)progressBar.getBorder();
				border.setTitle(BUNDLE.getString("complete_progress_bar"));
			}
			this.textAreaLog.setText(resultModel.getLog().toString());
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, width, height);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		/********* Config Panel *********/
		createConfigPanel();

		/********* Design *********/
		createDesignPanel();

		/********* Results *********/
		createResultsPanel();
	}

	/**
	 * 
	 */
	protected void createConfigPanel() {
		panelConfig = new JPanel();
		tabbedPane.addTab(BUNDLE.getString("config_panel_name"), null, panelConfig, null);
		panelConfig.setLayout(null);

		/* Entrada de datos */
		JPanel panelEntryData = new JPanel();
		panelEntryData.setBorder(new TitledBorder(null, BUNDLE.getString("config_data_entry_label"),
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelEntryData.setBounds(5, 0, 625, 150);
		panelEntryData.setLayout(null);
		panelConfig.add(panelEntryData);

		JLabel lblTemplateImg = new JLabel(BUNDLE.getString("config_data_entry_img_tmpl"));
		lblTemplateImg.setHorizontalAlignment(SwingConstants.LEFT);
		lblTemplateImg.setBounds(12, 23, 163, 17);
		panelEntryData.add(lblTemplateImg);

		templateImage = new JTextField();
		templateImage.setBounds(146, 23, 330, 19);
		templateImage.setColumns(10);
		templateImage.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				loadImage(new File(templateImage.getText()));
				dataModel.setTemplateFile(templateImage.getText());
			}
		});
		panelEntryData.add(templateImage);

		JButton btnSelecTempl = new JButton(BUNDLE.getString("select_btn"));
		btnSelecTempl.setFont(new Font("Dialog", Font.PLAIN, 10));
		btnSelecTempl.setBounds(488, 23, 89, 17);
		btnSelecTempl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectImgButtonActionPerformed(e);
				dataModel.setTemplateFile(templateImage.getText());
			}
		});
		panelEntryData.add(btnSelecTempl);

		JLabel lblFolderImgs = new JLabel(BUNDLE.getString("config_data_entry_dir_imgs"));
		lblFolderImgs.setHorizontalAlignment(SwingConstants.LEFT);
		lblFolderImgs.setBounds(12, 52, 134, 17);
		panelEntryData.add(lblFolderImgs);

		folderImages = new JTextField();
		folderImages.setColumns(10);
		folderImages.setBounds(146, 52, 330, 19);
		folderImages.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				dataModel.setInputFolder(folderImages.getText());
			}
		});
		panelEntryData.add(folderImages);

		JButton btnSelecFoldImgs = new JButton(BUNDLE.getString("select_btn"));
		btnSelecFoldImgs.setFont(new Font("Dialog", Font.PLAIN, 10));
		btnSelecFoldImgs.setBounds(488, 52, 89, 17);
		btnSelecFoldImgs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectFolderButtonActionPerformed(e);
				dataModel.setInputFolder(folderImages.getText());
			}
		});
		panelEntryData.add(btnSelecFoldImgs);

		JLabel lblCsvFile = new JLabel(BUNDLE.getString("config_data_entry_file_csv"));
		lblCsvFile.setHorizontalAlignment(SwingConstants.LEFT);
		lblCsvFile.setBounds(12, 81, 134, 17);
		panelEntryData.add(lblCsvFile);

		csvFile = new JTextField();
		csvFile.setColumns(10);
		csvFile.setBounds(146, 81, 330, 19);
		csvFile.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				dataModel.setCsvFile(csvFile.getText());
			}
		});
		panelEntryData.add(csvFile);

		JButton btnSelecCSVFile = new JButton(BUNDLE.getString("select_btn"));
		btnSelecCSVFile.setFont(new Font("Dialog", Font.PLAIN, 10));
		btnSelecCSVFile.setBounds(488, 81, 89, 17);
		btnSelecCSVFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectCSVButtonActionPerformed(e);
				dataModel.setCsvFile(csvFile.getText());
			}
		});
		panelEntryData.add(btnSelecCSVFile);

		JLabel lblEncoding = new JLabel(BUNDLE.getString("config_data_entry_file_csv_encoding"));
		lblEncoding.setHorizontalAlignment(SwingConstants.LEFT);
		lblEncoding.setBounds(12, 110, 134, 17);
		panelEntryData.add(lblEncoding);

		encoding = new JComboBox<Object>(Charset.availableCharsets().keySet().toArray());
		encoding.setBounds(146, 110, 182, 24);
		encoding.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				dataModel.setCsvFileEncoding(encoding.getSelectedItem().toString());
			}
		});

		panelEntryData.add(encoding);
		/* Entrada de datos */

		/* Salida de datos */
		JPanel panelSalida = new JPanel();
		panelSalida.setBorder(new TitledBorder(null, BUNDLE.getString("config_data_exit_label"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panelSalida.setBounds(5, 160, 625, 120);
		panelSalida.setLayout(null);
		panelConfig.add(panelSalida);

		JLabel lblDirSalida = new JLabel(BUNDLE.getString("config_data_exit_dir"));
		lblDirSalida.setHorizontalAlignment(SwingConstants.LEFT);
		lblDirSalida.setBounds(12, 25, 134, 17);
		panelSalida.add(lblDirSalida);

		folderResult = new JTextField();
		folderResult.setColumns(10);
		folderResult.setBounds(146, 25, 330, 19);
		folderResult.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				dataModel.setOutputFolder(folderResult.getText());
			}
		});
		panelSalida.add(folderResult);

		JButton btnSelecFoldResult = new JButton(BUNDLE.getString("select_btn"));
		btnSelecFoldResult.setFont(new Font("Dialog", Font.PLAIN, 10));
		btnSelecFoldResult.setBounds(488, 25, 89, 17);
		btnSelecFoldResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectFolderResultButtonActionPerformed(e);
				dataModel.setOutputFolder(folderResult.getText());
			}
		});
		panelSalida.add(btnSelecFoldResult);
		/* Entrada de Salida */
	}

	protected void createDesignPanel() {
		panelPositions = new JPanel();
		panelPositions.setSize(width, height);
		panelPositions.setLayout(null);
		panelImage = new JPanelImage(dataModel);
		JScrollPane scrollPane = new JScrollPane(panelImage, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
		tabbedPane.addTab(BUNDLE.getString("design_panel_name"), null, panelDesign, null);

		/* Datos de la foto */
		int yMovPos = 10;
		int widthVar = 150;

		JPanel panelImage = new JPanel();
		panelImage.setBorder(new TitledBorder(null, BUNDLE.getString("design_photo_label"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panelImage.setBounds(5, yMovPos, 180, widthVar);
		panelImage.setLayout(null);
		panelPositions.add(panelImage);

		JLabel lblImgPosX = new JLabel(BUNDLE.getString("design_photo_posx"));
		lblImgPosX.setHorizontalAlignment(SwingConstants.LEFT);
		lblImgPosX.setBounds(12, 25, 100, 17);
		panelImage.add(lblImgPosX);
		posXImgTmpl = new JTextField();
		posXImgTmpl.setColumns(10);
		posXImgTmpl.setBounds(106, 25, 50, 19);
		posXImgTmpl.getDocument().addDocumentListener(new BindingListener(dataModel, "posXImgTmpl"));
		panelImage.add(posXImgTmpl);

		JLabel lblImgPosY = new JLabel(BUNDLE.getString("design_photo_posy"));
		lblImgPosY.setHorizontalAlignment(SwingConstants.LEFT);
		lblImgPosY.setBounds(12, 54, 100, 17);
		panelImage.add(lblImgPosY);
		posYImgTmpl = new JTextField();
		posYImgTmpl.setColumns(10);
		posYImgTmpl.setBounds(106, 54, 50, 19);
		posYImgTmpl.getDocument().addDocumentListener(new BindingListener(dataModel, "posYImgTmpl"));
		panelImage.add(posYImgTmpl);

		JLabel lblImgWidth = new JLabel(BUNDLE.getString("design_photo_width"));
		lblImgWidth.setHorizontalAlignment(SwingConstants.LEFT);
		lblImgWidth.setBounds(12, 83, 100, 17);
		panelImage.add(lblImgWidth);
		imageWidth = new JTextField();
		imageWidth.setColumns(10);
		imageWidth.setBounds(106, 83, 50, 19);
		imageWidth.getDocument().addDocumentListener(new BindingListener(dataModel, "widthImgTmpl"));
		panelImage.add(imageWidth);

		JLabel lblImgHeight = new JLabel(BUNDLE.getString("design_photo_height"));
		lblImgHeight.setHorizontalAlignment(SwingConstants.LEFT);
		lblImgHeight.setBounds(12, 112, 100, 17);
		panelImage.add(lblImgHeight);
		imageHeight = new JTextField();
		imageHeight.setColumns(10);
		imageHeight.setBounds(106, 112, 50, 19);
		imageHeight.getDocument().addDocumentListener(new BindingListener(dataModel, "heightImgTmpl"));
		panelImage.add(imageHeight);

		yMovPos += widthVar + 10;
		widthVar = 60;
		JPanel panelRole = new JPanel();
		panelRole.setBorder(new TitledBorder(null, BUNDLE.getString("design_role_label"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panelRole.setBounds(5, yMovPos, 180, widthVar);
		panelRole.setLayout(null);
		panelPositions.add(panelRole);

		JLabel lblPosyRole = new JLabel(BUNDLE.getString("design_role_posy"));
		lblPosyRole.setHorizontalAlignment(SwingConstants.LEFT);
		lblPosyRole.setBounds(12, 25, 100, 17);
		panelRole.add(lblPosyRole);
		posYRole = new JTextField();
		posYRole.setColumns(10);
		posYRole.setBounds(106, 25, 50, 19);
		posYRole.getDocument().addDocumentListener(new BindingListener(dataModel, "posYRoleTmpl"));
		panelRole.add(posYRole);

		yMovPos += widthVar + 10;
		JPanel panelName = new JPanel();
		panelName.setBorder(new TitledBorder(null, BUNDLE.getString("design_name_label"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panelName.setBounds(5, yMovPos, 180, widthVar);
		panelName.setLayout(null);
		panelPositions.add(panelName);

		JLabel lblPosyNombre = new JLabel(BUNDLE.getString("design_name_posy"));
		lblPosyNombre.setHorizontalAlignment(SwingConstants.LEFT);
		lblPosyNombre.setBounds(12, 25, 100, 17);
		panelName.add(lblPosyNombre);
		posYName = new JTextField();
		posYName.setColumns(10);
		posYName.setBounds(106, 25, 50, 19);
		posYName.getDocument().addDocumentListener(new BindingListener(dataModel, "posYNameTmpl"));
		panelName.add(posYName);

		yMovPos += widthVar + 10;
		JPanel panelIDAndRH = new JPanel();
		panelIDAndRH.setBorder(new TitledBorder(null, BUNDLE.getString("design_id_label"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panelIDAndRH.setBounds(5, yMovPos, 180, widthVar);
		panelIDAndRH.setLayout(null);
		panelPositions.add(panelIDAndRH);

		JLabel lblPosyCedRH = new JLabel(BUNDLE.getString("design_id_posy"));
		lblPosyCedRH.setHorizontalAlignment(SwingConstants.LEFT);
		lblPosyCedRH.setBounds(12, 25, 100, 17);
		panelIDAndRH.add(lblPosyCedRH);
		posYIDAndRH = new JTextField();
		posYIDAndRH.setColumns(10);
		posYIDAndRH.setBounds(106, 25, 50, 19);
		posYIDAndRH.getDocument().addDocumentListener(new BindingListener(dataModel, "posYIDAndRHTmpl"));
		panelIDAndRH.add(posYIDAndRH);

		yMovPos += widthVar + 10;
		JPanel panelProcess = new JPanel();
		panelProcess.setBounds(5, yMovPos, 180, widthVar);
		JButton processBtn = new JButton(BUNDLE.getString("process_btn"));
		processBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!dataModel.isModelComplete()) {
					JOptionPane.showMessageDialog(frame, BUNDLE.getString("process_data_complete_error"), "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					tabbedPane.setSelectedComponent(panelResults);
					progressBar.setVisible(true);
					TitledBorder border = (TitledBorder)progressBar.getBorder();
					border.setTitle(BUNDLE.getString("in_progress_bar"));
					processController = new ProcessController(dataModel, resultModel);
					processController.start();
				}
			}
		});
		panelProcess.add(processBtn);
		panelPositions.add(panelProcess);
	}

	protected void createResultsPanel() {
		panelResults = new JPanel();
		panelResults.setSize(width, height);
		panelResults.setLayout(null);
		tabbedPane.addTab(BUNDLE.getString("results_panel_name"), null, panelResults, null);

		JPanel panelProcess = new JPanel();
		panelProcess.setBorder(new TitledBorder(null, BUNDLE.getString("results_panel_processing_label"),
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelProcess.setBounds(5, 0, 628, 120);
		panelProcess.setLayout(null);
		panelResults.add(panelProcess);

		JLabel lblTotalRecords = new JLabel(BUNDLE.getString("results_panel_totalrecords_label"));
		lblTotalRecords.setBounds(12, 23, 181, 15);
		lblTotalRecords.setHorizontalAlignment(SwingConstants.CENTER);
		panelProcess.add(lblTotalRecords);

		JLabel lblValidRecords = new JLabel(BUNDLE.getString("results_panel_validrecords_label"));
		lblValidRecords.setBounds(212, 23, 181, 15);
		lblValidRecords.setHorizontalAlignment(SwingConstants.CENTER);
		panelProcess.add(lblValidRecords);

		JLabel lblErrorRecords = new JLabel(BUNDLE.getString("results_panel_errorrecords_label"));
		lblErrorRecords.setBounds(412, 23, 181, 15);
		lblErrorRecords.setHorizontalAlignment(SwingConstants.CENTER);
		panelProcess.add(lblErrorRecords);

		totalRecords = new JLabel();
		totalRecords.setBounds(12, 45, 181, 15);
		totalRecords.setHorizontalAlignment(SwingConstants.CENTER);
		panelProcess.add(totalRecords);

		validRecords = new JLabel();
		validRecords.setBounds(212, 45, 181, 15);
		validRecords.setHorizontalAlignment(SwingConstants.CENTER);
		panelProcess.add(validRecords);

		errorRecords = new JLabel();
		errorRecords.setBounds(412, 45, 181, 15);
		errorRecords.setHorizontalAlignment(SwingConstants.CENTER);
		panelProcess.add(errorRecords);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(22, 71, 571, 35);
		progressBar.setMinimum(0);
		progressBar.setStringPainted(true);
		progressBar.setVisible(false);
		progressBar.setValue(0);
		Border border = BorderFactory.createTitledBorder("");
	    progressBar.setBorder(border);
		panelProcess.add(progressBar);

		textAreaLog = new JTextArea();
		DefaultCaret caret = (DefaultCaret) textAreaLog.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		textAreaLog.setAutoscrolls(true);
		JScrollPane scrollPane = new JScrollPane(textAreaLog, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(5, 130, 628, 400);
		scrollPane.setAutoscrolls(true);
		panelResults.add(scrollPane);

	}

	protected void selectImgButtonActionPerformed(ActionEvent e) {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images(jpg, jpeg, png)", "jpg", "jpeg", "png");
		File reference = openFileChooser(templateImage, JFileChooser.FILES_ONLY, filter);

		loadImage(reference);
	}

	protected void selectFolderButtonActionPerformed(ActionEvent e) {
		openFileChooser(folderImages, JFileChooser.DIRECTORIES_ONLY, null);
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
		sourceImgage = ImageHelper.loadImage(reference);
		printImage();
	}

	private void printImage() {
		if (sourceImgage != null) {
			panelImage.setImage(sourceImgage);
			panelImage.setSize(sourceImgage.getWidth(), sourceImgage.getHeight());
		}
	}

	private void loadProperties() {
		Properties prop = new Properties();
		try {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("resources/IDBadge.properties");

			if (inputStream != null) {
				prop.load(inputStream);
				String value = prop.getProperty("templateFile");
				dataModel.setTemplateFile(value);
				setValueToField(value, templateImage);
				loadImage(new File(templateImage.getText()));
				 
				value = prop.getProperty("csvFile");
				dataModel.setCsvFile(value);
				setValueToField(value, csvFile);

				// setValueToField(prop.getProperty("csvFileDelimiter"), csvDelimiter);
				
				value = prop.getProperty("csvFileEncoding","UTF-8");
				dataModel.setCsvFileEncoding(value);
				if (value != null) {
					encoding.setSelectedItem(value);
				}
				
				value = prop.getProperty("inputFolder");
				dataModel.setInputFolder(value);
				setValueToField(value, folderImages);
				
				value = prop.getProperty("outputFolder");
				dataModel.setOutputFolder(value);
				setValueToField(value, folderResult);
				
				value = prop.getProperty("posXImgTmpl","-1");
				dataModel.setPosXImgTmpl(Integer.valueOf(value));
				setValueToField(value, posXImgTmpl);

				value = prop.getProperty("posYImgTmpl","-1");
				dataModel.setPosYImgTmpl(Integer.valueOf(value));
				setValueToField(value, posYImgTmpl);

				value = prop.getProperty("widthImgTmpl","-1");
				dataModel.setWidthImgTmpl(Integer.valueOf(value));
				setValueToField(value, imageWidth);

				value = prop.getProperty("heightImgTmpl","-1");
				dataModel.setHeightImgTmpl(Integer.valueOf(value));
				setValueToField(value, imageHeight);

				value = prop.getProperty("posYRoleTmpl","-1");
				dataModel.setPosYRoleTmpl(Integer.valueOf(value));
				setValueToField(value, posYRole);

				value = prop.getProperty("posYNameTmpl","-1");
				dataModel.setPosYNameTmpl(Integer.valueOf(value));
				setValueToField(value, posYName);
				
				value = prop.getProperty("posYIDAndRHTmpl","-1");
				dataModel.setPosYIDAndRHTmpl(Integer.valueOf(value));
				setValueToField(value, posYIDAndRH);

				value = prop.getProperty("nameColumnName","Nombre");
				dataModel.setNameColumnName(value);

				value = prop.getProperty("idColumnName","Cedula");
				dataModel.setIdColumnName(value);

				value = prop.getProperty("typeIDColumnName","TipoCC");
				dataModel.setTypeIDColumnName(value);

				value = prop.getProperty("roleColumnName","Cargo");
				dataModel.setRoleColumnName(value);

				value = prop.getProperty("rhColumnName","RH");
				dataModel.setRhColumnName(value);

				value = prop.getProperty("imageColumnName","Foto");
				dataModel.setImageColumnName(value);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void setValueToField(String value, JTextComponent component) {
		if (value != null) {
			component.setText(value);
		}
	}
}
