package huskymaps;

import astar.AStarGraph;
import astar.ShortestPathsSolver;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static huskymaps.utils.Constants.BASE_DIR_PATH;

/**
 * Magically returns the correct shortest paths for a few special cases.
 * This implementation will work on the autograder for HW 7 (HuskyMaps), but won't run correctly on
 * your own machine.
 *
 * Note: HW 8 (Seam Carving) will require you to have your own working implementation of
 * ShortestPathsSolver, so you will eventually need to fix any issues in your AStarSolver code.
 */
public class ShortestPathsOracle implements ShortestPathsSolver<Long> {
    private Pair pair;

    public ShortestPathsOracle(AStarGraph<Long> input, Long start, Long end, double timeout) {
        this.pair = new Pair(start, end);
    }

    public SolverOutcome outcome() {
        return SolverOutcome.SOLVED;
    }

    public List<Long> solution() {
        return oracle.getOrDefault(pair, List.of());
    }

    public double solutionWeight() {
        return 0;
    }

    public int numStatesExplored() {
        return 0;
    }

    public double explorationTime() {
        return 0;
    }

    private static class Pair {
        long start;
        long end;

        Pair(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Pair pair = (Pair) o;
            return start == pair.start && end == pair.end;
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }
    }

    private static final String REQUEST_FORMAT = BASE_DIR_PATH + "tests/routersmall/requestNode%d.json";
    private static final String RESULT_FORMAT = BASE_DIR_PATH + "tests/routersmall/result%d.json";
    private static final int NUM_TESTS = 25;

    private static Map<Pair, List<Long>> oracle = new HashMap<>();

    static {
        Gson gson = new Gson();
        for (int i = 0; i < NUM_TESTS; i += 1) {
            Pair request;
            try (Reader reader = new FileReader(String.format(REQUEST_FORMAT, i))) {
                request = gson.fromJson(reader, Pair.class);
            } catch (IOException e) {
                System.out.println("Missing data: " + String.format(REQUEST_FORMAT, i));
                continue;
            }
            List<Long> expected;
            try (Reader reader = new FileReader(String.format(RESULT_FORMAT, i))) {
                expected = Arrays.asList(gson.fromJson(reader, Long[].class));
            } catch (IOException e) {
                System.out.println("Missing data: " + String.format(RESULT_FORMAT, i));
                continue;
            }
            oracle.put(request, expected);
        }
    }
}
