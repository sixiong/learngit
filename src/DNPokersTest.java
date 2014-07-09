import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;


public class DNPokersTest {
	DNPokers pokers;

	@Before
	public void testDNPokers() {
		pokers = new DNPokers();
	}



	@Test
	public void testSetPoker() {
		DNPoker []newPoker = new DNPoker[5];
		for (int j = 0; j < newPoker.length; j++) {
			newPoker[j] = new DNPoker();
		}
		pokers.SetPoker(newPoker);
		for (int i = 0; i < newPoker.length; i++) {
			assertNotNull(pokers.poker[i]);
			assertEquals(0,pokers.poker[i].poker_value);
			assertEquals(0,pokers.poker[i].poker_color);
		}
		
	}

	@Test
	public void testSetMaxPoker() {
		DNPoker poker_max = new DNPoker((byte)13,(byte)4);
		pokers.SetMaxPoker(poker_max);
		assertNotNull(pokers.max_poker);
		assertEquals(poker_max,pokers.max_poker);
	}
	@Parameters
	@Test
	public void testGetTypeofNiuNum() {
		DNPoker []newPoker = new DNPoker[5];
		for (int j = 0; j < newPoker.length; j++) {
			newPoker[j] = new DNPoker((byte)(j+9),(byte)3);	
		}
		newPoker[0].poker_value = 11;
		newPoker[1].poker_value = 11;
		newPoker[2].poker_value = 11;
		newPoker[3].poker_value = 11;
		newPoker[4].poker_value = 12;
		
		newPoker[0].poker_value = 1;
		newPoker[1].poker_value = 1;
		newPoker[2].poker_value = 1;
		newPoker[3].poker_value = 1;
		newPoker[4].poker_value = 2;
		
		pokers.SetPoker(newPoker);
		//assertEquals(5, pokers.getTypeofNiuNum());
		System.out.print(pokers.getTypeofNiuNum());
	}

	@Test
	public void testIsEqualFour() {
		byte a,b,c,d;
		a = b = c = d = (byte)100;
//		a = b = c = d = (byte)-5;
		assertEquals(true, pokers.isEqualFour(a, b, c, d));
	}

}
