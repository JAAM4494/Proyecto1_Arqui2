/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arm.assembler;

/**
 *
 * @author jaam
 */
public class Deco_Codigo_Instruccion {

    String cmd, op, B, L, cond,sh;
    int tipo;

    public void cmd_inst(String nombre) {
        //System.out.println(nombre);
        switch (nombre) {
            case "and":
                cmd = "0000";
                op = "00";
                tipo = 0;
                cond = "1110";
                sh = "00";
                break;
            case "eor":
                cmd = "0001";
                op = "00";
                tipo = 0;
                cond = "1110";
                sh = "00";
                break;
            case "sub":
                cmd = "0010";
                op = "00";
                tipo = 0;
                cond = "1110";
                sh = "00";
                break;
            case "rsb":
                cmd = "0011";
                op = "00";
                tipo = 0;
                cond = "1110";
                sh = "00";            
                break;
            case "add":
                cmd = "0100";
                op = "00";
                tipo = 0;
                cond = "1110";
                sh = "00";
                break;
            case "adc":
                cmd = "0101";
                op = "00";
                tipo = 0;
                cond = "1110";
                sh = "00";
                break;
            case "sbc":
                cmd = "0110";
                op = "00";
                tipo = 0;
                cond = "1110";
                sh = "00";
                break;
            case "rsc":
                cmd = "0111";
                op = "00";
                tipo = 0;
                cond = "1110";
                sh = "00";
                break;
            case "cmp":
                cmd = "1010";
                op = "00";
                tipo = 0;
                cond = "1110";
                sh = "00";
                break;
            case "cmn":
                cmd = "1011";
                op = "00";
                tipo = 0;
                cond = "1110";
                sh = "00";
                break;
            case "orr":
                cmd = "1100";
                op = "00";
                tipo = 0;
                cond = "1110";
                sh = "00";
                break;
            case "mov":
                cmd = "1101";
                op = "00";
                tipo = 0;
                cond = "1110";
                sh = "00";
                break;
            case "lsl":
                cmd = "1101";
                op = "00";
                tipo = 4;
                cond = "1110";
                sh = "00";
                break;
            case "asr":
                cmd = "1101";
                op = "00";
                tipo = 4;
                cond = "1110";
                sh = "10";
                break;
            case "rrx":
                cmd = "1101"; // REVISAR
                op = "00";
                tipo = 5;
                cond = "1110";
                sh = "11";
                break;
            case "ror":
                cmd = "1101"; // REVISAR
                op = "00";
                tipo = 4;
                cond = "1110";
                sh = "11";
                break;
            case "bic":
                cmd = "1110";
                op = "00";
                tipo = 0;
                cond = "1110";
                sh = "00";
                break;
            case "mvn":
                cmd = "1111";
                op = "00";
                tipo = 0;
                cond = "1110";
                sh = "00";
                break;
            /// Multiplicacion
            case "mul":
                cmd = "000";
                op = "00";
                tipo = 1;
                cond = "1110";
                sh = "00";
                break;
            case "mla":
                cmd = "001";
                op = "00";
                tipo = 1;
                cond = "1110";
                sh = "00";
                break;
            /// MEMORIA
            case "str":
                op = "01";
                B = "0";
                L = "0";
                tipo = 2;
                cond = "1110";
                sh = "00";
                break;
            case "ldr":
                op = "01";
                B = "0";
                L = "1";
                tipo = 2;
                cond = "1110";
                sh = "00";
                break;

            case "strb":
                op = "01";
                B = "1";
                L = "0";
                tipo = 2;
                cond = "1110";
                sh = "00";
                break;

            case "ldrb":
                op = "01";
                B = "1";
                L = "1";
                tipo = 2;
                cond = "1110";
                sh = "00";
                break;

            case "b":
                op = "10";
                L = "0"; // Revisar L
                tipo = 3;
                cond = "1110";
                sh = "00";
                break;

            case "beq":
                op = "10";
                L = "0"; // Revisar L
                tipo = 3;
                cond = "0000";
                sh = "00";
                break;

            case "bne":
                op = "10";
                L = "0"; // Revisar L
                tipo = 3;
                cond = "0001";
                sh = "00";
                break;

            case "blt":
                op = "10";
                L = "0"; // Revisar L
                tipo = 3;
                cond = "1011";
                sh = "00";
                break;
                
            case "bgt":
                op = "10";
                L = "0"; // Revisar L
                tipo = 3;
                cond = "1100";
                sh = "00";
                break;
            default:
                cmd = "0001";
                op = "00";
                tipo = 0;
                L = "0";
                B = "1";
                cond = "1110";
                sh = "00";
                break;

        }
    }

    public String getB() {
        return B;
    }

    public String getL() {
        return L;
    }

    public int getTipo() {
        return tipo;
    }

    public String getCmd() {
        //System.out.println("CMD ES " + cmd);
        return cmd;
    }

    public String getOp() {
        return op;
    }
    
    public String getcond() {
        return cond;
    }
    public String getsh() {
        return sh;
    }

    public String numero_registro(String registro) {
        String valor_registro = "0000";
        switch (registro) {
            case "r0":
                valor_registro = "0000";
                break;
            case "r1":
                valor_registro = "0001";
                break;
            case "r2":
                valor_registro = "0010";
                break;
            case "r3":
                valor_registro = "0011";
                break;
            case "r4":
                valor_registro = "0100";
                break;
            case "r5":
                valor_registro = "0101";
                break;
            case "r6":
                valor_registro = "0110";
                break;
            case "r7":
                valor_registro = "0111";
                break;
            case "r8":
                valor_registro = "1000";
                break;
            case "r9":
                valor_registro = "1001";
                break;
            case "r10":
                valor_registro = "1010";
                break;
            case "r11":
                valor_registro = "1011";
                break;
            case "r12":
                valor_registro = "1100";
                break;
            case "r13":
                valor_registro = "1101";
                break;
            case "r14":
                valor_registro = "1110";
                break;
            case "r15":
                valor_registro = "1111";
                break;
            default:
                //System.out.println("Default");
                valor_registro = "0000";
                break;
        }
        return valor_registro;
    }
}
