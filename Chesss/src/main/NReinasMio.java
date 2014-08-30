package main;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
public class NReinasMio {
 
    private boolean[] diagonalSuperior;
    private boolean[] diagonalInferior;
    private int n;
    Map<Cell, boolean [][]> allowedCells;
    private Board solutionBoard;
    private List<Board> solutions = new ArrayList<>();
    List<Cell> piezas = new ArrayList<>();
    private int puestas;
 
    public NReinasMio(int tamanio, int cantReinas, int cantTorre, int cantAlfil, int kings){
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
        //if (tamanio < 4) throw new NullPointerException();

        inicializar();
    }
 
    private void inicializar(){
        for (boolean[][] aux: allowedCells.values()){
            for (int i = 0; i < n; i++){
                for (int j=0; j < n; j++){
                    aux[i][j] = true;
                }
            }
        }
        
        //this.horizontal = new boolean[n];
        //this.vertical = new boolean[n];
        this.solutionBoard = new Board(n, n);
//        for (int i = 0; i<n;i++){
//            this.horizontal [i] = true;
//            this.vertical [i] = true;
//        }
        this.diagonalInferior = new boolean[2*n-1];
        this.diagonalSuperior = new boolean[2*n-1];
        for (int i = 0; i<2*n-1;i++){
            this.diagonalInferior[i] = true;
            this.diagonalSuperior[i] = true;
        }
        this.puestas = 0;
        //this.aPoner = cantReinas + cantTorre;
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
                            buscarSolucion2(i, j, piezas.get(puestas), exito);
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
        solutionBoard.putPiece(row, col, pieza.piece);
        puestas++;
        toggleCell(row, col, pieza, false);
        
    }
    
    private void toggleCell(int row, int col, Cell pieza, boolean b){
        if (pieza.piece.startsWith("R")){
            for (int i = 0; i < n; i++){
                allowedCells.get(pieza)[row][i] = b; //merca fila
            }
            for (int i = 0; i < n; i++){
                allowedCells.get(pieza)[i][col] = b; //marca columna
            }
            //System.out.println(solutionBoard);
            //printCells();
            //marcarDiagonales(row, col, b);
            diagonalInferior[col-row+n-1] = b;
            diagonalSuperior[col+row] = b;
            
        }else if (pieza.piece.startsWith("T")){
            for (int i = 0; i < n; i++){
                allowedCells.get(pieza)[row][i] = b;
            }
            for (int i = 0; i < n; i++){
                allowedCells.get(pieza)[i][col] = b;
            }
            //System.out.println(solutionBoard);
            //printCells();
        }else if (pieza.piece.startsWith("A")){
            diagonalInferior[col-row+n-1] = b;
            diagonalSuperior[col+row] = b;
        }else if (pieza.piece.startsWith("K")){
            allowedCells.get(pieza)[row][col] = b;
            if (row > 0){
                 allowedCells.get(pieza)[row - 1][col] = b;
                 if (col < n - 1){
                     allowedCells.get(pieza)[row - 1][col + 1] = b;
                 }
            }
            if (col < n - 1){
                allowedCells.get(pieza)[row][col + 1] = b;
                if (row < n - 1){
                    allowedCells.get(pieza)[row + 1][col + 1] = b;
                }
            }
            if (row < n -1){
                allowedCells.get(pieza)[row + 1][col] = b;
                if (col > 0){
                    allowedCells.get(pieza)[row + 1][col - 1] = b;
                }
            }
            if (col > 0){
                allowedCells.get(pieza)[row][col - 1] = b;
                if (row > 0){
                    allowedCells.get(pieza)[row - 1][col - 1] = b;
                }
            }
        }
    }
    
    private void sacar(int row, int col, Cell pieza){
        solutionBoard.resetCell(row, col);
        puestas--;
        toggleCell(row, col, pieza, true);
    }
    
    private void marcarDiagonales(int row, int col, Cell pieza, boolean b){
        if (row >= col){
            int i = row - col;
            int j = 0;
            while (i < n && j < n){
                allowedCells.get(pieza)[i][j] = b;
                i++;
                j++;
            }
            i = 0;
            j = row + col;
            if (j > n - 1){
                j = n - 1;
            }
            while (i < n &&  j >= 0 && j < n && i >= 0){
                allowedCells.get(pieza)[i][j] = b;
                i++;
                j--;
            }
        }else{
            int i = 0;
            int j = col - row;
            while (i < n && j < n){
                allowedCells.get(pieza)[i][j] = b;
                i++;
                j++;
            }
        }
    }
    
//    public void printCells(){
//        String text = "";
//        for (int i = 0; i < n; i++){
//            for (int j = 0; j < n; j++){
//                if (allowedCells[i][j]){
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
        for (boolean[][] aux: allowedCells.values()){
            if (!aux[row][col]){
                return false;
            }
        }
        if (!(diagonalInferior[col-row+n-1] && diagonalSuperior[col+row])){
                return false;
            }
        if (pieza.piece.startsWith("R")){
            
        }else if (pieza.piece.startsWith("K")){
            //System.out.println(solutionBoard);
            Cell[][] aux = solutionBoard.matrix;
            if (!aux[row][col].piece.equals(Cell.EMPTY_CELL)) {
                return false;
            }
            if (row > 0) {
                if (!aux[row-1][col].piece.equals(Cell.EMPTY_CELL)) {
                    return false;
                }
                if (col < n - 1) {
                    if (!aux[row - 1][col + 1].piece.equals(Cell.EMPTY_CELL)) {
                        return false;
                    }
                }
            }
            if (col < n - 1) {
                if (!aux[row][col + 1].piece.equals(Cell.EMPTY_CELL)) {
                    return false;
                }
                if (row < n - 1) {
                    if (!aux[row + 1][col + 1].piece.equals(Cell.EMPTY_CELL)) {
                        return false;
                    }
                }
            }
            if (row < n - 1) {
                if (!aux[row + 1][col].piece.equals(Cell.EMPTY_CELL)) {
                    return false;
                }
                if (col > 0) {
                    if (!aux[row + 1][col - 1].piece.equals(Cell.EMPTY_CELL)) {
                        return false;
                    }
                }
            }
            if (col > 0) {
                if (!aux[row][col - 1].piece.equals(Cell.EMPTY_CELL)) {
                    return false;
                }
                if (row > 0) {
                    if (!aux[row - 1][col - 1].piece.equals(Cell.EMPTY_CELL)) {
                        return false;
                    }
                }
            }

        }
        return true;
    }

    public void buscarSoluciones2(){
        
//        Boolean exito = false;
//        buscarSolucion2(0, 2, piezas.get(0), exito);
//        buscarSolucion2(0, 1, piezas.get(0), exito);
        boolean hayDistintas = true;
        int k = 0;
        Cell aux = null;
        while (k < piezas.size()) {
            if (aux == null || !aux.piece.startsWith(piezas.get(k-1).piece)) {
                aux = piezas.get(k);
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        Boolean exito = false;
                        //System.out.println("voy con " + i + " " + j);
                        buscarSolucion2(i, j, aux, exito);
                        inicializar();
                    }
                }
            }
            k++;
        }
        
//        inicializar();
//        marcarDiagonales(3, 3, false);
//        printCells();
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