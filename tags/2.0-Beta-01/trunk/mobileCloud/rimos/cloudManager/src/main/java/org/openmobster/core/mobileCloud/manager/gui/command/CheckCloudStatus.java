/**
 * Copyright (c) {2003,2010} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openmobster.core.mobileCloud.manager.gui.command;

import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.i18n.MessageFormat;

import org.openmobster.core.mobileCloud.manager.gui.LocaleKeys;
import org.openmobster.core.mobileCloud.rimos.configuration.Configuration;
import org.openmobster.core.mobileCloud.rimos.errors.ErrorHandler;
import org.openmobster.core.mobileCloud.rimos.errors.SystemException;
import org.openmobster.core.mobileCloud.rimos.module.bus.Bus;
import org.openmobster.core.mobileCloud.rimos.module.bus.SyncInvocation;

import org.openmobster.core.mobileCloud.api.ui.framework.Services;
import org.openmobster.core.mobileCloud.api.ui.framework.command.CommandContext;
import org.openmobster.core.mobileCloud.api.ui.framework.command.RemoteCommand;
import org.openmobster.core.mobileCloud.api.ui.framework.command.AppException;
import org.openmobster.core.mobileCloud.api.ui.framework.resources.AppResources;

import org.openmobster.core.mobileCloud.api.service.Request;
import org.openmobster.core.mobileCloud.api.service.Response;
import org.openmobster.core.mobileCloud.api.service.MobileService;

import org.openmobster.core.mobileCloud.api.model.MobileBean;

/**
 * @author openmobster@gmail.com
 */
public class CheckCloudStatus implements RemoteCommand
{	
	public void doAction(CommandContext commandContext) 
	{
		AppException appException = null;
		
		//Test Mobile Invocation Service Status
		try
		{
			Request request = new Request("/status/mobilebeanservice");
			request.setAttribute("param1", "param1Value");
			request.setAttribute("param2", "param2Value");
			Response response = MobileService.invoke(request);	
			
			//Assert the state
			String param1Value = response.getAttribute("param1");
			String param2Value = response.getAttribute("param2");
			
			if(!param1Value.equals("response://param1Value"))
			{
				throw new RuntimeException("Service Invocation Failure!!");
			}
			
			if(!param2Value.equals("response://param2Value"))
			{
				throw new RuntimeException("Service Invocation Failure!!");
			}
		}		
		catch(Exception sie)
		{
			//System.out.println("----------------------------------------------------");
			//System.out.println("Exception: "+sie.getMessage());
			//System.out.println("Exception: "+sie.toString());
			//System.out.println("----------------------------------------------------");
			if(appException == null)
			{
				appException = new AppException();
			}
			
			ErrorHandler.getInstance().handle(new SystemException(this.getClass().getName(), "doAction", new Object[]{
				"Checking Cloud Status....",
				"Target Command:"+commandContext.getTarget()				
			}));
			appException.setAttribute("statusInvocationFailure", "true");
		}
		
		//Test Mobile Sync Service Status
		try
		{
			SyncInvocation syncInvocation = new SyncInvocation("org.openmobster.core.mobileCloud.invocation.SyncInvocationHandler", 
			SyncInvocation.bootSync, "syncstatuschannel");
			syncInvocation.deactivateBackgroundSync();
			Bus.getInstance().invokeService(syncInvocation);
			
			//Assert the state
			MobileBean[] beans = MobileBean.readAll("syncstatuschannel");
			if(beans.length != 5)
			{
				throw new RuntimeException("Sync Service Failure!!");
			}
			for(int i=0; i<beans.length;i++)
			{
				if(!beans[i].getId().equals(""+i))
				{
					throw new RuntimeException("Sync Service Failure!!");
				}
				if(!beans[i].getValue("value").equals("/status/"+i))
				{
					throw new RuntimeException("Sync Service Failure!!");
				}

			}
		}
		catch(Exception synce)
		{
			//System.out.println("----------------------------------------------------");
			//System.out.println("Exception: "+synce.getMessage());
			//System.out.println("Exception: "+synce.toString());
			//System.out.println("----------------------------------------------------");
			if(appException == null)
			{
				appException = new AppException();
			}
			
			ErrorHandler.getInstance().handle(new SystemException(this.getClass().getName(), "doAction", new Object[]{
				"Checking Cloud Status....",
				"Target Command:"+commandContext.getTarget()				
			}));
			appException.setAttribute("statusSyncFailure", "true");
		}
		
		if(appException != null)
		{
			throw appException;
		}
	}

	public void doViewBefore(CommandContext commandContext) 
	{		
	}
	
	public void doViewAfter(CommandContext commandContext) 
	{	
		Configuration configuration = Configuration.getInstance();
		AppResources resources = Services.getInstance().getResources();
		String statusOk = resources.localize(LocaleKeys.statusOk, LocaleKeys.statusOk);
		statusOk = MessageFormat.format(statusOk, new Object[]{
				configuration.getServerIp(),
				configuration.getEmail(),
				configuration.isSSLActivated()?configuration.getSecureServerPort():configuration.getPlainServerPort()
		});
		Dialog.alert(statusOk);
	}

	public void doViewError(CommandContext commandContext) 
	{
		AppResources resources = Services.getInstance().getResources();
		AppException appException = commandContext.getAppException();
		
		String message = "";
		if(appException.getAttribute("statusSyncFailure") != null)
		{
			message += resources.localize(LocaleKeys.statusSyncFailure, LocaleKeys.statusSyncFailure);
		}
		
		if(appException.getAttribute("statusInvocationFailure") != null)
		{
			message += resources.localize(LocaleKeys.statusInvocationFailure, LocaleKeys.statusInvocationFailure);
		}
		
		
		Dialog.alert(message);
	}					
}
