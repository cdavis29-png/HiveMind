package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="TeleOp")
public class HiveMindTeleOp extends General {
    Hardware robot;
    ElapsedTime teleOpTimer = new ElapsedTime();
    Gamepad main;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Hardware(hardwareMap);
        main = new Gamepad();


        // code to change to field-centric drive
        // robot.setMode(Hardware.RunMode.FIELD_CENTRIC);

        waitForStart();
        startIntake(1);
        teleOpTimer.reset();


        while (opModeIsActive() && !isStopRequested() && (teleOpTimer.time() < 120)) { // Regular driving code
            main.copy(gamepad1);

            double y = -main.left_stick_y;
            double x = main.left_stick_x;
            double rx = main.right_stick_x;

            robot.drive(x, y, rx);
        }
        teleOpTimer.reset();
        stopIntake();
    }
}