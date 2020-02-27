package astar;

import edu.princeton.cs.algs4.Stopwatch;
import heap.ArrayHeapMinPQ;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @see ShortestPathsSolver for more method documentation
 */
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private ArrayHeapMinPQ<Vertex> fringe;
    private List<Vertex> solution;
    private double timeSpent;
    private int numStatesExplored;
    private double solutionWeight;
    private Map<Vertex, Double> distTo;
    private Map<Vertex, Vertex> edgeTo;
    private Stopwatch sw;

    /**
     * Immediately solves and stores the result of running memory optimized A*
     * search, computing everything necessary for all other methods to return
     * their results in constant time. The timeout is given in seconds.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        sw = new Stopwatch();
        fringe = new ArrayHeapMinPQ<>();
        solution = new LinkedList<>();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        distTo.put(start, 0.0);
        edgeTo.put(start, null);
        fringe.add(start, input.estimatedDistanceToGoal(start, end));
        //add sth to the fringe
        while (!fringe.isEmpty()) {
            timeSpent = sw.elapsedTime();
            if (timeSpent > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                return;
            }
            Vertex poppedVertex = fringe.removeSmallest();
            numStatesExplored++;
            //find the goal
            if (poppedVertex.equals(end)) {
                solutionWeight = distTo.get(end);
                //
                Vertex current = end;
                while (!current.equals(start)) {
                    solution.add(current);
                    current = edgeTo.get(current);
                }
                solution.add(start);
                //
                Collections.reverse(solution);
                outcome = SolverOutcome.SOLVED;
                return;
            }
            //这段没问题
            for (WeightedEdge<Vertex> v : input.neighbors(poppedVertex)) {
                double potentialDist = distTo.get(poppedVertex) + v.weight();
                double priority = potentialDist + input.estimatedDistanceToGoal(v.to(), end);
                if (!distTo.containsKey(v.to())){
                    distTo.put(v.to(), potentialDist);
                    edgeTo.put(v.to(), poppedVertex);
                    fringe.add(v.to(), priority);
                }else if (potentialDist < distTo.get((v.to()))) {
                    distTo.put(v.to(), potentialDist);
                    edgeTo.put(v.to(), poppedVertex);
                    if (!fringe.contains(v.to())){
                        fringe.add(v.to(), priority);
                    } else {
                        fringe.changePriority(v.to(), priority);
                    }
                }
            }
        }
        outcome = SolverOutcome.UNSOLVABLE;
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {

        return solutionWeight;
    }

    /** The total number of priority queue removeSmallest operations. */
    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
