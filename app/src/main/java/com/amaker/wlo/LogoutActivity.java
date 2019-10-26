package com.amaker.wlo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LogoutActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	}
	
	
	private void logout(){
		/*AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("���Ҫ�˳�ϵͳ��")
		       .setCancelable(false)
		       .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	  SharedPreferences pres = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
		        	  SharedPreferences.Editor editor = pres.edit();
		        	  editor.putString("id", "");
		        	  editor.putString("name", "");
		        	  
		        	  Intent intent = new Intent();
		        	  intent.setClass(LogoutActivity.this, LoginActivity.class);
		        	  startActivity(intent);
		           }
		       })
		       .setNegativeButton("ȱ��", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();*/
		SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this);
		sweetAlertDialog.setContentText("���Ҫ�˳�ϵͳ��")
						.setConfirmButton("ȷ��", new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog sweetAlertDialog) {
								SharedPreferences pres = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
								SharedPreferences.Editor editor = pres.edit();
								editor.putString("id", "");
								editor.putString("name", "");

								Intent intent = new Intent();
								intent.setClass(LogoutActivity.this, LoginActivity.class);
								startActivity(intent);
							}
						})
						.setCancelButton("ȡ��", new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog sweetAlertDialog) {
								sweetAlertDialog.cancel();
							}
						})
						.show();
	}
}
