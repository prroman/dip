package protas.roman;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Unsharp extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
	private final BufferedImage originalImage;
    private final BufferedImage image;
    private final Main parent;
    private int numer;
    private int[] Sx;
    private int[] Sy;
    private int [][]mask;
    private int suma;
    private Color color;

    public Unsharp (BufferedImage image, Main parent) {
        this.parent = parent;
        this.originalImage = image;
        this.numer = 3;
        mask = new int[numer][numer];
        Sx = new int [numer];
        Sy = new int [numer];
        this.image = new BufferedImage(image.getWidth(),image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int x = 0; x < image.getWidth(); x++) {
            for(int y = 0; y < image.getHeight(); y++) {
                this.image.setRGB(x, y, image.getRGB(x, y));
            }
        }
        initComponents();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(rootPaneCheckingEnabled);
    }
    private void setmask(){
        double e = 2.71828183;
        double PI = 3.14159265;
        suma = 0;
        mask = new int[numer][numer];
        float sig = Float.parseFloat(sigma.getText());
        for(int i = 0; i < numer; i++) {
            for(int j = 0; j < numer; j++) {
                mask[i][j] =(int)((1/(2 * PI * sig * sig) * Math.pow(e,(-(Math.pow(i-numer/2,2) + Math.pow(j - numer/2,2))/(2 * sig * sig)))) * 100000);
                suma+= mask[i][j];
            }
        }
        if (suma==0)suma = 1;
    }
    private int checkRGB(int x){
        if(x < 0)return 0;
        if (x > 255)return 255;
        return x;
    }
    private int checkSx(int x){
        if (x < 0)return (Math.abs(x) - 1);
        if (x > originalImage.getWidth() - 1) return originalImage.getWidth() - (x - originalImage.getWidth()) - 1;
        return x;
    }
    private int checkSy(int x){
        if (x < 0)return (Math.abs(x) - 1);
        if (x > originalImage.getHeight() - 1) return originalImage.getHeight() - (x - originalImage.getHeight()) - 1;
        return x;
    }
    private int[] getWx(int x){
        int []t = new int[numer];
        int l = 0;
        for (int i = numer/2; i > 0; i--) {
            t[l] = checkSx(x - i);
            l++;
        }
        for (int i = 0; i <= numer/2; i++) {
            t[l] = checkSx(x+i);
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
            t[l] = checkSy(x + i);
            l++;
        }
        return t;
    }
    private void narysuj(){
        int sr = 0,sg = 0,sb = 0;
        for(int x = 0; x < this.image.getWidth(); x++) {
            Sx = getWx(x);
            for(int y = 0; y < this.image.getHeight(); y++) {
                Sy = getWy(y);
                for (int i = 0; i < numer; i++) {
                    for(int j = 0; j < numer; j++) {
                        color = new Color(originalImage.getRGB(Sx[j], Sy[i]));
                        sr+= color.getRed() * mask[i][j];
                        sg+= color.getGreen() * mask[i][j];
                        sb+= color.getBlue() * mask[i][j];
                }
                int r,g,b;
                float wz;
                wz = Float.parseFloat(wzmocnienie.getText());
                color = new Color(originalImage.getRGB(x,y));
                r = color.getRed() + (int)wz * checkRGB(color.getRed() - checkRGB(sr/suma));
                g = color.getGreen() + (int)wz * checkRGB(color.getGreen() - checkRGB(sg/suma));
                b = color.getBlue() + (int)wz * checkRGB(color.getBlue() - checkRGB(sb/suma));
                color = new Color(checkRGB(r),checkRGB(g),checkRGB(b));
                sr = 0;sg = 0;sb = 0;
                image.setRGB(x, y, color.getRGB());
            }
        }
        this.getContentPane().repaint();
        }
    }
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        OK = new javax.swing.JButton();
        Anuluj = new javax.swing.JButton();
        jSpinner1 = new javax.swing.JSpinner();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        sigma = new javax.swing.JTextField();
        wzmocnienie = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setSize(image.getWidth(), image.getHeight());
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        OK.setText("OK");
        OK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKActionPerformed(evt);
            }
        });
        jPanel2.add(OK, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 60, -1));

        Anuluj.setText("Anuluj");
        Anuluj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnulujActionPerformed(evt);
            }
        });
        jPanel2.add(Anuluj, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 70, -1));

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
        jSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner1StateChanged(evt);
            }
        });
        jPanel2.add(jSpinner1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, -1, -1));

        jButton1.setText("Wykonaj");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 150, -1));

        jLabel2.setText("Promien:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel3.setText("Stopien:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        sigma.setText("2");
        sigma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sigmaActionPerformed(evt);
            }
        });
        jPanel2.add(sigma, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 30, -1));

        wzmocnienie.setText("1");
        wzmocnienie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wzmocnienieActionPerformed(evt);
            }
        });
        jPanel2.add(wzmocnienie, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 30, -1));

        jLabel4.setText("Stopie\u0144 wyostrzenia:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        this.jLabel1.setIcon(new ImageIcon(image));
        this.jLabel1.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        this.jPanel1.add(this.jLabel1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
        			.addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(jLabel1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        				.addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
        			.addContainerGap())
        );
        jPanel1.setLayout(jPanel1Layout);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        getContentPane().setLayout(layout);

        pack();
    }                       
    private void sigmaActionPerformed(java.awt.event.ActionEvent evt) {                                      
    }                                     
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        setmask();
        narysuj();
    }                                        
    private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {                                       
        numer = Integer.parseInt(jSpinner1.getValue().toString()) * 2 + 1;
    }                                      
    private void AnulujActionPerformed(java.awt.event.ActionEvent evt) {                                       
    this.dispose();
    }                                      
    private void OKActionPerformed(java.awt.event.ActionEvent evt) {                                   
        for(int x = 0; x < this.image.getWidth(); x++) {
            for(int y = 0; y < this.image.getHeight(); y++) {
                this.originalImage.setRGB(x, y, this.image.getRGB(x, y));
            }
        }
        this.parent.repaint();
        this.dispose();
    }                                  
    private void wzmocnienieActionPerformed(java.awt.event.ActionEvent evt) {                                            
    }                                                             
    private javax.swing.JButton Anuluj;
    private javax.swing.JButton OK;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTextField sigma;
    private javax.swing.JTextField wzmocnienie;              
}