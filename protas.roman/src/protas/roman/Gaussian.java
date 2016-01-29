package protas.roman;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Vector;
import protas.roman.ImageAlgorithms;
import protas.roman.Main;
import javax.swing.JTable;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

public class Gaussian extends javax.swing.JFrame {
    public static double sigma = 0;
    public static int radius = 1;
    public static int[] secGauss = new int[21];
    public static double liczG = 0;
    public static int wzmocnienie = 0;
    
    public static int indexi(int i) {
    	if (i < 0) {
    		i = Math.abs(i) - 1;
    		}
    	if (i >= height) {
    		i = height - (i - height + 1);
    		}
    	return (i);
    	}

    public static int indexj(int i) {
    	if (i < 0) {
    		i = Math.abs(i) - 1;
    		}
    	if (i >= width) {
    		i = width - (i - width + 1);
    		}
    	return (i);
    	}
	private static final long serialVersionUID = 1L;
	Main parent = null;
    static int height; 
    static int width; 
    static int h;
    static int s;
    static int l;
    static int newP;
    static int rozmiarMaski=3;
    static int [][] SPixel = new int [21][21];
    static int [] SPixelG = new int [21];
    static int [][] SWaga = new int [21][21];
    
    public Gaussian(Main parent) {
    	this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.parent = parent;
        initComponents();
    }
    private Gaussian() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    void Splot(BufferedImage src, BufferedImage dst,int[]SPixel, int[]SWaga) {
        DataBufferInt sbuff = (DataBufferInt) src.getRaster().getDataBuffer();
        DataBufferInt dbuff = (DataBufferInt) dst.getRaster().getDataBuffer();
        int[] sp = sbuff.getData();
        int[] dp = dbuff.getData();
        int[][] sp2 = new int[712][712];
        int i = 0;
        int p1=0;
        height = src.getHeight();
        width = src.getWidth();
        for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) {
                sp2[y][x] = sp[i];
                i++;
            }
        }
         i = 0;
        for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) { 
               for (int y1 = 0; y1 < rozmiarMaski; y1++) {
                 for (int x1 = 0; x1 < rozmiarMaski; x1++) { 
                	 SPixel[y1] = sp2[indexi((y - (rozmiarMaski - 1)/2 + y1))][indexj((x - (rozmiarMaski - 1)/2) + x1)];
                 }  
               }  
                dp[i] = ImageAlgorithms.Gauss(sp[i], SPixel); // pixel celowy
                i++;
            }
        }   
    }
    void Gauss(BufferedImage src, BufferedImage dst,int[]SPixel) {
        DataBufferInt sbuff = (DataBufferInt) src.getRaster().getDataBuffer();
        DataBufferInt dbuff = (DataBufferInt) dst.getRaster().getDataBuffer();
        int[] sp = sbuff.getData();
        int[] dp = dbuff.getData();
        int[][] sp2 = new int[src.getHeight()][src.getWidth()];
        int[][] dp2 = new int[src.getHeight()][src.getWidth()];
        int i = 0;
        int p1 = 0;
        height = src.getHeight();
        width = src.getWidth();
        for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) {
                sp2[y][x] = sp[i];
                i++;
            }
        }
         i = 0;
        for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) { 
               for (int y1 = 0; y1 < rozmiarMaski; y1++) {
               SPixelG[y1]=sp2[indexi((y-(rozmiarMaski-1)/2+y1))][indexj(x)];
               }  
                dp[i] = ImageAlgorithms.Gauss(sp[i], SPixelG); // pixel celowy
                i++;
            }
        }
        i=0;
         for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) {
                sp2[y][x] = dp[i];
                i++;
            }
         }
        // druga maska
        i=0;
           for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) {      
                 for (int x1 = 0; x1 < rozmiarMaski; x1++) { 
               SPixelG[x1] = sp2[indexi(y)][indexj((x-(rozmiarMaski-1)/2+x1))];
               }  
                dp[i] = ImageAlgorithms.Gauss(sp[i], SPixelG); // pixel celowy
                i++;
            }
        }   
    }
   void Unsharp(BufferedImage src, BufferedImage dst,int[]SPixel) {
        DataBufferInt sbuff = (DataBufferInt) src.getRaster().getDataBuffer();
        DataBufferInt dbuff = (DataBufferInt) dst.getRaster().getDataBuffer();
        int[] sp = sbuff.getData();
        int[] dp = dbuff.getData();
        int i = 0;
        height = src.getHeight();
        width = src.getWidth();
        for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) {
                dp[i] = ImageAlgorithms.Unsharp(sp[i],dp[i]); // pixel celowy
                i++;
            }
        }
    } 
    @SuppressWarnings("unchecked")
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jSpinner3 = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jSpinner1 = new javax.swing.JSpinner();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Promien");

        jLabel2.setText("Sigma");

        jLabel3.setText("Współczynnik");

        jButton1.setText("Gauss");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GaussStart(evt);
            }
        });

        jSpinner3.setValue(1);
        jSpinner3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner3StateChanged(evt);
            }
        });

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Integer[rozmiarMaski][rozmiarMaski],
            new String [rozmiarMaski]
        ));
        jScrollPane1.setViewportView(jTable1);

        jSpinner1.setValue(1);
        jSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner1CounterChange(evt);
            }
        });

        jButton5.setText("Zastosuj");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Anuluj");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton2.setText("Unsharp");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UnsharpStart(evt);
            }
        });

        jTextField1.setText("0.5");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSpinner3, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }
    private void jSpinner1CounterChange(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner1CounterChange
        rozmiarMaski = ((int)jSpinner1.getValue())*2+1;
        radius =((int)jSpinner1.getValue());
        jTable1 = new javax.swing.JTable();
        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Integer[rozmiarMaski][rozmiarMaski],
            new String [rozmiarMaski]
        ));
        jScrollPane1.setViewportView(jTable1);
        sigma = Double.parseDouble(jTextField1.getText());        
        int k1 = 1;
        for (int k = 1; k <= radius+1; k++ ) {
        	secGauss[k + radius - 1] = ((int) (Math.pow((Math.E), (-k * k)/2 * sigma * sigma) * 10));
        	secGauss[k1 + radius - 1] = ((int) (Math.pow((Math.E), (-k * k)/2 * sigma * sigma) * 10));
        k1--;
    }
        liczG = 0;
        for (int k = 0;k < radius * 2 + 1 ; k++ ) {
        	jTable1.setValueAt(secGauss[k],(int)jSpinner1.getValue(),k);
        	jTable1.setValueAt(secGauss[k],k,(int)jSpinner1.getValue());
        	liczG = liczG + secGauss[k]; 
        }
        for (int k = 0; k < radius * 2 + 1 ;k++ ) {
        	jTable1.setValueAt(secGauss[k],(int)jSpinner1.getValue(),k);
        	jTable1.setValueAt(secGauss[k],k,(int)jSpinner1.getValue());
        }
    }
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        parent.updateImage(); //Zastosuj
    }
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        parent.revertImage(); 
    }
    private void GaussStart(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GaussStart
         try {
            Gauss(parent.originalImage, parent.workImage, SPixelG);
            parent.repaint();   
        }
        catch (Exception e)
        {
            System.out.println("Gauss error: " + e.getMessage());
        }
    }
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
    	sigma = Double.parseDouble(jTextField1.getText());  
    	radius =((int)jSpinner1.getValue());
    	int k1 = 1;
    	for (int k = 1; k <= radius + 1; k++ ) {
    		secGauss[k + radius - 1] = ((int) (Math.pow((Math.E), (-k * k)/2 * sigma * sigma) * 10));
    		secGauss[k1 + radius - 1] = ((int) (Math.pow((Math.E), (-k * k)/2 * sigma * sigma) * 10));
    		k1--;
    	}
    	liczG = 0;
    	for (int k = 0;k < radius * 2 + 1; k++ ) {
    		jTable1.setValueAt(secGauss[k],(int)jSpinner1.getValue(),k);
    		jTable1.setValueAt(secGauss[k],k,(int)jSpinner1.getValue());
    		liczG = liczG + secGauss[k]; 
    	} 
    }
    private void UnsharpStart(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UnsharpStart
        wzmocnienie = ((int)jSpinner3.getValue());
        try {
            Gauss(parent.originalImage, parent.workImage, SPixelG);
            Unsharp(parent.originalImage, parent.workImage, SPixelG);
            parent.repaint();
        } 
        catch (Exception e)
        {
            System.out.println("Unsharp error: " + e.getMessage());
        }
    }
    private void jSpinner3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner3StateChanged
    	wzmocnienie = ((int)jSpinner3.getValue()); 
    }
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Gaussian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gaussian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gaussian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gaussian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gaussian().setVisible(true);
            }
        });
    }
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner3;
    private static javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
}
