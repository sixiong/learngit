
public class DNPokers {

	public DNPoker poker[] =  new DNPoker[5];
	byte dn_type;
	byte dn_player_select_type;

	DNPoker max_poker;
	int niu_poker_index[] = new int[5];

	public DNPokers() {
		for (int i=0; i<5; i++) {
//			poker[i] = new DNPoker();
//			poker[i].Reset();
			niu_poker_index[i] = -1;
		}
		max_poker = null;
		dn_type = DN_TYPE.MEI_NIU;
		dn_type = 1;
		dn_player_select_type = DN_TYPE.MEI_NIU;
	}

	boolean HasPoker()
	{
		return !(poker[0].IsNullPoker() || poker[1].IsNullPoker() || poker[2].IsNullPoker()|| poker[3].IsNullPoker()|| poker[4].IsNullPoker());
	}

	void SetPoker( DNPoker newPokers[] ) 
	{
		for(int i = 0;i < 5;i++)
			poker[i] = newPokers[i];
	}
	void SetMaxPoker(DNPoker p) {
		max_poker = p;
	}
	void SetNiuPokerIndex(int niuPokerIndex[]) {
		for(int i = 0;i < 5;i++)
			niu_poker_index[i] = niuPokerIndex[i];
	}
	boolean IsTypeIdentified() {
		return max_poker != null;
	}
	public byte getTypeofNiuNum()
	{
		boolean ifHasNiu = false;
		boolean ifZhaDan = false;
		boolean ifWuXiao = false;
		boolean ifWuHua = false;
		
		int temp1 = 0,temp2 = 0,temp3 = 0;
		for(int i = 0;i < 3;i++)
			for(int j = i+1;j < 4;j++)
				for(int k = j+1;k < 5;k++)
					if((poker[i].poker_value+poker[j].poker_value+poker[k].poker_value)%10==0)
					{
						ifHasNiu = true;
						temp1 = i;
						temp2 = j;
						temp3 = k;
					}
		if(ifHasNiu==false)
			dn_type = 0;
		else {
			int a = 0,b = 0,num = 0;
			for(int index = 0;index < 5;index++)
			{
				if(index!=temp1&&index!=temp2&&index!=temp3&&num==0)
				{
					a = index;
					num++;
				}
				else if(index!=temp1&&index!=temp2&&index!=temp3&&num==1)
				{
					b = index;
				}
			}
			dn_type = (byte) ((byte)(poker[a].poker_value + poker[b].poker_value)%10);
		}
		
		if(isEqualFour(poker[0].poker_value, poker[1].poker_value, poker[2].poker_value, poker[3].poker_value)==true)
			ifZhaDan = true;
		else if(isEqualFour(poker[0].poker_value, poker[1].poker_value, poker[2].poker_value, poker[4].poker_value)==true)
			ifZhaDan = true;
		else if(isEqualFour(poker[0].poker_value, poker[1].poker_value, poker[3].poker_value, poker[4].poker_value)==true)
			ifZhaDan = true;
		else if(isEqualFour(poker[0].poker_value, poker[2].poker_value, poker[3].poker_value, poker[4].poker_value)==true)
			ifZhaDan = true;
		else if(isEqualFour(poker[1].poker_value, poker[2].poker_value, poker[3].poker_value, poker[4].poker_value)==true)
			ifZhaDan = true;
		else 
			ifZhaDan = false;
		
		boolean flagTemp = false;
		for(int i = 0;i < 5;i++)
			if(poker[i].getRealValue()!=10)
			{
				flagTemp = true;
				break;
			}
		if(flagTemp==false)
			ifWuHua = true;

		int sum = 0;
		for (int i = 0; i < 5; i++) {
			if(poker[i].poker_value > 10)
				sum+=10;
			else {
				sum+=poker[i].poker_value;
			}
			
		}
		if(sum<=10)
			ifWuXiao = true;
		
		if(ifZhaDan==true)
			dn_type = 11;
		if(ifWuHua==true)
			dn_type = 12;
		if(ifWuXiao==true)
			dn_type = 13;
		System.out.println(dn_type);
		return dn_type;
	}
	boolean isEqualFour(byte a,byte b,byte c,byte d)
	{
		if(a==b&&a==c&&c==d)
			return true;
		else
			return false;
	}
}
