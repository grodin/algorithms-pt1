import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class WordNet {

  private final List<Set<String>> synsets;
  private final Digraph hypernymGraph;

  public WordNet(final String synsets, final String hypernymns) {
    this.synsets = readSynsets(new File(requireNotNull(synsets)));

    this.hypernymGraph = validateDigraph(
        readHypernyms(new File(requireNotNull(hypernymns)), this.synsets)
    );
  }

  private WordNet(final List<Set<String>> synsets,
                  final Digraph hypernymGraph) {
    this.synsets = synsets;
    this.hypernymGraph = validateDigraph(hypernymGraph);
  }

  private static <T> Digraph readHypernyms(final File inputPath,
                                           final List<T> synsets) {
    var in = new In(inputPath);
    var graph = new Digraph(synsets.size());
    while (in.hasNextLine()) {
      var fields =
          Stream.of(in.readLine().split(","))
              .map(String::trim)
              .collect(Collectors.toList());
      var id = Integer.parseInt(fields.get(0));
      fields.stream()
          .skip(1)
          .map(Integer::parseInt)
          .forEach(hyponym -> graph.addEdge(id, hyponym));
    }
    return validateDigraph(graph);
  }

  private static List<Set<String>> readSynsets(final File inputPath) {
    var in = new In(inputPath);
    var synsets = new ArrayList<Set<String>>();
    while (in.hasNextLine()) {
      var fields =
          Stream.of(in.readLine().split(","))
              .map(String::trim)
              .collect(Collectors.toList());
      final String synset = fields.get(1);
      var nouns = Stream.of(synset.split("\\s"))
          .map(String::trim)
          .map(noun -> noun.replaceAll("_", " "))
          .collect(Collectors.toSet());
      synsets.add(nouns);
    }
    return synsets;
  }

  public Iterable<String> nouns() {
    return () -> nounsStream().iterator();
  }

  private Stream<String> nounsStream() {
    return synsets.stream()
        .flatMap(Collection::stream);
  }

  public boolean isNoun(final String word) {
    return nounsStream().anyMatch(noun -> requireNotNull(word).equals(noun));
  }

  public int distance(final String nounA, final String nounB) {
    requireNotNull(nounA);
    requireNotNull(nounB);
    return -1;
  }

  public String sap(String nounA, String nounB) {
    requireNotNull(nounA);
    requireNotNull(nounB);
    return null;
  }

  public static void main(String[] args) {

  }

  private static Digraph validateDigraph(final Digraph graph) {
    graph.
  }

  private static void check(final boolean condition) {
    if (!condition) throw new IllegalArgumentException();
  }

  @SuppressWarnings( "UnusedReturnValue" )
  private static <T> T requireNotNull(final T value) {
    check(value != null);
    return value;
  }
}
