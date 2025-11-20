package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name="TeleOp")
public class HiveMindTeleOp extends LinearOpMode {
    Hardware robot;

    Gamepad main;
    static final int MOTOR_TICK_COUNTS = 5377; // Is 10x actual number because ints can't be a decimal...

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Hardware(hardwareMap);
        main = new Gamepad();

        // code to change to field-centric drive
        // robot.setMode(Hardware.RunMode.FIELD_CENTRIC);

        waitForStart();
        double diameter = 3.7;
        double circumference = diameter * 3.14159265;
        driveDistance(circumference, 24, 0.5);
        while (opModeIsActive() && !isStopRequested()) {
            main.copy(gamepad1);

            double y = -main.left_stick_y;
            double x = main.left_stick_x;
            double rx = main.right_stick_x;

            robot.drive(x, y, rx);
        }
    }
    private void resetEncoder(){
        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    private void setTargetPosition(int encoderDrivingTarget){
        robot.frontLeft.setTargetPosition(encoderDrivingTarget);
        robot.frontRight.setTargetPosition(encoderDrivingTarget);
        robot.backLeft.setTargetPosition(encoderDrivingTarget);
        robot.backRight.setTargetPosition(encoderDrivingTarget);
    }
    private void isDrivingSetAmount(){
        while (robot.backLeft.isBusy() || robot.backRight.isBusy() || robot.frontLeft.isBusy() || robot.frontRight.isBusy()){
            boolean isTrue = true;
        }
    }
    private void runToPosition(){
        robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        isDrivingSetAmount();
    }
    public void driveDistance(double circumference, double distance, double power){
        resetEncoder();
        double requiredRotations = distance / circumference;
        int encoderDrivingTarget = (int)(requiredRotations*MOTOR_TICK_COUNTS/10);
        setTargetPosition(encoderDrivingTarget);
        if (distance > 0) {
            robot.setMotorPowers(power / 100);
        }
        else if (distance < 0){
            robot.setMotorPowers(-1*power/100);
        }
        runToPosition();
        robot.setMotorPowers(0);
    }
}
