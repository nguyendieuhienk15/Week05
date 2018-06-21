package com.example.todo.network;

import com.example.todo.model.ToDo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GetDataService {
    @GET("/todos")
    Observable<List<ToDo>> getAllToDos();
}
