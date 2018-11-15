package walletTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import wallet.Money;

class MoneyTest {

	@Test
	void testGetAmount() {
		assertEquals(10000, Money.TenThousand.getAmount());
	}

	@Test
	void testIsCoin() {
		assertEquals(false, Money.TenThousand.isCoin());
		assertEquals(true, Money.Ten.isCoin());
	}

	@Test
	void testIsHigherEqual() {
		assertEquals(true, Money.TenThousand.isHigherEqual(9999));
		assertEquals(true, Money.TenThousand.isHigherEqual(10000));
		assertEquals(false, Money.TenThousand.isHigherEqual(10001));
	}

	@Test
	void testToString() {
		assertEquals("10000円札", Money.TenThousand.toString());
		assertEquals("10円硬貨", Money.Ten.toString());
	}

}
