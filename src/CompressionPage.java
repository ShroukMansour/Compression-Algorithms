

import imageCompression.NonuniformQuantizer;
import imageCompression.PredictiveFeedForward;
import textCompression.AdabtiveHuffman;
import textCompression.Arithmetic;
import textCompression.LZWAlgorithm;
import textCompression.StdHuffman;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

import assets.ImageClass;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.eclipse.swt.custom.CBanner;

// divide in more relevant package's name
// make clean code especially adaptive
// Write binary

public class CompressionPage {

    protected Shell shell;
    private String fileName;
    private Text txtNumOfLevels;
    private Text text;


    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            CompressionPage window = new CompressionPage();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Open the window.
     */
    public void open() {
        Display display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    /**
     * Create contents of the window.
     */
    protected void createContents() {
        shell = new Shell();
        shell.setImage(SWTResourceManager.getImage("C:\\Users\\Shrouk Mansour\\Pictures\\wpid-curata__9d1f1665d963efe6c463be961ce00458.jpg"));
        shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION_TEXT));
        shell.setSize(493, 489);
        shell.setText("SWT Application");

        Label lblNewLabel = new Label(shell, SWT.NONE);
        lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblNewLabel.setFont(SWTResourceManager.getFont("Sitka Text", 16, SWT.NORMAL));
        lblNewLabel.setBounds(101, 10, 289, 38);
        lblNewLabel.setText("Compression Algorithms");

        Label fileChoosed = new Label(shell, SWT.NONE);
        fileChoosed.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        fileChoosed.setBounds(159, 72, 276, 15);
        fileChoosed.setText("No file choosen");

        Button btnNewButton = new Button(shell, SWT.NONE);
        btnNewButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                JFileChooser choose = new JFileChooser();

                File f = new File("E:\\FCI\\Third year\\First term\\Multimedia\\readWriteImage\\ReadWriteImageClass");

                choose.setCurrentDirectory(f);
                int returnValue = choose.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    fileName = choose.getSelectedFile().getAbsolutePath();
                    fileChoosed.setText(choose.getSelectedFile().getAbsolutePath());
                } else {
                    fileChoosed.setText("No file choosen");
                }
            }
        });
        btnNewButton.setBounds(10, 67, 143, 25);
        btnNewButton.setText("Choose file or image...");


        Button btnNewButton_1 = new Button(shell, SWT.NONE);
        btnNewButton_1.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                LZWAlgorithm lzw = new LZWAlgorithm();
                try {
                    lzw.readFile(fileName);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnNewButton_1.setBounds(27, 195, 75, 25);
        btnNewButton_1.setText("Compress");

        Label lblChooseAlgorithms = new Label(shell, SWT.NONE);
        lblChooseAlgorithms.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblChooseAlgorithms.setFont(SWTResourceManager.getFont("Segoe Marker", 14, SWT.NORMAL));
        lblChooseAlgorithms.setBounds(10, 123, 226, 25);
        lblChooseAlgorithms.setText("Text compression algorithms");

        Label lblLzw = new Label(shell, SWT.NONE);
        lblLzw.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblLzw.setBounds(25, 166, 55, 15);
        lblLzw.setText("LZW");

        Button btnNewButton_2 = new Button(shell, SWT.NONE);
        btnNewButton_2.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                LZWAlgorithm lzw = new LZWAlgorithm();
                try {
                    lzw.readTagsFromFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnNewButton_2.setBounds(27, 226, 75, 25);
        btnNewButton_2.setText("Decompress");

        Label lblStandardHuffman = new Label(shell, SWT.NONE);
        lblStandardHuffman.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblStandardHuffman.setBounds(120, 166, 98, 15);
        lblStandardHuffman.setText("Standard Huffman");

        Button btnCpmpress = new Button(shell, SWT.NONE);
        btnCpmpress.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                StdHuffman stdHuff = new StdHuffman();
                try {
                    stdHuff.compress(fileName);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        btnCpmpress.setBounds(121, 195, 75, 25);
        btnCpmpress.setText("Compress");

        Button btnDecompress = new Button(shell, SWT.NONE);
        btnDecompress.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                StdHuffman stdHuff = new StdHuffman();
                try {
                    stdHuff.decompress();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        btnDecompress.setBounds(121, 226, 75, 25);
        btnDecompress.setText("Decompress");

        Label lblAdaptiveHuffman = new Label(shell, SWT.NONE);
        lblAdaptiveHuffman.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblAdaptiveHuffman.setBounds(232, 166, 106, 15);
        lblAdaptiveHuffman.setText("Adaptive Huffman");

        Button btnCompress = new Button(shell, SWT.NONE);
        btnCompress.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                AdabtiveHuffman advHuff = new AdabtiveHuffman();
                try {
                    advHuff.compress(fileName);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnCompress.setBounds(232, 195, 75, 25);
        btnCompress.setText("Compress");

        Button btnDecompress_1 = new Button(shell, SWT.NONE);
        btnDecompress_1.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                AdabtiveHuffman advHuff = new AdabtiveHuffman();
                try {
                    advHuff.decompress();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        });
        btnDecompress_1.setBounds(232, 226, 75, 25);
        btnDecompress_1.setText("Decompress");

        Button btnCompress_1 = new Button(shell, SWT.NONE);
        btnCompress_1.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Arithmetic arithmetic = new Arithmetic();
                try {
                    arithmetic.compress(fileName);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnCompress_1.setBounds(360, 195, 75, 25);
        btnCompress_1.setText("Compress");

        Button btnDecompress_2 = new Button(shell, SWT.NONE);
        btnDecompress_2.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Arithmetic arithmetic = new Arithmetic();
                try {
                    arithmetic.decompress();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        });
        btnDecompress_2.setBounds(360, 226, 75, 25);
        btnDecompress_2.setText("Decompress");

        Label lblArithmetic = new Label(shell, SWT.NONE);
        lblArithmetic.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblArithmetic.setBounds(360, 165, 84, 15);
        lblArithmetic.setText("Arithmetic");

        Label lblImageCompressionAlgorithms = new Label(shell, SWT.NONE);
        lblImageCompressionAlgorithms.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblImageCompressionAlgorithms.setFont(SWTResourceManager.getFont("Segoe Marker", 14, SWT.NORMAL));
        lblImageCompressionAlgorithms.setBounds(10, 270, 224, 25);
        lblImageCompressionAlgorithms.setText("Image compression algorithms");

        Label lblNonuniformQuantizer = new Label(shell, SWT.NONE);
        lblNonuniformQuantizer.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblNonuniformQuantizer.setBounds(25, 305, 171, 15);
        lblNonuniformQuantizer.setText("Non-uniform Quantizer");

        txtNumOfLevels = new Text(shell, SWT.BORDER);
        txtNumOfLevels.setText("Num of levels");
        txtNumOfLevels.setBounds(27, 342, 85, 21);

        Button btnCompress_2 = new Button(shell, SWT.NONE);
        btnCompress_2.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                NonuniformQuantizer nq = new NonuniformQuantizer();
                ImageClass img = new ImageClass();
                String val = txtNumOfLevels.getText();
                nq.setNumOfLevels((int) Math.pow(2, Integer.parseInt(val)));
                nq.setImgMatrix(img.readImage(fileName));
                nq.setImgHieght(img.getHieght());
                nq.setImgWidth(img.getWidth());

                try {
                    nq.compress();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        btnCompress_2.setBounds(27, 369, 85, 25);
        btnCompress_2.setText("Compress");

        Button btnDecompress_3 = new Button(shell, SWT.NONE);
        btnDecompress_3.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                NonuniformQuantizer nq = new NonuniformQuantizer();
                try {
                    nq.decompress();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        btnDecompress_3.setBounds(27, 401, 85, 25);
        btnDecompress_3.setText("Decompress");
        
        text = new Text(shell, SWT.BORDER);
        text.setText("Num of levels");
        text.setBounds(253, 342, 85, 21);
        
        Button button = new Button(shell, SWT.NONE);
        button.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
                PredictiveFeedForward ff = new PredictiveFeedForward();
                ImageClass img = new ImageClass();
                String val = text.getText();
                ff.setNumOfLevels((int) Math.pow(2, Integer.parseInt(val)));
                ff.setImgMatrix(img.readImage(fileName));

                try {
                    ff.compress();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
        	}
        });
        button.setText("Compress");
        button.setBounds(253, 369, 85, 25);
        
        Button button_1 = new Button(shell, SWT.NONE);
        button_1.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		PredictiveFeedForward ff = new PredictiveFeedForward();
        		try {
					ff.decompress();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        button_1.setText("Decompress");
        button_1.setBounds(253, 401, 85, 25);
        
        Label lblPredictiveFeedForward = new Label(shell, SWT.NONE);
        lblPredictiveFeedForward.setText("Predictive feed forward");
        lblPredictiveFeedForward.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        lblPredictiveFeedForward.setBounds(253, 305, 171, 15);


    }
}
