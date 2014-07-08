import java.util.Random;

public class Main {
	
	public static final int MAX_DNPOKER_COUNT = 52;	// ȥ����С�� Joker
	public static final byte MIN_DNPOKER_VALUE = 1;		//A
	public static final byte MAX_DNPOKER_VALUE = 13;   //K
	public static final byte DNPOKER_COLOR_COUNT = 4;
	public static DNPoker pokers[] = new DNPoker[MAX_DNPOKER_COUNT];
	public byte deal_position_ = 0;
	public static int playerNumber = 2;

	public  void initPokers(byte minPoker,byte maxPoker)//��ʼ����
	{
		assert(minPoker <= maxPoker);

		int poker_count_color = maxPoker - minPoker + 1;
		int total_poker_count = DNPOKER_COLOR_COUNT * poker_count_color;

		for (int i=0; i<total_poker_count; ++i)
		{
			byte byValue = (byte) ((i % poker_count_color) + minPoker);
			byte byColor = (byte) ((i / poker_count_color) + 1);
			pokers[i] = new DNPoker(byValue, byColor);
		}
	}
	public  boolean shuffle(short iTimes)//���ϴ��
	{
		

		for (int iT=0; iT<iTimes; ++iT)
		{
			int iValue = 0;
			DNPoker median;

			for(int index = 0; index < 
					MAX_DNPOKER_COUNT; index ++)
			{
				Random random = new Random();
				
				iValue = index + random.nextInt(MAX_DNPOKER_COUNT - index);
				//iValue = index + random() % (MAX_DNPOKER_COUNT - index);
				median = pokers[index];
				pokers[index] = pokers[iValue];
				pokers[iValue] = median;
			}
		}
		deal_position_ = 0;
		return true;
	}
	public  boolean DNDeal(DNPoker poker[])//�������
	{
		if (deal_position_ < 0 || deal_position_ > MAX_DNPOKER_COUNT-5)
		{
			return false;		
		}

		poker[0] = pokers[deal_position_++];
		poker[1] = pokers[deal_position_++];
		poker[2] = pokers[deal_position_++];
		poker[3] = pokers[deal_position_++];
		poker[4] = pokers[deal_position_++];
		
		return true;
	}
	public  DNPoker FindMaxPoker(DNPoker []poker)//Ѱ��5������������һ����
	{
		DNPoker max_poker = new DNPoker(poker[0].poker_value,poker[0].poker_value);
		DNPoker current_poker ;

		for (int i = 1; i < 5; i++)
		{
			current_poker = new DNPoker(poker[i].poker_value,poker[i].poker_color);
			if ((max_poker.poker_value < current_poker.poker_value) ||
				(max_poker.poker_value == current_poker.poker_value && max_poker.poker_color < poker[i].poker_color)
				)
			{
				max_poker = poker[i];
			}
		}
		return max_poker;
	}
	public boolean CompareSinglePoker(DNPoker poker_a, DNPoker poker_b)//�Ƚϵ����ƵĴ�С
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
	int CompareNiutype(byte a_niu_type, byte b_niu_type)//����ţ����Ƚ�
	{
		//�Ƚ�ţ��
		if (a_niu_type < b_niu_type)
		{
			//ׯ��,��ʤ
			return -1;
		}
		else if (a_niu_type == b_niu_type)
		{
			return 0;
		}
		else 
		{
			return 1;
		}
	}
	
	boolean ComparePokers(DNPokers poker_a, DNPokers poker_b) {//�ж�poker_a�Ƿ��poker_bС
//		if (poker_a.IsTypeIdentified() == false) {
//			//FindCow(poker_a, null, null);
//		}
//		if (poker_b.IsTypeIdentified() == false) {
//			//FindCow(poker_b, null, null);
//		}
		boolean a_lose = false;

		//�Ƚ�ţ�ͣ����ţ����ͬ���Ƚ��������ƴ�С��ֱ����ɫ
		int ret = CompareNiutype(poker_a.dn_type, poker_b.dn_type);
		if (ret == 0)
		{
			//��5�������ҵ�������,�ж�.��С���ұ�
			if (!CompareSinglePoker(poker_a.max_poker, poker_b.max_poker))
			{
				a_lose = true;
			}
		}
		else if (ret == -1)
		{
			a_lose = true;
		}

		return a_lose;
	}

	

	int DNPokerCompare(DNPoker pokerA,DNPoker pokerB)//�Ƚ������ƵĴ�С
	{
		if (pokerA.poker_value == pokerB.poker_value)
		{
			if (pokerA.poker_color == pokerB.poker_color)//����
			{
				return 0;
			}
			else if (pokerA.poker_color > pokerB.poker_color)
			{
				return 1;
			}
			else
			{
				return -1;
			}
		}
		else if (pokerA.poker_value > pokerB.poker_value)
		{
			return 1;
		}
		else if (pokerA.poker_value < pokerB.poker_value)
		{
			return -1;
		}
		else {
			return 0;
		}	
	}
	 boolean DealPokers(DNPokers pokersFenPei[],int playerNumber)//������Ҳ�Ϊÿ����Ҷ�����5����,�������������
	{
		for (int i=0; i<playerNumber; i++) 
		{
			DNDeal(pokersFenPei[i].poker);
			pokersFenPei[i].SetMaxPoker(FindMaxPoker(pokersFenPei[i].poker));
			//FindMaxPoker(pokersFenPei[i].poker);
		}
		return true;
	}
	String GetDNTypeToString(byte tType)
	{
		String type = "mei_niu";
		switch(tType)
		{
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
	
	String GetDNType(byte tType)
	{
		String type = "ûţ";
		switch(tType)
		{
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

	public static void main(String args[])
	{
		Main main = new Main();
		main.initPokers((byte)1,(byte)13);
		main.shuffle((short)360);
		DNPokers []examples = new DNPokers[playerNumber];
		for (int i = 0; i < examples.length; i++) {
			examples[i] = new DNPokers();
			for (int j = 0; j < 5; j++) {
				examples[i].poker[j] = new DNPoker(); 
			}
		}
		main.DealPokers(examples, playerNumber);
		for (int i = 0; i < examples.length; i++) {
			for (int j = 0; j < examples[0].poker.length; j++) {
				System.out.println(examples[i].poker[j].poker_value+" "+examples[i].poker[j].poker_color);
			}
			System.out.print('\n');
		}
		System.out.println(examples[0].getTypeofNiuNum());
		System.out.println(examples[1].getTypeofNiuNum());
		System.out.println(main.ComparePokers(examples[0], examples[1]));
		
//		Main main = new Main();
//		main.initPokers((byte)1,(byte)13);
//		main.shuffle((short)360);
//		DNPokers example = new DNPokers();
//		for (int i = 0; i < 5; i++) {
//			example.poker[i] = new DNPoker((byte)i,(byte)1);
//		}
//		example.poker[0].poker_value = 1;
//		example.poker[1].poker_value = 1;
//		example.poker[2].poker_value = 1;
//		example.poker[3].poker_value = 1;
//		example.poker[4].poker_value = 2;
//		System.out.println(example.getTypeofNiuNum());
	}
}
