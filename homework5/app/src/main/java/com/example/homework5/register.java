package com.example.homework5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homework5.bean.UserInfo;
import com.example.homework5.database.UserDBHelper;
import com.example.homework5.util.DateUtil;
import com.example.homework5.util.ViewUtil;

public class register extends AppCompatActivity implements View.OnClickListener {

    private EditText ret_phone;
    private TextView et_name;
    private EditText et_num;
    private Button reb;
    private EditText et_phone;

    private UserDBHelper mHelper; // 声明一个用户数据库的帮助器对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ret_phone=findViewById(R.id.ret_phone);
        et_name=findViewById(R.id.et_name);
        et_num=findViewById(R.id.et_num);
        reb=findViewById(R.id.reb);

        et_phone = findViewById(R.id.et_phone);





        // 给et_phone添加文本变更监听器
        ret_phone.addTextChangedListener(new HideTextWatcher(ret_phone));
        // 给et_password添加文本变更监听器
        et_num.addTextChangedListener(new HideTextWatcher(et_num));

        reb.setOnClickListener(this);


    }

    protected void onResume() {
        super.onResume();
        // 获得用户数据库帮助器的一个实例
        mHelper = UserDBHelper.getInstance(this, 2);
        // 恢复页面，则打开数据库连接
        mHelper.openWriteLink();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 暂停页面，则关闭数据库连接
        mHelper.closeLink();
    }

    // 校验通过，登录成功
    private void registerSuccess() {//点击注册
        Log.d("c","dddddddddd");

        //把手机号码和密码保存为数据库的用户表记录
        // 创建一个用户信息实体类
        UserInfo info = new UserInfo();
        info.name=et_name.getText().toString();
        info.phone = ret_phone.getText().toString();
        info.pwd = et_num.getText().toString();
        info.update_time = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
            // 往用户数据库添加登录成功的用户信息（包含手机号码、密码、登录时间）
        mHelper.insert(info);


        String desc = String.format("您的手机号码是%s,恭喜你通过注册验证，点击“确定”按钮跳转到登录界面",
                ret_phone.getText().toString());
        // 弹出提醒对话框，提示用户注册成功
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("注册成功");
        builder.setMessage(desc);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intentloginform();
            }
        });
        builder.setNegativeButton("我再看看", null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View v) {
        String phone = ret_phone.getText().toString();
        String num = et_num.getText().toString();
        String name = et_name.getText().toString();
        if (v.getId() == R.id.reb) {
            if (et_name.length() < 2) {
                Toast.makeText(this, "请输入正确的名字", Toast.LENGTH_SHORT).show();
                return;
            }
            if (ret_phone.length() != 11) { // 手机号码不足11位
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.d("aaa", "aaaaaaaaaaaaaa");
           // UserInfo info = mHelper.queryByPhone(ret_phone.getText().toString());
            //输入的手机号跟数据库的比较
//            if (ret_phone.getText().toString().equals(info.phone)) {
//                Log.d("b", "bbbbbbbbbb");
//                Toast.makeText(this, "该手机号已注册", Toast.LENGTH_SHORT).show();
//                return;
//            }
            Log.d("c", "eeeeeeeee");
            if (et_num.length() != 8) {
                Log.d("c", "ccccccccccccccccccc");
                Toast.makeText(this, "请输入8位密码", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.d("c", "dddddddddddddd");
            registerSuccess(); // 提示用户注册成功
        }
    }

    public void intentloginform(){
        Intent intent=new Intent(this,loginform.class);//跳转到登录页面
        startActivity(intent);
    }


    private class HideTextWatcher implements TextWatcher {
        private EditText mView;
        private int mMaxLength;
        private CharSequence mStr;

        HideTextWatcher(EditText v) {
            super();
            mView = v;
            mMaxLength = ViewUtil.getMaxLength(v);
        }

        // 在编辑框的输入文本变化前触发
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        // 在编辑框的输入文本变化时触发
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mStr = s;
        }

        // 在编辑框的输入文本变化后触发
        public void afterTextChanged(Editable s) {
            if (mStr == null || mStr.length() == 0)
                return;
            // 手机号码输入达到11位，或者密码/验证码输入达到6位，都关闭输入法软键盘
            if ((mStr.length() == 11 && mMaxLength == 11) ||
                    (mStr.length() == 8 && mMaxLength == 8)) {
                ViewUtil.hideOneInputMethod(register.this, mView);
            }
        }
    }

}