/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.domain;

import java.util.Objects;

/**
 *
 * @author alvaro
 */
public class Cell {
    private Piece piece;
    
    public Cell(){}

    public Cell(Piece piece) {
        this.piece = piece;
    }
    
    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
