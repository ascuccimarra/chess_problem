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
public class King extends Piece{
    private static int[] xMovements;
    private static int [] yMovements;
    
    static {
        xMovements = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
        yMovements = new int[]{-1, 0, 1, 1, -1, -1, 0, 1};
    }
    
    public King(String text) {
        super(text);
    }

    @Override
    public void toggleEatableCells(int row, int col, List<Piece>[][] cells, int n, boolean b) {
        addOrRemoveAsEating(row, col, cells, b);
        for (int i = 0; i < 8; i++){
            int x = xMovements[i] + row;
            int y = yMovements[i] + col;
            if (isOnTheBoard(x, y, n)){
                addOrRemoveAsEating(x, y, cells, b);
            }
        }
    }

    @Override
    public boolean isEatable(int row, int col, List<Piece>[][] cells, Cell[][] board, int n) {
        if (!cells[row][col].isEmpty()){
            return true;
        }
        for (int i = 0; i < 8; i++){
            int x = xMovements[i] + row;
            int y = yMovements[i] + col;
            if (isOnTheBoard(x, y, n)){
                if (board[x][y].getPiece() != null){
                    return true;
                }
            }
        }
        return false;
    } 
}
