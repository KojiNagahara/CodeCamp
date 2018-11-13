package wallet;

/**
 * Walletの機能テストを実施するテストドライバ。
 *
 * @author KojiNagahara
 */
public class WalletTester {
	/**
	 * テストのエントリポイント。
	 * @param argv 起動時引数。使用しない。
	 */
	public static void main(String[] argv) {
		Wallet wallet = new Wallet();
		wallet.add(Money.TenThousand, 1);
		wallet.add(Money.FiveThousand, 1);
		wallet.add(Money.Thousand, 3);
		wallet.add(Money.FiveHundred, 1);
		wallet.add(Money.Ten, 8);
		wallet.add(Money.Five, 2);
		wallet.add(Money.One, 4);

		System.out.println("支払い前の財布の状態：");
		System.out.println(wallet);

		if (wallet.canPay(3200)) {
			System.out.println("OK");
			// (オプション）
			System.out.println("支払った金額:"+wallet.pay(3200));
		}

		System.out.println("支払い後の財布の状態（お釣りの考慮なし）：");
		System.out.println(wallet);

		// より高額の紙幣がない場合のテスト
		System.out.println("支払った金額:"+wallet.pay(12600));

		System.out.println("支払い後の財布の状態（お釣りの考慮なし）：");
		System.out.println(wallet);

		// ちゃんと500円玉で払ってくれるかどうかのテスト
		System.out.println("支払った金額:"+wallet.pay(500));

		System.out.println("支払い後の財布の状態（お釣りの考慮なし）：");
		System.out.println(wallet);
	}
}
