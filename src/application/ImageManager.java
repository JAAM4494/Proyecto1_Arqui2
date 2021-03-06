/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author jaam
 */
public class ImageManager {

    private BufferedImage image;
    private int width;
    private int height;

    private String parentPath;
    private String imagePath;

    private ArrayList<ArrayList<ColorModel>> grayPixels;

    public ImageManager(Path pPath) {
        imagePath = pPath.toString();
        parentPath = pPath.getParent().toString();

        grayPixels = new ArrayList<>();
        //System.out.println(parentPath);
    }
    
    public ArrayList<ArrayList<ColorModel>> getImageGrayPixels() {
        return grayPixels;
    }
    
    public void buildImage(ArrayList<ArrayList<ColorModel>> pMatrix,String pName) {
        
        int limitOne = pMatrix.size();
        int limitTwo = pMatrix.get(0).size();
        
         for (int i = 0; i < limitOne; i++) {
            ArrayList<ColorModel> pixelLine = pMatrix.get(i);
            for (int j = 0; j < limitTwo; j++) {
                
                    ColorModel temp = pixelLine.get(j);
                    
                    int avg = (temp.getRed() + temp.getGreen() + temp.getBlue()) / 3;
                    
                    //Color newColor = new Color(avg, avg, avg,temp.getAlpha());
                    
                    Color newColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(),temp.getAlpha());

                    image.setRGB(j, i, newColor.getRGB());
            }
        }
         
          File output = new File(parentPath + "/" + pName +".jpg");
        try {
            ImageIO.write(image, "jpg", output);
        } catch (IOException ex) {
            Logger.getLogger(ImageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<ArrayList<ColorModel>> getImagePixels() throws IOException {
        File input = new File(imagePath);
        image = ImageIO.read(input);
        width = image.getWidth();
        height = image.getHeight();

        ArrayList<ArrayList<ColorModel>> pixels = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            ArrayList<ColorModel> pixelLine = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                Color imageColor = new Color(image.getRGB(j, i));
                ColorModel temp = new ColorModel();
                temp.setRed(imageColor.getRed());
                temp.setGreen(imageColor.getGreen());
                temp.setBlue(imageColor.getBlue());
                temp.setAlpha(imageColor.getAlpha());
                pixelLine.add(temp);
            }
            pixels.add(pixelLine);
        }

        return pixels;
    }

    public void imageToGrayScale() {
        try {
            File input = new File(imagePath);
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();

            for (int i = 0; i < height; i++) {
                ArrayList<ColorModel> pixelLine = new ArrayList<>();
                for (int j = 0; j < width; j++) {

                    Color c = new Color(image.getRGB(j, i));
                    int red = (int) (c.getRed() * 0.299);
                    int green = (int) (c.getGreen() * 0.587);
                    int blue = (int) (c.getBlue() * 0.114);
                    Color newColor = new Color(red + green + blue,
                            red + green + blue, red + green + blue);

                    //image.setRGB(j, i, newColor.getRGB());
                    
                    int avg = red + green + blue;

                    ColorModel temp = new ColorModel();
                    temp.setRed(avg);
                    temp.setGreen(avg);
                    temp.setBlue(avg);
                    temp.setAlpha(c.getAlpha());
                    pixelLine.add(temp);
                }
                grayPixels.add(pixelLine);
            }

            // File output = new File(parentPath + "/grayscale.jpg");
            // ImageIO.write(image, "jpg", output);
        } catch (Exception e) {
            System.out.println("ERROR..!!!");
            System.out.println(e);
        }
    }
}
