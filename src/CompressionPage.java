import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

// divide in more relevant package's name
// make clean code especially adaptive
// Write binary

public class CompressionPage {

	protected Shell shell;
	private String fileName;
	

	/**
	 * Launch the application.
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
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION_TEXT));
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
		lblNewLabel.setBounds(101, 10, 226, 38);
		lblNewLabel.setText("Compression Algorithms");
		
		Label fileChoosed = new Label(shell, SWT.NONE);
		fileChoosed.setBounds(130, 72, 276, 15);
		fileChoosed.setText("No file choosen");
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			       JFileChooser choose=new JFileChooser();
			       
			        File f = new File("E:\\FCI\\Third year\\First term\\Multimedia\\Testing Files");
			        
			        choose.setCurrentDirectory(f);
			        int returnValue = choose.showOpenDialog(null);
			        if(returnValue == JFileChooser.APPROVE_OPTION)
			        {   
			            fileName = choose.getSelectedFile().getAbsolutePath();
			            fileChoosed.setText(choose.getSelectedFile().getAbsolutePath());
			        }
			        else
			        {
			        	fileChoosed.setText("No file choosen");
			        }
			}
		});
		btnNewButton.setBounds(27, 67, 83, 25);
		btnNewButton.setText("Choose file...");
		

		
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
		lblChooseAlgorithms.setBounds(27, 123, 148, 25);
		lblChooseAlgorithms.setText("Choose algorithm");
		
		Label lblLzw = new Label(shell, SWT.NONE);
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
		lblStandardHuffman.setBounds(120, 166, 114, 15);
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		btnDecompress_1.setBounds(232, 226, 75, 25);
		btnDecompress_1.setText("Decompress");

	}
}
