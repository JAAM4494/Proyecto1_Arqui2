/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arm.scalar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jaam
 */
public class BancoRegistros {
    
    public static ArrayList<Long> registros = new ArrayList<>();
    
    public static void inicioRegistros() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("BancoRegistros.txt"));
        try {
            StringBuilder sb = new StringBuilder();
            String line = "";
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
                if(line != null){
                String[] parts = line.split(" ");
                registros.add(Long.parseLong(parts[1]));
                }
            }
        } finally {
            br.close();
        }

    }
    
    public static long getRegistro(int indice){
        if(indice >= 0 && indice <=15)
            return registros.get(indice);
        else
            return 0;
    }
    
     public static void setRegistro(int indice, long valor){
        if(indice >= 0 && indice <=15){
            registros.remove(indice);
            registros.add(indice, valor);
        }
    }
    
    public ArrayList<Long> obtenerRegistrosIniciales() throws IOException{
        inicioRegistros();

        return registros;
    }
    
    public ArrayList<Long> obtenerRegistros() throws IOException {
        return registros;
    }
    
    public static void borrarRegistros() {
        registros.clear();
        try {
            inicioRegistros();
        } catch (IOException ex) {
            Logger.getLogger(BancoRegistros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }          

}
