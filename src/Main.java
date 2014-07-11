import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

	public static final int MAX_DNPOKER_COUNT = 52; // 去掉大小王 Joker
	public static final byte MIN_DNPOKER_VALUE = 1; // A
	public static final byte MAX_DNPOKER_VALUE = 13; // K
	public static final byte DNPOKER_COLOR_COUNT = 4;
	private static final Exception NIU1 = null;
	public static DNPoker pokers[] = new DNPoker[MAX_DNPOKER_COUNT];
	public List<DNPoker> list = new ArrayList<DNPoker>();
	public byte poker_Num_Left = MAX_DNPOKER_COUNT;
	public static int playerNumber = 2;
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
				median = pokers[index];
				pokers[index] = pokers[iValue];
				pokers[iValue] = median;
			}
		}
		list.clear();
		for (int j = 0; j < pokers.length; j++) {
			list.add(pokers[j]);
		}
		poker_Num_Left = MAX_DNPOKER_COUNT;
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
		poker_Num_Left -=5;
		return true;
	}
	public boolean MeiNiuCheck(DNPoker[] poker)
	{
		boolean ifHasNiu = false;
		for(int i = 0;i < 3;i++)
			for(int j = i+1;j < 4;j++)
				for(int k = j+1;k < 5;k++)
					if((poker[i].getRealValue()+poker[j].getRealValue()+poker[k].getRealValue())%10==0)
					{
						ifHasNiu = true;
						break;
					}
		if(ifHasNiu==false)
			return true;
		else {
			return false;
		}
	}
	public boolean DNDeal(DNPoker poker[], int niu_type)// 特殊牌型发牌(没牛~小五)都可以
	{
		boolean ifFlag = false;
		if (poker_Num_Left < 5)
			return false;
		else {
			if (niu_type==0) {
			for (int i = 0; i < list.size()-5; i++) {
				for (int j = i+1; j < list.size()-4; j++) {
					for (int j2 = j+1; j2 < list.size()-3; j2++) {
						for (int k = j2+1; k < list.size()-2; k++) {
							for (int k2 = k+1; k2 < list.size()-1; k2++) {
								poker[0] = list.get(i);
								poker[1] = list.get(j);
								poker[2] = list.get(j2);
								poker[3] = list.get(k);
								poker[4] = list.get(k2);
								if (MeiNiuCheck(poker)==true) {
		
									for(int l = 0;l < poker.length;l++)
										list.remove(poker[l]);
									poker_Num_Left-=5;
									ifFlag = true;
									return true;
								}
							}
						}
					}
				}
			}
			if (ifFlag==false) {
				return false;
			}
		}
		else if (niu_type >= 1 && niu_type <= 10) {
			if (HuiSuThree(0, 1, 2) == true) {
				poker[0] = list.get(indexOfThree[0]);
				poker[1] = list.get(indexOfThree[1]);
				poker[2] = list.get(indexOfThree[2]);
				list.remove(poker[0]);
				list.remove(poker[1]);
				list.remove(poker[2]);
				poker_Num_Left -= 3;
			}
			if (HuiSuTwo(0, 1, niu_type) == true)
			{
				poker[3] = list.get(indexOfTwo[0]);
				poker[4] = list.get(indexOfTwo[1]);
				list.remove(poker[3]);
				list.remove(poker[4]);
				poker_Num_Left -= 2;
				return true;
			}
			else {
				for (int j = 0; j < 3; j++) {
					list.add(poker[j]);
				}
				poker_Num_Left += 3;
				return false;
			}
		} 
		else if (niu_type == 11) {
			int k = 0;
			int jj =0;
			for (jj = 1; jj < 14; jj++) {
				if (count_list(jj) == (byte)4) {

					k = jj;
					break;
				}
			}
			if (jj>14) {
				return false;
			}
			else {
				for (int i = 0,numk = 0; i < list.size(); i++) {
					if (k==list.get(i).poker_value) {
						poker[numk++] = list.get(i);
					}
				}
				for (int i = 0; i < poker.length-1; i++) {
					list.remove(poker[i]);
				}
				if(k<10)
				{
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).poker_value != k&&(4*k+list.get(i).poker_value)>10) {
						poker[4] = list.get(i);
						break;
					}
				}
				}
				else {
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).poker_value != k&&(4*k+list.get(i).poker_value)>10&&list.get(i).poker_value<10) {
							poker[4] = list.get(i);
							break;
						}
					}
				}
				list.remove(poker[4]);
				poker_Num_Left -=5;
				return true;
			}	
		} 
		else if (niu_type == 12) {
			byte index = 0;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getRealValue() == 10) {
					poker[index] = list.get(i);
					index++;
					if (index == 5) {
						break;
					}
				}
			}
			if (index==5) {
				for (int i = 0; i < poker.length; i++) {
					list.remove(poker[i]);
				}
				return true;
			}
			else {
				return false;
			}
		} 
		else if (niu_type == 13) {
			if (HuiSuabcde(poker) == true)
			{
				for (int i = 0; i < poker.length; i++) {
					list.remove(poker[i]);
				}
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
		return false;
}

	public boolean HuiSuabcde(DNPoker[] poker) {
		int indexOfb = 0;
		int indexOfc = 0;
		int indexOfd = 0;
		int indexOfe = 0;
		int indexOfa = 0;
		indexOfa = FirstLessSix();
		if (indexOfa==-1) {
			return false;
		}
		poker[0] = list.get(indexOfa);

		for (int i = indexOfa + 1; i < list.size(); i++) {
			if ((poker[0].poker_value + list.get(i).poker_value) <= 7) {
				indexOfb = i;
				break;
			}
		}
		if (indexOfb == 0) {
			System.out.println("indexOfb==0!");
			return false;
		}

		else {
			poker[1] = list.get(indexOfb);
			for (int i = indexOfb + 1; i < list.size(); i++) {
				if ((poker[0].getRealValue() + poker[1].getRealValue() + list
						.get(i).getRealValue()) <= 8) {
					indexOfc = i;
					break;
				}
			}
			if (indexOfc == 0) {
				System.out.println("indexOfc==0!");
				return false;
			} else {
				poker[2] = list.get(indexOfc);
				for (int i = indexOfc + 1; i < list.size(); i++) {
					if ((poker[0].getRealValue() + poker[1].getRealValue()
							+ poker[2].getRealValue() + list.get(i)
							.getRealValue()) <= 9) {
						indexOfd = i;
						break;
					}
				}
				if (indexOfd == 0) {
					return false;
				} else {
					poker[3] = list.get(indexOfd);

					for (int i = indexOfd + 1; i < list.size(); i++) {
						if ((poker[0].getRealValue() + poker[1].getRealValue()
								+ poker[2].getRealValue()
								+ poker[3].getRealValue() + list.get(i)
								.getRealValue()) <= 10) {
							indexOfe = i;
							break;
						}
					}
					if (indexOfe == 0) {
						return false;
					} else {

						poker[4] = list.get(indexOfe);
						return true;
					}
				}
			}
		}
	}

	public int FirstLessSix() {
		int num = -1;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getRealValue() <= 6) {
				num = i;
				break;
			}
		}
		return num;
	}

	public byte count_list(int j) {
		byte num = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).poker_value == j) {
				num++;
			}
		}
		return num;
	}

	public boolean HuiSuTwo(int a, int b, int niu_num) {
		DNPoker poker1, poker2;
		poker1 = list.get(a);
		int i = b;
		while (i < list.size()) {
			poker2 = list.get(i);
			i++;
			if (niu_num!=10) {
				if ((poker1.getRealValue() + poker2.getRealValue()) % 10 == niu_num) {
					indexOfTwo[0] = a;
					indexOfTwo[1] = i - 1;
					return true;
				}
			}
			else {
				if ((poker1.getRealValue() + poker2.getRealValue()) % 10 == 0) {
					indexOfTwo[0] = a;
					indexOfTwo[1] = i - 1;
					return true;
				}
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

	boolean DealPokers(DNPokers pokersFenPei[], int playerNumber,int type)// 分配玩家并为每个玩家都分配5张牌,并求其最大牌数
	{
		if (type==-1) {//随机发牌
		for (int i = 0; i < playerNumber; i++) {
			DNDeal(pokersFenPei[i].poker);
			pokersFenPei[i].SetMaxPoker(FindMaxPoker(pokersFenPei[i].poker));
			}
		}
		else{//特殊牌型发牌
			for (int i = 0; i < playerNumber; i++) {
				DNDeal(pokersFenPei[i].poker, type);
				pokersFenPei[i].SetMaxPoker(FindMaxPoker(pokersFenPei[i].poker));
			}
		}
		
		return true;
	}

	String GetDNType(byte type2) {
		String type = "error";
		switch (type2) {
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
			type = "error";
			break;
		}
		return type;
	}
	String GetColorType(byte tType) {
		String type = "";
		switch (tType) {
		case 1:
			type = "方块";
			break;
		case 2:
			type = "樱桃";
			break;
		case 3:
			type = "桃花";
			break;
		case 4:
			type = "黑桃";
			break;
		default:
			type = "error";
			break;
		}
		return type;
	}
//	public static void main(String args[]) {
//		Main main = new Main();
//		main.initPokers((byte) 1, (byte) 13);
//		main.shuffle((short) 360);
//		DNPokers[] examples = new DNPokers[playerNumber];
//		for (int i = 0; i < examples.length; i++) {
//			examples[i] = new DNPokers();
////			for (int j = 0; j < 5; j++){
////				examples[i].poker[j] = new DNPoker();
////			}
//		}
//		System.out.println("先开始为两个玩家顺序发牌！(非特殊牌型)");
//		main.DealPokers(examples, playerNumber,-1);
//		for (int i = 0; i < examples.length; i++) {
//			if (i==0) {
//				System.out.println("玩家甲(闲):");
//			}
//			else {
//				System.out.println("玩家乙(庄):");
//			}
//			for (int j = 0; j < examples[i].poker.length; j++) {
//				System.out.println(main.GetColorType(examples[i].poker[j].poker_color)+examples[i].poker[j].poker_value + " ");
//			}
//			System.out.println("牌型："+main.GetDNType(examples[i].getTypeofNiuNum()));
//			System.out.println("");
//		}
//		boolean flag = false;
//		flag = main.ComparePokers(examples[0], examples[1]);
//		System.out.println("比较结果:");
//		if (flag == true) {
//			System.out.println("乙胜利！");
//		}
//		else {
//			System.out.println("甲胜利！");
//		}
//		
//		
//		DNPokers[] vips = new DNPokers[1];
//		for (int i = 0; i < vips.length; i++) {
//			vips[i] = new DNPokers();
//		}
//		System.out.println("我是VIP,我要选择得到特殊牌型！输入牌型值：");
//		Scanner sc = new Scanner(System.in); 
//		int type = sc.nextInt();
//		main.DealPokers(vips, 1,type);
//		System.out.println("所抽牌型"+main.GetDNType((byte)type)+"如下所示:");
//		for (int i = 0; i < vips[0].poker.length; i++) {
//			System.out.println(main.GetColorType(vips[0].poker[i].poker_color)+vips[0].poker[i].poker_value + " ");
//		}
//	}
}
