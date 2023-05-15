package ru.sfedu.computer_vision.api.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;
import ru.sfedu.computer_vision.Constants;
import ru.sfedu.computer_vision.api.ImageService;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.*;
import java.util.List;

import static org.opencv.core.CvType.CV_8UC3;


public class ImageImpl implements ImageService {

    private static final Logger log = LogManager.getLogger(ImageImpl.class);


    public Mat imgToMatByPath(int numberOfChannel, String pathName, String imageName) {
        //Загрузка исходной картинки
        Mat image = Imgcodecs.imread(pathName + imageName);
        //Определение размера и инициализация массива Byte
        int totalBytes = (int) (image.total() * image.elemSize());
        byte[] buffer = new byte[totalBytes];
        //Выгрузка контента в массив
        //Перебор каждого элемента, в случае если индекс элемента соответствует каналу
        //- выполняется обнуление этого элемента
        image.get(0, 0, buffer);
        //for (int i=0; i<5; i++){
          //  sum += matrix[i][i];
       // }
        for (int i = 0; i < totalBytes; i++) {
            if (i % numberOfChannel == 0) {
                buffer[i] = 0;
            }
        }
        //Копирование массива Byte в матрицу исходного изображения
        image.put(0, 0, buffer);
        return image;
    }

    @Override
    public void showImage(Mat m) {
        //Класс Java BufferedImage является подклассом класса Image.
        //Он используется для обработки и управления данными изображения.

        //TYPE_BYTE_GRAYПредставляет собой беззнаковое байтовое изображение
        // в оттенках серого, неиндексированное. Это изображение имеет ComponentColorModel с цветовым пространством CS_GRAY.
        // Когда данные с непрозрачной альфа-версией хранятся в изображении этого типа, данные о цвете должны быть
        // приведены к форме без предварительного умножения, а альфа-версия отброшена, как описано в документации

        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (m.channels() > 1) {
            //Представляет изображение с 8-разрядными цветовыми компонентами RGB,
            // соответствующими цветовой модели BGR в стиле Windows) с цветами Синий,
            // Зеленый и Красный, сохраненными в 3 байтах.
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels() * m.cols() * m.rows();
        byte[] b = new byte[bufferSize];
        ///Выгрузка контента в массив
        m.get(0, 0, b);
        BufferedImage bufferedImage = new BufferedImage(m.cols(), m.rows(), type);

        //Этот класс расширяется DataBuffer и хранит данные внутренне как байты.
        // Значения сохранены в байтовом массиве (ах) этого DataBuffer обрабатываются как значения без знака.
        //Получая доступ к массиву пикселей напрямую, используя:

        //getRaster() - Returns the WritableRaster. Этот класс расширяет возможности растра для обеспечения возможности
        // записи в пикселях.
        //Raster - Класс, представляющий прямоугольный массив пикселей.
        //getDataBuffer() - Возвращает буфер данных, связанный с этим растром.
        //Возвращает данные для одного пикселя в примитивном массиве типа TransferType.

        final byte[] targetPixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();

        //System.arraycopy - копирования элементов из одного массива в другой.
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        ImageIcon icon = new ImageIcon(bufferedImage);

        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(frame.getToolkit().getScreenSize().width +50, frame.getToolkit().getScreenSize().height +50);
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
    //    for (int i = 0;i<1000;i++){

            frame.setVisible(false);
       // }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void saveMatToFile(String imageName, Mat img) {
        try {
            final String modFilePath = buildImageName(Constants.IMAGE_PATH,imageName);
            Imgcodecs.imwrite(modFilePath, img);
        } catch (Exception e) {
            log.debug("only .JPG and .PNG files are supported");
        }
    }

    public void saveMatToFileWithPath(String imageName, Mat img, String path) {
        try {
            final String modFilePath = buildImageName(path,imageName);
            Imgcodecs.imwrite(modFilePath, img);
        } catch (Exception e) {
            log.debug("only .JPG and .PNG files are supported");
        }
    }

    private String buildImageName(String imPath, String name) {
        return String.format("%s/%s", imPath, name);
    }

    @Override
    public BufferedImage matToBufferedImage(Mat matImage) {

        int type = BufferedImage.TYPE_BYTE_GRAY;

        if (matImage.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = matImage.channels() * matImage.cols() * matImage.rows();
        byte[] b = new byte[bufferSize];
        matImage.get(0, 0, b);
        BufferedImage bufferedImage = new BufferedImage(matImage.cols(), matImage.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return bufferedImage;
    }

/*
    @Override
    public void showImageByBufferedImage(BufferedImage bufferedImage) {
        ImageIcon icon = new ImageIcon(bufferedImage);
        frame(icon);
    }

    private void frame(ImageIcon icon) {
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(frame.getToolkit().getScreenSize().width - 300, frame.getToolkit().getScreenSize().height - 350);
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

*/

    //Преобразует изображение из одного цветового пространства в другое
    /*
    //https://docs.opencv.org/java/2.4.2/org/opencv/imgproc/Imgproc.html#cvtColor(org.opencv.core.Mat,%20org.opencv.core.Mat,%20int)
     //https://docs.opencv.org/java/2.4.2/org/opencv/imgproc/Imgproc.html#cvtColor(org.opencv.core.Mat,%20org.opencv.core.Mat,%20int)
        //Converts an image from one color space to another.
        //In case of a transformation to-from RGB color space, the order of the channels should be specified explicitly (RGB or BGR).
        // Note that the default color format in OpenCV is often referred to as RGB but it is actually BGR (the bytes are reversed).
        // So the first byte in a standard (24-bit) color image will be an 8-bit Blue component,
        // the second byte will be Green, and the third byte will be Red.
        // The fourth, fifth, and sixth bytes would then be the second pixel (Blue, then Green, then Red), and so on.
        /*The conventional ranges for R, G, and B channel values are:

        0 to 255 for CV_8U images
        0 to 65535 for CV_16U images
        0 to 1 for CV_32F images
        //https://docs.opencv.org/java/2.4.2/org/opencv/imgproc/Imgproc.html#Sobel(org.opencv.core.Mat,%20org.opencv.core.Mat,%20int,%20int,%20int)

        //Using the sobel operation, you can detect the edges of an image in both horizontal and vertical directions.
        /*This method accepts the following parameters −

        src − An object of the class Mat representing the source (input) image.

        dst − An object of the class Mat representing the destination (output) image.

        ddepth − An integer variable representing the depth of the image (-1)

        dx − An integer variable representing the x-derivative. (0 or 1)

        dy − An integer variable representing the y-derivative. (0 or 1)

        //https://docs.opencv.org/3.4/d2/de8/group__core__array.html#ga3460e9c9f37b563ab9dd550c4d8c4e7d

        В первом параметре указывается исходное изображение,
        Во втором — матрица, в которую будет записан результат операции
        Параметр ddepth задает глубину итоговой матрицы. Если указано значение –1,
        то глубина будет соответствовать глубине исходного изображения.
        Следует учитывать, что вычисленное значение может быть как положительным, так и отрицательным.
        Если мы используем исходное изображение с глубиной CV_8U, то в параметре ddepth лучше явно указать глубину
        CV_16S, CV_32F или CV_64F, иначе отрицательное значение в результате нормализации станет равно 0.
        Выполнить преобразование матрицы в тип CV_8U без потери отрицательных значений можно
        с помощью статического метода convertScaleAbs() из класса Core.
        Указываются параметры - производная по x или y

        Параметр dx задает порядок производной по оси X (0, 1 или 2), а параметр dy — по
    оси Y. Оба параметра не могут одновременно иметь значение 0.

     */


    @Override
    public Mat convertSobel(Mat image, int dx, int dy) {
        Mat grayImage = new Mat();

        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
        Mat dstSobel = new Mat();

        Imgproc.Sobel(grayImage, dstSobel, CvType.CV_32F, dx, dy);

        /*
Масштабирует, вычисляет абсолютные значения и преобразует результат в 8-битный.

Для каждого элемента входного массива функция convertScaleAbs последовательно выполняет три операции:
масштабирование, получение абсолютного значения, преобразование в 8-разрядный тип без знака:*/
        Core.convertScaleAbs(dstSobel, dstSobel);
        return dstSobel;

        //Из доки
        /*
         Imgproc.cvtColor(srcImage, grayImage, Imgproc.COLOR_BGR2GRAY);
         Mat dstSobelX = new Mat();
         Imgproc.Sobel(grayImage, dstSobelX, CvType.CV_32F, 1, 0);
         Mat dstSobelY = new Mat();
         Imgproc.Sobel(grayImage, dstSobelY, CvType.CV_32F, 0, 1);
         Imgcodecs.imwrite(destDirPath + "SobelX_" + srcFileName, dstSobelX);
         Imgcodecs.imwrite(destDirPath + "SobelY_" + srcFileName, dstSobelY); */
    }

    /*
     * Laplacian Operator is also a derivative operator which is used to find edges in an image. It is a second order derivative mask.
     * In this mask we have two further classifications one is Positive Laplacian Operator and other is Negative Laplacian Operator.*/

        /*Unlike other operators Laplacian didn’t take out edges in any particular direction but it takes out edges in following classification.

            Inward Edges
            Outward Edges*/

        /*This method accepts the following parameters −

        src − A Mat object representing the source (input image) for this operation.

        dst − A Mat object representing the destination (output image) for this operation.

        ddepth − A variable of the type integer representing depth of the destination image.
         //https://docs.opencv.org/java/2.4.2/org/opencv/imgproc/Imgproc.html#Laplacian(org.opencv.core.Mat,%20org.opencv.core.Mat,%20int)
        //https://www-tutorialspoint-com.translate.goog/opencv/opencv_laplacian_transformation.htm?_x_tr_sl=en&_x_tr_tl=ru&_x_tr_hl=ru&_x_tr_pto=op,wapp
*/


    @Override
    public Mat convertLaplace(Mat image, int ksize) {
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
        Mat dstLaplace = new Mat();

        /*В первом параметре указывается исходное изображение, а во втором — матрица,
            в которую будет записан результат операции. Параметр ddepth задает глубину итоговой матрицы.
            Доп параметр -  ksize задает размер ядра фильтра (положительное нечетное число).

            В параметре scale можно дополнительно указать масштабный коэффициент (значение по умолчанию: 1),
            а в параметре delta — значение, которое будет прибавлено к результату операции (значение по умолчанию: 0).*/

        Imgproc.Laplacian(grayImage, dstLaplace, CvType.CV_32F,ksize);
        Core.convertScaleAbs(dstLaplace, dstLaplace);
        return dstLaplace;

        /*
         String srcFileName = "wolf.jpg";
         Mat grayImage = new Mat();
         Mat srcImage = Imgcodecs.imread(dirPath + srcFileName, Imgcodecs.IMREAD_COLOR);
         Imgproc.cvtColor(srcImage, grayImage, Imgproc.COLOR_BGR2GRAY);
         Mat dstLaplace = new Mat();
         Imgproc.Laplacian(srcImage, dstLaplace, CvType.CV_32F);
         Mat absLaplasImg = new Mat();
         Core.convertScaleAbs(dstLaplace, absLaplasImg);
         Imgcodecs.imwrite(destDirPath + "Laplace_" + srcFileName, absLaplasImg);
         */
    }

    //https://docs.opencv.org/4.x/d6/d91/group__gapi__transform.html#gaa5d151bf720b9e4186ca3d415dc879c7
    //https://www.programcreek.com/java-api-examples/?class=org.opencv.core.Core&method=flip
    /*src: исходное изображение
 dst: целевая карта
flipCode: >0: перевернуть по оси y,
0: перевернуть по оси x,
<0: оси x и y переворачиваются одновременно(Повернуть на 180)*/

    /*The example scenarios of using the function are the following:
        Vertical flipping of the image (flipCode == 0) to switch between top-left and bottom-left image origin. T
        his is a typical operation in video processing on Microsoft Windows* OS.
        Horizontal flipping of the image with the subsequent horizontal shift and absolute difference calculation
        to check for a vertical-axis symmetry (flipCode > 0).
        Simultaneous horizontal and vertical flipping of the image with the subsequent shift and absolute difference calculation
        to check for a central symmetry (flipCode < 0). Reversing the order of point arrays (flipCode > 0 or flipCode == 0).
        Output image must be of the same depth as input one, size should be correct for given flipCode.*/
    @Override
    public Mat mirrorImage(Mat image, int flipCode) {
        Mat dstV = new Mat();

        Core.flip(image, dstV, flipCode);
        return dstV;
    }

    @Override
    public Mat repeatImage(Mat image, int ny, int nx) {
        Mat rotationImage = new Mat();
        /*
        Parameters
        src	input array to replicate.
        ny	Flag to specify how many times the src is repeated along the vertical axis.
        nx	Flag to specify how many times the src is repeated along the horizontal axis.*/
        Core.repeat(image, ny, nx, rotationImage);
        return rotationImage;
    }

    @Override
    public Mat unionImage(Mat mat1, Mat mat2, int axis) {

        /*Core.hconcat - по горизонтали
        Core.vconcat - по вертикали*/

        Mat unionImage = new Mat();

        List<Mat> src = Arrays.asList(mat1, mat2);

        switch (axis) {
            case 0:
                Core.vconcat(src ,unionImage);
                break;
            case 1:
                Core.hconcat(src, unionImage);
                break;
        }

        return unionImage;
    }


    public Mat unityImage(Mat imageOne, Mat imageTwo, String srcFileName, int axis){
        Mat dst = new Mat();
        List<Mat> listOfImages= Arrays.asList(imageOne, imageTwo);
        switch(axis){
            case 0:
                Core.vconcat(listOfImages ,dst);
                break;
            case 1:
                Core.hconcat( listOfImages,dst);
                break;}

        saveMatToFile(srcFileName+ "DASHA", dst);
        return dst;
    }

    @Override
    public Mat unionImageAddWeighted(Mat mat1, Mat mat2) {

        Mat unionImage = new Mat();
        /*Parameters:
src1 - first input array.
alpha - weight of the first array elements.
src2 - second input array of the same size and channel number as src1.
beta - weight of the second array elements.
gamma - scalar added to each sum.
dst - output array that has the same size and number of channels as the input arrays*/

        Core.addWeighted(mat1, 1, mat2, 0.5, 1, unionImage);

        return unionImage;
    }

    @Override
    public Mat resizeImage(Mat image, int width, int height) {
        Mat resizeImage = new Mat();
        Imgproc.resize(image, resizeImage, new Size(width, height));
        return resizeImage;
    }

    @Override
    public Mat RotationImage(Mat image, int angle_of_rotation, boolean isCut) {

        Point center = new Point(image.width()/2, image.height()/2);
        double scale = 1;
        Mat outImage = new Mat();
        /*Вычисляет аффинную матрицу 2D-вращения.
                    /* Parameters
                     center	Center of the rotation in the source image.
                             angle	Rotation angle in degrees.
                             Positive values mean counter-clockwise rotation //положительные знач- против часовой
                             (the coordinate origin is assumed to be the top-left corner).
                             scale	Isotropic scale factor.*/
                    /*В первом параметре указываются координаты точки, вокруг которой производится
                    вращение, во втором — угол в градусах (положительное значение означает поворот
                    против часовой стрелки), а в третьем — коэффициент масштабирования.*/
        Mat M = Imgproc.getRotationMatrix2D(center, angle_of_rotation, 1);
        if (isCut) {
            /*
            * //диагональ прямоугольника
            double size = Math.sqrt(image.width() * image.width() + image.height() * image.height());
            double scaleX = image.width() / size;
            double scaleY = image.height() / size;
            scale = Math.min(scaleX, scaleY);*/

            Rect rect = new RotatedRect(
                    new Point(image.width() / 2, image.height() / 2),
                    new Size(image.width(), image.height()), angle_of_rotation).boundingRect();
            // Корректировка матрицы трансформации
            double[] arrX = M.get(0, 2);
            double[] arrY = M.get(1, 2);
            arrX[0] -= rect.x;
            arrY[0] -= rect.y;
            M.put(0, 2, arrX);
            M.put(1, 2, arrY);


            Imgproc.warpAffine(image, outImage, M, rect.size(),
                    Imgproc.INTER_LINEAR, Core.BORDER_TRANSPARENT, new Scalar(255, 255, 255, 255));
        }

        else{
            Imgproc.warpAffine(image, outImage, M,new Size(image.width(), image.height()),
                Imgproc.INTER_LINEAR, Core.BORDER_TRANSPARENT,new Scalar(0,0,255,255));}



        /*
        * Applies an affine transformation to an image.

Parameters:
src – Source image
dst – Destination image; will have size dsize and the same type as src
M – 2\times 3 transformation matrix
dsize – Size of the destination image
flags – A combination of interpolation methods, see resize() , and the optional flag WARP_INVERSE_MAP that means that M is the inverse transformation ( \texttt{dst}\rightarrow\texttt{src} )
borderMode – The pixel extrapolation method, see borderInterpolate() . When the borderMode=BORDER_TRANSPARENT , it means that the pixels in the destination image that corresponds to the “outliers” in the source image are not modified by the function
borderValue – A value used in case of a constant border. By default it is 0*/

        /*Статический метод warpAffine() из класса Imgproc позволяет выполнить различные
трансформации изображения (смещение, масштабирование, вращение и сдвиг). */
        /*В первом параметре указывается исходное изображение, а во втором — матрица,
в которую будет записан результат операции. Следующий Параметр  задает матрицу трансформации */
        //Core.BORDER_TRANSPARENT - прозрачная рамка

        /*INTER_NEAREST — интерполяция методом ближайшего соседа. При уменьшении
изображения наклонные линии будут отображаться «лесенкой», а при увеличении — пикселы просто повторяются. Это самый быстрый алгоритм изменения
размеров.*/

        /*INTER_AREA — дает более качественный результат из всех методов при уменьшении изображения. При увеличении результат похож на результат метода
INTER_NEAREST. По времени выполняется дольше, чем описанные далее билинейная и бикубическая интерполяции. */

        /*
        * INTER_LINEAR — билинейная интерполяция (используется по умолчанию). При
уменьшении изображения немного уступает в качестве методу INTER_AREA, но
выполняется почти в два раза быстрее. При увеличении изображения по качеству уступает методу INTER_CUBIC, но выполняется почти в два раза быстрее.
Пожалуй, самый оптимальный алгоритм изменения размеров по соотношению
скорости выполнения и качества.*/

        /*INTER_CUBIC — бикубическая интерполяция (окрестность 4 на 4 пиксела). Самый
лучший метод для увеличения изображения. */

        /*INTER_LANCZOS4 — фильтр Ланцоша (окрестность 8 на 8 пикселов). Самый долгий
метод по времени выполнения.*/

        return outImage;
    }

    @Override
    public Mat imageShift(Mat image,int count_x, int count_y) {

        Mat M = new Mat(2, 3, CvType.CV_32FC1);
        M.put(0, 0,
                1, 0, count_x,
                0, 1, count_y
        );
        log.debug("Матрица трансформации");
        log.debug(M.dump());

        Mat mat = new Mat();
        Imgproc.warpAffine(image, mat, M,
                new Size(image.width() , image.height()),
                Imgproc.INTER_LINEAR, Core.BORDER_CONSTANT,
                new Scalar(0, 0, 0, 255));


        return mat;
    }



    @Override
    public Mat ChangeOfPerspective(Mat img, int x, int y) {
        Mat grayImage = new Mat();
        Imgproc.cvtColor(img, grayImage, Imgproc.COLOR_BGR2GRAY);
        Mat src = new Mat(4, 1, CvType.CV_32FC2);
        src.put(0, 0,
                0, 0, // Левый верхний угол
                img.width() - 1, 0, // Правый верхний угол
                0, img.height() - 1, // Левый нижний угол
                img.width() - 1, img.height() - 1 // Правый нижний угол
        );
        Mat dst = new Mat(4, 1, CvType.CV_32FC2);
        dst.put(0, 0,
                x, y, // Левый верхний угол
                img.width() - x+1, y, // Правый верхний угол
                0, img.height() - 1, // Левый нижний угол
                img.width() - 1, img.height() - 1 // Правый нижний угол
        );
        Mat M = Imgproc.getPerspectiveTransform(src, dst);
        Mat img2 = new Mat();
        Imgproc.warpPerspective(img, img2, M, img.size(),
                Imgproc.INTER_LINEAR, Core.BORDER_CONSTANT,
                new Scalar(255, 255, 255, 255));
        return img2;
    }





    //4 лабораторная работа

    /*
    * Во время этой операции изображение сворачивается с помощью блочного фильтра (нормализуется).
    * В этом процессе центральный элемент изображения заменяется средним значением всех пикселей в области ядра.
    * Центр ядра считается точкой привязки, если имеется отрицательное значение.*/
        @Override
        public Mat baseBlur(Mat src, Mat dst, Size ksize) {
            Imgproc.blur(src, dst, ksize, new Point(-1, -1));
            //Imgproc.blur(src, dst, ksize);
            /* усредняющий тип
            * public static void blur(Mat src, Mat dst, Size ksize)
где: src — матрица исходного изображения,dst — матрица обработанного изображения, ksize
— размер ядра;
*
Так же можно задать область изображения, которую нужно отфильтровать:
public static void blur(Mat src, Mat dst, Size ksize, Point anchor, int borderType)
где:
* anchor - узловая точка, значение по умолчанию (-1, -1) означает, что якорь находится в
центре ядра,
* borderType — параметр который задает правило преобразования «приграничных» с
ядром пикселей, иными словами используется для экстраполяции пикселей.*/
            //showImage(dst);
            return src;
        }

        /*
        * Основное отличие Гауссовского фильтра от предыдущего заключается в том, что в ядре
свертки максимальный вес имеет центральный пиксел, а остальные соответствуют Гауссовскому
распределению. Для реализации данной фильтрации используется метод:
public static void GaussianBlur(Mat src, Mat dst, Size ksize, double sigmaX [, double sigmaY], int
borderType)
где: src — матрица исходного изображения,
* dst — матрица обработанного изображения,
* ksize
— размер ядра, для получения симметричного распределения размер ядра должен быть задан
нечетными положительными числами,
* sigma — стандартное среднеквадратичное отклонение по соответствующим осям.
* Если sigmaY не указано, то он берется равным sigmaX,
*  borderType — параметр который задает правило преобразования «приграничных» с ядром пикселей;

* В общем случае рекомендуется устанавливать значение параметра sigmaX равное 0. Тогда
среднеквадратичное отклонение будет расчитываться по формуле :
sigma = 0.3*((ksize-1)*0.5 - 1) + 0.8*/

    /*
    * enum  	cv::BorderTypes {
  cv::BORDER_CONSTANT = 0,   рамка заливается цветом, указанным в параметре value
  cv::BORDER_REPLICATE = 1,  -  повтор крайних пикселов
  cv::BORDER_REFLECT = 2,   - зеркальное отображение
  cv::BORDER_WRAP = 3, - not supported  повтор изображения
  cv::BORDER_REFLECT_101 = 4,
  cv::BORDER_TRANSPARENT = 5,
  cv::BORDER_REFLECT101 = BORDER_REFLECT_101, - зеркально, но граница не выводится дважды
  cv::BORDER_DEFAULT = BORDER_REFLECT_101,
  cv::BORDER_ISOLATED = 16
}*/

    @Override
        public Mat gaussianBlur(Mat src, Mat dst, Size ksize, double sigmaX, double sigmaY, int borderType) {
            Imgproc.GaussianBlur(src, dst, ksize, sigmaX, sigmaY, borderType);
            return src;
        }

        /*
        * Медианное сглаживание широко используется в алгоритмах обнаружения краев, потому, что
при определенных условиях оно сохраняет края, удаляя шум. Идея медианной фильтрации,
заключается в использовании значения медианного пиксела в ядре свертки, вместо среднего
значения. Для этого реализован метод:
public static void medianBlur(Mat src, Mat dst, int ksize)
где: src — матрица исходного изображения, dst — матрица обработанного изображения, ksize
— размер ядра он должен быть нечетным и больше 1, например: 3, 5, 7.
Данный типа фильтра используется в случаях когда параметры «графического шума»
слишком велики, т.е. это выглядит как большой изолированный выброс, что может значительно
сместить среднее значение.*/

    /*метод вычисляет не свертку,
а медиану (выбирается средний элемент, и его значение становится результатом операции). */
        @Override
        public Mat medianBlur(Mat src, Mat dst, int ksize) {
            Imgproc.medianBlur(src, dst, ksize);
            return src;
        }

        /*
        * Bilateral (Двусторонняя) размытость является одним из самых современных фильтров для
сглаживания изображения и снижения уровня шума. Недостатком этого типа фильтра является то,
что его работа занимает больше времени для того, чтобы отфильтровать входное изображение. В
то время как описанные выше фильтры выполняют размывание шума и краев, двусторонний
фильтр сохраняет края неизменными, за счёт учёта пространственного расстояния и различия
интенсивности пиксела при расчёте среднего значения. Пикселы с интенсивностью различия выше
среднего порога, не включаются в итоговое изображение. Сигнатура метода выглядит следующим
образом:
public static void bilateralFilter(Mat src, Mat dst, int d, double sigmaColor, double sigmaSpace, [int
borderType])
где: src — матрица исходного изображения,
* dst — матрица обработанного изображения,
*  d —диаметр окрестности фильтрации, если значение отрицательное, то диаметр будет рассчитываться
из параметра sigmaSpace.
* SigmaColor — сигма фильтр в цветовом пространстве.
SigmaSpace — сигма фильтр в координатном пространстве. Не вдаваясь в детали реализации, с
помощью данных фильтров, достигается акварельный эффект, который является очень полезным
на первом шаге сегментации изображения.
* borderType — параметр который задает правило
преобразования «приграничных» с ядром пикселей;
*/
        @Override
        public Mat bilateralFilter(Mat src, Mat dst, int d, double sigmaColor, double sigmaSpace, int borderType) {
            Imgproc.bilateralFilter(src, dst, d, sigmaColor, sigmaSpace, borderType);
            return dst;
        }

        /*Морфологические операции обычно используются в контексте решения следующих задач:
изоляции элементов, удаления «графического шума», соединение разрозненных фрагментов в
единое целое. Для решения этих задач используется две трансформации: размытие (erosion) и
расширение (dilation), которые изменяют форму исходного объекта.
Эрозия (размывание/сужение) изображения обычно используется для избавления от
случайных вкраплений на изображении. Идея состоит в том, что вкрапления при размывании
устранятся, тогда как крупные и соответсвенно более визуально-значимые регионы остаются.
Растягивание (расширение) же, по идее, так же должно устранять шум и способствовать
объединению областей изображения, которые были разделены шумом, тенями, etc.

Применение же небольшого растягивания должно сплавить эти области в одну. Морфологические операции, чаще
всего, применяются над двоичными изображениями, которые получаются после порогового
преобразования (thresholding). Основная идея выполнения этих операций заключается в
следующем. Все изображение сканируется сверточным ядром заданного размера. В рамках ядра, в
зависимости от типа операции вычисляется или локальный минимум или локальный максимум по
яркости пиксела. Операция сужения, размывает изображение на основе локальных минимумов —
т.е. будут увеличиваться тёмные области. Операция расширения растягивает изображение
формируя его из локальных максимумов — т.е. будут увеличиваться светлые области). В качестве
примера, предлагаю рассмотреть, авторский тестовый метод, который необходимо будет
адаптировать, для выполнения рабочего задания. Суть метода заключается в проверке влияния
размера ядра свертки на качество итоговой картинки для операции размытия, которая реализуется
с помощью метода erode класса Imgproc и операции расширения - dilate того-же класса

*/


/*
* Статический метод erode() из класса Imgproc выполняет обратную операцию: расширяет темные области и сужает светлые.*/

    /*
    * В первом параметре указывается исходное изображение (тип CV_8U, CV_16U, CV_16S,
CV_32F или CV_64F), а во втором — матрица, в которую будет записан результат операции.
* Параметр kernel задает матрицу с ядром. Если в параметре kernel указано
значение Mat(), то создается матрица размером 3x3 с ядром прямоугольной формы.
* Создать произвольную матрицу с ядром позволяет статический метод
getStructuringElement() и*/
    @Override
    public void morphingErode(Mat defaultMat) {
        double[] sizes = {3, 5, 7, 9, 13, 15};
        for (double size : sizes) {
            Mat morphRect = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(size, size));
          //  Mat morphGradient = Imgproc.getStructuringElement(Imgproc.MORPH_GRADIENT, new Size(size, size));
            Mat morphCross = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(size, size));
            Mat morphEllips = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(size, size));

            Mat dstmorphRect = defaultMat.clone();
            Imgproc.erode(defaultMat, dstmorphRect, morphRect);
           // showImage(dst);
            String modFileRect = buildImageName(Constants.IMAGE_PATH_MORFOLOGY,"erode_rect"+size);
            modFileRect = modFileRect + ".jpg";
            Imgcodecs.imwrite(modFileRect, dstmorphRect);

/*

            Mat dstmorphGradient = defaultMat.clone();
            Imgproc.erode(defaultMat, dstmorphGradient, morphGradient);
            String modFileGradient = buildImageName(Constants.IMAGE_PATH_MORFOLOGY,"erode_gradient"+size);
            modFileGradient = modFileGradient + ".jpg";
            Imgcodecs.imwrite(modFileGradient, dstmorphGradient);
            // showImage(dst);*/

            Mat dstmorphCross = defaultMat.clone();
            Imgproc.erode(defaultMat, dstmorphCross, morphCross);
            String modFileBlackhat = buildImageName(Constants.IMAGE_PATH_MORFOLOGY,"erode_cross"+size);
            modFileBlackhat = modFileBlackhat + ".jpg";
            Imgcodecs.imwrite(modFileBlackhat, dstmorphCross);
            // showImage(dst);

            Mat dstmorphEllips = defaultMat.clone();
            Imgproc.erode(defaultMat, dstmorphEllips, morphEllips);
            String modFileEllips = buildImageName(Constants.IMAGE_PATH_MORFOLOGY,"erode_ellipse"+size);
            modFileEllips = modFileEllips + ".jpg";
            Imgcodecs.imwrite(modFileEllips, dstmorphEllips);
            // showImage(dst);


/*

            String modFilePath1 = buildImageName(Constants.IMAGE_PATH_MORFOLOGY,"mrf_ellipse_rect"+size);
            modFilePath1 = modFilePath1 + ".jpg";
            Imgcodecs.imwrite(modFilePath1, dst1);

            Mat dst1_1 = defaultMat.clone();
            Imgproc.morphologyEx(defaultMat, dst1_1, Imgproc.MORPH_GRADIENT, morphRect);
            String modFilePath2 = buildImageName(Constants.IMAGE_PATH_MORFOLOGY,"mrf_gradient_rect"+size);
            modFilePath2 = modFilePath2 + ".jpg";
            Imgcodecs.imwrite(modFilePath2, dst1_1);

            Mat dst1_2 = defaultMat.clone();
            Imgproc.morphologyEx(defaultMat, dst1_2, Imgproc.MORPH_BLACKHAT, morphRect);
            String modFilePath3 = buildImageName(Constants.IMAGE_PATH_MORFOLOGY,"mrf_blackhat_rect"+size);
            modFilePath3 = modFilePath3 + ".jpg";
            Imgcodecs.imwrite(modFilePath3, dst1_2);

*/

        }
    }


/*Статический метод dilate() из класса Imgproc расширяет светлые области и сужает
темные.*/
    @Override
    public void morphingDilate(Mat defaultMat) {

        double[] sizes = {3, 5, 7, 9, 13, 15};
        for (double size : sizes) {
            Mat morphRect = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(size, size));
            //  Mat morphGradient = Imgproc.getStructuringElement(Imgproc.MORPH_GRADIENT, new Size(size, size));
            Mat morphCross = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(size, size));
            Mat morphEllips = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(size, size));

            Mat dstmorphRect = defaultMat.clone();
            Imgproc.dilate(defaultMat, dstmorphRect, morphRect);
            // showImage(dst);
            String modFileRect = buildImageName(Constants.IMAGE_PATH_MORFOLOGY,"dilate_rect"+size);
            modFileRect = modFileRect + ".jpg";
            Imgcodecs.imwrite(modFileRect, dstmorphRect);

/*

            Mat dstmorphGradient = defaultMat.clone();
            Imgproc.erode(defaultMat, dstmorphGradient, morphGradient);
            String modFileGradient = buildImageName(Constants.IMAGE_PATH_MORFOLOGY,"erode_gradient"+size);
            modFileGradient = modFileGradient + ".jpg";
            Imgcodecs.imwrite(modFileGradient, dstmorphGradient);
            // showImage(dst);*/

            Mat dstmorphCross = defaultMat.clone();
            Imgproc.dilate(defaultMat, dstmorphCross, morphCross);
            String modFileBlackhat = buildImageName(Constants.IMAGE_PATH_MORFOLOGY,"dilate_cross"+size);
            modFileBlackhat = modFileBlackhat + ".jpg";
            Imgcodecs.imwrite(modFileBlackhat, dstmorphCross);
            // showImage(dst);

            Mat dstmorphEllips = defaultMat.clone();
            Imgproc.dilate(defaultMat, dstmorphEllips, morphEllips);
            String modFileEllips = buildImageName(Constants.IMAGE_PATH_MORFOLOGY,"dilate_ellipse"+size);
            modFileEllips = modFileEllips + ".jpg";
            Imgcodecs.imwrite(modFileEllips, dstmorphEllips);
            // showImage(dst);
    }}


    //5 Лабораторная работа

    @Override
    public void toFill(Integer initVal, Mat defaultMat){
       // showImage(defaultMat);
        //Обратите внимание: по умолчанию результат операции будет записан в ту же самую матрицу.
        //seedPoint - координаты точки начала анализа параметров цвета заданного изображения,
        //задает начальную точку внутри заливаемой области
        Point seedPoint = new Point(0,0);
        //newVal - цвет (rgb-параметры) области «заливки»
        Scalar newVal = new Scalar(255,255,0);
        //loDiff upDiff - диапазон параметров цвета (верхняя и нижняя граница), который следует
        //заменить заданным цветом области «заливки»;
        Scalar loDiff = new Scalar(initVal,initVal,initVal);
        Scalar upDiff = new Scalar(initVal,initVal,initVal);
        Mat mask = new Mat();
        //В объект, указанный в параметре rect,будут записаны координаты и размеры прямоугольной области, ограничивающей
        //область заливки.
        Imgproc.floodFill(defaultMat, mask, seedPoint, newVal, new Rect(), loDiff, upDiff,
                4 | (255 << 8));
        //Imgproc.FLOODFILL_FIXED_RANGE + 8
        //4 | (255 << 8)
        /*
        * Параметр flags задает дополнительные флаги. Если указано значение 4 (значение по умолчанию), то учитываются
            только четыре соседних пиксела,
        * а если 8 — то восемь соседних. Следующие восемь битов задают значение для маски: от 1 (значение по умолчанию)
        * до 255. Записать значение 255 можно так:
        4 | (255 << 8)
        Дополнительно можно указать следующие флаги (константы из класса Imgproc):
         FLOODFILL_FIXED_RANGE — если флаг установлен, то параметры loDiff и upDiff
        задают разницу между начальным и конечным пикселами. В противном случае
        они задают разницу для соседних пикселов.
         FLOODFILL_MASK_ONLY — если флаг установлен, то метод не станет изменять исходное изображение.
         * Результат будет записан в матрицу, указанную в параметре mask. */
       // convertionService.saveMatToFile("floodFill", defaultMat);

        String modFilePath1 = buildImageName(Constants.IMAGE_PATH,"floodFill"); //D:/computerVision/src/main/resources/images/testImage.jpg.jpg
        modFilePath1 = modFilePath1 + ".jpg";
        Imgcodecs.imwrite(modFilePath1, defaultMat);
      //  showImage(defaultMat);


    }

    /*
    * Следующим механизмом сегментации изображений является - Image pyramids. Это
подразумевает получение уменьшенных или увеличенных копий исходного изображения с потерей
качества. Если полученный набор изображений наложить друг на друга то получится пирамида.
По умолчанию размер полученного изображения составляет четверть от размеров исходного, т.е.
если размер исходного изображения 800х800, то размер уменьшенного на первом шаге будет
400Х400 и т.п. Как правило для «размытия» исходного изображения используется Гауссовский
фильтр.*/

    public void toPyr(Mat defaultMat)  {
      //  Mat defaultMat = noiseMat(350, 350);


        //В «Пирамиде вниз» изображение сначала размыто, а затем уменьшено.
        Mat out_down = new Mat();
        //Imgproc.pyrDown(defaultMat, mask);
        // Applying pyrDown on the Image
        Imgproc.pyrDown(defaultMat, out_down, new Size(defaultMat.cols()/2,  defaultMat.rows()/2),
                Core.BORDER_DEFAULT);
        String modFilePath1 = buildImageName(Constants.IMAGE_PATH,"mask_pyrDown"); //D:/computerVision/src/main/resources/images/testImage.jpg.jpg
        modFilePath1 = modFilePath1 + ".jpg";
        Imgcodecs.imwrite(modFilePath1, out_down);


        //В «Пирамиде вверх» изображение сначала дискретизируется, а затем размывается.
        //Imgproc.pyrUp(defaultMat, mask);
        // Applying pyrUp on the Image
        Mat out_up = new Mat();
        Imgproc.pyrUp(defaultMat, out_up, new Size(defaultMat.cols()*2,  defaultMat.rows()*2), Core.BORDER_DEFAULT);
        String modFilePath2 = buildImageName(Constants.IMAGE_PATH,"mask_pyrUp"); //D:/computerVision/src/main/resources/images/testImage.jpg.jpg
        modFilePath2 = modFilePath2 + ".jpg";
        Imgcodecs.imwrite(modFilePath2, out_up);

        /*Mat out_sub = new Mat();
        Core.subtract(defaultMat, out_down, out_down);

        String modFilePath3 = buildImageName(Constants.IMAGE_PATH,"mask_subtract"); //D:/computerVision/src/main/resources/images/testImage.jpg.jpg
        modFilePath3 = modFilePath3 + ".jpg";
        Imgcodecs.imwrite(modFilePath3, out_down);
*/

    }



    public void toSquare(Mat defaultMat, double height, double width){
        Mat grayImage = new Mat();
        Imgproc.cvtColor(defaultMat, grayImage, Imgproc.COLOR_BGR2GRAY);
        String modFilePath1 = buildImageName(Constants.IMAGE_PATH,"toSquare_grey_image"); //D:/computerVision/src/main/resources/images/testImage.jpg.jpg
        modFilePath1 = modFilePath1 + ".jpg";
        Imgcodecs.imwrite(modFilePath1, grayImage);


//Выполните шумоподавление изображения с использованием алгоритма шумоподавления нелокальных средств
        Mat denoisingImage = new Mat();
        Photo.fastNlMeansDenoising(grayImage, denoisingImage);
        String modFilePath2 = buildImageName(Constants.IMAGE_PATH,"toSquare_denoising_image"); //D:/computerVision/src/main/resources/images/testImage.jpg.jpg
        modFilePath2 = modFilePath2 + ".jpg";
        Imgcodecs.imwrite(modFilePath2, denoisingImage);




//это метод, который улучшает контрастность изображения, чтобы расширить диапазон интенсивности
        //Применение выравнивания гистограммы начинается с вычисления гистограммы интенсивности пикселей
        // во входном изображении в оттенках серого/одноканальном изображении:
        Mat histogramEqualizationImage = new Mat();
        Imgproc.equalizeHist(denoisingImage, histogramEqualizationImage);
        String modFilePath3 = buildImageName(Constants.IMAGE_PATH,"toSquare_histogramEqualization_image"); //D:/computerVision/src/main/resources/images/testImage.jpg.jpg
        modFilePath3 = modFilePath3 + ".jpg";
        Imgcodecs.imwrite(modFilePath3, histogramEqualizationImage);


//увеличили темные участки
        Mat morphologicalOpeningImage = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
        Imgproc.morphologyEx(histogramEqualizationImage, morphologicalOpeningImage, Imgproc.MORPH_RECT, kernel);
        String modFilePath4 = buildImageName(Constants.IMAGE_PATH,"toSquare_morphologicalOpening_image"); //D:/computerVision/src/main/resources/images/testImage.jpg.jpg
        modFilePath4 = modFilePath4 + ".jpg";
        Imgcodecs.imwrite(modFilePath4, morphologicalOpeningImage);


//Функция Core.subtract служит для захвата фрагмента изображения с исходного.
        //границы на темном фоне
        Mat subtractImage = new Mat();
        Core.subtract(histogramEqualizationImage, morphologicalOpeningImage, subtractImage);
        String modFilePath5 = buildImageName(Constants.IMAGE_PATH,"toSquare_subtract_image"); //D:/computerVision/src/main/resources/images/testImage.jpg.jpg
        modFilePath5 = modFilePath5 + ".jpg";
        Imgcodecs.imwrite(modFilePath5, subtractImage);


// Это разделение основано на изменении интенсивности между пикселями объекта и пикселями фона.
//Чтобы отличить интересующие нас пиксели от остальных (которые в конечном итоге будут отклонены), мы выполняем сравнение значения
// интенсивности каждого пикселя относительно порогового значения (определяемого в соответствии с решаемой задачей).
//После того, как мы правильно разделили важные пиксели, мы можем задать им определенное значение для их идентификации
// (т.Е. Мы можем присвоить им значение 0 (черный), 255 (белый) или любое значение, которое соответствует вашим потребностям).
        Mat thresholdImage = new Mat();
        double threshold = Imgproc.threshold(subtractImage, thresholdImage, 50, 255, Imgproc.THRESH_OTSU);
        thresholdImage.convertTo(thresholdImage, CvType.CV_16SC1);
        String modFilePath6 = buildImageName(Constants.IMAGE_PATH,"toSquare_threshold_image"); //D:/computerVision/src/main/resources/images/testImage.jpg.jpg
        modFilePath6 = modFilePath6 + ".jpg";
        Imgcodecs.imwrite(modFilePath6, thresholdImage);


//выделяет границы объекта
        Mat edgeImage = new Mat();
        thresholdImage.convertTo(thresholdImage, CvType.CV_8U);
        Imgproc.Canny(thresholdImage, edgeImage, threshold, threshold * 3, 3, true);
        String modFilePath7 = buildImageName(Constants.IMAGE_PATH,"toSquare_canny_image"); //D:/computerVision/src/main/resources/images/testImage.jpg.jpg
        modFilePath7 = modFilePath7 + ".jpg";
        Imgcodecs.imwrite(modFilePath7, edgeImage);

        //Увеличиваем светлые области
        Mat dilatedImage = new Mat();
        Imgproc.dilate(thresholdImage, dilatedImage, kernel);
        String modFilePath8 = buildImageName(Constants.IMAGE_PATH,"toSquare_dilated_image"); //D:/computerVision/src/main/resources/images/testImage.jpg.jpg
        modFilePath8 = modFilePath8 + ".jpg";
        Imgcodecs.imwrite(modFilePath8, dilatedImage);

        // поиск контуров
        /*
        * В первом параметре указывается исходное черно-белое изображение (8 битов, один
канал). Любое значение больше 1, считается 1, а нулевые значения так и остаются
нулевыми. Если в параметре mode указана константа RETR_CCOMP, то исходная матрица может иметь тип CV_32SC1.
* Обратите внимание: метод findContours() в вер*/
        /* Параметр mode задает режим поиска контуров. М
        * RETR_EXTERNAL — найти только крайние внешние контуры.
        * RETR_LIST — найти все контуры без установления иерархии.
        * RETR_CCOMP — найти все контуры и организовать их в двухуровневую структуру.
        * RETR_TREE — найти все контуры и организовать полную иерархию вложенных
контуров. */

        /*
        * Параметр method задает способ описания найденных контуров. Можно указать следующие константы из класса Imgproc:
 CHAIN_APPROX_NONE — сохраняются все точки контура. Формат:
public static final int CHAIN_APPROX_NONE
 CHAIN_APPROX_SIMPLE — горизонтальные, вертикальные и диагональные сегменты
сжимаются, и указываются только их конечные точки. Например, прямая линия
будет закодирована двумя точками. Формат:
public static final int CHAIN_APPROX_SIMPLE
 CHAIN_APPROX_TC89_KCOS и CHAIN_APPROX_TC89_L1 — используется алгоритм Teh-Chin. Ф*/
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(dilatedImage, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        log.debug("toSquare");

        contours.sort(Collections.reverseOrder(Comparator.comparing(Imgproc::contourArea)));
        int i = 0; //кол-во прямоугольников на фотографии
        for (MatOfPoint contour : contours.subList(0, 1)) {
            MatOfPoint2f point2f = new MatOfPoint2f();
            MatOfPoint2f approxContour2f = new MatOfPoint2f();
            MatOfPoint approxContour = new MatOfPoint();
            contour.convertTo(point2f, CvType.CV_32FC2);
            double arcLength = Imgproc.arcLength(point2f, true);
            Imgproc.approxPolyDP(point2f, approxContour2f, 0.03 * arcLength, true);
            approxContour2f.convertTo(approxContour, CvType.CV_32S);
            Rect rect = Imgproc.boundingRect(approxContour);
            Mat submat = defaultMat.submat(rect);
            double ratio = (double) rect.height / rect.width;
          //  double h = submat.height();
           // double w = submat.width();
            if (Math.abs(0.3 - ratio) > 5) {
                continue;
            }

            i++;


           // log.debug(submat);
            Imgproc.resize(submat, submat, new Size(400, 400 * ratio));

            String modFilePath9 = buildImageName(Constants.IMAGE_PATH,"toSquare_submat_image");
          //  log.debug(modFilePath9);
            modFilePath9 = modFilePath9  +".jpg";

            Imgcodecs.imwrite(modFilePath9, submat);

        }

        if (i !=0){
            log.trace("На изображении присутствует " + i + " прямоугольников");
        }
        else {
            log.trace("На изображении нет таких прямоугольников");





        }

    }

    protected Mat noiseMat(int height, int width) {
        Mat noiseMat = new Mat(new Size(width, height), CV_8UC3, new Scalar(0, 0, 0));
        Core.randn(noiseMat, 20, 50);
        Core.add(noiseMat, noiseMat, noiseMat);
        return noiseMat;

    }



    /*
    *
    public static void Segmentation(Mat grayImage, Mat srcImage, double height, double width) {


        Mat denoisingImage = new Mat();
        Photo.fastNlMeansDenoising(grayImage, denoisingImage);
        Imgcodecs.imwrite(Constant.PATH_TO_PROJECT_RESOURCES_lab_5 + "noNoise.jpg", denoisingImage);


        Mat histogramEqualizationImage = new Mat();
        Imgproc.equalizeHist(denoisingImage, histogramEqualizationImage);
        Imgcodecs.imwrite(Constant.PATH_TO_PROJECT_RESOURCES_lab_5 + "histogramEq.jpg", histogramEqualizationImage);


        Mat morphologicalOpeningImage = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
        Imgproc.morphologyEx(histogramEqualizationImage, morphologicalOpeningImage, Imgproc.MORPH_RECT, kernel);
        Imgcodecs.imwrite(Constant.PATH_TO_PROJECT_RESOURCES_lab_5 + "morphologicalOpening.jpg", morphologicalOpeningImage);

        Mat subtractImage = new Mat();
        Core.subtract(histogramEqualizationImage, morphologicalOpeningImage, subtractImage);
        Imgcodecs.imwrite(Constant.PATH_TO_PROJECT_RESOURCES_lab_5 + "subtract.jpg", subtractImage);

        Mat thresholdImage = new Mat();
        double threshold = Imgproc.threshold(subtractImage, thresholdImage, 50, 255, Imgproc.THRESH_OTSU);
        Imgcodecs.imwrite(Constant.PATH_TO_PROJECT_RESOURCES_lab_5 + "threshold.jpg", thresholdImage);
        thresholdImage.convertTo(thresholdImage, CvType.CV_16SC1);

        Mat edgeImage = new Mat();
        thresholdImage.convertTo(thresholdImage, CvType.CV_8U);
        Imgproc.Canny(thresholdImage, edgeImage, threshold, threshold * 3, 3, true);
        Imgcodecs.imwrite(Constant.PATH_TO_PROJECT_RESOURCES_lab_5 + "edge.jpg", edgeImage);

        Mat dilatedImage = new Mat();
        Imgproc.dilate(thresholdImage, dilatedImage, kernel);
        Imgcodecs.imwrite(Constant.PATH_TO_PROJECT_RESOURCES_lab_5 + "dilation.jpg", dilatedImage);

        ArrayList<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(dilatedImage, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        contours.sort(Collections.reverseOrder(Comparator.comparing(Imgproc::contourArea)));

        int i = 0; //кол-во прямоугольников на фотографии
        for (MatOfPoint contour : contours.subList(0, 10))
        {
            System.out.println(Imgproc.contourArea(contour));
            MatOfPoint2f point2f = new MatOfPoint2f();
            MatOfPoint2f approxContour2f = new MatOfPoint2f();
            MatOfPoint approxContour = new MatOfPoint();
            contour.convertTo(point2f, CvType.CV_32FC2);
            double arcLength = Imgproc.arcLength(point2f, true);
            Imgproc.approxPolyDP(point2f, approxContour2f, 0.03 * arcLength, true);
            approxContour2f.convertTo(approxContour, CvType.CV_32S);
            Rect rect = Imgproc.boundingRect(approxContour);
            Mat submat = srcImage.submat(rect);

            double ratio = (double) rect.height / rect.width;
            double h = submat.height();
            double w = submat.width();
            if (Math.abs(submat.height() - height) > 5 || Math.abs(submat.width() - width) > 5) {
                continue;
            }

            i++;

            Imgproc.resize(submat, submat, new Size(400, 400 * ratio));
            Imgcodecs.imwrite(Constant.PATH_TO_PROJECT_RESOURCES_lab_5 + "result_" + i + "_" + contour.hashCode() + ".jpg", submat);
        }

        if (i !=0){
            log.trace("На изображении присутствует " + i + " прямоугольников");
        }
        else {
            log.trace("На изображении нет таких прямоугольников");
        }
    }*/

    @Override
    public Mat canny(Mat src){

        Mat grayImage = new Mat();
        Imgproc.cvtColor(src, grayImage, Imgproc.COLOR_BGR2GRAY);
       // Mat dstLaplace = new Mat();
      //  Mat grayImage = new Mat();
      //  Mat srcImage = Imgcodecs.imread(dirPath + srcFileName, Imgcodecs.IMREAD_COLOR);
      //  showImage(srcImage,"source" );
      //  Imgproc.cvtColor(srcImage, grayImage, Imgproc.COLOR_BGR2GRAY);
        Mat detectedEdges = new Mat();
        Imgproc.blur(grayImage, detectedEdges, new Size(3, 3));
        Mat thresholdImage = new Mat();
        //Из серого в черно белое
        double threshold = Imgproc.threshold(grayImage, thresholdImage, 0, 255, Imgproc.THRESH_OTSU);
        Imgproc.Canny(detectedEdges, detectedEdges, threshold, threshold * 3);
        //showImage(detectedEdges,"detectedEdges");


        return detectedEdges;

    }

}



















