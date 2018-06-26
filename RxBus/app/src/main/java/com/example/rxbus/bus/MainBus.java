package com.example.rxbus.bus;

//Singleton Main Bus
public class MainBus extends RxBus{
    private static MainBus instance;
    private MainBus() {
    }

    public static MainBus getInstance() {
        if (instance == null)
            instance = new MainBus();
        return instance;
    }
}
