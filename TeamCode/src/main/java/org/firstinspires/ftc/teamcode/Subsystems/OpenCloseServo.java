package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotCoreExtensions.Initializable;

/**
 * Created by Team added 4 open positions on 10/17/2019.
 */

public class OpenCloseServo implements Initializable {
    private final Servo servo;
    private final double initializedPosition;
    private final double opened1Position;
    private final double opened2Position;
    private final double opened3Position;
    private final double opened4Position;
    private final double closedPosition;

    private enum OpenCloseServoState {
        INITIALIZED,
        OPENED1,
        OPENED2,
        OPENED3,
        OPENED4,
        CLOSED,
    }

    private OpenCloseServoState openCloseServoState = OpenCloseServoState.CLOSED;

    public OpenCloseServo(Servo servo, double initializedPosition, double opened1Position, double opened2Position, double opened3Position, double opened4Position, double closedPosition) {
        this.servo = servo;
        this.initializedPosition = initializedPosition;
        this.opened1Position = opened1Position;
        this.opened2Position = opened2Position;
        this.opened3Position = opened3Position;
        this.opened4Position = opened4Position;
        this.closedPosition = closedPosition;
        this.close();
    }

    @Override
    public void initialize() {
        servo.setPosition(initializedPosition);
        openCloseServoState = OpenCloseServoState.INITIALIZED;
    }

    public void opened1() {
        servo.setPosition(opened1Position);
        openCloseServoState = OpenCloseServoState.OPENED1;
    }
    public void opened2() {
        servo.setPosition(opened2Position);
        openCloseServoState = OpenCloseServoState.OPENED2;
    }
    public void opened3() {
        servo.setPosition(opened3Position);
        openCloseServoState = OpenCloseServoState.OPENED3;
    }
    public void opened4() {
        servo.setPosition(opened4Position);
        openCloseServoState = OpenCloseServoState.OPENED4;
    }

    public void close() {
        servo.setPosition(closedPosition);
        openCloseServoState = OpenCloseServoState.CLOSED;
    }

    @Override
    public String toString() {
        switch (openCloseServoState) {
            case INITIALIZED:
                return "Initialized";

            case OPENED1:
                return "Opened1";

            case OPENED2:
                return "Opened2";

            case OPENED3:
                return "Opened3";

            case OPENED4:
                return "Opened4";

            case CLOSED:
                return "Closed";

            default:
                return "Unknown";
        }
    }
}