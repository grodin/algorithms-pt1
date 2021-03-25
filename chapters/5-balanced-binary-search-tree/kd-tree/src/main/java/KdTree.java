import java.util.ArrayList;
import java.util.Collection;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {

  private static final RectHV neverIntersectingRect =
      new RectHV(-1.0, -1.0,
          -0.5, -0.5);

  private static final Point2D outOfBoundsPoint = new Point2D(1000, 1000);
  private VNode root = VNode.NULL_VNODE;

  public boolean isEmpty() {
    return root == VNode.NULL_VNODE;
  }

  public int size() {
    return root.size();
  }

  public void insert(Point2D p) {
    root = root.insert(requireNonNull(p));
  }

  public boolean contains(Point2D p) {
    return root.contains(requireNonNull(p));
  }

  public void draw() {
    root.draw();
  }

  public Iterable<Point2D> range(RectHV rect) {
    return root.range(requireNonNull(rect));
  }

  public Point2D nearest(Point2D p) {
    if (isEmpty()) return null;
    var champion = root.getPoint();
    champion = root.nearest(requireNonNull(p), champion);
    return champion;
  }

  private static <R> R requireNonNull(final R value) {
    if (value == null) {
      throw new IllegalArgumentException();
    }
    return value;
  }

  public static void main(String[] args) {}

  private interface Node {
    Node getLeftChild();

    Node getRightChild();

    RectHV getLessThanRect();

    RectHV getGreaterThanRect();

    Point2D getPoint();

    default Collection<Point2D> range(RectHV rect) {
      var foundPoints = new ArrayList<Point2D>();
      if (rect.contains(getPoint())) {
        foundPoints.add(getPoint());
      }
      if (rect.intersects(getLessThanRect())) {
        foundPoints.addAll(getLeftChild().range(rect));
      }
      if (rect.intersects(getGreaterThanRect())) {
        foundPoints.addAll(getRightChild().range(rect));
      }
      return foundPoints;
    }

    default Point2D nearest(final Point2D queryPoint, final Point2D champion) {
      var newChampion = champion;
      var championDistance = champion.distanceSquaredTo(queryPoint);
      if (getPoint().distanceSquaredTo(queryPoint) < championDistance) {
        newChampion = getPoint();
        championDistance = newChampion.distanceSquaredTo(queryPoint);
      }
      if (getLessThanRect().distanceSquaredTo(queryPoint) < championDistance) {
        newChampion = getLeftChild().nearest(queryPoint, newChampion);
        championDistance = newChampion.distanceSquaredTo(queryPoint);
      }
      if (getGreaterThanRect().distanceSquaredTo(queryPoint) < championDistance) {
        newChampion = getRightChild().nearest(queryPoint, newChampion);
      }
      return newChampion;
    }

    double coordinateExtractor(Point2D other);

    int size();

    default boolean contains(Point2D point) {
      var comp = Double.compare(
          coordinateExtractor(point),
          coordinateExtractor(getPoint()));
      if (comp < 0)
        return getLeftChild().contains(point);
      else if (comp > 0)
        return getRightChild().contains(point);
      else
        return getPoint().equals(point);
    }

    default void draw() {
      getPoint().draw();
    }
  }

  private static class VNode implements Node {

    private HNode leftChild;
    private HNode rightChild;
    private final Point2D point;
    private int size;

    public VNode(final Point2D point,
                 final HNode leftChild,
                 final HNode rightChild) {
      this(point, leftChild, rightChild, 1);
    }

    private VNode(final Point2D point,
                  final HNode leftChild,
                  final HNode rightChild, final int size) {
      this.point = point;
      this.leftChild = leftChild;
      this.rightChild = rightChild;
      this.size = size;
    }

    @Override public HNode getLeftChild() {
      return leftChild;
    }

    @Override public HNode getRightChild() {
      return rightChild;
    }

    @Override public RectHV getLessThanRect() {
      return new RectHV(0.0, 0.0, point.x(), 1.0);
    }

    @Override public RectHV getGreaterThanRect() {
      return new RectHV(point.x(), 0.0, 1.0, 1.0);
    }

    @Override public Point2D getPoint() {
      return point;
    }

    @Override public double coordinateExtractor(final Point2D other) {
      return other.x();
    }

    @Override public int size() {
      return size;
    }

    public VNode insert(final Point2D point) {
      if (this.point.equals(point)) return this;
      if (point.x() <= getPoint().x()) {
        leftChild = leftChild.insert(point);
      } else {
        rightChild = rightChild.insert(point);
      }
      updateSize();
      return this;
    }

    private void updateSize() {
      size = 1 + leftChild.size() + rightChild.size();
    }

    public static final VNode NULL_VNODE = new VNode(outOfBoundsPoint, null,
        null, 0) {
      @Override public RectHV getLessThanRect() {
        return neverIntersectingRect;
      }

      @Override public RectHV getGreaterThanRect() {
        return neverIntersectingRect;
      }

      @Override public boolean contains(final Point2D point) {
        return false;
      }

      @Override public int size() {
        return 0;
      }

      @Override public void draw() {

      }

      @Override public VNode insert(final Point2D point) {
        return new VNode(point, HNode.NULL_HNODE, HNode.NULL_HNODE);
      }
    };
  }

  private static class HNode implements Node {

    private VNode leftChild;
    private VNode rightChild;
    private final Point2D point;
    private int size;

    public HNode(final Point2D point,
                 final VNode leftChild,
                 final VNode rightChild) {
      this(point, leftChild, rightChild, 1);
    }

    private HNode(final Point2D point,
                 final VNode leftChild,
                 final VNode rightChild, final int size) {
      this.point = point;
      this.leftChild = leftChild;
      this.rightChild = rightChild;
      this.size = size;
    }

    @Override public VNode getLeftChild() {
      return leftChild;
    }

    @Override public VNode getRightChild() {
      return rightChild;
    }

    @Override public RectHV getLessThanRect() {
      return new RectHV(0.0, 0.0, 1.0, point.y());
    }

    @Override public RectHV getGreaterThanRect() {
      return new RectHV(0.0, point.y(), 1.0, 1.0);
    }

    @Override public Point2D getPoint() {
      return point;
    }

    @Override public double coordinateExtractor(final Point2D other) {
      return other.y();
    }

    public HNode insert(final Point2D point) {
      if (this.point.equals(point)) return this;
      if (point.y() <= getPoint().y()) {
        leftChild = leftChild.insert(point);
      } else {
        rightChild = rightChild.insert(point);
      }
      updateSize();
      return this;
    }

    @Override public int size() {
      return size;
    }

    private void updateSize() {
      size = 1 + leftChild.size() + rightChild.size();
    }

    public static final HNode NULL_HNODE = new HNode(outOfBoundsPoint, null,
        null, 0) {

      @Override public RectHV getLessThanRect() {
        return neverIntersectingRect;
      }

      @Override public RectHV getGreaterThanRect() {
        return neverIntersectingRect;
      }

      @Override public void draw() {

      }

      @Override public boolean contains(final Point2D point) {
        return false;
      }

      @Override public HNode insert(final Point2D point) {
        return new HNode(point, VNode.NULL_VNODE, VNode.NULL_VNODE);
      }
    };
  }
}
