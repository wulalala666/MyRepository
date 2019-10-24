package com.amaker.wlo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amaker.util.HttpUtil;

public class PayActivity extends AppCompatActivity {
	// ��ʾ�����ϢWebView
	private WebView wv;
	// ��ѯ�����Ϣ��ť�ͽ��㰴ť
	private Button queryBtn,payBtn;
	// �������
	private EditText orderIdEt;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case android.R.id.home:
				finish();
				break;
				default:
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("����		���µĶ�����Ϊ��"+OrderActivity.orderId);
		// ���õ�ǰActivity�Ľ��沼��
		setContentView(R.layout.pay);
		//ʵ����toolbar
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_pay);
		setSupportActionBar(toolbar);
		ActionBar actionBar=getSupportActionBar();
		if (actionBar!=null){
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		// ���WebViewʵ��
		wv = (WebView)findViewById(R.id.pay_webview);
		// ʵ������ѯ��ť
		queryBtn = (Button)findViewById(R.id.pay_query_Button01);
		// ʵ�������㰴ť
		payBtn = (Button)findViewById(R.id.pay_Button01);
		// ʵ����������ű༭��
		orderIdEt = (EditText)findViewById(R.id.pay_order_number_EditText01);
		
		// ��Ӳ�ѯ�����Ϣ������
		queryBtn.setOnClickListener(queryListener);
		// ��ӽ���Ϣ������
		payBtn.setOnClickListener(payListener);
	}
	
	// ��ѯ�����Ϣ������
	OnClickListener queryListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// ��ö������
			String orderId = orderIdEt.getText().toString();
			// ���������url
			String url =HttpUtil.BASE_URL+"servlet/PayServlet?id="+orderId;
			//�з��������𣿣���
			// ��������Ϣ��WebView����ʾ
			wv.loadUrl(url);
		}
	};
	
	// ���������
	OnClickListener payListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// ��ö������
			String orderId = orderIdEt.getText().toString();
			// ���������url
			String url =HttpUtil.BASE_URL+"servlet/PayMoneyServlet?id="+orderId;
			// ��ò�ѯ��� ������
			String result = HttpUtil.queryStringForPost(url);
			// ��ʾ������
			Toast.makeText(PayActivity.this, result, Toast.LENGTH_LONG).show();
			// ʹ���㰴ťʧЧ
			payBtn.setEnabled(false);
		}
	};

}
