//
//  MDDetailViewController.h
//  WhyMCA_Client
//
//  Created by Nicola Miotto on 2/18/12.
//  Copyright (c) 2012 Università degli studi di Padova. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MDDetailViewController : UIViewController

@property (nonatomic, strong) IBOutlet UITableView  *tableView;
@property (nonatomic, strong) NSMutableArray        *dataSource;

@end
