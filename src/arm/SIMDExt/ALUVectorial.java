/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arm.SIMDExt;

import arm.scalar.BancoRegistros;
import arm.scalar.Memoria;
import java.nio.ByteBuffer;


/**
 *
 * @author edwin
 */
public class ALUVectorial {
    
    public static void ALUVectorial(String destino, String nombreOperando1, String nombreOperando2, String operacion){
        
        byte[] resultado = new byte[4],operando2 ;
        byte[] operando1 = BancoVectores.getVector(Integer.parseInt(nombreOperando1.substring(1)));

        if (nombreOperando2.contains("v")) { //Significa que es un vector
 
            operando2 = BancoVectores.getVector(Integer.parseInt(nombreOperando2.substring(1)));
        } else { //Significa que es un inmediato
                operando2=immMov(nombreOperando2);     
        }
        
        
     
         switch (operacion) {
           
            case "movv":
                resultado=operando2;
                break;
            case "addv":
                resultado[0]=(byte)(operando1[0]+operando2[0]);
                resultado[1]=(byte)(operando1[1]+operando2[1]);
                resultado[2]=(byte)(operando1[2]+operando2[2]);
                resultado[3]=(byte)(operando1[3]+operando2[3]);
                break;
            case "subv":
                resultado[0]=(byte)(operando1[0]-operando2[0]);
                resultado[1]=(byte)(operando1[1]-operando2[1]);
                resultado[2]=(byte)(operando1[2]-operando2[2]);
                resultado[3]=(byte)(operando1[3]-operando2[3]);
                break;
            case "shift left":
                resultado[0]=(byte)(operando1[0]<< operando2[0]);
                resultado[1]=(byte)(operando1[1]<< operando2[1]);
                resultado[2]=(byte)(operando1[2]<< operando2[2]);
                resultado[3]=(byte)(operando1[3]<< operando2[3]);
                break;
            
            case "shift right":
                resultado[0]=(byte)(operando1[0]>> operando2[0]);
                resultado[1]=(byte)(operando1[1]>> operando2[1]);
                resultado[2]=(byte)(operando1[2]>> operando2[2]);
                resultado[3]=(byte)(operando1[3]>> operando2[3]);
                break; 
             case "xor":
                resultado[0]=(byte)(operando1[0] ^ operando2[0]);
                resultado[1]=(byte)(operando1[1] ^ operando2[1]);
                resultado[2]=(byte)(operando1[2] ^ operando2[2]);
                resultado[3]=(byte)(operando1[3] ^ operando2[3]);
                break;    
             
                
            default:
                break;
        }
        
         
    //    System.out.println("Resultado!: "+  resultado[0]);
     //   System.out.println("operacion!: "+ operacion);

         BancoVectores.setVector(Integer.parseInt(destino.substring(1)), resultado);

         
        
        
    }
    
    private static byte[] immMov(String nombreOperando2){
        byte[] operando2 = new byte[4];

        int tempOp2 = Integer.parseInt(nombreOperando2);
         String binaryOp2= Integer.toBinaryString(tempOp2);
         operando2[0]  = Byte.parseByte(binaryOp2, 2);
         operando2[1]  = Byte.parseByte(binaryOp2, 2);
         operando2[2]  = Byte.parseByte(binaryOp2, 2);
         operando2[3]  = Byte.parseByte(binaryOp2, 2);  
         return operando2;
    }
    
     public static void rotateVectorialR(String Rd, String Rn, String Src2){
        byte[] operando2 = new byte[4];
        byte[] resultado = new byte[4];
        byte[] operando1 = BancoVectores.getVector(Integer.parseInt(Rn.substring(1)));

        if (Src2.contains("v")) { //Significa que es un vector
            operando2 = BancoVectores.getVector(Integer.parseInt(Src2.substring(1)));
        } else { //Significa que es un inmediato
                operando2=immMov(Src2);     
        }
        
        resultado[0]= (byte)(((operando1[0] & 0xff)  >>> operando2[0]) | ((operando1[0] & 0xff) << (8 - operando2[0])));        
        resultado[1]= (byte)(((operando1[1] & 0xff)  >>> operando2[1]) | ((operando1[1] & 0xff) << (8 - operando2[1])));        
        resultado[2]= (byte)(((operando1[2] & 0xff)  >>> operando2[2]) | ((operando1[2] & 0xff) << (8 - operando2[2])));        
        resultado[3]= (byte)(((operando1[3] & 0xff)  >>> operando2[2]) | ((operando1[2] & 0xff) << (8 - operando2[2])));        
        BancoVectores.setVector(Integer.parseInt(Rd.substring(1)), resultado);

    }
     
     public static void rotateVectorialL(String Rd, String Rn, String Src2){
        byte[] operando2 = new byte[4];
        byte[] resultado = new byte[4];
        byte[] operando1 = BancoVectores.getVector(Integer.parseInt(Rn.substring(1)));
            
        if (Src2.contains("v")) { //Significa que es un vector
            operando2 = BancoVectores.getVector(Integer.parseInt(Src2.substring(1)));
        } else { //Significa que es un inmediato
                operando2=immMov(Src2);     
        }
        
        resultado[0]= (byte)(((operando1[0] & 0xff) << operando2[0]) | ((operando1[0] & 0xff) >>> (8 - operando2[0])));     
        resultado[1]= (byte)(((operando1[1] & 0xff) << operando2[1]) | ((operando1[1] & 0xff) >>> (8 - operando2[1])));     
        resultado[2]= (byte)(((operando1[2] & 0xff) << operando2[2]) | ((operando1[2] & 0xff) >>> (8 - operando2[2])));     
        resultado[3]= (byte)(((operando1[3] & 0xff) << operando2[3]) | ((operando1[3] & 0xff) >>> (8 - operando2[3])));     
        BancoVectores.setVector(Integer.parseInt(Rd.substring(1)), resultado);
        
    }
     
     
     public static void MEM(String Rd, String Rn, String Src2, String inst) {
        byte[] dato;
        long registro;
        long imm;
        
        System.out.println("realizando mem vectorial");   
        dato = BancoVectores.getVector(Integer.parseInt(Rd.substring(1)));
        System.out.println("Imprimiendo dato"+dato[0]);   

        int decimalData=fromByteArray(dato);
        
        String dato_hexa = Long.toString(decimalData, 16); //decimal to Hex

        
        //String dato_hexa = Integer.toString(fromByteArray(dato), 16); //decimal to Hex
        
        registro = BancoRegistros.getRegistro(Integer.parseInt(Rn.substring(1)));
       
        if (Src2.contains("r")) { //Significa que es un registro
            imm = BancoRegistros.getRegistro(Integer.parseInt(Src2.substring(1)));
        } else { //Significa que es un inmediato
            imm = Integer.parseInt(Src2);
        }

        switch (inst) {
            case "strv":
                Memoria.Store_word((int) (imm + registro), dato_hexa); // Revisar que por castear a int no se pierdan valores
                break;
          
        }
    }
     
    
     static int fromByteArray(byte[] bytes) {
     return ByteBuffer.wrap(bytes).getInt();
    }
     
    static byte[] toByteArray(int value) {
    return new byte[] { 
        (byte)(value >> 24),
        (byte)(value >> 16),
        (byte)(value >> 8),
        (byte)value };
}
     
     
     
     
     
    
    
    
}