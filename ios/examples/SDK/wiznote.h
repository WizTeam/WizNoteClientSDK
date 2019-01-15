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
+ (void) launchWizNote:(UIViewController *)parentViewController options:(NSDictionary *)options customActionBlock:(id)customActionBlock updateCookiesBlock:(id)updateCookiesBlock shareNoteCallbackBlock:(id)shareNoteCallbackBlock delegate:(id)object;
+ (void) getDocumentsListBy:(NSString*)appId objectId:(NSString*)objectId userOptions:(NSDictionary*)options completeBlock:(void(^)(NSArray* documents))block;
+ (void) getDocumentsListByCategory:(NSString*)category userOptions:(NSDictionary*)options first:(int64_t)first count:(int64_t)count completeBlock:(void(^)(NSArray* documents))block;
@end
