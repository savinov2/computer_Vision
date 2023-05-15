package ru.sfedu.computer_vision.api.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import ru.sfedu.computer_vision.Constants;
import ru.sfedu.computer_vision.Constants.OSType;
import ru.sfedu.computer_vision.api.ImageService;
import ru.sfedu.computer_vision.api.Task;
import ru.sfedu.computer_vision.utils.ConfigurationUtil;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Locale;

public class TaskImpl implements Task {

    private static final Logger log = LogManager.getLogger(TaskImpl.class);
    private final String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);

    private final ImageService imageService = new ImageImpl();


    public TaskImpl() {
        try {
            log.info("Checking OS.....");
            switch (OSType.getOperatingSystemType(OS)) {
                case LINUX -> loadProperties(Constants.PATH_TO_NATIVE_LIB_LINUX);
                case WINDOWS -> loadProperties(Constants.PATH_TO_NATIVE_LIB_WIN);
                case MACOS -> throw new Exception("Mac OS does not support!!!!!!!!");
                case OTHER -> throw new Exception("Current OS does not support!!!!!");
            }
        } catch (Exception e) {
            log.debug(e);
        }
    }

    private void loadProperties(String path) throws IOException {
        String pathLin = ConfigurationUtil.getConfigurationEntry(path);
        //System.load() - инициализация библиотеки

        //pathLin - pathLin./lib/opencv_java455.dll
        //Paths.get(pathLin).toAbsolutePath().toString() ---- D:\computerVision\.\lib\opencv_java455.dll

        //Получение текущей версии библиотеки
        //Core.getVersionString()
        System.load(Paths.get(pathLin).toAbsolutePath().toString());
        log.info(Paths.get(pathLin).toAbsolutePath().toString());
        log.debug("Properties are loaded \n" + "OS Version: " + OS + "\n" + "Open CV version - " + Core.getVersionString());
    }

    @Override
    public void task2(int numberOfChannel, String pathName, String imageName) {

        //imageService.showImageByPath(pathName + imageName);
        Mat mat = imageService.imgToMatByPath(numberOfChannel, pathName, imageName);

        imageService.showImage(mat);
        //imageService.showImageByBufferedImage(imageService.matToBufferedImage(mat));
       // log.debug("check file name - ", imageName);
        imageService.saveMatToFile(imageName, mat);

    }


/*
    @Override
    public void task4(String path, int dx, int dy) {
        Size size = new Size(dx, dy);
        Mat image = Imgcodecs.imread(path);
        Mat newImage = new Mat();

        Mat mat = imageService.baseBlur(image, image, size);
        imageService.showImageByBufferedImage(imageService.matToBufferedImage(mat));
        imageService.saveMatToFile("Blur", mat);

        Mat matGaussian = imageService.gaussianBlur(mat, mat, size, 90, 90, 2);
        imageService.showImageByBufferedImage(imageService.matToBufferedImage(matGaussian));
        imageService.saveMatToFile("Gaussian", matGaussian);

        Mat median = imageService.medianBlur(matGaussian, matGaussian, dx);
        imageService.showImageByBufferedImage(imageService.matToBufferedImage(median));
        imageService.saveMatToFile("Median", median);

        Mat bilateral = imageService.bilateralFilter(median, newImage, 15, 80, 80, Core.BORDER_DEFAULT);
        imageService.showImageByBufferedImage(imageService.matToBufferedImage(bilateral));
        imageService.saveMatToFile("Bilateral", bilateral);
    }
*/
}