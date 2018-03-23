package com.elead.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.code.rome.android.repackaged.java.beans.IntrospectionException;
import com.google.code.rome.android.repackaged.java.beans.PropertyDescriptor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class DbHelper<T> extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 0;
    private SQLiteDatabase db;
    private Class clas;
    private String tabName;
    private String id;
    private LinkedHashMap<String, Class> canonicalNames;

    public DbHelper(Context context, Class clas) {
        super(context, clas.getSimpleName().toLowerCase() + ".db", null, DATABASE_VERSION);
        Log.d("a", "a");
        this.clas = clas;
        tabName = clas.getSimpleName();
        getDataBaseConn();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("b", "b");
        db.execSQL(getCreatStr(clas));
    }

    public void getDataBaseConn() {
        db = getWritableDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if (oldVersion < 2) {
//            db.execSQL("ALTER TABLE " + UserDao.TABLE_NAME + " ADD COLUMN " +
//                    UserDao.COLUMN_NAME_AVATAR + " TEXT ;");
//        }
//        if (oldVersion < 5) {
//            db.execSQL("ALTER TABLE " + InviteMessgeDao.TABLE_NAME + " ADD COLUMN " +
//                    InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT + " INTEGER ;");
//        }
//        if (oldVersion < 6) {
//            db.execSQL("ALTER TABLE " + InviteMessgeDao.TABLE_NAME + " ADD COLUMN " +
//                    InviteMessgeDao.COLUMN_NAME_GROUPINVITER + " TEXT;");
//        }
    }


    public void closeDB() {
        try {
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * delete a contact
     *
     * @param
     */
    synchronized public boolean delete(String whereClause,
                                       String[] whereArgs) {
        int count = db.delete(tabName, whereClause, whereArgs);
        boolean flag = (count > 0 ? true : false);
        return flag;
    }

    synchronized public boolean deleteByAttName(String attName,
                                                String value) {
        return delete(attName + " = ?", new String[]{value});
    }

    /**
     * save a contact
     *
     * @param obj
     */
    synchronized public void saveObj(Object obj) {
        ContentValues values = new ContentValues();

        Class clazz = obj.getClass();
        Field[] fields = obj.getClass().getDeclaredFields();// 获得属性
        for (Field field : fields) {
            PropertyDescriptor pd;
            try {
                pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();// 获得get方法
                Object o = getMethod.invoke(obj);// 执行get方法返回一个Object
                System.out.println(field.getName() + "*--*" + o);
                if (null != o) {
                    values.put(field.getName(), (byte[]) o);
                }
            } catch (IntrospectionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (db.isOpen()) {
            db.replace(tabName, null, values);
        }
    }

    synchronized public List<T> getObjectList() {
        List<T> ts = new ArrayList<>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + tabName + " desc", null);
            while (cursor.moveToNext()) {

                for (Map.Entry<String, Class> entry : canonicalNames.entrySet()) {
                    try {
                        T o = (T) Class.forName(clas.getName()).newInstance();
                        String simpleName = entry.getValue().getSimpleName();
                        String name = entry.getKey();
                        name = name.substring(0, 1).toUpperCase() + name.substring(1);
                        Method m = clas.getMethod("get" + name);
                        m = clas.getMethod("set" + name, String.class);

                        Object value = null;
                        if (simpleName.equals("String")) {
                            value = cursor.getString(cursor.getColumnIndex(name));
                        } else if (simpleName.equals("float")) {
                            value = cursor.getFloat(cursor.getColumnIndex(name));
                        } else if (simpleName.equals("int")) {
                            value = cursor.getInt(cursor.getColumnIndex(name));
                        } else if (simpleName.equals("long")) {
                            value = cursor.getLong(cursor.getColumnIndex(name));
                        } else if (simpleName.equals("double")) {
                            value = cursor.getDouble(cursor.getColumnIndex(name));
                        }
                        m.invoke(o, value);
                        ts.add(o);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                }


            }
            cursor.close();
        }
        return ts;
    }


    synchronized int getCount(String name) {
        int count = 0;
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select " + name + " from " + tabName, null);
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            cursor.close();
        }
        return count;
    }


    private String getCreatStr(Class clas) {
        canonicalNames = init(clas.getCanonicalName());
        StringBuilder crearStr = new StringBuilder("CREATE TABLE "
                + tabName + " (");
        for (Map.Entry<String, Class> entry : canonicalNames.entrySet()) {
            crearStr.append(entry.getKey()
                    + getValue(entry.getKey(), entry.getValue()));
        }
        int lastIndexOf = crearStr.toString().lastIndexOf(",");
        String str = crearStr.toString().substring(0, lastIndexOf);
        return str + ");";

    }

    private String getValue(String key, Class value) {

        String str = value.getSimpleName();
        if (key.contains("id") || key.contains("ID") || key.contains("Id")) {
            id = key;
            if (str.endsWith("String")) {
                return asd.get("textid");
            } else {
                return asd.get("intid");
            }
        }
        return asd.get(str);
    }

    private static HashMap<String, String> asd = new HashMap<>();

    static {
        asd.put("float", " INTEGER, ");
        asd.put("int", " INTEGER, ");
        asd.put("double", " INTEGER, ");
        asd.put("boolean", " BOOLEAN, ");
        asd.put("String", " TEXT, ");
        asd.put("intid", " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        asd.put("textid", " TEXT PRIMARY KEY, ");
    }

    private LinkedHashMap<String, Class> init(String classPath) {
        try {
            LinkedHashMap<String, Class> fieldHashMap = new LinkedHashMap<>();
            Class cls = Class.forName(classPath);
            Field[] fieldlist = cls.getDeclaredFields();
            for (int i = 0; i < fieldlist.length; i++) {
                Field fld = fieldlist[i];
                fieldHashMap.put(fld.getName(), fld.getType());
            }
            return fieldHashMap;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
