package com.amaker.wlo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.amaker.util.HttpUtil;

public class LoginActivity extends Activity {
	// ������¼��ȡ����ť
	private Button cancelBtn,loginBtn;
	// �����û��������������
	private EditText userEditText,pwdEditText;
//	EditText ipEditText;
//	Button ipButton;
	//public static String   BASE_URL=null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�û���¼");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.login_system);
		// ͨ��findViewById����ʵ�������
		cancelBtn = (Button)findViewById(R.id.cancelButton);
		// ͨ��findViewById����ʵ�������
		loginBtn = (Button) findViewById(R.id.loginButton);
		// ͨ��findViewById����ʵ�������
		userEditText = (EditText)findViewById(R.id.userEditText);
		// ͨ��findViewById����ʵ�������
		pwdEditText = (EditText)findViewById(R.id.pwdEditText);
		//�ֶ���ֵip
//		ipEditText=(EditText)findViewById(R.id.ip);
//		ipButton=(Button)findViewById(R.id.ip_button);
//		ipButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				BASE_URL="http://"+ipEditText.getText().toString()+":8080/WirelessOrder_Server/";
//			}
//		});
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(validate()){
					if(login()){
						Intent intent = new Intent(LoginActivity.this,MainMenuActivity.class);
						startActivity(intent);
					}else{
						showDialog("�û����ƻ�������������������룡");
					}
				}
			}
		});
	}
	// ��¼����
	private boolean login(){
		// ����û�����
		String username = userEditText.getText().toString();
		// �������
		String pwd = pwdEditText.getText().toString();
		// ��õ�¼���
		String result=query(username,pwd);
		if(result!=null&&result.equals("0")){
			return false;
		}else{
			saveUserMsg(result);
			return true;
		}
	}
	
	// ���û���Ϣ���浽�����ļ�
	private void saveUserMsg(String msg){
		// �û����
		String id = "";
		// �û�����
		String name = "";
		// �����Ϣ����
		String[] msgs = msg.split(";");
		int idx = msgs[0].indexOf("=");
		id = msgs[0].substring(idx+1);
		idx = msgs[1].indexOf("=");
		name = msgs[1].substring(idx+1);
		// ������Ϣ
		SharedPreferences pre = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = pre.edit();
		editor.putString("id", id);
		editor.putString("name", name);
		editor.commit();
	}
	
	// ��֤����
	private boolean validate(){
		String username = userEditText.getText().toString();
		if(username.equals("")){
			showDialog("�û������Ǳ����");
			return false;
		}
		String pwd = pwdEditText.getText().toString();
		if(pwd.equals("")){
			showDialog("�û������Ǳ����");
			return false;
		}
		return true;
	}
	private void showDialog(String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg)
		       .setCancelable(false)
		       .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
	// �����û����������ѯ
	private String query(String account,String password){
		// ��ѯ����
		String queryString = "account="+account+"&password="+password;
		// url
		String url =HttpUtil.BASE_URL+"/servlet/LoginServlet?"+queryString;
		// ��ѯ���ؽ��
		return HttpUtil.queryStringForPost(url);
    }
}