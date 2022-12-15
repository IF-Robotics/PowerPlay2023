package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class FiveConeAuto extends OpenCVAuto {


    @Override
    public void runOpMode() {


        telemetry.addData("Waiting...", 1);
        telemetry.update();
        autoInit();
        telemetry.addData("done", 2);
        telemetry.update();

        int position = aPipe.getColor();

        waitForStart();
        while(!isStarted()) {
            position = aPipe.getColor();
            telemetry.addData("position", position);
            telemetry.update();
        }
        telemetry.update();
        back_Leftx.setDirection(DcMotorSimple.Direction.REVERSE);
        front_Left.setDirection(DcMotorSimple.Direction.REVERSE);
        back_Right.setDirection(DcMotorSimple.Direction.FORWARD);
        front_Right.setDirection(DcMotorSimple.Direction.FORWARD);
        front_Left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_Leftx.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        front_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    //strafe to the right
        front_Left.setPower(.3);
        back_Right.setPower(.3);
        front_Right.setPower(-.6);
        back_Leftx.setPower(-.6);
        sleep(500);
        front_Left.setPower(0);
        back_Right.setPower(0);
        front_Right.setPower(0);
        back_Leftx.setPower(0);
        sleep(400);
    //forward
        front_Left.setPower(.4);
        back_Right.setPower(.4);
        front_Right.setPower(.4);
        back_Leftx.setPower(.4);
        sleep(2500);
        front_Left.setPower(0);
        back_Right.setPower(0);
        front_Right.setPower(0);
        back_Leftx.setPower(0);
    //turn Right
        back_Leftx.setPower(.4);
        front_Left.setPower(.4);
        sleep(1500);
    }
    public void zoneThreePath() {
        strafe(.5, -1, 1100);
        drive(.3, -1, 2500);
        drive(.5, 1, 1500);
    }
    public void zoneTwoPath() {

        drive(.5, 1, 1500);
    }
    public void zoneOnePath() {
        strafe(.5, 1, 1100);
        drive(.3, -1, 2500);
        drive(.5, 1, 1500);
    }
}
