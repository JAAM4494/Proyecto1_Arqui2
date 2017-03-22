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
    public static ArrayList<ArrayList<ColorModel>> encryptedMatrix= new ArrayList<>();
    ArrayList<ColorModel> tmpArrayList;
    
    private String sourceCode;

    private String xorCode;
    private String sShiftCode;
    private String cShiftCode;
    private String addCode;

    private String xorDesencryptCode;
    private String sShiftDesencryptCode;
    private String cShiftDesencryptCode;
    private String addDesencryptCode;
    int posMem=0;

    private String endLine;

    public ImageProcessing() {
        endLine = "\n" + "\n" + "ADD R0, R0, #0";
        initCodes();
    }

    public void encryptImage(ArrayList<ArrayList<ColorModel>> pMatrix, String pAlgorithm,
            int pKey, int pShiftNumber, ColorModel pKeyVector) throws IOException {
        
        int heightSize = pMatrix.size();
        int widthSize = pMatrix.get(0).size();
        //int iterations = heightSize * widthSize;  
        //int temp = 0;

        int memoryAdd = 1024;
        tmpArrayList= new ArrayList<>();
        
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
                posMem++;

            }
        }
    }

    public void desencryptImage(ArrayList<ArrayList<ColorModel>> pMatrix, String pAlgorithm,
            int pKey, int pShiftNumber, ColorModel pKeyVector) throws IOException {

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

    private void compileCode() throws IOException {
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
        storeEncryptData();
        //Borrar el contenido actual de las instrucciones, banderas, ensamblaje, memoria, branch y registros
        bancoInstrucciones.borrarInstrucciones();
        bancoBanderas.borrarBanderas();

        bancoRegistros.borrarRegistros();
        moduloErrores.borrarErrores();
        bancoBranch.borrarBranch();
    }
    
    
    private void storeEncryptData() throws IOException{
        boolean flag;
        if(posMem==0){
            flag=false;
        } else { flag=true;  }
        FileWriter fichero = null;
        PrintWriter pw = null;
            
        fichero = new FileWriter("Datos Encriptados.txt", flag);
        pw = new PrintWriter(fichero, flag);
        //pw.println(dato);
        System.out.println("Guardando dato \n"+Memoria_Ram[posMem]);
        System.out.println("flag\n"+flag);
        buildEncryptedImage(Memoria_Ram[posMem]);
            
        pw.println(Memoria_Ram[posMem]);
        pw.close();
        fichero.close();
    }
    
    
    private void buildEncryptedImage(String pData){
        ColorModel tmp=new ColorModel();
        int value = Integer.parseInt(pData, 16);  
        byte[] pixel=toBytes(value);
        
        tmp.setRed(pixel[0]& 0xFF);
        tmp.setGreen(pixel[1]& 0xFF);
        tmp.setBlue(pixel[2]& 0xFF);
        tmp.setAlpha(pixel[3]& 0xFF);
        
        tmpArrayList.add(tmp);
        if(posMem%100==0 && posMem!=0){ //Se completa la primera fila
            tmpArrayList.remove(tmp); //se debe eliminar el ultimo ya que 
                                        //sobrepasa 100 elementos
            encryptedMatrix.add(tmpArrayList); //Se agrega matriz principal
            tmpArrayList=new ArrayList<>(); 
            tmpArrayList.add(tmp);        
        }
        
         
    }
    
    byte[] toBytes(int i)
    {
    byte[] result = new byte[4];

    result[0] = (byte) (i >> 24);
    result[1] = (byte) (i >> 16);
    result[2] = (byte) (i >> 8);
    result[3] = (byte) (i /*>> 0*/);

    return result;
    }

    private void initCodes() {
        xorCode = "eorv v3, v1, v2 \n";
        
    }
}
