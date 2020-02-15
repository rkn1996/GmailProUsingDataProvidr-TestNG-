package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Pages.ComposePage;
import Pages.HomePage;
import Pages.LoginPage;
import Utility.UtilityPage;

public class LoginTestWithDataProvider 
{
	@DataProvider(name="logintestdata")
	public Object[][] datamethod()
	{
		Object[][] data= {
				         {"chrome","https://www.gmail.com","kumarnayakroshan305","valid","xxxxxxxxxx","valid"},
				         {"chrome","https://www.gmail.com","kumarnayakroshan305","valid","rknroshan@123#1","invalid"},
				         {"chrome","https://www.gmail.com","kumarnayakroshan305","valid","","blank"},
				         {"chrome","https://www.gmail.com","kumarnayakroshan@234d","invalid","N/A","N/A"},
				         {"chrome","https://www.gmail.com","","blank","N/A","N/A"},
		                 };
		return(data);		
	}
	
	@Test(dataProvider="logintestdata")
	public void runnermethod(String bn,String url,String u,String uc,String p,String pc) throws Exception
	{
		//DDT in TDD using TestNG(DataProvider)
        //Create object to "TestUtility" Class
	    UtilityPage tu=new UtilityPage();		
	    //open browser
	    WebDriver driver=tu.openBrowser(bn);
	    //create object to page classes
	    HomePage hp=new HomePage(driver);
		LoginPage lp=new LoginPage(driver);
		ComposePage cp=new ComposePage(driver);	
		//Launch site
		tu.launchsite(driver,"https://www.gmail.com");
		WebDriverWait w=new WebDriverWait(driver,20);
		w.until(ExpectedConditions.visibilityOf(hp.uid));
		try
		{
		   //Enter userid and click next
		   hp.uidfill(u);
		   w.until(ExpectedConditions.elementToBeClickable(hp.uidnext));
		   hp.uidnextclick();
		   Thread.sleep(5000);
		   if(uc.equalsIgnoreCase("blank") && hp.blankuiderr.isDisplayed())
			{
			    Reporter.log("Login test passed for blank userid");
		    }
			else if(uc.equalsIgnoreCase("invalid") && hp.invaliduiderr.isDisplayed())
			{
				Reporter.log("Login test passed for invalid userid");
			}
			else if(uc.equalsIgnoreCase("valid") && lp.pwd.isDisplayed())
			{
				//Enter Password and perform password validations
				lp.pwdfill(p);
				w.until(ExpectedConditions.elementToBeClickable(lp.pwdnext));
				lp.pwdnextclick();
				Thread.sleep(5000);
				if(pc.equalsIgnoreCase("blank") && lp.blankpwderr.isDisplayed())
				{
					Reporter.log("Login test passed for blank password");
				}
				else if(pc.equalsIgnoreCase("invalid") && lp.invalidpwderr.isDisplayed())
				{
					Reporter.log("Login test passed for invalid password");
				}
				else if(pc.equalsIgnoreCase("valid") && cp.comp.isDisplayed())
				{
					Reporter.log("Login test passed for valid userid and password");
					//do logout
					cp.profilepicclick();
					w.until(ExpectedConditions.elementToBeClickable(cp.signout));
					cp.signoutclick();
				}
				else
				{
					String f1=tu.pageScreenshot(driver);
					Reporter.log("pwd test failed,see "+f1);
					Reporter.log("<a href=\""+f1+"\"><img src=\""+f1+"\" height=\"100\" width=\"100\"/> </a>");
				}
			}
			else
			{
				String f2=tu.pageScreenshot(driver);
				Reporter.log("userid test failed,see "+f2);
				Reporter.log("<a href=\""+f2+"\"><img src=\""+f2+"\" height=\"100\" width=\"100\"/> </a>");
			}
			//close site
			tu.closesite(driver);     
		}
		catch(Exception ex) 
		{
			String f3=tu.pageScreenshot(driver);
			Reporter.log("problem occured,see"+f3+" "+ex.getMessage());
			Reporter.log("<a href=\""+f3+"\"><img src=\""+f3+"\" height=\"100\" width=\"100\"/> </a>");
		}			
	}
}
