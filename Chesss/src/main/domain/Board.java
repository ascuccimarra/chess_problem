/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.domain;

import java.util.Arrays;

/**
 *
 * @author alvaro
 */
public class Board {
    Cell[][] matrix;
    final int rows;
    final int cols;
    
    public Board(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        matrix = new Cell[rows][cols];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                matrix[i][j] = new Cell();
            }
        }
    }

    public void putPiece(int fila, int col, Piece piece) {
        matrix[fila][col].setPiece(piece);
    }

    public void resetCell(int fila, int col) {
        matrix[fila][col].setPiece(null);
    }

    public Cell getCell(int i, int j) {
        return matrix[i][j];
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final Board other = (Board) obj;
        if (!Arrays.deepEquals(this.matrix, other.matrix)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Arrays.deepHashCode(this.matrix);
        return hash;
    }

    @Override
    public String toString() {
        String text = "";
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                if (isEmpty(i, j)){
                    text += "-";
                }else{
                    text += getCell(i, j).getPiece().getText().substring(0, 1);
                }
                text += " ";
            }
            text += "\n";
        }
        return text;
    }
    
    public void print(){
        System.out.println(this);
    }

    public Board cloneBoard() {
        Cell[][] auxMatrix = new Cell[rows][cols];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                auxMatrix[i][j] = new Cell(matrix[i][j].getPiece());
            }
        }
        Board clone = new Board(rows, cols);
        clone.matrix = auxMatrix;
        return clone;
    }

    public boolean isEmpty(int row, int col) {
        return getCell(row, col).getPiece() == null;
    }

    public Cell[][] getMatrix() {
        return matrix;
    }
}
