package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "use this")
public class outreachrobot extends TeleopFunctions {
    public enum ArmMode {
        Stack,
        Moving,
        Camping
    }

    double moveX;
    double moveY;
    double rotate;
    boolean fieldCentric;

    @Override
    public void runOpMode() throws InterruptedException{
        teleopInit();
        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(new BNO055IMU.Parameters());

        // Drivetrain
        strafeDriveTrainPower = .7;
        forwardDriveTrainPower = 1;
        Left_Stick_X = 0;
        left_stick_y = 0;
        Right_Stick_Y = 0;

        elevate_brake_L = 220;
        elevate_brake_R = 220;

        boolean claws = true;

        stackHeight = 400;
        MoveClass moveClass = new MoveClass(front_Left, back_Leftx, front_Right, back_Right);
        //start
        waitForStart();
        //wristDown();
        //reset();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                moveX = 0;
                moveY = 0;
                rotate = 0;
                isElevatorUsed = false;
                // drive train

                telemetry.addData("reverse", reverse);

                Left_Stick_X = gamepad1.left_stick_x;
                int direction = 1;
                if (reverse == false) {
                    direction = 1;
                    left_stick_y = gamepad1.left_stick_y;
                    Right_Stick_Y = gamepad1.right_stick_y;
                } else if (reverse == true) {
                    direction = -1;
                    left_stick_y = gamepad1.right_stick_y;
                    Right_Stick_Y = gamepad1.left_stick_y;
                }


                double xPower = 0;
                double spin = 0;

                    front_Left.setPower(direction * (strafeDriveTrainPower * -1 * Left_Stick_X + forwardDriveTrainPower * left_stick_y));
                    back_Leftx.setPower(direction * (strafeDriveTrainPower * 1 * Left_Stick_X + forwardDriveTrainPower * left_stick_y));
                    front_Right.setPower(direction * (strafeDriveTrainPower * 1 * Left_Stick_X + forwardDriveTrainPower * Right_Stick_Y));
                    back_Right.setPower(direction * (strafeDriveTrainPower * -1 * Left_Stick_X + forwardDriveTrainPower * Right_Stick_Y));


                if (gamepad1.dpad_down) {
                    //reverse mode
                    reverse = true;
                } else if (gamepad1.dpad_up) {
                    //normal mode
                    reverse = false;
                } else if (gamepad1.dpad_right) {
                    //rightCampingMode
                    campingMode = 1;
                } else if (gamepad1.dpad_left) {
                    //leftSideCamping
                    campingMode = 0;
                }

                if (gamepad1.right_bumper) {
                    strafeDriveTrainPower = 0.3;
                    forwardDriveTrainPower = .2;
                } else if (gamepad1.left_bumper) {
                    strafeDriveTrainPower = 1;
                    forwardDriveTrainPower = 1;
                } else {
                    strafeDriveTrainPower = .5;
                    forwardDriveTrainPower = .4;
                }

                /*
                double y = -gamepad1.left_stick_y; // Remember, this is reversed!
                double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
                double rx = (gamepad1.right_stick_x * -1);

                // Read inverse IMU heading, as the IMU heading is CW positive
                double botHeading = -imu.getAngularOrientation().firstAngle;

                double rotX = x * Math.cos(botHeading) - y * Math.sin(botHeading);
                double rotY = x * Math.sin(botHeading) + y * Math.cos(botHeading);

                // Denominator is the largest motor power (absolute value) or 1
                // This ensures all the powers maintain the same ratio, but only when
                // at least one is out of the range [-1, 1]
                double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
                double frontLeftPower = driveTrainPower * (rotY + rotX + rx) / denominator;
                double backLeftPower = driveTrainPower * (rotY - rotX + rx) / denominator;
                double frontRightPower = driveTrainPower * (rotY - rotX - rx) / denominator;
                double backRightPower = driveTrainPower * (rotY + rotX - rx) / denominator;

                front_Left.setPower(frontLeftPower);
                back_Leftx.setPower(backLeftPower);
                front_Right.setPower(frontRightPower);
                back_Right.setPower(backRightPower);

                if (gamepad1.right_bumper) {
                    driveTrainPower = .35;
                }    else if (gamepad1.left_bumper){
                    driveTrainPower = 1;
                } else {
                    driveTrainPower = .7;
                }

*/

                if(gamepad1.right_trigger>.1){
                    intake.setPower(1);
                } else{
                    intake.setPower(0);
                }
                if(gamepad1.left_trigger>.1){
                    shooter.setPower(1);
                    sleep(2000);
                    launcher.setPosition(.5);
                    sleep(2000);
                    launcher.setPosition(.2);
                    shooter.setPower(0);
                }

                if (moveX != 0 || moveY != 0 || rotate != 0)
                    moveClass.driveRobotCentric(moveX, moveY, rotate, strafeDriveTrainPower);

                // telemetry
                telemetry.addData("reverse", reverse);
                telemetry.addData("Left Joystick", gamepad2.left_stick_y);
                telemetry.addData("armMode", armMode);

                telemetry.addData("odopody", front_Right.getCurrentPosition());
                telemetry.addData("back_Left", back_Leftx.getCurrentPosition());
                telemetry.addData("isSoftStop", isSoftStop);
                telemetry.addData("Timer", softStopTimer.seconds());
                telemetry.addData("!ElevatorUsed", !isElevatorUsed);
                telemetry.addData("isSoftStopReset", isSoftStopReset);

                telemetry.addData("softStopTime", softStopTime);

                telemetry.update();

            }
        }
    }

    public void stopMotors() {
        front_Left.setPower(0);
        back_Leftx.setPower(0);
        front_Right.setPower(0);
        back_Right.setPower(0);
    }
}

