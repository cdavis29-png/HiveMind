package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Gamepad;

import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Autonomous")
public class Auto extends General
{
    boolean isRed = false;
    Hardware robot;

    Gamepad main;
    ElapsedTime autoTimer = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {

        robot = new Hardware(hardwareMap);
        main = new Gamepad();
        // code to change to field-centric drive
        // robot.setMode(Hardware.RunMode.FIELD_CENTRIC);

        waitForStart();
        autoTimer.reset();
        startIntake(1);
        driveToLoadingZone();
        Thread.sleep(loadTime);
        while (autoTimer.time() < 30){
            driveBackFromLoadingZone();
            driveToTheGoal();
            shoot3times();
            driveBackFromGoal();
            driveToLoadingZone();
            Thread.sleep(loadTime);
        }
        stopIntake();
        autoTimer.reset();
    }
    public void shoot() {
        turnFlyWheel(true, 1);
    }
    public void shoot3times() {
        shoot();
        shoot();
        shoot();
    }
    public void driveBackFromGoal() {
        driveDistance(-118.8, 1);
        if(isRed){
            turnSetDegrees(-45, 1);
        }
        else{
            turnSetDegrees(45, 1);
        driveDistance(-6, 1);
        }
    }
    public void driveToLoadingZone(){
        driveDistance(-24, 1);
        if(isRed) {
            turnSetDegrees(-90, 1);
        }
        else {
            turnSetDegrees(90, 1);
        }
        driveDistance(30, 1);
    }

    public void driveBackFromLoadingZone() {
        driveDistance(-30, 1);
        if(isRed) {
            turnSetDegrees(90, 1);
        }
        else {
            turnSetDegrees(-90, 1);
        }
        driveDistance(24, 1);
    }

    public void driveToTheGoal(){
        driveDistance(6, 1);
        if(isRed){
            turnSetDegrees(45, 1);
        }
        else{
            turnSetDegrees(-45, 1);
        }
        driveDistance(118.8, 1);
    }
}