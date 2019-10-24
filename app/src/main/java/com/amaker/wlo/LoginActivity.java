package com.amaker.wlo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.amaker.util.HttpUtil;

public class LoginActivity extends AppCompatActivity {
	// 声明登录、取消按钮
	private Button cancelBtn,loginBtn;
	// 声明用户名、密码输入框
	private EditText userEditText,pwdEditText;
//	EditText ipEditText;
//	Button ipButton;
	//public static String   BASE_URL=null;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case android.R.id.home:
				finish();
				break;
				default:
		}
		return true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//网络请求不应该放在UI线程里 会阻塞UI 甚至假死 但没办法 honeycomb SDK 3.0后禁止 手动允许
		if(Build.VERSION.SDK_INT > 9){
			StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		// 设置标题
		setTitle("用户登录");
		// 设置当前Activity界面布局
		setContentView(R.layout.login_system);
		//实例化toolbar
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_login);
		setSupportActionBar(toolbar);
		//显示导航键
		ActionBar actionBar=getSupportActionBar();
		if (actionBar!=null){
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		//第一个活动的导航键重写为退出finish

		// 通过findViewById方法实例化组件
		//cancelBtn = (Button)findViewById(R.id.cancelButton);
		// 通过findViewById方法实例化组件
		loginBtn = (Button) findViewById(R.id.loginButton);
		// 通过findViewById方法实例化组件
		userEditText = (EditText)findViewById(R.id.userEditText);
		// 通过findViewById方法实例化组件
		pwdEditText = (EditText)findViewById(R.id.pwdEditText);
		//手动赋值ip
//		ipEditText=(EditText)findViewById(R.id.ip);
//		ipButton=(Button)findViewById(R.id.ip_button);
//		ipButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				BASE_URL="http://"+ipEditText.getText().toString()+":8080/WirelessOrder_Server/";
//			}
//		});
		/*cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});*/
		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(validate()){
					if(login()){
						Intent intent = new Intent(LoginActivity.this,MainMenuActivity.class);
						startActivity(intent);
					}else{
						showDialog("用户名称或者密码错误，请重新输入！");
					}
				}
			}
		});
	}
	// 登录方法
	private boolean login(){
		// 获得用户名称
		String username = userEditText.getText().toString();
		// 获得密码
		String pwd = pwdEditText.getText().toString();
		// 获得登录结果
		String result=query(username,pwd);
		if(result!=null&&result.equals("0")){
			return false;
		}else{
			saveUserMsg(result);
			return true;
		}
	}
	
	// 将用户信息保存到配置文件
	private void saveUserMsg(String msg){
		// 用户编号
		String id = "";
		// 用户名称
		String name = "";
		// 获得信息数组
		String[] msgs = msg.split(";");
		int idx = msgs[0].indexOf("=");
		id = msgs[0].substring(idx+1);
		idx = msgs[1].indexOf("=");
		name = msgs[1].substring(idx+1);
		// 共享信息
		SharedPreferences pre = getSharedPreferences("user_msg",MODE_PRIVATE);
		SharedPreferences.Editor editor = pre.edit();
		editor.putString("id", id);
		editor.putString("name", name);
		editor.commit();
	}
	
	// 验证方法
	private boolean validate(){
		String username = userEditText.getText().toString();
		if(username.equals("")){
			showDialog("用户名称是必填项！");
			return false;
		}
		String pwd = pwdEditText.getText().toString();
		if(pwd.equals("")){
			showDialog("用户密码是必填项！");
			return false;
		}
		return true;
	}
	private void showDialog(String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg)
		       .setCancelable(false)
		       .setPositiveButton("确定", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
	// 根据用户名称密码查询
	private String query(String account,String password){
		// 查询参数
		String queryString = "account="+account+"&password="+password;
		// url
		String url =HttpUtil.BASE_URL+"/servlet/LoginServlet?"+queryString;
		// 查询返回结果
		return HttpUtil.queryStringForPost(url);
    }
}