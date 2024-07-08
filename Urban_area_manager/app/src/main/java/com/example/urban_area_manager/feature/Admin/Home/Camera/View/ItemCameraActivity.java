package com.example.urban_area_manager.feature.Admin.Home.Camera.View;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.core.content.ContextCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.rtsp.RtspMediaSource;
import androidx.media3.exoplayer.source.MediaSource;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.FragmentItemCameraBinding;
import com.example.urban_area_manager.feature.Admin.Home.Camera.Model.Camera;
import com.example.urban_area_manager.feature.Admin.Home.Camera.ViewModel.CameraViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.extensions.Extensions;
import com.example.urban_area_manager.utils.view.DialogView;


public class ItemCameraActivity extends BaseActivity<FragmentItemCameraBinding, CameraViewModel> {

    Camera camera;
    private ExoPlayer player;
    private boolean isMuted = false;
    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;



    @Override
    public void onCommonViewLoaded() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            camera = (Camera) bundle.getSerializable(Constant.GO_TO_ItemCameraFragment);
        }
//        setSupportActionBar(binding.toolbar);
        setupCamera();
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        binding.nameTitle.setText(camera.getArea());
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);

        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(1.0f, Math.min(scaleFactor, 5.0f));
            binding.playerView.setScaleX(scaleFactor);
            binding.playerView.setScaleY(scaleFactor);
            return true;
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        scaleGestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
    @OptIn(markerClass = UnstableApi.class)
    private void setupCamera() {

        player = new ExoPlayer.Builder(this).build();
        binding.playerView.setPlayer(player);
        String rtspUrl = camera.getLink();
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(rtspUrl));
        MediaSource mediaSource = new RtspMediaSource.Factory()
                .setTimeoutMs(4000)
                .setForceUseRtpTcp(true)
                .createMediaSource(mediaItem);
        player.setMediaSource(mediaSource);
        player.prepare();
        player.play();
//
        ImageButton playPauseBtn = findViewById(R.id.playPauseBtn);
        ImageButton fullScreenBtn = findViewById(R.id.fullScreenBtn);
        ImageButton volume = findViewById(R.id.volume);

        playPauseBtn.setOnClickListener(v -> {
            if (player.isPlaying()) {
                player.pause();
                playPauseBtn.setImageResource(R.drawable.ic_pause);
            } else {
                player.play();
                playPauseBtn.setImageResource(R.drawable.ic_play);
            }
        });

        fullScreenBtn.setOnClickListener(v -> {
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        });
        volume.setOnClickListener(v->{
            if (isMuted) {
                player.setVolume(1f);
                volume.setImageResource(R.drawable.ic_volume_up);

            } else {
                player.setVolume(0f);
                volume.setImageResource(R.drawable.ic_volume_off);

            }
            isMuted = !isMuted;
        });
        handleOrientationChange(getResources().getConfiguration().orientation);

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        handleOrientationChange(newConfig.orientation);
    }

    private void handleOrientationChange(int orientation) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            binding.toolbar.setVisibility(View.GONE);
            binding.tbTemperatureSensor.setVisibility(View.GONE);

            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
//            binding.toolbar.setVisibility(View.VISIBLE);
            binding.tbTemperatureSensor.setVisibility(View.VISIBLE);

        }
    }
    @Override
    public void addViewListener() {
//        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        binding.btnDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    @Override
    protected FragmentItemCameraBinding getViewBinding() {
        return FragmentItemCameraBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<CameraViewModel> getViewModelClass() {
        return CameraViewModel.class;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_toolbar_edit, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.edit) {
//            Bundle bundle = new Bundle();
//            bundle.putSerializable(Constant.GO_TO_EDIT_CAMERA,camera);
//            openActivity(EditCameraActivity.class);
//        } else if (id == R.id.delete) {
//            Runnable listenerPositive = () -> {
//                viewModel.deleteCamera(camera);
//            };
//            DialogView.showDialogDescriptionByHtml(this,"Xác nhận","Bạn có muốn xóa camera này không",listenerPositive);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.isDeleteSucess.observe(this,isDelete->{
            Extensions.showToastShort(this,"Xóa thành công");
            finish();
        });
    }
}