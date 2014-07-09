import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class MainTest {
	Main main = null;
	@Before
	public void setUp() throws Exception {
		main = new Main();
		main.initPokers((byte)1, (byte)13);
	}

	@Test
	public void testInitPokers() {
		assertEquals(52, main.pokers.length);
		assertNotNull(main.pokers[0]);
		assertNotNull(main.pokers[4]);
		assertNotNull(main.pokers[51]);
	}

	@Test
	public void testShuffle() {
		main.shuffle((short)30);
		printPoker(main.pokers);
	}
	@Ignore
	public void printPoker(DNPoker[] objects)
	{
		for (int i = 0; i < objects.length; i++) {
			System.out.println(objects[i].poker_value+" "+objects[i].poker_color);
		}
	}
	@Test
	public void testDNDealDNPokerArray() {
		DNPoker[] pokerr = new DNPoker[5];
		main.shuffle((short)30);
		printPoker(main.pokers);
		main.DNDeal(pokerr);
		System.out.println("\n"+"\n");
		printPoker(pokerr);
		System.out.println("\n"+"\n");
		for (int i = 0; i < main.list.size(); i++) {
			System.out.println(main.list.get(i).poker_value+" "+main.list.get(i).poker_color);
		}
	}

	@Test
	public void testDNDealDNPokerArrayByte() {
		fail("Not yet implemented");
	}

	@Test
	public void testHuiSuabcde() {
		fail("Not yet implemented");
	}

	@Test
	public void testFirstLessSix() {
		fail("Not yet implemented");
	}

	@Test
	public void testCount_list() {
		fail("Not yet implemented");
	}

	@Test
	public void testOneToTen() {
		fail("Not yet implemented");
	}

	@Test
	public void testHuiSuTwo() {
		fail("Not yet implemented");
	}

	@Test
	public void testHuiSuThree() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindMaxPoker() {
		fail("Not yet implemented");
	}

	@Test
	public void testCompareSinglePoker() {
		fail("Not yet implemented");
	}

	@Test
	public void testCompareNiutype() {
		fail("Not yet implemented");
	}

	@Test
	public void testComparePokers() {
		fail("Not yet implemented");
	}

	@Test
	public void testDNPokerCompare() {
		fail("Not yet implemented");
	}

	@Test
	public void testDealPokers() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDNTypeToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDNType() {
		fail("Not yet implemented");
	}

	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

}
