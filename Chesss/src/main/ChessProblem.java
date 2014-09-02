package main;

import main.domain.Board;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import main.domain.Bishop;
import main.domain.King;
import main.domain.Knight;
import main.domain.Piece;
import main.domain.Queen;
import main.domain.Rook;

public class ChessProblem {

    private int n;
    private List<Piece>[][] eatableCells;
    private Board solutionBoard;
    private List<Board> solutions = new ArrayList<>();
    List<Piece> pieces = new ArrayList<>();
    private int alreadyPlaced;

    public ChessProblem(int size, int queens, int rooks, int bishops, int kings, int knights) {
        this.n = size;
        eatableCells = new List[n][n];
        for (int i = 0; i < queens; i++) {
            pieces.add(new Queen("Q" + i));
        }
        for (int i = 0; i < rooks; i++) {
            pieces.add(new Rook("R" + i));
        }
        for (int i = 0; i < bishops; i++) {
            pieces.add(new Bishop("B" + i));
        }
        for (int i = 0; i < kings; i++) {
            pieces.add(new King("K" + i));
        }
        for (int i = 0; i < knights; i++) {
            pieces.add(new Knight("N" + i));
        }
        init();
    }

    private void init() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                eatableCells[i][j] = new ArrayList<>();
            }
        }
        this.solutionBoard = new Board(n, n);
        this.alreadyPlaced = 0;
    }

    private void searchSolution(int row, int col, Piece piece, Boolean success) {
        if (isPlacable(row, col, piece)) {
            put(row, col, piece);
            if (done(row, col)) {
                if (alreadyPlaced == pieces.size()) {
                    
                    if (isNewSolution(solutionBoard)) {
                        //System.out.println(solutionBoard);
                        System.out.println(solutions.size());
                        addSolution();
                        
                    }
                    
                } else {
                    success = true;
                }
                remove(row, col, piece);
            } else {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++){
                        if (alreadyPlaced < pieces.size()) {
//                            System.out.println("es menor");
//                            System.out.println(i + " " + j);
//                            System.out.println(pieces.get(alreadyPlaced).getText());
                            searchSolution(i, j, pieces.get(alreadyPlaced), success);
                        }
                    }
                }
                if (!success) {
                    remove(row, col, piece);
                }
            }
        }
    }

    private boolean done(int row, int col) {
        return (row == n - 1 && col == n - 1) || alreadyPlaced == pieces.size();
    }

    private void put(int row, int col, Piece pieza) {
        solutionBoard.putPiece(row, col, pieza);
//        System.out.println("pongo " + row + " " + col);
//        System.out.println(solutionBoard);
        alreadyPlaced++;
        toggleCell(row, col, pieza, true);
    }

    private void toggleCell(int row, int col, Piece pieza, boolean add) {
        pieza.toggleEatableCells(row, col, eatableCells, n, add);
    }

    private void remove(int row, int col, Piece pieza) {
        solutionBoard.resetCell(row, col);
//        System.out.println("saco " + row + " " + col);
//        System.out.println(solutionBoard);
        alreadyPlaced--;
        toggleCell(row, col, pieza, false);
    }

    private boolean isPlacable(int row, int col, Piece pieza) {
        if (!solutionBoard.isEmpty(row, col)) {
            return false;
        }
        return !pieza.isEatable(row, col, eatableCells, solutionBoard.getMatrix(), n);
    }

    public void searchSolutions() {
//        int k = 0;
//        Piece aux = null;
//        while (k < pieces.size()) {
//            if (aux == null || !aux.getText().startsWith(pieces.get(k - 1).getText().substring(0, 1))) {
//                if (aux != null){
//                    System.out.println("aux: " + aux.getText());
//                     System.out.println("k - 1" + pieces.get(k - 1).getText());
//                     System.out.println(aux.getText().startsWith(pieces.get(k - 1).getText()));
//                }
//                aux = pieces.get(k);
//                
//                System.out.println(k);
//                Collections.swap(pieces, 0, k);
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        System.out.println("i " + i + " j " + j);
                        Piece aux = pieces.get(0);
                        System.out.println(solutionBoard);
                        searchSolution(i, j, aux, false);
                        init();
                    }
                }
//            }
//            k++;
//        }
    }

    private void addSolution() {
        solutions.add(this.solutionBoard.cloneBoard());
    }

    private boolean isNewSolution(Board newSolutionBoard) {
        int i = 0;
        boolean isNew = true;
        while (i < solutions.size() && isNew) {
            Board aBoard = solutions.get(i);
            if (aBoard.equals(newSolutionBoard)) {
                isNew = false;
            }
            i++;
        }
        return isNew;
    }

    public List<Board> getSolutions() {
        return this.solutions;
    }

    private boolean isAlreadyOnBoard(Piece pieza) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!solutionBoard.isEmpty(i, j) && solutionBoard.getCell(i, j).getPiece().getText().equals(pieza.getText())) {
                    System.out.println("is on board");
                    System.out.println(solutionBoard);
                    System.out.println("puestas " + alreadyPlaced);
                    return true;
                }
            }
        }
        return false;
    }
}