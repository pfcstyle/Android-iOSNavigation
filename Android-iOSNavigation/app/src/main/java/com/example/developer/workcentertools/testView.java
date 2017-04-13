package com.example.developer.workcentertools;

import android.content.Context;

import pfc.iosnavigation.MyNavigationView;

/**
 * Created by developer on 17/2/7.
 */

public class testView extends MyNavigationView {
    public testView(Context context, int rid) {
        super(context, rid);
    }

    @Override
    public void viewWillAppear() {
        super.viewWillAppear();
        System.out.println("testView " + "viewWillAppear");
    }

    @Override
    public void viewDidAppear() {
        super.viewDidAppear();
        System.out.println("testView " + "viewDidAppear");
    }

    @Override
    public void viewWillDisappear() {
        super.viewWillDisappear();
        System.out.println("testView " + "viewWillDisappear");
    }

    @Override
    public void viewDidDisappear() {
        super.viewDidDisappear();
        System.out.println("testView " + "viewDidDisappear");
    }

    @Override
    public void viewDidLoad() {
        super.viewDidLoad();
        System.out.println("testView " + "viewDidLoad");
    }

    @Override
    public void viewWillDealloc() {
        super.viewWillDealloc();
        System.out.println("testView " + "viewWillDealloc");
    }
}
