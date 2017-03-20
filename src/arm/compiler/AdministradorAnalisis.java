/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arm.compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jaam
 */
public class AdministradorAnalisis {

    public void iniciar(String instruccionesString) throws FileNotFoundException, IOException {

        /*  Generamos el analizador lexico y sintactico.
         lcalc.flex contiene la definicion del analizador lexico
         ycalc.cup contiene la definicion del analizador sintactico
         */
        String archLexico = "alexico.flex";
        String archSintactico = "asintactico.cup";
        String[] alexico = {archLexico};
        String[] asintactico = {"-parser", "AnalizadorSintactico", archSintactico};
        jflex.Main.main(alexico);
        
        try {
            java_cup.Main.main(asintactico);
        } catch (Exception ex) {
            Logger.getLogger(AdministradorAnalisis.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //movemos los archivos generados
        boolean mvAL = moverArch("AnalizadorLexico.java");
        boolean mvAS = moverArch("AnalizadorSintactico.java");
        boolean mvSym = moverArch("sym.java");
        String[] archivo = {"BancoInstrucciones.txt"};
        //Abrir archivo, escribir y cerrar archivo
        prepararDatos(instruccionesString, "BancoInstrucciones.txt");
        AnalizadorSintactico.main(archivo);
        //System.out.println("Fin del analisis lexico y sintactico");

    }
    
    private void prepararDatos(String instrucciones, String nombreArchivo) throws FileNotFoundException, IOException{
        FileOutputStream out = new FileOutputStream(nombreArchivo);
        out.write(instrucciones.getBytes());
        out.close();
    }

    public boolean moverArch(String archNombre) {
        boolean efectuado = false;
        File arch = new File(archNombre);
        if (arch.exists()) {
            //System.out.println("Moviendo " + arch);
            Path currentRelativePath = Paths.get("");
            String nuevoDir = currentRelativePath.toAbsolutePath().toString()
                    + File.separator + "src" + File.separator
                    + "compilador" + File.separator + arch.getName();
            File archViejo = new File(nuevoDir);
            archViejo.delete();
            if (arch.renameTo(new File(nuevoDir))) {
                //System.out.println("Generado " + archNombre);
                efectuado = true;
            } else {
                System.out.println("No movido " + archNombre);
            }

        } else {
            System.out.println("*** Codigo no existente ***");
        }
        return efectuado;
    }

}
