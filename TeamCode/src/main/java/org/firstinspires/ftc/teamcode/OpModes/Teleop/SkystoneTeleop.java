package org.firstinspires.ftc.teamcode.OpModes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotConfiguration.Skystone.SkystoneRobot;
import org.firstinspires.ftc.teamcode.RobotCoreExtensions.GamepadWrapper;
import org.firstinspires.ftc.teamcode.RobotCoreExtensions.TeleopDriver;

/**
 * Created by 3486 on 7/15/2017.
 */

@TeleOp(name = "Rover Teleop", group = "Teleop2018")
public class SkystoneTeleop extends OpMode {
    //Declare parts of the robot that will be used by this Teleop
    private SkystoneRobot skystoneRobot;
    //
    private GamepadWrapper joy1 = new GamepadWrapper();
    private GamepadWrapper joy2 = new GamepadWrapper();
    private TeleopDriver teleopDriver;
    boolean middleManipulatorOpen = true;
    boolean manipulatorOpen = true;

    @Override
    public void init() {
        skystoneRobot = new SkystoneRobot(this.hardwareMap);
        skystoneRobot.initialize();
        teleopDriver = new TeleopDriver(skystoneRobot);
        skystoneRobot.initialize();
        //skystoneRobot.jewelArm.fullyExtend();
    }

    @Override
    public void loop() {
        //Gamepad 1 is the driver controller, gamepad 2 is the gunner controller
        joy1.update(gamepad1);
        joy2.update(gamepad2);

        //Drivetrain*******************************************************

        //Toggle Half Speed on the drivetrain
        if (joy1.toggle.right_stick_button) {
            //Swap front and back of the robot, and control the drive train at half speed
            teleopDriver.setMaxSpeed(1f);
            if (joy1.toggle.left_stick_button) {
                teleopDriver.tankDrive(gamepad1, TeleopDriver.Direction.BACKWARD);
            } else {
                teleopDriver.tankDrive(gamepad1, TeleopDriver.Direction.FORWARD);
            }
        } else {
            //Swap front and back of the robot, and control the drive train
            teleopDriver.setMaxSpeed(.5f);
            if (joy1.toggle.left_stick_button) {
                teleopDriver.tankDrive(gamepad1, TeleopDriver.Direction.BACKWARD);
            } else {
                teleopDriver.tankDrive(gamepad1, TeleopDriver.Direction.FORWARD);
            }
        }

        //PICKUP Functions********************************************************

        //Pickup Release Servo
        if (gamepad1.a) {
            skystoneRobot.releaseServo.opened1();
        }  else if (gamepad1.b){
            skystoneRobot.releaseServo.close();

        //Pickup Motor
        if (gamepad1.right_bumper) {
                skystoneRobot.pickup1.run(.9);
                skystoneRobot.pickup2.run(.9);
            } else if (gamepad1.left_bumper) {
                skystoneRobot.pickup1.reverse(-0.5);
                skystoneRobot.pickup2.reverse(-0.5);
            } else
                skystoneRobot.pickup1.stop();
                skystoneRobot.pickup2.stop();

        //ARM Functions**************************************************************

        //Buttons for the arm extension/retraction
        if (gamepad2.left_stick_y > joy2.getLeftStickThreshold()) {
            skystoneRobot.armHorizontal.reverse(gamepad2.left_stick_y);
        } else if (gamepad2.left_stick_y < -joy2.getLeftStickThreshold()) {
            skystoneRobot.armHorizontal.run(gamepad2.left_stick_y);
        } else {
            skystoneRobot.armHorizontal.stop();
        }
        //Buttons for the arm up/down
        if (gamepad2.right_stick_y > joy2.getLeftStickThreshold()) {
            skystoneRobot.armVertical.reverse(gamepad2.right_stick_y);
        }  else if (gamepad2.right_stick_y < -joy2.getLeftStickThreshold()) {
            skystoneRobot.armVertical.run(gamepad2.right_stick_y);
        } else {
            skystoneRobot.armVertical.stop();
        }
        //Manipulator Functions **********************************************

        //Buttons for the Stone angle servo
            // Has 4 positions

        if (gamepad2.x) {
            skystoneRobot.angleServo.opened1();
        }  else if (gamepad2.y){
            skystoneRobot.angleServo.opened2();
        }  else if (gamepad2.b){
            skystoneRobot.angleServo.opened3();
        }  else if (gamepad2.a){
            skystoneRobot.angleServo.opened4();
        }
        //2nd joint 180 - rotates stone from in the armVertical to out - middleManipulatorServo
        if (gamepad2.left_bumper && middleManipulatorOpen) {
                skystoneRobot.middleManipulatorServo.opened1();
            middleManipulatorOpen = !middleManipulatorOpen;
        }  else {
            skystoneRobot.middleManipulatorServo.close();
            middleManipulatorOpen = !middleManipulatorOpen;
        }
        //Manipulator/grabber servo
        if (gamepad2.right_bumper && manipulatorOpen) {
            skystoneRobot.manipulatorServo.opened1();
            manipulatorOpen = !manipulatorOpen;
        }  else {
            skystoneRobot.manipulatorServo.close();
            manipulatorOpen = !manipulatorOpen;
        }

        //Capstone
        if (gamepad1.x) {
            skystoneRobot.capstoneServo.opened1();
        } else {
            skystoneRobot.capstoneServo.close();
        }

         //Skystone**********************************************

         //Skystone server




        }
        // old unused code
        /*
        //Buttons for the flapper motor-
        if (gamepad2.right_bumper) {
            skystoneRobot.flapperMotor.run();
        } else if (gamepad2.left_bumper) {
            skystoneRobot.flapperMotor.reverse(-0.5);
        } else {
            skystoneRobot.flapperMotor.stop();
        }
         //Buttons for the flapper servo
        if (Math.abs(gamepad2.right_stick_y) > joy2.getRightStickThreshold()) {
            skystoneRobot.flapperServo.run(gamepad2.right_stick_y);
        }



        */
        /*
        Buttons for the latch
        if (gamepad1.right_bumper) {
            skystoneRobot.latch.manualExtend();
        } else if (gamepad1.left_bumper) {
            skystoneRobot.latch.manualRetract();
        } else {
            skystoneRobot.latch.manualStop();
        }
        */


        // TODO: jewelColor should be private. Telemetry should be exposed through toString methods

        //telemetry.addData("Is Not Pressed", skystoneRobot.touch1();

        //telemetry.addData("Green Value", skystoneRobot.colorSensor.green());
        //telemetry.addData("Blue Value", skystoneRobot.colorSensor.blue());
        //telemetry.addData("Red Value", skystoneRobot.colorSensor.red());
        //telemetry.addData("Flapper Servo", skystoneRobot.flapperServo);
        //telemetry.update();

    }
}