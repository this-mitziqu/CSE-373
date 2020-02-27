package astar.slidingpuzzle;

import astar.AStarSolver;
import astar.LazySolver;
import astar.ShortestPathsSolver;
import astar.SolutionPrinter;

/**
 * Showcases how the AStarSolver can be used for solving sliding puzzles.
 */
public class DemoSlidingPuzzleSolution {
    private static String[] hardPuzzles = {
            "HardPuzzle3.txt",
    };

    private static String[] basicPuzzles = {
        "BasicPuzzle1.txt",
        "BasicPuzzle2.txt",
        "BasicPuzzle3.txt",
        "BasicPuzzle4.txt",
        "BasicPuzzle5.txt",
    };

    private static String[] elitePuzzles = {
            "ElitePuzzle2.txt"
    };

    public static void main(String[] args) {
        for (String puzzleFile : elitePuzzles) {
            BoardState start = BoardState.readBoard("data/puzzles/" + puzzleFile);
            System.out.println(start);
            int N = start.size();
            BoardState goal = BoardState.solved(N);

            BoardGraph spg = new BoardGraph();

            ShortestPathsSolver<BoardState> solver;
            try {
                solver = new AStarSolver<>(spg, start, goal, 40);
            } catch (UnsupportedOperationException e) {
                System.out.println("AStarSolver doesn't seem to be implemented yet; using LazySolver instead.");
                solver = new LazySolver<>(spg, start, goal, 20);
            }
            SolutionPrinter.summarizeSolution(solver, "\n");
        }
    }
}
