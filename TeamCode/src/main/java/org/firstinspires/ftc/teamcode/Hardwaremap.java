package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class Hardwaremap extends LinearOpMode {

    //variables
    public DcMotor back_Right;
    public DcMotor front_Right;
    public DcMotor front_Left;
    public DcMotor back_Leftx;
    public DcMotor elevate_Left;
    public DcMotor elevate_Right;
    public DcMotor arm;
    public Servo flip;
    public Servo wrist;
    public Servo wrist2;
    public Servo claw;
    public LED leftGreen;
    public LED leftRed;
    public LED rightGreen;
    public LED rightRed;
    public DistanceSensor distance;

    public Gamepad currentGamepad2 = new Gamepad();
    public Gamepad previousGamepad2 = new Gamepad();

    public void teleopInit() {
        startInit();
        back_Leftx.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_Leftx.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); //reset the motor to running w/o encoder after resetting the encoder so that it can be run again

        // Elevate
        elevate_Left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevate_Right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevate_Left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevate_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevate_Right.setDirection(DcMotorSimple.Direction.FORWARD);
        elevate_Left.setDirection(DcMotorSimple.Direction.REVERSE);
        elevate_Right.setTargetPosition(220);
        elevate_Left.setTargetPosition(220);
        elevate_Right.setPower(0.7);
        elevate_Left.setPower(0.7);
        elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Accessories
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setTargetPosition(0);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(1);
        flip.setPosition(0.3);
        wrist.setPosition(0.95);
        wrist2.setPosition(0);
        claw.setPosition(0.7);
    }

    public void autoInit() {
        startInit();
    }

    private void startInit() {
        //hardwaremap
        back_Right = hardwareMap.get(DcMotor.class, "back_Right");
        front_Right = hardwareMap.get(DcMotor.class, "front_Right");
        front_Left = hardwareMap.get(DcMotor.class, "front_Left");
        //front left has y axis dead wheel on it
        back_Leftx = hardwareMap.get(DcMotor.class, "back_Left");
        elevate_Left = hardwareMap.get(DcMotor.class, "elevate_Left");
        elevate_Right = hardwareMap.get(DcMotor.class, "elevate_Right");
        arm = hardwareMap.get(DcMotor.class, "arm");
        flip = hardwareMap.get(Servo.class, "flip");
        wrist = hardwareMap.get(Servo.class, "wrist");
        wrist2 = hardwareMap.get(Servo.class, "wrist2");
        claw = hardwareMap.get(Servo.class, "claw");
        leftGreen = hardwareMap.get(LED.class, "leftGreen");
        leftRed = hardwareMap.get(LED.class, "leftRed");
        rightGreen = hardwareMap.get(LED.class, "rightGreen");
        rightRed = hardwareMap.get(LED.class, "rightRed");
        distance = hardwareMap.get(DistanceSensor.class, "distance");

        //directions
        back_Right.setDirection(DcMotorSimple.Direction.REVERSE);
        front_Right.setDirection(DcMotorSimple.Direction.REVERSE);

        //zeropowerbehavior
        front_Left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_Right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_Leftx.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_Right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}
