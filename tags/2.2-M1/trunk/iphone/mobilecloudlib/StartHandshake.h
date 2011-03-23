/**
 * Copyright (c) {2003,2010} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

#import <Foundation/Foundation.h>
#import "ActionHandler.h"
#import "StringUtil.h"
#import "SyncXMLGenerator.h"
#import "Configuration.h"
#import "SyncEngine.h"
#import "Session.h"
#import "SyncAdapterRequest.h"
#import "WorkflowManager.h"

/**
 * 
 * @author openmobster@gmail.com
 */
@interface StartHandshake : NSObject<ActionHandler> 
{

}

+(id) withInit;

@end
