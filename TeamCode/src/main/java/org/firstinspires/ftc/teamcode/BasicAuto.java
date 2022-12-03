package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
@Disabled
public class BasicAuto extends CameraShortcut {

    @Override
    public void runOpMode() {
        initCamera();
        autoInit();
        waitForStart();
        //if you want to default a position set it equal to signalPosition.One, signalPosition.Two, signalPosition.Three instead of getSignalPosition
        signalPosition position = getSignalPosition();
        telemetry.addData("Position", position);
        telemetry.update();

        //write cone scoring code here






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
        drive(.3, -1, 1000);
        drive(.5, 1, 1500);
    }
    public void zoneTwoPath() {
        
        drive(.5, 1, 1500);
    }
    public void zoneOnePath() {
        strafe(.5, 1, 1500);
        drive(.3, -1, 1000);
        drive(.5, 1, 1500);
    }
}
