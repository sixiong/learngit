import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;


public class DNPokersTest {
	DNPokers pokers;

	@Before
	public void testDNPokers() {
		pokers = new DNPokers();
		assertNotNull(pokers);
		for (int i = 0; i < pokers.poker.length; i++) {
			assertEquals(null, pokers.poker[i]);
		}
		assertNull(pokers.max_poker);
		assertEquals(0, pokers.dn_type);
	}
	@Test
	public void testSetPoker() {
		DNPoker []newPoker = new DNPoker[5];
		for (int j = 0; j < newPoker.length; j++) {
			newPoker[j] = new DNPoker(DNPokerTest.GetRandTow(13),DNPokerTest.GetRandTow(13));
		}
		pokers.SetPoker(newPoker);
		for (int i = 0; i < newPoker.length; i++) {
			assertNotNull(pokers.poker[i]);
			assertEquals(newPoker[i].poker_value,pokers.poker[i].poker_value);
			assertEquals(newPoker[i].poker_color,pokers.poker[i].poker_color);
		}
		
	}

	@Test
	public void testSetMaxPoker() {
		DNPoker poker_max = new DNPoker((byte)13,(byte)4);
		pokers.SetMaxPoker(poker_max);
		assertNotNull(pokers.max_poker);
		assertEquals(poker_max,pokers.max_poker);
	}
	@Test
	public void testGetTypeofNiuNum() {
		DNPoker []newPoker = new DNPoker[5];
		for (int j = 0; j < newPoker.length; j++) {
			newPoker[j] = new DNPoker((byte)(j+9),(byte)3);	
		}
		pokers.SetPoker(newPoker);
		newPoker[0].poker_value = 10;
		newPoker[1].poker_value = 11;
		newPoker[2].poker_value = 3;
		newPoker[3].poker_value = 7;
		for (int i = 0; i <= 9; i++) {
			newPoker[4].poker_value = (byte) (i+1);
			assertEquals(i+1, pokers.getTypeofNiuNum());
		}
		newPoker[0].poker_value = 1;
		newPoker[1].poker_value = 1;
		newPoker[2].poker_value = 1;
		newPoker[3].poker_value = 1;
		newPoker[4].poker_value = 7;
		assertEquals(11,pokers.getTypeofNiuNum());
		newPoker[0].poker_value = 1;
		newPoker[1].poker_value = 1;
		newPoker[2].poker_value = 1;
		newPoker[3].poker_value = 7;
		newPoker[4].poker_value = 1;
		assertEquals(11,pokers.getTypeofNiuNum());
		newPoker[0].poker_value = 1;
		newPoker[1].poker_value = 1;
		newPoker[2].poker_value = 7;
		newPoker[3].poker_value = 1;
		newPoker[4].poker_value = 1;
		assertEquals(11,pokers.getTypeofNiuNum());
		newPoker[0].poker_value = 1;
		newPoker[1].poker_value = 7;
		newPoker[2].poker_value = 1;
		newPoker[3].poker_value = 1;
		newPoker[4].poker_value = 1;
		assertEquals(11,pokers.getTypeofNiuNum());
		newPoker[0].poker_value = 7;
		newPoker[1].poker_value = 1;
		newPoker[2].poker_value = 1;
		newPoker[3].poker_value = 1;
		newPoker[4].poker_value = 1;
		assertEquals(11,pokers.getTypeofNiuNum());
		
		
		
		
		newPoker[0].poker_value = 11;
		newPoker[1].poker_value = 10;
		newPoker[2].poker_value = 10;
		newPoker[3].poker_value = 10;
		newPoker[4].poker_value = 10;
		assertEquals(12,pokers.getTypeofNiuNum());
		
		newPoker[0].poker_value = 11;
		newPoker[1].poker_value = 12;
		newPoker[2].poker_value = 10;
		newPoker[3].poker_value = 10;
		newPoker[4].poker_value = 10;
		assertEquals(12,pokers.getTypeofNiuNum());
		
		
		
		newPoker[0].poker_value = 1;
		newPoker[1].poker_value = 1;
		newPoker[2].poker_value = 3;
		newPoker[3].poker_value = 1;
		newPoker[4].poker_value = 4;
		assertEquals(13,pokers.getTypeofNiuNum());
		
		newPoker[0].poker_value = 1;
		newPoker[1].poker_value = 1;
		newPoker[2].poker_value = 1;
		newPoker[3].poker_value = 1;
		newPoker[4].poker_value = 6;
		assertEquals(13,pokers.getTypeofNiuNum());
		
		newPoker[0].poker_value = 10;
		newPoker[1].poker_value = 11;
		newPoker[2].poker_value = 8;
		newPoker[3].poker_value = 4;
		newPoker[4].poker_value = 7;
		assertEquals(0,pokers.getTypeofNiuNum());
	}

	@Test
	public void testIsEqualFour() {
		byte a,b,c,d;
		a = b = c = d = (byte)100;
		assertEquals(true, pokers.isEqualFour(a, b, c, d));
		a = b = (byte)-5;
		assertEquals(false, pokers.isEqualFour(a, b, c, d));
		
	}

}
