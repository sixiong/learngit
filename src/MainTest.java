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
//		printPoker(main.pokers);
//		
//		for (int i = 0; i < main.list.size(); i++) {
//			System.out.println(main.list.get(i).poker_value+" "+main.list.get(i).poker_color);
//		}
//			System.out.println(main.poker_Num_Left);
	}

	@Test
	public void testShuffle() {
		assertEquals(true, main.shuffle((short)30));
//		printPoker(main.pokers);
//		System.out.println("\n\n\n");
//		for (int i = 0; i < main.list.size(); i++) {
//			System.out.println(main.list.get(i).poker_value+" "+main.list.get(i).poker_color);
//		}
//		System.out.println(main.poker_Num_Left);
	}
	
	@Ignore
	public void printPoker(DNPoker[] objects)
	{
		for (int i = 0; i < objects.length; i++) {
			System.out.println(objects[i].poker_value+" "+objects[i].poker_color);
		}
		System.out.print("\n");
	}
	
	@Test
	public void testDNDealDNPokerArray() {
		DNPoker[] pokerr = new DNPoker[5];
		main.shuffle((short)30);
		for (int i = 0; i < 10; i++) {
			assertEquals(true,main.DNDeal(pokerr));
		}	
		assertEquals(2, main.poker_Num_Left);
		assertEquals(2, main.list.size());
		assertEquals(false, main.DNDeal(pokerr));
	}
	@Test
	public void testMeiNiuCheck()
	{
		DNPoker[] newPoker = new DNPoker[5];
		for (int i = 0; i < newPoker.length; i++) {
			newPoker[i] = new DNPoker();
		}
		newPoker[0].poker_value = 2;
		newPoker[1].poker_value = 1;
		newPoker[2].poker_value = 1;
		newPoker[3].poker_value = 1;
		newPoker[4].poker_value = 7;
		assertEquals(false, main.MeiNiuCheck(newPoker));
		
		newPoker[0].poker_value = 2;
		newPoker[1].poker_value = 2;
		newPoker[2].poker_value = 2;
		newPoker[3].poker_value = 2;
		newPoker[4].poker_value = 7;
		assertEquals(true, main.MeiNiuCheck(newPoker));
	}
	@Test
	public void testDNDealDNPokerArrayByte() {
		DNPoker[] poker = new DNPoker[5];
		for (int i = 0; i < 14; i++) {
			main.shuffle((short)30);
			for (int j = 0; j < 50; j++) {
				 main.DNDeal(poker, i);
			}
		}
	}

	@Test
	public void testHuiSuabcde() {
		main.shuffle((short)30);
		DNPoker[] poker = new DNPoker[5];
		for (int i = 0; i < 100; i++) {
			main.HuiSuabcde(poker);
		}
	
	}

	@Test
	public void testFirstLessSix() {
		main.shuffle((short)30);
		for (int i = 0; i < main.list.size(); i++) {
		System.out.println(main.list.get(i).poker_value+" "+main.list.get(i).poker_color);
	}
		System.out.println(main.FirstLessSix());
		for (int i = 0; i < main.list.size(); i++) {
			main.list.get(i).poker_value = 10;
		}
		System.out.println(main.FirstLessSix());
	}

	@Test
	public void testCount_list() {
		main.shuffle((short)30);
		for (int i = 0; i < main.list.size()-30; i++) {
			main.list.get(i).poker_value = 20;
		}
		assertEquals(22, main.count_list(20));
		main.count_list(3);
	}

	@Test
	public void testHuiSuTwo() {
		main.shuffle((short)30);
		for (int i = 0; i < 50; i++) {
			for (int j = 1; j <= 10; j++) {
				main.HuiSuTwo(i,i+1,j);
			}
		}
	}

	@Test
	public void testHuiSuThree() {	
		main.shuffle((short)30);
		for (int i = 0; i < 50; i++) {
			main.HuiSuThree(i, i+1, i+2);
		}
	}

	@Test
	public void testFindMaxPoker() {
		DNPoker[] poker = new DNPoker[5];
		poker[0] = new DNPoker((byte)4,(byte)3);
		poker[1] = new DNPoker((byte)7,(byte)3);
		poker[2] = new DNPoker((byte)4,(byte)2);
		poker[3] = new DNPoker((byte)13,(byte)4);
		poker[4] = new DNPoker((byte)13,(byte)3);
		assertEquals(poker[3],main.FindMaxPoker(poker));
		
		poker[0] = new DNPoker((byte)4,(byte)3);
		poker[1] = new DNPoker((byte)7,(byte)3);
		poker[2] = new DNPoker((byte)2,(byte)2);
		poker[3] = new DNPoker((byte)13,(byte)4);
		poker[4] = new DNPoker((byte)13,(byte)1);
		assertEquals(poker[3],main.FindMaxPoker(poker));	
	}

	@Test
	public void testCompareSinglePoker() {
		DNPoker poker_aDnPoker = new DNPoker((byte)12,(byte)3);
		DNPoker poker_bDnPoker = new DNPoker((byte)11,(byte)3);
		assertEquals(true, main.CompareSinglePoker(poker_aDnPoker, poker_bDnPoker));
		
		poker_aDnPoker = new DNPoker((byte)8,(byte)3);
		poker_bDnPoker = new DNPoker((byte)11,(byte)3);
		assertEquals(false, main.CompareSinglePoker(poker_aDnPoker, poker_bDnPoker));
		
		poker_aDnPoker = new DNPoker((byte)12,(byte)3);
		poker_bDnPoker = new DNPoker((byte)12,(byte)1);
		assertEquals(true, main.CompareSinglePoker(poker_aDnPoker, poker_bDnPoker));
		
		poker_aDnPoker = new DNPoker((byte)12,(byte)1);
		poker_bDnPoker = new DNPoker((byte)12,(byte)3);
		assertEquals(false,main.CompareSinglePoker(poker_aDnPoker, poker_bDnPoker));
	}

	@Test
	public void testCompareNiutype() {
		byte a = 3;
		byte b = 4;
		assertEquals(-1, main.CompareNiutype(a, b));
		
		a = 4;
		b = 4;
		assertEquals(0, main.CompareNiutype(a, b));
		
		a = 12;
		b = 4;
		assertEquals(1, main.CompareNiutype(a, b));
	}

	@Test
	public void testComparePokers() {
		DNPokers[] examples_a = new DNPokers[1];
		for (int i = 0; i < examples_a.length; i++) {
			examples_a[i] = new DNPokers();
		}
		DNPokers[] examples_b = new DNPokers[1];
		for (int i = 0; i < examples_b.length; i++) {
			examples_b[i] = new DNPokers();
		}
		for (int i = 0; i < examples_b.length; i++) {
			main.shuffle((short)30);
			main.DealPokers(examples_a, 1, 2);
			main.DealPokers(examples_b, 1, 2);
			main.ComparePokers(examples_a[0], examples_b[0]);
		}		
		main.shuffle((short)30);
		main.DealPokers(examples_a, 1, 2);
		main.DealPokers(examples_b, 1, 3);
		main.ComparePokers(examples_a[0], examples_b[0]);
		
		main.shuffle((short)30);
		main.DealPokers(examples_a, 1, 3);
		main.DealPokers(examples_b, 1, 2);
		main.ComparePokers(examples_a[0], examples_b[0]);
	}

	@Test
	public void testDealPokers() {
		DNPokers[] examples = new DNPokers[1];
		for (int i = 0; i < examples.length; i++) {
			examples[i] = new DNPokers();
		}
		for (int i = -1; i < 14; i++) {
			assertEquals(true, main.shuffle((short)100));
			main.DealPokers(examples, 1, i);
			if (i!=-1) {
				assertEquals(i, examples[0].getTypeofNiuNum());
			}
			
		}	
	}

	@Test
	public void testGetColorType() {
		assertEquals("error", main.GetColorType((byte)8));
		assertEquals("·½¿é", main.GetColorType((byte)1));
		assertEquals("Ó£ÌÒ", main.GetColorType((byte)2));
		assertEquals("ÌÒ»¨", main.GetColorType((byte)3));
		assertEquals("ºÚÌÒ", main.GetColorType((byte)4));
	}

	@Test
	public void testGetDNType() {
		assertEquals("error", main.GetDNType((byte)14));
		assertEquals("Ã»Å£", main.GetDNType((byte)0));
		assertEquals("Å£¶¡", main.GetDNType((byte)1));
		assertEquals("Å£¶þ", main.GetDNType((byte)2));
		assertEquals("Å£Èý", main.GetDNType((byte)3));
		assertEquals("Å£ËÄ", main.GetDNType((byte)4));
		assertEquals("Å£Îå", main.GetDNType((byte)5));
		assertEquals("Å£Áù", main.GetDNType((byte)6));
		assertEquals("Å£Æß", main.GetDNType((byte)7));
		assertEquals("Å£°Ë", main.GetDNType((byte)8));
		assertEquals("Å£¾Å", main.GetDNType((byte)9));
		assertEquals("Å£Å£", main.GetDNType((byte)10));
		assertEquals("ËÄÕ¨", main.GetDNType((byte)11));
		assertEquals("Îå»¨Å£", main.GetDNType((byte)12));
		assertEquals("ÎåÐ¡Å£", main.GetDNType((byte)13));
	}

	@Ignore
	public void testMain() {
		fail("Not yet implemented");
	}

}
