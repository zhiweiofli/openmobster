/**
 * Copyright (c) {2003,2009} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openmobster.core.synchronizer.server.engine;

import org.openmobster.core.synchronizer.model.AbstractOperation;

/**
 * 
 * @author openmobster@gmail.com
 */
public interface SyncRecordSupportsLongObject extends SyncRecord
{
	/**
	 * 
	 * @return
	 */
	public void fillNextChunk(AbstractOperation operationCommand);
	
	/**
	 * 
	 * @return
	 */
	public String getData();
	
	/**
	 * 
	 *
	 */
	public void setUpForReTransmission();
}