import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;


public class DNPokerTest {
	DNPoker poker = null;
	@Before
	public void setUp() throws Exception {
		poker = new DNPoker();
	}

	@Test
	public void testDNPokerByteByte() {
		byte index1,index2;
		for (int i = 0; i < 100; i++) {
			index1 = (byte) (GetRandTow(13)+1);
			index2 = (byte) (GetRandTow(4)+1);
//			System.out.println(index1+" "+index2);
			poker = new DNPoker(index1,index2);
			assertNotNull(poker);
			assertEquals(index1,poker.poker_value);
			assertEquals(index2,poker.poker_color);
		}
		poker = new DNPoker((byte)0,(byte)4);
		assertEquals(0,poker.poker_value);
		assertEquals(0,poker.poker_color);
		poker = new DNPoker((byte)15,(byte)4);
		assertEquals(0,poker.poker_value);
		assertEquals(0,poker.poker_color);
		poker = new DNPoker((byte)3,(byte)8);
		assertEquals(0,poker.poker_value);
		assertEquals(0,poker.poker_color);
		poker = new DNPoker((byte)3,(byte)0);
		assertEquals(0,poker.poker_value);
		assertEquals(0,poker.poker_color);
	}
	public static byte GetRandTow(int num)
	{
		byte index = 0;
		Random random = new Random();
			index = (byte) random.nextInt(num);
		return index;
	}
	public static byte[] GetRandTow2(int num)
	{
		byte[] index = new byte[num];
		Random random = new Random();
		random.nextInt(128);
		for (int i = 0; i < index.length; i++) {
			index[i] = (byte) random.nextInt(128);
		}		
		return index;
	}
	@Test
	public void testDNPoker() {
		for (int i = 0; i < 10; i++) {
			poker = new DNPoker();
			assertNotNull(poker);
			assertEquals(0,poker.poker_value);
			assertEquals(0,poker.poker_color);
		}
	}

	@Test
	public void testReset() {
		byte[] index = new byte[2];
		for (int i = 0; i < 100; i++) {
			index[0] = (byte) (GetRandTow(13)+1);
			index[1] = (byte) (GetRandTow(4)+1);
//			System.out.println(index[0]+" "+index[1]);
			poker = new DNPoker(index[0], index[1]);
			assertNotNull(poker);
			assertEquals(index[0],poker.poker_value);
			assertEquals(index[1],poker.poker_color);
			poker.Reset();
			assertEquals(0, poker.poker_value);
			assertEquals(0, poker.poker_color);
		}
	}

	@Test
	public void testGetRealValue() {
		byte[] index = new byte[2];
		for (int i = 0; i < 10; i++) {
			index[0] = (byte) (GetRandTow(13)+1);
			index[1] = (byte) (GetRandTow(4)+1);
			poker = new DNPoker(index[0], index[1]);
			if (index[0]>=10) {
				assertEquals(10, poker.getRealValue());
			}
			else {
				assertEquals(poker.poker_value, poker.getRealValue());
			}
		}
	}

}
