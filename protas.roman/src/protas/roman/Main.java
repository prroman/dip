package protas.roman;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Glowna klasa integrujaca wszystki formatki kontrolne i wyswietlajaca obraz
 */
public class Main extends javax.swing.JFrame
{
	private static final long serialVersionUID = 1L;
    public BufferedImage originalImage = null;
    public BufferedImage workImage = null;   
    // Formatka kontrolna z parametrami konkretnej operacji
    private BCG colorEdit = null;
    private Histogram histogram = null;
    private CMYK rgb2cmyk = null;
    private HSL rgb2hsl = null;
    private LAB rgb2lab = null;
    private LUV rgb2luv = null;
    private Splot convolution = null;
    private Gaussian gaussian = null;
    private Unsharp unsharp = null;
    private Median minmaxmed = null;
    private TFourier tfourier = null;
    private Watermark watermark = null;    
    // Komponenty do wyЕ›wietlania obrazu na formatce glownej
    private JLabel imageLabel = null;
    private ImageIcon imageIcon = null;

    public Main()
    {
        setTitle("POC - Imie Nazwisko");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);        
        initComponents();
    }
        
    // Metoda przywracajÄ…ca oryginalny obraz do obrazu roboczego
    public void revertImage()
    {
        try
        {
            originalImage.copyData(workImage.getRaster());
            imageIcon.setImage(workImage);
            imageLabel.setIcon(imageIcon);
            imageLabel.repaint();
        } catch (Exception e) {}
    }

    // Metoda aktualizujaca oryginalny obraz danymi z obrazu roboczego
    public void updateImage()
    {
        try
        {
            workImage.copyData(originalImage.getRaster());
            imageIcon.setImage(workImage);
            imageLabel.setIcon(imageIcon);
        } catch (Exception e) {}
    }

    // Metoda wczytujaca obraz z pliku i tworzaca na jego podstawie obraz oryginalny i jego kopie robocza
    public void readImage(String fn)
    {
        try
        {
            // Wczytanie obrazu z pliku
            BufferedImage loadImage = ImageIO.read(new File(fn));
            
            // Oryginalny obraz tworzony na podstawie wczytanego z ewentualna konwersja obrazu do formatu 32 bit RGB
            originalImage = new BufferedImage(loadImage.getWidth(), loadImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            originalImage.getGraphics().drawImage(loadImage, 0, 0, null);            
            
            // Roboczy obraz tworzony jako kopia oryginalnego
            workImage = new BufferedImage(loadImage.getWidth(), loadImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            originalImage.copyData(workImage.getRaster());

            // Wyswietlenie roboczego obrazu na formatce
            imageIcon = new ImageIcon(workImage);
            imageLabel.setIcon(imageIcon);
            
        } catch (Exception e)
        {
            System.out.println("Image read error: " + e.getMessage());
        }
    }



    private void initComponents()
    {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        JMenuItem mitem = new JMenuItem("Open");
        mitem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File file = fc.getSelectedFile();
                    String fname = file.getPath();
                    readImage(fname);
                    repaint();
                }
            }
        });
        menu.add(mitem);
        menu.addSeparator();
        mitem = new JMenuItem("Exit");
        mitem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                System.exit(0);
            }
        });
        menu.add(mitem);
        menu = new JMenu("Edit");
        menuBar.add(menu);
        mitem = new JMenuItem("Color Edit");
        mitem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                colorEdit = new BCG(Main.this);
                colorEdit.pack();
                colorEdit.setVisible(true);
            }
        });
        menu.add(mitem);     
        mitem = new JMenuItem("Histogram Equalization");
        mitem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                histogram = new Histogram(originalImage, Main.this);
                histogram.pack();
                histogram.setVisible(true);
            }
        });
        menu.add(mitem);     
        menu = new JMenu("Convert to ...");
        menuBar.add(menu); 
        mitem = new JMenuItem("CMYK");
        mitem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                rgb2cmyk = new CMYK(workImage ,Main.this);
                rgb2cmyk.pack();
                rgb2cmyk.setVisible(true);
            }
            
        });
        menu.add(mitem);
        
        mitem = new JMenuItem("HSL");
        mitem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                rgb2hsl = new HSL(workImage, Main.this);
                rgb2hsl.pack();
                rgb2hsl.setVisible(true);
            }
        });
        menu.add(mitem);
        
        mitem = new JMenuItem("LAB");
        mitem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                rgb2lab = new LAB(workImage, Main.this);
                rgb2lab.pack();
                rgb2lab.setVisible(true);
            }
        });
        menu.add(mitem);
        
        mitem = new JMenuItem("LUV");
        mitem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                rgb2luv = new LUV(workImage, Main.this);
                rgb2luv.pack();
                rgb2luv.setVisible(true);
            }
        });
        menu.add(mitem);
        menu = new JMenu("Filters");
        menuBar.add(menu); 
        mitem = new JMenuItem("Convolution");
        mitem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                convolution = new Splot(workImage, Main.this);
                convolution.pack();
                convolution.setVisible(true);
            }
            
        });
        menu.add(mitem);
        mitem = new JMenuItem("Gaussian Blur");
        mitem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                gaussian = new Gaussian(Main.this);
                gaussian.pack();
                gaussian.setVisible(true);
            }
            
        });
        menu.add(mitem);
        mitem = new JMenuItem("Unsharp Mask");
        mitem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                unsharp = new Unsharp(workImage ,Main.this);
                unsharp.pack();
                unsharp.setVisible(true);
            }
            
        });
        menu.add(mitem);
        mitem = new JMenuItem("Min/Max/Med");
        mitem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                minmaxmed = new Median(workImage ,Main.this);
                minmaxmed.pack();
                minmaxmed.setVisible(true);
            }
            
        });
        menu.add(mitem);
        menu = new JMenu("Tranformation");
        menuBar.add(menu); 
        mitem = new JMenuItem("TFourier");
        mitem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                tfourier = new TFourier(workImage, Main.this);
                tfourier.pack();
                tfourier.setVisible(true);
            }
            
        });
        menu.add(mitem);
        mitem = new JMenuItem("Watermark");
        mitem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                watermark = new Watermark(workImage, Main.this);
                watermark.pack();
                watermark.setVisible(true);
            }
            
        });
        menu.add(mitem);
                
        imageLabel = new JLabel();                
        add(new JScrollPane(imageLabel));
    }

    public static void main(String[] args)
    {
        Main main = new Main();
        main.setVisible(true);
        main.setSize(800, 600);
        
        //main.setExtendedState(Frame.MAXIMIZED_BOTH);               
    }
}
