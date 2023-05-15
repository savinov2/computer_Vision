package ru.sfedu.computer_vision.api.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import static ru.sfedu.computer_vision.Constants.*;


class Task3Test {

    private static final Logger log = LogManager.getLogger(Task3Test.class);

    TaskImpl task = new TaskImpl();

    ImageImpl impl = new ImageImpl();


    @Test
    void Sobel() {
        log.info("Test Sobel Success");

        Mat sobelMat = Imgcodecs.imread(TEST_IMAGE_SOBEL_LAPLACE);
        impl.saveMatToFileWithPath(SAVE_IMAGE_NAME_SOBEL_X_0_Y_1, impl.convertSobel(sobelMat, 0, 1),IMAGE_PATH_SOBEL);
        impl.saveMatToFileWithPath(SAVE_IMAGE_NAME_SOBEL_X_1_Y_0, impl.convertSobel(sobelMat, 1, 0),IMAGE_PATH_SOBEL);
        impl.saveMatToFileWithPath(SAVE_IMAGE_NAME_SOBEL_X_1_Y_1, impl.convertSobel(sobelMat, 1, 1),IMAGE_PATH_SOBEL);
        impl.saveMatToFileWithPath(SAVE_IMAGE_NAME_SOBEL_X_2_Y_1 , impl.convertSobel(sobelMat, 2, 1),IMAGE_PATH_SOBEL);
    }

    @Test
    void Laplace() {
        log.info("Test Laplace Success");
        Mat laplaceMat = Imgcodecs.imread(TEST_IMAGE_SOBEL_LAPLACE);
        impl.saveMatToFileWithPath(SAVE_IMAGE_LAPLACE_1, impl.convertLaplace(laplaceMat, 1),IMAGE_PATH_LAPLACE );
        impl.saveMatToFileWithPath(SAVE_IMAGE_LAPLACE_3, impl.convertLaplace(laplaceMat, 3),IMAGE_PATH_LAPLACE );
        impl.saveMatToFileWithPath(SAVE_IMAGE_LAPLACE_5, impl.convertLaplace(laplaceMat, 5),IMAGE_PATH_LAPLACE );
    }

    @Test
    void MirrorSuccess() {
        log.info("Test Mirror Success");
        Mat defaultMat = Imgcodecs.imread(TEST_IMAGE_MIRROR);
        impl.saveMatToFileWithPath(SAVE_IMAGE_MIRROR_0, impl.mirrorImage(defaultMat, 0),IMAGE_PATH_MIRROR);
        impl.saveMatToFileWithPath(SAVE_IMAGE_MIRROR_1, impl.mirrorImage(defaultMat, 1),IMAGE_PATH_MIRROR);
        impl.saveMatToFileWithPath(SAVE_IMAGE_MIRROR__1, impl.mirrorImage(defaultMat, -1),IMAGE_PATH_MIRROR);
    }

    @Test
    void RepeatSuccess() {
        log.info("Test Repeat Success");
        Mat defaultMat = Imgcodecs.imread(TEST_IMAGE_REPEAT);
        impl.saveMatToFile(SAVE_IMAGE_REPEAT, impl.repeatImage(defaultMat, 4, 5));
    }

    @Test
    void UnionSuccess() {
        log.info("Test Union Success");

        Mat defaultMat1 = Imgcodecs.imread(TEST_IMAGE_UNION1);
        defaultMat1 = impl.resizeImage(defaultMat1, 600, 600);
        Mat defaultMat2 = Imgcodecs.imread(TEST_IMAGE_UNION2);
        defaultMat2 = impl.resizeImage(defaultMat2, 600, 600);

        impl.saveMatToFile(SAVE_IMAGE_UNION_0, impl.unionImage(defaultMat2, defaultMat1,0));
        impl.saveMatToFile(SAVE_IMAGE_UNION_1, impl.unionImage(defaultMat2, defaultMat1,1));


        impl.saveMatToFile(SAVE_IMAGE_UNION_ADD_WEIGHTED, impl.unionImageAddWeighted(defaultMat2, defaultMat1));

    }

    @Test
    void lab3Unity() {
        ImageImpl imageImlp = new ImageImpl();
        log.info("lab3Unity");
        Mat imageOne = Imgcodecs.imread(TEST_IMAGE_PATH + "tears.jpg", Imgcodecs.IMREAD_COLOR);
        imageOne = impl.resizeImage(imageOne, 600, 600);
        Mat imageTwo = Imgcodecs.imread(TEST_IMAGE_PATH + "testImage.jpg", Imgcodecs.IMREAD_COLOR);
        imageTwo = impl.resizeImage(imageTwo, 600, 600);
        impl.unityImage(imageOne, imageTwo, "SOFA_ONE_NAME", 0);
    }

    @Test
    void ResizeSuccess() {
        log.info("Test Resize Success");
        impl.saveMatToFile(SAVE_IMAGE_RESIZE, impl.resizeImage(Imgcodecs.imread(TEST_IMAGE_RESIZE), 300, 300));
    }

    @Test
    void RotationImageSuccess() {
        log.info("Test Rotation Success");
        Mat defaultMat = Imgcodecs.imread(TEST_IMAGE_ROTATION);
        impl.saveMatToFile(SAVE_IMAGE_ROTATION_CUT_TRUE, impl.RotationImage(defaultMat,45,true));
        impl.saveMatToFile(SAVE_IMAGE_ROTATION_CUT_FALSE, impl.RotationImage(defaultMat,45,false));
    }

    @Test
    void ImageShiftSuccess(){
        log.info("Test Shift Success");
        Mat defaultMat = Imgcodecs.imread(TEST_IMAGE_ROTATION);
        impl.saveMatToFile(SAVE_IMAGE_SHIFT , impl.imageShift(defaultMat,100,50));
    }

    @Test
    void ChangeOfPerspectiveSuccess() {
        log.info("ChangeOfPerspectiveSuccess");
        Mat defaultMat = Imgcodecs.imread(TEST_IMAGE_WARP);
        impl.saveMatToFile(SAVE_TEST_IMAGE_CHANGE_PERSPECTIVE, impl.ChangeOfPerspective(defaultMat,360,360));
    }

}