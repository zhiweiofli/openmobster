/******************************************************************************
 * OpenMobster                                                                *
 * Copyright 2008, OpenMobster, and individual                                *
 * contributors as indicated by the @authors tag. See the                     *
 * copyright.txt in the distribution for a full listing of                    *
 * individual contributors.                                                   *
 *                                                                            *
 * This is free software; you can redistribute it and/or modify it            *
 * under the terms of the GNU Lesser General Public License as                *
 * published by the Free Software Foundation; either version 2.1 of           *
 * the License, or (at your option) any later version.                        *
 *                                                                            *
 * This software is distributed in the hope that it will be useful,           *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU           *
 * Lesser General Public License for more details.                            *
 *                                                                            *
 * You should have received a copy of the GNU Lesser General Public           *
 * License along with this software; if not, write to the Free                *
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA         *
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.                   *
 ******************************************************************************/
package org.openmobster.core.mobileCloud.test.sync;

import org.openmobster.core.mobileCloud.rimos.module.mobileObject.MobileObject;
import org.openmobster.core.mobileCloud.rimos.module.sync.*;


/**
 * 
 * @author openmobster@gmail.com
 *
 */
public final class TestOneWayServerSync extends AbstractSyncTest 
{		
	/**
	 * 
	 */
	public void runTest()
	{
		try
		{
			//Add test case
			this.setUp("add");
			SyncService.getInstance().performOneWayServerSync(service, service, false);
			this.assertRecordPresence("unique-1", "/TestOneWayServerSync/add");
			this.assertRecordPresence("unique-2", "/TestOneWayServerSync/add");
			this.assertRecordPresence("unique-3", "/TestOneWayServerSync/add");
			this.assertRecordPresence("unique-4", "/TestOneWayServerSync/add");
			this.tearDown();		
			
			//Replace test case
			this.setUp("replace");
			SyncService.getInstance().performOneWayServerSync(service, service, false);
			MobileObject afterUnique1 = this.getRecord("unique-1");
			MobileObject afterUnique2 = this.getRecord("unique-2");
			this.assertRecordPresence("unique-1", "/TestOneWayServerSync/replace");
			this.assertRecordPresence("unique-2", "/TestOneWayServerSync/replace");
			this.assertEquals(afterUnique1.getValue("message"), "<tag apos='apos' quote=\"quote\" ampersand='&'>unique-1/Updated/Server</tag>", 
			"/TestOneWayServerSync/replace/updated/unique-1");
			this.assertEquals(afterUnique2.getValue("message"), "<tag apos='apos' quote=\"quote\" ampersand='&'>unique-2/Updated/Client</tag>", 
			"/TestOneWayServerSync/replace/updated/unique-2");
			this.tearDown();
			
			//Delete test case
			this.setUp("delete");
			SyncService.getInstance().performOneWayServerSync(service, service, false);
			this.assertRecordAbsence("unique-1", "/TestOneWayServerSync/delete");
			this.assertRecordAbsence("unique-2", "/TestOneWayServerSync/delete");
			this.tearDown();
			
			//Conflict test case
			this.setUp("conflict");
			SyncService.getInstance().performOneWayServerSync(service, service, false);
			afterUnique1 = this.getRecord("unique-1");
			afterUnique2 = this.getRecord("unique-2");
			this.assertRecordPresence("unique-1", "/TestOneWayServerSync/conflict");
			this.assertRecordPresence("unique-2", "/TestOneWayServerSync/conflict");
			this.assertEquals(afterUnique1.getValue("message"), "<tag apos='apos' quote=\"quote\" ampersand='&'>unique-1/Updated/Server</tag>", 
			"/TestOneWayServerSync/conflict/updated/unique-1");
			this.assertEquals(afterUnique2.getValue("message"), "<tag apos='apos' quote=\"quote\" ampersand='&'>unique-2/Message</tag>", 
			"/TestOneWayServerSync/conflict/updated/unique-2");
			this.tearDown();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.toString());
		}
	}	
}
