package com.example.sim.category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.example.sim.BaseActivity;
import com.example.sim.ChangeImageActivity;
import com.example.sim.MainActivity;
import com.example.sim.R;
import com.example.sim.dto.category.CategoryCreateDTO;
import com.example.sim.service.CategoryNetwork;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryCreateActivity extends BaseActivity {

    int SELECT_CROPPER = 300;
    Uri uri = null;
    ImageView IVPreviewImage;
    TextInputEditText txtCategoryName;
    TextInputEditText txtCategoryPriority;
    TextInputEditText txtCategoryDescription;

    private TextInputLayout txtFieldCategoryName;
    private TextInputLayout txtFieldCategoryPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_create);
        IVPreviewImage=findViewById(R.id.IVPreviewImage);
        txtCategoryName=findViewById(R.id.txtCategoryName);
        txtCategoryPriority=findViewById(R.id.txtCategoryPriority);
        txtCategoryDescription=findViewById(R.id.txtCategoryDescription);

        txtFieldCategoryName = findViewById(R.id.txtFieldCategoryName);
        txtFieldCategoryPriority = findViewById(R.id.txtFieldCategoryPriority);

        onChangeListener(txtCategoryName);
        onChangeListener(txtCategoryPriority);
    }

    public void handleSelectImageClick(View view) {
        Intent intent = new Intent(this, ChangeImageActivity.class);
        startActivityForResult(intent, SELECT_CROPPER);
    }

    public void handleCreateCategoryClick(View view) {
        if(!validation())
            return;
        CategoryCreateDTO model = new CategoryCreateDTO();
        model.setName(txtCategoryName.getText().toString());
        int number = Integer.parseInt(txtCategoryPriority.getText().toString());
        model.setPriority(number);
        model.setDescription(txtCategoryDescription.getText().toString());
        model.setImageBase64(uriGetBase64(uri));
        CategoryNetwork
                .getInstance()
                .getJsonApi()
                .create(model)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Intent intent = new Intent(CategoryCreateActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
    }

    private void onChangeListener(TextInputEditText input) {
        input.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                validation();
            }
        });

    }
    private boolean validation() {
        boolean result = true;
        String name = txtCategoryName.getText().toString();
        String priority = txtCategoryPriority.getText().toString();
        if(name.isEmpty()) {
            result = false;
            txtFieldCategoryName.setError("Вкажіть назву");
        }
        else
            txtFieldCategoryName.setError("");
        if(priority.isEmpty()) {
            result = false;
            txtFieldCategoryPriority.setError("Вкажіть пріорітет");
        }
        else
            txtFieldCategoryPriority.setError("");
        return result;
    }
    private String uriGetBase64(Uri uri) {
        try {
            Bitmap bitmap=null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch(IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            byte[] byteArr = bytes.toByteArray();
            return Base64.encodeToString(byteArr, Base64.DEFAULT);
        } catch(Exception ex) {
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==SELECT_CROPPER) {
            uri = (Uri) data.getParcelableExtra("croppedUri");
            IVPreviewImage.setImageURI(uri);
        }
    }
}