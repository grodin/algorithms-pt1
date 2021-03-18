import java.util.ArrayList;
import java.util.List;

public class SliderTestUtils {
  public static int[] row(int... values) {
    return values;
  }

  public static Board board(int[]... rows) {
    return new Board(rows);
  }

  public static <T> List<T> collectToList(Iterable<T> iterable) {
    var items = new ArrayList<T>();
    iterable.forEach(items::add);
    return items;
  }
}
