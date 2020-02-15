package tests;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MethodConcept 
{
	@DataProvider(name="x")
	public Object[][] testdata(Method m)
	{
		Object[][] data=null;
		if(m.getName().equalsIgnoreCase("z"))
		{
			data=new Object[][] {
				                 {"10","20"},
				                 {"90","12"}
			                    };
		}
		else
		{
			data=new Object[][] {
				{"abdul","kalam"},
				{"steve","jobs"}
			};
		}
		return (data);		
	}
	
	@Test(dataProvider="x")
	public void y(String a,String b)
	{
		String x=a+b;
		System.out.println(x);
	}
	
	@Test(dataProvider="x")
	public void Z(String a,String b)
	{
		String y=a+b;
	}

}
