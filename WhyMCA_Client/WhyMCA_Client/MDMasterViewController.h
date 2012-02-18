//
//  MDMasterViewController.h
//  WhyMCA_Client
//
//  Created by Nicola Miotto on 2/18/12.
//  Copyright (c) 2012 Università degli studi di Padova. All rights reserved.
//

#import <UIKit/UIKit.h>

@class MDDetailViewController;

@interface MDMasterViewController : UITableViewController

@property (strong, nonatomic) MDDetailViewController    *detailViewController;
@property (strong, nonatomic) NSMutableArray            *dataSource;

@end
