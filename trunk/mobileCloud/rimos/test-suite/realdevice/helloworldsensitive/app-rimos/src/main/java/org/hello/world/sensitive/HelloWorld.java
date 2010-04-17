package org.hello.world.sensitive;
/**
 * 
 */


import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.ui.UiApplication;

import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.container.MainScreen;

/**
 * @author openmobster@gmail
 *
 */
public class HelloWorld extends UiApplication
{
	public static void main(String[] args)
	{
		UiApplication app = new HelloWorld();
		app.enterEventDispatcher();
	}
	
	public void activate()
	{
		//System.out.println("------------------------------------------");
		//System.out.println("Activate called(hot deploy)..........................."+this.getClass().getName());
		//System.out.println("------------------------------------------");
		this.pushScreen(new HelloWorldScreen());
	}
	
	//create a new screen that extends MainScreen, which provides
	//default standard behavior for BlackBerry applications
	final class HelloWorldScreen extends MainScreen
	{
	        public HelloWorldScreen()
	        {

	                //invoke the MainScreen constructor
	                super();

	                //add a title to the screen
	                LabelField title = new LabelField("HelloWorld Sample", LabelField.ELLIPSIS
	                                | LabelField.USE_ALL_WIDTH);
	                setTitle(title);
	                
	                //String blah = "blah";
	                //System.out.println("------------------------------------------");
	                //System.out.println("Blah: "+blah);
	                //System.out.println("Blah2: "+blah);
	                //System.out.println("------------------------------------------");

	                //add the text "Hello World!" to the screen
	                add(new RichTextField("Hello Blackberry"));
	        }

	        //override the onClose() method to display a dialog box to the user
	        //with "Goodbye!" when the application is closed
	        public boolean onClose()
	        {
	        	long localUid = (long)this.getClass().getName().hashCode();
	        	PersistentObject localDatabase = PersistentStore.getPersistentObject(localUid);
	    		Object data = localDatabase.getContents();
	    		String status = "null";
	    		if(data != null)
	    		{
	    			status = "not-null";
	    		}
	    		
	            Dialog.alert("Good Bye!! ("+status+")");
	            System.exit(0);
	            return true;
	        }
	}
}
