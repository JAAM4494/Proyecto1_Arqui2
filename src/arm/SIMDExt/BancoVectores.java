/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arm.SIMDExt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author edwin
 */
public class BancoVectores {
    
    /**
     *
     */
    public static ArrayList<byte[]> vectores = new ArrayList<>();
    
    
    public static void inicioVectores() throws FileNotFoundException, IOException { 
        vectores.clear();
        for (int i = 0; i < 15; i++) {
            byte[] tempByte=new byte[4];
            vectores.add(tempByte);
        }
    }
    
    public static void setVector(int indice, byte[] valor){
        byte[] newByte = new byte[4];
        if(indice >= 0 && indice <=15){
            newByte[0]=valor[0];
            newByte[1]=valor[1];
            newByte[2]=valor[2];
            newByte[3]=valor[3];
            vectores.remove(indice);
            vectores.add(indice, newByte);
        }
        
    }
    
    public static byte[] getVector(int indice){
        //if(indice >= 0 && indice <=15)
            return vectores.get(indice);
        //else
         //   return 0;
    }
    
     public String obtenerVectores() throws IOException{
        String vectoresString = "";
        System.out.println("Tamano"+ vectores.size() );
        for (int i = 0; i < vectores.size(); i++) {
            vectoresString = vectoresString + "\n" + "V" + i + ": \n"   ; 
            vectoresString = vectoresString + "[0]="+vectores.get(i)[0] +"\n"  ;  
            vectoresString = vectoresString + "[1]="+vectores.get(i)[1] +"\n"   ;  
            vectoresString = vectoresString + "[2]="+vectores.get(i)[2] +"\n"  ;  
            vectoresString = vectoresString + "[3]="+vectores.get(i)[3] +"\n"  ;  
        }
        return vectoresString;
    }
     
      public static void borrarVectores() {
        try {
            inicioVectores();
        } catch (IOException ex) {
           // Logger.getLogger(BancoRegistros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    
    
    
    
}