package main;
 
import main.domain.Board;
import java.util.List;
 
//TODO add JUnit TESTS!!!!!!
public class TestChessProblem {
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        test(4,0,0,2,1,2);  
        test(7,2,0,2,2,1);
        test(8,8,0,0,0,0);
        printSolutions(test(3,0,1,0,2,0));
        
    }
    
    static List<Board> test(int size, int queens, int rooks, int bishops, int kings, int knights){
        ChessProblem chess= new ChessProblem(size, queens, rooks, bishops, kings, knights);
        chess.searchSolutions();
        List<Board> solutions = chess.getSolutions();
        String text = "";
        if (queens > 0){
            text += queens + " Queens, ";
        }
        if (rooks > 0){
            text += rooks + " Rooks, ";
        }
        if (bishops > 0){
            text += bishops + " Bishops, ";
        }
        if (kings > 0){
            text += kings + " Kings, ";
        }
        if (knights > 0){
            text += knights + " Knights, ";
        }
        text = text.substring(0, text.length() - 2) + ": ";
        System.out.println(text + solutions.size());
        return solutions;
    }
    
    static void printSolutions(List<Board> solutions){
        for (int i = 0; i < solutions.size(); i++){
            Board b = solutions.get(i);
            System.out.println("Solution: " + (i + 1));
            System.out.println(b);
            System.out.println("");
        }
    }
}