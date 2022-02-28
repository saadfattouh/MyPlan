package com.example.myplan;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.icu.text.SimpleDateFormat;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.myplan.utils.SharedPrefManager;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Locale;

public class LectureViewActivity extends AppCompatActivity implements OnLoadCompleteListener, OnPageChangeListener, OnPageErrorListener {

    //for writing on storage permission
    private static final int WRITE_EXT_STORAGE_CODE = 1;

    //for binding
    PDFView pdfView;
    BottomNavigationView bottomNav;

    //while pdf is loaded
    ProgressDialog progressDialog;

    //for getting from extras
    String url;
    String lectureName;
    int lectureId;
    int numOfPages;

    SharedPrefManager prefManager;
    float progress;

    //playing sound for capturing image
    MediaPlayer mp;

    //values to get from shared preferences
    // for bookmarks purposes
    int currentPageNumber = 0;

    //pdf file after downloading
    File pdfUrl;

    //controls showing and hiding bottom menu
    OnTapListener onTapListener;
    private boolean tapped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_view);

        bottomNav = findViewById(R.id.bottom_navigation_bookView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        prefManager = SharedPrefManager.getInstance(this);

        Intent i = getIntent();
        url = i.getStringExtra("url");
        lectureId = i.getIntExtra("lect_id", -1);
        lectureName = i.getStringExtra("name");



        //if the pdf is large or not downloaded so it will take time to load
        pdfView = findViewById(R.id.pdfView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait\nFetching pdf...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        //showing and hiding bottom tools menu when tapping on screen
        onTapListener = new OnTapListener() {
            @Override
            public boolean onTap(MotionEvent e) {
                if (tapped) {
                    bottomNav.setVisibility(View.INVISIBLE);
                    tapped = false;
                } else {
                    bottomNav.setVisibility(View.VISIBLE);
                    tapped = true;
                }
                return true;
            }
        };


        FileLoader.with(this)
                .load(url, false) //2nd parameter is optioal, pass true to force load from network
                .fromDirectory("lectures/"+lectureName, FileLoader.DIR_INTERNAL)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        pdfUrl = response.getBody();
                        // do something with the file
                        try {
                            pdfView.fromFile(pdfUrl)
                                    .onTap(onTapListener)
                                    .onPageScroll(new OnPageScrollListener() {
                                        @Override
                                        public void onPageScrolled(int page, float positionOffset) {
                                            bottomNav.setVisibility(View.VISIBLE);

                                            final Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    bottomNav.setVisibility(View.INVISIBLE);
                                                }
                                            }, 1000);
                                        }
                                    })
                                    .defaultPage(0)
                                    .enableSwipe(true)
                                    .enableAnnotationRendering(true)
                                    .onLoad(LectureViewActivity.this)
                                    .onPageChange(LectureViewActivity.this)
                                    .scrollHandle(new DefaultScrollHandle(LectureViewActivity.this))
                                    .enableDoubletap(true)
                                    .onPageError(LectureViewActivity.this)
                                    .swipeHorizontal(true)
                                    .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
                                    .fitEachPage(true)
                                    .pageSnap(true)
                                    .pageFling(true)
                                    .spacing(0)
                                    .load();

                            Log.i("FilePath: ", "onLoad: " + pdfUrl);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(LectureViewActivity.this, "" + t.getMessage() + ", File Error", Toast.LENGTH_SHORT).show();

                    }
                });


    }


    @Override
    public void loadComplete(int nbPages) {
        progressDialog.dismiss();
        numOfPages = nbPages;
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        currentPageNumber = page + 1;

        progress = (float)currentPageNumber/(float)numOfPages;

        if(progress > 1.0f){
            progress = 1.0f;
        }


    }

    @Override
    public void onPageError(int page, Throwable t) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void screenShot() {
        Bitmap b = Bitmap.createBitmap(pdfView.getWidth(), pdfView.getHeight(), Bitmap.Config.ARGB_8888);

        int width, height;
        width = pdfView.getWidth();
        height = pdfView.getHeight();
        Bitmap cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(cs);
        c.drawBitmap(b, 0, 0, null);
        pdfView.draw(c);
        c.setBitmap(cs);


        String time = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        File path = Environment.getExternalStorageDirectory();
        File dir = new File(path + "/DCIM/MyPlan");
        dir.mkdir();
        String imagename = time + ".JPEG";
        File file = new File(dir, imagename);
        OutputStream out;
        try {
            out = new FileOutputStream(file);
            cs.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            mp = MediaPlayer.create(this, R.raw.camerashutter);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.stop();
                }
            });
            //for gallery to be notified of the Image
            MediaScannerConnection.scanFile(this, new String[]{file.getAbsolutePath()},
                    null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {

                        }
                    });
            //todo add string resource for locals
            Toast.makeText(LectureViewActivity.this, "screenshot saved to gallery", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED) {
                    String[] permission = {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    };
                    requestPermissions(permission, WRITE_EXT_STORAGE_CODE);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        prefManager.setLectureProgress("lecture" + lectureId, progress);
        super.onBackPressed();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            int navId = item.getItemId();

            switch (navId) {

                case R.id.screen_shot:
                    screenShot();
                    break;

                case R.id.bookmark:
                    //todo save the bookmark here

                    break;
                default:
                    break;

            }

            return true;
        }
    };



}