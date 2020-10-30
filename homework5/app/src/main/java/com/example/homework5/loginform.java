package com.example.homework5;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homework5.bean.UserInfo;
import com.example.homework5.database.UserDBHelper;
import com.example.homework5.util.ViewUtil;


public class loginform extends AppCompatActivity implements OnCheckedChangeListener, View.OnClickListener, View.OnFocusChangeListener {

    private RadioGroup rg_login; // 声明一个单选组对象
    private RadioButton rb_scale; // 声明一个单选按钮对象
    private RadioButton rb_scale2;
    private EditText et_phone; // 声明一个编辑框对象
    private TextView tv_password; // 声明一个文本视图对象
    private EditText et_password; // 声明一个编辑框对象
    private Button btn_forget; // 声明一个忘记密码按钮控件对象
    private Button btn_login; // 声明一个登录按钮控件对象
    private TextView ck_remember; // 声明一个文本对象 记住密码
    private Switch switch1;
    private boolean bRemember = false; // 是否记住密码
    private String mVerifyCode; // 验证码
    private String mPassword = ""; // 默认密码
    private SharedPreferences mShared; // 声明一个共享参数对象
    private Button int_register;


    private UserDBHelper mHelper; // 声明一个用户数据库的帮助器对象

    private int mRequestCode = 0; // 跳转页面时的请求代码






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginform);

        // 从布局文件中获取名叫rg_login的单选组
        rg_login = findViewById(R.id.rg_scale);

        rb_scale = findViewById(R.id.rb_scale);
        rb_scale2 = findViewById(R.id.rb_scale2);

        et_phone = findViewById(R.id.et_phone);
        tv_password = findViewById(R.id.tv_password);
        et_password = findViewById(R.id.et_password);
        btn_forget = findViewById(R.id.btn_forget);
        btn_login = findViewById(R.id.btn_login);
        ck_remember= findViewById(R.id.rm_password);
        switch1=findViewById(R.id.switch1);
        int_register=findViewById(R.id.int_register);



        // 设置单选按钮图片样式
        Drawable drawable = this.getResources().getDrawable(R.drawable.radio_selector);
        drawable.setBounds(0,0,70,70);
        rb_scale.setCompoundDrawablePadding(20);
        rb_scale.setCompoundDrawables(drawable,null,null,null);

        Drawable drawable2 = this.getResources().getDrawable(R.drawable.radio_selector);
        drawable2.setBounds(0,0,70,70);
        rb_scale2.setCompoundDrawablePadding(20);
        rb_scale2.setCompoundDrawables(drawable2,null,null,null);

        // 给rg_login设置单选监听器，一旦用户点击组内的单选按钮，就触发监听器的onCheckedChanged方法
        rg_login.setOnCheckedChangeListener(this);

        // 给手机号码编辑框添加文本变化监听器
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone));
        // 给密码编辑框添加文本变化监听器
        et_password.addTextChangedListener(new HideTextWatcher(et_password));

        switch1.setOnCheckedChangeListener(new CheckListener());



        btn_forget.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        int_register.setOnClickListener(this);


        initSpinner();


        //从share_login.xml中获取共享参数对象
        mShared = getSharedPreferences("share_login", MODE_PRIVATE);
        // 获取共享参数中保存的手机号码
        String phone = mShared.getString("phone", "");
        // 获取共享参数中保存的密码
        String password = mShared.getString("password", "");
        et_phone.setText(phone); // 给手机号码编辑框填写上次保存的手机号

        et_password.setText(password); // 给密码编辑框填写上次保存的密码



        // 给密码编辑框注册一个焦点变化监听器，一旦焦点发生变化，就触发监听器的onFocusChange方法
        et_password.setOnFocusChangeListener(this);



    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_scale) {
            et_phone.setText("");
            et_password.setText("");
            tv_password.setText("登录密码");
            et_password.setHint("请输入密码");
            btn_forget.setText("忘记密码");
            ck_remember.setVisibility(View.VISIBLE);
            switch1.setVisibility(switch1.VISIBLE);
        } else if (checkedId == R.id.rb_scale2) {
            et_phone.setText("");
            et_password.setText("");
            tv_password.setText("验证码");
            et_password.setHint("请输入验证码");
            btn_forget.setText("获取验证码");
            ck_remember.setVisibility(View.INVISIBLE);
            switch1.setVisibility(switch1.INVISIBLE);
        }
    }


    private class CheckListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == R.id.switch1) {
                bRemember = isChecked;

            }
        }
    }

    // 定义下拉列表需要显示的文本数组
    private String[] starArray = {"公有", "私有", "张辉辉18990198"};
    private void initSpinner() {
        // 声明一个下拉列表的数组适配器
        ArrayAdapter<String> starAdapter = new ArrayAdapter<String>(this, R.layout.item_select, starArray);
        // 设置数组适配器的布局样式
        starAdapter.setDropDownViewResource(R.layout.item_dropdown);
        // 从布局文件中获取名叫sp_dialog的下拉框
        Spinner sp_dialog = findViewById(R.id.sp_dialog);
        // 设置下拉框的标题
        sp_dialog.setPrompt("请选择类别");
        // 设置下拉框的数组适配器
        sp_dialog.setAdapter(starAdapter);
        // 设置下拉框默认显示第三项
        sp_dialog.setSelection(2);
        // 给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        sp_dialog.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) new MySelectedListener());
    }
    // 定义一个选择监听器，它实现了接口OnItemSelectedListener
    class MySelectedListener implements AdapterView.OnItemSelectedListener {
        /* 选择事件的处理方法
        adapter:适配器
        view:视图
        position:第几项
        id:id
        */
        public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
            //获取选择的项的值
            String sInfo = adapter.getItemAtPosition(position).toString();
        }
            // 未选择时的处理方法，通常无需关注
            public void onNothingSelected(AdapterView<?> arg0) {
            }
    }





    // 定义一个编辑框监听器，在输入文本达到指定长度时自动隐藏输入法
    private class HideTextWatcher implements TextWatcher {
        private EditText mView; // 声明一个编辑框对象
        private int mMaxLength; // 声明一个最大长度变量
        private CharSequence mStr; // 声明一个文本串

        public HideTextWatcher(EditText v) {
            //super()从子类中调用父类的构造方法
            super();
            mView = v;
            // 通过反射机制获取编辑框的最大长度
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
            //还没输入
            if (mStr == null || mStr.length() == 0)
                return;
            // 输入文本达到11位（如手机号码）时关闭输入法
            if (mStr.length() == 11 && mMaxLength == 11) {
                ViewUtil.hideAllInputMethod(loginform.this);
            }
            // 输入文本达到8位（如登录密码）时关闭输入法
            if (mStr.length() == 8 && mMaxLength == 8) {
                ViewUtil.hideOneInputMethod(loginform.this, mView);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.int_register){
            Intent intent=new Intent(this,register.class);//跳转到注册页面
            startActivity(intent);
            return;
        }
        String phone = et_phone.getText().toString();
        UserInfo info = mHelper.queryByPhone(et_phone.getText().toString());
        if (info == null){
            Toast.makeText(this, "查无手机号，请先注册", Toast.LENGTH_SHORT).show();
        }
        else{
            if (v.getId() == R.id.btn_forget) { // 点击了“忘记密码”按钮
                if (phone.length() < 11) { // 手机号码不足11位
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (rb_scale.isChecked()) { // 选择了密码方式校验，此时要跳到找回密码页面
                    Intent intent = new Intent(this, forgetpassword.class);
                    // 携带手机号码跳转到找回密码页面
                    intent.putExtra("phone", phone);
                    startActivityForResult(intent, mRequestCode);
                } else if (rb_scale2.isChecked()) { // 选择了验证码方式校验，此时要生成六位随机数字验证码
                    // 生成六位随机数字的验证码,结果用0填充
                    mVerifyCode = String.format("%06d", (int) ((Math.random() * 9 + 1) * 100000));
                    // 弹出提醒对话框，提示用户六位验证码数字
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("请记住验证码");
                    builder.setMessage("手机号" + phone + "，本次验证码是" + mVerifyCode + "，请输入验证码");
                    builder.setPositiveButton("好的", null);
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
             if (v.getId() == R.id.btn_login) { // 点击了“登录”按钮
                if (phone.length() < 11) { // 手机号码不足11位
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (rb_scale.isChecked()) { // 密码方式校验
                    // 输入的密码跟mPassword比较
                    if (!et_password.getText().toString().equals(info.pwd)) {
                        //输入的密码和数据库储存的比较
                        Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show(); }
                    else { // 密码校验通过
                        loginSuccess(); // 提示用户登录成功
                    }
                }
                else if (rb_scale2.isChecked()) { // 验证码方式校验
                    if (!et_password.getText().toString().equals(mVerifyCode)) {
                        Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                    } else { // 验证码校验通过
                        loginSuccess(); // 提示用户登录成功
                    }
                }
            }
        }

    }



        // 忘记密码修改后，从后一个页面携带参数返回当前页面时触发
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == mRequestCode && data != null) {
            // 用户密码已改为新密码，故更新密码变量
            mPassword = data.getStringExtra("new_password");
        }
    }

    // 从修改密码页面返回登录页面，要清空密码的输入框
    @Override
    protected void onRestart() {
        et_password.setText("");
        super.onRestart();
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



    private void loginSuccess() {
        if (bRemember) {
            //把手机号码和密码都保存到共享参数中
            SharedPreferences.Editor editor = mShared.edit(); // 获得编辑器的对象
            editor.putString("phone", et_phone.getText().toString()); // 添加名叫phone的手机号码
            editor.putString("password", et_password.getText().toString()); // 添加名叫password的密码
            //editor.clear();
            editor.commit();// 提交编辑器中的修改

        }
        String desc = String.format("您的手机号码是%s，恭喜你通过登录验证，点击“确定”按钮返回上个页面",
                et_phone.getText().toString());
        // 弹出提醒对话框，提示用户登录成功
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登录成功");
        builder.setMessage(desc);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("我再看看", null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void onFocusChange(View v, boolean hasFocus) {
        String phone = et_phone.getText().toString();
        // 判断是否是密码编辑框发生焦点变化
        if (v.getId() == R.id.et_password) {
            // 用户已输入手机号码，且密码框获得焦点
            if (phone.length() > 0 && hasFocus) {
                // 根据手机号码到数据库中查询用户记录
                UserInfo info = mHelper.queryByPhone(phone);
                if (info != null) {
                    // 找到用户记录，则自动在密码框中填写该用户的密码
                    et_password.setText(info.pwd);
                    mPassword = info.pwd;
                }
            }
        }
    }
}