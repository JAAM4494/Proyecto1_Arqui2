/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arm.scalar;

import java.util.ArrayList;

/**
 *
 * @author jaam
 */
public class BancoBanderas {
    static public int N,Z,C,V;
    
    public static void iniciar(){
        N = 0;
        Z = 0;
        C = 0;
        V = 0;
    }
    
    public static void setN(int valor){
        N = valor;
    }
    
    public static void setZ(int valor){
        Z = valor;
    }
    
    public static void setC(int valor){
        C = valor;
    }
    
    public static void setV(int valor){
        V = valor;
    }
    
    public static int getN(){
        return N;
    }
    
    public static int getZ(){
        return Z;
    }
    
    public static int getC(){
        return C;
    }
    
    public static int getV(){
        return V;
    }
    
    public static void imprimirBanderas(){
        System.out.println("");
        System.out.println("Impresion de banderas");
        System.out.println("N: " + N);
        System.out.println("Z: " + Z);
        System.out.println("C: " + C);
        System.out.println("V: " + V);
    }
    
    public static void borrarBanderas(){
        N = 0;
        Z = 0;
        C = 0;
        V = 0;
    }
    
    public ArrayList<String> obtenerBanderasIniciales(){
        iniciar();
        ArrayList<String> flags = new ArrayList<>();
        flags.add(Integer.toString(N));
        flags.add(Integer.toString(Z));
        flags.add(Integer.toString(C));
        flags.add(Integer.toString(V));

        return flags;
    }
    
    public ArrayList<String> obtenerBanderas(){
        ArrayList<String> flags = new ArrayList<>();
        flags.add(Integer.toString(N));
        flags.add(Integer.toString(Z));
        flags.add(Integer.toString(C));
        flags.add(Integer.toString(V));
        
        return flags;
    }
}
