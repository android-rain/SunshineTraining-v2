package com.yun.android.sunshine.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailActivity extends ActionBarActivity {

    //    be used to test the sequence of lifecycle events following a device rotation

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("on : ", "Destroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("on : ", "Stop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("on : ", "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("on : ", "Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("on : ", "Pause");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.i("on : ", "Create");
//        if (savedInstanceState == null){
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.action_bar_container, new DetailFragment())
//                    .commit();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);

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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public static class DetailFragment extends Fragment {
        private static final String  Log_TAG = DetailFragment.class.getSimpleName();

        private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
        private String mForecastStr;

        public DetailFragment() {
            setHasOptionsMenu(true);
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Intent intent = getActivity().getIntent();
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                mForecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
                ((TextView)rootView.findViewById(R.id.detailText))
                        .setText(mForecastStr);
            }

            return rootView;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

            inflater.inflate(R.menu.detailfragment, menu);

            //Locate MenuItem with ShareActionProvider
            MenuItem item = menu.findItem(R.id.action_share);
            //Fetch and store shareActionProvider
            ShareActionProvider mShareActionProvider =
                    (ShareActionProvider) MenuItemCompat.getActionProvider(item);
            //attach an intent to this ShareActionProvider
            if (mShareActionProvider != null){
                mShareActionProvider.setShareIntent(createShareForecastIntent());
            }else {
                Log.d(Log_TAG, "Share Action Provider is null?");
            }
        }
        private Intent createShareForecastIntent(){
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,  mForecastStr + FORECAST_SHARE_HASHTAG);
            return shareIntent;
        }

    }

}
