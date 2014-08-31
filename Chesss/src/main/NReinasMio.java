package main;
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
public class NReinasMio {
    
    //private int[] xCaballo;
    //private int [] yCaballo;
    private int[] xKing;
    private int [] yKing;
    private int n;
    Map<Cell, boolean [][]> allowedCells;
    private Board solutionBoard;
    private List<Board> solutions = new ArrayList<>();
    List<Cell> piezas = new ArrayList<>();
    private int puestas;
 
    public NReinasMio(int tamanio, int cantReinas, int cantTorre, int cantAlfil, int kings, int caballos){
        this.n = tamanio;
        allowedCells = new HashMap<>();
        for (int i = 0; i < cantReinas; i++){
            Cell c = new Cell("R" + i);
            piezas.add(c);
            boolean[][] aux = new boolean[n][n];
            allowedCells.put(c, aux);
        }
        for (int i = 0; i < cantTorre; i++){
            Cell c = new Cell("T" + i);
            piezas.add(c);
            boolean[][] aux = new boolean[n][n];
            allowedCells.put(c, aux);
        }
        for (int i = 0; i < cantAlfil; i++){
            Cell c = new Cell("A" + i);
            piezas.add(c);
            boolean[][] aux = new boolean[n][n];
            allowedCells.put(c, aux);
        }
        for (int i = 0; i < kings; i++){
            Cell c = new Cell("K" + i);
            piezas.add(c);
            boolean[][] aux = new boolean[n][n];
            allowedCells.put(c, aux);
        }
        for (int i = 0; i < caballos; i++){
            Cell c = new Cell("C" + i);
            piezas.add(c);
            boolean[][] aux = new boolean[n][n];
            allowedCells.put(c, aux);
        }
        inicializar();
    }
 
    private void inicializar(){
        xCaballo = new int[]{1, 1, 2, 2, -1, -1, -2, -2};
        yCaballo = new int[]{2, -2, 1, -1, 2, -2, 1, -1};
        xKing = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
        yKing = new int[]{-1, 0, 1, 1, -1, -1, 0, 1};
        for (boolean[][] aux: allowedCells.values()){
            for (int i = 0; i < n; i++){
                for (int j=0; j < n; j++){
                    aux[i][j] = true;
                }
            }
        }
        this.solutionBoard = new Board(n, n);
        this.puestas = 0;
    }
    
    private void buscarSolucion2(int row, int col, Cell pieza, Boolean exito){
        if (sePuedeColocar(row, col, pieza) && !estaPuesta(pieza)){
           //System.out.println("pongo " + row + " " + col);
           poner(row, col, pieza);
           //System.out.println(solutionBoard);
           //System.out.println(aPoner);
            if (termine(row, col)){
                //System.out.println(aPoner);
                
                if (puestas == piezas.size()){
                    if (isNewSolution(solutionBoard)){
                        //solutionBoard.print();
                        addSolution();
                        sacar(row, col, pieza);
                    }
                }else{
                    exito = true;
                }
            } else{
                int j = col;
                for (int i = row; i < n; i++){
                    while (j < n){
                        //System.out.println("i: " + i + " j: " + j);
                        //if (!exito){
                            if (puestas < piezas.size()){
                                buscarSolucion2(i, j, piezas.get(puestas), exito);
                            }
                        //}
                        
                        j++;
                    }
                    j = 0;
                }
                if (!exito){
                    //System.out.println("saco " + row + " " + col);
                    sacar(row, col, pieza);
                    //System.out.println(solutionBoard);
                }
            }
        }
    }
    
    private boolean termine(int row, int col){
        return (row == n-1 && col == n-1) || puestas == piezas.size();
    }
    
    private void poner(int row, int col, Cell pieza){
        //System.out.println(solutionBoard);
        solutionBoard.putPiece(row, col, pieza.piece);
        puestas++;
        toggleCell(row, col, pieza, false);
        
    }
    
    private boolean perteneceAlTablero(int row, int col){
        return row >= 0 && row < n && col >= 0 && col < n;
    }
    
    private void toggleCell(int row, int col, Cell pieza, boolean b){
        if (pieza.piece.startsWith("R")){
            for (int i = 0; i < n; i++){
                allowedCells.get(pieza)[row][i] = b; //merca fila
            }
            for (int i = 0; i < n; i++){
                allowedCells.get(pieza)[i][col] = b; //marca columna
            }
            marcarDiagonales(row, col, pieza, b);
        }else if (pieza.piece.startsWith("T")){
            for (int i = 0; i < n; i++){
                allowedCells.get(pieza)[row][i] = b;
            }
            for (int i = 0; i < n; i++){
                allowedCells.get(pieza)[i][col] = b;
            }
        }else if (pieza.piece.startsWith("A")){
            marcarDiagonales(row, col, pieza, b);
        }else if (pieza.piece.startsWith("C")){
            allowedCells.get(pieza)[row][col] = b;
            for (int i = 0; i < 8; i++){
                int x = xCaballo[i] + row;
                int y = yCaballo[i] + col;
                if (perteneceAlTablero(x, y)){
                    allowedCells.get(pieza)[x][y] = b;
                }
            }
        }else if (pieza.piece.startsWith("K")){
            allowedCells.get(pieza)[row][col] = b;
            for (int i = 0; i < 8; i++){
                int x = xKing[i] + row;
                int y = yKing[i] + col;
                if (perteneceAlTablero(x, y)){
                    allowedCells.get(pieza)[x][y] = b;
                }
            }
        }
    }
    
    private void sacar(int row, int col, Cell pieza){
        //System.out.println(solutionBoard);
        solutionBoard.resetCell(row, col);
        puestas--;
        toggleCell(row, col, pieza, true);
    }
    
    private void marcarDiagonales(int row, int col, Cell pieza, boolean b){
        int i = row;
        int j = col;
        while (i >= 0 && j >= 0){
            allowedCells.get(pieza)[i][j] = b;
            i--;
            j--;
        }
        i = row;
        j = col;
        while (i < n && j < n){
            allowedCells.get(pieza)[i][j] = b;
            i++;
            j++;
        }
        i = row;
        j = col;
        while (i >= 0 && j < n){
            allowedCells.get(pieza)[i][j] = b;
            i--;
            j++;
        }
        i = row;
        j = col;
        while (i < n && j >= 0){
            allowedCells.get(pieza)[i][j] = b;
            i++;
            j--;
        }
    }
    
//    public void printCells(Cell pieza){
//        String text = "";
//        for (int i = 0; i < n; i++){
//            for (int j = 0; j < n; j++){
//                if (allowedCells.get(pieza)[i][j]){
//                    text += "-";
//                }else{
//                    text += "x";
//                }
//                text += " ";
//            }
//            text += "\n";
//        }
//        System.out.println(text);
//        System.out.println("");
//    }
    
    private boolean sePuedeColocar(int row, int col, Cell pieza){
        if (!solutionBoard.matrix[row][col].piece.equals(Cell.EMPTY_CELL)) {
            return false;
        }
        for (boolean[][] aux: allowedCells.values()){
            if (!aux[row][col]){
                return false;
            }
        }
        if (pieza.piece.startsWith("R")){
            if (!sePuedenDiagonales(row, col)){
                return false;
            }
            if (!sePuedeFila(row, col)){
                return false;
            }
            if (!sePuedeColumna(row, col)){
                return false;
            }
        }else if (pieza.piece.startsWith("A")){
            if (!sePuedenDiagonales(row, col)){
                return false;
            }
        }else if (pieza.piece.startsWith("T")){
             if (!sePuedeFila(row, col)){
                return false;
            }
            if (!sePuedeColumna(row, col)){
                return false;
            }
        }else if (pieza.piece.startsWith("C")){
            for (int i = 0; i < 8; i++){
                int x = xCaballo[i] + row;
                int y = yCaballo[i] + col;
                if (perteneceAlTablero(x, y)){
                    if (!solutionBoard.matrix[x][y].piece.equals(Cell.EMPTY_CELL)) {
                        return false;
                    }
                }
            }
        }else if (pieza.piece.startsWith("K")){
            for (int i = 0; i < 8; i++){
                int x = xKing[i] + row;
                int y = yKing[i] + col;
                if (perteneceAlTablero(x, y)){
                    if (!solutionBoard.matrix[x][y].piece.equals(Cell.EMPTY_CELL)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    private boolean sePuedenDiagonales(int row, int col){
        Cell[][] aux = solutionBoard.matrix;
        int i = row;
        int j = col;
        while (i >= 0 && j >= 0){
            if (!aux[i][j].piece.equals(Cell.EMPTY_CELL)){
                return false;
            }
            i--;
            j--;
        }
        i = row;
        j = col;
        while (i < n && j < n){
            if (!aux[i][j].piece.equals(Cell.EMPTY_CELL)){
                return false;
            }
            i++;
            j++;
        }
        i = row;
        j = col;
        while (i >= 0 && j < n){
            if (!aux[i][j].piece.equals(Cell.EMPTY_CELL)){
                return false;
            }
            i--;
            j++;
        }
        i = row;
        j = col;
        while (i < n && j >= 0){
            if (!aux[i][j].piece.equals(Cell.EMPTY_CELL)){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
    
    private boolean sePuedeFila(int row, int col){
        Cell[][] aux = solutionBoard.matrix;
        for (int i=0; i < n; i++){
             if (!aux[row][i].piece.equals(Cell.EMPTY_CELL)){
                return false; 
             }
        }
        return true;
    }
    
    private boolean sePuedeColumna(int row, int col){
        Cell[][] aux = solutionBoard.matrix;
        for (int i=0; i < n; i++){
             if (!aux[i][col].piece.equals(Cell.EMPTY_CELL)){
                return false; 
             }
        }
        return true;
    }

    public void buscarSoluciones2(){
        
//        Boolean exito = false;
//        buscarSolucion2(0, 2, piezas.get(0), exito);
//        buscarSolucion2(0, 1, piezas.get(0), exito);
//        boolean hayDistintas = true;
        int k = 0;
        Cell aux = null;
        while (k < piezas.size()) {
            if (aux == null || !aux.piece.startsWith(piezas.get(k-1).piece)) {
                aux = piezas.get(k);
                Collections.swap(piezas, 0, k);
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        Boolean exito = false;
                        //System.out.println("voy con " + i + " " + j);
                        //Cell aux = piezas.get(0);
                        buscarSolucion2(i, j, aux, exito);
                        inicializar();
                    }
                }
            }
            k++;
        }
    }

    
    private void addSolution(){
        solutions.add(this.solutionBoard.cloneBoard());
    }
    
    private boolean isNewSolution(Board newSolutionBoard){
        int i = 0;
        boolean esNueva = true;
        while (i < solutions.size() && esNueva){ 
            Board aBoard = solutions.get(i);
            if (aBoard.equals(newSolutionBoard)){
                esNueva = false;
            }
            i++;
        }
        return esNueva;
    }

    
    public List<Board> getSolutions(){
        return this.solutions;
    }

    private boolean estaPuesta(Cell pieza) {
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                if (solutionBoard.matrix[i][j].piece.equals(pieza.piece)){
                    return true;
                }
            }
        }
        return false;
    }
}