//
//  UIView+SelfFromNib.m
//
//  Created by Alberto De Bortoli on 14/08/11.
//  Copyright 2010 Qwoote. All rights reserved.
//

#import <QuartzCore/QuartzCore.h>

@implementation UIView (SelfFromNib)

+ (id)selfFromNib
{
    Class theClass = [self class];
    NSArray *topLevelObjects = [[NSBundle mainBundle] loadNibNamed:[theClass description] owner:nil options:nil];
    for(id currentObject in topLevelObjects) {
        if([currentObject isKindOfClass:[self class]]) {
            return currentObject;
        }
    }
    return nil;
}

@end
