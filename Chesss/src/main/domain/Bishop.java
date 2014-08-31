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
public class Bishop extends Piece{
    public Bishop(String text) {
        super(text);
    }

    @Override
    public void toggleEatableCells(int row, int col, List<Piece>[][] cells, int n, boolean b) {
        toggleDiagonals(row, col, cells, n, b);
    }

    @Override
    public boolean isEatable(int row, int col, List<Piece>[][] cells, Cell[][] board, int n) {
        return !cells[row][col].isEmpty() || !isFreeDiagonals(row, col, board, n);
    }
}
