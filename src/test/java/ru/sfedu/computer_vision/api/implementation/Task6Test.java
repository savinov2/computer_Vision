package ru.sfedu.computer_vision.api.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;

import static ru.sfedu.computer_vision.Constants.*;


class Task6Test {

    private static final Logger log = LogManager.getLogger(Task6Test.class);

    TaskImpl task = new TaskImpl();
    ImageImpl impl = new ImageImpl();

    Mat defaultMat = Imgcodecs.imread("D:/Computer_Vision/images/ufu.jpg");
   // Mat defaultMat2 = Imgcodecs.imread("D:/Computer_Vision/images/rectangle.jpg");



    @Test
    void task5toFill() {
        impl.toFill(3, defaultMat);
    }

    @Test
    void task5canny() {
        impl.canny(defaultMat);
        impl.saveMatToFile(SAVE_TEST_IMAGE_CANNY, impl.canny(defaultMat));

    }


}
