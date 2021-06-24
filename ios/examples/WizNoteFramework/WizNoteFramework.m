//
//  WizNoteFramework.m
//  WizNoteFramework
//
//  Created by Wei Shijun on 27/02/2018.
//  Copyright © 2018 Wei Shijun. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "WizNoteFramework.h"
#import "wiznote.h"



FOUNDATION_EXPORT BOOL WizNoteSetup(NSDictionary* options)
{
    [WizNote setup:options];
    return YES;
}

FOUNDATION_EXPORT BOOL WizNoteLaunch(NSDictionary* options,
                                     id customActionBlock,
                                     id updateCookiesBlock,
                                     id shareNoteCallbackBlock,
                                     id delegate,
                                     id completeHandler)
{
    [WizNote launchWizNote:options
         customActionBlock:customActionBlock
        updateCookiesBlock:updateCookiesBlock
    shareNoteCallbackBlock:shareNoteCallbackBlock
                  delegate:delegate
           completeHandler:completeHandler];
    return YES;
}


FOUNDATION_EXPORT BOOL WizNoteLaunchEditor(UIViewController* parentViewController, NSDictionary* options, id customActionBlock, id updateCookiesBlock, id delegate)
{
    [WizNote launchWizEditor:parentViewController
                     options:options
           customActionBlock:customActionBlock
          updateCookiesBlock:updateCookiesBlock
                    delegate:delegate
     ];
    return YES;
}



FOUNDATION_EXPORT BOOL WizNoteGetDocuments(NSString* appId, NSString* objectId, NSString* category, NSDictionary* options, id getDocumentsBlock)
{
    [WizNote getDocumentsListBy:appId objectId:objectId category:category userOptions:options completeBlock:getDocumentsBlock];
    return YES;
}

FOUNDATION_EXPORT NSArray* WizNoteGetDocumentsBy(NSString* appId, NSString* objectId, NSString* category, NSDictionary* options)
{
    return [WizNote getDocumentsListBy:appId objectId:objectId category:category userOptions:options];
}


FOUNDATION_EXPORT BOOL WizNoteGetDocumentsByNoteBook(NSDictionary* options, int64_t first, int64_t count ,id getDocumentsBlock)
{
    [WizNote getDocumentsListByNoteBook:options first:first count:count completeBlock:getDocumentsBlock];
    return YES;
}

FOUNDATION_EXPORT BOOL WizNoteShareInWith(NSDictionary* options,
                                          NSString* body,
                                          NSDictionary* pictures,
                                          NSString* title,
                                          id customActionBlock,
                                          id updateCookiesBlock,
                                          id shareNoteCallbackBlock,
                                          id delegate,
                                          id completeHandler)
{
    [WizNote shareInWithOptions:options
                           body:body
                       pictures:pictures
                          title:title
              customActionBlock:customActionBlock
             updateCookiesBlock:updateCookiesBlock
         shareNoteCallbackBlock:shareNoteCallbackBlock
                       delegate:delegate
                completeHandler:completeHandler];
    return YES;
}
