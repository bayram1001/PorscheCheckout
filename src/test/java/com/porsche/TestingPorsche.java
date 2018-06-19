package com.porsche;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestingPorsche {
	
public static Integer isDigit(String letter) {
		
		String num = "";
	
		for (int a = 0; a < letter.length(); a++) {
			if (letter.charAt(a) == '.') {
				break;
			}
			if (Character.isDigit(letter.charAt(a))) {

				num += letter.charAt(a);
			}
		}

		return Integer.parseInt(num);
	}



   public static void main(String[] args) throws InterruptedException {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// 2.Go to url “https://www.porsche.com/usa/modelstart/”
		String url = "https://www.porsche.com/usa/modelstart/";
		driver.get(url);
		// getting parent window session id.
		String parent = driver.getWindowHandle(); // this one takes one window session
		System.out.println("Parent window : " + parent);

		// 3.Click model 718
		driver.findElement(By.xpath("/html/body/div[2]/div[4]/div/div[2]/a[1]/div/div[2]/div/span")).click();
		// 4.Remember the price of 718Cayman. // 5.Click on Build & Price under 718Cayman
		driver.findElement(By.xpath("//*[@id=\"m982120\"]/div[2]/div/a/span")).click(); // 
        // we are assigning all the opened windows by method getWindowhandles() to the set.
		Set<String> allWindows = driver.getWindowHandles();// this one collects all open windows sessions
        // it shows the size of how many window is opening
		int countWindow = allWindows.size();
		System.out.println("Total count " + countWindow);
		//we are iterating each window session ids
		for (String child : allWindows) {
			// if parent window is not equal to child window 		
			if (!parent.equalsIgnoreCase(child)) {
				//then switch to child window
				driver.switchTo().window(child);
				//print the child window
				System.out.println("Child window "+child);

			}
		}

		// 5.The price of second window under 718Cayman
		String count = driver.findElement(By.cssSelector("#s_price > div.ccaTable > div:nth-child(1) > div.ccaPrice")).getText();

		int countResult1 = isDigit(count);
		

		System.out.println("countResult "+countResult1);
		// 6.Verify that Base price displayed on the page is same as the price from step
		String BasePrice = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[1]/div[2]")).getText();// 56000 

		// 7.Verify that Price for Equipment is 0
		String expectedEquipmentPrice = "$0";
		String actualEquipmentPrice = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();// 0
		int actualEquipmentPriceNum = isDigit(actualEquipmentPrice);
		
		if (actualEquipmentPrice.equals(expectedEquipmentPrice)) {
			System.out.println("Equipment price verification passed");
		} else {
			System.out.println("Equipment price verification failed");
		}

		int BasePriceNum =isDigit(BasePrice);
		
		System.out.println("BasePriceNum "+BasePriceNum);
		if (countResult1 == BasePriceNum) {
			System.out.println("Pass");
		} else {
			System.out.println("Fail");

		}
		//8.Verify that total price is the sum of base price + Delivery, Processing and Handling Fee
		String deliveryFee= driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[3]/div[2]")).getText();// delivery, processing and handling fee 1,050
		
		String totalPrice= driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[4]/div[2]")).getText();//total price $57,950
		
		int DeliveryFeeNum =isDigit(deliveryFee);
		System.out.println("Delivery fee: "+deliveryFee);
		
		
        int TotalPriceNum =isDigit(totalPrice);
		System.out.println("Total Price:" +TotalPriceNum);
		
		int sum=DeliveryFeeNum+BasePriceNum;
		System.out.println("Fisrt Sum1: "+ sum);
		
		// task number 9
		driver.findElement(By.xpath("//*[@id=\"s_exterieur_x_FJ5\"]/span")).click();// miami blue color
		//10.Verify that Price for Equipment is Equal to Miami Blue price
		driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[4]/div[2]")).getText(); // 
		
		String actualEquipmentPrice2 = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();// $2,580
		
		int actualEquipmentPriceNum2 = isDigit(actualEquipmentPrice2);
		
		int sum2=DeliveryFeeNum+actualEquipmentPriceNum2+BasePriceNum;
		
		System.out.println("Total price for second sum2: "+sum2);
		//12.Select 20" Carrera Sport Wheels
		 driver.findElement(By.xpath("//*[@id=\"s_exterieur_x_MXRD\"]/span/span")).click();
		// 13.Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels
		 String lastEquipmentPrice = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();
		int lastEquipmentPriceNum=isDigit(lastEquipmentPrice);
		System.out.println("Task 13" +lastEquipmentPrice);
		
		int sum3=sum2+lastEquipmentPriceNum;
		//14.Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee
		String color= driver.findElement(By.xpath("//*[@id=\"s_exterieur_x_IAF\"]/div[2]/div[1]/div/div[2]")).getText();
		int colorNum=isDigit(color);
		String wheel= driver.findElement(By.xpath("//*[@id=\"s_exterieur_x_IRA\"]/div[2]/div[1]/div/div[2]")).getText();
		int wheelNum= isDigit(wheel);
		String newEquipmentPrice= driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();
		int newEquipmentPriceNum=isDigit(newEquipmentPrice);
		
		if (newEquipmentPriceNum==colorNum+wheelNum) {
			System.out.println("Pass for miami blue and wheel price");
		}else {
			System.out.println("Fail for miami blue and wheel price");
		}
		//15.Select seats ‘Power Sport Seats (14-way) with Memory Package’
		driver.findElement(By.xpath("*[@id=\"headline_73\"]/div")).sendKeys("s_interieur_x_PP06");
//		WebElement elem= driver.findElement(By.className("radiobutton checked"));
		Select select = new Select(driver.findElement(By.xpath("//*[@id=\"s_interieur_x_PP06\"]")));
		//*[@id="headline_73"]/div	
	//	loginButtonId.submit();
//		   select.click;
//	  Select select= new Select(loginButtonId);
		
//	  if(!loginButtonId.isSelected()) {
//		  loginButtonId.click();
//			
//		}
//		
//		if(loginButtonId.isSelected()) {
//			loginButtonId.click();
//			
//		}
		
		Thread.sleep(5000);
		
	
		driver.quit();
   }
}
