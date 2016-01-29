package protas.roman;

import static protas.roman.Gauss.rozmiarMaski;
import static protas.roman.Gauss.secGauss;
import static protas.roman.Gauss.liczG;
import static protas.roman.Gauss.wzmocnienie;
import protas.roman.Sortowanie;

public class ImageAlgorithms {
	public static int jrgb(int r, int g, int b) { return (r << 16) + (g << 8) + b; }
    public static int jred(int rgb)   { return ((rgb >> 16) & 0xff); }
    public static int jgreen(int rgb) { return ((rgb >> 8) & 0xff); }
    public static int jblue(int rgb)  { return (rgb & 0xff); }

    public static int clamp(int v, int min, int max) {
        if (v <= min) return min;
        if (v >= max) return max;
        return v;
    }
    // Funkcja zmieniajaca jasnosc koloru rgb o wartosc db
    public static int changeBrightness(int rgb, int db) {
        // Rozbior koloru na skladowe R, G, B
        int r = jred(rgb);
        int g = jgreen(rgb);
        int b = jblue(rgb);
        // Modyfikacja skladowych razem z przypilnowaniem zakresow 0-255
        r = clamp(r + db, 0, 255);
        g = clamp(g + db, 0, 255);
        b = clamp(b + db, 0, 255);
        // Zlozenie skladowych R, G, B w jeden kolor i zwrocenie jego wartosci
        return jrgb(r, g, b);        
    }
    public static int changeGamma(int rgb) {
    	// Rozbior koloru na skladowe R, G, B
        int r = jred(rgb);
        int g = jgreen(rgb);
        int b = jblue(rgb);
        // Modyfikacja skladowych razem z przypilnowaniem zakresow 0-255
        r = clamp(BCG.gammaTable[r], 0, 255);        
        g = clamp(BCG.gammaTable[g], 0, 255);
        b = clamp(BCG.gammaTable[b], 0, 255);
        // Zlozenie skladowych R, G, B w jeden kolor i zwrocenie jego wartosci
        return jrgb(r, g, b);    
    } 
    public static int changeContrast(int rgb) {
        int r = jred(rgb);
        int g = jgreen(rgb);
        int b = jblue(rgb);
        r = clamp(BCG.contrastTable[r], 0, 255);        
        g = clamp(BCG.contrastTable[g], 0, 255);
        b = clamp(BCG.contrastTable[b], 0, 255);
        return jrgb(r, g, b);         
    } 
    public static int Gauss (int rgb, int[] SPixelG){
      // Rozbior koloru na skladowe R, G, B
     int r = 0;
     int g = 0;
     int b = 0;
     int r1 = 0;
     int g1 = 0;
     int b1 = 0;     
     int i=0;
     int rgbt =0;
     for (int v = 0; v < rozmiarMaski; v++) {              
      i++;
      rgb = ((SPixelG[v]));
      r1 = r1 + (jred(rgb)*((secGauss[v])));
      g1 = g1 + (jgreen(rgb)*((secGauss[v])));
      b1 = b1 + (jblue(rgb))*((secGauss[v]));                                 
          }  
     if (liczG==0)liczG = 1; 
     	r = (int)(r1/liczG);
     	g = (int)(g1/liczG);
     	b = (int)(b1/liczG); 

     	r = clamp(r , 0, 255);
     	g = clamp(g , 0, 255);
     	b = clamp(b , 0, 255);
     
      return jrgb(r, g, b);        
  }
    public static int Unsharp(int rgb, int rgb1) {      
      // Rozbior koloru na skladowe R, G, B
      int r = jred(rgb)+(jred(rgb)-jred(rgb1)) * wzmocnienie;
      int g = jgreen(rgb)+(jgreen(rgb)-jgreen(rgb1)) * wzmocnienie;
      int b = jblue(rgb)+(jblue(rgb)-jblue(rgb1)) * wzmocnienie;
      // Modyfikacja skladowych razem z przypilnowaniem zakresow 0-255
      r = clamp(r, 0, 255);
      g = clamp(g, 0, 255);
      b = clamp(b, 0, 255);  
      // Zlozenie skladowych R, G, B w jeden kolor i zwrocenie jego wartosci
      return jrgb(r, g, b);        
  }   
    public static void WaterMarking (double [][] outPut, double [] w, double alfa, double [][] plik) {
        int height = outPut.length, weight = outPut[0].length; int K = w.length;
        double [][] outSort = new double [height * weight][4];      
        for (int x = 0; x < height; x++) {
           for (int y = 0; y < weight; y++) {
               outSort[x*weight + y][0] = x;                // x
               outSort[x*weight + y][1] = y;                // y
               outSort[x*weight + y][2] = outPut[x][y];     // c
               outSort[x*weight + y][3] = 0;                // w
           }
        }
        Sortowanie sorter = new Sortowanie();
        sorter.sort(outSort);    
        int s = outSort.length - K;
        for (int i = 0; i < plik.length; i++) {
        	plik [i][0] = outSort[s + i][0] ;
        	plik [i][1] = outSort[s + i][1] ;
        	plik [i][2] = outSort[s + i][2] ;
        	plik [i][3] = w[i];
        	outPut[ (int)plik[i][0] ] [ (int)plik[i][1] ] =  plik[i][2] * (1 + alfa*plik[i][3]);
        	System.out.println("OutSort rnum "+ i + "x = "+ plik[i][0]+" y = "+plik[i][1]+" c = "+plik[i][2]+" w = "+plik[i][3]);
        }
    }
}
