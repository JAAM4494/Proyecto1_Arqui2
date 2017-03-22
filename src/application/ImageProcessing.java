/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import arm.SIMDExt.BancoVectores;
import arm.assembler.Decodificacion;
import arm.compiler.BancoInstrucciones;
import arm.compiler.ManejadorErrores;
import arm.compiler.Principal;
import arm.scalar.BancoBanderas;
import arm.scalar.BancoBranch;
import arm.scalar.BancoRegistros;
import arm.scalar.Memoria;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jaam
 */
public class ImageProcessing {

    BancoRegistros bancoRegistros = new BancoRegistros();
    BancoVectores bancoVectores = new BancoVectores();
    BancoBanderas bancoBanderas = new BancoBanderas();
    Decodificacion ensamblador = new Decodificacion();
    BancoBranch bancoBranch = new BancoBranch();
    Memoria memoria = new Memoria();

    private String sourceCode;

    private String xorCode;
    private String sShiftCode;
    private String cShiftCode;
    private String addCode;

    private String xorDesencryptCode;
    private String sShiftDesencryptCode;
    private String cShiftDesencryptCode;
    private String addDesencryptCode;

    private String endLine;

    public ImageProcessing() {
        endLine = "\n" + "\n" + "ADD R0, R0, #0";
        initCodes();
    }

    public void encryptImage(ArrayList<ArrayList<ColorModel>> pMatrix, String pAlgorithm,
            int pKey, int pShiftNumber, ColorModel pKeyVector) {

        int heightSize = pMatrix.size();
        int widthSize = pMatrix.get(0).size();
        //int iterations = heightSize * widthSize;  
        //int temp = 0;

        int memoryAdd = 1024;
        try {
            memoria.inicia_memoria();
        } catch (IOException ex) {
            Logger.getLogger(ImageProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < heightSize; i++) {
            for (int j = 0; j < widthSize; j++) {
                ColorModel tempVector = pMatrix.get(i).get(j);

                String tempInstr = "movv v1,#" + tempVector.getRed() + ",#"
                        + tempVector.getGreen() + ",#" + tempVector.getBlue() + ",#"
                        + tempVector.getAlpha() + "\n";

                switch (pAlgorithm) {
                    case "xor":
                        String tempInstr2 = "movv v2,#" + pKey + "\n";

                        sourceCode = tempInstr + tempInstr2 + xorCode
                                + "strv v3, [r0, #" + memoryAdd + "]" + endLine;
                        break;
                    case "shift-s":
                        sourceCode = tempInstr + sShiftCode + endLine;
                        break;
                    case "shift-c":
                        sourceCode = tempInstr + cShiftCode + endLine;
                        break;
                    case "add":
                        sourceCode = tempInstr + addCode + endLine;
                        break;
                }
                // next step, pass assembler code to the architecture
                compileCode();
                memoryAdd += 4;
            }
        }
    }

    public void desencryptImage(ArrayList<ArrayList<ColorModel>> pMatrix, String pAlgorithm,
            int pKey, int pShiftNumber, ColorModel pKeyVector) {

        int heightSize = pMatrix.size();
        int widthSize = pMatrix.get(0).size();
        //int iterations = heightSize * widthSize;  
        //int temp = 0;

        for (int i = 0; i < heightSize; i++) {
            for (int j = 0; j < widthSize; j++) {
                ColorModel tempVector = pMatrix.get(i).get(j);

                String tempInstr = "movv v1,#" + tempVector.getRed() + ",#"
                        + tempVector.getGreen() + ",#" + tempVector.getBlueByte() + ",#"
                        + tempVector.getAlpha();

                switch (pAlgorithm) {
                    case "xor":
                        sourceCode = tempInstr + xorCode + endLine;
                        break;
                    case "shift-s":
                        sourceCode = tempInstr + sShiftCode + endLine;
                        break;
                    case "shift-c":
                        sourceCode = tempInstr + cShiftCode + endLine;
                        break;
                    case "add":
                        sourceCode = tempInstr + addCode + endLine;
                        break;
                }
                // next step, pass assembler code to the architecture
                compileCode();
            }
        }
    }

    private void compileCode() {
        ////////
        //////// Architecture Initialization
        ////////
        ArrayList<Long> registros = new ArrayList<>();
        try {
            registros = bancoRegistros.obtenerRegistrosIniciales();
        } catch (IOException ex) {
            System.out.println("Error obteniendo los registros a imprimir");
        }
        ArrayList<String> banderasString = bancoBanderas.obtenerBanderasIniciales();
        /////////
        ////////

        Principal compilador = new Principal();
        BancoInstrucciones bancoInstrucciones = new BancoInstrucciones();
        ManejadorErrores moduloErrores = new ManejadorErrores();
        ensamblador.borrarEnsamblaje();

        System.out.println(sourceCode);
        try {
            compilador.principal(sourceCode);
        } catch (IOException ex) {
            System.out.println("Error, loading file");
        }
        //Borrar el contenido actual de las instrucciones, banderas, ensamblaje, memoria, branch y registros
        bancoInstrucciones.borrarInstrucciones();
        bancoBanderas.borrarBanderas();

        bancoRegistros.borrarRegistros();
        moduloErrores.borrarErrores();
        bancoBranch.borrarBranch();

    }

    private void initCodes() {
        xorCode = "eorv v3, v1, v2 \n";
    }
}
