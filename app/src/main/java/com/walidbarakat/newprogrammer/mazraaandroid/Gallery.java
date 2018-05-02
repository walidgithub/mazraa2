package com.walidbarakat.newprogrammer.mazraaandroid;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

public class Gallery extends AppCompatActivity {
    ImageView img;
    String ImageSelect;
    Bitmap thumbnail;
    EditText txtdiscription;
    public static String deletedisc;
    public static Bitmap shareimage;

    DB_Sqlite db = new DB_Sqlite(this);

    private RecyclerView recyclerView;
    private RecyclerAdapter recycleradapter;
    public List<RecyclerModel> recyclermodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        img = (ImageView) findViewById(R.id.header);

        txtdiscription = (EditText) findViewById(R.id.TxtDiscription);

        recyclerView = (RecyclerView) findViewById(R.id.m_recyclerview);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        try {
            db.openDatabase();
            Cursor Cr = db.mDatabase.rawQuery("select * from gallery", null);
            Cr.moveToFirst();
            recyclermodel = new ArrayList<>();
            while (!Cr.isAfterLast()) {
                recyclermodel.add(new RecyclerModel(Cr.getString(Cr.getColumnIndex("Discription")), Cr.getBlob(Cr.getColumnIndex("Img"))));
                Cr.moveToNext();
            }
            recycleradapter = new RecyclerAdapter(recyclermodel, this);
            recyclerView.setAdapter(recycleradapter);
            Cr.close();
            db.closeDatabase();
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        try {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.gallerymenu, menu);
            MenuItem item = menu.findItem(R.id.BtnSearch);
            SearchView searchView = (SearchView) item.getActionView();

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    search(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return false;
                }
            });
        } catch (Exception l) {
            Log.e("", l.getMessage());
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.BtnCamera) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 2);
            ImageSelect = "Camera";
        } else if (id == R.id.BtnShare) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                    return super.onOptionsItemSelected(item);
                }
            }
            SaveFile();
        } else if (id == R.id.BtnDelete) {

            db.openDatabase();

            db.mDatabase.execSQL("delete from gallery where Discription='" + deletedisc + "'");

            Cursor Cr = db.mDatabase.rawQuery("select * from gallery", null);
            Cr.moveToFirst();
            recyclermodel = new ArrayList<>();
            while (!Cr.isAfterLast()) {
                recyclermodel.add(new RecyclerModel(Cr.getString(Cr.getColumnIndex("Discription")), Cr.getBlob(Cr.getColumnIndex("Img"))));
                Cr.moveToNext();
            }
            recycleradapter = new RecyclerAdapter(recyclermodel, this);
            recyclerView.setAdapter(recycleradapter);
            Cr.close();
            db.closeDatabase();

            Toast.makeText(this, "تم الحذف بنجاح", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.BtnGallery) {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
            ImageSelect = "Gallery";
        } else if (id == R.id.BtnSave) {
            if (txtdiscription.getText().toString().trim().equals("")) {
                Toast.makeText(this, "لابد من ادخال وصف للصوره", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }

            db.openDatabase();
            Cursor Cr = db.mDatabase.rawQuery("select * from gallery where Discription='" + txtdiscription.getText().toString().trim() + "'", null);
            Cr.moveToFirst();
            int Count = Cr.getCount();
            if (Count > 0) {
                Toast.makeText(this, "يوجد صوره بنفس الوصف", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }
            Cr.close();
            db.closeDatabase();

            img.setDrawingCacheEnabled(true);
            img.buildDrawingCache();
            Bitmap bitmap = img.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            db.Insert_To_Gallery(data, txtdiscription.getText().toString().trim());

            Intent Mothers = new Intent(Gallery.this, Gallery.class);
            startActivity(Mothers);
            Toast.makeText(this, "تمت الاضافه بنجاح", Toast.LENGTH_SHORT).show();
            finish();
        }

        return super.onOptionsItemSelected(item);

    }

    private void SaveFile() {
        SavePhoto(shareimage);
    }

    private void SavePhoto(Bitmap bitmap) {
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MazraaImages";
        File dir = new File(file_path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, deletedisc + ".jpg");
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(byteArray.toByteArray());
            fos.close();

            ShareImage(Uri.fromFile(file));
        } catch (Exception f) {

        }


    }

    private void ShareImage(Uri path) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, path);
        intent.putExtra(Intent.EXTRA_TEXT, deletedisc);
        startActivity(Intent.createChooser(intent, "مشاركة صوره"));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (ImageSelect.equals("Camera")) {
            if (requestCode == 2 && resultCode == RESULT_OK) {
                //Uri uri = data.getData();
                //img.setImageURI(uri);
                onCaptureImageResult(data);
            }
        } else if (ImageSelect.equals("Gallery")) {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    img.setImageBitmap(selectedImage);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        img.setMaxWidth(200);
        img.setImageBitmap(thumbnail);
    }

    public void search(String word) {
        db.openDatabase();
        Cursor Cr = db.mDatabase.rawQuery("select * from gallery where Discription like '%" + word + "%'", null);
        Cr.moveToFirst();
        recyclermodel = new ArrayList<>();
        while (!Cr.isAfterLast()) {
            recyclermodel.add(new RecyclerModel(Cr.getString(Cr.getColumnIndex("Discription")), Cr.getBlob(Cr.getColumnIndex("Img"))));
            Cr.moveToNext();
        }
        recycleradapter = new RecyclerAdapter(recyclermodel, this);
        recyclerView.setAdapter(recycleradapter);
        Cr.close();
        db.closeDatabase();
    }
}
