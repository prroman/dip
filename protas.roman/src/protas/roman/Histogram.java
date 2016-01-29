package protas.roman;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;

public class Histogram extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private final BufferedImage originalImage;
    private final BufferedImage hist;
    private final BufferedImage image;
    private final int []hgray = new int[256];
    private int maxG = 0;
    private final int []hred = new int[256];
    private int maxR = 0;
    private final int []hgreen = new int[256];
    private int maxGr = 0;
    private final int []hblue = new int[256];
    private int maxB = 0;
     
    public Histogram(BufferedImage  image, Main parent) {       
        this.originalImage = image;
        this.image = new BufferedImage(image.getWidth(),image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int x = 0; x < image.getWidth();x++) {
            for(int y = 0; y < image.getHeight(); y++) {
                this.image.setRGB(x, y, image.getRGB(x, y));
            }
        }
        zliczHistogramy(image);
        hist = new BufferedImage(256,256, BufferedImage.TYPE_INT_RGB);
        rysujHist(maxG,hgray);
        initComponents();
        zerujbuttony();
        jRadioButton1.setSelected(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(rootPaneCheckingEnabled);
    }
    private int check(int v){
        if (v < 0) 	 return 0;
        if (v > 255) return 255;
        return v;
    }
    private void zliczHistogramy(BufferedImage im){
        for(int i = 0;i < 256;i++){
            hgray[i] = 0;
            hred[i] = 0;
            hgreen[i] = 0;
            hblue[i] = 0;
        }
        for(int x = 0; x < im.getWidth(); x++) {
            for(int y = 0;y < im.getHeight(); y++) {
                int rgb = im.getRGB(x, y);
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = (rgb) & 0xff;
                hred[red]++;
                hblue[blue]++;
                hgreen[green]++;
            }
        }
        for(int i = 0;i < 256;i++){
            hgray[i] = (int)(0.2*hred[i] + 0.7*hgreen[i] + 0.1*hblue[i]);
            if (maxG < hgray[i])maxG = hgray[i];
            if (maxR < hred[i])maxR = hred[i];
            if (maxGr < hgreen[i])maxGr = hgreen[i];
            if (maxB < hblue[i])maxB = hblue[i];
        }
    }
    private void rysujHist(int max, int tab[]){        
        Color white = new Color(255,255,255);
        Color black = new Color(0,0,0);
        double skala = (double)hist.getHeight()/(double)max;
        for (int x = 255; x >= 0; x--) {
            int y;
            for (y = check((int)(max*skala)); y >= 255 - check((int)(skala*tab[x])); y--) {
                hist.setRGB(x, y, black.getRGB());
            }
            for (;y >= 0;y--){
                hist.setRGB(x, y, white.getRGB());
            }
        }
    }
    private void wyrownaj(){
        int rgb,red,green,blue;
        int []LUTR = new int[256];
        int []LUTG = new int[256];
        int []LUTB = new int[256];
        LUTR = liczLUT(hred);
        LUTG = liczLUT(hgreen);
        LUTB = liczLUT(hblue);
        for(int x = 0; x < image.getWidth(); x++) {
            for(int y = 0;y < image.getHeight(); y++) {
                rgb = image.getRGB(x, y);
                red = (rgb >> 16) & 0xff;
                green = (rgb >> 8) & 0xff;
                blue = (rgb) & 0xff;
                image.setRGB(x, y, new Color(LUTR[red],LUTG[green],LUTB[blue]).getRGB());
            }
        }
    }
    private int[] liczLUT(int []hist){
        int ilePixeli = originalImage.getWidth() * originalImage.getWidth();
        double []dystrybuanta = new double[256];
        int []LUT = new int[256];
        double temp = 0;
        for (int i = 0; i < 256; i++){
            temp+=((double)hist[i]/(double)ilePixeli);
            dystrybuanta[i] = temp;
            LUT[i] = 0;
        }
        double niezerowa = 0;
        for(int i = 0; i < 256; i++) {
            if(dystrybuanta[i] > 0) {
                niezerowa = dystrybuanta[i];
                break;
            }
        }
        for(int i = 0; i < 256; i++){
            LUT[i] = check((int)(((dystrybuanta[i]-niezerowa)/(1-niezerowa))*255));
        }
        return LUT;
    }
    private void prerepaint(){
        zliczHistogramy(image);
        if(jRadioButton1.isSelected()){
            rysujHist(maxG,hgray);
        }else{
            if(jRadioButton2.isSelected()){
                rysujHist(maxR,hred);
            }else{
                if(jRadioButton3.isSelected()){
                    rysujHist(maxGr,hgreen);
                }else{
                    rysujHist(maxB,hblue);
                }
            }
        }
    }
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();


        setTitle("Histogram Equalization");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel1.setSize(hist.getWidth(), hist.getHeight());

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jRadioButton1.setText("Grayscale");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setText("Red");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jRadioButton3.setText("Green");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        jRadioButton4.setText("Blue");
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        jButton1.setText("Wyrуwnaj");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("OK");

        jButton2.setText("Reset");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2Layout.setHorizontalGroup(
        	jPanel2Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(Alignment.LEADING, jPanel2Layout.createSequentialGroup()
        					.addContainerGap()
        					.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jButton2, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
        						.addComponent(jButton3, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
        						.addComponent(jButton1, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)))
        				.addGroup(jPanel2Layout.createSequentialGroup()
        					.addGap(14)
        					.addComponent(jRadioButton4, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
        				.addGroup(jPanel2Layout.createSequentialGroup()
        					.addGap(14)
        					.addComponent(jRadioButton3, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
        				.addGroup(Alignment.LEADING, jPanel2Layout.createSequentialGroup()
        					.addGap(14)
        					.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jRadioButton1, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
        						.addComponent(jRadioButton2, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))))
        			.addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
        	jPanel2Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel2Layout.createSequentialGroup()
        			.addGap(25)
        			.addComponent(jRadioButton1)
        			.addGap(18)
        			.addComponent(jRadioButton2)
        			.addGap(18)
        			.addComponent(jRadioButton3)
        			.addGap(18)
        			.addComponent(jRadioButton4)
        			.addGap(18)
        			.addComponent(jButton1)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(jButton2)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(jButton3)
        			.addContainerGap(145, Short.MAX_VALUE))
        );
        jPanel2.setLayout(jPanel2Layout);

        this.jLabel1.setIcon(new ImageIcon(hist));
        this.jLabel1.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        this.jPanel1.add(this.jLabel1);

        this.jLabel2.setIcon(new ImageIcon(image));
        this.jLabel2.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        this.jPanel1.add(this.jLabel2);
        
        JTextPane textPane = new JTextPane();
        
        JTextPane textPane_1 = new JTextPane();
        
        JTextPane textPane_2 = new JTextPane();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
        				.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING, false)
        					.addComponent(textPane_2, Alignment.LEADING)
        					.addComponent(textPane, Alignment.LEADING)
        					.addComponent(textPane_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)))
        			.addPreferredGap(ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
        			.addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 548, GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(textPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(textPane_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(textPane_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap())
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE))
        );
        jPanel1.setLayout(jPanel1Layout);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        
    private void zerujbuttony(){
        jRadioButton1.setSelected(false);
        jRadioButton2.setSelected(false);
        jRadioButton3.setSelected(false);
        jRadioButton4.setSelected(false);
    }
    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                              
        zerujbuttony();
        jRadioButton1.setSelected(true);
        rysujHist(maxG,hgray);
        this.repaint();
    }                                             

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                              
        zerujbuttony();
        jRadioButton2.setSelected(true);
        rysujHist(maxR,hred);
        this.repaint();
    }                                             

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                              
        zerujbuttony();
        jRadioButton3.setSelected(true);
        rysujHist(maxGr,hgreen);
        this.repaint();
    }                                             

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                              
        zerujbuttony();
        jRadioButton4.setSelected(true);
        rysujHist(maxB,hblue);
        this.repaint();
    }                                             

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         

        wyrownaj();
        prerepaint();
        this.repaint();
    }                                        

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        for(int x=0; x<image.getWidth();x++){
            for(int y=0; y<image.getHeight(); y++){
                this.image.setRGB(x, y, originalImage.getRGB(x, y));
            }
        }
        prerepaint();
        this.repaint();
    }                                                             
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
}
