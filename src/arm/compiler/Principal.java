/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arm.compiler;

import arm.scalar.Simulacion;
import arm.scalar.BancoRegistros;
import arm.scalar.Memoria;
import arm.scalar.BancoBanderas;
import java.util.ArrayList;
import arm.assembler.Decodificacion;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author jaam
 */
public class Principal {

    public static void principal(String instruccionesString) throws IOException {
        ArrayList<ArrayList<String>> instrucciones = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> branch = new ArrayList<ArrayList<String>>();
        AdministradorAnalisis analisis = new AdministradorAnalisis();
        analisis.iniciar(instruccionesString);
        BancoRegistros bancoRegistros = new BancoRegistros();
        //bancoRegistros.inicioRegistros();
        ManejadorErrores moduloErrores = new ManejadorErrores();
        Simulacion simulacion = new Simulacion();
        Decodificacion decodificacion = new Decodificacion();
        instrucciones = BancoInstrucciones.getInstrucciones();
        BancoBanderas bancoBanderas = new BancoBanderas();
        Memoria memoria = new Memoria();
        BancoBanderas.iniciar();

        //Revisar si hay errores lexicos o sintacticos
        if (moduloErrores.getHuboError() == 1) {
            JOptionPane.showMessageDialog(new JFrame(), moduloErrores.getErrorLexico(), "Error Lexico", JOptionPane.ERROR_MESSAGE);
        } else if (moduloErrores.getHuboError() == 2) {
            JOptionPane.showMessageDialog(new JFrame(), moduloErrores.getErrorSintactico(), "Error Sintactico", JOptionPane.ERROR_MESSAGE);
        } else {
            if(instrucciones.size()<256){
            //Se inicia la simulacion
            simulacion.principal(instrucciones);
            //Se inicia el ensamblaje
            decodificacion.inicio(instrucciones);
            //memoria.imprimirMemoria();
            }else
                JOptionPane.showMessageDialog(new JFrame(), "La cantidad de instrucciones ingresadas sobrepasa el tamaño de la memoria de instrucciones", 
                        "Desbordamiento de memoria", JOptionPane.ERROR_MESSAGE);
        }
    }
}
