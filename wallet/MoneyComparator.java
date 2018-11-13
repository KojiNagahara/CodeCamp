package wallet;

import java.util.Comparator;

/**
 * EnumであるMoneyの比較基準を定義する。
 * @author KojiNagahara
 */
public class MoneyComparator implements Comparator<Money> {

	@Override
	public int compare(Money o1, Money o2) {
		return o1.getAmount() - o2.getAmount();
	}
}
