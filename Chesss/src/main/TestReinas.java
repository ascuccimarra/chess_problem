package main;
 
import java.util.ArrayList;
import java.util.List;
 
public class TestReinas {
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        NReinasMio reinas= new NReinasMio(8,5,1,1);
        //reinas.buscarUnaSolucion();
        reinas.buscarSoluciones2();
        List<Board> solutions = reinas.getSolutions();
        
        for (int i = 0; i < solutions.size(); i++){
            Board b = solutions.get(i);
            System.out.println("solucion: " + i);
            System.out.println(b);
            System.out.println("");
        }
        
//        NReinas reinas = new NReinas(8);
//        //reinas.buscarUnaSolucion();
//        reinas.buscarSoluciones();
//        ArrayList soluciones = reinas.getSoluciones();
//        for (int i = 0; i<soluciones.size();i++){
//            int[] aux  = (int[]) soluciones.get(i);
//            System.out.println("Solucion " + (i+1) + ":");
//            for (int j = 0; j<aux.length;j++){
//                System.out.print("(" + (j+1) + "," + (aux[j]+1) + ")");
//            }
//            System.out.println("");
//        }
 
 
    }
}