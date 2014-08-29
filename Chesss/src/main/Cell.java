/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Objects;

/**
 *
 * @author alvaro
 */
class Cell {
    public static final String EMPTY_CELL = "-";
    //int row;
    //int col;
    String piece = EMPTY_CELL;

    public Cell(String piece) {
        this.piece = piece;
    }

    public Cell() {
        
    }

    void reset() {
        piece = EMPTY_CELL;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.piece);
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
        final Cell other = (Cell) obj;
        if (!Objects.equals(this.piece, other.piece)) {
            return false;
        }
        return true;
    }
      
    
}
