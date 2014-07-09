import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

	public static final int MAX_DNPOKER_COUNT = 52; // ȥ����С�� Joker
	public static final byte MIN_DNPOKER_VALUE = 1; // A
	public static final byte MAX_DNPOKER_VALUE = 13; // K
	public static final byte DNPOKER_COLOR_COUNT = 4;
	private static final Exception NIU1 = null;
	public static DNPoker pokers[] = new DNPoker[MAX_DNPOKER_COUNT];
	public List<DNPoker> list = new ArrayList<DNPoker>();

	public byte poker_Num_Left = MAX_DNPOKER_VALUE;
	public static int playerNumber = 2;
	public int[] indexOfThree = new int[3];
	public int[] indexOfTwo = new int[2];

	public void initPokers(byte minPoker, byte maxPoker)// ��ʼ����
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
	public boolean shuffle(short iTimes)// ���ϴ��
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
//			System.out.println(list.get(j).poker_value + " "
//					+ list.get(j).poker_color);
		}
		poker_Num_Left = MAX_DNPOKER_VALUE;
//		System.out.println("\n");
		return true;
	}
	
	public boolean DNDeal(DNPoker poker[])// �������
	{
		if (poker_Num_Left < 5)
			return false;

		for (int i = 0; i < poker.length; i++) {
			poker[i] = list.get(0);
			list.remove(0);
		}
		return true;
	}

	public boolean DNDeal(DNPoker poker[], int niu_type)// �������ͷ���(ţ��~С��)������
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
			int k = 0;
			for (int j = 1; j < 14; j++) {
				if (count_list(j) == 4) {
					k = j;
					break;
				}
			}
			for (int i = 0; i < poker.length - 1; i++) {
				poker[i].poker_value = (byte) k;
				poker[i].poker_color = (byte) (i + 1);
			}
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).poker_value != k) {
					poker[4] = list.get(i);
					break;
				}
			}
			for (int i = 0; i < poker.length; i++) {
				list.remove(poker[i]);
			}
		} else if (niu_type == 12) {
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
			for (int i = 0; i < poker.length; i++) {
				list.remove(poker[i]);
			}
		} else if (niu_type == 13) {
			if (HuiSuabcde(poker) == true)
				for (int i = 0; i < poker.length; i++) {
					list.remove(poker[i]);
				}
			else {
				System.out.println("Not Found!");
			}
		}
		return true;
	}

	public boolean HuiSuabcde(DNPoker[] poker) {
		int indexOfb = 0;
		int indexOfc = 0;
		int indexOfd = 0;
		int indexOfe = 0;
		int indexOfa = 0;
		indexOfa = FirstLessSix();
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
		int num = 0;
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

	public void OneToTen(DNPoker[] poker) {

		poker[3] = list.get(indexOfTwo[0]);
		poker[4] = list.get(indexOfTwo[1]);
		list.remove(indexOfTwo[0]);
		list.remove(indexOfTwo[1] - 1);
		poker_Num_Left -= 2;

	}

	public boolean HuiSuTwo(int a, int b, int niu_num) {
		DNPoker poker1, poker2;
		if (niu_num==10) {
			niu_num = 0;
		}
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

	public DNPoker FindMaxPoker(DNPoker[] poker)// Ѱ��5������������һ����
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

	public boolean CompareSinglePoker(DNPoker poker_a, DNPoker poker_b)// �Ƚϵ����ƵĴ�С
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
	int CompareNiutype(byte a_niu_type, byte b_niu_type)// ����ţ����Ƚ�
	{
		// �Ƚ�ţ��
		if (a_niu_type < b_niu_type) {
			// ׯ��,��ʤ
			return -1;
		} else if (a_niu_type == b_niu_type) {
			return 0;
		} else {
			return 1;
		}
	}

	boolean ComparePokers(DNPokers poker_a, DNPokers poker_b) {// �ж�poker_a�Ƿ��poker_bС

		boolean a_lose = false;

		// �Ƚ�ţ�ͣ����ţ����ͬ���Ƚ��������ƴ�С��ֱ����ɫ
		int ret = CompareNiutype(poker_a.dn_type, poker_b.dn_type);
		if (ret == 0) {
			// ��5�������ҵ�������,�ж�.��С���ұ�
			if (!CompareSinglePoker(poker_a.max_poker, poker_b.max_poker)) {
				a_lose = true;
			}
		} else if (ret == -1) {
			a_lose = true;
		}

		return a_lose;
	}

	int DNPokerCompare(DNPoker pokerA, DNPoker pokerB)// �Ƚ������ƵĴ�С
	{
		if (pokerA.poker_value == pokerB.poker_value) {
			if (pokerA.poker_color == pokerB.poker_color)// ����
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

	boolean DealPokers(DNPokers pokersFenPei[], int playerNumber,int type)// ������Ҳ�Ϊÿ����Ҷ�����5����,�������������
	{
		if (type==0) {
		for (int i = 0; i < playerNumber; i++) {
			DNDeal(pokersFenPei[i].poker);
			pokersFenPei[i].SetMaxPoker(FindMaxPoker(pokersFenPei[i].poker));
			}
		}
		else{
			for (int i = 0; i < playerNumber; i++) {
				DNDeal(pokersFenPei[i].poker, type);
				pokersFenPei[i].SetMaxPoker(FindMaxPoker(pokersFenPei[i].poker));
			}
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

	String GetDNType(byte type2) {
		String type = "ûţ";
		switch (type2) {
		case 0:
			type = "ûţ";
			break;
		case 1:
			type = "ţ��";
			break;
		case 2:
			type = "ţ��";
			break;
		case 3:
			type = "ţ��";
			break;
		case 4:
			type = "ţ��";
			break;
		case 5:
			type = "ţ��";
			break;
		case 6:
			type = "ţ��";
			break;
		case 7:
			type = "ţ��";
			break;
		case 8:
			type = "ţ��";
			break;
		case 9:
			type = "ţ��";
			break;
		case 10:
			type = "ţţ";
			break;
		case 11:
			type = "��ը";
			break;
		case 12:
			type = "�廨ţ";
			break;
		case 13:
			type = "��Сţ";
			break;
		default:
			type = "ûţ";
			break;
		}
		return type;
	}
	String GetColorType(byte tType) {
		String type = "";
		switch (tType) {
		case 1:
			type = "����";
			break;
		case 2:
			type = "ӣ��";
			break;
		case 3:
			type = "�һ�";
			break;
		case 4:
			type = "����";
			break;
		default:
			type = "";
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
//			for (int j = 0; j < 5; j++){
//				examples[i].poker[j] = new DNPoker();
//			}
		}
		System.out.println("�ȿ�ʼΪ�������˳���ƣ�(����������)");
		main.DealPokers(examples, playerNumber,0);
		for (int i = 0; i < examples.length; i++) {
			if (i==0) {
				System.out.println("��Ҽ�(��):");
			}
			else {
				System.out.println("�����(ׯ):");
			}
			for (int j = 0; j < examples[i].poker.length; j++) {
				System.out.println(main.GetColorType(examples[i].poker[j].poker_color)+examples[i].poker[j].poker_value + " ");
			}
			System.out.println("���ͣ�"+main.GetDNType(examples[i].getTypeofNiuNum()));
			System.out.println("");
		}
		boolean flag = false;
		flag = main.ComparePokers(examples[0], examples[1]);
		System.out.println("�ȽϽ��:");
		if (flag == true) {
			System.out.println("��ʤ����");
		}
		else {
			System.out.println("��ʤ����");
		}
		DNPokers[] vips = new DNPokers[1];
		for (int i = 0; i < vips.length; i++) {
			vips[i] = new DNPokers();
		}
		System.out.println("����VIP,��Ҫѡ��õ��������ͣ���������ֵ��");
		Scanner sc = new Scanner(System.in); 
		int type = sc.nextInt();
		main.DealPokers(vips, 1,type);
		System.out.println("��������"+main.GetDNType((byte)type)+"������ʾ:");
		for (int i = 0; i < vips[0].poker.length; i++) {
			System.out.println(main.GetColorType(vips[0].poker[i].poker_color)+vips[0].poker[i].poker_value + " ");
		}
	}
}
