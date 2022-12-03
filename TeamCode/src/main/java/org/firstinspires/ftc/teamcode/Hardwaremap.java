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
    public PoleDetect lPipe = new PoleDetect();
    public PoleDetect rPipe = new PoleDetect();

    public enum Height {
        High,
        Medium,
        Low,
        Ground,
        //Down,
        Start
    }

    public enum SoftStopBehavior {
        Open,
        Down_And_Open,
        Other
    }

    public Height height;
    public SoftStopBehavior softStopBehavior;

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
        arm.setTargetPosition(20);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(1);
        flip.setPosition(0.3);
        wrist.setPosition(1);
        wrist2.setPosition(0);
        claw.setPosition(0.7);

        height = Height.Start;
        softStopBehavior = SoftStopBehavior.Other;
    }

    public void autoInit() {
        //TODO: Merge my auto & base structure to help Micah with autos
        startInit();
        claw.setPosition(.59);
        wrist.setPosition(1);
        arm.setTargetPosition(30);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(0.2);
        flip.setPosition(.3);
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
        back_Leftx.setDirection(DcMotorSimple.Direction.REVERSE);
        front_Left.setDirection(DcMotorSimple.Direction.REVERSE);

        //zeropowerbehavior
        front_Left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_Right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_Leftx.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_Right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //drivetrain
        front_Left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_Leftx.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        front_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //elevator
        elevate_Left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevate_Right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevate_Left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevate_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevate_Right.setDirection(DcMotorSimple.Direction.FORWARD);
        elevate_Left.setDirection(DcMotorSimple.Direction.REVERSE);
        elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        //imu
        // Retrieve the IMU from the hardware map
        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        // Technically this is the default, however specifying it is clearer
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        // Without this, data retrieving from the IMU throws an exception
        imu.initialize(parameters);
        double driveTrainPower = 0.5;
/*
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        int[] viewportContainerIds = OpenCvCameraFactory.getInstance()
                .splitLayoutForMultipleViewports(cameraMonitorViewId, 2, OpenCvCameraFactory.ViewportSplitMethod.HORIZONTALLY);
        WebcamName LName = hardwareMap.get(WebcamName.class, "lcam");
        OpenCvCamera lcam = OpenCvCameraFactory.getInstance().createWebcam(LName, viewportContainerIds[0]);
        lcam.openCameraDevice();
        this.lPipe = new PoleDetect();
        lcam.setPipeline(lPipe);


        WebcamName RName = hardwareMap.get(WebcamName.class, "rcam");
        OpenCvCamera rcam = OpenCvCameraFactory.getInstance().createWebcam(RName, viewportContainerIds[1]);
        rcam.openCameraDevice();
        this.rPipe = new PoleDetect();
        rcam.setPipeline(rPipe);
        sleep(500);

        startCameras(lcam, rcam);
    */
    }
    /*
    public void startCameras(OpenCvCamera lcam, OpenCvCamera rcam) {
        if (!isStopRequested()) {
            try {
                lcam.openCameraDevice();
                rcam.openCameraDevice();
                lcam.startStreaming(640,480, OpenCvCameraRotation.UPRIGHT);
                rcam.startStreaming(640,480, OpenCvCameraRotation.UPRIGHT);
            }
            catch (Exception e) {
                telemetry.addData("error",e);
                telemetry.update();
                startCameras(lcam,rcam);
            }
        }
    }
    */
}
