package thread;

/**
 * Calcクラスを使って応用問題を排他制御で解いた実装。
 * @author KojiNagahara
 */
public class SumThread implements Runnable {
	private int start;
	private int end;
	private Calc calc;

	/**
	 * コンストラクタ。
	 * @param start 加算を開始する数。計算に含まれる。
	 * @param end 加算を終了する数。計算に含まれる。
	 */
	public SumThread(int start, int end, Calc calc) {
		this.start = start;
		this.end = end;
		this.calc = calc;
		System.out.printf("start:%d, end:%d\n", start, end);
	}

	/**
	 * 指定された開始から終了までの数の合計をcalcにセットする。
	 * このメソッドは実装次第で性能が変わってくる。
	 * ポイントは「時間のかかる処理をいかに少なくするか」にある。
	 * 基本的に排他制御は処理を1スレッドずつ順番に処理していくので
	 * 並行処理の恩恵が受けられなくなる。
	 * そのため、排他制御の待ち時間をなるべく少なくすることが性能向上に役立つ。
	 */
	@Override
	public void run() {
		// 遅い実装 ... 毎回排他処理が入るのでボトルネックの待ち時間が長い
		for (int i = start; i <= end; i++) {
			calc.add(i);
		}

		// 速い実装 ... 計算回数そのものは1回多いが、排他処理が各スレッド1回ずつなのでボトルネックの待ち時間が短い
//		long sum = 0;
//		for (int i = start; i <= end; i++) {
//			sum += i;
//		}
//		System.out.printf("小計は%,dです。\n", sum);
//		calc.add(sum);
	}
}
