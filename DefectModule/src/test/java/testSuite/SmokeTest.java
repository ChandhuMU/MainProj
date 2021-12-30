package testSuite;

import org.testng.annotations.Test;

import testClass.MainspringLogin;

public class SmokeTest {
	
	@Test
	public void smokeTesting() throws Exception{
		MainspringLogin mainspringLogin = new MainspringLogin();
		
		mainspringLogin.openURL();
		mainspringLogin.openProject();
	}

}
