package com.fachati.hp;

import android.app.Application;
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

public class HpApplication extends Application{

    private HpService service;
    private Scheduler defaultSubscribeScheduler;
    public static List<Book> selectedBook;

    public static HpApplication get(Context context) {
        return (HpApplication) context.getApplicationContext();
    }

    public HpService getService() {
        if (service == null) {
            service = HpService.Factory.create();
            selectedBook=new ArrayList<>();
        }
        return service;
    }

    //For setting mocks during testing
    public void setService(HpService service) {
        this.service = service;
    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    //User to change scheduler from tests
    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
        this.defaultSubscribeScheduler = scheduler;
    }

}
