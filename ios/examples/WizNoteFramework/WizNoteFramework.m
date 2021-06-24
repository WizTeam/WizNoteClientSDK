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

FOUNDATION_EXPORT BOOL WizNoteLaunch(UIViewController* parentViewController, NSDictionary* options, id customActionBlock, id updateCookiesBlock, id shareNoteCallback)
{
//    [WizNote launchWizNote:options customActionBlock:customActionBlock updateCookiesBlock:updateCookiesBlock shareNoteCallbackBlock:shareNoteCallback delegate:<#(id)#> completeHandler:^(UIViewController *viewController) {
//        
//    }]
//    [WizNote launchWizNote:parentViewController options:options customActionBlock:customActionBlock updateCookiesBlock:updateCookiesBlock];
    return YES;
}

