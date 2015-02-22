package com.example.lenovopc.wallpaperapp;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lenovopc.wallpaperapp.utils.SimpleGestureFilter;
import com.squareup.picasso.Picasso;

import java.io.InputStream;


public class MainActivity extends Activity {
    private Context mcontext = this;
    private ProgressBar progressBar;
    private Button closeBtn, wallpaperBtn;
    private SimpleGestureFilter detector;
    private ImageView wallpaper;
    private String[] wallpaperImageURL;
    private int counter = 0;
    private Bitmap mBitmap;
    private WallpaperManager myWallpaperManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wallpaperImageURL = new String[]{
                "http://pixabay.com/static/uploads/photo/2014/11/13/15/24/minecraft-529462_640.jpg",
                "http://cdn.smosh.com/sites/default/files/ftpuploads/bloguploads/0813/minecraft-castles-evil.jpg",
                "http://cdn.smosh.com/sites/default/files/ftpuploads/bloguploads/0813/minecraft-castles-nether.jpg",
                "http://cdn.smosh.com/sites/default/files/ftpuploads/bloguploads/0813/minecraft-castles-dragon.jpg",
                "http://cdn.smosh.com/sites/default/files/ftpuploads/bloguploads/0813/minecraft-castles-angelfalls.jpg",
                "http://1.bp.blogspot.com/-6J3tz74wMgI/UgG2o1eX2iI/AAAAAAAABbM/8rFACEKJcUc/s1600/14czll5.png",
                "http://trendminicraft.com/wp-content/uploads/2014/08/minecraft-castle-ideas-6qsfvgvn.png",
                "http://www.minecraftgallery.com/wp-content/uploads/2012/07/isolate-castle-keep.jpeg",
                "http://pixabay.com/static/uploads/photo/2014/11/13/15/24/minecraft-529462_640.jpg",
                "http://minecraft-delicious.com/wp-content/uploads/2012/12/minecraft-castle-building-picture_6.jpg",
                "http://fc04.deviantart.net/fs70/i/2011/285/9/4/minecraft__castle_w_i_p__by_cj64-d4cl38c.png",
                "http://gamingprecision.com/wp-content/uploads/2014/02/minecraft-castle.jpg",
                "http://view71.com/wp-content/uploads/2014/02/castles-minecraft.jpg",
                "http://cdn.smosh.com/sites/default/files/ftpuploads/bloguploads/0813/minecraft-castles-keep.jpg",
                "http://cdn.smosh.com/sites/default/files/ftpuploads/bloguploads/0813/minecraft-castles-square.jpg",
                "http://cdn.smosh.com/sites/default/files/ftpuploads/bloguploads/0813/minecraft-castles-stormhold.jpg",
                "http://cdn.smosh.com/sites/default/files/ftpuploads/bloguploads/0813/minecraft-castles-zelda.jpg",
                "http://cdn.smosh.com/sites/default/files/ftpuploads/bloguploads/0813/minecraft-castles-mario.jpg",
                "http://cdn.smosh.com/sites/default/files/ftpuploads/bloguploads/0813/minecraft-castles-fantym.jpg",
                "http://cdn.smosh.com/sites/default/files/ftpuploads/bloguploads/0813/minecraft-castles-roman.jpg",
                "http://cdn.smosh.com/sites/default/files/ftpuploads/bloguploads/0813/minecraft-castles-ruins.jpg",
                "http://cdn.smosh.com/sites/default/files/ftpuploads/bloguploads/0813/minecraft-castles-medieval.jpg",
                "http://cdn.smosh.com/sites/default/files/ftpuploads/bloguploads/0813/minecraft-castles-estel.jpg",
                "http://cdn.smosh.com/sites/default/files/ftpuploads/bloguploads/0813/minecraft-castles-lotr.jpg",
                "http://matt.legault.me/wp-content/uploads/2013/06/Castle2.jpg"

        };
        setContentView(R.layout.activity_main);
        closeBtn = (Button) findViewById(R.id.closeBtn);
        wallpaperBtn = (Button) findViewById(R.id.wallpaperBtn);
        wallpaper = (ImageView) findViewById(R.id.wallpaperImageView);

        /**
         * Close Button Click Handler
         */
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * Set Wallpaper Button Click Handler
         */
        wallpaperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAsWallpaper();
            }
        });

        // new DownloadImageTask(wallpaper).execute(wallpaperImageURL[0]);

        Picasso.with(mcontext).load(wallpaperImageURL[counter
                ]).into(wallpaper);


        detector = new SimpleGestureFilter(MainActivity.this, new SimpleGestureFilter.SimpleGestureListener() {
            @Override
            public void onSwipe(int direction) {
                if(direction == detector.SWIPE_LEFT){
                    showPrevImage();
                }else{
                    showNextImage();
                }
            }

            @Override
            public void onDoubleTap() {

            }
        });

    }

    /**
     * Set As Wallpaper Function Logic goes here.
     */
    private void setAsWallpaper() {
        mBitmap = ((BitmapDrawable) wallpaper.getDrawable()).getBitmap();
        myWallpaperManager = WallpaperManager.getInstance(mcontext);
        try {
            if (mBitmap != null) {
                myWallpaperManager.setBitmap(mBitmap);
            }
            Toast.makeText(MainActivity.this, "Wallpapr set", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Error setting wallpaper " + e, Toast.LENGTH_LONG).show();
        }
    }

    private void showPrevImage(){
        --counter;
        if(counter < 0){
            counter = wallpaperImageURL.length -1 ;
        }
        Picasso.with(mcontext).load(wallpaperImageURL[counter]).into(wallpaper);
    }

    private void showNextImage(){
        ++counter;
        if(counter > wallpaperImageURL.length -1 ){
            counter = 0;
        }
        Picasso.with(mcontext).load(wallpaperImageURL[counter]).into(wallpaper);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return detector.onTouchEvent(ev);
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

}
