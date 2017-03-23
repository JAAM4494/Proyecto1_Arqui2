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
import static arm.scalar.Memoria.Memoria_Ram;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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
    public static ArrayList<ArrayList<ColorModel>> encryptedMatrix = new ArrayList<>();
    public static ArrayList<ArrayList<ColorModel>> desencryptedMatrix = new ArrayList<>();

    ArrayList<ColorModel> tmpArrayList;

    private String sourceCode;

    private String xorCode;
    private String sShiftCode;
    private String cShiftCode;
    private String addCode;

    private String sShiftDesencryptCode;
    private String cShiftDesencryptCode;
    private String addDesencryptCode;
    /////////////////////////////////////////////
    private String xorCodeScalar;

    private String cShiftCodeScalar;
    private String addCodeScalar;

    private String cShiftDesencryptCodeScalar;
    private String addDesencryptCodeScalar;

    int posMem = 0;

    private String endLine;

    public ImageProcessing() {
        endLine = "\n" + "\n" + "ADD R0, R0, #0";
        initCodes();
    }

    public ArrayList<ArrayList<ColorModel>> getEncryptedImageArray() {
        return encryptedMatrix;
    }

    public ArrayList<ArrayList<ColorModel>> getDesencryptedImageArray() {
        return desencryptedMatrix;
    }

    public void encryptImage(ArrayList<ArrayList<ColorModel>> pMatrix, String pAlgorithm, int pSacalar,
            int pKey, int pShiftNumber, ColorModel pKeyVector) throws IOException {

        int heightSize = pMatrix.size();
        int widthSize = pMatrix.get(0).size();
        //int iterations = heightSize * widthSize;  
        //int temp = 0;

        System.out.println("Image HeightSize:" + heightSize);
        System.out.println("Image WidthSize:" + widthSize);

        int memoryAdd = 1024;
        tmpArrayList = new ArrayList<>();

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
                        if (pSacalar == 0) {
                            tempInstr = "mov r1,#" + tempVector.getRed() + "\n"
                                    + "mov r2,#" + tempVector.getGreen() + "\n" 
                                    + "mov r3,#" + tempVector.getBlue() + "\n"
                                    + "mov r4,#" + tempVector.getAlpha() + "\n";
                            
                            String tempInstr2 = "mov r5,#" + pKey + "\n";
                            
                            sourceCode = tempInstr + tempInstr2 + xorCodeScalar
                                    + "str r6, [r0, #" + memoryAdd + "]";
                            memoryAdd += 4;
                            sourceCode = sourceCode
                                    + "str r7, [r0, #" + memoryAdd + "]";
                            memoryAdd += 4;
                            sourceCode = sourceCode
                                    + "str r8, [r0, #" + memoryAdd + "]";
                            memoryAdd += 4;
                            sourceCode = sourceCode
                                    + "str r9, [r0, #" + memoryAdd + "]" + endLine;
                            memoryAdd += 4;
                        } else {
                            String tempInstr2 = "movv v2,#" + pKey + "\n";

                            sourceCode = tempInstr + tempInstr2 + xorCode
                                    + "strv v3, [r0, #" + memoryAdd + "]" + endLine;
                        }

                        break;
                    case "shift-s":
                        if (pSacalar == 0) {
                            tempInstr = "mov r1,#" + tempVector.getRed() + "\n"
                                    + "mov r2,#" + tempVector.getGreen() + "\n" 
                                    + "mov r3,#" + tempVector.getBlue() + "\n"
                                    + "mov r4,#" + tempVector.getAlpha() + "\n";
                            
                            sourceCode = tempInstr + "lsl r5, r1,#" + pShiftNumber + "\n"
                                    + "str r5, [r0, #" + memoryAdd + "]";
                            memoryAdd += 4;
                            sourceCode = sourceCode + "lsl r6, r2,#" + pShiftNumber + "\n"
                                    + "str r6, [r0, #" + memoryAdd + "]";
                            memoryAdd += 4;
                            sourceCode = sourceCode + "lsl r7, r3,#" + pShiftNumber + "\n"
                                    + "str r7, [r0, #" + memoryAdd + "]";
                            memoryAdd += 4;
                            sourceCode = sourceCode + "lsl r8, r4,#" + pShiftNumber + "\n"
                                    + "str r8, [r0, #" + memoryAdd + "]" + endLine;
                            memoryAdd += 4;
                        } else {
                            //String tempInstr3 = "movv v2,#" + pShiftNumber + "\n";

                            sourceCode = tempInstr + sShiftCode + pShiftNumber + "\n"
                                    + "strv v3, [r0, #" + memoryAdd + "]" + endLine;
                        }

                        break;
                    case "shift-c":
                        if (pSacalar == 0) {
                            tempInstr = "mov r1,#" + tempVector.getRed() + "\n"
                                    + "mov r2,#" + tempVector.getGreen() + "\n" 
                                    + "mov r3,#" + tempVector.getBlue() + "\n"
                                    + "mov r4,#" + tempVector.getAlpha() + "\n";
                        } else {
                            //String tempInstr4 = "movv v2,#" + pShiftNumber + "\n";

                            sourceCode = tempInstr + cShiftCode + pShiftNumber + "\n"
                                    + "strv v3, [r0, #" + memoryAdd + "]" + endLine;
                        }

                        break;
                    case "add":
                        if (pSacalar == 0) {
                            tempInstr = "mov r1,#" + tempVector.getRed() + "\n"
                                    + "mov r2,#" + tempVector.getGreen() + "\n" 
                                    + "mov r3,#" + tempVector.getBlue() + "\n"
                                    + "mov r4,#" + tempVector.getAlpha() + "\n";
                            
                            String tempInstr5 = "mov r5,#" + pKeyVector.getRed() + "\n"
                                    + "mov r6,#" + pKeyVector.getGreen() + "\n"
                                    + "mov r7,#" + pKeyVector.getBlue() + "\n"
                                    + "mov r8,#" + pKeyVector.getAlpha() + "\n";
                            
                            sourceCode = tempInstr + tempInstr5 + addCodeScalar
                                    + "str r9, [r0, #" + memoryAdd + "]";
                            memoryAdd += 4;
                            sourceCode = sourceCode
                                    + "str r10, [r0, #" + memoryAdd + "]";
                            memoryAdd += 4;
                            sourceCode = sourceCode
                                    + "str r11, [r0, #" + memoryAdd + "]";
                            memoryAdd += 4;
                            sourceCode = sourceCode
                                    + "str r12, [r0, #" + memoryAdd + "]" + endLine;
                            memoryAdd += 4;
                        } else {
                            String tempInstr5 = "movv v2,#" + pKeyVector.getRed()
                                    + ",#" + pKeyVector.getGreen() + ",#" + pKeyVector.getBlue()
                                    + ",#" + pKeyVector.getAlpha() + "\n";

                            sourceCode = tempInstr + tempInstr5 + addCode
                                    + "strv v3, [r0, #" + memoryAdd + "]" + endLine;
                        }

                        break;
                }
                // next step, pass assembler code to the architecture
                compileCode(0);
                if(pSacalar != 0) {
                    memoryAdd += 4;
                }
                posMem += 1;

            }
        }
    }

    public void desencryptImage(ArrayList<ArrayList<ColorModel>> pMatrix, String pAlgorithm, int pSacalar,
            int pKey, int pShiftNumber, ColorModel pKeyVector) throws IOException {

        int heightSize = pMatrix.size();
        int widthSize = pMatrix.get(0).size();

        int memoryAdd = 1024;
        posMem = 0;
        tmpArrayList = new ArrayList<>();

        counter = 0;

        try {
            memoria.inicia_memoria();
        } catch (IOException ex) {
            Logger.getLogger(ImageProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }

        //int iterations = heightSize * widthSize;  
        //int temp = 0;
        for (int i = 0; i < heightSize; i++) {
            for (int j = 0; j < widthSize; j++) {
                ColorModel tempVector = pMatrix.get(i).get(j);

                String tempInstr = "movv v1,#" + tempVector.getRed() + ",#"
                        + tempVector.getGreen() + ",#" + tempVector.getBlue() + ",#"
                        + tempVector.getAlpha() + "\n";

                switch (pAlgorithm) {
                    case "xor":
                        if (pSacalar == 0) {
                            tempInstr = "mov r1,#" + tempVector.getRed() + "\n"
                                    + "mov r2,#" + tempVector.getGreen() + "\n" 
                                    + "mov r3,#" + tempVector.getBlue() + "\n"
                                    + "mov r4,#" + tempVector.getAlpha() + "\n";
                            
                            String tempInstr2 = "mov r5,#" + pKey + "\n";
                            
                            sourceCode = tempInstr + tempInstr2 + xorCodeScalar
                                    + "str r6, [r0, #" + memoryAdd + "]";
                            memoryAdd += 4;
                            sourceCode = sourceCode
                                    + "str r7, [r0, #" + memoryAdd + "]";
                            memoryAdd += 4;
                            sourceCode = sourceCode
                                    + "str r8, [r0, #" + memoryAdd + "]";
                            memoryAdd += 4;
                            sourceCode = sourceCode
                                    + "str r9, [r0, #" + memoryAdd + "]" + endLine;
                            memoryAdd += 4;
                        } else {
                            String tempInstr2 = "movv v2,#" + pKey + "\n";

                            sourceCode = tempInstr + tempInstr2 + xorCode
                                    + "strv v3, [r0, #" + memoryAdd + "]" + endLine;
                        }

                        break;
                    case "shift-s":
                        if (pSacalar == 0) {
                            tempInstr = "mov r1,#" + tempVector.getRed() + "\n"
                                    + "mov r2,#" + tempVector.getGreen() + "\n" 
                                    + "mov r3,#" + tempVector.getBlue() + "\n"
                                    + "mov r4,#" + tempVector.getAlpha() + "\n";
                            
                            sourceCode = tempInstr + "lsr r5, r1,#" + pShiftNumber + "\n"
                                    + "str r5, [r0, #" + memoryAdd + "]";
                            memoryAdd += 4;
                            sourceCode = sourceCode + "lsr r6, r2,#" + pShiftNumber + "\n"
                                    + "str r6, [r0, #" + memoryAdd + "]";
                            memoryAdd += 4;
                            sourceCode = sourceCode + "lsr r7, r3,#" + pShiftNumber + "\n"
                                    + "str r7, [r0, #" + memoryAdd + "]";
                            memoryAdd += 4;
                            sourceCode = sourceCode + "lsr r8, r4,#" + pShiftNumber + "\n"
                                    + "str r8, [r0, #" + memoryAdd + "]" + endLine;
                            memoryAdd += 4;
                        } else {
                            //String tempInstr3 = "movv v2,#" + pShiftNumber + "\n";

                            sourceCode = tempInstr + sShiftDesencryptCode + pShiftNumber + "\n"
                                    + "strv v3, [r0, #" + memoryAdd + "]" + endLine;
                        }

                        break;
                    case "shift-c":
                        if (pSacalar == 0) {
                            tempInstr = "mov r1,#" + tempVector.getRed() + "\n"
                                    + "mov r2,#" + tempVector.getGreen() + "\n" 
                                    + "mov r3,#" + tempVector.getBlue() + "\n"
                                    + "mov r4,#" + tempVector.getAlpha() + "\n";
                        } else {
                            //String tempInstr4 = "movv v2,#" + pShiftNumber + "\n";

                            sourceCode = tempInstr + cShiftDesencryptCode + pShiftNumber + "\n"
                                    + "strv v3, [r0, #" + memoryAdd + "]" + endLine;
                        }

                        break;
                    case "add":
                        if (pSacalar == 0) {
                            tempInstr = "mov r1,#" + tempVector.getRed() + "\n"
                                    + "mov r2,#" + tempVector.getGreen() + "\n" 
                                    + "mov r3,#" + tempVector.getBlue() + "\n"
                                    + "mov r4,#" + tempVector.getAlpha() + "\n";
                            
                            String tempInstr5 = "mov r5,#" + pKeyVector.getRed() + "\n"
                                    + "mov r6,#" + pKeyVector.getGreen() + "\n"
                                    + "mov r7,#" + pKeyVector.getBlue() + "\n"
                                    + "mov r8,#" + pKeyVector.getAlpha() + "\n";
                            
                            sourceCode = tempInstr + tempInstr5 + addDesencryptCodeScalar
                                    + "str r9, [r0, #" + memoryAdd + "]";
                            memoryAdd += 4;
                            sourceCode = sourceCode
                                    + "str r10, [r0, #" + memoryAdd + "]";
                            memoryAdd += 4;
                            sourceCode = sourceCode
                                    + "str r11, [r0, #" + memoryAdd + "]";
                            memoryAdd += 4;
                            sourceCode = sourceCode
                                    + "str r12, [r0, #" + memoryAdd + "]" + endLine;
                            memoryAdd += 4;
                        } else {
                            String tempInstr5 = "movv v2,#" + pKeyVector.getRed()
                                    + ",#" + pKeyVector.getGreen() + ",#" + pKeyVector.getBlue()
                                    + ",#" + pKeyVector.getAlpha() + "\n";

                            sourceCode = tempInstr + tempInstr5 + addDesencryptCode
                                    + "strv v3, [r0, #" + memoryAdd + "]" + endLine;
                        }

                        break;
                }
                // next step, pass assembler code to the architecture
                compileCode(1);
                if(pSacalar != 0) {
                    memoryAdd += 4;
                }
                posMem += 1;
            }
        }
    }

    private void compileCode(int pSelection) throws IOException {
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

        if (pSelection == 0) {
            storeEncryptData();
        } else {
            buildDesencryptedImage(Memoria_Ram[posMem]);
        }

        //Borrar el contenido actual de las instrucciones, banderas, ensamblaje, memoria, branch y registros
        bancoInstrucciones.borrarInstrucciones();
        bancoBanderas.borrarBanderas();

        bancoRegistros.borrarRegistros();
        moduloErrores.borrarErrores();
        bancoBranch.borrarBranch();
    }

    private void storeEncryptData() throws IOException {
        boolean flag;
        if (posMem == 0) {
            flag = false;
        } else {
            flag = true;
        }
        FileWriter fichero = null;
        PrintWriter pw = null;

        fichero = new FileWriter("Datos Encriptados.txt", flag);
        pw = new PrintWriter(fichero, flag);
        //pw.println(dato);
        System.out.println("Guardando dato \n" + Memoria_Ram[posMem]);
        System.out.println("flag\n" + flag);
        buildEncryptedImage(Memoria_Ram[posMem]);

        pw.println(Memoria_Ram[posMem]);
        pw.close();
        fichero.close();
    }

    int counter = 0;

    private void buildEncryptedImage(String pData) {
        ColorModel tmp = new ColorModel();

        int temp = Integer.parseUnsignedInt(pData, 16); // hex to dec

        System.out.println("Dato de Memoria_Encrypted:" + temp);

        byte[] pixel = toBytes(temp);

        tmp.setRed(pixel[0] & 0xFF);
        tmp.setGreen(pixel[1] & 0xFF);
        tmp.setBlue(pixel[2] & 0xFF);
        tmp.setAlpha(pixel[3] & 0xFF);

        System.out.println("Dato Memoria1 Bytes0:" + pixel[0]);
        System.out.println("Dato Memoria1 Bytes1:" + pixel[1]);
        System.out.println("Dato Memoria1 Bytes2:" + pixel[2]);
        System.out.println("Dato Memoria1 Bytes3:" + pixel[3]);

        System.out.println("Dato Pixel0:" + (pixel[0] & 0xFF));
        System.out.println("Dato Pixel1:" + (pixel[1] & 0xFF));
        System.out.println("Dato Pixel2:" + (pixel[2] & 0xFF));
        System.out.println("Dato Pixel3:" + (pixel[3] & 0xFF));

        tmpArrayList.add(tmp);

        if (counter == 99) {
            encryptedMatrix.add(tmpArrayList);
            tmpArrayList = new ArrayList<>();
            counter = 0;
        } else {
            counter += 1;
        }

        /*if(posMem%100==0 && posMem!=0){ //Se completa la primera fila
            tmpArrayList.remove(tmpArrayList.size()-1); //se debe eliminar el ultimo ya que 
                                        //sobrepasa 100 elementos
            encryptedMatrix.add(tmpArrayList); //Se agrega matriz principal
            tmpArrayList=new ArrayList<>(); 
            tmpArrayList.add(tmp);        
        }*/
    }

    private void buildDesencryptedImage(String pData) {
        ColorModel tmp = new ColorModel();

        int temp = Integer.parseUnsignedInt(pData, 16); // hex to dec

        System.out.println("Dato de Memoria_Desencrypted:" + temp);

        byte[] pixel = toBytes(temp);

        tmp.setRed(pixel[0] & 0xFF);
        tmp.setGreen(pixel[1] & 0xFF);
        tmp.setBlue(pixel[2] & 0xFF);
        tmp.setAlpha(pixel[3] & 0xFF);

        if (counter == 99) {
            desencryptedMatrix.add(tmpArrayList);
            tmpArrayList = new ArrayList<>();
            counter = 0;
        } else {
            counter += 1;
        }

    }

    byte[] toBytes(int i) {
        byte[] result = new byte[4];

        result[0] = (byte) (i >> 24);
        result[1] = (byte) (i >> 16);
        result[2] = (byte) (i >> 8);
        result[3] = (byte) (i /*>> 0*/);

        return result;
    }

    private void initCodes() {
        xorCode = "eorv v3, v1, v2 \n";
        sShiftCode = "lslv v3, v1,#";
        cShiftCode = "rolv v3, v1,#";
        addCode = "addv v3, v1, v2 \n";

        sShiftDesencryptCode = "lsrv v3, v1,#";
        cShiftDesencryptCode = "rorv v3, v1,#";
        addDesencryptCode = "subv v3, v1, v2 \n";
    }
}
