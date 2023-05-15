package ru.sfedu.computer_vision.api.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import ru.sfedu.computer_vision.Constants;
import ru.sfedu.computer_vision.api.ImageService;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;


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
        frame.setSize(frame.getToolkit().getScreenSize().width, frame.getToolkit().getScreenSize().height);
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
            frame.setVisible(true);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void saveMatToFile(String imageName, Mat img) {
        try {
            final String modFilePath = Constants.IMAGE_PATH +"/"+ imageName;
            Imgcodecs.imwrite(modFilePath, img);
        } catch (Exception e) {
            log.debug("only .JPG and .PNG files are supported");
        }
    }

}



















