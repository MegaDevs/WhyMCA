//
//  MDAppDelegate.m
//  WhyMCA_Client
//
//  Created by Nicola Miotto on 2/18/12.
//  Copyright (c) 2012 Università degli studi di Padova. All rights reserved.
//

#import "MDAppDelegate.h"

#import "MDMasterViewController.h"
#import "StackMob.h"
#import "Constants.h"
#import "ASIHTTPRequest.h"

@implementation MDAppDelegate

@synthesize window = _window;
@synthesize navigationController = _navigationController;
@synthesize masterViewController = _masterViewController;

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    // Override point for customization after application launch.
    [[UIApplication sharedApplication] registerForRemoteNotificationTypes:
     (UIRemoteNotificationTypeAlert | UIRemoteNotificationTypeBadge | UIRemoteNotificationTypeSound)];
    
    [ASIHTTPRequest setDefaultTimeOutSeconds:30];
    
    // Get theft id
    NSString *theftId = [[launchOptions objectForKey:@"UIApplicationLaunchOptionsRemoteNotificationKey"] 
                         objectForKey:kMDUserDefaultTheftId];
    if (theftId != nil) {
        [[NSUserDefaults standardUserDefaults] setObject:theftId forKey:kMDUserDefaultTheftId];
        [[NSUserDefaults standardUserDefaults] synchronize];
    }
    
    self.masterViewController = [[MDMasterViewController alloc] initWithNibName:@"MDMasterViewController" bundle:nil];
    self.navigationController = [[UINavigationController alloc] initWithRootViewController:self.masterViewController];
    self.window.rootViewController = self.navigationController;
    [self.window makeKeyAndVisible];
    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    /*
     Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
     Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
     */
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    /*
     Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
     If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
     */
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    /*
     Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
     */
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    /*
     Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
     */
    self.masterViewController.theftId = [[NSUserDefaults standardUserDefaults] objectForKey:kMDUserDefaultTheftId];
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    /*
     Called when the application is about to terminate.
     Save data if appropriate.
     See also applicationDidEnterBackground:.
     */
}

- (void)application:(UIApplication *)app didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken
{
    NSString *token = [[deviceToken description] stringByTrimmingCharactersInSet:[NSCharacterSet characterSetWithCharactersInString:@"<>"]];
    
    // Use device ID to uniquely identify user
    NSString *deviceUDID = [[UIDevice currentDevice] uniqueIdentifier];
    
    token = [[token componentsSeparatedByString:@" "] componentsJoinedByString:@""];
    // Persist your user's accessToken here if you need
    [[StackMob stackmob] registerForPushWithUser:deviceUDID token:token andCallback:^(BOOL success, id result){
        if(success){
            // token saved sucessfully for user
            NSLog(@"StacMob reg success");
        }
        else{
            // Unable to register device for PUSH notifications
            // Failed.  Alert your delgates
            NSLog(@"StacMob reg fail");
        }
    }];
}

@end
