package wallet;

/**
 * Walletに入る紙幣/硬貨の種類を定義する。
 * @author KojiNagahara
 */
public enum Money {
	TenThousand(10000, false),
	FiveThousand(5000, false),
	TwoThousand(2000, false),
	Thousand(1000, false),
	FiveHundred(500, true),
	Hundred(100, true),
	Fifty(50, true),
	Ten(10, true),
	Five(5, true),
	One(1, true);

	private final int amount;
	private final boolean isCoin;

	/**
	 * コンストラクタ。フィールドの初期化を実行する。
	 * @param amount 金額
	 * @param isCoin 硬貨であるかどうか
	 */
	private Money(final int amount, final boolean isCoin) {
		this.amount = amount;
		this.isCoin = isCoin;
	}

	/**
	 * 金額を取得する
	 * @return 金額
	 */
	public int getAmount() { return this.amount; }

	/**
	 * 硬貨であるかどうかを取得する
	 * @return 硬貨ならtrue、紙幣ならfalse
	 */
	public boolean isCoin() { return this.isCoin; }

	/**
	 * 金額が指定された額以上かどうか
	 * @param target 比較対象の金額
	 * @return 金額が指定された額以上ならtrue、金額が指定された額未満ならfalse
	 */
	public boolean isHigherEqual(int target) {
		return this.amount >= target;
	}

	@Override
	public String toString() {
		return isCoin ? amount+"円硬貨" : amount+"円札";
	}
}
