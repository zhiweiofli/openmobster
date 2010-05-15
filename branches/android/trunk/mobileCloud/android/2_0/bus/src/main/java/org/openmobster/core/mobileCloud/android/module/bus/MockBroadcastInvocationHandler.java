/**
 * Copyright (c) {2003,2010} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openmobster.core.mobileCloud.android.module.bus;

import java.util.Map;
import java.util.Set;

import org.openmobster.core.mobileCloud.android.service.Service;
import org.openmobster.core.mobileCloud.android.errors.SystemException;
import org.openmobster.core.mobileCloud.android.module.bus.Invocation;
import org.openmobster.core.mobileCloud.android.module.bus.InvocationHandler;
import org.openmobster.core.mobileCloud.android.module.bus.InvocationResponse;

/**
 * @author openmobster@gmail.com
 *
 */
public class MockBroadcastInvocationHandler extends Service implements InvocationHandler
{
	@Override
	public void start()
	{
		try
		{
			Bus.getInstance().register(this);
		}
		catch(Exception e)
		{
			throw new SystemException(this.getClass().getName(), "start", new Object[]{e.getMessage()});
		}
	}

	@Override
	public void stop()
	{
	}
	
	public String getUri()
	{
		return this.getClass().getName();
	}

	public InvocationResponse handleInvocation(Invocation invocation)
	{
		InvocationResponse response = new InvocationResponse();
		
		Map<String,Object> input = invocation.getShared();
		Set<String> keys = input.keySet();
		for(String key:keys)
		{
			Object value = input.get(key);			
			response.setValue(key, "broadcast://"+value.toString());
		}
		
		//Indicate broadcast invoked..
		Map<String,String> shared = response.getShared();
		keys = shared.keySet();
		System.out.println("---------------------------------");
		for(String key:keys)
		{
			System.out.println(key+": "+shared.get(key));
		}
		System.out.println("---------------------------------");
		
		return response;
	}
}
