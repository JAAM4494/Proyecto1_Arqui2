/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arm.scalar;

import java.util.ArrayList;

/**
 *
 * @author jaam
 */
public class BancoBranch {
    
    public static ArrayList<ArrayList<String>> bancoBranch = new ArrayList<ArrayList<String>>();
    public static ArrayList<ArrayList<String>> bancoBranchSimulacion = new ArrayList<ArrayList<String>>();
    
    public void setBandera(int indice, String bandera){
        ArrayList<String> branch = new ArrayList<String>();
        branch.add(Integer.toString(indice));
        branch.add(bandera);
        bancoBranch.add(branch);
    }
    
    public void setBanderaSimulacion(int indice, String bandera){
        ArrayList<String> branch = new ArrayList<String>();
        branch.add(Integer.toString(indice));
        branch.add(bandera);
        bancoBranchSimulacion.add(branch);
    }
    
    public void imprimirBranch(){
        for (int i = 0; i < bancoBranch.size(); i++) {
            System.out.println("");
            System.out.println("Impresion de branch");
            System.out.println(bancoBranch.get(i));
            System.out.println("");
        }
    }
    
    public int buscarBranch(String label){
        for (int i = 0; i < bancoBranch.size(); i++) {
            if(bancoBranch.get(i).get(1).equalsIgnoreCase(label))//Busca si el label almacenado es igual al label que se busca
                return Integer.parseInt(bancoBranch.get(i).get(0));//Retorna el indice de ese label
        }
        return -1;//Si no lo encuentra retorna -1
    }
    
    public int buscarBranchSimulacion(String label){
        for (int i = 0; i < bancoBranchSimulacion.size(); i++) {
            if(bancoBranchSimulacion.get(i).get(1).equalsIgnoreCase(label))//Busca si el label almacenado es igual al label que se busca
                return Integer.parseInt(bancoBranchSimulacion.get(i).get(0));//Retorna el indice de ese label
        }
        return -1;//Si no lo encuentra retorna -1
    }
    
    public static void borrarBranch(){
        bancoBranch.clear();
        bancoBranchSimulacion.clear();
    }
    
    public static ArrayList<ArrayList<String>> getBrach() {
        return bancoBranch;
    } 
}
