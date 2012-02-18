//
//  NSData+Json.m
//  WhyMCA_Client
//
//  Created by Nicola Miotto on 2/18/12.
//  Copyright (c) 2012 Università degli studi di Padova. All rights reserved.
//

#import "NSData+Json.h"
#import "SBJson.h"

@implementation NSData (Json)

- (id)decodeJson
{
    SBJsonParser *parser = [[SBJsonParser alloc] init];
    id repr = [parser objectWithData:self];
    if (!repr)
        NSLog(@"-JSONValue failed. Error is: %@", parser.error);
    return repr;
}

@end
