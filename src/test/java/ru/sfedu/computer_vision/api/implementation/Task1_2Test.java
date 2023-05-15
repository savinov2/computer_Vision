package ru.sfedu.computer_vision.api.implementation;

import org.junit.jupiter.api.Test;

import static ru.sfedu.computer_vision.Constants.TEST_IMAGE_NAME;
import static ru.sfedu.computer_vision.Constants.TEST_IMAGE_PATH;

class Task1_2Test {

    TaskImpl task = new TaskImpl();


    @Test
    void task2() {

        task.task2(3, TEST_IMAGE_PATH, TEST_IMAGE_NAME);

    }

}