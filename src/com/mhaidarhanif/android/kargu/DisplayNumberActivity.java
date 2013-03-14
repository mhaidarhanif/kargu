package com.mhaidarhanif.android.kargu;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

public class DisplayNumberActivity extends MainActivity {

  @SuppressLint("NewApi")

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display_number);

    /** Check min. API level version to use ActionBar API */
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUR_DEVELOPMENT) {
      // Show Up button in ActionBar
      getActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  /** Inherit method from ActionBar */
  @Override
  public boolean onOptionsItemsSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        NavUtils.navigateUpFromSameTask(this);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

}
