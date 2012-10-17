
package com.example.sample_google_analytics;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ContentDao {

    private Context _ctx;

    public ContentDao(Context ctx) {
        _ctx = ctx;
    }

    public List<ContentDto> findAll() {
        List<ContentDto> l = new ArrayList<ContentDto>();

        DBHelper con = new DBHelper(_ctx);
        try {
            SQLiteDatabase db = con.getReadableDatabase();
            try {
                String sql = "select _id, title from content order by _id";

                Cursor c = db.rawQuery(sql, new String[0]);
                try {
                    if (c.moveToFirst()) {
                        do {
                            ContentDto content = new ContentDto();
                            content.setId(c.getInt(c.getColumnIndex("_id")));
                            content.setTitle(c.getString(c.getColumnIndex("title")));

                            l.add(content);
                        } while (c.moveToNext());
                    }
                } finally {
                    c.close();
                }
            } finally {
                db.close();
            }
        } finally {
            con.close();
        }

        return l;
    }

    public ContentDto findById(long id) {
        ContentDto content = null;

        DBHelper con = new DBHelper(_ctx);
        try {
            SQLiteDatabase db = con.getReadableDatabase();
            try {
                String sql = "select _id, title, content, link from content where _id = ?";
                String[] selectionArgs = new String[] { Long.toString(id) };

                Cursor c = db.rawQuery(sql, selectionArgs);
                try {
                    if (c.moveToFirst()) {
                        content = new ContentDto();
                        content.setId(c.getInt(c.getColumnIndex("_id")));
                        content.setTitle(c.getString(c.getColumnIndex("title")));
                        content.setContent(c.getString(c.getColumnIndex("content")));
                        content.setLink(c.getString(c.getColumnIndex("link")));
                    }
                } finally {
                    c.close();
                }
            } finally {
                db.close();
            }
        } finally {
            con.close();
        }

        return content;
    }

}
