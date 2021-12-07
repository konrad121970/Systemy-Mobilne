//
//  SecondViewController.m
//  SM4_iOS3
//
//  Created by xd on 10/11/2021.
//  Copyright © 2021 xdmj. All rights reserved.
//

#import "SecondViewController.h"

@interface SecondViewController ()

@end

@implementation SecondViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    if(locationManager == nil){
        locationManager = [[CLLocationManager alloc] init];
        locationManager.delegate = self;
    }
    [locationManager requestWhenInUseAuthorization];
    [locationManager startUpdatingLocation];
    geocoder = [[CLGeocoder alloc] init];
    // Do any additional setup after loading the view.
}

-(void)getCurrentLocation:(id)sender{
    locationManager.delegate = self;
    if([locationManager respondsToSelector:@selector(requestWhenInUseAuthorization)]){
        [locationManager requestWhenInUseAuthorization];
    }
    locationManager.desiredAccuracy = kCLLocationAccuracyBest;
    [locationManager startUpdatingLocation];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)locationManager:(CLLocationManager*) manager didFailWithError: (NSError*)error{
        NSLog(@"didFailWithError: %@", error);
        UIAlertController* alertController = [UIAlertController alertControllerWithTitle:@"Error" message:@"Failed to get your location" preferredStyle:UIAlertControllerStyleAlert];
    
    UIAlertAction* okButton = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault handler:^(UIAlertAction* action){
        [self.view setBackgroundColor:[UIColor blueColor]];
    }];
    [alertController addAction:okButton];
    [self presentViewController:alertController animated:YES completion:nil];
}

-(void)locationManager:(CLLocationManager*) manager didUpdateLocations: (nonnull NSArray<CLLocation *> *)locations{
    NSLog(@"didUpdateLocations");
    CLLocation *currentLocation = [locations lastObject];
    if(currentLocation != nil){
        _longitudeText.text = [NSString stringWithFormat:@"%.8f", currentLocation.coordinate.longitude];
        _latitudeText.text = [NSString stringWithFormat:@"%.8f", currentLocation.coordinate.latitude];
    }
    [geocoder reverseGeocodeLocation:currentLocation completionHandler:^(NSArray<CLPlacemark*>* _Nullable placemarks, NSError * _Nullable error){
        if(error == nil && [placemarks count] >0){
            self->placemark = [placemarks lastObject];
            self->_addressText.text = [NSString stringWithFormat:@"%@%@\n%@ %@\n%@\n%@", self->placemark.subThoroughfare, self->placemark.thoroughfare, self->placemark.postalCode, self->placemark.locality, self->placemark.administrativeArea, self->placemark.country];
        }
        else{
            NSLog(@"%@", error.debugDescription);
        }
    }];
}



@end
