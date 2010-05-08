/**
 * 
 */
package org.openmobster.core.mobileCloud.android.module.bus;

import java.util.Map;
import java.util.Set;

import org.openmobster.core.mobileCloud.android.testsuite.Test;

import org.openmobster.core.mobileCloud.android.module.bus.Bus;
import org.openmobster.core.mobileCloud.android.module.bus.InvocationResponse;
import org.openmobster.core.mobileCloud.android.module.bus.MobilePushInvocation;
import org.openmobster.core.mobileCloud.android.module.bus.MobilePushMetaData;

/**
 * @author openmobster
 *
 */
public class TestBusBroadcast extends Test
{	
	public void runTest()
	{
		try
		{
			BusRegistration remoteBus = new BusRegistration("org.openmobster.core.mobileCloud.android.remote.bus");
			remoteBus.addInvocationHandler("org.openmobster.core.mobileCloud.android.remote.bus.MockInvocationHandler");
			remoteBus.addInvocationHandler(MockBroadcastInvocationHandler.class.getName());
			remoteBus.save();
			
			Bus bus = Bus.getInstance();
			bus.register(new MockBroadcastInvocationHandler());
			
			
			MobilePushMetaData metadata = new MobilePushMetaData("emailChannel", "uid:blah@blah.com");
			metadata.setAdded(true);
			
			MobilePushInvocation invocation = new MobilePushInvocation(
			MockBroadcastInvocationHandler.class.getName());
			invocation.addMobilePushMetaData(metadata);
			
			bus.broadcast(invocation);
		}
		catch(Exception e)
		{
			e.printStackTrace(System.out);
			throw new RuntimeException(e);
		}
	}
}
