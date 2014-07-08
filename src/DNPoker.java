
public class DNPoker {
	public byte poker_value = 0;
	public byte poker_color = 0;
	public byte real_value = 0;

	public DNPoker(byte byValue, byte byColor) {
		poker_color = byColor;
		poker_value = byValue;
	}
	public DNPoker() {

	}
	public boolean IsNullPoker() 
	{
		return (poker_value==0||poker_color==0);
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
			real_value = 10;
		}
		return real_value;
	}
}
