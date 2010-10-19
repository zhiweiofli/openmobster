/**
 * Copyright (c) {2003,2010} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package test.openmobster.device.comet;

import java.util.Date;
import java.util.List;

import org.openmobster.core.security.device.Device;
import org.openmobster.server.api.ExecutionContext;
import org.openmobster.server.api.model.MobileBean;
import org.openmobster.server.api.model.Channel;
import org.openmobster.server.api.model.ChannelInfo;
import org.openmobster.core.common.Utilities;

/**
 * @author openmobster@gmail.com
 */
@ChannelInfo(uri="twitterChannel", 
		     mobileBeanClass="test.openmobster.device.comet.TwitterBean",
		     updateCheckInterval=3000
)
public class TwitterChannel implements Channel
{
	private int newCount = 2;
	
	//---Channel implementation-----------------------------------------------------------------------------------
	public List<? extends MobileBean> bootup() 
	{
		return null;
	}

	public String create(MobileBean mobileBean) 
	{		
		return null;
	}

	public void delete(MobileBean mobileBean) 
	{		
		
	}

	public MobileBean read(String id) 
	{		
		return null;
	}

	public List<? extends MobileBean> readAll() 
	{		
		return null;
	}

	public String[] scanForDeletions(Device device, Date lastScanTimestamp) 
	{		
		return null;
	}

	public String[] scanForNew(Device device, Date lastScanTimestamp) 
	{
		if(this.newCount != 0)
		{
			this.newCount--;
			
			return new String[]{Utilities.generateUID(),
			Utilities.generateUID(),
			Utilities.generateUID()};
		}
		return null;
	}

	public String[] scanForUpdates(Device device, Date lastScanTimestamp) 
	{		
		return null;
	}

	public void update(MobileBean mobileBean) 
	{				
	}	
}
