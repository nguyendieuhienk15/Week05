package com.example.ndh.dagger2.di;

import android.app.Application;
import android.content.Context;

import com.example.ndh.dagger2.DummyDependency;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


@Module
public abstract class AppModule {

    @Binds
    public abstract Context bindContext(Application application);

    @Provides
    static DummyDependency provideDummyDependency(Context context) {
        return new DummyDependency(context);
    }

}
