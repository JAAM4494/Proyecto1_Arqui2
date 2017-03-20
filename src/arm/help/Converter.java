package arm.help;

import java.math.BigInteger;


/**
 * Class
 * @author jaam
 */
public class Converter {
    
    public String decToBin(String pInput) {
        String retVal="";
        
        int temp = Integer.parseInt(pInput);
        retVal = Integer.toBinaryString(temp); // dec to bin
        
        return retVal;
    }
    
    public String decToHex(String pInput) {
        String retVal="";
        
        int temp = Integer.parseInt(pInput);
        retVal = Integer.toHexString(temp); // dec to bin
        retVal = retVal.toUpperCase();
        retVal = "0x" + retVal; 
        
        return retVal;
    }
    
    public String binToDec(String pInput) {
        String retVal="";
        
        int temp = Integer.parseUnsignedInt(pInput, 2);
        retVal = Integer.toString(temp);
        
        return retVal;
    }
    
    public String binToHex(String pInput) {
        String retVal="";
        
        int temp = Integer.parseUnsignedInt(pInput, 2);
        retVal = Integer.toHexString(temp);
        retVal = retVal.toUpperCase();
        retVal = "0x" + retVal; 
        
        return retVal;
    }
    
    public String hexToBin(String pInput) {
        String retVal="";
        
        retVal = pInput.replace("0x", "");
        BigInteger bi = new BigInteger(retVal, 16); // hex to bin
        retVal = bi.toString(2);
        
        return retVal;
    }
    
    public String hexToDec(String pInput) {
        String retVal="";
        
        retVal = pInput.replace("0x", "");
        int temp = Integer.parseUnsignedInt(retVal, 16); // hex to dec
        retVal = Integer.toString(temp);
        
        return retVal;
    }
    
}