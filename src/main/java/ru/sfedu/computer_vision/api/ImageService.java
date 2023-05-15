package ru.sfedu.computer_vision.api;

import org.opencv.core.Mat;

public interface ImageService {

    //Lab2

    Mat imgToMatByPath(int numberOfChannel, String pathName, String imageName);

    void showImage(Mat m);

    void saveMatToFile(String imageName, Mat img);


}
