package protas.roman;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LAB extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
	private BufferedImage originalImage;
    private BufferedImage image;
    private Main parent;
    private double l, a, b;
    private double RGB[],XYZ[];
    private double E = 0.008856;
    private double k = 903.3;

    public LAB(BufferedImage image, Main parent) {
        this.parent = parent;
        this.originalImage = image;
        this.image = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                this.image.setRGB(x, y, image.getRGB(x, y));
            }
        }
        RGB = new double[3];
        XYZ = new double[3];
        initComponents();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(rootPaneCheckingEnabled);
    }
    private double F_XYZtoLab(double X) {
        float pot = (float)(1.0/3.0);
        if (X > E) {
            return Math.pow(X,pot);
        } else {
            return ((k * X + 16) / 116);
        }
    }
    private int checkRGB(int x){
        if(x < 0)return 0;
        if(x > 255)return 255;
        return x;
    }
    private void rgb2xyz(){
            XYZ[0] = 0.5767		*RGB[0] + 0.185556	*RGB[1] + 0.188212	*RGB[2];
            XYZ[1] = 0.297361	*RGB[0] + 0.627355	*RGB[1] + 0.0752847	*RGB[2];
            XYZ[2] = 0.0270328	*RGB[0] + 0.0706879	*RGB[1] + 0.991248	*RGB[2];
    }
    private void xyz2rgb(){
            RGB[0] = 2.04148*XYZ[0] -   0.564977*XYZ[1] -  0.344713*XYZ[2];
            RGB[1] = (-0.969258)*XYZ[0]+ 1.87599*XYZ[1] + 0.0415557*XYZ[2];
            RGB[2] = 0.0134455*XYZ[0] - 0.118373*XYZ[1]   + 1.01527*XYZ[2];
    }
    private void rysuj() {
        double dl = Double.parseDouble(lSpinner.getValue().toString());
        double da = Double.parseDouble(aSpinner.getValue().toString());
        double db = Double.parseDouble(bSpinner.getValue().toString());
        double X, Y, Z, fx, fy, fz;
        double Xr = 0.964221;
        double Yr = 1.0;
        double Zr = 0.825211;
        double gamma,rob;
        Color c;
        for (int x = 0; x < this.image.getWidth(); x++) {
            for (int y = 0; y < this.image.getHeight(); y++) {
                c = new Color(originalImage.getRGB(x, y));
                //RGBtoXYZ
                gamma = 2.2;
                RGB[0] = Math.pow(((float)c.getRed()/255.0),gamma);
                RGB[1] = Math.pow(((float)c.getGreen()/255.0),gamma);
                RGB[2] = Math.pow(((float)c.getBlue()/255.0),gamma);
                rgb2xyz();
                //XYZtoLab
                X = (XYZ[0] / Xr);
                Y = (XYZ[1] / Yr);
                Z = (XYZ[2] / Zr);
                fx = F_XYZtoLab(X);
                fy = F_XYZtoLab(Y);
                fz = F_XYZtoLab(Z);
                l = (116 * fy - 16) + dl;
                a = (500 * (fx - fy)) + da;
                b = (200 * (fy - fz)) + db;
                //LabtoXYZ
               // checkLAB();
                fy = ((l + 16) / 116.0);
                fz = (fy - (b / 200.0));
                fx = (a / 500.0) + fy;
                rob = Math.pow(fx,3);
                if (rob > E){
                    X = rob;
                }else{
                    X = (((116.0 * fx) - 16)/k);
                }
                if (l > (k * E)){
                    Y = Math.pow(((l + 16)/116.0),3);
                }else{
                    Y = l/k;
                }
                rob = Math.pow(fz,3);
                if (rob > E){
                    Z = rob;
                }else{
                    Z = (((116.0 * fz)-16)/k);
                }
                XYZ[0] = X * Xr;
                XYZ[1] = Y * Yr;
                XYZ[2] = Z * Zr;
                //XYZ to RGB
                xyz2rgb();
                gamma = 1.0/gamma;
                int  r = checkRGB((int)(Math.pow(RGB[0],gamma) * 255));
                int  g = checkRGB((int)(Math.pow(RGB[1],gamma) * 255));
                int bl = checkRGB((int)(Math.pow(RGB[2],gamma) * 255));
                c = new Color(r,g,bl);
                this.image.setRGB(x, y, c.getRGB());
            }
        }
        this.getContentPane().repaint();
    }                         
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        aSlider = new javax.swing.JSlider(-128,127,0);
        lSlider = new javax.swing.JSlider(-100,100,0);
        bSlider = new javax.swing.JSlider(-128,127,0);
        OK = new javax.swing.JButton();
        Anuluj = new javax.swing.JButton();
        aSpinner = new javax.swing.JSpinner();
        bSpinner = new javax.swing.JSpinner();
        lSpinner = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setSize(image.getWidth(), image.getHeight());

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Hashtable<Integer, JLabel> labelTable1 = new Hashtable<Integer, JLabel>();
        labelTable1.put( new Integer(-128), new JLabel("-128") );
        labelTable1.put( new Integer(0), new JLabel("0") );
        labelTable1.put( new Integer(127), new JLabel("127") );
        aSlider.setLabelTable( labelTable1 );
        aSlider.setMajorTickSpacing(127);
        aSlider.setMinorTickSpacing(10);
        aSlider.setPaintLabels(true);
        aSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                aSliderStateChanged(evt);
            }
        });
        jPanel2.add(aSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 150, -1));

        Hashtable<Integer, JLabel> labelTable3 = new Hashtable<Integer, JLabel>();
        labelTable3.put( new Integer(-100), new JLabel("-100") );
        labelTable3.put( new Integer(0), new JLabel("0") );
        labelTable3.put( new Integer(100), new JLabel("100") );
        lSlider.setLabelTable( labelTable3 );
        lSlider.setMajorTickSpacing(100);
        lSlider.setMinorTickSpacing(10);
        lSlider.setPaintLabels(true);
        lSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                lSliderStateChanged(evt);
            }
        });
        jPanel2.add(lSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 150, -1));

        Hashtable<Integer, JLabel> labelTable2 = new Hashtable<Integer, JLabel>();
        labelTable2.put( new Integer(-128), new JLabel("-128") );
        labelTable2.put( new Integer(0), new JLabel("0") );
        labelTable2.put( new Integer(127), new JLabel("127") );
        bSlider.setLabelTable( labelTable2 );
        bSlider.setMajorTickSpacing(127);
        bSlider.setMinorTickSpacing(10);
        bSlider.setPaintLabels(true);
        bSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                bSliderStateChanged(evt);
            }
        });
        jPanel2.add(bSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 150, -1));

        OK.setText("OK");
        OK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKActionPerformed(evt);
            }
        });
        jPanel2.add(OK, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        Anuluj.setText("Anuluj");
        Anuluj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnulujActionPerformed(evt);
            }
        });
        jPanel2.add(Anuluj, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, -1, -1));

        aSpinner.setModel(new javax.swing.SpinnerNumberModel(0, -128, 127, 1));
        aSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                aSpinnerStateChanged(evt);
            }
        });
        jPanel2.add(aSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 37, -1));

        bSpinner.setModel(new javax.swing.SpinnerNumberModel(0, -128, 127, 1));
        bSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                bSpinnerStateChanged(evt);
            }
        });
        jPanel2.add(bSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 37, -1));

        lSpinner.setModel(new javax.swing.SpinnerNumberModel(0, -100, 100, 1));
        lSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                lSpinnerStateChanged(evt);
            }
        });
        jPanel2.add(lSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 37, -1));

        jLabel2.setText("a:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        jLabel3.setText("b:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        jLabel4.setText("L:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        this.jLabel1.setIcon(new ImageIcon(image));
        this.jLabel1.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        this.jPanel1.add(this.jLabel1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
                .addGap(11, 11, 11))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }                        
    private void aSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {                                      
        this.aSlider.setValue(Integer.parseInt(this.aSpinner.getValue().toString()));
    }                                     
    private void AnulujActionPerformed(java.awt.event.ActionEvent evt) {                                       
        this.dispose();
    }                                      
    private void OKActionPerformed(java.awt.event.ActionEvent evt) {                                   
        for (int x = 0; x < this.image.getWidth(); x++) {
            for (int y = 0; y < this.image.getHeight(); y++) {
                this.originalImage.setRGB(x, y, this.image.getRGB(x, y));
            }
        }
        this.parent.repaint();
        this.dispose();
    }                                 
    private void bSliderStateChanged(javax.swing.event.ChangeEvent evt) {                                     
        this.bSpinner.setValue(this.bSlider.getValue());
        rysuj();
    }                                    
    private void lSliderStateChanged(javax.swing.event.ChangeEvent evt) {                                     
        this.lSpinner.setValue(this.lSlider.getValue());
        rysuj();
    }                                    
    private void aSliderStateChanged(javax.swing.event.ChangeEvent evt) {                                     
        this.aSpinner.setValue(this.aSlider.getValue());
        rysuj();
    }                                    
    private void bSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {                                      
        this.bSlider.setValue(Integer.parseInt(this.bSpinner.getValue().toString()));
    }                                     
    private void lSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {                                      
        this.lSlider.setValue(Integer.parseInt(this.lSpinner.getValue().toString()));
    }                                                         
    private javax.swing.JButton Anuluj;
    private javax.swing.JButton OK;
    private javax.swing.JSlider aSlider;
    private javax.swing.JSpinner aSpinner;
    private javax.swing.JSlider bSlider;
    private javax.swing.JSpinner bSpinner;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSlider lSlider;
    private javax.swing.JSpinner lSpinner;                 
}

