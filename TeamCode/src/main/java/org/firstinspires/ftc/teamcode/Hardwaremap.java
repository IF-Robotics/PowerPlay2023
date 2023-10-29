package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public abstract class Hardwaremap extends LinearOpMode {

    //variables
    public DcMotor back_Right;
    public DcMotor front_Right;
    public DcMotor front_Left;
    public DcMotor back_Leftx;
    public DcMotor intake;
    public DcMotor shooter;
    public Servo launcher;


    public void teleopInit() {
        startInit();
        back_Leftx.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_Leftx.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); //reset the motor to running w/o encoder after resetting the encoder so that it can be run again
        front_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        front_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        back_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launcher.setPosition(.2);


    }


    public void complexAutoInit() {

        //drivetrain
        front_Left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_Leftx.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        front_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //elevator


        back_Right.setDirection(DcMotorSimple.Direction.FORWARD);
        front_Right.setDirection(DcMotorSimple.Direction.FORWARD);
        back_Leftx.setDirection(DcMotorSimple.Direction.REVERSE);
        front_Left.setDirection(DcMotorSimple.Direction.REVERSE);


    }

    private void startInit() {
        //hardwaremap
        back_Right = hardwareMap.get(DcMotor.class, "back_Right");
        front_Right = hardwareMap.get(DcMotor.class, "front_Right");
        front_Left = hardwareMap.get(DcMotor.class, "front_Left");
        //front left has y axis dead wheel on it
        back_Leftx = hardwareMap.get(DcMotor.class, "back_Left");
        shooter = hardwareMap.get(DcMotor.class, "shooter");
        intake = hardwareMap.get(DcMotor.class, "intake");
        launcher = hardwareMap.get(Servo.class, "launcher");

        launcher.setPosition(.2);

        //directions
        back_Right.setDirection(DcMotorSimple.Direction.REVERSE);
        front_Right.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);

        //zeropowerbehavior
        front_Left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_Right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_Leftx.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_Right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //imu
        // Retrieve the IMU from the hardware map
        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        // Technically this is the default, however specifying it is clearer
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        // Without this, data retrieving from the IMU throws an exception
        imu.initialize(parameters);
        double driveTrainPower = 0.5;
    }

}