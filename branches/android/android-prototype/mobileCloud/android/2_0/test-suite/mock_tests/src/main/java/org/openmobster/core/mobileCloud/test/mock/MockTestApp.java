package org.openmobster.core.mobileCloud.test.mock;

import java.lang.reflect.Field;

import android.app.Activity;
import android.os.Bundle;

import org.openmobster.core.mobileCloud.android.testsuite.TestSuite;
import org.openmobster.core.mobileCloud.android.testsuite.TestContext;

public class MockTestApp extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	try
    	{
	    	//android.os.Debug.waitForDebugger();	    	
	        super.onCreate(savedInstanceState);
	        
	        String layoutClass = "org.openmobster.core.mobileCloud.test.mock.R$layout";
	        String main = "main";
	        
	        Class clazz = Class.forName(layoutClass);	        
	        Field field = clazz.getField(main);
	        
	        setContentView(field.getInt(clazz));
	        
	        this.executeTestFramework();   
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace(System.out);
    	}
    }
    
    private void executeTestFramework() throws Exception
    {
    	Thread t = new Thread(new TestExecutor());
    	t.start();
    }
    
    private class TestExecutor implements Runnable
    {
    	public void run()
    	{
    		TestSuite suite = new TestSuite();
        	TestContext testContext = new TestContext();
        	suite.setContext(testContext);
        	suite.getContext().setAttribute("android:context", 
        	MockTestApp.this);
        	
        	suite.addTest(new StorageTestDrive());        	
        	suite.addTest(new BusTestDrive());
        	suite.addTest(new BusRPCTestDrive());
        	suite.addTest(new BackgroundProcessingTestDrive());
        	
        	//For network oriented test drive
        	//suite.addTest(new SocketTestDrive());
        	
        	suite.execute();
    	}
    }
}
