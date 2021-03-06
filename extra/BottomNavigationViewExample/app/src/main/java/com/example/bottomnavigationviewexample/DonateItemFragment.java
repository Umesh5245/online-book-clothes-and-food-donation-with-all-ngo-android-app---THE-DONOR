package com.example.bottomnavigationviewexample;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class DonateItemFragment extends Fragment {


    private Spinner spinnerSelectItem;
    private Spinner spinnerSelectCity;
    private ArrayList<String> item;
    private Uri mainUri;
    private ArrayList<String> city;
    private Button takePicBtn;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private Bitmap compressedImageFile;


    public DonateItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donate_item, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mainUri = result.getUri();
                //uptill here we got the real image Now we will convert it to thumbnail for less memory consumption

                File newImageFile = new File(mainUri.getPath());
                try {
                    compressedImageFile = new Compressor(getContext())
                            .setMaxHeight(200)
                            .setMaxWidth(200)
                            .setQuality(10)
                            .compressToBitmap(newImageFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // imageView.setImageURI(mainUri); //this was to setup real image

                //now for thumbnail quality

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data1 = baos.toByteArray();





                imageView.setImageURI(mainUri);








            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        takePicBtn = view.findViewById(R.id.fragment_donate_take_img);

        takePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(getContext(),"Permission Denied", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                    else{
                        //Toast.makeText(SetupActivity.this, "You already have permission" , Toast.LENGTH_SHORT).show();
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1, 1)
                                .start(getActivity());



                    }
                }
                else{

                }



            }
        });





        imageView = view.findViewById(R.id.fragment_donate_img);

        spinnerSelectCity = view.findViewById(R.id.fragment_donate_edt_city_spinner);
        spinnerSelectItem = view.findViewById(R.id.fragment_donate_spinner_items);






        city = new ArrayList<>();
        city.add("Select City");
        city.add("Rajkot");
        city.add("Gandhinagar");
        city.add("Jamnagar");
        city.add("Saurashtra");
        city.add("Morbi");


        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity(),R.layout.layout_city_spinner,city){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }


        };spinnerSelectCity.setAdapter(arrayAdapter2);













        item = new ArrayList<>();
        item.add("Select the item");
        item.add("Food");
        item.add("Clothes");
        item.add("Books");



        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getActivity(),R.layout.layout_item_spinner,item){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };


        spinnerSelectItem.setAdapter(arrayAdapter1);


        spinnerSelectItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i)
                {
                    case 1:

                        imageView.setImageResource(R.drawable.foo);

                        break;
                    case 2:

                        imageView.setImageResource(R.drawable.clothes);
                        break;
                    case 3:

                        imageView.setImageResource(R.drawable.books);
                        break;
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });












    }




}
