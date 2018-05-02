package com.walidbarakat.newprogrammer.mazraaandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Environment;

import java.util.ArrayList;

/**
 * Created by PC on 18/03/2018.
 */

public class DB_Sqlite extends SQLiteOpenHelper {

    public static final String DBname = "mazraa.db";

    public static final String DBLOCATION = Environment.getDataDirectory() + "/data/com.walidbarakat.newprogrammer.mazraaandroid/databases/";
    public Context mContext;
    public SQLiteDatabase mDatabase;
    private SQLiteStatement insertStatement_logo;

    public DB_Sqlite(Context context) {
        super(context, DBname, null, 1);
        this.mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DBname).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    //Users
    public Boolean Insert_to_users(String Code, String Username, String Pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Code", Code);
        contentValues.put("Username", Username);
        contentValues.put("Pass", Pass);
        long result = db.insert("Users", null, contentValues);

        if (result == -1)
            return false;
        else
            return true;

    }

    public Boolean Insert_to_Autousers(String Code) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Autonum", Code);
        long result = db.insert("Autouser", null, contentValues);

        if (result == -1)
            return false;
        else
            return true;

    }

    public int get_check_users(String InsertUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor Cr = db.rawQuery("select * from Users Where Username='" + InsertUser + "'", null);
        Cr.moveToFirst();
        int count = Cr.getCount();
        return count;
    }

    public int get_check_usersCode(String InsertUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor Cr = db.rawQuery("select * from Users Where code='" + InsertUser + "'", null);
        Cr.moveToFirst();
        int count = Cr.getCount();
        return count;
    }

    public int get_check_Autousers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor Cr = db.rawQuery("select * from AutoUser", null);
        int count = Cr.getCount() + 1;
        return count;
    }


    public ArrayList srch_Users(String InsertUser) {
        ArrayList<UsersModel> arrayList = new ArrayList<>();
        openDatabase();
        Cursor Cr = mDatabase.rawQuery("select * from Users Where Username like '%" + InsertUser + "%'", null);
        Cr.moveToFirst();
        while (Cr.isAfterLast() == false) {
            String Code = Cr.getString(0);
            String Username = Cr.getString(1);
            arrayList.add(new UsersModel(Code, Username));
            Cr.moveToNext();
        }
        Cr.close();
        closeDatabase();
        return arrayList;
    }

    public boolean updatepass(String code, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("pass", pass);
        db.update("users", contentValues, "code = ?", new String[]{code});
        return true;
    }

    public boolean updatealluser(String code, String username, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("pass", pass);
        db.update("users", contentValues, "code = ?", new String[]{code});
        return true;
    }

    public Integer Deleteuser(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("users", "code = ?", new String[]{code});
    }

    public ArrayList get_All_Users() {
        ArrayList<UsersModel> arrayList = new ArrayList<>();
        openDatabase();
        Cursor Cr = mDatabase.rawQuery("select * from Users", null);
        Cr.moveToFirst();
        while (Cr.isAfterLast() == false) {
            String Code = Cr.getString(0);
            String Username = Cr.getString(1);
            arrayList.add(new UsersModel(Code, Username));
            Cr.moveToNext();
        }
        Cr.close();
        closeDatabase();
        return arrayList;
    }

    //----------------------------------------------------------------------------------------------
//DataEntry
    public int get_check_AutoAllnames() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor Cr = db.rawQuery("select * from Autoname", null);
        int count = Cr.getCount() + 1;
        return count;
    }

    //Mother

    public boolean updateallname(String Autonum, String Mpartition, String Dady) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mpartition", Mpartition);
        contentValues.put("dady", Dady);
        db.update("allnames", contentValues, "autonum = ?", new String[]{Autonum});
        return true;
    }

    public boolean updatelastmotherdata(String tablename, String Mpartition, String Dady) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mpartition", Mpartition);
        contentValues.put("dady", Dady);
        db.update("lastmotherdata", contentValues, "tablename = ?", new String[]{tablename});
        return true;
    }

    public boolean updateallnamemother(String Autonum, String Mother, String Mpartition, String Dady) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Mothernum", Mother);
        contentValues.put("mpartition", Mpartition);
        contentValues.put("dady", Dady);
        db.update("allnames", contentValues, "autonum = ?", new String[]{Autonum});
        return true;
    }

    public Boolean Insert_to_Mother(String CreateDate, String username, String autonum, String mothernum, String mpartition, String dady) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CreateDate", CreateDate);
        contentValues.put("username", username);
        contentValues.put("autonum", autonum);
        contentValues.put("mothernum", mothernum);
        contentValues.put("mpartition", mpartition);
        contentValues.put("dady", dady);
        long result = db.insert("allnames", null, contentValues);

        if (result == -1)
            return false;
        else
            return true;

    }

    public Integer Deletelastmotherdata(String Tablename) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("lastmotherdata", "Tablename = ?", new String[]{Tablename});
    }

    public Boolean Insert_to_lastmotherdata(String Tablename, String mpartition, String dady) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Tablename", Tablename);
        contentValues.put("MPartition", mpartition);
        contentValues.put("Dady", dady);
        long result = db.insert("lastmotherdata", null, contentValues);

        if (result == -1)
            return false;
        else
            return true;

    }

    public Boolean Insert_to_autoname(String autonum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("autonum", autonum);
        long result = db.insert("autoname", null, contentValues);

        return result != -1;

    }

    //Baby
    public boolean updateallnameBaby(String ToMother, String Gender, String Birthdate, String EndDate, String Autonum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Tomother", ToMother);
        contentValues.put("Gender", Gender);
        contentValues.put("Birthdate", Birthdate);
        contentValues.put("EndDate", EndDate);
        db.update("allnames", contentValues, "autonum = ?", new String[]{Autonum});
        return true;
    }

    public boolean updateallnameBaby2(String ToMother, String Gender, String Birthdate, String Birthdate2, String Autonum2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Tomother", ToMother);
        contentValues.put("Gender", Gender);
        contentValues.put("Birthdate", Birthdate);
        db.update("allnames", contentValues, "Birthdate = ? AND " + "Autonum = ?", new String[]{Birthdate2, Autonum2});
        return true;
    }

    public boolean updateallnameBaby3(String BabyNum, String ToMother, String Gender, String Birthdate, String EndDate, String Autonum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Tomother", ToMother);
        contentValues.put("Gender", Gender);
        contentValues.put("Birthdate", Birthdate);
        contentValues.put("EndDate", EndDate);
        contentValues.put("BabyNum", BabyNum);
        db.update("allnames", contentValues, "autonum = ?", new String[]{Autonum});
        return true;
    }

    public boolean updateallnameBaby4(String BabyNum, String ToMother, String Gender, String Birthdate, String Birthdate2, String Autonum2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Tomother", ToMother);
        contentValues.put("Gender", Gender);
        contentValues.put("Birthdate", Birthdate);
        contentValues.put("BabyNum", BabyNum);
        db.update("allnames", contentValues, "Birthdate = ? AND " + "Autonum = ?", new String[]{Birthdate2, Autonum2});
        return true;
    }

    public Boolean Insert_to_Baby(String Username, String Autonum, String Babynum, String ToMother, String Gender, String EndDate, String Birthdate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username", Username);
        contentValues.put("Autonum", Autonum);
        contentValues.put("Babynum", Babynum);
        contentValues.put("Tomother", ToMother);
        contentValues.put("Gender", Gender);
        contentValues.put("EndDate", EndDate);
        contentValues.put("Birthdate", Birthdate);
        long result = db.insert("allnames", null, contentValues);

        if (result == -1)
            return false;
        else
            return true;

    }

    public Boolean Insert_to_Baby2(String Username, String Mothernum, String Gender, String Babynum, String Birthdate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username", Username);
        contentValues.put("Mothernum", Mothernum);
        contentValues.put("Gender", Gender);
        contentValues.put("Babynum", Babynum);
        contentValues.put("Birthdate", Birthdate);
        long result = db.insert("allnames", null, contentValues);

        if (result == -1)
            return false;
        else
            return true;

    }

    public Boolean Insert_to_AutoAllnames(String Autonum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Autonum", Autonum);
        long result = db.insert("autoname", null, contentValues);

        if (result == -1)
            return false;
        else
            return true;

    }

    //----------------------------------------------------------------------------------------------
    //DeleteHistory
    public Boolean Insert_to_DeleteHistory(String Username, String DeleteReason, String Mothernum, String Deletedate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username", Username);
        contentValues.put("DeleteReason", DeleteReason);
        contentValues.put("MotherNum", Mothernum);
        contentValues.put("DeleteDate", Deletedate);
        long result = db.insert("DeleteHistory", null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Integer DeleteAllnamesM(String mothernum) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Allnames", "MotherNum = ?", new String[]{mothernum});
    }

    public Boolean Insert_to_DeleteHistoryB(String Username, String DeleteReason, String Babynum, String ToMother, String Gender, String Deletedate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username", Username);
        contentValues.put("DeleteReason", DeleteReason);
        contentValues.put("Babynum", Babynum);
        contentValues.put("ToMother", ToMother);
        contentValues.put("Gender", Gender);
        contentValues.put("DeleteDate", Deletedate);
        long result = db.insert("DeleteHistory", null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Integer DeleteAllnamesB(String babynum) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Allnames", "BabyNum = ? AND " + "MotherNum IS NULL", new String[]{babynum});
    }

    //----------------------------------------------------------------------------------------------
    //FollowBaby
    public int get_check_AutoFollowAutonum() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor Cr = db.rawQuery("select * from FollowAutonum", null);
        int count = Cr.getCount() + 1;
        return count;
    }

    public Integer DeleteFollowBaby(String followautonum) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Allnames", "FollowAutonum = ? ", new String[]{followautonum});
    }

    public Boolean Insert_to_AllnamesB(String Username, String TahsynB, String babyNum, String Medicinb, String Notesb, String TahsynDateb, String Gender, String FollowAutonum, String Birthdate, String MedicinDateb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username", Username);
        contentValues.put("TahsynB", TahsynB);
        contentValues.put("babyNum", babyNum);
        contentValues.put("MedicinB", Medicinb);
        contentValues.put("NotesB", Notesb);
        contentValues.put("TahsynDateB", TahsynDateb);
        contentValues.put("Gender", Gender);
        contentValues.put("FollowAutonum", FollowAutonum);
        contentValues.put("BirthDate", Birthdate);
        contentValues.put("MedicinDateB", MedicinDateb);
        long result = db.insert("Allnames", null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Boolean Insert_to_BabyRemember(String TahsynB, String babyNum, String Medicinb, String Notesb, String TahsynDateb, String Gender, String Birthdate, String MedicinDateb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Tahsyn", TahsynB);
        contentValues.put("BabyNum", babyNum);
        contentValues.put("Medicin", Medicinb);
        contentValues.put("Notes", Notesb);
        contentValues.put("TahsynDate", TahsynDateb);
        contentValues.put("Gender", Gender);
        contentValues.put("BirthDate", Birthdate);
        contentValues.put("MedicinDate", MedicinDateb);
        long result = db.insert("BabyAlarm", null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    //FollowMother
    public Boolean Insert_to_AllnamesM(String Username, String TahsynM, String MotherNum, String MedicinM, String NotesM, String TahsynDateM, String FollowAutonum, String MedicinDateB) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username", Username);
        contentValues.put("Tahsyn", TahsynM);
        contentValues.put("MotherNum", MotherNum);
        contentValues.put("Medicin", MedicinM);
        contentValues.put("NotesM", NotesM);
        contentValues.put("TahsynDate", TahsynDateM);
        contentValues.put("FollowAutonum", FollowAutonum);
        contentValues.put("MedicinDate", MedicinDateB);
        long result = db.insert("Allnames", null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Boolean Insert_to_MotherRemember(String TahsynM, String MotherNum, String MedicinM, String NotesM, String TahsynDateM, String MedicinDateM) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Tahsyn", TahsynM);
        contentValues.put("MotherNum", MotherNum);
        contentValues.put("Medicin", MedicinM);
        contentValues.put("Notes", NotesM);
        contentValues.put("TahsynDate", TahsynDateM);
        contentValues.put("MedicinDate", MedicinDateM);
        long result = db.insert("MotherAlarm", null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Boolean Insert_to_FollowAutonum(String Autonum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FollowAutonum", Autonum);
        long result = db.insert("FollowAutonum", null, contentValues);

        if (result == -1)
            return false;
        else
            return true;

    }

    //----------------------------------------------------------------------------------------------
//Gallery
    public void Insert_To_Gallery(byte[] image, String Discription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Discription", Discription);
        contentValues.put("Img", image);
        db.insert("Gallery", null, contentValues);
    }
    //----------------------------------------------------------------------------------------------
}
