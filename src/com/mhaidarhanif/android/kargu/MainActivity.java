package com.mhaidarhanif.android.kargu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

  // For complete title purpose, will be used later
  // String appEditionName = getString(R.string.app_name) + " " + getString(R.string.edition_name);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  public void openDialog(View v) {
    Intent intent = new Intent(MainActivity.this, AboutDialogActivity.class);
    startActivity(intent);
  }

}
