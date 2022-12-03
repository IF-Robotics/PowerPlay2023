package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class ConeAuto extends CameraShortcut {

    @Override
    public void runOpMode() {
        initCamera();
        autoInit();
        telemetry.addData("Ready", "Ready");
        waitForStart();
        //if you want to default a position set it equal to signalPosition.One, signalPosition.Two, signalPosition.Three instead of getSignalPosition
        signalPosition position = getSignalPosition();
        telemetry.addData("Position", position);
        telemetry.update();

        //write cone scoring code here

            //strafe Left
                front_Left.setPower(.3);
                back_Right.setPower(.3);
                front_Right.setPower(-.6);
                back_Leftx.setPower(-.6);
                sleep(380);
                front_Left.setPower(0);
                back_Right.setPower(0);
                front_Right.setPower(0);
                back_Leftx.setPower(0);
                sleep(400);
            //go forward
                front_Left.setPower(.4);
                back_Right.setPower(.4);
                front_Right.setPower(.4);
                back_Leftx.setPower(.4);
                sleep(2100);
                front_Left.setPower(0);
                back_Right.setPower(0);
                front_Right.setPower(0);
                back_Leftx.setPower(0);
            //bring down wrist and raise the elevator and bring the arm up
                wrist.setPosition(.39);
                wrist2.setPosition(.61);
                arm.setTargetPosition(500);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                arm.setPower(0.2);
                elevate_Right.setTargetPosition(1630);
                elevate_Left.setTargetPosition(1630);
                elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                elevate_Right.setPower(0.8);
                elevate_Left.setPower(0.8);
                sleep(2000);
            //turn left
                front_Right.setPower(.2);
                back_Right.setPower(.2);
                sleep(1280);
                front_Right.setPower(0);
                back_Right.setPower(0);
                sleep(1000);
            //lower and then drop the cone, then bring the elevator back up
                elevate_Right.setTargetPosition(1230);
                elevate_Left.setTargetPosition(1230);
                elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                elevate_Right.setPower(1);
                elevate_Left.setPower(1);
                sleep(500);
                claw.setPosition(.93);
                sleep(300);
                elevate_Right.setTargetPosition(1630);
                elevate_Left.setTargetPosition(1630);
                elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                elevate_Right.setPower(1);
                elevate_Left.setPower(1);
                sleep(500);
            //turn right
                front_Right.setPower(-.2);
                back_Right.setPower(-.2);
                sleep(1350);
                front_Right.setPower(0);
                back_Right.setPower(0);
                sleep(1000);
            //reset the arm, wrist and elevator
                elevate_Right.setTargetPosition(20);
                elevate_Left.setTargetPosition(20);
                elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                elevate_Right.setPower(0.9);
                elevate_Left.setPower(0.9);
                arm.setTargetPosition(10);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                arm.setPower(0.2);
                wrist.setPosition(1);
                wrist2.setPosition(0);
                claw.setPosition(.69);
                sleep(1000);
            //go forward
                front_Left.setPower(.4);
                back_Right.setPower(.4);
                front_Right.setPower(.4);
                back_Leftx.setPower(.4);
                sleep(450);
                front_Left.setPower(0);
                back_Right.setPower(0);
                front_Right.setPower(0);
                back_Leftx.setPower(0);
                sleep(300);



        switch(position) {
            case One:
                zoneOnePath();
                break;
            case Two:
                zoneTwoPath();
                break;
            case Three:
                zoneThreePath();
                break;
            default:
                zoneOnePath();
        }
    }

    //these go to the positions, so you change these values to whatever you want
    public void zoneThreePath() {
        strafe(.5, -1, 1500);

    }
    public void zoneTwoPath() {
        

    }
    public void zoneOnePath() {
        strafe(.5, 1, 1500);

    }
}
