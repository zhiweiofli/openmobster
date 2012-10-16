/**
 * Copyright (c) {2003,2011} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openmobster.perf.framework;

import java.util.List;
import org.apache.log4j.Logger;

import junit.framework.TestCase;

import org.openmobster.cloudConnector.api.service.MobileService;
import org.openmobster.cloudConnector.api.service.Request;
import org.openmobster.cloudConnector.api.service.Response;
import org.openmobster.device.agent.frameworks.mobileObject.MobileObject;

public class PerfBandwidth extends TestCase
{
	private static Logger log = Logger.getLogger(PerfBandwidth.class);
	
	private SimulatedDeviceStack deviceStack;
	
	public void setUp() throws Exception
	{
		log.info("Starting PerfBandwidth....................................................");
		this.deviceStack = PerfSuite.getDevice();
		
		if(!this.deviceStack.isActivated())
		{
			this.deviceStack.getRunner().activateDevice();
			this.deviceStack.setActivated(true);
		}
		
		//this.deviceStack.startPushSocket();
	}
	
	public void tearDown() throws Exception
	{
	}
	
	public void testBandwidth() throws Exception
	{
		DeviceStackRunner runner = null;
		
		this.assertNotNull("Device Not Found for this test case", this.deviceStack);
		
		Request request = new Request("/perf-framework/bandwidthrunner");
		StringBuilder buffer = new StringBuilder();
		for(int i=0; i<1024; i++)
		{
			buffer.append("a");
		}
		request.setAttribute("payload", buffer.toString());
		
		MobileService service = new MobileService();
		Response response = service.invoke(request);
		
		this.assertNotNull(response);
		
		String statusCode = response.getStatusCode();
		this.assertEquals("200", statusCode);
	}
}
