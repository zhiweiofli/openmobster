/**
 * Copyright (c) {2003,2009} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openmobster.core.moblet.deployment;

import java.util.List;
import java.util.ArrayList;
import java.net.URL;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.openmobster.core.common.ServiceManager;
import org.openmobster.core.common.XMLUtilities;
import org.openmobster.core.moblet.MobletApp;
import org.openmobster.core.moblet.registry.Registry;


/**
 * @author openmobster@gmail.com
 */
public class MobletDeployer 
{	
	public static final String uri = "moblet-management://deployer";
	
	private Registry registry;
			
	public MobletDeployer()
	{
		
	}
	
	public static MobletDeployer getInstance()
	{
		return (MobletDeployer)ServiceManager.locate(uri);
	}
				
	public Registry getRegistry() 
	{
		return registry;
	}

	public void setRegistry(Registry registry) 
	{
		this.registry = registry;
	}

	public void deploy(URL url) throws Throwable
	{		
		InputStream is = url.openStream();
		
		List<MobletApp> apps = this.parseMobletApps(is);
		
		//Make them available for the provisioning system
		this.registry.register(apps);
	}
	//--------------------------------------------------------------------------------------------------
	private List<MobletApp> parseMobletApps(InputStream is) throws Exception
	{
		List<MobletApp> apps = new ArrayList<MobletApp>();
		
		Document root = XMLUtilities.parse(is);
		
		NodeList mobletAppNodes = root.getElementsByTagName("moblet-app");
		if(mobletAppNodes != null && mobletAppNodes.getLength()>0)
		{
			int size = mobletAppNodes.getLength();
			for(int i=0; i<size; i++)
			{
				Element mobletAppElem = (Element)mobletAppNodes.item(i);
				MobletApp app = this.parseMobletApp(mobletAppElem);
				apps.add(app);
			}
		}
		
		return apps;
	}
	
	private MobletApp parseMobletApp(Element mobletAppElem) throws Exception
	{
		MobletApp app = new MobletApp();
						
		Element name = (Element)mobletAppElem.getElementsByTagName("name").item(0);
		app.setName(name.getFirstChild().getTextContent());
		
		Element description = (Element)mobletAppElem.getElementsByTagName("description").item(0);
		app.setDescription(description.getFirstChild().getTextContent());
		
		Element binLocation = (Element)mobletAppElem.getElementsByTagName("bin-loc").item(0);
		String binaryLocation = binLocation.getFirstChild().getTextContent();
		if(!binaryLocation.startsWith("/"))
		{
			binaryLocation = "/"+binaryLocation;
		}
		app.setBinaryLocation(binaryLocation);
		
		Element configLocation = (Element)mobletAppElem.getElementsByTagName("config-loc").item(0);
		String confLocation = configLocation.getFirstChild().getTextContent();
		if(!confLocation.startsWith("/"))
		{
			confLocation = "/"+confLocation;
		}
		app.setConfigLocation(confLocation);
		
		//Get the unique uri of the moblet-app in the system
		String uri = app.getBinaryLocation();
		int startIndex = uri.lastIndexOf('/');
		if(startIndex != -1)
		{
			uri = uri.substring(startIndex+1);
		}
		
		int endIndex = -1;
		if((endIndex=uri.indexOf('.')) != -1)
		{
			uri = uri.substring(0, endIndex);
		}
		
		app.setUri(uri);
						
		return app;
	}
}
