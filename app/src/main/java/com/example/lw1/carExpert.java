package com.example.lw1;

import android.content.Context;
import android.widget.ImageView;

public class carExpert {
    private final Context context;

    public carExpert(Context context){
        this.context = context;
    }

    public String getModel(String car_model){

        switch (car_model){
            case "SUV":
                return context.getString(R.string.suv_model);
            case "Sedan":
                return context.getString(R.string.sedan_model);
            case "Hatchback":
                return context.getString(R.string.hatchback_model);
            case "Pickup":
                return context.getString(R.string.pickup_model);
            default:
                return "";
        }

    }

    public void getModel_image(String car_model, ImageView imageView){

        switch (car_model){
            case "SUV":
                imageView.setImageResource(R.drawable.escape);
                break;
            case "Sedan":
                imageView.setImageResource(R.drawable.fusion);
                break;
            case "Hatchback":
                imageView.setImageResource(R.drawable.focus);
                break;
            case "Pickup":
                imageView.setImageResource(R.drawable.f_150);
                break;
        }

    }

}
