/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arm.scalar;

/**
 * @author jaam
 */
public class ALU {

    public static void ALU(String destino, String nombreOperando1, String nombreOperando2, String operacion) {
        long operando1, operando2;
        Simulacion simulacion = new Simulacion();
        
        operando1 = BancoRegistros.getRegistro(Integer.parseInt(nombreOperando1.substring(1)));
        if (nombreOperando2.contains("r")) { //Significa que es un registro
            operando2 = BancoRegistros.getRegistro(Integer.parseInt(nombreOperando2.substring(1)));
        } else { //Significa que es un inmediato
            operando2 = Integer.parseInt(nombreOperando2);
            if(operando2 > 31 && (operacion.equalsIgnoreCase("shift left") || operacion.equalsIgnoreCase("shift right aritmetico")))
                simulacion.reportarError("No es posible realizar un shift con un valor mayor a 31", "Error en corrimiento");
        }

        long resultado = 0;
        switch (operacion) {
            case "and":
                resultado = operando1 & operando2;
                break;
            case "xor":
                resultado = operando1 ^ operando2;
                break;
            case "resta":
                resultado = operando1 - operando2;
                break;
            case "resta inversa":
                resultado = operando2 - operando1;
                break;
            case "resta inversa con carry":
                int carry = (BancoBanderas.getC());
                if (carry == 0) {
                    carry = 1;
                } else {
                    carry = 0;
                }
                resultado = operando2 - operando1 - (carry);
                break;

            case "suma":
                resultado = operando1 + operando2;
                break;
            case "suma con carry":
                resultado = operando1 + operando2 + BancoBanderas.getC();
                break;
            case "or":
                resultado = operando1 | operando2;
                break;
            case "not":
                resultado = -1 * operando1;//Revisar esta operacion
                break;
            case "bitwise clear":
                resultado = operando1 & (~operando2);
                break;
            case "mul":
               resultado = reduce_bits(operando1 * operando2);
                 //Ver como agarrar solo los 32 bits menos significativos
                break;
            case "mov":
                resultado = operando2;
                break;
            case "mvn":
                resultado = (-1)*operando2;
                break;
            case "shift left":
                    resultado = operando1 << operando2;
                break;
            case "shift right aritmetico":
                resultado = operando1 >> operando2;
                break;

            default:
               // System.out.println("Default switch ALU");
                break;
        }

        BancoRegistros.setRegistro(Integer.parseInt(destino.substring(1)), resultado);
    }

    public static void MLA(String destino, String nombreOperando1, String nombreOperando2,String nombreOperando3) {
        long operando1, operando2, operando3, resultado;

        operando1 = BancoRegistros.getRegistro(Integer.parseInt(nombreOperando1.substring(1)));
        operando2 = BancoRegistros.getRegistro(Integer.parseInt(nombreOperando2.substring(1)));
        operando3 = BancoRegistros.getRegistro(Integer.parseInt(nombreOperando3.substring(1)));
        resultado = reduce_bits(operando1 * operando2 + operando3);
        BancoRegistros.setRegistro(Integer.parseInt(destino.substring(1)), resultado);
    }

    public static Long reduce_bits(Long resultado) {
        String resul = Long.toBinaryString(resultado);
        String aux = "";
        long resul_final;
        if (resul.length() <= 32) {
            resul_final = resultado;
        } else {
            for (int i = 0; i < 31; i++) {
                aux = (resul.charAt(32 - i)) + aux;
            }
            resul_final = Long.parseLong(aux);
        }
        return resul_final;
    }

    public static void MEM(String Rd, String Rn, String Src2, String inst) {
        long dato, registro, inm;

        dato = BancoRegistros.getRegistro(Integer.parseInt(Rd.substring(1)));
        String dato_hexa = Long.toString(dato, 16); //decimal to Hex
        registro = BancoRegistros.getRegistro(Integer.parseInt(Rn.substring(1)));
        if (Src2.contains("r")) { //Significa que es un registro
            inm = BancoRegistros.getRegistro(Integer.parseInt(Src2.substring(1)));
        } else { //Significa que es un inmediato
            inm = Integer.parseInt(Src2);
        }

        switch (inst) {
            case "str":
                Memoria.Store_word((int) (inm + registro), dato_hexa); // Revisar que por castear a int no se pierdan valores
                break;
            case "ldr":
                int nuevo_valor = Memoria.hex2decimal(Memoria.Load_word((int) (inm + registro)));
                String dato_decimal = Integer.toString(nuevo_valor);
                BancoRegistros.setRegistro(Integer.parseInt(Rd.substring(1)), Long.parseLong(dato_decimal));
                break;
            case "strb":
                Memoria.Store_byte((int) (inm + registro), dato_hexa);
                break;
            case "ldrb":
                long nuevo_valor2 = Memoria.hex2decimal(Memoria.Load_byte((int) (inm + registro)));
                String dato_decimal2 = Long.toString(nuevo_valor2);
                BancoRegistros.setRegistro(Integer.parseInt(Rd.substring(1)), Long.parseLong(dato_decimal2));
                break;
        }

    }

    public static void rotate(String Rd, String Rn, String Src2) {
        Simulacion simulacion = new Simulacion();

        long inm, Rn_valor;
        Rn_valor = BancoRegistros.getRegistro(Integer.parseInt(Rn.substring(1)));
        if (Src2.contains("r")) { //Significa que es un registro
            inm = BancoRegistros.getRegistro(Integer.parseInt(Src2.substring(1)));
        } else { //Significa que es un inmediato
            inm = Long.parseLong(Src2);
            if(inm > 31)
                simulacion.reportarError("No es posible realizar un shift con un valor mayor a 31", "Error en corrimiento");
        }
          
        String Aux = "", Aux2 = "", bit_rotate;
        String rn = extension_ceros(Long.toBinaryString(Rn_valor));

        for (int i = 0; i < 32; i++) {
            if (i < inm) {
                Aux = (rn.charAt(31 - i)) + Aux;
            } else {
                Aux2 = (rn.charAt(31 - i)) + Aux2;
            }
        }
        bit_rotate = Aux + Aux2;
        
        
        
        long bit_rotate2 = Long.parseLong(bit_rotate, 2);

        System.out.println("rotacion es " + bit_rotate2);
        BancoRegistros.setRegistro(Integer.parseInt(Rd.substring(1)), bit_rotate2);
        
    }

    public static void rotate(String Rd, String Src2) {
        long inm;
        String Aux="";
        if (Src2.contains("r")) { //Significa que es un registro
            inm = BancoRegistros.getRegistro(Integer.parseInt(Src2.substring(1)));
        } else { //Significa que es un inmediato
            inm = Long.parseLong(Src2);
        }

        String rn = extension_ceros(Long.toBinaryString(inm));
        String carry = Integer.toString(BancoBanderas.getC());
        
        for (int i = 0; i < 31; i++) {
            Aux =   Aux+(rn.charAt( i)); 
        }
        String bit_rotate = carry + Aux;
        long bit_rotate2 = Long.parseLong(bit_rotate, 2);
        BancoRegistros.setRegistro(Integer.parseInt(Rd.substring(1)), bit_rotate2);
    }

    private static String extension_ceros(String numero) {
        String aux_inm = numero;

        for (int j = 0; j < (32 - numero.length()); j++) {
            aux_inm = "0" + aux_inm;
        }
        return aux_inm;
    }
}
