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

        telemetry.addData("done", 2);
        telemetry.update();
//        SignalDetect.Largest position = aPipe.getColor();

//        waitForStart();
        while(!isStarted()) {

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
