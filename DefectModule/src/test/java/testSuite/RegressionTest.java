package testSuite;

import org.testng.annotations.Test;

import testClass.Defect;
import testClass.MainspringLogin;

public class RegressionTest {

	@Test
	public void regressionTesting() throws Exception{
		
		MainspringLogin mainspringLogin = new MainspringLogin();
		Defect defect = new Defect();
		
		mainspringLogin.createDefect();
	
		defect.selectCreatedDefect();
		defect.createWorkflow();
		defect.createComments();
		defect.createAttachments();
		defect.previousVersion();
		defect.activityLog();
	}
}
