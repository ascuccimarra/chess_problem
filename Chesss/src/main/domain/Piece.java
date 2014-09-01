/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.domain;

import java.util.List;

/**
 *
 * @author alvaro
 */
public abstract class Piece {
    private final String text;
    
    public Piece(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }
    
    protected boolean isOnTheBoard(int row, int col, int n){
        return row >= 0 && row < n && col >= 0 && col < n;
    }

    public abstract void toggleEatableCells(int row, int col, List<Piece>[][] cells, int n, boolean b);
    
    protected void toggleRow(int row, List<Piece>[][] cells, int n, boolean b){
        for (int i = 0; i < n; i++){
            addOrRemoveAsEating(row, i, cells, b);
        }
    }
    
    protected void toggleCol(int col, List<Piece>[][] cells, int n, boolean b){
        for (int i = 0; i < n; i++){
            addOrRemoveAsEating(i, col, cells, b);
        }
    }
    
    protected void toggleDiagonals(int row, int col, List<Piece>[][] cells, int n, boolean b){
        int i = row;
        int j = col;
        while (i >= 0 && j >= 0){
            addOrRemoveAsEating(i, j, cells, b);
            i--;
            j--;
        }
        i = row;
        j = col;
        while (i < n && j < n){
            addOrRemoveAsEating(i, j, cells, b);
            i++;
            j++;
        }
        i = row;
        j = col;
        while (i >= 0 && j < n){
            addOrRemoveAsEating(i, j, cells, b);
            i--;
            j++;
        }
        i = row;
        j = col;
        while (i < n && j >= 0){
            addOrRemoveAsEating(i, j, cells, b);
            i++;
            j--;
        }
    }
    
    protected void addOrRemoveAsEating(int i, int j, List<Piece>[][] cells, boolean add){
        if (add){
            cells[i][j].add(this);
         }else{
             cells[i][j].remove(this);
         }
    }
    
    public abstract boolean isEatable(int row, int col, List<Piece>[][] cells, Cell[][] board, int n);
    
    boolean isFreeRow(int row, Cell[][] board, int n){
        for (int i = 0; i < n; i++){
            if (board[row][i].getPiece() != null){
                return false;
            }
        }
        return true;
    }
    
    boolean isFreeCol(int col, Cell[][] board, int n){
        for (int i = 0; i < n; i++){
            if (board[i][col].getPiece() != null){
                return false;
            }
        }
        return true;
    }
    
    protected boolean isFreeDiagonals(int row, int col, Cell[][] board, int n){
        int i = row;
        int j = col;
        while (i >= 0 && j >= 0){
            if (board[i][j].getPiece() != null){
                return false;
            }
            i--;
            j--;
        }
        i = row;
        j = col;
        while (i < n && j < n){
            if (board[i][j].getPiece() != null){
                return false;
            }
            i++;
            j++;
        }
        i = row;
        j = col;
        while (i >= 0 && j < n){
            if (board[i][j].getPiece() != null){
                return false;
            }
            i--;
            j++;
        }
        i = row;
        j = col;
        while (i < n && j >= 0){
            if (board[i][j].getPiece() != null){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Piece other = (Piece) obj;
        return true;
    }
    
    
}
