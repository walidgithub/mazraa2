package com.walidbarakat.newprogrammer.mazraaandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    EditText txtcode;
    EditText txtuser;
    EditText txtpass;
    EditText txtconfirm;
    ListView lstview;
    String EditableUser;

    Boolean go;
    DB_Sqlite db = new DB_Sqlite(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);
        txtcode = (EditText) findViewById(R.id.TxtCode);
        txtuser = (EditText) findViewById(R.id.TxtUser);
        txtpass = (EditText) findViewById(R.id.TxtPass);
        txtpass.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        txtconfirm = (EditText) findViewById(R.id.TxtPassConfirm);
        txtconfirm.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        lstview = (ListView) findViewById(R.id.LstView);

        File database = getApplicationContext().getDatabasePath(db.DBname);
        if (false == database.exists()) {
            db.getReadableDatabase();
            if (copyDatabase(this)) {
                go = true;
                //Toast.makeText(MainActivity.this, "تم نسخ قاعدة البيانات بنجاح", Toast.LENGTH_SHORT).show();
            } else {
                go = false;
                // Toast.makeText(MainActivity.this, "خطأ لم يتم نسخ قاعدة البيانات", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            go = true;
        }

        Clear();

        ArrayList<UsersModel> menuItemv = new ArrayList<UsersModel>();

        menuItemv = db.get_All_Users();
        ListAdaptor arryadp = new ListAdaptor(menuItemv);
        lstview.setAdapter(arryadp);

        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    TextView txtcode2 = (TextView) view.findViewById(R.id.TxtCode);
                    db.openDatabase();
                    Cursor Cr = db.mDatabase.rawQuery("select * from Users Where code='" + txtcode2.getText().toString().trim() + "'", null);
                    while (Cr.moveToNext()) {
                        txtcode.setText(Cr.getString(0));
                        txtuser.setText(Cr.getString(1));
                        EditableUser = Cr.getString(1);
                        txtpass.setText(Cr.getString(2));
                        txtconfirm.setText(Cr.getString(2));
                    }
                    Cr.close();
                    db.closeDatabase();
                } catch (Exception h) {
                    Log.e("", h.getMessage());
                }
            }
        });

    }
/*
    public void copyDataBase() {
        try {
            InputStream myInput = getApplicationContext().getAssets().open(db.DBname);
            String outFileName = db.DBLOCATION + db.DBname;
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
*/

    private boolean copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(db.DBname);
            String outFileName = db.DBLOCATION + db.DBname;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private void Clear() {
        lstview.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        txtcode.setEnabled(false);

        int check = db.get_check_Autousers();
        txtcode.setText(String.valueOf(check));

        txtuser.requestFocus();
    }

    public static class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;

            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }

            public char charAt(int index) {
                return '*'; // This is the important part
            }

            public int length() {
                return mSource.length(); // Return default
            }

            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.usersmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.BtnSrch) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("اكتب اسم المستخدم");
            final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    ArrayList<UsersModel> menuItemv = new ArrayList<UsersModel>();

                    menuItemv = db.srch_Users(input.getText().toString().trim());
                    ListAdaptor arryadp = new ListAdaptor(menuItemv);
                    lstview.setAdapter(arryadp);
                }
            });
            builder.setNegativeButton("اغلاق", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        } else if (id == R.id.BtnSave) {
            if (txtuser.getText().toString().trim().equals("") || txtpass.getText().toString().trim().equals("") || txtconfirm.getText().toString().trim().equals("")) {
                Toast.makeText(this, "تأكد من كتابة كل البيانات", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }

            if (!txtconfirm.getText().toString().trim().equals(txtpass.getText().toString().trim())) {
                Toast.makeText(this, "تأكد من كتابة كلمة المرور بشكل صحيح", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }

            String Code = txtcode.getText().toString().trim();
            String Username = txtuser.getText().toString().trim();
            String Pass = txtpass.getText().toString().trim();

            try {
                int check = db.get_check_usersCode(txtcode.getText().toString().trim());
                if (check == 0) {
                    int check2 = db.get_check_users(txtuser.getText().toString().trim());
                    if (check2 == 0) {
                        db.Insert_to_users(Code, Username, Pass);
                        db.Insert_to_Autousers(Code);
                        Toast.makeText(UserActivity.this, "تم اضافة مستخدم جديد بنجاح", Toast.LENGTH_SHORT).show();

                        txtuser.requestFocus();
                    } else {
                        Toast.makeText(UserActivity.this, "يوجد مستخدم بنفس الاسم", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    if (txtuser.getText().toString().trim().equals(EditableUser)) {
//update pass
                        Boolean result = db.updatepass(Code, Pass);
                        if (result == true) {
                            Toast.makeText(UserActivity.this, "تم التعديل بنجاح", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UserActivity.this, "لم يتم التعديل بنجاح", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        int check2 = db.get_check_users(txtuser.getText().toString().trim());
                        if (check2 == 0) {
                            //update all
                            Boolean result = db.updatealluser(Code, Username, Pass);
                            if (result == true) {
                                Toast.makeText(UserActivity.this, "تم التعديل بنجاح", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UserActivity.this, "لم يتم التعديل بنجاح", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(UserActivity.this, "يوجد مستخدم بنفس الاسم", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


                txtuser.setText("");
                txtpass.setText("");
                txtconfirm.setText("");
                txtcode.setText("");
                int check3 = db.get_check_Autousers();
                txtcode.setText(String.valueOf(check3));

                ArrayList<UsersModel> menuItemv = new ArrayList<UsersModel>();

                menuItemv = db.get_All_Users();
                ListAdaptor arryadp = new ListAdaptor(menuItemv);
                lstview.setAdapter(arryadp);

                txtuser.requestFocus();

            } catch (Exception h) {
                Log.e("", h.getMessage());
            }
        } else if (id == R.id.BtnDelete) {
            String Code = txtcode.getText().toString().trim();
            Integer result = db.Deleteuser(Code);
            if (result > 0) {
                Toast.makeText(UserActivity.this, "تم الحذف بنجاح", Toast.LENGTH_SHORT).show();
                txtuser.setText("");
                txtpass.setText("");
                txtconfirm.setText("");
                txtcode.setText("");
                int check = db.get_check_Autousers();
                txtcode.setText(String.valueOf(check));

                ArrayList<UsersModel> menuItemv = new ArrayList<UsersModel>();

                menuItemv = db.get_All_Users();
                ListAdaptor arryadp = new ListAdaptor(menuItemv);
                lstview.setAdapter(arryadp);

                txtuser.requestFocus();
            } else {
                Toast.makeText(UserActivity.this, "لم يتم الحذف بنجاح", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.BtnBack) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //-------------------------------------------------------------------------------------------------
    private class ListAdaptor extends BaseAdapter {
        ArrayList<UsersModel> usersshow = new ArrayList<UsersModel>();

        ListAdaptor(ArrayList<UsersModel> usersshow) {
            this.usersshow = usersshow;
        }

        @Override
        public int getCount() {
            return usersshow.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return usersshow.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.userslayoutshow, null);

            TextView txtcode = (TextView) view.findViewById(R.id.TxtCode);
            TextView txttahsyn = (TextView) view.findViewById(R.id.TxtUsename);

            txtcode.setText(usersshow.get(position).Code);
            txttahsyn.setText(usersshow.get(position).Username);

            return view;
        }
    }

    //--------------------------------------------------------------------------------------------------

}
