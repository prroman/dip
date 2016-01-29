package protas.roman;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class HSL extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
	private BufferedImage originalImage;
    private BufferedImage image;
    private Main parent;
    private float light,sat,hue;
    private float red,green,blue;

    public HSL(BufferedImage image, Main parent) {
        this.parent = parent;
        this.originalImage = image;
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
    private float min(float a, float b, float c){
        float min = a;
        if(b < min)min = b;
        if(c < min)min = c;
        return min;
    }
    private float max(float a, float b, float c){
        float max = a;
        if(b > max)max = b;
        if(c > max)max = c;
        return max;
    }
    private void RGBtoHSL(Color c){
        red = (float)c.getRed()/255;
        green = (float)c.getGreen()/255;
        blue = (float)c.getBlue()/255;
        float max = max(red,green,blue);
        float min = min(red,green,blue);
        float dM = max - min;
        light = (max + min)/2;
        if(light==0 || max==min){
            sat = 0;
        }else{
            if(light>0 && light<=0.5){
                sat = dM/(2*light);
            }else{
                sat = dM/(2-2*light);
            }
        }
        if(max==min){
            hue = 0;
        }else{
            if(max==red && green>=blue){
                hue = 60*(green-blue)/dM;
            }else{
                if(max==red && green<blue){
                    hue = 60*(green-blue)/dM + 360;
                }else{
                    if(max==green){
                        hue = 60 * (blue-red)/dM + 120;
                    }else{
                        hue = 60 * (red-green)/dM + 240;
                    }
                }
            }
        }
    }
    private void HSLtoRGB(){
        if(sat==0){
            red = light;
            green = light;
            blue = light;
        }else{
            float q;
            if(light < 0.5){
                q = light * (1 + sat);
            }else{
                q = light + sat - (light * sat);
            }
            float p = 2 * light - q;
            float h = (float)hue/360;
            float v = (float) (1.0 / 3.0);
            float tRed = h + v;
            float tGreen = h;
            float tBlue = h - v;
            if(tRed < 0)tRed+= 1;
            if(tRed > 1)tRed-= 1;
            if(tGreen < 0)tGreen+= 1;
            if(tGreen > 1)tGreen-=1;
            if(tBlue < 0)tBlue+= 1;
            if(tBlue > 1)tBlue-= 1;
            float v1 = (float) (1.0 / 6.0);
            float v2 = (float) (2.0 / 3.0);
            if(tRed < v1){
                red = p + (q - p) * 6 * tRed;
            }else{
                if(tRed >= v1 && tRed < 0.5){
                    red = q;
                }else{
                    if(tRed >= 0.5 && tRed < v2){
                        red = p + (q - p) * 6 * (v2 - tRed);
                    }else{
                        red=p;
                    }
                }
            }
            if(tGreen < v1){
                green = p + (q - p) * 6 * tGreen;
            }else{
                if(tGreen >= v1 && tGreen <0.5){
                    green = q;
                }else{
                    if(tGreen >= 0.5 && tGreen < v2){
                        green = p +(q - p) * 6 * (v2 - tGreen);
                    }else{
                        green = p;
                    }
                }
            }
            if(tBlue < v1){
                blue = p + (q - p) * 6 * tBlue;
            }else{
                if(tBlue >= v1 && tBlue < 0.5){
                    blue = q;
                }else{
                    if(tBlue >= 0.5 && tBlue < v2){
                        blue = p +(q - p) * 6 * (v2 - tBlue);
                    }else{
                        blue = p;
                    }
                }
            }
        }
    }
    private float checkHSL(float a){
        if(a < 0)return 0;
        if (a > 1)return 1;
        return a;
    }
    private float checkHue(float a,float b){
        return ((a + b)%360);
    }
    private int checkRGB(int a){
        if (a < 0) return 0;
        if (a > 255) return 255;
        return a;
    }
    private void rysuj(){
        for(int x = 0; x < this.image.getWidth(); x++) {
            for(int y = 0; y<this.image.getHeight(); y++) {
                Color color = new Color(this.originalImage.getRGB(x, y));
                RGBtoHSL(color);
                float deltalight = ((float)Integer.parseInt(lightSpinner.getValue().toString()))/100;
                float deltasat = ((float)Integer.parseInt(satSpinner.getValue().toString()))/100;
                int deltahue = Integer.parseInt(hueSpinner.getValue().toString());
                light = checkHSL(light + deltalight);
                sat = checkHSL(sat + deltasat);
                hue=checkHue(hue,deltahue);
                HSLtoRGB();
                color = new Color(checkRGB((int)(255 * red)),checkRGB((int)(255 * green)),checkRGB((int)(255 * blue)));
                this.image.setRGB(x, y, color.getRGB());
            }
        }
        this.getContentPane().repaint();
    }
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        hueSlider = new javax.swing.JSlider(-100,100,0);
        lightSlider = new javax.swing.JSlider(-100,100,0);
        satSlider = new javax.swing.JSlider(-100,100,0);
        OK = new javax.swing.JButton();
        Anuluj = new javax.swing.JButton();
        hueSpinner = new javax.swing.JSpinner();
        satSpinner = new javax.swing.JSpinner();
        lightSpinner = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setSize(image.getWidth(), image.getHeight());

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Hashtable<Integer, JLabel> labelTable1 = new Hashtable<Integer, JLabel>();
        labelTable1.put( new Integer(-100), new JLabel("-100") );
        labelTable1.put( new Integer(0), new JLabel("0") );
        labelTable1.put( new Integer(100), new JLabel("100") );
        hueSlider.setLabelTable( labelTable1 );
        hueSlider.setMajorTickSpacing(100);
        hueSlider.setMinorTickSpacing(10);
        hueSlider.setPaintLabels(true);
        hueSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                hueSliderStateChanged(evt);
            }
        });
        jPanel2.add(hueSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 39, 150, -1));

        Hashtable<Integer, JLabel> labelTable3 = new Hashtable<Integer, JLabel>();
        labelTable3.put( new Integer(-100), new JLabel("-100") );
        labelTable3.put( new Integer(0), new JLabel("0") );
        labelTable3.put( new Integer(100), new JLabel("100") );
        lightSlider.setLabelTable( labelTable3 );
        lightSlider.setMajorTickSpacing(100);
        lightSlider.setMinorTickSpacing(10);
        lightSlider.setPaintLabels(true);
        lightSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                lightSliderStateChanged(evt);
            }
        });
        jPanel2.add(lightSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 150, -1));

        Hashtable<Integer, JLabel> labelTable2 = new Hashtable<Integer, JLabel>();
        labelTable2.put( new Integer(-100), new JLabel("-100") );
        labelTable2.put( new Integer(0), new JLabel("0") );
        labelTable2.put( new Integer(100), new JLabel("100") );
        satSlider.setLabelTable( labelTable2 );
        satSlider.setMajorTickSpacing(100);
        satSlider.setMinorTickSpacing(10);
        satSlider.setPaintLabels(true);
        satSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                satSliderStateChanged(evt);
            }
        });
        jPanel2.add(satSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 150, -1));

        OK.setText("OK");
        OK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKActionPerformed(evt);
            }
        });
        jPanel2.add(OK, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        Anuluj.setText("Anuluj");
        Anuluj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnulujActionPerformed(evt);
            }
        });
        jPanel2.add(Anuluj, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, -1, -1));

        hueSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 360, 1));
        hueSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                hueSpinnerStateChanged(evt);
            }
        });
        jPanel2.add(hueSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 37, -1));

        satSpinner.setModel(new javax.swing.SpinnerNumberModel(0, -100, 100, 1));
        satSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                satSpinnerStateChanged(evt);
            }
        });
        jPanel2.add(satSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 37, -1));

        lightSpinner.setModel(new javax.swing.SpinnerNumberModel(0, -100, 100, 1));
        lightSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                lightSpinnerStateChanged(evt);
            }
        });
        jPanel2.add(lightSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 37, -1));

        jLabel2.setText("Hue:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel3.setText("Saturation:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        jLabel4.setText("Light:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

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
    private void hueSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {                                        
        this.hueSlider.setValue(Integer.parseInt(this.hueSpinner.getValue().toString()));
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
    private void satSliderStateChanged(javax.swing.event.ChangeEvent evt) {                                       
        this.satSpinner.setValue(this.satSlider.getValue());
        rysuj();
    }                                      
    private void lightSliderStateChanged(javax.swing.event.ChangeEvent evt) {                                         
        this.lightSpinner.setValue(this.lightSlider.getValue());
        rysuj();
    }                                        
    private void hueSliderStateChanged(javax.swing.event.ChangeEvent evt) {                                       
        this.hueSpinner.setValue(this.hueSlider.getValue());
        rysuj();
    }                                      
    private void satSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {                                        
        this.satSlider.setValue(Integer.parseInt(this.satSpinner.getValue().toString()));
    }                                       
    private void lightSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {                                          
        this.lightSlider.setValue(Integer.parseInt(this.lightSpinner.getValue().toString()));
    }                                                          
    private javax.swing.JButton Anuluj;
    private javax.swing.JButton OK;
    private javax.swing.JSlider hueSlider;
    private javax.swing.JSpinner hueSpinner;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSlider lightSlider;
    private javax.swing.JSpinner lightSpinner;
    private javax.swing.JSlider satSlider;
    private javax.swing.JSpinner satSpinner;                
}
