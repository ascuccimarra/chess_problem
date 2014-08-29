package main;
 
import java.util.ArrayList;
import java.util.List;
 
public class NReinasMio {
 
    private boolean[] horizontal;
    private boolean[] vertical;
    private boolean[] diagonalSuperior;
    private boolean[] diagonalInferior;
    private int n; 
    private Board solutionBoard;
    private boolean haySolucion;
    private List<Board> solutions = new ArrayList<>();
    private final int cantReinas;
    private int aPoner;
 
    public NReinasMio(int tamanio, int cantReinas){
        this.cantReinas = cantReinas;
        
        if (tamanio < 4) throw new NullPointerException();
        this.n = tamanio;
        inicializar();
    }
 
    private void inicializar(){
        this.horizontal = new boolean[n];
        this.vertical = new boolean[n];
        this.solutionBoard = new Board(n, n);
        for (int i = 0; i<n;i++){
            this.horizontal [i] = true;
            this.vertical [i] = true;
        }
        this.diagonalInferior = new boolean[2*n-1];
        this.diagonalSuperior = new boolean[2*n-1];
        for (int i = 0; i<2*n-1;i++){
            this.diagonalInferior[i] = true;
            this.diagonalSuperior[i] = true;
        }
        haySolucion = false;
        this.aPoner = cantReinas;
    }
    
    private void buscarSolucion2(int row, int col, Boolean exito){
        if (sePuedeColocar(row, col)){
           //System.out.println("pongo " + row + " " + col);
           poner(row, col);
           //System.out.println(aPoner);
            if (termine(row, col)){
                
                if (aPoner == 0){
                    if (isNewSolution(solutionBoard)){
                        solutionBoard.print();
                        haySolucion = true;
                        addSolution();
                        sacar(row, col);
                    }
                }else{
                    exito = true;
                }
            } else{
                int j = col + 1;
                for (int i = row; i < n; i++){
                    while (j < n){
                        //System.out.println("i: " + i + " j: " + j);
                        //if (!exito){
                            buscarSolucion2(i, j, exito);
                        //}
                        
                        j++;
                    }
                    j = 0;
                }
                if (!exito){
                    //System.out.println("saco " + row + " " + col);
                    sacar(row, col);
                }
            }
        }
    }
    
    private boolean pase(int row, int col){
        //System.out.println(solutionBoard.matrix[row][col].piece);
        return !solutionBoard.matrix[row][col].piece.equals(Cell.EMPTY_CELL);
    }
    
    private boolean termine(int row, int col){
        return (row == n-1 && col == n-1) || aPoner == 0;
    }
    
    private void poner(int row, int col){
        solutionBoard.putPiece(row, col, "R");
        horizontal[row] = false;
        vertical[col] = false;
        diagonalInferior[col-row+n-1] = false;
        diagonalSuperior[col+row] = false;
        aPoner--;
    }
    
    private void sacar(int row, int col){
        solutionBoard.resetCell(row, col);
        horizontal[row] = true;
        vertical[col] = true;
        diagonalInferior[col-row+n-1] = true;
        diagonalSuperior[col+row] = true;
        aPoner++;
    }
    
    private boolean sePuedeColocar(int row, int col){
        return horizontal[row] && vertical[col] && diagonalInferior[col-row+n-1] && diagonalSuperior[col+row];
    }
 
    private void buscarSolucion(int fila){
        int col = 0;
        while (col < n && !haySolucion){
            if (horizontal[fila] && vertical[col] && diagonalInferior[col-fila+n-1] && diagonalSuperior[col+fila]){
                
                solutionBoard.putPiece(fila, col, "R");
                horizontal[fila] = false;
                vertical[col] = false;
                diagonalInferior[col-fila+n-1] = false;
                diagonalSuperior[col+fila] = false;
 
                if (fila == n-1 /*&& solucionNueva(this.solucion) */ && isNewSolution(solutionBoard)){
                    haySolucion = true;
                }else{
                    if (fila+1 < n ){
                        buscarSolucion(fila+1); 
                    }
                    if (!haySolucion){                  
                        solutionBoard.resetCell(fila, col);
                        horizontal[fila] = true;
                        vertical[col] = true;
                        diagonalInferior[col-fila+n-1] = true;
                        diagonalSuperior[col+fila] = true;
 
                    }
                }
            }
            col++;
        }
    }
    
    public void buscarSoluciones2(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Boolean exito = false;
                //System.out.println("voy con " + i + " " + j);
                buscarSolucion2(i, j, exito);
                inicializar();
            }
        }
    }
    public void buscarSoluciones(){
        boolean flag = true;
        while(flag){
            buscarSolucion(0);
            if (isNewSolution(solutionBoard)){
                flag = true;
                addSolution();
            } else{
                flag = false;
            }
            inicializar();
        }

//        Boolean exito = false;
////        buscarSolucion2(0, 0, exito);
//        buscarSolucion2(0, 1, exito);
        
//        for (int i = 0; i < n; i++){
//            for (int j = 0; j < n; j++){
//                Boolean exito = false;
//                System.out.println("voy con " + i + " " + j);
//                buscarSolucion2(i, j, exito);
//                inicializar();
//            }
//        }
    }
    
    private void addSolution(){
        solutions.add(this.solutionBoard.cloneBoard());
    }
    
    private boolean isNewSolution(Board newSolutionBoard){
        if (newSolutionBoard.getCell(0, 0).piece.equals(Cell.EMPTY_CELL)){
            //return false;
        }//MMMM DUDA!!
        int i = 0;
        boolean esNueva = true;
        while (i < solutions.size() && esNueva){ 
            Board aBoard = solutions.get(i);
            if (aBoard.equals(newSolutionBoard)){
                esNueva = false;
            }
            i++;
        }
//        if (esNueva){
//            System.out.println(newSolutionBoard);
//        }
        return esNueva;
    }

    
    public List<Board> getSolutions(){
        return this.solutions;
    }
}