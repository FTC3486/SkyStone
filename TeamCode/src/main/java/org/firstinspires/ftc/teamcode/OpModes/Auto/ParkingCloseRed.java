package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotConfiguration.Skystone.SkystoneRobot;
import org.firstinspires.ftc.teamcode.RobotCoreExtensions.EncoderAutoDriver;

/*
    Filename: ParkingCloseRed.java

    Description:
        Park robot close to wall on Blue.

    Use:
        Parking robot close to the wall on Blue.

    Requirements:
 *     - AutoDrive configured for stall monitor
 *     - Drive motors with encoders
 *     - Two Range sensors
 *     - one color sensor
 *     -Jewel armVertical
 * *
 * Changelog:
     *     -Created by Saatvik on 2/27/20.

 */

@Autonomous (group = "Red" )
public class ParkingCloseRed extends LinearOpMode {
    @Override
    public void runOpMode() {
        final SkystoneRobot skystoneRobot = new SkystoneRobot(this.hardwareMap);
        final EncoderAutoDriver encoderAutoDriver = new EncoderAutoDriver(skystoneRobot, this);
        //RangeAutoDriver rangeAutoDriver = new RangeAutoDriver(rover, this);
        skystoneRobot.initialize();

        //Autonomous for Squatty the Robot Blue Side - Park Close
        waitForStart();
        encoderAutoDriver.setPower(.5);
        encoderAutoDriver.driveToDistance(4);
        encoderAutoDriver.spinRight(7.6, -7.6);
        encoderAutoDriver.driveToDistance(10);
        skystoneRobot.platformServo.open();
        skystoneRobot.releaseServo.open();

    }
}