package com.example.mynote;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * @author tianfengx
 * @description 类简述
 * SharePreference辅助类
 * 用于存贮简单的对象
 * 存贮对象
 * @date 2016年5月26日
 */

public class CacheUtil {
    private static Context context;
    private static CacheUtil mACache = new CacheUtil();
    public static final String DEFAULT_CATCHE = "data";

    private static SharedPreferences share;

    public synchronized  static CacheUtil get(Context context) {
        CacheUtil.context = context;
        return mACache;
    }

    public synchronized boolean put(String name, String str) {
        share = context.getSharedPreferences(DEFAULT_CATCHE, Context.MODE_PRIVATE
);
        Editor edit = share.edit();
        edit.putString(name, str);
        return edit.commit();

    }

    public synchronized boolean put(String fileName, String name, String str) {
        share = context.getSharedPreferences(fileName, Context.MODE_PRIVATE
);
        Editor edit = share.edit();
        edit.putString(name, str);
        return edit.commit();
    }

    public synchronized String getString(String name, String def) {
        share = context.getSharedPreferences(DEFAULT_CATCHE, Context.MODE_PRIVATE
);
        return share.getString(name, def);
    }

    public synchronized String getString(String fileName, String name, String def) {
        share = context.getSharedPreferences(fileName, Context.MODE_PRIVATE
);
        return share.getString(name, def);
    }

    public synchronized boolean put(String name, int str) {
        share = context.getSharedPreferences(DEFAULT_CATCHE, Context.MODE_PRIVATE
);
        Editor edit = share.edit();
        edit.putInt(name, str);
        return edit.commit();

    }

    public synchronized boolean put(String fileName, String name, int str) {
        share = context.getSharedPreferences(fileName, Context.MODE_PRIVATE
);
        Editor edit = share.edit();
        edit.putInt(name, str);
        return edit.commit();
    }

    public synchronized int getInt(String name, int def) {
        share = context.getSharedPreferences(DEFAULT_CATCHE, Context.MODE_PRIVATE
);
        return share.getInt(name, def);
    }

    public synchronized int getInt(String fileName, String name, int def) {
        share = context.getSharedPreferences(fileName, Context.MODE_PRIVATE
);
        return share.getInt(name, def);
    }


    public synchronized boolean put(String name, long str) {
        share = context.getSharedPreferences(DEFAULT_CATCHE, Context.MODE_PRIVATE
    );
        Editor edit = share.edit();
        edit.putLong(name, str);
        return edit.commit();

    }

    public synchronized boolean put(String fileName, String name, long str) {
        share = context.getSharedPreferences(fileName, Context.MODE_PRIVATE
);
        Editor edit = share.edit();
        edit.putLong(name, str);
        return edit.commit();
    }

    public synchronized long getLong(String name, long def) {
        share = context.getSharedPreferences(DEFAULT_CATCHE, Context.MODE_PRIVATE
);
        return share.getLong(name, def);
    }

    public synchronized long getLong(String fileName, String name, long def) {
        share = context.getSharedPreferences(fileName, Context.MODE_PRIVATE
);
        return share.getLong(name, def);
    }


    public boolean put(String name, float str) {
        share = context.getSharedPreferences(DEFAULT_CATCHE, Context.MODE_PRIVATE
);
        Editor edit = share.edit();
        edit.putFloat(name, str);
        return edit.commit();

    }

    public synchronized boolean put(String fileName, String name, float str) {
        share = context.getSharedPreferences(fileName, Context.MODE_PRIVATE
);
        Editor edit = share.edit();
        edit.putFloat(name, str);
        return edit.commit();
    }

    public synchronized float getFloat(String name, float def) {
        share = context.getSharedPreferences(DEFAULT_CATCHE, Context.MODE_PRIVATE
);
        return share.getFloat(name, def);
    }

    public float getFloat(String fileName, String name, float def) {
        share = context.getSharedPreferences(fileName, Context.MODE_PRIVATE
);
        return share.getFloat(name, def);
    }

    public synchronized boolean put(String name, boolean str) {
        share = context.getSharedPreferences(DEFAULT_CATCHE, Context.MODE_PRIVATE
);
        Editor edit = share.edit();
        edit.putBoolean(name, str);
        return edit.commit();

    }

    public synchronized boolean put(String fileName, String name, boolean str) {
        share = context.getSharedPreferences(fileName, Context.MODE_PRIVATE
);
        Editor edit = share.edit();
        edit.putBoolean(name, str);
        return edit.commit();
    }

    public synchronized boolean getBoolean(String name, boolean def) {
        share = context.getSharedPreferences(DEFAULT_CATCHE, Context.MODE_PRIVATE
);
        
        return share.getBoolean(name, def);
    }

    public boolean getBoolean(String fileName, String name, boolean def) {
        share = context.getSharedPreferences(fileName, Context.MODE_PRIVATE
);
        return share.getBoolean(name, def);
    }


    public synchronized boolean put(String name, Serializable obj) {
        share = context.getSharedPreferences(DEFAULT_CATCHE, Context.MODE_PRIVATE
);
        Editor edit = share.edit();
        String serializableConventToString;
        boolean commit = false;
        try {
            serializableConventToString = serializableConventToString(obj);
            edit.putString(name, serializableConventToString);
            commit = edit.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return commit;
    }

    public synchronized boolean put(String fileName, String getName, Serializable obj) {
        share = context.getSharedPreferences(fileName, Context.MODE_PRIVATE
);
        Editor edit = share.edit();
        String serializableConventToString;
        boolean commit = false;
        try {
            serializableConventToString = serializableConventToString(obj);
            edit.putString(getName, serializableConventToString);
            commit = edit.commit();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return commit;
    }

    public synchronized void remove(String name) {
        share = context.getSharedPreferences(DEFAULT_CATCHE, Context.MODE_PRIVATE
);
        Editor edit = share.edit();
        edit.remove(name);
        edit.commit();
    }

    public synchronized void remove(String fileName, String name) {
        share = context.getSharedPreferences(fileName, Context.MODE_PRIVATE
);
        Editor edit = share.edit();
        edit.remove(name);
        edit.commit();
    }

    public synchronized void clear() {
        share = context.getSharedPreferences(DEFAULT_CATCHE, Context.MODE_PRIVATE
);
        Editor edit = share.edit();
        edit.clear();
        edit.commit();
    }

    public synchronized void clear(String fileName) {
        share = context.getSharedPreferences(fileName, Context.MODE_PRIVATE
);
        Editor edit = share.edit();
        edit.clear();
        edit.commit();
    }

    public synchronized Serializable getAsObject(String name) {
        share = context.getSharedPreferences(DEFAULT_CATCHE, Context.MODE_PRIVATE
);
        String str = share.getString(name, null);
        Serializable stringToSerializable = null;
        if (str == null || str.equals("")) {
            return null;
        }
        try {
            stringToSerializable = StringToSerializable(str);

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return stringToSerializable;

    }

    public synchronized Serializable getAsObject(String fileName, String name) {
        share = context.getSharedPreferences(fileName, Context.MODE_PRIVATE
);
        String str = share.getString(name, null);
        Serializable stringToSerializable = null;
        if (str == null || str.equals("")) {
            return null;
        }
        try {
            stringToSerializable = StringToSerializable(str);
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return stringToSerializable;

    }

    public synchronized static String serializableConventToString(Serializable ser)
            throws IOException {
        if (ser == null) {
            return null;
        }
        String serStr = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream;

        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(ser);
        serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();

        return serStr;
    }

    public synchronized static Serializable StringToSerializable(String str) throws IOException,
            ClassNotFoundException {
        if (str == null) {
            return null;
        }
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        Serializable person = (Serializable) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return person;
    }

}
