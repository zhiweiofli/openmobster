/**
 * Copyright (c) {2003,2010} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openmobster.core.agent.provisioning.status;

import org.openmobster.server.api.model.MobileBean;
import org.openmobster.server.api.model.MobileBeanId;

/**
 * @author openmobster@gmail.com
 */
public class SyncStatusBean implements MobileBean 
{
	private static final long serialVersionUID = -7408157108973777404L;

	@MobileBeanId
	private String beanId;
	
	private String value;
	
	public SyncStatusBean()
	{
		
	}

	public String getBeanId()
	{
		return beanId;
	}

	public void setBeanId(String beanId)
	{
		this.beanId = beanId;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
