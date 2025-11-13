package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name="TeleOp")
public class HiveMindTeleOp extends LinearOpMode {
    Hardware robot;

    Gamepad main;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Hardware(hardwareMap);
        main = new Gamepad();

        // code to change to field-centric drive
        // robot.setMode(Hardware.RunMode.FIELD_CENTRIC);

        waitForStart();
        double ratio = 19.2;
        double rpm = 312;
        double diameter = 18;
        double motorSpeed = getMotorSpeed(ratio, rpm, diameter, 0);
        driveDistance(24, motorSpeed, 50);
        while (opModeIsActive() && !isStopRequested()) {
            main.copy(gamepad1);

            double y = -main.left_stick_y;
            double x = main.left_stick_x;
            double rx = main.right_stick_x;

            robot.drive(x, y, rx);
        }
    }
    private double getMotorSpeed(double ratio, double rpm, double diameter, double motorSpeed){
        motorSpeed = (ratio * rpm * diameter * 3.1415926535)/60;
        return motorSpeed;
    }
    public void driveDistance(double distance, double motorSpeed, double speed){
        double startTime = System.currentTimeMillis();
        double driveTime = ((Math.abs(distance)) / motorSpeed)*(1 / (0.01 * speed));
        if (distance < 0) {
            robot.setMotorPowers(-1*speed/100);
        }
        else{
            robot.setMotorPowers(1*speed/100);
        }
        while ((System.currentTimeMillis() - startTime) < driveTime){
            boolean isMoving = true;
        }
        robot.setMotorPowers(0);
    }
}
