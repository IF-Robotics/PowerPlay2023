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
    public ElapsedTime clawTimer = new ElapsedTime();
    public ElapsedTime tailWait = new ElapsedTime();


}
