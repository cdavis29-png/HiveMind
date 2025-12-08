
package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Constants.*;

        import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Hardware {
    private static Hardware instance;

    public DcMotor frontLeft; // 3
    public DcMotor backLeft; // 2
    public DcMotor frontRight; // 1
    public DcMotor backRight; // 0

    public DcMotor flywheel1;
    public DcMotor flywheel2;
    public DcMotor intake1;
    public DcMotor intake2;

    public IMU imu;

    public enum RunMode {
        ROBOT_CENTRIC,
        FIELD_CENTRIC
    }
    RunMode mode = RunMode.ROBOT_CENTRIC;

    public Hardware(HardwareMap hwMap) {
        // initialize the drivetrain
        frontLeft = hwMap.get(DcMotor.class, "frontLeft");
        backLeft = hwMap.get(DcMotor.class, "backLeft");
        frontRight = hwMap.get(DcMotor.class, "frontRight");
        backRight = hwMap.get(DcMotor.class, "backRight");

        // reverse the left-side drive motors
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        // initialize te gyro
        imu = hwMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        LOGO_FACING_DIR,
                        USB_FACING_DIR
                )
        );
        // use these parameters for the gyro
        imu.initialize(parameters);
    }

    /**
     * sets the mode of all drive motors
     */
    public void setDriveTrainMode(DcMotor.RunMode mode) {
        frontLeft.setMode(mode);
        backLeft.setMode(mode);
        frontRight.setMode(mode);
        backRight.setMode(mode);
    }

    /**
     * Sets all the drive motors to a specific power
     */
    public void setMotorPowers(double power) {
        frontLeft.setPower(power);
        backLeft.setPower(power);
        frontRight.setPower(power);
        backRight.setPower(power);
    }

    /**
     Sets each drive motor to a specific power
     */
    public void setMotorPowers(double fl, double bl, double fr, double br) {
        frontLeft.setPower(fl);
        backLeft.setPower(bl);
        frontRight.setPower(fr);
        backRight.setPower(br);
    }

    // default drive is robot centric
    public void drive(double x, double y, double rx) {
        if (mode == RunMode.FIELD_CENTRIC) {
            double robotHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            x = x * Math.cos(-robotHeading) - y * Math.sin(-robotHeading);
            y = x * Math.sin(-robotHeading) + y * Math.cos(-robotHeading);
        }

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        setMotorPowers(frontLeftPower, backLeftPower, frontRightPower, backRightPower);
    }

    public void stop() {
        setMotorPowers(0);
    }

    /**
     * @return current run mode of the robot: is it in field or robot centric drive?
     */
    public RunMode getMode() {
        return mode;
    }
    public void setMode(RunMode mode) {
        this.mode = mode;
    }
}