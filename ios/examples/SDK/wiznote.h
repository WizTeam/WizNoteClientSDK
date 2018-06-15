//
//  wiznote.h
//  wiznote
//
//  Created by Wei Shijun on 13/02/2018.
//  Copyright © 2018 dzpqzb inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface WizNote : NSObject
+ (void) setup:(NSDictionary*)options;
+ (void) launchWizNote:(UIViewController *)parentViewController options:(NSDictionary *)options customActionBlock:(id)customActionBlock;
@end
