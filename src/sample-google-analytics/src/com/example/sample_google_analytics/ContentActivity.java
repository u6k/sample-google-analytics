
package com.example.sample_google_analytics;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class ContentActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "sample-ga";

    private TextView _titleText;

    private TextView _contentText;

    private TextView _linkText;

    private GoogleAnalyticsTracker _tracker;

    private ContentDto _content;

    private boolean _isTracked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        // TODO GAのシングルトン・インスタンスを取得。
        _tracker = GoogleAnalyticsTracker.getInstance();

        _titleText = (TextView) findViewById(R.id.title_text);
        _contentText = (TextView) findViewById(R.id.content_text);
        _linkText = (TextView) findViewById(R.id.link_text);
        _linkText.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        long contentId = intent.getLongExtra("content_id", -1);
        if (contentId == -1) {
            throw new RuntimeException("content_id == -1");
        }

        ContentDao dao = new ContentDao(this);
        _content = dao.findById(contentId);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO PVをカウントする。
        // 重複カウントを防止するためフラグ制御を行っている。
        // (端末回転、画面バックなどでカウントされてしまう)
        if (!_isTracked) {
            // TODO カスタム変数に、コンテンツIDをページスコープで付与。
            _tracker.setCustomVar(2, "content-id", Long.toString(_content.getId()));
            _tracker.trackPageView("/content");
            _tracker.dispatch();

            Log.d(TAG, "trackPageView: /content: content-id=" + _content.getId());

            _isTracked = true;
        } else {
            Log.d(TAG, "not track");
        }

        _titleText.setText(_content.getTitle());
        _contentText.setText(_content.getContent());
        _linkText.setText(_content.getLink());
    }

    @Override
    public void onClick(View v) {
        String url = _linkText.getText().toString();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

}
