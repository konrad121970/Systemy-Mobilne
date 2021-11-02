//
//  ViewController.h
//  Zadanie3
//
//  Created by student on 02/11/2021.
//  Copyright © 2021 com.iOSDevelopmentTutorials. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController
@property (nonatomic, weak) IBOutlet UIButton *informationButton;
@property (nonatomic, weak) IBOutlet UIImageView *imageView;

-(IBAction)InformationButtonClick;
@end

