package com.fachati.hp;

import android.content.Context;

import com.fachati.hp.model.Book;
import com.fachati.hp.model.HpService;

import java.util.ArrayList;
import java.util.List;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by fachati on 08/03/17.
 */

public class Application extends android.app.Application {

    private HpService service;
    private Scheduler defaultSubscribeScheduler;
    public static List<Book> selectedBook;
    private static Application instance;

    private RxBus bus;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        bus = new RxBus();
        this.selectedBook=new ArrayList<>();
    }

    public static Application get() {
        return instance;
    }


    public RxBus bus() {
        return bus;
    }

    public static Application get(Context context) {
        return (Application) context.getApplicationContext();
    }

    public HpService getService() {
        if (service == null) {
            service = HpService.Factory.create();
            selectedBook=new ArrayList<>();
        }
        return service;
    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }
}
