//
//  MDAppDelegate.h
//  WhyMCA_Client
//
//  Created by Nicola Miotto on 2/18/12.
//  Copyright (c) 2012 Università degli studi di Padova. All rights reserved.
//

#import <UIKit/UIKit.h>

@class MDMasterViewController;

@interface MDAppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (strong, nonatomic) UINavigationController *navigationController;

@property (strong, nonatomic) MDMasterViewController *masterViewController;

@end
