//
//  WizNoteFramework.h
//  WizNoteFramework
//
//  Created by Wei Shijun on 27/02/2018.
//  Copyright © 2018 Wei Shijun. All rights reserved.
//

#import <UIKit/UIKit.h>

//! Project version number for WizNoteFramework.
FOUNDATION_EXPORT double WizNoteFrameworkVersionNumber;

//! Project version string for WizNoteFramework.
FOUNDATION_EXPORT const unsigned char WizNoteFrameworkVersionString[];

// In this header, you should import all the public headers of your framework using statements like #import <WizNoteFramework/PublicHeader.h>


FOUNDATION_EXPORT BOOL WizNoteSetup(NSDictionary* options);
FOUNDATION_EXPORT BOOL WizNoteLaunch(NSDictionary* options,
                                     id customActionBlock,
                                     id updateCookiesBlock,
                                     id shareNoteCallbackBlock,
                                     id delegate,
                                     id completeHandler);
FOUNDATION_EXPORT BOOL WizNoteLaunchEditor(UIViewController* parentViewController, NSDictionary* options, id customActionBlock, id updateCookiesBlock, id delegate);
FOUNDATION_EXPORT BOOL WizNoteGetDocuments(NSString* appId, NSString* objectId, NSString* category,NSDictionary* options, id getDocumentsBlock);
FOUNDATION_EXPORT NSArray* WizNoteGetDocumentsBy(NSString* appId, NSString* objectId, NSString* category, NSDictionary* options);
FOUNDATION_EXPORT BOOL WizNoteGetDocumentsByNoteBook(NSDictionary* options, int64_t first, int64_t count ,id getDocumentsBlock);
