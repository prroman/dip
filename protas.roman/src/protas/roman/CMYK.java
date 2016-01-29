package protas.roman;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CMYK extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
	private BufferedImage originalImage;
    private BufferedImage image;
    private Main parent;

    public CMYK(BufferedImage image, Main parent) {
        this.parent = parent;
        this.originalImage = image;
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
    private float getBlack(float a, float b, float c){
        float bl = a;
        if (bl>b) bl = b;
        if (bl>c) bl = c;
        return bl;
    }
    private int checkRGB(int a){
        if (a < 0) return 0;
        if (a > 255) return 255;
        return a;
    }
    private float CMYKtoRGB(float cmykVal, float black){
        float r = cmykVal*(1 - black) + black;
        return 1 - r;
    }
    private float RGBtoCMYK(float rgb, float black){
        return (rgb - black)/(1 - black);
    }
    private void rysuj(){
        Color color;
        for(int x = 0;x < originalImage.getWidth(); x++) {
            for(int y = 0; y < originalImage.getHeight(); y++) {
                color = new Color(originalImage.getRGB(x, y));
                float red = (float) color.getRed() / 255;
                float green = (float) color.getGreen() / 255;
                float blue = (float) color.getBlue() / 255;
                float cyan = 1 - red;
                float magenta = 1 - green;
                float yellow = 1 - blue;
                float black = getBlack(cyan,magenta,yellow);
                cyan = (RGBtoCMYK(cyan,black));
                magenta = (RGBtoCMYK(magenta,black));
                yellow = (RGBtoCMYK(yellow,black));
                float deltaC = ((Float.parseFloat(this.cyanSpinner.getValue().toString())) / 255);
                float deltaM = ((Float.parseFloat(this.magentaSpinner.getValue().toString())) / 255);
                float deltaY = ((Float.parseFloat(this.yellowSpinner.getValue().toString())) / 255);
                float deltaK = ((Float.parseFloat(this.blackSpinner.getValue().toString())) / 255);
                cyan = (cyan + deltaC);
                magenta = (magenta + deltaM);
                yellow = (yellow + deltaY);
                black = (black + deltaK);
                int r = checkRGB((int)(255 * CMYKtoRGB(cyan,black)));
                int g = checkRGB((int)(255 * CMYKtoRGB(magenta,black)));
                int b = checkRGB((int)(255*CMYKtoRGB(yellow,black)));
                color = new Color(r,g,b);
                image.setRGB(x, y, color.getRGB());
            }
        }
        this.getContentPane().repaint();
    }                        
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cyanSlider = new javax.swing.JSlider(-255,255,0);
        cyanLabel = new javax.swing.JLabel();
        yellowLabel = new javax.swing.JLabel();
        yellowSlider = new javax.swing.JSlider(-255,255,0);
        magentaLabel = new javax.swing.JLabel();
        magentaSlider = new javax.swing.JSlider(-255,255,0);
        OK = new javax.swing.JButton();
        Anuluj = new javax.swing.JButton();
        cyanSpinner = new javax.swing.JSpinner();
        blackLabel = new javax.swing.JLabel();
        blackSlider = new javax.swing.JSlider(-255,255,0);
        magentaSpinner = new javax.swing.JSpinner();
        yellowSpinner = new javax.swing.JSpinner();
        blackSpinner = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setSize(image.getWidth(), image.getHeight());
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Hashtable<Integer, JLabel> labelTable1 = new Hashtable<Integer, JLabel>();
        labelTable1.put( new Integer(-255), new JLabel("-255") );
        labelTable1.put( new Integer(0), new JLabel("0") );
        labelTable1.put( new Integer(255), new JLabel("255") );
        cyanSlider.setLabelTable( labelTable1 );
        cyanSlider.setMajorTickSpacing(255);
        cyanSlider.setMinorTickSpacing(15);
        cyanSlider.setPaintLabels(true);
        cyanSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cyanSliderStateChanged(evt);
            }
        });
        jPanel2.add(cyanSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 150, -1));

        cyanLabel.setText("Cyan:");
        jPanel2.add(cyanLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 16, -1, -1));

        yellowLabel.setText("Yellow:");
        jPanel2.add(yellowLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        Hashtable<Integer, JLabel> labelTable3 = new Hashtable<Integer, JLabel>();
        labelTable3.put( new Integer(-255), new JLabel("-255") );
        labelTable3.put( new Integer(0), new JLabel("0") );
        labelTable3.put( new Integer(255), new JLabel("255") );
        yellowSlider.setLabelTable( labelTable3 );
        yellowSlider.setMajorTickSpacing(255);
        yellowSlider.setMinorTickSpacing(15);
        yellowSlider.setPaintLabels(true);
        yellowSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                yellowSliderStateChanged(evt);
            }
        });
        jPanel2.add(yellowSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 150, -1));

        magentaLabel.setText("Magenta:");
        jPanel2.add(magentaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        Hashtable<Integer, JLabel> labelTable2 = new Hashtable<Integer, JLabel>();
        labelTable2.put( new Integer(-255), new JLabel("-255") );
        labelTable2.put( new Integer(0), new JLabel("0") );
        labelTable2.put( new Integer(255), new JLabel("255") );
        magentaSlider.setLabelTable( labelTable2 );
        magentaSlider.setMajorTickSpacing(255);
        magentaSlider.setMinorTickSpacing(15);
        magentaSlider.setPaintLabels(true);
        magentaSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                magentaSliderStateChanged(evt);
            }
        });
        jPanel2.add(magentaSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 150, -1));

        OK.setText("OK");
        OK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKActionPerformed(evt);
            }
        });
        jPanel2.add(OK, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, -1, -1));

        Anuluj.setText("Anuluj");
        Anuluj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnulujActionPerformed(evt);
            }
        });
        jPanel2.add(Anuluj, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, -1, -1));

        cyanSpinner.setModel(new javax.swing.SpinnerNumberModel(0, -255, 255, 1));
        cyanSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cyanSpinnerStateChanged(evt);
            }
        });
        jPanel2.add(cyanSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 37, -1));

        blackLabel.setText("Black:");
        jPanel2.add(blackLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, 20));

        Hashtable<Integer, JLabel> labelTable4 = new Hashtable<Integer, JLabel>();
        labelTable4.put( new Integer(-255), new JLabel("-255") );
        labelTable4.put( new Integer(0), new JLabel("0") );
        labelTable4.put( new Integer(255), new JLabel("255") );
        blackSlider.setLabelTable( labelTable4 );
        blackSlider.setMajorTickSpacing(255);
        blackSlider.setMinorTickSpacing(15);
        blackSlider.setPaintLabels(true);
        blackSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                blackSliderStateChanged(evt);
            }
        });
        jPanel2.add(blackSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 150, -1));

        magentaSpinner.setModel(new javax.swing.SpinnerNumberModel(0, -255, 255, 1));
        magentaSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                magentaSpinnerStateChanged(evt);
            }
        });
        jPanel2.add(magentaSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 37, -1));

        yellowSpinner.setModel(new javax.swing.SpinnerNumberModel(0, -255, 255, 1));
        yellowSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                yellowSpinnerStateChanged(evt);
            }
        });
        jPanel2.add(yellowSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 37, 20));

        blackSpinner.setModel(new javax.swing.SpinnerNumberModel(0, -255, 255, 1));
        blackSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                blackSpinnerStateChanged(evt);
            }
        });
        jPanel2.add(blackSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 220, 37, -1));

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
    }// </editor-fold>                        

    private void blackSliderStateChanged(javax.swing.event.ChangeEvent evt) {                                         
        this.blackSpinner.setValue(this.blackSlider.getValue());
        rysuj();
}                                        

    private void cyanSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {                                         
        this.cyanSlider.setValue(Integer.parseInt(this.cyanSpinner.getValue().toString()));
}                                        

    private void AnulujActionPerformed(java.awt.event.ActionEvent evt) {                                       
        this.dispose();
}                                      

    private void OKActionPerformed(java.awt.event.ActionEvent evt) {                                   
        for(int x=0; x<this.image.getWidth();x++){
            for(int y=0; y<this.image.getHeight(); y++){
                this.originalImage.setRGB(x, y, this.image.getRGB(x, y));
            }
        }
        this.parent.repaint();
        this.dispose();
}                                  

    private void magentaSliderStateChanged(javax.swing.event.ChangeEvent evt) {                                           
        this.magentaSpinner.setValue(this.magentaSlider.getValue());
        rysuj();
}                                          

    private void yellowSliderStateChanged(javax.swing.event.ChangeEvent evt) {                                          
        this.yellowSpinner.setValue(this.yellowSlider.getValue());
        rysuj();
}                                         

    private void cyanSliderStateChanged(javax.swing.event.ChangeEvent evt) {                                        
        this.cyanSpinner.setValue(this.cyanSlider.getValue());
        rysuj();
}                                       

    private void magentaSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {                                            
        this.magentaSlider.setValue(Integer.parseInt(this.magentaSpinner.getValue().toString()));
    }                                           

    private void yellowSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {                                           
        this.yellowSlider.setValue(Integer.parseInt(this.yellowSpinner.getValue().toString()));
    }                                          

    private void blackSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {                                          
        this.blackSlider.setValue(Integer.parseInt(this.blackSpinner.getValue().toString()));
    }                                         
                   
    private javax.swing.JButton Anuluj;
    private javax.swing.JButton OK;
    private javax.swing.JLabel blackLabel;
    private javax.swing.JSlider blackSlider;
    private javax.swing.JSpinner blackSpinner;
    private javax.swing.JLabel cyanLabel;
    private javax.swing.JSlider cyanSlider;
    private javax.swing.JSpinner cyanSpinner;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel magentaLabel;
    private javax.swing.JSlider magentaSlider;
    private javax.swing.JSpinner magentaSpinner;
    private javax.swing.JLabel yellowLabel;
    private javax.swing.JSlider yellowSlider;
    private javax.swing.JSpinner yellowSpinner;                 
}
