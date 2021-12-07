//
//  ViewController.h
//  SM4 - iOS3
//
//  Created by student on 09/11/2021.
//  Copyright © 2021 xdmj. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SecondViewController.h"
@interface ViewController : UIViewController
@property(weak, nonatomic) IBOutlet UILabel *gestureLabel;


-(IBAction) tapGesture: (UITapGestureRecognizer*) sender;
-(IBAction) pinchGesture: (UIPinchGestureRecognizer*) sender;
-(IBAction) swipeGesture: (UISwipeGestureRecognizer*) sender;
-(IBAction) longPressGesture: (UILongPressGestureRecognizer*) sender;


@end
