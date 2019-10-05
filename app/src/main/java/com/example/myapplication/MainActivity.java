package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText suvidha_et, distributor_et, add_et, ward_et, contact_et, name_et, adhaar_et, pan_et, email_et;
    public static ImageView profile_photo;
    public static ImageView adhaar_card;
    public static ImageView pan_card;

    public static String profile_photo_string;
    public static String adhaar_card_string;
    public static String pan_card_string;

    private int PICK_IMAGE_REQUEST = 1;
    private int PICK_IMAGE_REQUEST1 = 2;
    private int PICK_IMAGE_REQUEST2 = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name_et = (EditText) findViewById(R.id.name_et);
        suvidha_et = (EditText) findViewById(R.id.suvidha_et);
        distributor_et = (EditText) findViewById(R.id.distributor_et);
        add_et = (EditText) findViewById(R.id.add_et);
        ward_et = (EditText) findViewById(R.id.ward_et);
        contact_et = (EditText) findViewById(R.id.contact_et);
        adhaar_et = (EditText) findViewById(R.id.adhaar_et);
        pan_et = (EditText) findViewById(R.id.pan_et);
        email_et = (EditText) findViewById(R.id.email_et);
        profile_photo = (ImageView) findViewById(R.id.img);
        adhaar_card = (ImageView) findViewById(R.id.adhaar);
        pan_card = (ImageView) findViewById(R.id.pan);
    }
    public void add_image(View view) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        //File file = new File(Environment.getExternalStorageDirectory(),
        //      counter+".jpg");
        //Uri photoPath = Uri.fromFile(file);
        // i.putExtra(MediaStore.EXTRA_OUTPUT, photoPath);
        startActivityForResult(i.createChooser(i, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    public void add_adhaar_card(View view) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i.createChooser(i, "Select Picture"), PICK_IMAGE_REQUEST1);
    }
    public void add_pan_card(View view) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i.createChooser(i, "Select Picture"), PICK_IMAGE_REQUEST2);
    }
    // function to convert bitmap to string
    public String getStringImage(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public String getStringImage1(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public String getStringImage2(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                Bitmap bitmap = decodeUri(MainActivity.this, filePath, 700);
                profile_photo_string = getStringImage(bitmap);
                //Setting the Bitmap to ImageView
                profile_photo.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST1 && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                Bitmap bitmap1 = decodeUri(MainActivity.this, filePath, 700);
                adhaar_card_string = getStringImage(bitmap1);
                //Setting the Bitmap to ImageView
                adhaar_card.setImageBitmap(bitmap1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST2 && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                Bitmap bitmap2 = decodeUri(MainActivity.this, filePath, 700);
                pan_card_string = getStringImage(bitmap2);
                //Setting the Bitmap to ImageView
                pan_card.setImageBitmap(bitmap2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // function to scale down image
    public Bitmap decodeUri(Context c, Uri uri, final int requiredSize)
            throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while (true) {
            if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
    }
    public void submit(View view) {

        String name = name_et.getText().toString();
        String suvidha = suvidha_et.getText().toString();
        String distributor = distributor_et.getText().toString();
        String email = email_et.getText().toString();
        String address = add_et.getText().toString();
        String ward = ward_et.getText().toString();
        String contact = contact_et.getText().toString();
        String adhaar = adhaar_et.getText().toString();
        String pan = pan_et.getText().toString();


        if (suvidha.equals("")) {
            Toast.makeText(MainActivity.this, "enter the suvidha center name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (distributor.equals("")) {
            Toast.makeText(MainActivity.this, "enter the distributor name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ward.equals("")) {
            Toast.makeText(MainActivity.this, "enter the ward no.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (name.equals("")) {
            Toast.makeText(MainActivity.this, "enter the name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (contact.length() < 10) {
            Toast.makeText(MainActivity.this, "re-enter the contact no. ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (address.equals("")) {
            Toast.makeText(MainActivity.this, "enter the address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (adhaar.length() < 8) {
            Toast.makeText(MainActivity.this, "please check your adhaar card no.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pan.length() < 8) {
            Toast.makeText(MainActivity.this, "please check your pan card no.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(MainActivity.this, "enter valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject job = new JSONObject();
        try {
            job.put("customers_name", name);
            job.put("customers_contact", contact);
            job.put("customers_email", email);
            job.put("suvidha_center_name", suvidha);
            job.put("distributor_name", distributor);
            job.put("ward_no", ward);
            job.put("customers_address", address);
            job.put("adhaar_no", adhaar);
            job.put("pan_no", pan);

            job.put("customer_id", getIntent().getStringExtra("customer_id"));
            job.put("profile_photo", profile_photo_string);
            job.put("adhaar_card", adhaar_card_string);
            job.put("pan_card", pan_card_string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jobjreq = new JsonObjectRequest("http://testing.reitindia.org/welcome/insert_customers", job, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {

                    if (response.getString("key").equals("0")) {
                        Toast.makeText(MainActivity.this, "check your email", Toast.LENGTH_SHORT).show();
                    }
                    else if (response.getString("key").equals("1")) {
                        Toast.makeText(MainActivity.this, "done", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                {
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);

                    }
                });
        jobjreq.setRetryPolicy(new DefaultRetryPolicy(2000, 2, 2));
        AppController app = new AppController(MainActivity.this);
        app.addToRequestQueue(jobjreq);

    }

    public void upload_ptp(View view) {
    }

    public void reference2(View view) {
    }

    public void reference1(View view) {
    }
}