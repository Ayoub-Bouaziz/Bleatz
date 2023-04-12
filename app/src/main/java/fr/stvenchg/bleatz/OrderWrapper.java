package fr.stvenchg.bleatz;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderWrapper {
    @SerializedName("processing")
    public List<Order> processing;

    @SerializedName("finished")
    public List<Order> finished;
}