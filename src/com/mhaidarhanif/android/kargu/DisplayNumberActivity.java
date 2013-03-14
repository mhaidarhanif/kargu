package com.mhaidarhanif.android.kargu;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class DisplayNumberActivity extends MainActivity {

  @SuppressLint("NewApi")

  /** Display new activity */
  @TargetApi(11)
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display_number);

    /** Check minimum API level number to use ActionBar API */
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      // Show Up button in ActionBar
      getActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  /** Inherit method from ActionBar */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        NavUtils.navigateUpFromSameTask(this);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

}
