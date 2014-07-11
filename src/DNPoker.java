
public class DNPoker {
	public byte poker_value = 0;
	public byte poker_color = 0;
	public byte real_value = 0;

	public DNPoker(byte i, byte j) {
		if (i>13||i<1||j<1||j>4) {
			Reset();
		}
		poker_color = j;
		poker_value = i;
	}
	public DNPoker() {
		poker_color = 0;
		poker_value = 0;
	}
	public void Reset()
	{
		poker_value = 0;
		poker_color = 0;
	}
	public byte getRealValue()
	{
		if (poker_value>10)
		{
			this.real_value = 10;
		}
		else {
			
			real_value = poker_value;
		}
		return real_value;
	}
}
