public class mainCalc {

	
	public String multiplyNum(Double x, Double y)
	{
		String temp = "";
		Double total = x * y;
		
		temp = Double.toString(total);
		return temp; 
	}
	
	public String divisionNum(Double x, Double y)
	{
		String temp = "";
		Double total = x / y;
		
		temp = Double.toString(total);
		return temp; 
	}
	
	public String additionNum(Double x, Double y)
	{
		String temp = "";
		Double total = x + y;
		
		temp = Double.toString(total);
		return temp; 
	}
	
	public String subtractNum(Double x, Double y)
	{
		String temp = "";
		Double total = x - y;
		
		temp = Double.toString(total);
		return temp; 
	}
}
