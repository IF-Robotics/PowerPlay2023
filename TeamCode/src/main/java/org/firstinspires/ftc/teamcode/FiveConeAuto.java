package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

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

        telemetry.update();
        elevate_Left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevate_Right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevate_Right.setDirection(DcMotorSimple.Direction.FORWARD);
        elevate_Left.setDirection(DcMotorSimple.Direction.REVERSE);
        back_Leftx.setDirection(DcMotorSimple.Direction.REVERSE);
        front_Left.setDirection(DcMotorSimple.Direction.REVERSE);
        back_Right.setDirection(DcMotorSimple.Direction.FORWARD);
        front_Right.setDirection(DcMotorSimple.Direction.FORWARD);
        front_Left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_Leftx.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        front_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        front_Left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_Right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_Leftx.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_Right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        wrist.setPosition(1);
        wrist2.setPosition(0);
        tail.setPosition(.44);
        flip.setPosition(0.25);
        claw.setPosition(.59);
        int stackHeight = 400;
        ElapsedTime deceleration = new ElapsedTime();

        waitForStart();
        while(!isStarted()) {
            position = aPipe.getColor();
            telemetry.addData("position", position);
            telemetry.update();
        }


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
        sleep(600);
    //forward
        front_Left.setPower(.4);
        back_Right.setPower(.4);
        front_Right.setPower(.4);
        back_Leftx.setPower(.4);
        sleep(1460);
    //slow down
        deceleration.reset();
        while (deceleration.seconds() < 1.2) {
            front_Left.setPower(.4 - deceleration.seconds()/3);
            back_Right.setPower(.4 - deceleration.seconds()/3);
            front_Right.setPower(.4 - deceleration.seconds()/3);
            back_Leftx.setPower(.4- deceleration.seconds()/3);
            telemetry.addData("deceleration", deceleration);
            telemetry.update();
        }
        front_Left.setPower(0);
        back_Right.setPower(0);
        front_Right.setPower(0);
        back_Leftx.setPower(0);
    //turn Right
        back_Leftx.setPower(.4);
        front_Left.setPower(.4);
        sleep(1850);
    //backwards/forwards

        deceleration.reset();
        while (deceleration.seconds() < .2) {
            front_Left.setPower(.2);
            back_Right.setPower(.2);
            front_Right.setPower(.2);
            back_Leftx.setPower(.2);

            telemetry.addData("deceleration", deceleration);
            telemetry.update();
        }
        front_Left.setPower(0);
        back_Right.setPower(0);
        front_Right.setPower(0);
        back_Leftx.setPower(0);

        stackHeight = 400;
        for (int i=1; i < 6; i++) {//put down the wrist, open the claw, move the tail, and lift the elevator
            preset(stackHeight, 1, .3, .7, .3, .96, 50, .5, .52);
            sleep(2000);
            //reach up and over
            claw.setPosition(.69);
            sleep(200);
            preset(1700, 1, .3, .43, .57, .69, 1500, .35, .52);
            sleep(500);
            flip.setPosition(1);
            tail.setPosition(.72);
            sleep(2000);
            claw.setPosition(.96);
            stackHeight-=80;
            sleep(1000);
        }

        if(position == 1) {
            zoneOnePath();
        } else if (position == 2) {
            zoneTwoPath();
        } else {
            zoneThreePath();
        }
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
    public void preset(int elevatePosition, double elevatePower, double flipPosition, double wristPosition, double wrist2Position, double clawPosition, int armPosition, double armPower, double tailPosition) {
        elevate_Right.setTargetPosition(elevatePosition);
        elevate_Left.setTargetPosition(elevatePosition);
        elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevate_Left.setPower(elevatePower);
        elevate_Right.setPower(elevatePower);
        flip.setPosition(flipPosition);
        wrist.setPosition(wristPosition);
        wrist2.setPosition(wrist2Position);
        claw.setPosition(clawPosition);
        arm.setTargetPosition(armPosition);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(armPower);
        tail.setPosition(tailPosition);
    }
}
