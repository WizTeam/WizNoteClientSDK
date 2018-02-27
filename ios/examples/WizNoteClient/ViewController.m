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

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    
    UIButton* btn = [[UIButton alloc] initWithFrame:CGRectMake(100, 100, 160, 44)];
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
                              @"appStyle" : @"style1",
                              @"disableSubFolder" : @(YES),
                              @"disableTeam": @(YES),
                              @"disableTag": @(YES),
                              @"disableReminder": @(YES),
                              @"disableAttachment": @(YES),
                              @"disableComment": @(YES),
                              @"disableShare": @(YES),
                              @"disableOnTop": @(YES),
                              @"disableEncrypt": @(YES),
                              @"disableShortcut": @(YES),
                              };
    WizNoteSetup(options);
    WizNoteLaunch(self, options);
}

@end

