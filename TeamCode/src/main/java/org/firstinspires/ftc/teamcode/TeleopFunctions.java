package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class TeleopFunctions extends Hardwaremap{
    public double strafeDriveTrainPower;
    public double forwardDriveTrainPower;
    public float Left_Stick_X;
    public float left_stick_y;
    public float Right_Stick_Y;
    public int elevate_brake_L;
    public int elevate_brake_R;
    public boolean reverse = false;

    public int campingMode = 1;
    public boolean clawStatus = false;
    public boolean wristStatus = false;
    public int armMode = 0;
    public int stackHeight = 400;
    public int stackOneClick = 0;
    public int modeOneSwitch = 0;
    public int clawOneClick = 0;
    public int wristOneClick = 0;
    public boolean isElevatorUsed = false;
    public boolean isSoftStop = false;
    public ElapsedTime softStopTimer = new ElapsedTime();
    public boolean isSoftStopReset = true;
    public double softStopTime = 0;
    public double softStopClawPosition = -1;
    public double softStopFlipPosition = -1;
    public int softStopElevatePosition = 90;
    public ElapsedTime clawTimer = new ElapsedTime();
    public ElapsedTime tailWait = new ElapsedTime();

    public void preset(int elevatePosition, double elevatePower, double flipPosition, double wristPosition, double wrist2Position, double clawPosition, int armPosition, double armPower) {
        elevate_brake_R = elevatePosition;
        elevate_brake_L = elevatePosition;
        elevate_Right.setTargetPosition(elevatePosition);
        elevate_Left.setTargetPosition(elevatePosition);
        elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevate_Left.setPower(elevatePower);
        elevate_Right.setPower(elevatePower);
        flip.setPosition(flipPosition);
        wrist.setPosition(wristPosition);
        //wrist2.setPosition(wrist2Position);
        claw.setPosition(clawPosition);
        arm.setTargetPosition(armPosition);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(armPower);
    }

    public void hold() {
        elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevate_Left.setTargetPosition(elevate_brake_L);
        elevate_Right.setTargetPosition(elevate_brake_R);
        elevate_Left.setPower(1);
        elevate_Right.setPower(1);
    }

    public void manualElevate(double elevatePower) {
//        elevate_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        elevate_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        elevate_brake_R = elevate_Left.getCurrentPosition();
//        elevate_brake_L = elevate_Right.getCurrentPosition();
//        elevate_Left.setPower(elevatePower * gamepad2.left_stick_y);
//        elevate_Right.setPower(elevatePower * gamepad2.left_stick_y);
        elevate_brake_R = elevate_brake_R + (int) (10 /* elevatePower*/ * gamepad2.left_stick_y);
        elevate_brake_L = elevate_brake_L + (int) (10 /* elevatePower*/ * gamepad2.left_stick_y);
        elevatePosition(elevate_brake_L, elevate_brake_R, 1);
    }
    public void elevatePosition (int leftPosition, int rightPosition, double power) {
        elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevate_Left.setTargetPosition(leftPosition);
        elevate_Right.setTargetPosition(rightPosition);
        elevate_Left.setPower(power);
        elevate_Right.setPower(power);
    }

    public void elevateSetRunToPosition() {
        elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void elevateSetRunWithoutEncoder() {
        elevate_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevate_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void elevatePower(double power) {
        elevateSetRunWithoutEncoder();
        elevate_Left.setPower(power);
        elevate_Right.setPower(power);
    }

    public void wristDown() {
        isElevatorUsed = true;
        flip.setPosition(.3);
        tail.setPosition(.53);
        wrist.setPosition(0.69);
        //wrist2.setPosition(0.39);
        elevate_Right.setTargetPosition(90);
        elevate_Left.setTargetPosition(90);
        elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevate_Left.setPower(0.2);
        elevate_Right.setPower(0.2);
        elevate_brake_R = 90;
        elevate_brake_L = 90;
        wristStatus = false;
    }

    public void reset() {
        isElevatorUsed = true;
        elevate_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevate_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevate_Left.setPower(-0.7);
        elevate_Right.setPower(-0.7);
        sleep(300);
        while (((DcMotorEx) elevate_Left).getVelocity() <= -200 && opModeIsActive()) {
            telemetry.addData("velocity", ((DcMotorEx) back_Leftx).getVelocity());
            telemetry.update();
        }
        elevate_Left.setPower(.1);
        elevate_Right.setPower(.1);
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm.setPower(-1);
        sleep(300);
        while (((DcMotorEx) arm).getVelocity() < -200 && opModeIsActive()) {
            telemetry.addData("velocity", ((DcMotorEx) arm).getVelocity());
            telemetry.update();
        }
        elevate_Left.setPower(0);
        elevate_Right.setPower(0);
        elevate_Left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevate_Right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(.2);
        sleep(30);
        arm.setPower(0);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setTargetPosition(40);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(.5);
    }

    public void softStopOn(SoftStopBehavior behavior, double time) {
        isSoftStop = true;
        isSoftStopReset = false;
        softStopBehavior = behavior;
        softStopTime = time;
        softStopTimer.reset();
    }

    //don't use bc glitch with claw position
    public void softStopOn(SoftStopBehavior behavior, double time, double clawPosition, double flipPosition, int elevatePosition) {
        softStopOn(behavior, time);
        softStopClawPosition = clawPosition;
        softStopFlipPosition = flipPosition;
        softStopElevatePosition = elevatePosition;
        softStopTimer.reset();
    }

    public void softStopOff() {
        if(softStopBehavior == SoftStopBehavior.Open) {
            claw.setPosition(.93);
            isElevatorUsed = false;
        } else if(softStopBehavior == SoftStopBehavior.Down_And_Open) {
            if(softStopClawPosition == -1) {
                claw.setPosition(.93);
                isElevatorUsed = true;
                preset(softStopElevatePosition, 1, softStopFlipPosition, .66, .39, .93, 3, .5);
            } else {
                isElevatorUsed = true;
                preset(softStopElevatePosition, 1, softStopFlipPosition, .66, .39, softStopClawPosition, 3, .5);
            }
        } else if(softStopBehavior == softStopBehavior.Down) {
            //claw.setPosition(.61);
        } else {
            telemetry.addLine("Error: SoftStop has no mode");
        }
        softStopClawPosition = -1;
        softStopFlipPosition = .3;
        softStopElevatePosition = 90;
        elevateSetRunToPosition();
        //timer.reset();
        isSoftStop = false;
        isSoftStopReset = true;
    }
    public void move(double forward, double spin) {
        front_Left.setPower(-(forward+spin));
        back_Leftx.setPower(-(forward+spin));
        front_Right.setPower(-(forward-spin));
        back_Right.setPower(-(forward-spin));
    }
}
