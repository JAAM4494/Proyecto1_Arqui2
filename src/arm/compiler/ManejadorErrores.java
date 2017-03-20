/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arm.compiler;

import arm.scalar.BancoBanderas;
import arm.scalar.BancoBranch;
import arm.scalar.BancoRegistros;
import arm.scalar.Memoria;
import static arm.scalar.Memoria.inicia_memoria;
import arm.assembler.Decodificacion;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author jaam
 */
public class ManejadorErrores {
    public static String errorLexico = "";
    public static String errorSintactico = "";
    public static int huboError = 0;
    
    public static void setErrorLexico (int columna, int fila, String caracterDesconocido){
        errorLexico = "Error lexico en la fila: " + fila + 
                ". Caracter desconocido: " + caracterDesconocido;
        huboError = 1;
    }
    
    public static void setErrorSintactico (int fila, int columna){
        errorSintactico = "Error sintactico en la fila: " + fila + ".";
        huboError = 2;
    }
    
    public static String getErrorLexico (){
        return errorLexico;
    }
    
    public static String getErrorSintactico (){
        return errorSintactico;
    }
    
    public static int getHuboError(){
        return huboError;
    }
    
    public static void borrarErrores(){
        huboError = 0;
    }
}
