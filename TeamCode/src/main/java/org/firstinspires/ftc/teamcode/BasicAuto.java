package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
@Autonomous
public class BasicAuto extends OpenCVAuto {

    @Override
    public void runOpMode() {
        //initCamera();
        telemetry.addData("Waiting...", 1);
        telemetry.update();
        autoInit();
        telemetry.addData("done", 2);
        telemetry.update();
//        SignalDetect.Largest position = aPipe.getColor();
        int position = aPipe.getColor();
//        waitForStart();
        while(!isStarted()) {
            position = aPipe.getColor();
            telemetry.addData("position", position);
            telemetry.update();
        }

//        switch(position) {
//            case one:
//                zoneOnePath();
//                break;
//            case two:
//                zoneTwoPath();
//                break;
//            case three:
//                zoneThreePath();
//                break;
//            default:
//                zoneOnePath();
//        }

        if(position == 1) {
            zoneOnePath();
        } else if (position == 2) {
            zoneTwoPath();
        } else{
            zoneThreePath();
        }
        //zoneOnePath();
    }

    public void zoneThreePath() {
        strafe(.5, -1, 1500);
        drive(.6, -1, 700);
        drive(.5, 1, 1500);
    }
    public void zoneTwoPath() {
        drive(.5, -1, 300);
        drive(.5, 1, 1500);
    }
    public void zoneOnePath() {
        strafe(.5, 1, 1300);
        drive(.6, -1, 700);
        drive(.5, 1, 1500);
    }
}
