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
import org.openmobster.core.mobileCloud.rimos.module.mobileObject.MobileObjectDatabase;
import org.openmobster.core.mobileCloud.rimos.module.sync.SyncService;
import org.openmobster.core.mobileCloud.rimos.module.sync.engine.SyncDataSource;


/**
 * 
 * @author openmobster@gmail.com
 *
 */
public abstract class AbstractMapSyncTest extends AbstractSyncTest
{
	public void setUp()
	{
		this.getTestSuite().getContext().setAttribute("service", "testServerBean.testMapping");
		this.service = (String)this.getTestSuite().getContext().getAttribute("service");
	}
	
	public void tearDown()
	{
		super.tearDown();
		this.getTestSuite().getContext().setAttribute("service", "testServerBean");
	}
	
	protected void setUp(String operation)
	{
		try
		{									
			MobileObjectDatabase.getInstance().deleteAll(this.service);
			SyncDataSource.getInstance().clearAll();				
			
			if(operation.equalsIgnoreCase("add") || 
			   operation.equalsIgnoreCase("deferMapUpdateToNextSync") ||
			   operation.equalsIgnoreCase("deferMapUpdateToNextSyncFailure")
			)
			{
				//Adding records 'unique-3' and 'unique-4' to the device and such that
				//'unique-1' and 'unique-2' exist on the server but not on device			
				for(int i=3; i<5; i++)
				{
					String uniqueId = "unique-"+String.valueOf(i);
					MobileObject cour = new MobileObject();
					cour.setRecordId(uniqueId);
					cour.setStorageId(this.service);
					cour.setValue("recordId",uniqueId);
					cour.setValue("from","@from.com");
					cour.setValue("to","@to.com");
					cour.setValue("subject","/Subject");
					cour.setValue("message","<tag apos='apos' quote=\"quote\" ampersand='&'>/Message</tag>");
					MobileObjectDatabase.getInstance().create(cour);
															
					SyncService.getInstance().updateChangeLog(service, SyncService.OPERATION_ADD, cour.getRecordId());
				}																			
			}
			else if(operation.equalsIgnoreCase("replace"))
			{		
				this.setUpServerSideSync("add");
				SyncService.getInstance().performTwoWaySync(service, service, false);
				
				//Only modifying record 'unique-2' on the device
				MobileObject cour = MobileObjectDatabase.getInstance().read(this.service, "unique-2-luid");															
				cour.setValue("message","<tag apos='apos' quote=\"quote\" ampersand='&'>unique-2/Updated/Client</tag>");
				MobileObjectDatabase.getInstance().update(cour);
														
				SyncService.getInstance().updateChangeLog(service, SyncService.OPERATION_UPDATE, cour.getRecordId());
			}
			else if(operation.equalsIgnoreCase("conflict"))
			{		
				this.setUpServerSideSync("add");
				SyncService.getInstance().performTwoWaySync(service, service, false);
				
				//Only modifying record 'unique-1' on the device creating a conflict, since its also modified on the server				
				MobileObject cour = MobileObjectDatabase.getInstance().read(this.service, "unique-1-luid");															
				cour.setValue("message","<tag apos='apos' quote=\"quote\" ampersand='&'>unique-1/Updated/Client</tag>");
				MobileObjectDatabase.getInstance().update(cour);
																	
				SyncService.getInstance().updateChangeLog(service, SyncService.OPERATION_UPDATE, cour.getRecordId());
			}
			else if(operation.equalsIgnoreCase("delete"))
			{
				this.setUpServerSideSync("add");
				SyncService.getInstance().performTwoWaySync(service, service, false);
				
				//Delete 'unique-2' from the device
				MobileObject cour = MobileObjectDatabase.getInstance().read(this.service, "unique-2-luid");
				MobileObjectDatabase.getInstance().delete(cour);
									
				SyncService.getInstance().updateChangeLog(service, SyncService.OPERATION_DELETE, cour.getRecordId());
			}			
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.toString());
		}
		finally
		{
			//reset the server side sync adapter
			this.setUpServerSideSync(operation);
		}
	}
		
	protected void setUpServerSideSync(String operation)
	{
		this.resetServerAdapter("setUp="+this.getClass().getName()+"/"+this.service+"/"+operation+"\n");
	}	
}
