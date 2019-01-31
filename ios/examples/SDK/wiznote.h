//
//  wiznote.h
//  wiznote
//
//  Created by Wei Shijun on 13/02/2018.
//  Copyright © 2018 dzpqzb inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface WizNote : NSObject
/*
 云笔记设置
 */
+ (void) setup:(NSDictionary*)options;

/*
 启动云笔记
 options: 用户信息
 */
/*
 启动云笔记阅读界面
 options: 用户信息 + 笔记id （ParamName：documentGuid）
 */
/*
 启动云笔记笔记本
 options: 用户信息 + 笔记本name （ParamName：category）
*/
+ (void) launchWizNote:(UIViewController *)parentViewController options:(NSDictionary *)options customActionBlock:(id)customActionBlock updateCookiesBlock:(id)updateCookiesBlock shareNoteCallbackBlock:(id)shareNoteCallbackBlock delegate:(id)object;

/*
 启动云笔记普通新建界面
 options: 用户信息 + 笔记信息 （ParamName：noteInfo）笔记信息可以为空
 eg: @"noteInfo":@{@"title": @“笔记标题”, @"category": @“/笔记本name/”}
 */
/*
 启动云笔记指定id新建界面
 options: 用户信息 + appId + objectId （ParamName：outerAppId 、 outerObjectId）
 eg:    @"outerAppId":@"huawei",
        @"outerObjectId":@"会议id",
        @"noteInfo":@{@"title": @“会议纪要”, @"category": @“/MyMeeting/”}  //title可以不传
 */
+ (void) launchWizEditor:(UIViewController *)parentViewController options:(NSDictionary *)options customActionBlock:(id)customActionBlock updateCookiesBlock:(id)updateCookiesBlock delegate:(id)object;

/*
 获取某一objecId下的笔记列表
 appId    应用id   ( required)
 objectId    对象id，例如会议id  ( required)
 options    用户信息
 getDocumentsBlock 获取笔记列表后的返回
 eg:    id getDocumentsBlock =  ^void(NSArray* documents) {
            //处理返回的笔记列表
        };
        WizNoteGetDocuments(@"huawei", @"会议id", userOptions, getDocumentsBlock);
 */
+ (void) getDocumentsListBy:(NSString*)appId objectId:(NSString*)objectId userOptions:(NSDictionary*)options completeBlock:(void(^)(NSArray* documents))block;

/*
 分页获取某一笔记本下的笔记列表
 category   笔记本名称 (required)
 options    用户信息
 first  分页开始
 count  分页Size
 getDocumentsBlock 获取笔记列表后的返回
 eg:    id getDocumentsBlock =  ^void(NSArray* documents) {
            //处理返回的笔记列表
        };
        WizNoteGetDocumentsByCategory(@"/MyMeeting/", launchOptions, 0, 1, getDocumentsBlock2);
 */
+ (void) getDocumentsListByCategory:(NSString*)category userOptions:(NSDictionary*)options first:(int64_t)first count:(int64_t)count completeBlock:(void(^)(NSArray* documents))block;
@end
