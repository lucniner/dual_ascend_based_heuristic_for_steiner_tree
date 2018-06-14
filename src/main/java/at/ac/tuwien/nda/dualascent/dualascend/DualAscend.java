//package at.ac.tuwien.nda.dualascent.dualascend;
//
//import at.ac.tuwien.nda.dualascent.SteinerTree.ProblemInstance;
//import at.ac.tuwien.nda.dualascent.SteinerTree.SolutionInstance;
//import at.ac.tuwien.nda.dualascent.util.Arc;
//import es.usc.citius.hipster.algorithm.Algorithm;
//import es.usc.citius.hipster.algorithm.Hipster;
//import es.usc.citius.hipster.graph.GraphBuilder;
//import es.usc.citius.hipster.graph.GraphSearchProblem;
//import es.usc.citius.hipster.graph.HipsterGraph;
//import es.usc.citius.hipster.model.problem.SearchProblem;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class DualAscend {
//    private final Random random = new Random();
//    private final List<Arc> graphArcs;
//    private final ProblemInstance problemInstance;
//    private HipsterGraph<Integer, Integer> graph;
//    private int lowerBound = 0;
//
//    public DualAscend(final ProblemInstance problemInstance) {
//        this.problemInstance = problemInstance;
//        this.graphArcs = this.problemInstance.getArcs();
//    }
//
//    public SolutionInstance solve() {
//
//
//        final List<Arc> currentGraph = new ArrayList<>();
//        final int rootIndex = getRandom(0, problemInstance.getTerminalNumber() - 1);
//        final int rootTerminal = problemInstance.getTerminals().get(rootIndex);
//        System.out.println("root terminal: " + rootTerminal);
//
//        final List<Integer> terminals = problemInstance.getTerminals();
//        terminals.remove(rootIndex);
//
//        while (!terminals.isEmpty()) {
//            final int activeIndex = getRandom(0, terminals.size() - 1);
//            final int activeTerminal = terminals.get(activeIndex);
//
//            this.constructGraph(currentGraph);
//
//            SearchProblem problem =
//                    GraphSearchProblem.startingFrom(activeTerminal).in(graph).takeCostsFromEdges().build();
//            final Algorithm.SearchResult result = Hipster.createDijkstra(problem).search(rootTerminal);
//
//            if (!result.getOptimalPaths().isEmpty()) {
//                if (result.getOptimalPaths().size() == 1) {
//                    List<Integer> l = (List<Integer>) result.getOptimalPaths().get(0);
//                    if (l.contains(rootTerminal)) {
//                        terminals.remove(activeIndex);
//                        continue;
//                    }
//                }
//
//            }
//
//
//            final OptionalInt delta =
//                    getConnectedEdgesToNode(activeTerminal).stream().mapToInt(Arc::getWeight).min();
//
//            for (final Arc arc : getConnectedEdgesToNode(activeTerminal)) {
//                if (delta.isPresent() && arc.getWeight() > 0) {
//                    final int newWeight = arc.getWeight() - delta.getAsInt();
//                    if (newWeight < 0) {
//                        throw new RuntimeException("this should not happen - fix it");
//                    }
//
//                    final int edgeIndex = getIndex(arc);
//                    this.graphArcs.get(edgeIndex).setWeight(newWeight);
//                    if (newWeight == 0) {
//                        currentGraph.add(arc);
//                    }
//                }
//            }
//
//            if (delta.isPresent()) {
//                lowerBound += delta.getAsInt();
//            }
//        }
//
//        System.out.println(lowerBound);
//        return null;
//    }
//
//    private void constructGraph(final List<Arc> arcs) {
//        final GraphBuilder<Integer, Integer> builder = GraphBuilder.create();
//        for (final Arc arc : arcs) {
//            builder.connect(arc.getTo()).to(arc.getFrom()).withEdge(arc.getWeight());
//        }
//        graph = builder.createUndirectedGraph();
//    }
//
//    private void printSolutionGraphs(final List<List<Integer>> paths) {
//        paths.forEach(path -> System.out.println(Arrays.toString(path.toArray())));
//    }
//
//    private int getRandom(final int min, final int max) {
//        return random.nextInt(max - min + 1) + min;
//    }
//
//    private Set<Arc> getConnectedEdgesToNode(final int node) {
//        return this.graphArcs
//                .stream()
//                .filter(e -> e.getWeight() > 0 && (e.getFrom() == node || e.getTo() == node))
//                .collect(Collectors.toSet());
//    }
//
//    private int getIndex(final Arc arc) {
//        int count = 0;
//        for (final Arc e : this.graphArcs) {
//            if (e.equals(arc)) {
//                return count;
//            }
//            count++;
//        }
//        return count;
//    }
//}