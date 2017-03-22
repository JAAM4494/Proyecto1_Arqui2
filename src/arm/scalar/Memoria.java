/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arm.scalar;

import arm.compiler.BancoInstrucciones;
import arm.compiler.ManejadorErrores;
import arm.assembler.Decodificacion;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author jaam
 */
public class Memoria {

    static boolean bandera = true;
    public static String Memoria_Ram[] = new String[66000];
    public static ArrayList<ArrayList<String>> bancoMemoria = new ArrayList<ArrayList<String>>();

    BancoRegistros bancoRegistros = new BancoRegistros();
    BancoBanderas bancoBanderas = new BancoBanderas();
    BancoBranch bancoBranch = new BancoBranch();
    Decodificacion ensamblador = new Decodificacion();
    BancoInstrucciones bancoInstrucciones = new BancoInstrucciones();
    ManejadorErrores moduloErrores = new ManejadorErrores();

    public static void setMemoria(int indice, String bandera) {
        ArrayList<String> branch = new ArrayList<String>();
        branch.add(Integer.toString(indice));
        branch.add(bandera);
        bancoMemoria.add(branch);
    }

    public static void inicia_memoria() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("BancoMemoria.txt"));
        try {
            String line;
            for (int i = 0; i < 256; i++) {
                line = br.readLine();
                if (line != null) {
                    String[] parts = line.split(":");
                    Memoria_Ram[i] = parts[1];
                }
            }
        } finally {
            br.close();
        }
        bancoMemoria.clear();
    }

       public ArrayList<ArrayList<String>> getMemoriaString() {
        /*ArrayList<String> dato = new ArrayList<String>();
        String memoriaString = "";
        String aux;
        for (int i = 0; i < bancoMemoria.size(); i++) {
            dato = bancoMemoria.get(i);
            aux = dato.get(0) + ": " + dato.get(1);
            memoriaString = memoriaString + aux + "\n";
        }*/
        return bancoMemoria;
    }

    public static String Load_word(int posicion) {
        revisa_posicion(posicion, 1);
        String sResultado = "";
        int resultado = (posicion - 1024) / 4;
        if (bandera) {
            if (Memoria_Ram[resultado] == null) {
                sResultado = "00000000";
            } else {
                sResultado = Memoria_Ram[resultado];
            }
        }
        return sResultado;
    }

    public static String Load_byte(int posicion) {
        revisa_posicion(posicion, 0);
        String palabra = null;
        String bite;
        int resultado, pos_palabra, pos_byte;
        resultado = (posicion - 1024);
        pos_palabra = resultado / 4;
        pos_byte = resultado % 4;
        if (bandera) {
            if (Memoria_Ram[pos_palabra] == null) {
                palabra = "00000000";
            } else {
                palabra = Memoria_Ram[pos_palabra];
            }
        }
        char letra1 = palabra.charAt(8 - (pos_byte * 2));
        char letra2 = palabra.charAt(8 - ((pos_byte * 2) + 1));
        bite = Character.toString(letra2) + Character.toString(letra1);
        return bite;
    }

    public static void Store_byte(int posicion, String dato) {
        if (dato.length()>2){
            dato = dato.substring(dato.length()-2);
        }
        revisa_posicion(posicion, 0);
        String palabra;
        String nueva_palabra = "";
        char dato1, dato2;
        int resultado, pos_palabra, pos_byte;
        resultado = (posicion - 1024);
        pos_palabra = resultado / 4;
        pos_byte = resultado % 4;
        if (bandera) {
            if (Memoria_Ram[pos_palabra] == null) {
                palabra = "00000000";
            } else {
                palabra = Memoria_Ram[pos_palabra];
            }

            int pos1 = 8 - (pos_byte * 2);
            int pos2 = 8 - ((pos_byte * 2) + 1);
            if (dato.length() < 2) {

                for (int i = dato.length(); i < 2; i++) {
                    dato = "0" + dato;
                }
            }
            dato1 = dato.charAt(0);
            dato2 = dato.charAt(1);
            for (int i = 0; i < palabra.length(); i++) {
                if (i == pos1) {
                    nueva_palabra = nueva_palabra + dato2;
                } else if (i == pos2) {
                    nueva_palabra = nueva_palabra + dato1;
                } else {
                    nueva_palabra = nueva_palabra + palabra.charAt(i);
                }
            }
            Memoria_Ram[pos_palabra] = nueva_palabra;
            setMemoria((pos_palabra * 4) + 1024, nueva_palabra);
        }
    }

    public static void Store_word(int posicion, String dato) {
        revisa_posicion(posicion, 1);
        int resultado = (posicion - 1024) / 4;
        if (bandera) {
            if (dato.length() < 8) {
                for (int i = dato.length(); i < 8; i++) {
                    dato = "0" + dato;
                }
            }
            Memoria_Ram[resultado] = dato;
            setMemoria((resultado * 4) + 1024, dato);
        }
    }
    
    public static void Store_Vector(int posicion, String dato) {
        revisa_posicion(posicion, 1);
        int resultado = (posicion - 1024) / 4;
        if (bandera) {
            if (dato.length() < 8) {
                for (int i = dato.length(); i < 8; i++) {
                    dato = "0" + dato;
                }
            }
            Memoria_Ram[resultado] = dato;
            setMemoria((resultado * 4) + 1024, dato);
            
        }
    }


    public static int hex2decimal(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16 * val + d;
        }
        return val;
    }

    public static void revisa_posicion(int posicion, int tipo) {
        if (posicion < 1024) {
            reporta_error(posicion);
            bandera = false;
        } else {
            if (tipo == 1) {
                if (posicion % 4 > 0) {
                    reporta_error(posicion);
                    bandera = false;
                }
            } else {
                bandera = true;
            }
        }
    }

    public static void reporta_error(int valor) {
        JOptionPane.showMessageDialog(new JFrame(), "Error: Valor " + valor + " no puede ser utilizado como dirección de memoria",
                "Direccion invalida", JOptionPane.ERROR_MESSAGE);
        BancoInstrucciones.borrarInstrucciones();
        BancoBanderas.borrarBanderas();
        BancoRegistros.borrarRegistros();
        ManejadorErrores.borrarErrores();
        BancoBranch.borrarBranch();
        Decodificacion.borrarEnsamblaje();
        try {
            inicia_memoria();
        } catch (IOException ex) {
            Logger.getLogger(Memoria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
