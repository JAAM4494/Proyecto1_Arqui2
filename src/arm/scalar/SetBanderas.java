/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arm.scalar;

/**
 *
 * @author jaam
 */
public class SetBanderas {

    public static void cmp(String nombreOperando1, String nombreOperando2, int tipo) {
        BancoRegistros bancoRegistros = new BancoRegistros();
        long operando1, operando2;

        operando1 = bancoRegistros.getRegistro(Integer.parseInt(nombreOperando1.substring(1)));
        if (nombreOperando2.contains("r")) { //Significa que es un registro
            operando2 = bancoRegistros.getRegistro(Integer.parseInt(nombreOperando2.substring(1)));
        } else { //Significa que es un inmediato
            //System.out.println("Es un inmediato");
            operando2 = Integer.parseInt(nombreOperando2);
        }

        checkN(operando1, operando2);
        checkZ(operando1, operando2);
        checkV(operando1, operando2);
        checkC(operando1, operando2, tipo);
    }

    private static void checkN(long operando1, long operando2) {
        long resultado = operando1 - operando2;
        if (resultado < 0) {
            BancoBanderas.setN(1);
        } else {
            BancoBanderas.setN(0);
        }
    }

    private static void checkZ(long operando1, long operando2) {
        long resultado = operando1 - operando2;
        if (resultado == 0) {
            BancoBanderas.setZ(1);
        } else {
            BancoBanderas.setZ(0);
        }
    }

    private static void checkV(long operando1, long operando2) {
        long resultado = operando1 - operando2;
        String resultadoS = Long.toString(resultado, 2); // decimal to binary
        //Se busca el caracter 1 a partir del bit 32. Esto significaria overflow
        if (resultadoS.indexOf("1", 31) == -1) {//Retorna -1 cuando no lo encuentra. Significa que no hay overflow
            BancoBanderas.setV(0);
        } else {
            BancoBanderas.setV(1);
        }
    }

    private static void checkC(long operando1, long operando2, long tipo) {
        int carry = 0;
        long op1_binary = decimaltobinary(operando1);
        String op1 = Long.toString(op1_binary);
        String ope1_32b = extension_ceros(op1);
        String ope2_32b;
        if (tipo == 0) {
            ope2_32b = complementoADos(operando2);
        } else {
            ope2_32b = extension_ceros(Long.toString(operando2));
        }

        for (int j = 0; j < 32; j++) {
            if ((ope1_32b.charAt(31 - j)) == '1' && ope2_32b.charAt(31 - j) == '1' && carry == 1) {
                carry = 1;
            } else if (ope1_32b.charAt(31 - j) == '1' && ope2_32b.charAt(31 - j) == '1' && carry == 0) {
                carry = 1;
            } else if (ope1_32b.charAt(31 - j) == '0' && ope2_32b.charAt(31 - j) == '1' && carry == 1) {
                carry = 1;
            } else if (ope1_32b.charAt(31 - j) == '1' && ope2_32b.charAt(31 - j) == '0' && carry == 1) {
                carry = 1;
            } else {
                carry = 0;}
        }
        if (carry == 1) {
            BancoBanderas.setC(1);
        } else {
            BancoBanderas.setC(0);
        }
    }

    private static String complementoADos(long numero) {
        numero = ~(numero);//NOT
        numero = numero + 1;
        String resultado = Long.toBinaryString(numero);
        String aux_inm = resultado;
        char aux = resultado.charAt(0);

        for (int j = 0; j < (32 - resultado.length()); j++) {
            aux_inm = aux + aux_inm;
        }
        return aux_inm;
    }

    private static String extension_ceros(String numero) {
        String aux_inm = numero;

        for (int j = 0; j < (32 - numero.length()); j++) {
            aux_inm = "0" + aux_inm;
        }
        return aux_inm;
    }

    private static long decimaltobinary(long numero) {
        long exp, digito;
        long binario;
        while (numero < 0);
        exp = 0;
        binario = 0;
        while (numero != 0) {
            digito = numero % 2;
            binario = (long) (binario + digito * Math.pow(10, exp));
            exp++;
            numero = numero / 2;
        }
        return binario;
    }
}
