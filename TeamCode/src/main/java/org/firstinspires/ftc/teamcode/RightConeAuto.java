package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class RightConeAuto extends CameraShortcut {

    @Override
    public void runOpMode() {
        initCamera();
        complexAutoInit();
        telemetry.addData("Ready", "Ready");
        while(!isStarted()) {
            if (gamepad2.dpad_down && !isLastHit) {
                isSecondCone = true;
                isLastHit = true;
            } else if (gamepad2.dpad_up && !isLastHit) {
                isSecondCone = false;
                isLastHit = true;
            } else {
                isLastHit = false;
            }
            telemetry.addData("isSecondCone", isSecondCone);
            telemetry.update();
        }
        //waitForStart();
        //if you want to default a position set it equal to signalPosition.One, signalPosition.Two, signalPosition.Three instead of getSignalPosition
        signalPosition position = getSignalPosition();
        telemetry.addData("Position", position);
        telemetry.update();

        //write cone scoring code here

        //strafe right
        front_Left.setPower(.3);
        back_Right.setPower(.3);
        front_Right.setPower(-.6);
        back_Leftx.setPower(-.6);
        sleep(400);
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
        sleep(2200);
        front_Left.setPower(0);
        back_Right.setPower(0);
        front_Right.setPower(0);
        back_Leftx.setPower(0);
        //bring down wrist and raise the elevator and bring the arm up

        sleep(2000);
        //turn left
        front_Right.setPower(.2);
        back_Right.setPower(.2);
        sleep(1300);
        front_Right.setPower(0);
        back_Right.setPower(0);
        sleep(1000);
        //lower and then drop the cone, then bring the elevator back up

        sleep(500);

        sleep(300);

        sleep(500);
        //turn right
        front_Right.setPower(-.2);
        back_Right.setPower(-.2);
        sleep(1250);
        front_Right.setPower(0);
        back_Right.setPower(0);
        sleep(1000);
        //reset the arm, wrist and elevator

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
