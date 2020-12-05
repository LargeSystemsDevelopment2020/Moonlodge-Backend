package dk.lsd.integration;

import dk.lsd.Main;

import org.junit.jupiter.api.*;

public class ITMain {

    @Test
    void getStringTestIT(){
        Assertions.assertEquals("Hello World", new Main().getString());
    }
}
