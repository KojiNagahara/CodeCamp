package wallet;

import java.util.Comparator;

/**
 * 応用問題用参考実装。
 * Walletクラスの比較を行うための基準を定義するクラス。
 * 優先順位は合計額＞合計の紙幣/貨幣の枚数
 *
 * @author KojiNagahara
 */
public class WalletComparator implements Comparator<Wallet> {

	@Override
	public int compare(Wallet o1, Wallet o2) {
		if (o1.getTotal() != o2.getTotal()) {
			return o1.getTotal() - o2.getTotal();
		} else {
			return o1.getTotalCount() - o2.getTotalCount();
		}
	}
}
