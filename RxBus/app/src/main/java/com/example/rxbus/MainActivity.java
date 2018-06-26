package com.example.rxbus;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rxbus.bus.MainBus;
import com.example.rxbus.event.ClickEvent;
import com.example.rxbus.rx.Transformer;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    Button button;
    private Observer<? super Object> mainBusSubscriber = new Observer<Object>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Object o) {
            if (o instanceof ClickEvent) {
                ClickEvent event = (ClickEvent) o;
                Snackbar.make(event.getView(), "Event Received", Snackbar.LENGTH_LONG)
                        .setAction("OK", null)
                        .show();
            }
        }

        @Override
        public void onError(Throwable t) {

        }

        @Override
        public void onComplete() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Send click event to main bus
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener((View v) -> MainBus.getInstance().post(new ClickEvent(v)));

        //Receive click event from main bus
        MainBus.getInstance().getBusObservable()
                .compose(Transformer.applySchedulers())
                .subscribe(mainBusSubscriber);
    }
}
