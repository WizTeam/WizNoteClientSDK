//
//  ViewController.m
//  WizNoteClient
//
//  Created by Wei Shijun on 13/02/2018.
//  Copyright © 2018 inc. All rights reserved.
//

#import "ViewController.h"
#import "WizNoteFramework.h"

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
                              @"appName": @"我的笔记",
                              @"apiServer": @"http://sandbox.wiz.cn",
                              @"lang": @"en-us",    //en, zh-cn, zh-hans, etc
                              @"authType": @"huawei",
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
                              };
    //
    WizNoteSetup(options);
    //
    //
    NSDictionary* launchOptions = @{
                                    @"enterpriseUserId": @"anzhen-test2@wiz.cn",
                                    @"authType": @"huawei",
                                    @"authCode": @"ef65f67c1eae1e636a76c951b0f2d2a84ykc249vyll",
                                    @"authBody": @"abc",
                                    };
    
    id actionCallback = ^void(int actionType, NSString* data) {
        NSLog(@"customAction: %@", @(actionType));
    };
    
    WizNoteLaunch(self, launchOptions, actionCallback);
}

- (void) styleChanged:(UISegmentedControl *)obj
{
    self.currentStyle = self.wiznoteStyles[obj.selectedSegmentIndex];
}
@end

