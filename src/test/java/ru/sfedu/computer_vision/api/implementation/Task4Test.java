package ru.sfedu.computer_vision.api.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;

import static ru.sfedu.computer_vision.Constants.*;


class Task4Test {

    private static final Logger log = LogManager.getLogger(Task4Test.class);

    TaskImpl task = new TaskImpl();
    ImageImpl impl = new ImageImpl();


    Mat defaultMat = Imgcodecs.imread(TEST_IMAGE_BLUR);
    Mat reclamaMat = Imgcodecs.imread(TEST_IMAGE_ADVERTISMENT);

    @Test
    void Blur() {
        log.info("Test Blur Success");
        Size size_3 = new Size(3, 3);
        Size size_5 = new Size(5, 5);
        Size size_7 = new Size(7, 7);
        impl.saveMatToFileWithPath(SAVE_TEST_IMAGE_BLUR_3, impl.baseBlur(defaultMat,defaultMat,size_3),IMAGE_PATH_BLUR);
        impl.saveMatToFileWithPath(SAVE_TEST_IMAGE_BLUR_5, impl.baseBlur(defaultMat,defaultMat,size_5),IMAGE_PATH_BLUR);
        impl.saveMatToFileWithPath(SAVE_TEST_IMAGE_BLUR_7, impl.baseBlur(defaultMat,defaultMat,size_7),IMAGE_PATH_BLUR);
    }

    @Test
    void GaussianBlur() {
        log.info("Test GaussianBlur Success");

        Size size_3 = new Size(3, 3);
        Size size_5 = new Size(5, 5);
        Size size_7 = new Size(7, 7);
        impl.saveMatToFileWithPath(SAVE_TEST_IMAGE_GAUSS_3, impl.gaussianBlur(defaultMat, defaultMat,size_3, 0, 90, 16),IMAGE_PATH_GAUSS);
        impl.saveMatToFileWithPath(SAVE_TEST_IMAGE_GAUSS_5, impl.gaussianBlur(defaultMat, defaultMat,size_5, 0, 90, 0),IMAGE_PATH_GAUSS);
        impl.saveMatToFileWithPath(SAVE_TEST_IMAGE_GAUSS_7, impl.gaussianBlur(defaultMat, defaultMat, size_7, 0, 90, 4),IMAGE_PATH_GAUSS);
    }

    @Test
    void MedianBlur() {
        log.info("Test MedianBlur Success");
        Mat defaultMat = Imgcodecs.imread(TEST_IMAGE_BLUR);

        impl.saveMatToFileWithPath(SAVE_TEST_IMAGE_MEDIAN_3, impl.medianBlur(defaultMat, defaultMat, 3),IMAGE_PATH_MEDIAN);
        impl.saveMatToFileWithPath(SAVE_TEST_IMAGE_MEDIAN_5, impl.medianBlur(defaultMat, defaultMat, 5),IMAGE_PATH_MEDIAN);
        impl.saveMatToFileWithPath(SAVE_TEST_IMAGE_MEDIAN_7, impl.medianBlur(defaultMat, defaultMat, 7),IMAGE_PATH_MEDIAN);
    }

    @Test
    void BilateralFilter() {
        log.info("Test BilateralFilter Success");
        Mat defaultMat = Imgcodecs.imread(TEST_IMAGE_BLUR);
        Mat mat = new Mat();
        impl.saveMatToFile(SAVE_TEST_IMAGE_BILATERAL, impl.bilateralFilter(defaultMat, mat, 5, 100, 100, Core.BORDER_DEFAULT));
    }

    @Test
    void morphingErode() {
        log.info("Test morphingErode Success");
        impl.morphingErode(reclamaMat);
    }


    @Test
    void morphingDilate() {
        log.info("Test morphingDilate Success");
        impl.morphingDilate(reclamaMat);
    }




}