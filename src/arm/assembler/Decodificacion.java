/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arm.assembler;

import arm.scalar.BancoBanderas;
import arm.scalar.BancoBranch;
import arm.scalar.BancoRegistros;
import arm.compiler.BancoInstrucciones;
import arm.compiler.ManejadorErrores;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author jaam
 */
public class Decodificacion {

    BancoRegistros bancoRegistros = new BancoRegistros();
    BancoBanderas bancoBanderas = new BancoBanderas();
    BancoBranch bancoBranch = new BancoBranch();
    BancoInstrucciones bancoInstrucciones = new BancoInstrucciones();
    ManejadorErrores moduloErrores = new ManejadorErrores();
    String cond, op, I, cmd, S, Rn, Rd, shamt5, sh, Rm;
    String Ra = "0000";
    String P, U, B, W, L;
    String instruccion_Comp = "";
    static String instruccionesString = "Ensamblaje:";
    String aux2 = "";
    String aux = "";
    int num_decimal = 0;
    String Scr2;
    String instruccion_Completa;
    String inst_hexa;
    int contador_instrucciones = 0;
    int direccion = 0;
    boolean bandera = true;

    public void inicio(ArrayList<ArrayList<String>> instrucciones) throws IOException {
        for (int i = 0; i < instrucciones.size(); i++) {
            decodificar(instrucciones.get(i));
        }
    }

    public void decodificar(List lista) throws IOException {
        Deco_Codigo_Instruccion cod_inst = new Deco_Codigo_Instruccion();
        BancoBranch bancobranch = new BancoBranch();

        if (lista.size() > 0) {
            String inst = (String) lista.get(0);
            cod_inst.cmd_inst(inst);
            op = cod_inst.getOp();
            cmd = cod_inst.getCmd();
            cond = cod_inst.getcond();

            if (lista.size() == 1) { // Caso de que sea un label
                inst_hexa = "00000000";
                Modificar(inst_hexa, contador_instrucciones);
            } else if (lista.size() == 2) { // Caso de que branch 
                int pos_inst = contador_instrucciones;
                int inmediato;
                int pos_label = bancobranch.buscarBranch((String) lista.get(1));

                if (pos_label < pos_inst) {
                    inmediato = ((pos_label * 4) - (pos_inst * 4) - 8) / 4;
                } else {
                    inmediato = ((pos_label * 4) - (pos_inst * 4) - 8) / 4;
                }

                String binary = extension_signo(Integer.toBinaryString(inmediato), 24);
                instruccion_Comp = extension_signo(Concatenar_Branch(cond, op, "10", binary), 8);
                Modificar(instruccion_Comp, contador_instrucciones);
                contador_instrucciones += 1;
                direccion += 4;
            } else { // Demas instrucciones
                Rd = cod_inst.numero_registro((String) lista.get(1));
                Rn = cod_inst.numero_registro((String) lista.get(2));
                S = "0"; // Considerando que no hay banderas, sino este si cambia

                if (inst.equals("cmp") || inst.equals("cmn")) {
                    S = "1";
                }
                if (lista.size() == 4) {
                    if (lista.get(2).equals("#")) {
                        aux = (String) lista.get(2); // Tiene el #
                        aux2 = (String) lista.get(3); // tiene el inmediato en string   
                        num_decimal = Integer.parseInt(aux2); // tiene el inmediato en decimal
                    } else {
                        aux = (String) lista.get(3); // Tiene el #
                        Rm = cod_inst.numero_registro((String) lista.get(3));
                    }
                } else if (lista.size() == 3) {
                    System.out.println("entre");
                    Rn = "0000";
                    Rm = cod_inst.numero_registro((String) lista.get(2));

                } else if (lista.size() == 5) {
                    aux = (String) lista.get(3); // Tiene el #
                    aux2 = (String) lista.get(4);

                    if (aux2.contains("r") || aux2.contains("R")) { // Caso de que la instuccion sea MLA
                        Ra = cod_inst.numero_registro((String) lista.get(4));
                        Rm = cod_inst.numero_registro((String) lista.get(3));
                    }
                }

                int numero_inst = cod_inst.getTipo();

                if (aux.contains("#")) { // Si existe inmediato
                    num_decimal = Integer.parseInt(aux2); // tiene el inmediato en decimal
                    aux = "";
                    I = "1";

                    String binary = Integer.toBinaryString(num_decimal); // convierto el inmediato a cod binario
                    switch (numero_inst) {
                        case 0: // Instrucciones de procesamiento de datos
                            if (num_decimal > 255) {
                                Scr2 = inmediato(num_decimal);
                            } else {
                                String aux_inm = binary;
                                for (int j = 0; j < (12 - binary.length()); j++) {
                                    aux_inm = "0" + aux_inm;
                                }
                                Scr2 = aux_inm;
                            }

                            if (inst.equals("cmp") || inst.equals("cmn")) {
                                instruccion_Comp = Concatenar_Data_1(cond, op, I, cmd, S, Rd, Rn, Scr2);
                            } else {
                                instruccion_Comp = Concatenar_Data_1(cond, op, I, cmd, S, Rn, Rd, Scr2);

//                                System.out.println("cond es " + cond);
//                                System.out.println("Op es " + op);
//                                System.out.println("I es " + I);
//                                System.out.println("Cmd es " + cmd);
//                                System.out.println("S es " + S);
//                                System.out.println("Rn es " + Rn);
//                                System.out.println("Rd es " + Rd);
//                                System.out.println("Shamt5 es " + Scr2);
                            }
                            break;
                        case 2:
                            I = "0";
                            P = "1";
                            W = "0";
                            B = cod_inst.getB();
                            L = cod_inst.getL();
                            U = "1"; // Suponiendo que todo numero es positivo
                            String aux_inm = binary;
                            for (int j = 0; j < (12 - binary.length()); j++) {
                                aux_inm = "0" + aux_inm;
                            }
                            Scr2 = aux_inm;
                            instruccion_Comp = Concatenar_Memory_1(cond, op, I, P, U, B, W, L, Rn, Rd, Scr2);
                            break;

                        case 4:
                            String aux_inm2 = binary;
                            for (int j = 0; j < (5 - binary.length()); j++) {
                                aux_inm2 = "0" + aux_inm2;
                            }
                            shamt5 = aux_inm2;
                            sh = cod_inst.getsh();
                            instruccion_Comp = Concatenar_Data_2(cond, op, "0", cmd, S, "0000", Rd, shamt5, sh, "0", Rn);
                            break;
                    }

                } else { // Si no existe inmediato
                    I = "0";
                    shamt5 = "00000";
                    sh = "00";
                    System.out.println("entreeeeee");
                    System.out.println("numero de instruccion es" + numero_inst);

                    switch (numero_inst) {
                        case 0: // Instrucciones de procesamiento de datos
                            if (inst.equals("cmp") || inst.equals("cmn")) {
                                instruccion_Comp = Concatenar_Data_2(cond, op, I, cmd, S, Rd, Rn, shamt5, sh, "0", Rm);
                            } else {
                                instruccion_Comp = Concatenar_Data_2(cond, op, I, cmd, S, Rn, Rd, shamt5, sh, "0", Rm);

                            }

                            break;
                        case 1: // Instrucciones de multiplicacion
                            instruccion_Comp = Concatenar_Multiply(cond, op, "00", cmd, S, Rd, Ra, Rm, "1001", Rn);
                            break;

                        case 2: // Instrucciones de memoria
                            I = "1";
                            P = "1";
                            W = "0";
                            B = cod_inst.getB();
                            L = cod_inst.getL();
                            U = "1"; // Suponiendo que todo numero es positivo
                            instruccion_Comp = Concatenar_Memory_2(cond, op, I, P, U, B, W, L, Rn, Rd, shamt5, sh, "0", Rm);
                            break;

                        case 4: // Instrucciones de memoria         
                            sh = cod_inst.getsh();
                            instruccion_Comp = Concatenar_Data_2(cond, op, "0", cmd, S, "0000", Rd, Rm, "0" + sh, "1", Rn);
                            break;

                        case 5: // RRX
                            sh = cod_inst.getsh();
                            instruccion_Comp = Concatenar_Data_2(cond, op, "0", cmd, S, "0000", Rd, Rn, "0" + sh, "0", Rm);
                            break;
                    }

                }
//            System.out.println("Cond es " + cond);
//            System.out.println("Op es " + op);
//            System.out.println("I es " + I);
//            System.out.println("S es " + S);
//            System.out.println("Shamt5 es " + shamt5);
//            System.out.println("Sh es " + sh);
//            System.out.println("Rn es " + Rn);
//            System.out.println("Rd es " + Rd);
//            System.out.println("Cmd es " + cmd);
//            System.out.println("Rm es " + Rm);
//            System.out.println("inmediato" + Scr2);
                Modificar(instruccion_Comp, contador_instrucciones);
                contador_instrucciones += 1;
                direccion += 4;
            }
        }

        //BancoRegistros.setRegistro(15, direccion+4+(bancobranch.getBrach().size()*4)); // caso no de usar pc eliminar esta linea
    }

    public void Modificar(String instruccion, int pos) throws IOException {
        String dir_hexa = extension_signo(Integer.toHexString(direccion), 8);
        String dato = "Memory Address: " + dir_hexa.toUpperCase() + "             " + "Instruction: " + pos + "  " + instruccion.toUpperCase();
        instruccionesString = instruccionesString + "\n" + dato;

        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("out.txt", false);
            pw = new PrintWriter(fichero, false);
            pw.println(dato);
            pw.flush();
            pw.close();
            fichero.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fichero) {
                fichero.close();
            }
            try {
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    public String obtenerEnsamblaje() {
        return instruccionesString;
    }

    public static void borrarEnsamblaje() {
        instruccionesString = "";
    }

    public String Concatenar_Data_1(String cond, String op, String I, String cmd, String S, String Rn, String Rd, String Src2) {
        instruccion_Completa = cond + op + I + cmd + S + Rn + Rd + Src2;
        inst_hexa = binariotohexa(instruccion_Completa);
        return inst_hexa;
    }

    public String Concatenar_Data_2(String cond, String op, String I, String cmd, String S, String Rn, String Rd, String shamt5, String sh, String bit4, String Rm) {
        instruccion_Completa = cond + op + I + cmd + S + Rn + Rd + shamt5 + sh + bit4 + Rm;
        inst_hexa = binariotohexa(instruccion_Completa);
        return inst_hexa;
    }

    public String Concatenar_Multiply(String cond, String op, String bit_25_24, String cmd, String S, String Rd, String Ra, String Rm, String bit4, String Rn) {
        instruccion_Completa = cond + op + bit_25_24 + cmd + S + Rd + Ra + Rm + bit4 + Rn;
        inst_hexa = binariotohexa(instruccion_Completa);
        return inst_hexa;
    }

    public String Concatenar_Memory_2(String cond, String op, String I, String P, String U, String B, String W, String L, String Rn, String Rd, String shamt5, String sh, String bit4, String Rm) {
        instruccion_Completa = cond + op + I + P + U + B + W + L + Rn + Rd + shamt5 + sh + bit4 + Rm;
        inst_hexa = binariotohexa(instruccion_Completa);
        return inst_hexa;
    }

    public String Concatenar_Memory_1(String cond, String op, String I, String P, String U, String B, String W, String L, String Rn, String Rd, String Src2) {
        instruccion_Completa = cond + op + I + P + U + B + W + L + Rn + Rd + Src2;
        inst_hexa = binariotohexa(instruccion_Completa);
        return inst_hexa;
    }

    public String Concatenar_Branch(String cond, String op, String I, String Inmediato) {
        instruccion_Completa = cond + op + I + Inmediato;
        inst_hexa = binariotohexa(instruccion_Completa);
        return inst_hexa;
    }

    public double decimaltobinary(int numero) {
        int exp, digito;
        double binario;
        while (numero < 0);
        exp = 0;
        binario = 0;
        while (numero != 0) {
            digito = numero % 2;
            binario = binario + digito * Math.pow(10, exp);
            exp++;
            numero = numero / 2;
        }
        return binario;
    }

    public String binariotohexa(String valor) {
        long dec = Long.parseLong(valor, 2);
        valor = Long.toHexString(dec);
        return valor;
    }

    public String extension_signo(String binario, int limite) {
        String ext;
        String aux_inm = binario;
        if (binario.length() > 24) {
            ext = binario.substring(binario.length() - 24, binario.length());
        } else {
            for (int j = 0; j < (limite - binario.length()); j++) {
                aux_inm = "0" + aux_inm;
            }
            ext = aux_inm;
        }
        return ext;
    }

    public void revisa_inmediato(int inme_decimal) throws IOException {
        String inmediato = extension_signo_inme(Integer.toBinaryString(inme_decimal), 32);
        boolean bandera_primeruno = true;
        int contador = 0;
        for (int j = 0; j < 32; j++) {
            if (bandera_primeruno) {
                if ((inmediato.charAt(31 - j)) == '1') {
                    contador = j;
                    bandera_primeruno = false;
                }
            } else {
                if (!bandera_primeruno && j > contador + 8) {
                    if ((inmediato.charAt(31 - j)) == '1') {
                        JOptionPane.showMessageDialog(new JFrame(), "Error: Valor " + inme_decimal + " no puede ser utilizado como operando2",
                                "Error: Valor " + inme_decimal + " no puede ser utilizado como operando2", JOptionPane.ERROR_MESSAGE);
                        BancoInstrucciones.borrarInstrucciones();
                        BancoBanderas.borrarBanderas();
                        BancoRegistros.borrarRegistros();
                        ManejadorErrores.borrarErrores();
                        BancoBranch.borrarBranch();
                        borrarEnsamblaje();
                        break;
                    }
                }
            }
        }
    }

    public String inmediato(int inmed_dec) throws IOException {
        int cont_rot = 0;
        String Src2;
        boolean bandera = true;

        revisa_inmediato(inmed_dec);

        while (bandera) {
            if (inmed_dec == 256) {
                inmed_dec = 1;
                cont_rot += 8;
            } else if (inmed_dec > 255) {
                cont_rot += 1;
                inmed_dec = inmed_dec / 2;
            } else {
                bandera = false;
            }
        }
        if (cont_rot == 1) {
            cont_rot = 2;
            inmed_dec = inmed_dec / 2;
        }
        String inme_binario = Integer.toBinaryString(inmed_dec);
        String aux = inme_binario;
        for (int j = 0; j < (8 - inme_binario.length()); j++) {
            aux = "0" + aux;
        }
        double rot_aux = decimaltobinary((32 - cont_rot) / 2);
        int i = (int) rot_aux;
        String rot = Integer.toString(i);
        //System.out.println("Rot: " + rot);
        //System.out.println("Inm: " + aux);
        Src2 = rot + aux;
        return Src2;

    }

    public String extension_signo_inme(String binario, int limite) {
        String ext;
        String aux_inm = binario;
        for (int j = 0; j < (limite - binario.length()); j++) {
            aux_inm = "0" + aux_inm;
        }
        ext = aux_inm;
        return ext;
    }
}
