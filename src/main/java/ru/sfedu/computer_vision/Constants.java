package ru.sfedu.computer_vision;

public class Constants {
    public static final String CONFIG_PATH = "config.path";

    public enum OSType {
        MACOS,
        WINDOWS,
        LINUX,
        OTHER;

        public static OSType getOperatingSystemType(String type){
            if (type.contains("mac") || type.contains("darwin")) {
                return OSType.MACOS;
            } else if (type.contains("win")) {
                return OSType.WINDOWS;
            } else if (type.contains("nux")) {
                return OSType.LINUX;
            } else {
                return OSType.OTHER;
            }
        }
    }

    //Lab2
    public static final String PATH_TO_NATIVE_LIB_LINUX = "lin_path";
    public static final String PATH_TO_NATIVE_LIB_WIN = "win_path";
    public static final String IMAGE_PATH = "/home/user/Computer_Vision/src/main/resources/images";
    public static final String IMAGE_PATH_SOBEL = "/home/user/Computer_Vision/src/main/resources/images/sobel";
    public static final String IMAGE_PATH_LAPLACE = "/home/user/Computer_Vision/src/main/resources/images/laplace";
    public static final String IMAGE_PATH_MIRROR = "/home/user/Computer_Vision/src/main/resources/images/mirror";

    public static final String TEST_IMAGE_NAME = "testImage.jpg";
    public static final String TEST_IMAGE_PATH = "/home/user/Computer_Vision/images/";


    //Lab3
    public static final String TEST_IMAGE_SOBEL_LAPLACE = "D:/Computer_Vision/images/sobel.jpg";
    public static final String SAVE_IMAGE_NAME_SOBEL_X_0_Y_1 = "Sobel_x_0_y_1.jpg";
    public static final String SAVE_IMAGE_NAME_SOBEL_X_1_Y_0 = "Sobel_x_1_y_0.jpg";
    public static final String SAVE_IMAGE_NAME_SOBEL_X_1_Y_1 = "Sobel_x_1_y_1.jpg";
    public static final String SAVE_IMAGE_NAME_SOBEL_X_2_Y_1 = "Sobel_x_2_y_0.jpg";


    public static final String SAVE_IMAGE_LAPLACE_1 = "Laplace_1.jpg";
    public static final String SAVE_IMAGE_LAPLACE_3 = "Laplace_3.jpg";
    public static final String SAVE_IMAGE_LAPLACE_5 = "Laplace_5.jpg";

    public static final String TEST_IMAGE_MIRROR = "D:/Computer_Vision/images/mirror.jpg";
    public static final String SAVE_IMAGE_MIRROR_0 = "mirror_0.jpg";
    public static final String SAVE_IMAGE_MIRROR_1 = "mirror_1.jpg";
    public static final String SAVE_IMAGE_MIRROR__1 = "mirror__1.jpg";

    public static final String TEST_IMAGE_REPEAT = "D:/Computer_Vision/images/repeat.jpg";
    public static final String SAVE_IMAGE_REPEAT = "repeat.jpg";

    public static final String TEST_IMAGE_UNION1 = "D:/Computer_Vision/images/testImage.jpg";
    public static final String TEST_IMAGE_UNION2= "D:/Computer_Vision/images/tears.jpg";
    public static final String SAVE_IMAGE_UNION_0 = "union_0.jpg";
    public static final String SAVE_IMAGE_UNION_1 = "union_1.jpg";
    public static final String SAVE_IMAGE_UNION_ADD_WEIGHTED = "union_weighted.jpg";


    public static final String TEST_IMAGE_RESIZE = "D:/Computer_Vision/images/grey1.jpg";
    public static final String SAVE_IMAGE_RESIZE = "resize.jpg";

    public static final String TEST_IMAGE_ROTATION = "D:/Computer_Vision/images/grey2.jpg";
    public static final String SAVE_IMAGE_ROTATION_CUT_TRUE = "rotation_cat_true.jpg";
    public static final String SAVE_IMAGE_SHIFT = "shift.jpg";
    public static final String SAVE_IMAGE_ROTATION_CUT_FALSE = "rotation_cat_false.jpg";


    public static final String SAVE_TEST_IMAGE_CHANGE_PERSPECTIVE = "change_perspective.jpg";
    public static final String TEST_IMAGE_WARP = "D:/Computer_Vision/images/grey2.jpg";


    public static final String IMAGE_PATH_BLUR = "D:/Computer_Vision/src/main/resources/images/blur";
    public static final String IMAGE_PATH_GAUSS = "D:/Computer_Vision/src/main/resources/images/gaussian";
    public static final String IMAGE_PATH_MEDIAN = "D:/Computer_Vision/src/main/resources/images/median";
    public static final String IMAGE_PATH_BILATERAL = "D:/Computer_Vision/src/main/resources/images/bilaterial";
    public static final String IMAGE_PATH_MORFOLOGY = "D:/Computer_Vision/src/main/resources/images/morfology";
    public static final String TEST_IMAGE_BLUR = "D:/Computer_Vision/images/Sea.jpg";

    public static final String SAVE_TEST_IMAGE_BLUR_3 = "blur_3.jpg";
    public static final String SAVE_TEST_IMAGE_BLUR_5 = "blur_5.jpg";
    public static final String SAVE_TEST_IMAGE_BLUR_7 = "blur_7.jpg";
    public static final String SAVE_TEST_IMAGE_GAUSS_3 = "gauss_3.jpg";
    public static final String SAVE_TEST_IMAGE_GAUSS_5 = "gauss_5.jpg";
    public static final String SAVE_TEST_IMAGE_GAUSS_7 = "gauss_7.jpg";

    public static final String SAVE_TEST_IMAGE_MEDIAN_3 = "median_3.jpg";
    public static final String SAVE_TEST_IMAGE_MEDIAN_5 = "median_5.jpg";
    public static final String SAVE_TEST_IMAGE_MEDIAN_7 = "median_7.jpg";

    public static final String SAVE_TEST_IMAGE_BILATERAL = "bilateral.jpg";


    public static final String TEST_IMAGE_ADVERTISMENT = "D:/Computer_Vision/images/reklam.jpg";


    public static final String TEST_IMAGE_FILL = "D:/Computer_Vision/images/circle.jpg";

    public static final String SAVE_TEST_IMAGE_CANNY = "canny.jpg";



}
