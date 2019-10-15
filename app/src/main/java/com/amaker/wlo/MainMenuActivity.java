package com.amaker.wlo;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amaker.util.HttpUtil;

public class MainMenuActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("���˵�");
        setContentView(R.layout.main_menu);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
    }
    
    // �̳�BaseAdapter
    public class ImageAdapter extends BaseAdapter {
    	// ������
        private Context mContext;
        // ���췽��
        public ImageAdapter(Context c) {
            mContext = c;
        }
        // �������
        public int getCount() {
            return mThumbIds.length;
        }
        // ��ǰ���
        public Object getItem(int position) {
            return null;
        }
        // ��ǰ���id
        public long getItemId(int position) {
            return 0;
        }
        // ��õ�ǰ��ͼ
        public View getView(int position, View convertView, ViewGroup parent) {
        	// ����ͼƬ��ͼ
            ImageView imageView;
            if (convertView == null) {
            	// ʵ����ͼƬ��ͼ
                imageView = new ImageView(mContext);
                // ����ͼƬ��ͼ����
                imageView.setLayoutParams(new GridView.LayoutParams(352,352));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(6, 6, 6, 6);
            } else {
                imageView = (ImageView) convertView;
            }
            // ����ͼƬ��ͼͼƬ��Դ
            imageView.setImageResource(mThumbIds[position]);
            // Ϊ��ǰ��ͼ��Ӽ�����
            switch (position) {
			case 0:
				// ��ӵ�ͼ�����
				imageView.setOnClickListener(orderLinstener);
				break;
			case 1:
				// ��̨������
				imageView.setOnClickListener(unionTableLinstener);
				break;
			case 2:
				// ���ת̨������
				imageView.setOnClickListener(changeTableLinstener);
				break;
			case 3:
				// ��Ӳ�̨������
				imageView.setOnClickListener(checkTableLinstener);
				break;
			case 4:
				// ��Ӹ��¼�����
				imageView.setOnClickListener(updateLinstener);
				break;
			case 6:
				// ���ע��������
				imageView.setOnClickListener(exitLinstener);
				break;
			case 7:
				// ��ӽ��������
				imageView.setOnClickListener(payLinstener);
				break;
			default:
				break;
			}
            
            return imageView;
        }
        // ͼƬ��Դ����
        private Integer[] mThumbIds = {
                R.drawable.diancai, R.drawable.bingtai,
                R.drawable.zhuantai, R.drawable.chatai,
                R.drawable.gengxin, R.drawable.shezhi,
                R.drawable.zhuxiao, R.drawable.jietai
        };
    }
    
    // ���¼�����
    OnClickListener updateLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			// ��������Activity
			intent.setClass(MainMenuActivity.this, UpdateActivity.class);
			startActivity(intent);
		}
	};
    
    // ��̨������
    OnClickListener checkTableLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			// ��������Activity
			intent.setClass(MainMenuActivity.this, CheckTableActivity.class);
			startActivity(intent);
		}
	};
    
    // ���������
    OnClickListener payLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			// ��������Activity
			intent.setClass(MainMenuActivity.this, PayActivity.class);
			startActivity(intent);
		}
	};
    
    // ���ͼ�����
    OnClickListener orderLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			// ��������Activity
			intent.setClass(MainMenuActivity.this, OrderActivity.class);
			startActivity(intent);
			Log.d("test","����������ɹ�");
		}
	};
	// ע��������
    OnClickListener exitLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			logout();
		}
	};
	
	// ת̨������
    OnClickListener changeTableLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			changeTable();
		}
	};
	
	// ��̨������
    OnClickListener unionTableLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			unionTable();
		}
	};
	
	
	// ��̨ϵͳ
	private void changeTable(){
		// ���LayoutInflaterʵ��
		LayoutInflater inflater = LayoutInflater.from(this);
		// ���LinearLayout��ͼʵ��
		View v =inflater.inflate(R.layout.change_table, null);
		// ��LinearLayout�л��EditTextʵ��
		final EditText et1 = (EditText) v.findViewById(R.id.change_table_order_number_EditText);
		final EditText et2 = (EditText) v.findViewById(R.id.change_table_no_EditText);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(" ���Ҫ����λ��")
		       .setCancelable(false)
		       .setView(v)
		       .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	// ��ö�����
		        	String orderId = et1.getText().toString();
		        	// �������
		       		String tableId = et2.getText().toString();
		       		// ��ѯ����
		       		String queryString = "orderId="+orderId+"&tableId="+tableId;
		       		// url
		       		String url = HttpUtil.BASE_URL+"servlet/ChangeTableServlet?"+queryString;
		       		// ��ѯ���ؽ��
		       		String result = HttpUtil.queryStringForPost(url);
		       		// ��ʾ���
		       		Toast.makeText(MainMenuActivity.this,result,Toast.LENGTH_LONG).show();
		           }
		       })
		       .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	
	// ��̨ϵͳ
	private void unionTable(){
		// ʵ����LayoutInflater
		LayoutInflater inflater = LayoutInflater.from(this);
		// ����Զ�����ͼ
		View v =inflater.inflate(R.layout.union_table, null);
		// ���Spinner
		final Spinner spinner1 = (Spinner) v.findViewById(R.id.union_table_Spinner1);
		final Spinner spinner2 = (Spinner) v.findViewById(R.id.union_table_Spinner2);
		// ���ʷ�������URL
		String urlStr =HttpUtil.BASE_URL + "servlet/UnionTableServlet";
		try {
			// ʵ����URL
			URL url = new URL(urlStr);
			// URLConnection ʵ��
			URLConnection conn = url.openConnection();
			// ���������
			InputStream in = conn.getInputStream();
			// ���DocumentBuilderFactory����
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			// ���DocumentBuilder����
			DocumentBuilder builder = factory.newDocumentBuilder();
			// ���Document����
			Document doc = builder.parse(in);
			// ��ýڵ��б�
			NodeList nl = doc.getElementsByTagName("table");
			// Spinner����
			List items = new ArrayList();
			
			// ���XML����
			for (int i = 0; i < nl.getLength(); i++) {
				// ��λ���
				String id = doc.getElementsByTagName("id")
						.item(i).getFirstChild().getNodeValue();
				// ����
				int num = Integer.parseInt(doc.getElementsByTagName("num")
						.item(i).getFirstChild().getNodeValue());
				Map data = new HashMap();
				data.put("id", id);
				items.add(data);
			}
			
			// ���SpinnerAdapter
			SpinnerAdapter as = new 
			SimpleAdapter(this, items, 
					R.layout.table_item,
					new String[] { "id" }, new int[] {R.id.table_item});
			
			// ������
			spinner1.setAdapter(as);
			spinner2.setAdapter(as);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(" ���Ҫ������")
		       .setCancelable(false)
		       .setView(v)
		       .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   TextView tv1 = (TextView) spinner1.getSelectedView();
		        	   TextView tv2 = (TextView) spinner2.getSelectedView();
		        	   
		        	   String tableId1 = tv1.getText().toString();
		        	   String tableId2 = tv2.getText().toString();
		        		// ��ѯ����
		       			String queryString = "tableId1="+tableId1+"&tableId2="+tableId2;
		       			// url
		       			String url =HttpUtil.BASE_URL+"servlet/UnionTableServlet2?"+queryString;
		       			// ��ѯ���ؽ��
		       			String result =  HttpUtil.queryStringForPost(url);
					   Toast.makeText(MainMenuActivity.this,"�����ɹ�",Toast.LENGTH_SHORT).show();
		           }
		       })
		       .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();

	}
	
	
	// �˳�ϵͳ
	private void logout(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("���Ҫ�˳�ϵͳ��")
		       .setCancelable(false)
		       .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	  SharedPreferences pres = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
		        	  SharedPreferences.Editor editor = pres.edit();
		        	  editor.putString("id", "");
		        	  editor.putString("name", "");
		        	  Intent intent = new Intent();
		        	  intent.setClass(MainMenuActivity.this, LoginActivity.class);
		        	  startActivity(intent);
		           }
		       })
		       .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
}