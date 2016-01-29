package protas.roman;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import org.jtransforms.fft.DoubleFFT_2D;

public class TFourier extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
	private final BufferedImage image;
    private final BufferedImage RealisImage;
    private final BufferedImage ImaginalisImage;
    private final BufferedImage ModuloImage;
    private final BufferedImage FazaImage;    
    
    public TFourier(BufferedImage image, Main parent) {
        this.image=new BufferedImage(image.getWidth(),image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int x=0; x<image.getWidth();x++){
            for(int y=0; y<image.getHeight(); y++){
                this.image.setRGB(x, y, image.getRGB(x, y));
            }
        }
        RealisImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        ImaginalisImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        ModuloImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        FazaImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        visualize ();
        
        initComponents();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(rootPaneCheckingEnabled);

        
        JlblRealis.setIcon(new ImageIcon(RealisImage));
        JlblImag.setIcon(new ImageIcon(ImaginalisImage));
        JlblModule.setIcon(new ImageIcon(ModuloImage));
        JlblFaza.setIcon(new ImageIcon(FazaImage));
    }
    
    private void visualize (){
    DoubleFFT_2D fft = new DoubleFFT_2D(image.getHeight(), image.getWidth());
    double[][] dataR = new double[image.getHeight()][2 * image.getWidth()];
    double[][] dataG = new double[image.getHeight()][2 * image.getWidth()];
    double[][] dataB = new double[image.getHeight()][2 * image.getWidth()];
    double[][] dFazaR = new double[image.getHeight()][image.getWidth()];
    double[][] dFazaG = new double[image.getHeight()][image.getWidth()];
    double[][] dFazaB = new double[image.getHeight()][image.getWidth()];
    
    Color color;
    Color colorR;
    Color colorI;
    Color colorF;
    
        for (int x = 0; x < image.getHeight(); x++) {
            for (int y = 0; y < image.getWidth(); y++) {
                color=new Color(image.getRGB(x, y));
                dataR[x][2*y]=color.getRed();
                dataR[x][2*y+1]=0;
                dataG[x][2*y]=color.getGreen();
                dataG[x][2*y+1]=0;
                dataB[x][2*y]=color.getBlue();
                dataB[x][2*y+1]=0;
            }
        }    
    fft.complexForward(dataR);
    fft.complexForward(dataG);
    fft.complexForward(dataB);
    
    double RmaxR=0,ImaxR=0;           // max znaczenie dla preskalowania
    double RmaxG=0,ImaxG=0;           // max znaczenie dla preskalowania
    double RmaxB=0,ImaxB=0;           // max znaczenie dla preskalowania
    
    for (int x = 0; x < image.getHeight(); x++) {
            System.out.println();
            for (int y = 0; y < image.getWidth(); y++) {
                dataR[x][2*y]=(Math.abs(dataR[x][2*y]));
                dataR[x][2*y+1]=(Math.abs(dataR[x][2*y+1]));
                dataG[x][2*y]=(Math.abs(dataG[x][2*y]));
                dataG[x][2*y+1]=(Math.abs(dataG[x][2*y+1]));
                dataB[x][2*y]=(Math.abs(dataB[x][2*y]));
                dataB[x][2*y+1]=(Math.abs(dataB[x][2*y+1]));
                
                dFazaR[x][y]=Math.atan(dataR[x][2*y+1]/dataR[x][2*y]);
                dFazaG[x][y]=Math.atan(dataG[x][2*y+1]/dataG[x][2*y]);
                dFazaB[x][y]=Math.atan(dataB[x][2*y+1]/dataB[x][2*y]);
                
                 if (RmaxR<dFazaR[x][y]){
                	 RmaxR=dFazaR[x][y];
                	 }
                 if (RmaxG<dFazaG[x][y]){
                	 RmaxG=dFazaG[x][y];
                	 }
                 if (RmaxB<dFazaB[x][y]){
                	 RmaxB=dFazaB[x][y];
                	 }
            }
        }
         for (int x = 0; x < image.getHeight(); x++) {
            for (int y = 0; y < image.getWidth(); y++) {
                dFazaR[x][y]=(dFazaR[x][y]*255/RmaxR);
                dFazaG[x][y]=(dFazaG[x][y]*255/RmaxG);
                dFazaB[x][y]=(dFazaB[x][y]*255/RmaxB);
               
            }
        }
    
      for (int x = 0; x < image.getHeight(); x++) {     
           
            for (int y = 0; y < image.getWidth(); y++) {
                color=new Color(ImageAlgorithms.clamp((int) ( dFazaR[x][y] ),0,255),
                                ImageAlgorithms.clamp((int) ( dFazaG[x][y] ),0,255),
                                ImageAlgorithms.clamp((int) ( dFazaB[x][y] ),0,255));
                FazaImage.setRGB(x, y, color.getRGB());
            }
        }
    
        for (int x = 0; x < image.getHeight(); x++) {
            System.out.println();
            for (int y = 0; y < image.getWidth(); y++) {
                dataR[x][2*y]=(Math.log(dataR[x][2*y]+1));
                dataR[x][2*y+1]=(Math.log(dataR[x][2*y+1])+1);
                dataG[x][2*y]=(Math.log(dataG[x][2*y])+1);
                dataG[x][2*y+1]=(Math.log(dataG[x][2*y+1])+1);
                dataB[x][2*y]=(Math.log(dataB[x][2*y])+1);
                dataB[x][2*y+1]=(Math.log(dataB[x][2*y+1])+1);
                if (RmaxR<dataR[x][2*y]){RmaxR=dataR[x][2*y];}
                if (ImaxR<dataR[x][2*y+1]){ImaxR=dataR[x][2*y+1];}
                if (RmaxG<dataG[x][2*y]){RmaxG=dataG[x][2*y];}
                if (ImaxG<dataG[x][2*y+1]){ImaxG=dataG[x][2*y+1];}
                if (RmaxB<dataB[x][2*y]){RmaxB=dataB[x][2*y];}
                if (ImaxB<dataB[x][2*y+1]){ImaxB=dataB[x][2*y+1];}            
            }
        }
        for (int x = 0; x < image.getHeight(); x++) {
            System.out.println();
            for (int y = 0; y < image.getWidth(); y++) {
                dataR[x][2*y]=(dataR[x][2*y]*255/RmaxR);
                dataR[x][2*y+1]=(dataR[x][2*y+1]*255/ImaxR);
                dataG[x][2*y]=(dataG[x][2*y]*255/RmaxG);
                dataG[x][2*y+1]=(dataG[x][2*y+1]*255/ImaxG);
                dataB[x][2*y]=(dataB[x][2*y]*255/RmaxB);
                dataB[x][2*y+1]=(dataB[x][2*y+1]*255/ImaxB); 
            }
        } 
        int h2=image.getHeight()/2;
        int w2=image.getWidth();
        double tempR=0;
        double tempG=0;
        double tempB=0;
        
        for (int x=0;x<h2;x++){
            for (int y=0;y<2*image.getWidth();y++){
                tempR=dataR[x][y];
                tempG=dataG[x][y];
                tempB=dataB[x][y];
                
                if (y<w2){
                    dataR[x][y]=dataR[x+h2][y+w2];
                    dataG[x][y]=dataG[x+h2][y+w2];
                    dataB[x][y]=dataB[x+h2][y+w2];
              
                    dataR[x+h2][y+w2]=tempR;
                    dataG[x+h2][y+w2]=tempG;
                    dataB[x+h2][y+w2]=tempB;
                    
                } else {
                    dataR[x][y]=dataR[x+h2][y-w2];
                    dataG[x][y]=dataG[x+h2][y-w2];
                    dataB[x][y]=dataB[x+h2][y-w2];
                    
                    dataR[x+h2][y-w2]=tempR;
                    dataG[x+h2][y-w2]=tempG;
                    dataB[x+h2][y-w2]=tempB;
                }   
            }
        }      
        for (int x = 0; x < RealisImage.getHeight(); x++) {
            for (int y = 0; y < RealisImage.getWidth(); y++) {
                colorR=new Color(ImageAlgorithms.clamp((int)dataR[x][2*y],0,255),
                               + ImageAlgorithms.clamp((int)dataG[x][2*y],0,255),
                               + ImageAlgorithms.clamp((int)dataB[x][2*y],0,255));
                
              RealisImage.setRGB(x, y, colorR.getRGB());
                
                colorI=new Color(ImageAlgorithms.clamp((int)dataR[x][2*y+1],0,255),
                               + ImageAlgorithms.clamp((int)dataG[x][2*y+1],0,255),
                               + ImageAlgorithms.clamp((int)dataB[x][2*y+1],0,255));
                
                ImaginalisImage.setRGB(x, y, colorI.getRGB());
                
                color=new Color( ImageAlgorithms.clamp((int) Math.sqrt( (int)dataR[x][2*y]*(int)dataR[x][2*y]+(int)dataR[x][2*y+1]*(int)dataR[x][2*y+1] ),0,255),
                               + ImageAlgorithms.clamp((int) Math.sqrt( (int)dataG[x][2*y]*(int)dataG[x][2*y]+(int)dataG[x][2*y+1]*(int)dataG[x][2*y+1] ),0,255),
                               + ImageAlgorithms.clamp((int) Math.sqrt( (int)dataB[x][2*y]*(int)dataB[x][2*y]+(int)dataB[x][2*y+1]*(int)dataB[x][2*y+1] ),0,255));
                
                ModuloImage.setRGB(x, y, color.getRGB());
                      
            }
        }   
    }                    
    private void initComponents() {
        jTabs = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        JlblRealis = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        JlblImag = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JlblModule = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JlblFaza = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabs.setName(""); 

        JlblRealis.setText("Realis");
        JlblRealis.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        JlblRealis.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jScrollPane4.setViewportView(JlblRealis);

        jTabs.addTab("Realis", jScrollPane4);

        JlblImag.setText("Imaginalis");
        JlblImag.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        JlblImag.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jScrollPane3.setViewportView(JlblImag);

        jTabs.addTab("Imag", jScrollPane3);

        JlblModule.setText("Spektrum Fouriera");
        JlblModule.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        JlblModule.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jScrollPane2.setViewportView(JlblModule);

        jTabs.addTab("Modulo", jScrollPane2);

        JlblFaza.setText("Faza");
        JlblFaza.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        JlblFaza.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jScrollPane1.setViewportView(JlblFaza);

        jTabs.addTab("Faza", jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jTabs, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jTabs, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }                                         
    private javax.swing.JLabel JlblFaza;
    private javax.swing.JLabel JlblImag;
    private javax.swing.JLabel JlblModule;
    private javax.swing.JLabel JlblRealis;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabs;
                  
}
