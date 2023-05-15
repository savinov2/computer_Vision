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
    public static final String IMAGE_PATH = "/home/savinov2/IdeaProjects/computer_Vision/src/main/resources/images";

    public static final String TEST_IMAGE_NAME = "testImage.jpg";
    public static final String TEST_IMAGE_PATH = "/home/savinov2/IdeaProjects/computer_Vision/images/";


}
