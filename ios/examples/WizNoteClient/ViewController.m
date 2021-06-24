//
//  ViewController.m
//  WizNoteClient
//
//  Created by Wei Shijun on 13/02/2018.
//  Copyright © 2018 inc. All rights reserved.
//

#import "ViewController.h"
#import "wiznote.h"

@interface ViewController ()
@property (nonnull, strong) NSArray* wiznoteStyles;
@property (nonnull, copy) NSString* currentStyle;
@end

@implementation ViewController
@synthesize wiznoteStyles;

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    //
    self.wiznoteStyles = @[@"style1", @"style2"];
    self.currentStyle = self.wiznoteStyles.firstObject;
    //
    UISegmentedControl* styles = [[UISegmentedControl alloc] initWithItems:self.wiznoteStyles];
    styles.selectedSegmentIndex = 0;
    styles.frame = CGRectMake(80, 200, 200, 40);
    [self.view addSubview:styles];
    [styles addTarget:self action:@selector(styleChanged:) forControlEvents:UIControlEventValueChanged];
    
    UIButton* btn = [[UIButton alloc] initWithFrame:CGRectMake(100, 260, 160, 44)];
    btn.backgroundColor = [UIColor redColor];
    [btn setTitle:@"Launch WizNote" forState:UIControlStateNormal];
    [btn addTarget:self action:@selector(launchWizNote) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btn];
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void) launchWizNote {
    NSDictionary* options = @{
                              @"appName": @"WeLink Note",
                              @"apiServer": @"http://v3.wiz.cn",
                              @"lang": @"zh-cn",    //en, zh-cn, zh-hans, etc
                              @"disableHttps": @(YES),
                              @"appStyle" : @"style1",
                              @"disableSubFolder" : @(YES),
                              @"disableTeam": @(YES),
                              //@"disableTag": @(YES),
                              @"disableReminder": @(YES),
                              @"disableAttachment": @(YES),
                              @"disableComment": @(YES),
                              @"disableShare": @(YES),
                              //@"disableOnTop": @(YES),
                              @"disableEncrypt": @(YES),
                              @"disableShortcut": @(YES),
                              @"disableGuide": @(YES),
                              @"disableTrackChanges": @(YES),
                              //
                              @"thirdpartyType": @"huawei"
                              };
    //
    [WizNote setup:options];
    //
    //
    NSDictionary* launchOptions = @{
                                    @"userId": @"guide103@wiz.cn",
                                    @"password": @"111111",
                                    };

    id actionCallback = ^void(int actionType, NSString* data) {
        NSLog(@"customAction: %@", @(actionType));
    };
    
    //
    typedef void (^UpdateCookiesResultBlock)(NSString* cookies, NSError* err);
    typedef void (^UpdateCookiesBlock)(UpdateCookiesResultBlock resultBlock);
    //
    UpdateCookiesBlock updateCookiesBlock = ^void(UpdateCookiesResultBlock resultBlock) {
        dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
            //
            resultBlock(@"new cookies", nil);
            //
        });
    };
    [WizNote launchWizNote:launchOptions customActionBlock:actionCallback updateCookiesBlock:updateCookiesBlock shareNoteCallbackBlock:nil delegate:self completeHandler:^(UIViewController *viewController) {
        UINavigationController* nav = [[UINavigationController alloc] initWithRootViewController:viewController];
        [self presentViewController:nav animated:YES completion:nil];
//        [self pushViewController:viewController animated:YES];
    }];
}

- (void) styleChanged:(UISegmentedControl *)obj
{
    self.currentStyle = self.wiznoteStyles[obj.selectedSegmentIndex];
}
@end

