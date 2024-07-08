package com.example.urban_area_manager.feature.Employee.Repair.Camera;

import androidx.annotation.OptIn;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.rtsp.RtspMediaSource;
import androidx.media3.exoplayer.source.MediaSource;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityItemCameraRepairBinding;
import com.example.urban_area_manager.feature.Admin.Home.Camera.Model.Camera;
import com.example.urban_area_manager.feature.Admin.Home.Camera.ViewModel.CameraViewModel;
import com.example.urban_area_manager.utils.Constant;

public class ItemCameraRepairActivity extends BaseActivity<ActivityItemCameraRepairBinding, CameraViewModel> {

    Camera camera;
    private ExoPlayer player;
    private boolean isMuted = false;
    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;

    @Override
    protected ActivityItemCameraRepairBinding getViewBinding() {
        return ActivityItemCameraRepairBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<CameraViewModel> getViewModelClass() {
        return CameraViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            camera = (Camera) bundle.getSerializable(Constant.GO_TO_ItemCameraRepairActivity);
        }
        setupCamera();
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        binding.toolbar.setTitle(camera.getArea());
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
            binding.toolbar.setVisibility(View.GONE);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.toolbar.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void addViewListener() {
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}