package ru.sfedu.computer_vision.api;

import org.opencv.core.Mat;
import org.opencv.core.Size;

import java.awt.image.BufferedImage;

public interface ImageService {

    //Lab2

    Mat imgToMatByPath(int numberOfChannel, String pathName, String imageName);

    void showImage(Mat m);

    void saveMatToFile(String imageName, Mat img);

    //Lab3
    BufferedImage matToBufferedImage(Mat image);

    Mat convertSobel(Mat image, int dx, int dy);

    Mat convertLaplace(Mat image, int ksize);

    Mat mirrorImage(Mat image, int flipCode);

    Mat unionImage(Mat mat, Mat dst,int axis);

    Mat unionImageAddWeighted(Mat mat, Mat dst);

    Mat repeatImage(Mat image, int ny, int nx);

    Mat resizeImage(Mat image, int width, int height);

    Mat RotationImage(Mat enterImage, int angle, boolean isCut);

    Mat imageShift(Mat image,int count_x, int count_y);

    Mat ChangeOfPerspective(Mat image, int x, int y);

    Mat baseBlur(Mat src, Mat dst, Size ksize);

    Mat gaussianBlur(Mat src, Mat dst, Size ksize, double sigmaX, double sigmaY, int borderType);

    Mat medianBlur(Mat src, Mat dst, int ksize);

    Mat bilateralFilter(Mat src, Mat dst, int d, double sigmaColor, double sigmaSpace, int borderType);

  //  void showImageByBufferedImage(BufferedImage bufferedImage);

    void morphingDilate(Mat defaultMat);

    void morphingErode(Mat defaultMat);

    void toFill(Integer initVal, Mat defaultMat);

    void toPyr(Mat defaultMat);

    void toSquare(Mat mat, double height, double width);

    Mat canny(Mat src);




}
