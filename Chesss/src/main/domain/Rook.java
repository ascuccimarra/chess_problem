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
public class Rook extends Piece{
    public Rook(String text) {
        super(text);
    }

    @Override
    public void toggleEatableCells(int row, int col, List<Piece>[][] cells, int n, boolean b) {
        toggleCol(col, cells, n, b);
        toggleRow(row, cells, n, b);
    }

    @Override
    public boolean isEatable(int row, int col, List<Piece>[][] cells, Cell[][] board, int n) {
         return !cells[row][col].isEmpty() || !isFreeRow(row, board, n) 
                || !isFreeCol(col, board, n);
    }
}
