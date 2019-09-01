package org.firstinspires.ftc.teamcode.OpModes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotCoreExtensions.GamepadWrapper;

@TeleOp(name = "Mechanum Drive Teleop", group = "Teleop")
public class MechanumDriveTeleop extends OpMode {
    private final GamepadWrapper joy1 = new GamepadWrapper();
    private final GamepadWrapper joy2 = new GamepadWrapper();

    private final DcMotor frontLeft = hardwareMap.dcMotor.get("Front Left");
    private final DcMotor frontRight = hardwareMap.dcMotor.get("Front Right");
    private final DcMotor backLeft = hardwareMap.dcMotor.get("Back Left");
    private final DcMotor backRight = hardwareMap.dcMotor.get("Back Right");

    private static final float CLOCKWISE_TURNING_SENSITIVITY = 0.5f;

    @Override
    public void init() {
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
    }

    @Override
    public void loop() {
        //Gamepad 1 is the driver controller, gamepad 2 is the gunner controller
        joy1.update(gamepad1);
        joy2.update(gamepad2);

        // Create direction vector
        final float forward = -gamepad1.left_stick_y;
        final float right = gamepad1.left_stick_x;
        float clockwise = gamepad1.right_stick_x;

        // Now add a tuning constant K for the “rotate” axis sensitivity.
        // Start with K=0, and increase it very slowly (do not exceed K=1)
        // to find the right value after you’ve got fwd/rev and strafe working:
        clockwise = CLOCKWISE_TURNING_SENSITIVITY * clockwise;

        // Now apply the inverse kinematic tranformation
        // to convert your vehicle motion command
        // to 4 wheel speed commands:
        float frontLeftSpeed = forward + clockwise + right;
        float frontRightSpeed = forward - clockwise - right;
        float backLeftSpeed = forward + clockwise - right;
        float backRightSpeed = forward - clockwise + right;

        // Finally, normalize the wheel speed commands
        // so that no wheel speed command exceeds magnitude of 1:
        float max = Math.abs(frontLeftSpeed);
        if (Math.abs(frontRightSpeed) > max) max = Math.abs(frontRightSpeed);
        if (Math.abs(backLeftSpeed) > max) max = Math.abs(backLeftSpeed);
        if (Math.abs(backRightSpeed) > max) max = Math.abs(backRightSpeed);
        if (max > 1) {
            frontLeftSpeed /= max;
            frontRightSpeed /= max;
            backLeftSpeed /= max;
            backRightSpeed /= max;
        }

        // Send power to wheels
        frontLeft.setPower(frontLeftSpeed);
        frontRight.setPower(frontRightSpeed);
        backLeft.setPower(backLeftSpeed);
        backRight.setPower(backRightSpeed);
    }
}
