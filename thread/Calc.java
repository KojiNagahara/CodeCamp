package thread;
/**
 * Calcクラス
 */
public class Calc {
  private long total; // 合計

  public long getTotal() {
    return total;
  }

  /**
   * 指定された値を合計に追加する。
   *
   * @param value 追加する値
   */
  public synchronized void add(long value) {
    total += value;
  }
}