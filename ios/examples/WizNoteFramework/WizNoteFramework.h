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
FOUNDATION_EXPORT BOOL WizNoteLaunch(UIViewController* parentViewController, NSDictionary* options, id customActionBlock, id updateCookiesBlock);
