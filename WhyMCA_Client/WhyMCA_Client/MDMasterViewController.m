//
//  MDMasterViewController.m
//  WhyMCA_Client
//
//  Created by Nicola Miotto on 2/18/12.
//  Copyright (c) 2012 Università degli studi di Padova. All rights reserved.
//

#import "MDMasterViewController.h"

#import "MDDetailViewController.h"
#import "MDGridTableViewCell.h"
#import "UIView+SelfFromNib.h"
#import "UIImageView+AFNetworking.h"
#import "Constants.h"
#import "ASIFormDataRequest.h"
#import "NSData+JSON.h"

@implementation MDMasterViewController

@synthesize detailViewController    = _detailViewController;
@synthesize dataSource              = _dataSource;
@synthesize theftId                 = _theftId;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.title = NSLocalizedString(@"Not robbed yet", nil);
        self.dataSource = [[NSMutableArray alloc] init];
    }
    return self;
}
							
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}

- (void)setTheftId:(NSString *)theftId
{
    if(![theftId isEqualToString:_theftId] &&
       theftId != nil) {
        _theftId = theftId;
        NSLog(@"Theft id set to %@", theftId);
        [self.navigationItem setTitle:[NSString stringWithFormat:@"Robbed. Theft ID: %@",theftId]];
        // Invoke api
        NSString *uri = [NSString stringWithFormat:@"%@%@?%@=%@", kMDURLBackend, kMDPathGetPics, kMDParTheftIdGetPics, theftId];
        NSLog(@"%@", uri);
        ASIFormDataRequest  *request = [[ASIFormDataRequest alloc] initWithURL:[NSURL URLWithString:uri]];
        [request setDelegate:self];
        [request startAsynchronous];
    }
}

#pragma mark - ASIHTTPRequest callbacks
- (void)requestFinished:(ASIHTTPRequest *)aResponse
{
    NSArray *pics = [[aResponse responseData] decodeJson];
    NSLog(@"Pics %@", [pics objectAtIndex:1]);
    NSMutableArray *tempDataSource = [[NSMutableArray alloc] initWithCapacity:[pics count]];
    [self.dataSource removeAllObjects];
    
    for (NSString *pic in pics) {
        NSString *completeUrl = [NSString stringWithFormat:@"%@%@/%@",kMDURLBackend, self.theftId, pic];
        [tempDataSource addObject:completeUrl];
        if ([tempDataSource count] == 4) {
            [self.dataSource addObject:[tempDataSource copy]];
            [tempDataSource removeAllObjects];
        }
    }
    
    [self.tableView reloadData];
}

- (void)requestFailed:(ASIHTTPRequest *)request
{
    NSLog(@"ERROR: %@", [request error]);
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
	[super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
	[super viewDidDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}

// Customize the number of sections in the table view.
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.dataSource count];
}

// Customize the appearance of table view cells.
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"MDGridTableViewCell";
    
    MDGridTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [MDGridTableViewCell selfFromNib];
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    }

    // Configure the cell.
    NSArray *aRowData = [self.dataSource objectAtIndex:indexPath.row];
    [cell.pic1 setImageWithURL:[NSURL URLWithString:[aRowData objectAtIndex:0]]];
    [cell.pic2 setImageWithURL:[NSURL URLWithString:[aRowData objectAtIndex:1]]];
    [cell.pic3 setImageWithURL:[NSURL URLWithString:[aRowData objectAtIndex:2]]];
    [cell.pic4 setImageWithURL:[NSURL URLWithString:[aRowData objectAtIndex:3]]];
    
    return cell;
}

/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source.
        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view.
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return kMDRowHeight;
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (!self.detailViewController) {
        self.detailViewController = [[MDDetailViewController alloc] initWithNibName:@"MDDetailViewController" bundle:nil];
    }
    NSMutableArray *aRowData = [self.dataSource objectAtIndex:indexPath.row];
    self.detailViewController.dataSource = aRowData;
    [self.navigationController pushViewController:self.detailViewController animated:YES];
}

@end
