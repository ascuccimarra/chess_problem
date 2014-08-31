package main;
 
import main.domain.Board;
import java.util.List;
 
//TODO add JUnit TESTS!!!!!!
public class TestChessProblem {
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        ChessProblem chess = new ChessProblem(7,2,0,2,2,1);
        chess.searchSolutions();
        List<Board> solutions = chess.getSolutions();
        System.out.println("7×7 board with 2 Kings, 2 Queens, 2 Bishops and 1 Knight: " + solutions.size());
        
        chess = new ChessProblem(4,0,0,2,1,2);
        chess.searchSolutions();
        solutions = chess.getSolutions();
        System.out.println("4x4 board with 2 bishops, 1 King and 2 Knight: " + solutions.size());
        
        chess= new ChessProblem(8,8,0,0,0,0);
        chess.searchSolutions();
        solutions = chess.getSolutions();
        System.out.println("8 Queens: " + solutions.size());
        
        chess= new ChessProblem(3,0,1,0,2,0);
        chess.searchSolutions();
        solutions = chess.getSolutions();
        System.out.println("2 Kings - 1 Rook: " + solutions.size());
        printSolutions(solutions);
    }
    
    static void printSolutions(List<Board> solutions){
        for (int i = 0; i < solutions.size(); i++){
            Board b = solutions.get(i);
            System.out.println("Solution: " + i);
            System.out.println(b);
            System.out.println("");
        }
    }
}