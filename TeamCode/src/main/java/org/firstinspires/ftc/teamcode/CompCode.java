package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "Scrimmage")
public class CompCode extends TeleopFunctions {

    @Override
    public void runOpMode() {
        teleopInit();
        // Drivetrain
        driveTrainPower = 1;
        Left_Stick_X = 0; //says that it is never used
        left_stick_y = 0;
        Right_Stick_Y = 0;

        elevate_brake_L = 220;
        elevate_brake_R = 220;

        //start
        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                // drive train
                if (gamepad1.dpad_down) {
                    reverse = true;
                }
                if (gamepad1.dpad_up) {
                    reverse = false;
                }

                Left_Stick_X = gamepad1.left_stick_x;
                int direction = 1;
                if (!reverse) {
                    direction = 1;
                    left_stick_y = gamepad1.left_stick_y;
                    Right_Stick_Y = gamepad1.right_stick_y;
                } else if (reverse) {
                    direction = -1;
                    left_stick_y = gamepad1.right_stick_y;
                    Right_Stick_Y = gamepad1.left_stick_y; //TODO: fix the fact that this is literally lying
                }

                front_Left.setPower(direction * driveTrainPower * (-1 * Left_Stick_X + left_stick_y));
                back_Leftx.setPower(direction * driveTrainPower * (1 * Left_Stick_X + left_stick_y));
                front_Right.setPower(direction * driveTrainPower * (1 * Left_Stick_X + Right_Stick_Y));
                back_Right.setPower(direction * driveTrainPower * (-1 * Left_Stick_X + Right_Stick_Y));

                if (gamepad1.right_bumper) {
                    driveTrainPower = 0.3;
                } else if (gamepad1.left_bumper) {
                    driveTrainPower = 1;
                } else {
                    driveTrainPower = 0.7; //TODO: fix later so that strafing goes full speed defaultly
                }

                //set up stackOneClick //TODO: Figure out how to do one-click thing
                if (gamepad2.a && armMode == 2){
                    stackOneClick += 1;
                } else if (stackOneClick > 2) {
                    stackOneClick = 2;
                } else {
                    stackOneClick = 0;
                }

                //This is used to make sure that it only changes once
                /*if (gamepad2.dpad_right){
                    modeOneSwitch += 1;
                } else if (modeOneSwitch > 2){
                    modeOneSwitch = 2;
                } else {
                    modeOneSwitch = 0;
                }*/

                //set up clawOneClick
                if (gamepad2.left_bumper){
                    clawOneClick += 1;
                } else if (clawOneClick > 2){
                    clawOneClick = 2;
                } else {
                    clawOneClick = 0;
                }

                //set up wristOneClick
                if (gamepad2.right_bumper){
                    wristOneClick += 1;
                } else if (wristOneClick > 2){
                    wristOneClick = 2;
                } else {
                    wristOneClick = 0;
                }

                //arm modes switch TODO: Change this from an counting number to an enum to be much more user friendly
                //why can't this just be add, instead of just checking and setting it to the next one?
                if (gamepad2.dpad_right && armMode == 0 && modeSwitch.isChangeState()/*modeOneSwitch == 1*/) {
                    armMode = 1;
                } else if (gamepad2.dpad_right && armMode == 1 && modeSwitch.isChangeState()/*modeOneSwitch ==1*/){
                    armMode = 2;
                } else if (gamepad2.dpad_right && armMode == 2 && modeSwitch.isChangeState()/*modeOneSwitch == 1*/){
                    armMode = 0;
                }

                //armMode Lights
                if (armMode == 0) {
                    leftGreen.enable(false);
                    leftRed.enable(true);
                    rightGreen.enable(false);
                    rightRed.enable(true);

                } else if (armMode == 1) {
                    leftGreen.enable(true);
                    leftRed.enable(false);
                    rightGreen.enable(true);
                    rightRed.enable(false);

                } else if (armMode == 2) {
                    leftGreen.enable(true);
                    leftRed.enable(false);
                    rightGreen.enable(false);
                    rightRed.enable(true);
                }

                // Claw
                if (gamepad2.left_bumper && !clawStatus && clawOneClick == 1) {
                    claw.setPosition(0.65);
                    clawStatus = true;
                } else if (gamepad2.left_bumper && clawStatus && clawOneClick == 1) {
                    claw.setPosition(0.99);
                    clawStatus = false;
                }

                // Wrist Move
                if (gamepad2.right_bumper && !wristStatus && wristOneClick == 1) {
                    wrist.setPosition(0.95);
                    wrist2.setPosition(0);
                    claw.setPosition(0.7);
                    elevate_Right.setTargetPosition(220);
                    elevate_Left.setTargetPosition(220);
                    elevate_Right.setPower(0.7);
                    elevate_Left.setPower(0.7);
                    elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elevate_brake_R = 220;
                    elevate_brake_L = 220;
                    wristStatus = true;
                } else if (gamepad2.right_bumper && wristStatus && wristOneClick == 1) {
                    wrist.setPosition(0.63);
                    wrist2.setPosition(0.32);
                    elevate_Right.setTargetPosition(90);
                    elevate_Left.setTargetPosition(90);
                    elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elevate_Left.setPower(0.2);
                    elevate_Right.setPower(0.2);
                    elevate_brake_R = 90;
                    elevate_brake_L = 90;
                    wristStatus = false;
                } else if (gamepad2.right_trigger > 0.1){
                    wrist.setPosition(0.63);
                    wrist2.setPosition(0.32);
                    claw.setPosition(0.99);
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

                //forward presets
                if (gamepad2.dpad_up) {
                    elevate_Right.setTargetPosition(1630);
                    elevate_Left.setTargetPosition(1630);
                    elevate_Right.setPower(0.7);
                    elevate_Left.setPower(0.7);
                    elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elevate_brake_R = 1630;
                    elevate_brake_L = 1630;
                    arm.setTargetPosition(500);
                    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    arm.setPower(0.6);
                    wrist.setPosition(0.4);
                    flip.setPosition(0.3);
                }

                if (gamepad2.dpad_left) {
                    elevate_Right.setTargetPosition(1788);
                    elevate_Left.setTargetPosition(1788);
                    elevate_Right.setPower(0.7);
                    elevate_Left.setPower(0.7);
                    elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    wrist.setPosition(0.62);
                    arm.setTargetPosition(0);
                    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    arm.setPower(0.6);
                    flip.setPosition(0.3);
                    elevate_brake_R = 1788;
                    elevate_brake_L = 1788;
                }

                if (gamepad2.dpad_down) {
                    elevate_Right.setTargetPosition(1110);
                    elevate_Left.setTargetPosition(1110);
                    elevate_Right.setPower(0.7);
                    elevate_Left.setPower(0.7);
                    elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    wrist.setPosition(0.62);
                    arm.setTargetPosition(0);
                    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    arm.setPower(0.6);
                    flip.setPosition(0.3);
                    elevate_brake_R = 1110;
                    elevate_brake_L = 1110;
                }

                //backward presets
                if (gamepad2.triangle) {
                    elevate_Right.setTargetPosition(1620);
                    elevate_Left.setTargetPosition(1620);
                    elevate_Right.setPower(0.7);
                    elevate_Left.setPower(0.7);
                    elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    arm.setTargetPosition(1370);
                    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    wrist.setPosition(.63);
                    wrist2.setPosition(.34);
                    flip.setPosition(0.95);
                    arm.setPower(0.5);
                    elevate_brake_R = 1620;
                    elevate_brake_L = 1620;
                    //preset(1620, .7, .95)
                } else if (gamepad2.square) {
                    elevate_Right.setTargetPosition(920);
                    elevate_Left.setTargetPosition(920);
                    elevate_Right.setPower(0.7);
                    elevate_Left.setPower(0.7);
                    elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    arm.setTargetPosition(1370);
                    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    wrist.setPosition(0.63);
                    wrist2.setPosition(.34);
                    flip.setPosition(0.95);
                    arm.setPower(0.6);
                    elevate_brake_R = 920;
                    elevate_brake_L = 920;

                } else if (gamepad2.circle) {
                    elevate_Right.setTargetPosition(372);
                    elevate_Left.setTargetPosition(372);
                    elevate_Right.setPower(0.7);
                    elevate_Left.setPower(0.7);
                    elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    arm.setTargetPosition(1370);
                    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    wrist.setPosition(0.63);
                    wrist.setPosition(.34);
                    flip.setPosition(0.95);
                    arm.setPower(0.6);
                    elevate_brake_R = 372;
                    elevate_brake_L = 372;

                    //armode 0 = camping = red, armode 1 = moving = greed, armode 2 = stacks = red and green
                } else if (gamepad2.cross && armMode == 0) {
                    preset(90, .7, .3, .61, .0,.965, 0, .8);
                } else if (gamepad2.cross && armMode == 1) {
                    preset(200, .7, .3, .95, .0,.7, 0, .8);
                } else if (gamepad2.cross && armMode == 2 && stackOneClick == 1) {
                    preset(stackHeight, .7, .3, .63, .0,.99, 0, .8);
                    if (stackHeight < 50) {
                        stackHeight = 477;
                    }


                } else {
                    if (!elevate_Right.isBusy()) {
                        if (elevate_brake_L < 200 || elevate_brake_R < 200) {
                            elevate_brake_R = elevate_Left.getCurrentPosition();
                            elevate_brake_L = elevate_Right.getCurrentPosition();
                            elevate_Left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                            elevate_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                            if (gamepad2.left_stick_y < -0.1) {
                                elevate_Left.setPower(-1 * gamepad2.left_stick_y);
                                elevate_Right.setPower(-1 * gamepad2.left_stick_y);
                            } else {
                                elevate_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                                elevate_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                                elevate_Left.setPower(0.03);
                                elevate_Right.setPower(0.03);
                            }
                        } else {
                            // virtual limit
                            if (elevate_brake_L > 1925 || elevate_brake_R > 1925) {
                                if (gamepad2.left_stick_y > 0.1) {
                                    //TODO: could I just use manual elevate here?
                                    elevate_brake_L = elevate_Right.getCurrentPosition();
                                    elevate_brake_R = elevate_Right.getCurrentPosition();
                                    elevate_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                                    elevate_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                                    elevate_Right.setPower(-1 * gamepad2.left_stick_y);
                                    elevate_Left.setPower(-1 * gamepad2.left_stick_y);
                                } else {
                                    hold();
                                }
                            } else {
                                // movement
                                if (gamepad2.left_stick_y < -0.1 || gamepad2.left_stick_y > 0.1){
                                    if (gamepad2.left_stick_y < .1) {
                                        manualElevate(-.7);
                                    } else {
                                        manualElevate(-.1);
                                    }
                                } else {
                                    elevatePosition(elevate_brake_L, elevate_brake_R, 1);
                                }
                            }
                        }
                    }
                }
                //rumble controllers
                if (claw.getPosition() > 0.95 && distance.getDistance(DistanceUnit.CM) < 7) {
                    gamepad1.rumble(100);
                    gamepad2.rumble(100);
                    gamepad1.setLedColor(1, 1, 1, 300);
                    gamepad2.setLedColor(1, 1, 1, 300);
                }
                // telemetry
                telemetry.addData("reverse", reverse);
                telemetry.addData("Left Joystick", gamepad2.left_stick_y);
                telemetry.addData("armMode", armMode);
                telemetry.addData("Arm", arm.getCurrentPosition());
                telemetry.addData("Elevator Left", elevate_Left.getCurrentPosition());
                telemetry.addData("Elevator Right", elevate_Right.getCurrentPosition());
                telemetry.addData("odopody", front_Right.getCurrentPosition());
                telemetry.addData("back_Left", back_Leftx.getCurrentPosition());
                telemetry.addData("distance sensor", distance.getDistance(DistanceUnit.CM));
                telemetry.update();
            }
        }
    }
}

