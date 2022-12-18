package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class SignalDetect extends OpenCvPipeline {
    double x=100;
    double y=120;
    boolean viewportPaused;
    Mat dst = new Mat();
    Mat hsv = new Mat();
    Mat otresh = new Mat();
    Mat ptresh = new Mat();
    Mat ohierarchy = new Mat();
    Mat phierarchy = new Mat();
    Mat gtresh = new Mat();
    Mat ghierarchy = new Mat();
    Mat inputResized = new Mat();
    Scalar color = new Scalar(0,255,0);
    Telemetry telemetry;
    double maxArea = 0;
    MatOfPoint maxContour;
//    public enum Largest {
//        one, two, three, error
//    }
//    Largest largest = Largest.error;
    int largest2 = 0;
    int pos=-1;
    int outputX;
    int outputY;

    @Override
    public Mat processFrame(Mat input) {

        List<MatOfPoint> ocontours = new ArrayList<>();
        List<MatOfPoint> pcontours = new ArrayList<>();
        List<MatOfPoint> gcontours = new ArrayList<>();
        Imgproc.resize(input,inputResized, new Size(320,240));
        Imgproc.GaussianBlur(inputResized, dst, new Size(5,5), 0);
        Imgproc.cvtColor(dst, hsv, Imgproc.COLOR_RGB2HSV);
        //Core.inRange(hsv, new Scalar(9,137,196), new Scalar(13,194,204), gtresh);
        Core.inRange(hsv, new Scalar(5,46/100*255,180), new Scalar(20,210,210), otresh); //orange
//        Core.inRange(hsv, new Scalar(129,89,163), new Scalar(134,173,79), ytresh); //purple
        Core.inRange(hsv, new Scalar(120,80,75), new Scalar(145,185,170), ptresh); //purple
//        Core.inRange(hsv, new Scalar(137/2,9/100*255,68/100*255), new Scalar(121/2,134/100*255,128/100*255), gtresh); //green
        Core.inRange(hsv, new Scalar(140/2,30/100*255,55/100*255), new Scalar(165/2,120/100*255,120/100*255), gtresh); //green

        Imgproc.findContours(otresh, ocontours, ohierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
        Imgproc.findContours(ptresh, pcontours, phierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
        Imgproc.findContours(gtresh, gcontours, ghierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
        maxArea=0;
        maxContour=null;
        pos=-1;
        for(int i = 0; i < pcontours.size(); i++) {
            double area = Imgproc.contourArea(pcontours.get(i));
            if (area> maxArea&&area>300) {
                MatOfPoint point2 = pcontours.get(i);
                double[] position = point2.get(0,0);

                maxArea=area;
                if (position[0]<120)
                    maxContour=pcontours.get(i);
//                largest = largest.one;
                largest2 =3;
            }
            if(area>300){
                Imgproc.drawContours(inputResized, pcontours, -1, new Scalar(200,20,2), 2
                        , Imgproc.LINE_8, phierarchy, 2, new Point());
                MatOfPoint point2 = pcontours.get(i);
                double[] position = point2.get(0,0);
                int x = (int) position[0];
                pos = x;
            }
        }
        for(int i = 0; i < ocontours.size(); i++) {
            double area = Imgproc.contourArea(ocontours.get(i));
            if (area > maxArea && area > 300) {
                MatOfPoint point2 = ocontours.get(i);
                double[] position = point2.get(0, 0);

                maxArea = area;
                if (position[0] < 120)
                    maxContour = ocontours.get(i);
//                largest = largest.three;
                largest2 = 1;
            }
            if (area > 300) {
                Imgproc.drawContours(inputResized, ocontours, -1, color, 2
                        , Imgproc.LINE_8, ohierarchy, 2, new Point());
                MatOfPoint point2 = ocontours.get(i);
                double[] position = point2.get(0, 0);
                int x = (int) position[0];
            }
        }
        for(int i = 0; i < gcontours.size(); i++) {
            double area = Imgproc.contourArea(gcontours.get(i));
            if (area> maxArea&&area>300) {
                MatOfPoint point2 = gcontours.get(i);
                double[] position = point2.get(0,0);

                maxArea=area;
                if (position[0]<120)
                    maxContour=gcontours.get(i);
//                largest = largest.three;
                largest2 = 2;
            }
            if(area>300){
                Imgproc.drawContours(inputResized, gcontours, -1, color, 2
                        , Imgproc.LINE_8, ghierarchy, 2, new Point());
                MatOfPoint point2 = gcontours.get(i);
                double[] position = point2.get(0,0);
                int x = (int) position[0];
            }
        }

        if (maxContour !=null) {
            MatOfPoint2f thing = new MatOfPoint2f(maxContour.toArray());
            RotatedRect rect = Imgproc.minAreaRect(thing);
            MatOfPoint2f approx = new MatOfPoint2f();
            Imgproc.approxPolyDP(thing, approx,0.15*Imgproc.arcLength(thing,true),true);
            if (!approx.empty()) {
                for (Point p : approx.toArray()) {
                    Imgproc.drawMarker(inputResized,p,new Scalar(2,20,200));
                }
            }
            if (approx.toArray().length<5) {
                Imgproc.drawMarker(inputResized,rect.center,new Scalar(200,20,2));
            }
            outputX = rect.boundingRect().x;
            outputY = rect.boundingRect().y;
        }

        Imgproc.drawMarker(otresh,new Point(x,y),new Scalar(200,20,2));
        return inputResized;
    }
    public void setX(double x) {
        this.x=x;
    }
    public void setY(double y) {
        this.y=y;
    }
    public void setTelemetry(Telemetry tele) {
        this.telemetry=tele;
    }
    public int getColor() {
        return largest2;
    }
    public int getX() {
        return outputX;
    }
    public int getY() {
        return outputY;
    }

}