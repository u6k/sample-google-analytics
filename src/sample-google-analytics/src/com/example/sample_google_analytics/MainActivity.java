
package com.example.sample_google_analytics;

import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    public static final String TAG = "sample-ga";

    private static final String TRACKING_ID = "UA-35644403-1";

    private ListView _titleListView;

    private GoogleAnalyticsTracker _tracker;

    private boolean _isTracked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO GAのシングルトン・インスタンスを取得
        _tracker = GoogleAnalyticsTracker.getInstance();

        // TODO トラッキングを開始。
        // 60秒ごとにGoogleAnalyticsにデータを送信する。
        _tracker.startNewSession(TRACKING_ID, 60, this);
        Log.d(TAG, "startNewSession: " + TRACKING_ID);

        // TODO ユニークユーザーを識別するためのUUIDを生成。
        // Google Analyticsは利用規約で個人情報の収集を禁止しているため、
        // 個人を特定できるユーザーIDのようなものをパラメータに含めることができない。
        // このため、当サンプルでは、アプリインストール毎にUUIDを生成し、
        // それをユーザー識別として利用する。
        // (ユーザー情報と切り離されるため、個人情報に該当しないと考えている)
        // デバイスをまたがる場合は、別の方法を検討する必要がある。
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String uuid = pref.getString("uuid", null);
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();

            Editor prefEditor = pref.edit();
            prefEditor.putString("uuid", uuid);
            prefEditor.commit();

            // TODO 訪問者スコープでUUIDを登録する。
            // 訪問者スコープなので「アプリの初回起動時のみ」呼び出す。
            // 以降、トラッキングにUUIDが付与される。
            _tracker.setCustomVar(1, "UUID", uuid, 1);

            Log.d(TAG, "setCustomVar: UUID=" + uuid);
        } else {
            Log.d(TAG, "not set: UUID=" + uuid);
        }

        _titleListView = (ListView) findViewById(R.id.title_list);

        ContentDao dao = new ContentDao(this);
        List<ContentDto> l = dao.findAll();

        TitleListArrayAdapter adapter = new TitleListArrayAdapter(l);
        _titleListView.setAdapter(adapter);
        _titleListView.setOnItemClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // TODO トラッキングを終了
        _tracker.stopSession();
        Log.d(TAG, "stopSession");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO PVをカウントする。
        // 重複カウントを防止するためフラグ制御を行っている。
        // (端末回転、画面バックなどでカウントされてしまう)
        if (!_isTracked) {
            _tracker.trackPageView("/main");
            Log.d(TAG, "trackPageView: /main");

            _isTracked = true;
        } else {
            Log.d(TAG, "not track");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long contentId = (Long) view.getTag();

        Intent intent = new Intent(this, ContentActivity.class);
        intent.putExtra("content_id", contentId);
        startActivity(intent);
    }

    private class TitleListArrayAdapter extends ArrayAdapter<ContentDto> {

        TitleListArrayAdapter(List<ContentDto> contentList) {
            super(MainActivity.this, android.R.layout.simple_list_item_1, contentList);
        }

        @Override
        public View getView(int position, View v, ViewGroup parent) {
            if (v == null) {
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                v = inflater.inflate(R.layout.row_title, null);
            }

            ContentDto content = getItem(position);

            TextView titleText = (TextView) v.findViewById(R.id.title_text);
            titleText.setText(content.getTitle());

            v.setTag(content.getId());

            return v;
        }

    }

}
