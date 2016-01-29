package protas.roman;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Median extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
	private BufferedImage originalImage;
    private BufferedImage image;
    private Main parent;
    private int numer;
    private int[] Sx;
    private int[] Sy;
    private Color color;

    public Median(BufferedImage image, Main parent) {
        this.parent = parent;
        this.originalImage = image;
        this.numer = 3;
        Sx = new int [numer];
        Sy = new int [numer];
        this.image = new BufferedImage(image.getWidth(),image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int x = 0; x < image.getWidth();x++) {
            for(int y = 0; y < image.getHeight(); y++) {
                this.image.setRGB(x, y, image.getRGB(x, y));
            }
        }
        initComponents();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(rootPaneCheckingEnabled);
    }
    private int checkSx(int x) {
        if (x < 0)return (Math.abs(x) - 1);
        if (x > originalImage.getWidth() - 1) return originalImage.getWidth() - (x - originalImage.getWidth()) - 1;
        return x;
    }
    private int checkSy(int x) {
        if (x < 0)return (Math.abs(x) - 1);
        if (x > originalImage.getHeight() - 1) return originalImage.getHeight() - (x - originalImage.getHeight()) - 1;
        return x;
    }
    private int[] getWx(int x) {
        int []t = new int[numer];
        int l = 0;
        for (int i =numer/2; i > 0; i--) {
            t[l] = checkSx(x - i);
            l++;
        }
        for (int i = 0; i <= numer/2; i++) {
            t[l] = checkSx(x + i);
            l++;
        }
        return t;
    }
    private int[] getWy(int x){
        int []t = new int[numer];
        int l = 0;
        for (int i = numer/2; i > 0; i--) {
            t[l] = checkSy(x - i);
            l++;
        }
        for (int i = 0; i <= numer/2; i++) {
            t[l]=checkSy(x+i);
            l++;
        }
        return t;
    }
    private Color min(){
        int mr,mg,mb;
        Color rob = null;
        mr = 300;
        mg = 300;
        mb = 300;
        for (int i = 0; i < numer; i++) {
            for(int j = 0; j < numer; j++) {
                rob = new Color(originalImage.getRGB(Sx[j], Sy[i]));
                if(mr > rob.getRed()) {
                	mr = rob.getRed();
                	}
                if(mg > rob.getGreen()) {
                	mg = rob.getGreen();
                	}
                if(mb > rob.getBlue()) {
                	mb = rob.getBlue();
                	}
            	}	
        	}
        return new Color(mr,mg,mb);
    }
    private Color max(){
        int mr,mg,mb;
        Color rob = null;
        mr = -1;
        mg = -1;
        mb = -1;
        for (int i = 0; i < numer; i++) {
            for(int j = 0; j <numer; j++) {
                rob = new Color(originalImage.getRGB(Sx[j], Sy[i]));
                if(mr < rob.getRed()) mr = rob.getRed();
                if(mg < rob.getGreen()) mg = rob.getGreen();
                if(mb < rob.getBlue()) mb = rob.getBlue();
            }
        }
        return new Color(mr,mg,mb);
    }
    private Color med() {
        int [] medr = new int[numer * numer];
        int [] medg = new int[numer * numer];
        int [] medb = new int[numer * numer];
        Color rob = null;
        int l = 0;
        for (int i = 0; i < numer; i++) {
            for(int j = 0; j <numer; j++) {
                rob = new Color(originalImage.getRGB(Sx[j], Sy[i]));
                medr[l] = rob.getRed();
                medg[l] = rob.getGreen();
                medb[l] = rob.getBlue();
                l++;
            }
        }
        Arrays.sort(medr);
        Arrays.sort(medg);
        Arrays.sort(medb);
        return new Color(medr[(numer * numer)/2], medg[(numer * numer)/2], medb[(numer * numer)/2]);
    }
    private void narysuj(){
        for(int x = 0;x < this.image.getWidth(); x++) {
            Sx = getWx(x);
            for(int y = 0; y < this.image.getHeight(); y++) {
                Sy = getWy(y);
                //minimum
                if(jRadioButton1.isSelected()) {
                    color = min();
                }
                //maksimum
                if (jRadioButton2.isSelected()) {
                    color = max();
                }
                //mediana
                if(jRadioButton3.isSelected()) {
                    color = med();
                }
                image.setRGB(x, y, color.getRGB());
            }
        }
        this.getContentPane().repaint();
    }                 
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        jRadioButton1.setText("Minimum");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setText("Maksimum");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jRadioButton3.setText("Mediana");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        jLabel1.setText("Rozmiar maski");

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(3), Integer.valueOf(1), null, Integer.valueOf(2)));
        jSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner1StateChanged(evt);
            }
        });

        jButton1.setText("Do");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("OK");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Anuluj");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jButton1, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
        				.addComponent(jRadioButton1)
        				.addComponent(jRadioButton2)
        				.addComponent(jRadioButton3)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addGap(8)
        					.addComponent(jLabel1)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(jSpinner1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addComponent(jButton2)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(jButton3)))
        			.addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addGap(21)
        			.addComponent(jRadioButton1)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jRadioButton2)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(jRadioButton3)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jSpinner1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel1))
        			.addGap(18)
        			.addComponent(jButton1)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jButton2)
        				.addComponent(jButton3))
        			.addContainerGap(146, Short.MAX_VALUE))
        );
        jPanel1.setLayout(jPanel1Layout);

        this.jLabel2.setIcon(new ImageIcon(image));
        this.jLabel2.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        this.jPanel1.add(this.jLabel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
        );
    }                       
    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                              
        if (jRadioButton1.isSelected()){
            jRadioButton2.setSelected(false);
            jRadioButton3.setSelected(false);
        }   
    }                                             
    private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {                                       
        numer = Integer.parseInt(jSpinner1.getValue().toString());
    }                                      
    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                              
        if (jRadioButton2.isSelected()){
            jRadioButton1.setSelected(false);
            jRadioButton3.setSelected(false);
        }
    }                                             
    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                              
        if (jRadioButton3.isSelected()) {
            jRadioButton2.setSelected(false);
            jRadioButton1.setSelected(false);
        }
    }                                             
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        narysuj();
    }                                        
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        for(int x = 0; x < this.image.getWidth(); x++) {
            for(int y = 0; y < this.image.getHeight(); y++){
                this.originalImage.setRGB(x, y, this.image.getRGB(x, y));
            }
        }
        this.parent.repaint();
        this.dispose();
    }                                        
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        this.dispose();
    }                                                            
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JSpinner jSpinner1;         
}