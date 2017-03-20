/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arm.compiler;

import java.util.ArrayList;

/**
 * Clase encargada de administrar las instrucciones de entrada. Las clases
 * AnalizadorLexico y AnalizadorSintactico escriben las instrucciones en la
 * variable "instrucciones" conforme las revisan.
 *
 * @author jaam
 */
public class BancoInstrucciones {

    public static ArrayList<ArrayList<String>> instrucciones = new ArrayList<ArrayList<String>>();

    //Añade una nueva instruccion al Array general
    public void añadirInstruccion(ArrayList<String> instruccion) {
        if(instruccion.size() != 0)
            instrucciones.add(new ArrayList<String>(instruccion));
    }

    //Imprime todas las instrucciones del Array general
    public static void imprimirInstrucciones() {
        System.out.println("Impresion de instrucciones");
        System.out.println("Cantidad de instrucciones: " + instrucciones.size());
        for (int i = 0; i < instrucciones.size(); i++) {
            System.out.println(instrucciones.get(i));
        }
        System.out.println("");
    }

    //Retorna el arreglo de instrucciones
    public static ArrayList<ArrayList<String>> getInstrucciones() {
        return instrucciones;
    }
    
    //Retorna l instruccion i
    public static ArrayList<String> getInstruccion(int i) {
        if(i<instrucciones.size() && i>=0)
            return instrucciones.get(i);
        else 
            return null;
    }
    
    public static void borrarInstrucciones(){
        instrucciones.clear();
    }
    
    public int cantidadInstrucciones(){
        return instrucciones.size();
    }

}
