package thread;

/**
 * 応用問題3の回答例。
 * 時間短縮のためにCalcを使用したが、AtomicIntegerを
 * 使うやり方などバリエーションはいくつか考えられるので
 * これが唯一無二の正解ではない。
 * @see java.util.concurrent.atomic.AtomicInteger
 * @author KojiNagahara
 */
public class SynchronizedSample {
	private static final int NUM_THREADS = 4;
	private static final int TARGET_NUMBER = 2000000000;

	/**
	 * エントリポイント。本来例外処理はこんな雑ではいけない。
	 * @param args コマンドライン引数
	 * @throws InterruptedException スレッドに割り込みが発生した場合に送出される。
	 */
	public static void main(String[] args) throws InterruptedException {
		Calc calc = new Calc();
	    Thread[] threads = new Thread[NUM_THREADS];

	    int start = 1;
	    int increment = TARGET_NUMBER / NUM_THREADS;

	    for (int i = 0; i < NUM_THREADS; i++) {
	    	threads[i] = new Thread(new SumThread(start, increment * (i+1), calc));
	    	start += increment;
	    }

	    for (Thread t:threads) {
	    	t.start();
	    }

	    for (Thread t:threads) {
	    	t.join();
	    }

	    System.out.printf("合計は%,dです。", calc.getTotal());
	}

}
