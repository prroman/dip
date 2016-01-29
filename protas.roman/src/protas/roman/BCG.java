package protas.roman;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JSlider;

	public class BCG extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
	Main parent = null;

    public BCG(Main parent) {
    	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.parent = parent;
        initComponents();
    }
    void processImage(BufferedImage src, BufferedImage dst, int db) {
        DataBufferInt sbuff = (DataBufferInt) src.getRaster().getDataBuffer();
        DataBufferInt dbuff = (DataBufferInt) dst.getRaster().getDataBuffer();
        int[] sp = sbuff.getData();
        int[] dp = dbuff.getData();       
        int i = 0;
        for (int y = 0; y < src.getHeight(); ++y) {
            for (int x = 0; x < src.getWidth(); ++x) {
                dp[i] = ImageAlgorithms.changeBrightness(sp[i], db);
                i++;
            }
        }
    }
    public static int [] gammaTable = new int [256];
    void processImageGam(BufferedImage src, BufferedImage dst, double db) {
        DataBufferInt sbuff = (DataBufferInt) src.getRaster().getDataBuffer();
        DataBufferInt dbuff = (DataBufferInt) dst.getRaster().getDataBuffer();
        int[] sp = sbuff.getData();
        int[] dp = dbuff.getData();
        for (int i = 0; i < 256; i++) {   
            gammaTable[i] = (int)(Math.pow(i / 255.0, db) * 255 + 0.5);        
         }
        int i = 0;
        for (int y = 0; y < src.getHeight(); ++y) {
            for (int x = 0; x < src.getWidth(); ++x) {
                dp[i] = ImageAlgorithms.changeGamma(sp[i]);
                i++;
                }
            }
        }   
    public static int [] contrastTable = new int [256];
    public int contrast (int wartosc, byte gama) {
         if (gama<0){
             wartosc = (int)((127.0 + gama)/127.0 * wartosc - gama); //zmniejszenia kontrastu
         } 
         else {
            wartosc = (int)(127/(127.0 - gama) * (wartosc - gama));
             }
        return wartosc ;
        }
    void processImageContrast(BufferedImage src, BufferedImage dst, byte dc) {
        DataBufferInt sbuff = (DataBufferInt) src.getRaster().getDataBuffer();
        DataBufferInt dbuff = (DataBufferInt) dst.getRaster().getDataBuffer();
        int[] sp = sbuff.getData();
        int[] dp = dbuff.getData();       
        for (int i = 0; i < 256; i++) {   
            contrastTable[i] = contrast(i,dc);        
            }
        int i = 0;
        for (int y = 0; y < src.getHeight(); ++y) {
            for (int x = 0; x < src.getWidth(); ++x) {
            	dp[i] = ImageAlgorithms.changeContrast(sp[i]);
                i++;
            }
        }
    } 
    private void initComponents() {

        brightnessSlider = new javax.swing.JSlider();
        vSpiner = new javax.swing.JSpinner();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        GammaSlider = new javax.swing.JSlider();
        ContrastSlider = new javax.swing.JSlider();
        GamaSpiner = new javax.swing.JSpinner();
        ContrastSpiner = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Color Equalizer");
        setMinimumSize(new java.awt.Dimension(500, 250));
        setPreferredSize(new java.awt.Dimension(480, 100));

        brightnessSlider.setMaximum(255);
        brightnessSlider.setMinimum(-255);
        brightnessSlider.setValue(0);
        brightnessSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                brightnessSliderStateChanged(evt);
            }
        });

        cancelButton.setText("Anuluj");
        cancelButton.setPreferredSize(new java.awt.Dimension(125, 25));
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelButtonMouseClicked(evt);
            }
        });

        okButton.setText("Ok");
        okButton.setPreferredSize(new java.awt.Dimension(125, 25));
        okButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                okButtonMouseClicked(evt);
            }
        });

        GammaSlider.setMaximum(200);
        GammaSlider.setMinorTickSpacing(10);
        GammaSlider.setSnapToTicks(true);
        GammaSlider.setValue(100);
        GammaSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                GammaSliderStateChanged(evt);
            }
        });

        ContrastSlider.setMaximum(127);
        ContrastSlider.setMinimum(-127);
        ContrastSlider.setSnapToTicks(true);
        ContrastSlider.setToolTipText("");
        ContrastSlider.setValue(0);
        ContrastSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ContrastSliderStateChanged(evt);
            }
        });

        jLabel1.setText("Brightness...");
        jLabel2.setText("Contrast...");
        jLabel3.setText("Gamma...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(brightnessSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(vSpiner, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ContrastSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(GammaSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(GamaSpiner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ContrastSpiner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(brightnessSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vSpiner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                		.addComponent(ContrastSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ContrastSpiner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                		.addComponent(GamaSpiner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(GammaSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        pack();
    }
    private void brightnessSliderStateChanged(javax.swing.event.ChangeEvent evt) {
        JSlider slider = (JSlider) evt.getSource();
        vSpiner.setValue(slider.getValue());
        try
        {
            processImage(parent.originalImage, parent.workImage, slider.getValue());
            parent.repaint();
        } 
        catch (Exception e)
        {
            System.out.println("brightness error: " + e.getMessage());
        }
    }
    private void cancelButtonMouseClicked(java.awt.event.MouseEvent evt) {
        parent.revertImage();
        setVisible(false);
        dispose();
    }
    private void okButtonMouseClicked(java.awt.event.MouseEvent evt) {
        parent.updateImage();
        setVisible(false);
        dispose();
    }
    double roundResult (double d, int precise) {
    precise = 10^precise;
    d = d*precise;
    int i = (int) Math.round(d);
    return (double) i/precise;
    }
    private void GammaSliderStateChanged(javax.swing.event.ChangeEvent evt) {
         JSlider slider = (JSlider) evt.getSource();
        if  (slider.getValue()<100) {
             GamaSpiner.setValue(slider.getValue()/100.0);
        }else {
             GamaSpiner.setValue((((slider.getValue()-100)/10.0)));
        }      
         try
        {
            processImageGam(parent.originalImage, parent.workImage, (GammaSlider.getValue()/100.0) );
            parent.repaint();
        } 
        catch (Exception e)
        {
            System.out.println("gama error: " + e.getMessage());
        }
    }
    private void ContrastSliderStateChanged(javax.swing.event.ChangeEvent evt) {
        
        JSlider slider = (JSlider) evt.getSource();
        ContrastSpiner.setValue(slider.getValue());
         try
        {
            processImageContrast(parent.originalImage, parent.workImage, (byte)slider.getValue());
            parent.repaint();
        } 
        catch (Exception e)
        {
            System.out.println("brightness error: " + e.getMessage());
        }   
    }
    private javax.swing.JSlider ContrastSlider;
    private javax.swing.JSpinner ContrastSpiner;
    private javax.swing.JSpinner GamaSpiner;
    private javax.swing.JSlider GammaSlider;
    private javax.swing.JSlider brightnessSlider;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton okButton;
    private javax.swing.JSpinner vSpiner;
}
