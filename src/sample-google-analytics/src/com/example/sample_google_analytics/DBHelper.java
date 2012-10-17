
package com.example.sample_google_analytics;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "sample-google-analytics";

    private static final int DB_VERSION = 1;

    public DBHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table content (";
        sql += "_id integer primary key,";
        sql += "title text not null,";
        sql += "content text,";
        sql += "link text";
        sql += ")";

        db.execSQL(sql);

        ContentValues v = new ContentValues();
        v.put("title", "AWS Billing Alerts を使った請求金額通知と、CLIを使った金額取得");
        v.put("content", "請求金額が閾値を超えたらアラートメールを送信することが可能。「閾値を超えたら停止」ではないことに注意。");
        v.put("link", "http://t.co/7F1gGXD");
        db.insert("content", null, v);

        v = new ContentValues();
        v.put("title", "ビルの屋上から落下したサーバーラックが大破する前にサーバの切替を成功させるムービー");
        v.put("content", "こういうの大好きです。");
        v.put("link", "http://t.co/beasoWE");
        db.insert("content", null, v);

        v = new ContentValues();
        v.put("title", "「spモードメール」がクラウド化し「ドコモメール」に改称");
        v.put("content", "『IMAPに近い環境だが、独自の方式を採用する。』なぜこんな無駄な意思決定をしてしまったのか、その経緯を知りたい。");
        v.put("link", "http://t.co/kvlvMlj");
        db.insert("content", null, v);

        v = new ContentValues();
        v.put("title", "ウェブデザインにおけるテクスチャやパターンの使い方をしっかり学びたい人用のまとめ");
        v.put("content", "これは良いtips。");
        v.put("link", "http://t.co/WyDsDvC");
        db.insert("content", null, v);

        v = new ContentValues();
        v.put("title", "Google元社長が実践していた「村上式シンプル英語勉強法」がすごい！");
        v.put("content", "読めるけど書けない。");
        v.put("link", "http://t.co/g1YzDTo");
        db.insert("content", null, v);

        v = new ContentValues();
        v.put("title", "Core App Quality Guidelines");
        v.put("content", "コアアプリ品質ガイドライン。これの日本語スプレッドシートも欲しい…");
        v.put("link", "http://t.co/0VHFAVL");
        db.insert("content", null, v);

        v = new ContentValues();
        v.put("title", "しゃべってコンシェルで「田井中律（CV佐藤聡美）」とお喋り可能に　キャラクターは順次追加");
        v.put("content", "そのキャラ独特の喋り方が再現できたらすごいと思う。");
        v.put("link", "http://t.co/qHAyTX2");
        db.insert("content", null, v);

        v = new ContentValues();
        v.put("title", "Webアプリケーションに検索機能を提供するApache Lucene/Solrが大型アップデート");
        v.put("content", "メモ。");
        v.put("link", "http://t.co/E692HnK");
        db.insert("content", null, v);

        v = new ContentValues();
        v.put("title", "JS 大規模プロジェクトの管理手法   ロードオブナイツの実例紹介");
        v.put("content", "これは良いアジャイル開発事例。");
        v.put("link", "http://t.co/CKvHnIR");
        db.insert("content", null, v);

        v = new ContentValues();
        v.put("title", "ネットエージェント、Androidアプリの潜在的危険度を判定できる無料サービスを公開");
        v.put("content", "有用なサービスだと思います。しかし、Androidアプリによる被害ってのは、どうすれば予防できますかねぇ…");
        v.put("link", "http://t.co/v9Q2Xjj");
        db.insert("content", null, v);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
