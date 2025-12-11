package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="TeleOp")
public class General extends LinearOpMode {
    Hardware robot;
    ElapsedTime flyWheelTimer = new ElapsedTime();
    double diameter = 3.7;
    double circumference = diameter * 3.14159265;
    Gamepad main;
    static final int MOTOR_TICK_COUNTS = 538; // Encoder ticks per second, actual value is 537.7
    double singleShotTime = 0; // Change this once we figure out the actual time
    long loadTime = 100; // Change this once we figure out the actual time
    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Hardware(hardwareMap);
        main = new Gamepad();


        // code to change to field-centric drive
        // robot.setMode(Hardware.RunMode.FIELD_CENTRIC);
    }

    private void resetEncoder() {
        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void setTargetPosition(int encoderDrivingTarget) {
        robot.frontLeft.setTargetPosition(encoderDrivingTarget);
        robot.frontRight.setTargetPosition(encoderDrivingTarget);
        robot.backLeft.setTargetPosition(encoderDrivingTarget);
        robot.backRight.setTargetPosition(encoderDrivingTarget);
    }

    private void isDrivingSetAmount() {
        while (robot.backLeft.isBusy() || robot.backRight.isBusy() || robot.frontLeft.isBusy() || robot.frontRight.isBusy()) {
            boolean isTrue = true;
        }
    }

    private void setDrivetrainMode(DcMotor.RunMode mode) {
        robot.frontLeft.setMode(mode);
        robot.frontRight.setMode(mode);
        robot.backLeft.setMode(mode);
        robot.backRight.setMode(mode);
    }

    public void driveDistance(double circumference, double distance, double power) {
        resetEncoder();

        double requiredRotations = distance / circumference;
        int encoderDrivingTarget = (int) (requiredRotations * MOTOR_TICK_COUNTS);

        setTargetPosition(encoderDrivingTarget);
        setDrivetrainMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.setMotorPowers(power / 100);

        isDrivingSetAmount();

        robot.setMotorPowers(0);
        setDrivetrainMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void turnFlyWheel(boolean right, double power){
        if (right){
            robot.flywheel1.setPower(1*power/100);
            robot.flywheel2.setPower(-1*power/800);

        }
        else{
            robot.flywheel1.setPower(-1*power/100);
            robot.flywheel2.setPower(-1*power/800);
        }
        flyWheelTimer.reset();
        while (flyWheelTimer.time() < singleShotTime){
            boolean isTrue = true;
        }
        flyWheelTimer.reset();
        robot.flywheel1.setPower(0);
        robot.flywheel2.setPower(0);
    }

    public void startIntake(double power) {
        robot.intake1.setPower(power/100);
        robot.intake2.setPower(power/100);
    }
    public void stopIntake(){
        robot.intake1.setPower(0);
        robot.intake2.setPower(0);
    }
    public void turnSetDegrees(double degrees, double power){
        resetEncoder();
        int encoderTurningTarget = (int)(degrees*MOTOR_TICK_COUNTS/360); // Degrees to turn

        robot.frontLeft.setTargetPosition(-encoderTurningTarget);
        robot.frontRight.setTargetPosition(encoderTurningTarget);
        robot.backLeft.setTargetPosition(-encoderTurningTarget);
        robot.backRight.setTargetPosition(encoderTurningTarget);

        setDrivetrainMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.setMotorPowers(power);
        isDrivingSetAmount();

        robot.setMotorPowers(0);
        setDrivetrainMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}