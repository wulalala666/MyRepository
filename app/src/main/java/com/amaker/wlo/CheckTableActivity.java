package com.amaker.wlo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amaker.util.CheckTable;
import com.amaker.util.HttpUtil;
/**
 * ��̨
 * @author ����־
 */
public class CheckTableActivity extends AppCompatActivity {
	// ��ʾ����״̬��GridView
	private GridView gv;
	// ��������
	private int count;
	// ���������Ϣ���б�
	private List list = new ArrayList();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("��̨");
		// ���õ�ǰActivity�Ľ��沼��
		setContentView(R.layout.check_table);
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_check);
		setSupportActionBar(toolbar);
		// ʵ����
        gv = (GridView) findViewById(R.id.check_table_gridview);
        //��ò����б�
        getTableList();
        // ΪGridView������
        gv.setAdapter(new ImageAdapter(this));
	}
	
	// ��ò�����Ϣ�б���Ϣ�������ź�״̬
	private void getTableList(){
		// ���ʷ�����url
        //EditText ipEditText=(EditText)findViewById(R.id.ip);
		String url =HttpUtil.BASE_URL+"/servlet/CheckTableServlet";
		// ��ѯ���ؽ��
		String result = HttpUtil.queryStringForPost(url);
		// ����ַ�����ת���ɶ�����ӵ��б�
		String[] strs = result.split(";");
		for (int i = 0; i < strs.length; i++) {
			int idx = strs[i].indexOf(",");
			int num = Integer.parseInt(strs[i].substring(0, idx));
			int flag = Integer.parseInt(strs[i].substring(idx+1));
			CheckTable ct = new CheckTable();
			ct.setFlag(flag);
			ct.setNum(num);
			list.add(ct);
		}
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
            return list.size();
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
        	LayoutInflater inflater = 
        		LayoutInflater.from(CheckTableActivity.this);
        	View v = null;
        	ImageView imageView =null;
        	TextView tv =null;
            if (convertView == null) {
            	// ʵ����ͼƬ��ͼ
            	v = inflater.inflate(R.layout.check_table_view,null);
            	// ����ͼƬ��ͼ����
                v.setPadding(8, 8, 8, 8);
            } else {
                v = (View) convertView;
            }
            // ���ImageView����
            imageView = (ImageView) v.findViewById(R.id.check_table_ImageView01);
       	 	// ���TextView����
            tv = (TextView) v.findViewById(R.id.check_tableTextView01);
            // ���CheckTable����
            CheckTable ct = (CheckTable) list.get(position);
            if(ct.getFlag()==1){
            	// ����ImageViewͼƬΪ ����
            	imageView.setImageResource(R.drawable.youren);
            }else{
            	// ����ImageViewͼƬΪ ��λ
            	imageView.setImageResource(R.drawable.kongwei);
            }
            // ΪTextView��������
            tv.setText(ct.getNum()+"");
            return v;
        }
    }
}
