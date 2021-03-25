import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.Point2D;

import static com.google.common.truth.Truth.assertThat;

class KdTreeTest {
  @Test
  void newInstanceIsEmpty() {
    var instance = new KdTree();
    assertThat(instance.size()).isEqualTo(0);
    assertThat(instance.isEmpty()).isTrue();
  }

  @Test
  void insertedPointsAreFound() {
    var tree = new KdTree();
    var point1 = new Point2D(0.5, 0.5);
    var point2 = new Point2D(0.3, 0.6);
    tree.insert(point1);
    tree.insert(point2);

    assertThat(tree.size()).isEqualTo(2);
    assertThat(tree.isEmpty()).isFalse();

    assertThat(tree.contains(point1)).isTrue();
    assertThat(tree.contains(point2)).isTrue();

    final Point2D nonInsertedPoint = new Point2D(0.1, 0.1);
    assertThat(tree.contains(nonInsertedPoint)).isFalse();
  }
}
