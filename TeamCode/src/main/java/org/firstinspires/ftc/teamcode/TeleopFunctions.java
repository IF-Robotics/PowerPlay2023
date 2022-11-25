package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class TeleopFunctions extends Hardwaremap{
    public double driveTrainPower;
    public float Left_Stick_X;
    public float left_stick_y;
    public float Right_Stick_Y;
    public int elevate_brake_L;
    public int elevate_brake_R;
    public boolean reverse = false;

    public boolean clawStatus = false;
    public boolean wristStatus = false;
    public int armMode = 0;
    public int stackHeight = 500;
    public int stackOneClick = 0;
    //public int modeOneSwitch = 0;
    public OneClick modeSwitch = new OneClick(()-> gamepad2.dpad_right);
    public int clawOneClick = 0;
    public int wristOneClick = 0;

    public enum ArmMode {
        Stack,
        Moving,
        Camping
    }

    public void preset(int elevatePosition, double elevatePower, double flipPosition, double wristPosition, double wrist2Position, double clawPosition, int armPosition, double armPower) {
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
        elevate_brake_R = elevatePosition;
        elevate_brake_L = elevatePosition;
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
        elevate_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevate_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevate_brake_R = elevate_Left.getCurrentPosition();
        elevate_brake_L = elevate_Right.getCurrentPosition();
        elevate_Left.setPower(elevatePower * gamepad2.left_stick_y);
        elevate_Right.setPower(elevatePower * gamepad2.left_stick_y);
    }
    public void elevatePosition (int leftPosition, int rightPosition, double power) {
        elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevate_Left.setTargetPosition(leftPosition);
        elevate_Right.setTargetPosition(rightPosition);
        elevate_Left.setPower(power);
        elevate_Right.setPower(power);
    }
}
