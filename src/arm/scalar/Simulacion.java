/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arm.scalar;

import arm.compiler.BancoInstrucciones;
import arm.compiler.ManejadorErrores;
import arm.assembler.Decodificacion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import arm.SIMDExt.*;
/**
 *
 * @author jaam
 */
public class Simulacion {

    BancoBranch bancoBranch = new BancoBranch();
    BancoBanderas bancoBanderas = new BancoBanderas();
    int pc = 8;

    public void principal(ArrayList<ArrayList<String>> instrucciones) {
        //Se revisa los labels existentes.
        agregaBranch(instrucciones);
        agregaBranchSimulacion(instrucciones);

        ArrayList<String> instruccion = new ArrayList<>();
        for (int i = 0; i < instrucciones.size(); i++) {

            instruccion = instrucciones.get(i);
            if (instruccion.isEmpty() == false) {
                BancoRegistros.setRegistro(15, pc);
                pc = pc + 4;
                if (instruccion.size() > 2) {
                    decodificacion(instruccion);
                } else { //Significa que la instruccion es un branch
                    if (instruccion.size() == 2) {//Se ignora los labels
                        int indiceNuevo = i;//Este indice indica a partir de donde esta el label al que se quiere saltar
                        String tipoSalto = instruccion.get(0);

                        if (tipoSalto.equals("b")) { //Branch incondicional
                            indiceNuevo = bancoBranch.buscarBranchSimulacion(instruccion.get(1));
                            //System.out.println("El indice de salto es " + indiceNuevo);
                        } else if (tipoSalto.equals("beq")) { //Branch on equal
                            //System.out.println("branch on equal");
                            if (bancoBanderas.getZ() == 1) {
                                indiceNuevo = bancoBranch.buscarBranchSimulacion(instruccion.get(1));
                                //System.out.println("El indice de salto es " + indiceNuevo);
                            }
                        } else if (tipoSalto.equals("bne")) { //Branch non equal
                            //System.out.println("branch non equal");
                            if (bancoBanderas.getZ() == 0) {
                                indiceNuevo = bancoBranch.buscarBranchSimulacion(instruccion.get(1));
                                //System.out.println("El indice de salto es " + indiceNuevo);
                            }
                        } else if (tipoSalto.equals("bgt")) { //Branch grater than
                            //System.out.println("branch grater than");
                            int Z = bancoBanderas.getZ();
                            int N = bancoBanderas.getN();
                            int V = bancoBanderas.getV();
                            //System.out.println("La operacion es " + (~Z & ~(N ^ V)));
                            if ((~Z & ~(N ^ V)) == -1) {
                                indiceNuevo = bancoBranch.buscarBranchSimulacion(instruccion.get(1));
                                //System.out.println("El indice de salto es " + indiceNuevo);
                            }
                        } else if (tipoSalto.equals("blt")) { //Branch less than
                            //System.out.println("branch less than");
                            int N = bancoBanderas.getN();
                            int V = bancoBanderas.getV();
                            //System.out.println("La operacion es " + (N ^ V));
                            if ((N ^ V) == 1) {
                                indiceNuevo = bancoBranch.buscarBranchSimulacion(instruccion.get(1));
                                //System.out.println("El indice de salto es " + indiceNuevo);
                            }
                        }

                        if (indiceNuevo != -1) {
                            i = indiceNuevo;
                        } else {
                            reportarError("La etiqueta ingresada no existe", "Etiqueta invalida");
                        }
                    }
                }
            }
        }

    }

    public void reportarError(String frase1, String frase2) {
        Memoria memoria = new Memoria();

        JOptionPane.showMessageDialog(new JFrame(), frase1, frase2, JOptionPane.ERROR_MESSAGE);
        BancoInstrucciones.borrarInstrucciones();
        BancoBanderas.borrarBanderas();
        BancoRegistros.borrarRegistros();
        ManejadorErrores.borrarErrores();
        BancoBranch.borrarBranch();
        Decodificacion.borrarEnsamblaje();
        try {
            memoria.inicia_memoria();
        } catch (IOException ex) {
            Logger.getLogger(Simulacion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void agregaBranch(ArrayList<ArrayList<String>> instrucciones) {
        int cont = 0;
        ArrayList<String> instruccion = new ArrayList<>();
        for (int i = 0; i < instrucciones.size(); i++) {
            instruccion = instrucciones.get(i);
            if (instruccion.size() == 1) {//Significa que es un label
                bancoBranch.setBandera(i - cont, instruccion.get(0));
                cont++;
            }
        }
    }

    private void agregaBranchSimulacion(ArrayList<ArrayList<String>> instrucciones) {
        ArrayList<String> instruccion = new ArrayList<>();
        for (int i = 0; i < instrucciones.size(); i++) {
            instruccion = instrucciones.get(i);
            if (instruccion.size() == 1) {//Significa que es un label
                bancoBranch.setBanderaSimulacion(i, instruccion.get(0));
            }
        }
    }

    private void decodificacion(ArrayList<String> instruccion) {
        ALU ALU = new ALU();
        SetBanderas setBanderas = new SetBanderas();

        System.out.println("La instruccion recibida en la decodificacion es " + instruccion);
        String nemonico = instruccion.get(0);
        String destino = instruccion.get(1);
        if (destino.equals("r15")) { // caso de no pc quitar esta linea
            reportarError("El registro R15 no es válido como destino", "");
        }
        String operando1 = instruccion.get(2);
        String operando2;
        int size = instruccion.size();
        
        if (size == 3) {//instruccion de dos registros                 
            operando1 = instruccion.get(1);
            operando2 = instruccion.get(2);
            //System.out.println("operando 2" + operando2);
        } else if (size == 4) {//instruccion de tres registros o de un registro con imm
            if (instruccion.get(2).equals("#"))//instruccion de un registro con imm
            {
                operando1 = instruccion.get(1);
                //System.out.println("instruccion con inmediato:"+operando1);
            }
            operando2 = instruccion.get(3);
            if (operando2.equals("r15")){
                reportarError("El registro R15 no es válido como operando2", "");
            }
           
        } else //instruccion de dos registros con imm
        {
            operando2 = instruccion.get(4);

        }

        switch (nemonico) {
            case "and":
                ALU.ALU(destino, operando1, operando2, "and");
                break;
            case "eor":
                ALU.ALU(destino, operando1, operando2, "xor");
                break;
            case "eorv":
                ALUVectorial.ALUVectorial(destino, operando1, operando2, "xor");
                break;
                
            case "sub":
                ALU.ALU(destino, operando1, operando2, "resta");
                break;
             case "subv":
                ALUVectorial.ALUVectorial(destino, operando1, operando2, "subv");
                break;
  
            case "rsb":
                ALU.ALU(destino, operando1, operando2, "resta inversa");
                break;
            case "add":
                ALU.ALU(destino, operando1, operando2, "suma");
                break;
            
            case "addv":
                ALUVectorial.ALUVectorial(destino, operando1, operando2, "addv");
                break;
                
            case "adc":
                ALU.ALU(destino, operando1, operando2, "suma con carry");
                break;
            case "sbc":
                ALU.ALU(destino, operando1, operando2, "resta con carry");
                break;
            case "rsc":
                ALU.ALU(destino, operando1, operando2, "resta inversa con carry");
                break;
            case "orr":
                ALU.ALU(destino, operando1, operando2, "or");
                break;
            case "mvn":
                ALU.ALU(destino, operando1, operando2, "mvn");
                break;
            case "bic":
                ALU.ALU(destino, operando1, operando2, "bitwise clear");
                break;
            case "mul":
                ALU.ALU(destino, operando1, operando2, "mul");
                break;
            case "cmp":
                setBanderas.cmp(operando1, operando2, 0);
                break;
            case "cmn":
                setBanderas.cmp(operando1, operando2, 1);
                break;
            case "mov":
                ALU.ALU(destino, operando1, operando2, "mov");
                break;
            case "movv":
                ALUVectorial.ALUVectorial(destino, operando1, operando2, "movv");
                break;    
       
            case "lsl":
                ALU.ALU(destino, operando1, operando2, "shift left");
                break;           
            case "lslv":
                ALUVectorial.ALUVectorial(destino, operando1, operando2, "shift left");
                break;
            case "lsrv":
                ALUVectorial.ALUVectorial(destino, operando1, operando2, "shift right");
                break;
            case "asr":
                ALU.ALU(destino, operando1, operando2, "shift right aritmetico");
                break;
            case "rrx":
                ALU.rotate(destino, operando1);
                break;
            case "ror":
                ALU.rotate(destino, operando1, operando2);
                break;
            case "rorv":
                ALUVectorial.rotateVectorialR(destino, operando1, operando2);
                break;
            case "rolv":
                ALUVectorial.rotateVectorialL(destino, operando1, operando2);
                break;
            case "mla":
                ALU.MLA(destino, operando1, instruccion.get(3), instruccion.get(4));
                break;
            case "str":
                ALU.MEM(destino, operando1, operando2, "str");
                break;   
             case "strv":
                ALUVectorial.MEM(destino, operando1, operando2, "strv");
                break;  
                       
                
            case "ldr":
                ALU.MEM(destino, operando1, operando2, "ldr");
                break;
             case "ldrv":
                ALUVectorial.MEM(destino, operando1, operando2, "ldrv");
                break;    
                
            case "strb":
                ALU.MEM(destino, operando1, operando2, "strb");
                break;
            case "ldrb":
                ALU.MEM(destino, operando1, operando2, "ldrb");
                break;
            default:
                //System.out.println("Default switch decodificacion");
                break;
        }
    }
}