package com.example.todo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.todo.adapter.ToDoAdapter;
import com.example.todo.model.Post;
import com.example.todo.model.ToDo;
import com.example.todo.network.GetDataService;
import com.example.todo.network.PostDataService;
import com.example.todo.network.RetrofitClientInstance;
import com.example.todo.rx.Transformer;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private CompositeDisposable compositeDisposable;
    private ToDoAdapter adapter;
    private RecyclerView recyclerView;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        //GET DATA
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(service.getAllToDos()
                .compose(Transformer.applySchedulers())
                .subscribe(this::handleResponse,this::handleError));

        //REPLACED BY RX
        /*Call<List<ToDo>> call = service.getAllToDos();
        call.enqueue(new Callback<List<ToDo>>() {

            @Override
            public void onResponse(Call<List<ToDo>> call, Response<List<ToDo>> response) {
                progressDialog.dismiss();
                Log.d("MyDummyError", "I am crashed on response");
                generateDataToDoList(response.body());
            }

            @Override
            public void onFailure(Call<List<ToDo>> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("MyDummyError", "I am crashed on failure");
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("MyDummyError", "I am crashed without reason");*/

        //POST DATA
        fab.setOnClickListener((View v) -> sendDummyPost());
    }


    private void handleResponse(List<ToDo> toDoList) {
        progressDialog.dismiss();
        recyclerView = findViewById(R.id.customRecyclerView);
        adapter = new ToDoAdapter(this, toDoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void handleError(Throwable error) {
        progressDialog.dismiss();
        Toast.makeText(this, "Error "+ error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    private void sendDummyPost() {
        PostDataService service = RetrofitClientInstance.getRetrofitInstance().create(PostDataService.class);
        compositeDisposable.add(service.savePost("hi","hello",1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handlePost,this::handlePostError));
    }

    private void handlePost(Post post) {
        Toast.makeText(this, post.toString(), Toast.LENGTH_SHORT).show();
    }

    private void handlePostError(Throwable error) {
        Toast.makeText(this, "Error "+ error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataToDoList(List<ToDo> toDoList) {
        recyclerView = findViewById(R.id.customRecyclerView);
        adapter = new ToDoAdapter(this, toDoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
