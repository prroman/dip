package protas.roman;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

public class Splot extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
	private BufferedImage originalImage;
    private BufferedImage image;
    JScrollPane jscrlp;
    private Object[][] data = {{1}};

    public Splot(BufferedImage image, Main parent) {
        this.originalImage = image;
        this.image = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                this.image.setRGB(x, y, image.getRGB(x, y));
            }
        }
        initComponents();
    }                  
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKernel = new javax.swing.JTable();
        SizeSpinner = new javax.swing.JSpinner();
        SizeSlider = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        ValueSpinner = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldGaus = new javax.swing.JTextField();

        setTitle("Convolution");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        this.jLabel3.setIcon(new ImageIcon(image));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton1.setText("Apply");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tblKernel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null}
            },
            new String [] {
                "", ""
            }
        ));
        jScrollPane2.setViewportView(tblKernel);

        SizeSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SizeSpinnerStateChanged(evt);
            }
        });
        SizeSlider.setMajorTickSpacing(3);
        SizeSlider.setMaximum(30);
        SizeSlider.setMinimum(1);
        SizeSlider.setToolTipText("");
        SizeSlider.setValue(3);
        SizeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SizeSliderStateChanged(evt);
            }
        });

        jLabel1.setText("Value :");
        ValueSpinner.setRequestFocusEnabled(false);
        ValueSpinner.setVerifyInputWhenFocusTarget(false);
        jLabel2.setText("Radius");
        jTextFieldGaus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldGausKeyPressed(evt);
            }
        });
        
        JLabel lblSigma = new JLabel();
        lblSigma.setText("Promie\u0144 maski :");
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(19)
        			.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createParallelGroup(Alignment.LEADING)
        					.addGroup(layout.createSequentialGroup()
        						.addGap(4)
        						.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        							.addGroup(layout.createSequentialGroup()
        								.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        									.addGroup(layout.createSequentialGroup()
        										.addComponent(jLabel2)
        										.addGap(168))
        									.addGroup(layout.createSequentialGroup()
        										.addComponent(lblSigma)
        										.addPreferredGap(ComponentPlacement.RELATED)
        										.addComponent(jTextFieldGaus, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
        										.addGap(56)
        										.addComponent(jLabel1)))
        								.addGap(21))
        							.addGroup(layout.createSequentialGroup()
        								.addComponent(SizeSlider, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        								.addPreferredGap(ComponentPlacement.RELATED)))
        						.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        							.addComponent(ValueSpinner, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
        							.addComponent(SizeSpinner, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)))
        					.addGroup(layout.createSequentialGroup()
        						.addPreferredGap(ComponentPlacement.RELATED)
        						.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        				.addComponent(jButton1))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE))
        				.addGroup(layout.createSequentialGroup()
        					.addGap(14)
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(jLabel2)
        							.addGap(11)
        							.addComponent(SizeSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        						.addComponent(SizeSpinner, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
        					.addGap(15)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(jLabel1)
        						.addComponent(ValueSpinner, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
        						.addComponent(lblSigma)
        						.addComponent(jTextFieldGaus, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(jButton1)
        					.addGap(41)
        					.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)))
        			.addGap(25))
        );
        getContentPane().setLayout(layout);

        pack();
    }
    private void SizeSliderStateChanged(javax.swing.event.ChangeEvent evt) {                                        
        if (SizeSlider.getValue() % 2 == 0) {
            SizeSlider.setValue(SizeSlider.getValue() + 1);
        }
        SizeSpinner.setValue(SizeSlider.getValue());
        data = null;
        data = new Object[SizeSlider.getValue()][SizeSlider.getValue()];
        float sigma = 0;
        if(jTextFieldGaus.getText().isEmpty()==false){
            try {
                sigma = Float.parseFloat(jTextFieldGaus.getText());
            }catch(Exception e){
                jTextFieldGaus.setText("Input floatf(0.00) or clear");
                return;
            }
            int R2 = data.length/2;
            float coeff = (float) (1.0/(2.0 * (float)Math.PI * sigma * sigma));
            int MinElem = (int)Math.log10(coeff * (float)Math.exp(-(Math.pow( 0 - R2, 2)+ Math.pow(0 - R2, 2))/(2 * sigma * sigma))); // data[0][0] - jest min elem 
            if (MinElem > 0) { MinElem = (int)Math.pow(10,0 - MinElem - 4);                
            }else { MinElem = (int)Math.pow(10,Math.abs(MinElem) + 4);                
            }
            System.out.println("data[0][0] = "+  + MinElem);
           
            for (int i = 0; i < data.length; i++) {            
                for (int j = 0; j < data.length; j++) {
                    double fGaus = (coeff * (float)Math.exp(-(Math.pow( i - R2, 2)+ Math.pow(j - R2, 2))/(2 * sigma * sigma)) );
                    data[i][j] = (int)(fGaus * MinElem);
                }
            }
        }else{
            for (int i = 0; i < data.length; i++) {              
                for (int j = 0; j < data.length; j++) {
                    data[i][j] = Integer.parseInt(this.ValueSpinner.getValue().toString());
                }
            }
        }   
        TableModel dataModel = new AbstractTableModel() {
			private static final long serialVersionUID = 1L;
			private Set<TableModelListener> listeners1 = new HashSet<TableModelListener>();
            public void addTableModelListener(TableModelListener listener) {
                listeners1.add(listener);
            }
            public int getColumnCount() {
                return data.length;
            }
            public int getRowCount() {
                return data.length;
            }
            public Object getValueAt(int row, int col) {
                return data[row][col];
            }
            public boolean isCellEditable(int row, int column) {
                System.out.println("OK");
                return true;
            }
            public void removeTableModelListener(TableModelListener listener) {
                listeners1.remove(listener);
            }
            public void setValueAt(Object value, int rowIndex, int columnIndex) {
                try{
                    data[rowIndex][columnIndex] = Integer.parseInt(value.toString());
                }catch (Exception e){ }
            }
        };
        tblKernel.setModel(dataModel);

    }                                       
    private void SizeSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {                                         
    }                                        
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        System.out.println(data.length/2 );
        int R = data.length/2;
        this.image = new BufferedImage(originalImage.getWidth() + data.length - 1, originalImage.getHeight() + data.length - 1, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                this.image.setRGB(x + R, y + R, originalImage.getRGB(x, y));
            }
        }
        this.jLabel3.setIcon(new ImageIcon(Convolution(data,originalImage)));
    }  
    private void jTextFieldGausKeyPressed(java.awt.event.KeyEvent evt) {                                          
        SizeSlider.setValue(SizeSlider.getValue() + 0);
    }                                         
    public static BufferedImage Convolution(Object[][] data, BufferedImage OrigImg){
        int R = data.length/2;
        int [][] dataInt = new int [data.length][data.length];
        BufferedImage WorkImage = new BufferedImage(OrigImg.getWidth() + data.length - 1, OrigImg.getHeight() + data.length - 1, BufferedImage.TYPE_INT_RGB);
        BufferedImage TemIimage = new BufferedImage(OrigImg.getWidth(), OrigImg.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < OrigImg.getWidth(); x++) {
            for (int y = 0; y < OrigImg.getHeight(); y++) {
                WorkImage.setRGB(x+R, y+R, OrigImg.getRGB(x, y));
            }
        } // skopijovali originalny obraz w srodek rob obrazu WorkImage zwiekszonego o rozmiar maski-1
        int KernelWeight = 0;
        for (int x = 0; x < data.length; x++){
            for (int y = 0; y < data.length; y++){
                dataInt [x][y] = Integer.parseInt(data[x][y].toString());
                KernelWeight = KernelWeight + dataInt [x][y];
            }
        }
        if(KernelWeight==0){
        	KernelWeight = 1;
        	}
        System.out.println("K = "+ KernelWeight);
        Color color;
        for (int x = R; x < WorkImage.getWidth()-(int)R; x++) {
            for (int y = R; y < WorkImage.getHeight()-R; y++) {    
                float red = 0; //0
                float green = 0;
                float blue = 0;
                for (int k = 0; k < data.length; k++) {
                    for (int j = 0; j < data.length; j++) {
                    	color = new Color(WorkImage.getRGB(x + k - R, y + j - R));
                        red = red + color.getRed() * dataInt[k][j];
                        green = green + color.getGreen() * dataInt[k][j];
                        blue = blue + color.getBlue() * dataInt[k][j];
                    }
                }
                int redI = ImageAlgorithms.clamp((int) (red/KernelWeight), 0, 255);
                int greenI = ImageAlgorithms.clamp((int) (green/KernelWeight), 0, 255);
                int blueI = ImageAlgorithms.clamp((int) (blue/KernelWeight), 0, 255);
                color = new Color(redI,greenI,blueI);
                TemIimage.setRGB(x - R, y - R, color.getRGB());
            }
        }
        return TemIimage;  
    }             
    private javax.swing.JSlider SizeSlider;
    private javax.swing.JSpinner SizeSpinner;
    private javax.swing.JSpinner ValueSpinner;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextFieldGaus;
    private javax.swing.JTable tblKernel;
}
