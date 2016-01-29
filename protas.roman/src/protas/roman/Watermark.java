package protas.roman;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.CANCEL_OPTION;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jtransforms.dct.DoubleDCT_2D;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

public class Watermark extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
    private BufferedImage RealisImage;
    double [][] fileR;
    double [][] fileG;
    double [][] fileB;
    
    public Watermark(BufferedImage img, Main parent) {
        image = img;
        RealisImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        initComponents();
    }   
    private  BufferedImage WaterMark (){
        double [] W;
        DoubleDCT_2D dct = new DoubleDCT_2D(image.getHeight(), image.getWidth());
        double[][] dataR = new double[image.getHeight()][image.getWidth()];
        double[][] dataG = new double[image.getHeight()][image.getWidth()];
        double[][] dataB = new double[image.getHeight()][image.getWidth()];
        Color color;
        float red, green, blue;
        int K = Integer.parseInt(jtxtK.getText());
        W = GaussElements(K);       
            for (int x = 0; x < image.getHeight(); x++) {
                for (int y = 0; y < image.getWidth(); y++) {
                    color = new Color(image.getRGB(y,x));
                    red = color.getRed();
                    green = color.getGreen();
                    blue = color.getBlue();

                    dataR[x][y] = red;
                    dataG[x][y] = green;
                    dataB[x][y] = blue;
                }
            }    
    //----------------------------------- DCT manipulacja ----------------------------------------------//
        dct.forward(dataR,true);
        dct.forward(dataG,true);
        dct.forward(dataB,true);
        fileR = new double [K][4];                // plik do zapisywania u file
        fileG = new double [K][4];                // plik do zapisywania u file
        fileB = new double [K][4];                // plik do zapisywania u file        
        ImageAlgorithms.WaterMarking(dataR, W, 0.1, fileR);
        ImageAlgorithms.WaterMarking(dataG, W, 0.1, fileG);
        ImageAlgorithms.WaterMarking(dataB, W, 0.1, fileB);        
        dct.inverse(dataR,true);
        dct.inverse(dataG,true);
        dct.inverse(dataB,true);
    //--------------------------------Normalizacja wynikow----------------------------------------------//
        double RmaxR = 0;             			// max znaczenie dla przeskalowania
        double RmaxG = 0;           			// max znaczenie dla przeskalowania
        double RmaxB = 0;
         for (int x = 0; x < image.getHeight(); x++) {
                for (int y = 0; y < image.getWidth(); y++) {
                    if (RmaxR < dataR[x][y]){
                    	RmaxR = dataR[x][y];
                    	}
                    if (RmaxG < dataG[x][y]){
                    	RmaxG = dataG[x][y];
                    	}
                    if (RmaxB < dataB[x][y]){
                    	RmaxB = dataB[x][y];
                    	}
                }
            }
            for (int x = 0; x < image.getHeight(); x++) {
                for (int y = 0; y < image.getWidth(); y++) {
                    dataR[x][y] = (dataR[x][y]*255/RmaxR);
                    dataG[x][y] = (dataG[x][y]*255/RmaxG);
                    dataB[x][y] = (dataB[x][y]*255/RmaxB);
                    color = new Color(ImageAlgorithms.clamp((int)dataR[x][y],0,255),
                    				  ImageAlgorithms.clamp((int)dataG[x][y],0,255),
                    				  ImageAlgorithms.clamp((int)dataB[x][y],0,255));
                    RealisImage.setRGB(y, x, color.getRGB());
                }
            }  
        return RealisImage;    
    }                      
    private void initComponents() {
    	lblCorrelation = new javax.swing.JLabel();
        JlblRealis = new javax.swing.JLabel();
        jbtnMark = new javax.swing.JButton();
        jtxtK = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jbtnSaveWaterMark = new javax.swing.JToggleButton();
        jbtnReadWaterMark = new javax.swing.JButton();
        jbtnCompare = new javax.swing.JButton();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        JlblRealis.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JlblRealis.setText("x");
        JlblRealis.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        JlblRealis.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnMark.setText("Mark");
        jbtnMark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnMarkActionPerformed(evt);
            }
        });

        jtxtK.setText("100");
        jLabel1.setText("Wspolczynnik [K]: ");
        jbtnSaveWaterMark.setText("Save WaterMark");
        jbtnSaveWaterMark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSaveWaterMarkActionPerformed(evt);
            }
        });
        jbtnReadWaterMark.setText("Read WaterMark");
        jbtnReadWaterMark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnReadWaterMarkActionPerformed(evt);
            }
        });
        jbtnCompare.setText("Compare");
        jbtnCompare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCompareActionPerformed(evt);
            }
        });    
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(JlblRealis, GroupLayout.DEFAULT_SIZE, 1324, Short.MAX_VALUE)
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jLabel1)
        						.addComponent(jbtnReadWaterMark))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        						.addComponent(jbtnSaveWaterMark, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
        						.addComponent(jtxtK, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(jbtnMark, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(jbtnCompare, GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
        					.addGap(918))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(lblCorrelation, GroupLayout.DEFAULT_SIZE, 1314, Short.MAX_VALUE)
        					.addContainerGap())))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addComponent(JlblRealis, GroupLayout.PREFERRED_SIZE, 599, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(lblCorrelation, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel1)
        				.addComponent(jtxtK, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jbtnCompare))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jbtnReadWaterMark)
        				.addComponent(jbtnSaveWaterMark)
        				.addComponent(jbtnMark))
        			.addContainerGap(30, Short.MAX_VALUE))
        );
        getContentPane().setLayout(layout);
        pack();
    }                       
    private void jbtnMarkActionPerformed(java.awt.event.ActionEvent evt) {                                         
        JlblRealis.setIcon(new ImageIcon(WaterMark()));       
    }                                        
    private void jbtnSaveWaterMarkActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        SaveWaterMark();
    }                                                 
    private void jbtnReadWaterMarkActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        OpenWaterMark();
    }                                                 
    private void jbtnCompareActionPerformed(java.awt.event.ActionEvent evt) {                                            
    	lblCorrelation.setText(CompareWaterMark());
    }                                           
    public double [] GaussElements(int K){
        double [] gArray = new double [K];
        Random r = new Random();
        for (int x = 0; x < K; x++){
                gArray[x] = r.nextGaussian();
        	}
        return gArray;
    }    
    public void SaveWaterMark() {
        JFileChooser fc = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");
        fc.addChoosableFileFilter(filter);
        fc.setFileFilter(filter);
            int returnVal = fc.showSaveDialog(null);           
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                DataOutputStream dos = null;
                File file = fc.getSelectedFile();
                String fname = file.getPath();
                this.setTitle(fname);              
                try{
                    ImageIO.write(RealisImage, "jpg", file);
                    dos = new DataOutputStream(new FileOutputStream(file+".wtm"));
                    dos.writeDouble(fileR.length);
                    for (int i = 0; i < fileR.length; i++) {
                        dos.writeDouble(fileR[i][0]);
                        dos.writeDouble(fileR[i][1]);
                        dos.writeDouble(fileR[i][2]);
                        dos.writeDouble(fileR[i][3]);                       
                        dos.writeDouble(fileG[i][0]);
                        dos.writeDouble(fileG[i][1]);
                        dos.writeDouble(fileG[i][2]);
                        dos.writeDouble(fileG[i][3]);                        
                        dos.writeDouble(fileB[i][0]);
                        dos.writeDouble(fileB[i][1]);
                        dos.writeDouble(fileB[i][2]);
                        dos.writeDouble(fileB[i][3]);
                    }                         
                }catch (IOException e){
                        System.out.print("I/O Error: "+e); 
                }finally {
                        try{
                            if (dos!=null){dos.close();}
                        }catch(IOException e){                      
                       }
                	}
            	}
    	}   
    public void OpenWaterMark() {
        int mc = JOptionPane.QUESTION_MESSAGE;
        int bc = JOptionPane.OK_CANCEL_OPTION;
        int ch = JOptionPane.showConfirmDialog (null, "Je¿eli watermark nie by³ zapisany, \n to zostane utracony. \n  Kliknij OK dla kontynuacji lub Cancel dla odmiany. ", "Title", bc, mc);
        if (ch==CANCEL_OPTION) return;
        
        JFileChooser fc = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("Watermark file", "wtm");
        fc.addChoosableFileFilter(filter);
        fc.setFileFilter(filter);
        int returnVal = fc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION){
                DataInputStream dis = null;
                File file = fc.getSelectedFile();
                String fname = file.getPath();
                this.setTitle("read from "+fname);
               
                try{
                    dis = new DataInputStream(new FileInputStream(file));
                    int size = (int)dis.readDouble();
                    fileR = new double [size][4]; 
                    fileG = new double [size][4];
                    fileB = new double [size][4]; 
                    for (int i = 0; i < size; i++) {
                       fileR[i][0]=dis.readDouble(); 
                       fileR[i][1]=dis.readDouble(); 
                       fileR[i][2]=dis.readDouble(); 
                       fileR[i][3]=dis.readDouble();
                       fileG[i][0]=dis.readDouble(); 
                       fileG[i][1]=dis.readDouble(); 
                       fileG[i][2]=dis.readDouble(); 
                       fileG[i][3]=dis.readDouble();
                       fileB[i][0]=dis.readDouble(); 
                       fileB[i][1]=dis.readDouble(); 
                       fileB[i][2]=dis.readDouble(); 
                       fileB[i][3]=dis.readDouble(); 
                    }                   
                }catch (IOException e){
                        System.out.print("I/O Error: "+e); 
                }finally {
                        try{
                            if (dis!=null){
                            	dis.close();
                            	}
                        }catch(IOException e){                       
                        }
                	}
            	}
    		}
    public String CompareWaterMark() {
    	String comparsion = "\n ";
    	DoubleDCT_2D dct = new DoubleDCT_2D(image.getHeight(), image.getWidth());
    	double[][] dataR = new double[image.getHeight()][image.getWidth()];
    	double[][] dataG = new double[image.getHeight()][image.getWidth()];
    	double[][] dataB = new double[image.getHeight()][image.getWidth()];    
    	Color color;
    	float red, green, blue; 
        for (int x = 0; x < image.getHeight(); x++) {
            for (int y = 0; y < image.getWidth(); y++) {
                color = new Color(image.getRGB(y,x));
                red = color.getRed();
                green = color.getGreen();
                blue = color.getBlue();
                dataR[x][y] = red;
                dataG[x][y] = green;
                dataB[x][y] = blue;
            }
        }    
//------------------------------------------ DCT  ----------------------------------------------//
        dct.forward(dataR,true);
        dct.forward(dataG,true);
        dct.forward(dataB,true);
        comparsion = "Correlation :Red = " + Compare(dataR,fileR) + " Green = " + Compare(dataG,fileG)+" Blue = " + Compare(dataB,fileB);
        return comparsion;
    }
    public double Compare (double [][] Kanal, double [][] WaterMark) {
    	double correlation = 0;
    	double []Wprym = new double[WaterMark.length];
    	double Wsrednie = 0;
    	double WprymSrednie = 0;
    		for (int i = 0; i < WaterMark.length; i++) {
    			Wprym[i] = ( Kanal[(int)WaterMark[i][0]][(int)WaterMark[i][0]]- WaterMark[i][2])/WaterMark[i][2];
    			Wsrednie = Wsrednie + WaterMark[i][3];
    			WprymSrednie = WprymSrednie + Wprym[i];
    		}
    	Wsrednie = Wsrednie+WaterMark.length;
    	WprymSrednie = WprymSrednie + WaterMark.length;
    	double A = 0,B = 0, C = 0, D = 0, E = 0;
    		for (int i = 0; i < WaterMark.length; i++) {
    			A = Wprym[i] - WprymSrednie;
    			B = WaterMark[i][3] - Wsrednie;
    			C = C +(A*B);
    			D = D +(A*A);
    			E = E +(B*B);
    			}          
    		correlation = C/Math.sqrt(D*E);
    	return correlation;
    	}           
    private javax.swing.JLabel lblCorrelation;
    private javax.swing.JLabel JlblRealis;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton jbtnCompare;
    private javax.swing.JButton jbtnMark;
    private javax.swing.JButton jbtnReadWaterMark;
    private javax.swing.JToggleButton jbtnSaveWaterMark;
    private javax.swing.JTextField jtxtK;                  
}
