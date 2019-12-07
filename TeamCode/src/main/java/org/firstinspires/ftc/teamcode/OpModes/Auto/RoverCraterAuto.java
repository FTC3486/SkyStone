package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotConfiguration.Skystone.SkystoneRobot;
import org.firstinspires.ftc.teamcode.RobotCoreExtensions.EncoderAutoDriver;

/*
    Filename: RoverCraterAuto.java

    Description:
        Test Autonomous program using encoders to drive Rover.

    Use:
        Test program to test encoderAutoDriver, JewelArm and RangeAutoDriver.  Test Stall monitor.

    Requirements:
 *     - AutoDrive configured for stall monitor
 *     - Drive motors with encoders
 *     - Two Range sensors
 *     - one color sensor
 *     -Jewel armVertical
 * *
 * Changelog:
 *     -Created by Saatvik on 10/5/18.
 *     -

 */

@Autonomous (group = "Blue" )
public class RoverCraterAuto extends LinearOpMode {
    @Override
    public void runOpMode() {
        final SkystoneRobot skystoneRobot = new SkystoneRobot(this.hardwareMap);
        final EncoderAutoDriver encoderAutoDriver = new EncoderAutoDriver(skystoneRobot, this);
        //RangeAutoDriver rangeAutoDriver = new RangeAutoDriver(rover, this);
        skystoneRobot.initialize();

        waitForStart();
/*
        encoderAutoDriver.setPower(1);
        while(!skystoneRobot.latch.isFullyExtended())
        {
            skystoneRobot.latch.extend();
            telemetry.addData("Latch state", skystoneRobot.latch.toString());
            telemetry.update();
        }

        //This is copy and pasted from RoverDepotAuto.java, the original code is commented out
        //encoderAutoDriver.driveToDistance(-1);
        skystoneRobot.latch.manualStop();

 */     while((skystoneRobot.getDrivetrain().getRightEncoderCount() <= 2500) && (opModeIsActive())) {
            encoderAutoDriver.setPower(0.75);
            encoderAutoDriver.driveToDistance(5);
        }

        encoderAutoDriver.setPower(0);
        //encoderAutoDriver.spinLeft(10.0,-10.0);
        //encoderAutoDriver.driveToDistance(24);
        //Below is a correction turn
        //encoderAutoDriver.spinRight(0.4, -0.4);

        //Go to the end of the sampling items
       // skystoneRobot.getDrivetrain().resetMotorEncoders();
        //skystoneRobot.getDrivetrain().setPowers(0.3, 0.3);
        //while ((skystoneRobot.getDrivetrain().getLeftEncoderCount() <= 2700) && !skystoneRobot.foundYellowObject() && opModeIsActive())
       // {
            //telemetry.addData("Green Value", skystoneRobot.colorSensor.green());
            //telemetry.addData("Blue Value", skystoneRobot.colorSensor.blue());
           // telemetry.addData("Red Value", skystoneRobot.colorSensor.red());
            //telemetry.addData("LeftEncoder", skystoneRobot.getDrivetrain().getLeftEncoderCount());
            //telemetry.update();
        //}
       // double counts = skystoneRobot.getDrivetrain().getLeftEncoderCount();
        /*
        skystoneRobot.getDrivetrain().haltDrive();
        encoderAutoDriver.spinLeft(-10,10);
        encoderAutoDriver.driveToDistance(-6);
        encoderAutoDriver.driveToDistance(6);
        encoderAutoDriver.spinRight(10.2, -10.2);
        skystoneRobot.getDrivetrain().setPowers(0.5, 0.5);
       // while(skystoneRobot.getDrivetrain().getLeftEncoderCount() <= 5075 - counts && opModeIsActive()) {telemetry.addData("Encoder", skystoneRobot.getDrivetrain().getLeftEncoderCount());}

        encoderAutoDriver.spinLeft(-28.5, 28.5);
        encoderAutoDriver.driveToDistance(-45);
        //encoderAutoDriver.spinLeft(-22, 22);
        encoderAutoDriver.driveToDistance(70);
        encoderAutoDriver.spinLeft(-.5,.5);

         */


    }
}