package org.firstinspires.ftc.teamcode.RobotCoreExtensions;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaBase;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class VuforiaDriver implements AutoCloseable {
    private final VuforiaBase vuforia;
    private final List<String> trackableNames;
    private final String vuforiaLicenseKey;

    public VuforiaDriver(
            final VuforiaBase vuforia,
            final List<String> trackableNames,
            final String vuforiaLicenseKey
    ) {
        this.vuforia = vuforia;
        this.trackableNames = trackableNames;
        this.vuforiaLicenseKey = vuforiaLicenseKey;
    }

    public void initialize(
            final VuforiaLocalizer.CameraDirection cameraDirection,
            final Position cameraPosition,
            final Orientation cameraOrientation
    ) {
        final Position cameraPositionMm = cameraPosition.toUnit(DistanceUnit.MM);
        final Orientation cameraOrientationDeg = cameraOrientation
                .toAngleUnit(AngleUnit.DEGREES)
                .toAxesOrder(AxesOrder.XYZ)
                .toAxesReference(AxesReference.EXTRINSIC);

        vuforia.initialize(
                vuforiaLicenseKey,
                cameraDirection,
                true,
                true,
                VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES,
                (float) cameraPositionMm.x,
                (float) cameraPositionMm.y,
                (float) cameraPositionMm.z,
                cameraOrientationDeg.firstAngle,
                cameraOrientationDeg.secondAngle,
                cameraOrientationDeg.thirdAngle,
                true
        );

        vuforia.activate();
    }

    /**
     * Calculate a single pose (position and orientation) from all tracking results.
     * <p>
     * When we received more than one tracking result claiming to have updated information about the
     * location of the robot, we should take care not to randomly select just one of these results
     * and run with it. The example code does this, and it could lead to a poor result.
     * <p>
     * Weighting is performed by sorting the results by distance from the robot and visibility. The
     * assumptions we are making are these:
     * <p>
     * 1. Visible markers will have more accurate readings than markers no longer visible.
     * 2. The closer a marker is to the robot, the more accurate its reading will be.
     *
     * @return The position of the robot
     */
    public OpenGLMatrix getRobotPosition() throws PositionCalculationException {
        final List<RobotPoseAndAssociatedMarker> robotPoseData = trackableNames.stream()
                .map(name -> new RobotPoseAndAssociatedMarker(vuforia.track(name), vuforia.trackPose(name)))
                .filter(pose -> pose.robotPose.isUpdatedRobotLocation)
                .sorted(Comparator.comparing(pose -> pose.markerPose.matrix.getTranslation().magnitude()))
                .sorted(Comparator.comparing(pose -> pose.robotPose.isVisible))
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());

        if (robotPoseData.isEmpty()) {
            throw new PositionCalculationException("After filtering for robot poses with " +
                    "available position data, there were no poses available from which a " +
                    "position could be calculated");
        }

//        return robotPoseData.get(robotPoseData.size()-1).robotPose.matrix;

        final float scalingFactor = 0.8F;
        final float geometricSumReciprocal = (float)
                ((1 - scalingFactor) / (1 - Math.pow(scalingFactor, robotPoseData.size())));

        return robotPoseData.stream()
                .map(pose -> pose.robotPose.matrix)
                .reduce((pose1, pose2) -> new OpenGLMatrix(pose1.scaled(0.8F).added(pose2)))
                .get()
                .scaled(geometricSumReciprocal);
    }

    /**
     * Container class to store a robot pose (the robot's location on the field) with the marker
     * that generated this location and its pose, which is relative to the robot and not the field.
     */
    private static class RobotPoseAndAssociatedMarker {
        final VuforiaBase.TrackingResults robotPose;
        final VuforiaBase.TrackingResults markerPose;

        RobotPoseAndAssociatedMarker(
                final VuforiaBase.TrackingResults robotPose,
                final VuforiaBase.TrackingResults markerPose
        ) {
            this.robotPose = robotPose;
            this.markerPose = markerPose;
        }
    }

    /**
     * To be thrown when the Vuforia Driver isn't capable of calculating the robot's position.
     */
    public static class PositionCalculationException extends Exception {
        public PositionCalculationException(String message) {
            super(message);
        }
    }

    @Override
    public void close() {
        vuforia.close();
    }
}
