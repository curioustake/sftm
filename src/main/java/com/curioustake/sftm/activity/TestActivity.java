package com.curioustake.sftm.activity;

import java.util.Arrays;

public class TestActivity implements Activity {
    public void invoke(String[] args) {
        System.out.println( "Execute => " + Arrays.toString(args) );
    }
}
