package ru.sfedu.computer_vision.api.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import static ru.sfedu.computer_vision.Constants.TEST_IMAGE_NAME;
import static ru.sfedu.computer_vision.Constants.TEST_IMAGE_PATH;

class Task1_2Test {

    private static final Logger log = LogManager.getLogger(Task1_2Test.class);

    TaskImpl task = new TaskImpl();


    @Test
    void task2() {

        task.task2(6, TEST_IMAGE_PATH, TEST_IMAGE_NAME);

    }

}