package com.example.ndh.dagger2.di;

import com.example.ndh.dagger2.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityContributorModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

}
