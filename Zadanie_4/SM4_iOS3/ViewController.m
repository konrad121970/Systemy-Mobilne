//
//  ViewController.m
//  SM4 - iOS3
//
//  Created by student on 09/11/2021.
//  Copyright © 2021 xdmj. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self becomeFirstResponder];
    // Do any additional setup after loading the view, typically from a nib.
}

-(void)motionEnded:(UIEventSubtype)motion withEvent:(UIEvent*)event{
    if(motion==UIEventSubtypeMotionShake){
        NSLog(@"Trzesie mnom ;-;");
        [self showShakeDetectedAlert];
    }
}

-(BOOL)canBecomeFirstResponder{
    return YES;
}

-(IBAction)showShakeDetectedAlert{
    UIAlertController *alertController = [UIAlertController
                                          alertControllerWithTitle:[NSString stringWithFormat:@"Shake gesture detected"]
                                          message:[NSString stringWithFormat:@"Do you want to change the background color?"]
                                          preferredStyle:UIAlertControllerStyleAlert];
    
    UIAlertAction *yesButton = [UIAlertAction actionWithTitle:@"Yes" style:UIAlertActionStyleDefault handler:^(UIAlertAction *action){
        self.view.backgroundColor = [UIColor redColor];
    }];
    
    UIAlertAction *noButton = [UIAlertAction actionWithTitle:@"No" style:UIAlertActionStyleDefault handler:^(UIAlertAction *action){
        self.view.backgroundColor = [UIColor whiteColor];
        NSLog(@"Shake detected!");
    }];
    
    [alertController addAction:yesButton];
    [alertController addAction:noButton];
    [self presentViewController:alertController animated:YES completion:nil];
}

-(IBAction) tapGesture:(UITapGestureRecognizer*) sender{
    _gestureLabel.text = @"Tap detected";
}


-(IBAction) pinchGesture:(UIPinchGestureRecognizer*) sender{
    _gestureLabel.text = @"Pinch detected";
}

-(IBAction) swipeGesture:(UISwipeGestureRecognizer*) sender{
    _gestureLabel.text = @"Swipe detected";
}

-(IBAction) longPressGesture:(UILongPressGestureRecognizer*) sender{
    _gestureLabel.text = @"Long press detected";
}
@end
