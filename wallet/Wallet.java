package wallet;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * 練習問題の回答例。
 * 財布を表すWalletクラス。
 *
 * @author KojiNagahara
 */
public class Wallet {
	/** 財布に入っている硬貨/紙幣の種類と枚数を格納する */
	Map<Money, Integer> moneyMap = new TreeMap<>();

	/**
	 * コンストラクタ。
	 */
	public Wallet() {}

	/**
	 * 財布に硬貨/紙幣を追加する。
	 * @param kind 硬貨/紙幣の種類
	 * @param count 追加する枚数
	 */
	public void add(Money kind, int count) {
		// 応用問題：countが負の数であっても問題なく動作するように修正
		if (moneyMap.containsKey(kind)) {
			moneyMap.compute(kind, (key, old) -> old + count);
		} else {
			moneyMap.put(kind, count);
		}
	}

	/**
	 * 指定された金額が支払えるかどうかを判定する
	 * @param target 要求された金額
	 * @return 支払えるならtrue、支払えないならfalse
	 */
	public boolean canPay(int target) {
		if (target <= 0) { return true; }
		if (moneyMap.size() == 0) { return false; }

		int total = 0;
		// 高額硬貨/紙幣から数えていってトータル金額が支払額より大きければtrueを返して終了
		for (Entry<Money, Integer> moneyEntry : moneyMap.entrySet()) {
			total += moneyEntry.getKey().getAmount() * moneyEntry.getValue();
			if (total >= target) { return true; }
		}
		return false;
	}

	/**
	 * 支払いを行う。
	 * @param target 支払金額
	 * @return 実際に支払う金額
	 */
	public int pay(int target) {
		if (!canPay(target)) {
			// 支払えない場合は0を返す
			return 0;
		}

		int payed = 0;
		// 財布の中に入っている紙幣/硬貨のうちtargetの金額よりも大きいか等しく、一番近い金額の紙幣があるかどうかを確認する。
		if (moneyMap.keySet().stream().filter(money -> money.isHigherEqual(target)).count() != 0) {
			// あればその紙幣/硬貨を1減らして支払金額とする
			Money nearest = moneyMap.keySet().stream()
					.filter(money -> money.getAmount() >= target)
					.min(new MoneyComparator())
					.get();
			moneyMap.compute(nearest, (key, old) -> old - 1 );
			payed = nearest.getAmount();
		} else {
			int remain = target;
			// なければより少額の紙幣/硬貨を使って支払いの計算をする
			for (Entry<Money, Integer> entry : moneyMap.entrySet()) {
				int amount = entry.getKey().getAmount();
				int count = entry.getValue().intValue();
				for(int i = 0; i < count; i++) {
					if (remain >= 0) {
						// 残額と財布の中の枚数を減らす
						remain -= amount;
						payed += amount;
						moneyMap.compute(entry.getKey(), (key, old) -> old - 1 );
					} else {
						// 既に全額支払ってしまったらreturn;
						break;
					}
				}

				if (remain <= 0) {
					break;
				}
			}
		}

		// 支払金額を返す
		return payed;
	}

	/**
	 * 財布に入っている合計金額を計算する
	 * @return 合計金額
	 */
	public int getTotal() {
		int total = 0;
		for (Entry<Money, Integer> moneyEntry : moneyMap.entrySet()) {
			total += moneyEntry.getKey().getAmount() * moneyEntry.getValue();
		}
		return total;
	}

	/**
	 * 財布に入っている紙幣/硬貨の合計枚数を計算する
	 * @return 紙幣/硬貨の合計枚数
	 */
	public int getTotalCount() {
		int count = 0;
		for (Integer eachCount : moneyMap.values()) {
			count += eachCount.intValue();
		}
		return count;
	}

	@Override
	public String toString() {
		return moneyMap.toString();
	}
}
