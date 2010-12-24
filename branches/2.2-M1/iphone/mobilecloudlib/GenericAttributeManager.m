#import "GenericAttributeManager.h"


@implementation GenericAttributeManager

-init
{
	if(self = [super init])
	{
		//Initialize the dictionary
		attributes = [NSMutableDictionary dictionary];
	}
	
	return self;
}

+(id) withInit
{
	GenericAttributeManager *attrMgr = [[GenericAttributeManager alloc] init];
	return [attrMgr autorelease];
}

-(void) setAttribute:(NSString *)name :(id)value
{
	[attributes setObject:value forKey:name];
}

-(id) getAttribute:(NSString *)name
{
	return [attributes objectForKey:name];
}

-(BOOL) isEmpty
{
	if([attributes count] == 0)
	{
		return YES;
	}
	
	return NO;
}

-(void) removeAttribute:(NSString *)name
{
	[attributes removeObjectForKey:name];
}

-(NSArray *) getNames
{
	return [attributes allKeys];
}

-(NSArray *) getValues
{
	return [attributes allValues];
}
@end
