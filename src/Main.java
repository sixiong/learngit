import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

	public static final int MAX_DNPOKER_COUNT = 52; // 去掉大小王 Joker
	public static final byte MIN_DNPOKER_VALUE = 1; // A
	public static final byte MAX_DNPOKER_VALUE = 13; // K
	public static final byte DNPOKER_COLOR_COUNT = 4;
	private static final Exception NIU1 = null;
	public static DNPoker pokers[] = new DNPoker[MAX_DNPOKER_COUNT];
	public List<DNPoker> list = new ArrayList<DNPoker>();

	public byte poker_Num_Left = MAX_DNPOKER_VALUE;
	public static int playerNumber = 1;
	public int[] indexOfThree = new int[3];
	public int[] indexOfTwo = new int[2];

	public void initPokers(byte minPoker, byte maxPoker)// 初始化牌
	{
		assert (minPoker <= maxPoker);

		int poker_count_color = maxPoker - minPoker + 1;
		int total_poker_count = DNPOKER_COLOR_COUNT * poker_count_color;

		for (int i = 0; i < total_poker_count; ++i) {
			byte byValue = (byte) ((i % poker_count_color) + minPoker);
			byte byColor = (byte) ((i / poker_count_color) + 1);
			pokers[i] = new DNPoker(byValue, byColor);
		}
	}

	public boolean shuffle(short iTimes)// 随机洗牌
	{

		for (int iT = 0; iT < iTimes; ++iT) {
			int iValue = 0;
			DNPoker median;

			for (int index = 0; index < MAX_DNPOKER_COUNT; index++) {
				Random random = new Random();

				iValue = index + random.nextInt(MAX_DNPOKER_COUNT - index);
				// iValue = index + random() % (MAX_DNPOKER_COUNT - index);
				median = pokers[index];
				pokers[index] = pokers[iValue];
				pokers[iValue] = median;
			}
		}
		for (int j = 0; j < pokers.length; j++) {
			list.add(pokers[j]);
			System.out.println(list.get(j).poker_value + " "
					+ list.get(j).poker_color);
		}
		poker_Num_Left = MAX_DNPOKER_VALUE;
		System.out.println("\n");
		return true;
	}

	public boolean DNDeal(DNPoker poker[])// 随机发牌
	{
		if (poker_Num_Left < 5)
			return false;

		for (int i = 0; i < poker.length; i++) {
			poker[i] = list.get(0);
			list.remove(0);
		}
		return true;
	}

	public boolean DNDeal(DNPoker poker[], byte niu_type)// 特殊牌型发牌(牛丁~牛牛)都可以
	{
		if (poker_Num_Left < 5)
			return false;
		if (niu_type >= 1 && niu_type <= 10) {
			if (HuiSuThree(0, 1, 2) == true) {
				poker[0] = list.get(indexOfThree[0]);
				poker[1] = list.get(indexOfThree[1]);
				poker[2] = list.get(indexOfThree[2]);
				list.remove(indexOfThree[0]);
				list.remove(indexOfThree[1] - 1);
				list.remove(indexOfThree[2] - 2);
				poker_Num_Left -= 3;
			}
			if (HuiSuTwo(0, 1, niu_type) == true)
				OneToTen(poker);
			else {
				poker_Num_Left += 3;
				for (int j = 0; j < 3; j++) {
					list.add(poker[j]);
				}
			}
		} else if (niu_type == 11) {
			for (int i = 0; i < list.size()-4; i++) {
				for (int j = i+1; j < list.size()-3; j++) {
					for (int j2 = j+1; j2 < list.size()-3; j2++) {
						for (int k = 0; k < poker.length; k++) {
							
						}
					}
				}
			}
		} else if (niu_type == 12) {
			byte index = 0;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getRealValue()==10) {
					poker[index] = list.get(i);
					index++;
					if (index==5) {
						break;
					}
				}
			}
		} else if (niu_type == 13) {

		}
		return true;
	}

	public void OneToTen(DNPoker[] poker) {

		poker[3] = list.get(indexOfTwo[0]);
		poker[4] = list.get(indexOfTwo[1]);
		list.remove(indexOfTwo[0]);
		list.remove(indexOfTwo[1] - 1);
		poker_Num_Left -= 2;

	}

	public boolean HuiSuTwo(int a, int b, int niu_num) {
		DNPoker poker1, poker2;

		poker1 = list.get(a);
		int i = b;
		while (i < list.size()) {
			poker2 = list.get(i);
			i++;
			if ((poker1.getRealValue() + poker2.getRealValue()) % 10 == niu_num) {
				indexOfTwo[0] = a;
				indexOfTwo[1] = i - 1;

				return true;
			}

		}
		if (i >= list.size() && a < list.size() - 2) {
			a++;
			b = a + 1;
			return HuiSuTwo(a, b, niu_num);
		} else
			return false;

	}

	public boolean HuiSuThree(int a, int b, int c) {
		DNPoker poker1, poker2, poker3;
		poker1 = list.get(a);
		poker2 = list.get(b);
		int i = c;
		while (i < list.size()) {
			poker3 = list.get(i);
			++i;
			if ((poker1.getRealValue() + poker2.getRealValue() + poker3
					.getRealValue()) % 10 == 0) {
				indexOfThree[0] = a;
				indexOfThree[1] = b;
				indexOfThree[2] = i - 1;
				return true;
			}

		}
		if (i >= list.size() && (a < list.size() - 3 || b < list.size() - 2)) {
			b++;
			c = b + 1;
			if (b >= list.size() - 2) {
				a++;
				b = a + 1;
				c = b + 1;
			}
			return HuiSuThree(a, b, c);
		} else
			return false;
	}

	public DNPoker FindMaxPoker(DNPoker[] poker)// 寻找5张牌里面最大的一张牌
	{
		DNPoker max_poker = new DNPoker(poker[0].poker_value,
				poker[0].poker_value);
		DNPoker current_poker;

		for (int i = 1; i < 5; i++) {
			current_poker = new DNPoker(poker[i].poker_value,
					poker[i].poker_color);
			if ((max_poker.poker_value < current_poker.poker_value)
					|| (max_poker.poker_value == current_poker.poker_value && max_poker.poker_color < poker[i].poker_color)) {
				max_poker = poker[i];
			}
		}
		return max_poker;
	}

	public boolean CompareSinglePoker(DNPoker poker_a, DNPoker poker_b)// 比较单张牌的大小
	{
		if (poker_a.poker_value == poker_b.poker_value) {
			return poker_a.poker_color > poker_b.poker_color;
		} else {
			return poker_a.poker_value > poker_b.poker_value;
		}
	}

	/**
	 ** -1 when a < b; 1 when a > b; otherwise 0.
	 */
	int CompareNiutype(byte a_niu_type, byte b_niu_type)// 都是牛，求比较
	{
		// 比较牛型
		if (a_niu_type < b_niu_type) {
			// 庄输,闲胜
			return -1;
		} else if (a_niu_type == b_niu_type) {
			return 0;
		} else {
			return 1;
		}
	}

	boolean ComparePokers(DNPokers poker_a, DNPokers poker_b) {// 判断poker_a是否比poker_b小

		boolean a_lose = false;

		// 比较牛型，如果牛型相同，比较最大的手牌大小，直到花色
		int ret = CompareNiutype(poker_a.dn_type, poker_b.dn_type);
		if (ret == 0) {
			// 从5张牌中找到最大的牌,判断.左小于右边
			if (!CompareSinglePoker(poker_a.max_poker, poker_b.max_poker)) {
				a_lose = true;
			}
		} else if (ret == -1) {
			a_lose = true;
		}

		return a_lose;
	}

	int DNPokerCompare(DNPoker pokerA, DNPoker pokerB)// 比较两张牌的大小
	{
		if (pokerA.poker_value == pokerB.poker_value) {
			if (pokerA.poker_color == pokerB.poker_color)// 错误
			{
				return 0;
			} else if (pokerA.poker_color > pokerB.poker_color) {
				return 1;
			} else {
				return -1;
			}
		} else if (pokerA.poker_value > pokerB.poker_value) {
			return 1;
		} else if (pokerA.poker_value < pokerB.poker_value) {
			return -1;
		} else {
			return 0;
		}
	}

	boolean DealPokers(DNPokers pokersFenPei[], int playerNumber)// 分配玩家并为每个玩家都分配5张牌,并求其最大牌数
	{
		for (int i = 0; i < playerNumber; i++) {
			// DNDeal(pokersFenPei[i].poker);
			DNDeal(pokersFenPei[i].poker, (byte) 12);
			pokersFenPei[i].SetMaxPoker(FindMaxPoker(pokersFenPei[i].poker));
			// FindMaxPoker(pokersFenPei[i].poker);
		}
		return true;
	}

	String GetDNTypeToString(byte tType) {
		String type = "mei_niu";
		switch (tType) {
		case 1:
			type = "niu_ding";
			break;
		case 2:
			type = "niu_er";
			break;
		case 3:
			type = "niu_san";
			break;
		case 4:
			type = "niu_si";
			break;
		case 5:
			type = "niu_wu";
			break;
		case 6:
			type = "niu_liu";
			break;
		case 7:
			type = "niu_qi";
			break;
		case 8:
			type = "niu_ba";
			break;
		case 9:
			type = "niu_jiu";
			break;
		case 10:
			type = "niu_niu";
			break;
		case 11:
			type = "si_zha";
			break;
		case 12:
			type = "wu_hua_niu";
			break;
		case 13:
			type = "wu_xiao_niu";
			break;
		default:
			type = "mei_niu";
			break;
		}
		return type;
	}

	String GetDNType(byte tType) {
		String type = "没牛";
		switch (tType) {
		case 0:
			type = "没牛";
			break;
		case 1:
			type = "牛丁";
			break;
		case 2:
			type = "牛二";
			break;
		case 3:
			type = "牛三";
			break;
		case 4:
			type = "牛四";
			break;
		case 5:
			type = "牛五";
			break;
		case 6:
			type = "牛六";
			break;
		case 7:
			type = "牛七";
			break;
		case 8:
			type = "牛八";
			break;
		case 9:
			type = "牛九";
			break;
		case 10:
			type = "牛牛";
			break;
		case 11:
			type = "四炸";
			break;
		case 12:
			type = "五花牛";
			break;
		case 13:
			type = "五小牛";
			break;
		default:
			type = "没牛";
			break;
		}
		return type;
	}

	public static void main(String args[]) {
		Main main = new Main();
		main.initPokers((byte) 1, (byte) 13);
		main.shuffle((short) 360);
		DNPokers[] examples = new DNPokers[playerNumber];
		for (int i = 0; i < examples.length; i++) {
			examples[i] = new DNPokers();
			for (int j = 0; j < 5; j++) {
				examples[i].poker[j] = new DNPoker();
			}
		}
		main.DealPokers(examples, playerNumber);
		for (int i = 0; i < examples.length; i++) {
			for (int j = 0; j < examples[0].poker.length; j++) {
				System.out.println(examples[i].poker[j].poker_value + " "
						+ examples[i].poker[j].poker_color);
			}
			System.out.print('\n');
		}
		System.out.println(examples[0].getTypeofNiuNum());
		// System.out.println(examples[1].getTypeofNiuNum());
		// System.out.println(main.ComparePokers(examples[0], examples[1]));

		// Main main = new Main();
		// main.initPokers((byte)1,(byte)13);
		// main.shuffle((short)360);
		// DNPokers example = new DNPokers();
		// for (int i = 0; i < 5; i++) {
		// example.poker[i] = new DNPoker((byte)i,(byte)1);
		// }
		// example.poker[0].poker_value = 2;
		// example.poker[1].poker_value = 5;
		// example.poker[2].poker_value = 7;
		// example.poker[3].poker_value = 4;
		// example.poker[4].poker_value = 5;
		// System.out.println(example.getTypeofNiuNum());
	}
}
