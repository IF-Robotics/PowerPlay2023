package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

@TeleOp(name = "League_2_Final")
public class CompCode extends TeleopFunctions {
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
    public void runOpMode() {
        teleopInit();
        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(new BNO055IMU.Parameters());

        // Drivetrain
        driveTrainPower = 1;
        Left_Stick_X = 0; //says that it is never used
        left_stick_y = 0;
        Right_Stick_Y = 0;

        elevate_brake_L = 220;
        elevate_brake_R = 220;


        stackHeight = 350;
        MoveClass moveClass = new MoveClass(front_Left, back_Leftx, front_Right, back_Right);
        //start
        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                moveX = 0;
                moveY = 0;
                rotate = 0;
                isElevatorUsed = false;
                // drive train
                if (gamepad1.dpad_down) {
                    reverse = true;
                }
                if (gamepad1.dpad_up) {
                    reverse = false;
                }
                int lDist = lPipe.getX();
                int rDist = 640 - rPipe.getX();
                double xPower = 0;
                double spin = 0;
                if (gamepad1.right_trigger > 0.1) {
                    telemetry.addData("gamepad1", 'a');
                    if (lDist != -404 && rDist != 1044) {
                        telemetry.addData("doenst see pole", true);
                        if (Math.abs(rDist - lDist) > 100) {
                            spin = (rDist - lDist) / 1300.0;

                            telemetry.addData("spin", spin);
                        }
                        if (rDist + lDist < 1800) {
                            xPower = -(rDist + lDist - 400) / 1200.0;
                            xPower = Range.clip(xPower, -0.3, 0.3);
                            telemetry.addData("xPower", xPower);
                        }
                        rotate = spin;
                        moveY = xPower;
                        //fieldCentric=false;
                    }
                }

                if (gamepad1.dpad_down) {
                    reverse = true;
                }
                if (gamepad1.dpad_up) {
                    reverse = false;
                }
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

                front_Left.setPower(direction * driveTrainPower * (-1 * Left_Stick_X + left_stick_y));
                back_Leftx.setPower(direction * driveTrainPower * (1 * Left_Stick_X + left_stick_y));
                front_Right.setPower(direction * driveTrainPower * (1 * Left_Stick_X + Right_Stick_Y));
                back_Right.setPower(direction * driveTrainPower * (-1 * Left_Stick_X + Right_Stick_Y));

                if (gamepad1.right_bumper) {
                    driveTrainPower = 0.3;
                } else if (gamepad1.left_bumper) {
                    driveTrainPower = 1;
                } else {
                    driveTrainPower = 0.7;
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
                //set up stackOneClick //TODO: Figure out how to do one-click thing
                if (gamepad2.a && armMode == 2) {
                    stackOneClick += 1;
                } else if (stackOneClick > 2) {
                    stackOneClick = 2;
                } else {
                    stackOneClick = 0;
                }


                //set up modeOneSwitch
                if (gamepad2.dpad_right) {
                    modeOneSwitch += 1;
                } else if (modeOneSwitch > 2) {
                    modeOneSwitch = 2;
                } else {
                    modeOneSwitch = 0;
                }

                //set up clawOneClick
                if (gamepad2.left_bumper) {
                    clawOneClick += 1;
                } else if (clawOneClick > 2) {
                    clawOneClick = 2;
                } else {
                    clawOneClick = 0;
                }

                //set up wristOneClick
                if (gamepad2.right_bumper) {
                    wristOneClick += 1;
                } else if (wristOneClick > 2) {
                    wristOneClick = 2;
                } else {
                    wristOneClick = 0;
                }

                //arm modes switch TODO: Change this from an counting number to an enum to be much more user friendly
                //why can't this just be add, instead of just checking and setting it to the next one?
                if (gamepad2.dpad_right && armMode == 0 && modeOneSwitch == 1) {
                    armMode = 1;
                } else if (gamepad2.dpad_right && armMode == 1 && modeOneSwitch == 1) {
                    armMode = 2;
                } else if (gamepad2.dpad_right && armMode == 2 && modeOneSwitch == 1) {
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
                    //closed
                    claw.setPosition(0.69);
                    clawStatus = true;
                } else if (gamepad2.left_bumper && clawStatus && clawOneClick == 1) {
                    //open
                    timer.reset();
                    if (elevate_Right.getCurrentPosition() > 250) {
                        isSoftStop = true;
                        isSoftStopReset = false;
                        softStopBehavior = SoftStopBehavior.Open;
                    } else {
                        claw.setPosition(0.93);
                    }
                    clawStatus = false;
                }

                // Wrist Move
                //TODO: Ask Wyatt why these are .2 and .7 for elevatePower
                if (gamepad2.right_bumper && !wristStatus && wristOneClick == 1) {
                    isElevatorUsed = true;
                    wrist.setPosition(1);
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
                    isElevatorUsed = true;
                    wrist.setPosition(0.61);
                    wrist2.setPosition(0.39);
                    elevate_Right.setTargetPosition(90);
                    elevate_Left.setTargetPosition(90);
                    elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elevate_Left.setPower(0.2);
                    elevate_Right.setPower(0.2);
                    elevate_brake_R = 90;
                    elevate_brake_L = 90;
                    wristStatus = false;
                } else if (gamepad2.right_trigger > 0.1) {
                    isElevatorUsed = true;
                    wrist.setPosition(0.61);
                    wrist2.setPosition(0.39);
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

                //Reset Button
                if (gamepad2.touchpad) {
                    isElevatorUsed = true;
                    elevate_Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    elevate_Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    elevate_Left.setPower(-0.7);
                    elevate_Right.setPower(-0.7);
                    // Put run blocks here.
                    sleep(500);
                    while (((DcMotorEx) elevate_Left).getVelocity() <= -200 && opModeIsActive()) {
                        telemetry.addData("velocity", ((DcMotorEx) back_Leftx).getVelocity());
                        telemetry.update();
                    }
                    elevate_Left.setPower(.1);
                    elevate_Right.setPower(.1);
                    arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    arm.setPower(-0.7);
                    sleep(500);
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
                    sleep(100);
                    arm.setPower(0);
                    arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    arm.setTargetPosition(40);
                    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    arm.setPower(.5);

                }


                //forward presets
                if (gamepad2.dpad_up) {
                    isElevatorUsed = true;
                    elevate_Right.setTargetPosition(1630);
                    elevate_Left.setTargetPosition(1630);
                    elevate_Right.setPower(1);
                    elevate_Left.setPower(1);
                    elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elevate_brake_R = 1630;
                    elevate_brake_L = 1630;
                    arm.setTargetPosition(500);
                    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    arm.setPower(0.6);
                    wrist.setPosition(0.4);
                    wrist2.setPosition(0.6);
                    flip.setPosition(0.3);
                    height = Height.High;
                }

                if (gamepad2.dpad_left) {
                    isElevatorUsed = true;
                    elevate_Right.setTargetPosition(1850);
                    elevate_Left.setTargetPosition(1850);
                    elevate_Right.setPower(1);
                    elevate_Left.setPower(1);
                    elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    wrist.setPosition(0.62);
                    wrist2.setPosition(0.38);
                    arm.setTargetPosition(0);
                    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    arm.setPower(0.6);
                    flip.setPosition(0.3);
                    elevate_brake_R = 1788;
                    elevate_brake_L = 1788;
                    height = Height.Medium;
                }

                if (gamepad2.dpad_down) {
                    isElevatorUsed = true;
                    elevate_Right.setTargetPosition(1110);
                    elevate_Left.setTargetPosition(1110);
                    elevate_Right.setPower(1);
                    elevate_Left.setPower(1);
                    elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    wrist.setPosition(0.62);
                    wrist2.setPosition(0.38);
                    arm.setTargetPosition(0);
                    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    arm.setPower(0.6);
                    flip.setPosition(0.3);
                    elevate_brake_R = 1110;
                    elevate_brake_L = 1110;
                    height = Height.Low;
                }

                //backward presets
                if (gamepad2.triangle) {
                    isElevatorUsed = true;
                    elevate_Right.setTargetPosition(1620);
                    elevate_Left.setTargetPosition(1620);
                    elevate_Right.setPower(1);
                    elevate_Left.setPower(1);
                    elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    arm.setTargetPosition(1370);
                    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    wrist.setPosition(.61);
                    wrist2.setPosition(.39);
                    flip.setPosition(0.97);
                    arm.setPower(0.3);
                    elevate_brake_R = 1620;
                    elevate_brake_L = 1620;
                    //preset(1620, .7, .95)
                    height = Height.High;
                } else if (gamepad2.square) {
                    isElevatorUsed = true;
                    elevate_Right.setTargetPosition(920);
                    elevate_Left.setTargetPosition(920);
                    elevate_Right.setPower(1);
                    elevate_Left.setPower(1);
                    elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    arm.setTargetPosition(1370);
                    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    wrist.setPosition(0.62);
                    wrist2.setPosition(.38);
                    flip.setPosition(0.97);
                    arm.setPower(0.3);
                    elevate_brake_R = 920;
                    elevate_brake_L = 920;
                    height = Height.Medium;
                } else if (gamepad2.circle) {
                    isElevatorUsed = true;
                    elevate_Right.setTargetPosition(330);
                    elevate_Left.setTargetPosition(330);
                    elevate_Right.setPower(1);
                    elevate_Left.setPower(1);
                    elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    arm.setTargetPosition(1370);
                    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    wrist.setPosition(0.62);
                    wrist2.setPosition(.38);
                    flip.setPosition(0.97);
                    arm.setPower(0.3);
                    elevate_brake_R = 372;
                    elevate_brake_L = 372;
                    height = Height.Low;

                    //armode 0 = camping = red, armode 1 = moving = greed, armode 2 = stacks = red and green


                } else if (gamepad2.cross && armMode == 0) {
                    isSoftStopReset = false;
                    isSoftStop = true;
                    isElevatorUsed = false;
                    softStopBehavior = SoftStopBehavior.Down_And_Open;
                } else if (gamepad2.cross && armMode == 1) {
                    preset(200, 1, .3, 1, 0, .69, 20, .6);
                } else if (gamepad2.cross && armMode == 2 && stackOneClick == 1) {
                    preset(stackHeight, 1, .3, .61, .39, .93, 50, .6);
                    stackHeight -= 90;
                    if (stackHeight < 0) {
                        stackHeight = 350;
                    }

                } else if (isSoftStop && timer.seconds() < .3 && !isElevatorUsed) {
                    isElevatorUsed = true;
                    elevatePower(-.7);
                } else if (!isSoftStopReset && softStopBehavior == SoftStopBehavior.Open) {
                    claw.setPosition(.93);
                    elevateSetRunToPosition();
                    timer.reset();
                    isSoftStop = false;
                    isSoftStopReset = true;
                    isElevatorUsed = false;
                } else if (!isSoftStopReset && softStopBehavior == SoftStopBehavior.Down_And_Open) {
                    claw.setPosition(.93);
                    elevateSetRunToPosition();
                    timer.reset();
                    isSoftStop = false;
                    isSoftStopReset = true;
                    isElevatorUsed = false;
                    preset(90, 1, .3, .61, .39, .93, 20, .5);
                } else {
                    //low virtual limit
                    if (!elevate_Right.isBusy() && !isElevatorUsed) {
                        if (elevate_brake_L < 200 || elevate_brake_R < 200) {
                            elevate_brake_R = elevate_Left.getCurrentPosition();
                            elevate_brake_L = elevate_Right.getCurrentPosition();
                            elevate_Left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                            elevate_Right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                            if (gamepad2.left_stick_y < -0.1) {
                                elevate_Left.setPower(-1 * gamepad2.left_stick_y);
                                elevate_Right.setPower(-1 * gamepad2.left_stick_y);
                            } else {
                                if (armMode == 0) {
                                    elevate_Left.setTargetPosition(100);
                                    elevate_Right.setTargetPosition(100);
                                } else if (armMode == 1) {
                                    elevate_Left.setTargetPosition(100);
                                    elevate_Right.setTargetPosition(100);
                                } else {
                                    elevate_Left.setTargetPosition(stackHeight);
                                    elevate_Right.setTargetPosition(stackHeight);
                                }
                                elevate_Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                                elevate_Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                                elevate_Left.setPower(0.5);
                                elevate_Right.setPower(0.5);

                            }
                        } else {
                            // High virtual limit
                            if (!isElevatorUsed && elevate_brake_L > 2000 || elevate_brake_R > 2000) {
                                if (gamepad2.left_stick_y > 0.1) {
                                    //TODO: could I just use manual elevate here
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
                                if (gamepad2.left_stick_y < -0.1 || gamepad2.left_stick_y > 0.1) {
                                    if (gamepad2.left_stick_y < .1) {
                                        manualElevate(-1);
                                    } else {
                                        manualElevate(-.2);
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
                if (moveX != 0 || moveY != 0 || rotate != 0)
                    moveClass.driveRobotCentric(moveX, moveY, rotate, driveTrainPower);

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
                telemetry.addData("wrist", wrist.getPosition());
                telemetry.addData("claw", claw.getPosition());
                telemetry.addData("isSoftStop", isSoftStop);
                telemetry.addData("Timer", timer.seconds());
                telemetry.addData("!ElevatorUsed", !isElevatorUsed);
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

