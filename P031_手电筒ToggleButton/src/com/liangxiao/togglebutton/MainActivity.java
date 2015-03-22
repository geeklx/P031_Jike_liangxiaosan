package com.liangxiao.togglebutton;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnCheckedChangeListener,
		OnClickListener {
	ImageView img;
	ToggleButton tg;
	Camera camera;
	Boolean isOpen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		isOpen = false;
		tg = (ToggleButton) findViewById(R.id.btn_tg);
		img = (ImageView) findViewById(R.id.img);
		img.setOnClickListener(this);
		tg.setOnCheckedChangeListener(this);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	public void onCheckedChanged(CompoundButton button, boolean isChecked) {
		if (tg.isChecked()) {
			camera = Camera.open();
			Parameters params = camera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(params);
			camera.startPreview(); // 开始亮灯

		} else {
			camera.stopPreview(); // 关掉亮灯
			camera.release(); // 关掉照相机
		}
		img.setBackgroundResource(isChecked ? R.drawable.light_on
				: R.drawable.light_off);
	}

	@Override
	public void onClick(View v) {
		if (v == img) {
			onOffFlash();
		}
	}

	private void onOffFlash() {
		if (isOpen) {
			camera.stopPreview(); // 关掉亮灯
			camera.release(); // 关掉照相机
			isOpen = false;
		} else {
			camera = Camera.open();
			Parameters params = camera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(params);
			camera.startPreview(); // 开始亮灯
			isOpen = true;
		}
		img.setBackgroundResource(isOpen ? R.drawable.light_on
				: R.drawable.light_off);
	}
}
