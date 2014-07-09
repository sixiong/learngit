import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class DNPokerTest {
	DNPoker poker = null;
	@Before
	public void setUp() throws Exception {
		poker = new DNPoker((byte)3, (byte)0);
	}

	@Test
	public void testDNPokerByteByte() {
		poker = new DNPoker((byte)3, (byte)2);
		assertNotNull(poker);
		assertEquals(3,poker.poker_value);
		assertEquals(2,poker.poker_color);
	}

	@Test
	public void testDNPoker() {
		poker = new DNPoker();
		assertNotNull(poker);
		assertEquals(0,poker.poker_value);
		assertEquals(0,poker.poker_color);
	}

	@Test
	public void testIsNullPoker() {
		assertEquals(true, poker.IsNullPoker());
	}

	@Test
	public void testReset() {
		poker.Reset();
		assertEquals(0, poker.poker_value);
		assertEquals(0, poker.poker_color);
	}

	@Test
	public void testGetRealValue() {
		poker = new DNPoker((byte)12,(byte)3);
		assertEquals(10, poker.getRealValue());
		poker = new DNPoker((byte)10,(byte)3);
		assertEquals(10, poker.getRealValue());
		poker = new DNPoker((byte)8,(byte)3);
		assertEquals(8, poker.getRealValue());
	}

}
