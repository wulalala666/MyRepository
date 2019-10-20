package com.amaker.wlo;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.amaker.provider.Menus;
import com.amaker.provider.Tables;
import com.amaker.util.HttpUtil;
/**
 * 
 * @author ����־
 * ʵ������ͬ������
 */
public class UpdateActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("����ͬ��");
		// ���ListViewʵ��
		ListView listView = getListView();
		//listView.setBackgroundColor(Color.RED);
		// ����ListViewҪ�󶨵�����
		String[] items = {"���²��ױ�����[MenuTbl]", "���²���������[TableTbl]" };
		// ʵ����adapter
		ListAdapter adapter = new ArrayAdapter<String>(this,
				R.layout.table_item, items);
		// ΪListView����adapter
		listView.setAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		switch (position) {
		// ���²��ױ�����
		case 0:
			confirm(1);
			break;
		// ������λ������
		case 1:
			confirm(2);
			break;
		default:
			break;
		}
	}
	// ȷ�϶Ի���
	private void confirm(final int item) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("�����Ҫ������?").setCancelable(false).setPositiveButton(
				"ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						if (item == 1) {
							// ���²��ױ�����
							updateMenu();
						} else {
							// ������λ������
							updateTable();
						}
					}
				}).setNegativeButton("ȡ��",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
	// ���²��ױ�
	private void updateMenu() {
		// ���ʷ�����url
		String urlStr =HttpUtil.BASE_URL + "servlet/UpdateServlet";
		try {
			// ʵ����URL����
			URL url = new URL(urlStr);
			// ������
			URLConnection conn = url.openConnection();
			// ���������
			InputStream in = conn.getInputStream();
			// ʵ����DocumentBuilderFactory
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			// ʵ����DocumentBuilder
			DocumentBuilder builder = factory.newDocumentBuilder();
			// ���Document
			Document doc = builder.parse(in);
			// ��ýڵ��б�
			NodeList nl = doc.getElementsByTagName("menu");
			// ��÷������ݽӿ�ContentResolver
			ContentResolver cr = getContentResolver();
			// �������ݵ�Uri
			Uri uri1 = Menus.CONTENT_URI;
			// ɾ������SQLite���ݿ��в��ױ��е�����
			cr.delete(uri1, null, null);

			// ѭ�������ݱ��浽���ױ�
			for (int i = 0; i < nl.getLength(); i++) {
				// ʵ����ContentValues
				ContentValues values = new ContentValues();
				// ����XML�ļ���ò˵�id
				int id = Integer.parseInt(doc.getElementsByTagName("id")
						.item(i).getFirstChild().getNodeValue());
				// ����
				String name = doc.getElementsByTagName("name").item(i)
						.getFirstChild().getNodeValue();
				// ͼƬ·��
				String pic = doc.getElementsByTagName("pic").item(i)
						.getFirstChild().getNodeValue();
				// �۸�
				int price = Integer.parseInt(doc.getElementsByTagName("price")
						.item(i).getFirstChild().getNodeValue());
				// ������
				int typeId = Integer.parseInt(doc
						.getElementsByTagName("typeId").item(i).getFirstChild()
						.getNodeValue());
				// ��ע
				String remark = doc.getElementsByTagName("remark").item(i)
						.getFirstChild().getNodeValue();
				
				// ��ӵ�ContenValues����
				values.put("_id", id);
				values.put("name", name);
				values.put("price", price);
				values.put("pic", pic);
				values.put("typeId", typeId);
				values.put("remark", remark);
				// ���뵽���ݿ�
				cr.insert(uri1, values);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        Toast.makeText(UpdateActivity.this,"ͬ�����ױ�ɹ�",Toast.LENGTH_SHORT).show();
	}

	private void updateTable() {
		//��ûд
		// ���ʷ�����url
		String urlStr =HttpUtil.BASE_URL + "servlet/UpdateTableServlet";
		try {
			// ʵ����URL����
			URL url = new URL(urlStr);
			// ������
			URLConnection conn = url.openConnection();
			// ���������
			InputStream in1 = conn.getInputStream();
			// ʵ����DocumentBuilderFactory
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			// ʵ����DocumentBuilder
			DocumentBuilder builder1 = factory.newDocumentBuilder();
			// ���Document
			Document doc1 = builder1.parse(in1);
			// ��ýڵ��б�
			NodeList nl = doc1.getElementsByTagName("table");
			// ��÷������ݽӿ�ContentResolver
			ContentResolver cr = getContentResolver();
			// �������ݵ�Uri
			Uri uri1 = Tables.CONTENT_URI;
			// ɾ������SQLite���ݿ��в��ױ��е�����
			cr.delete(uri1, null, null);

			// ѭ�������ݱ��浽���ױ�
			for (int i = 0; i < nl.getLength(); i++) {
				// ʵ����ContentValues
				ContentValues values = new ContentValues();
				// ����XML�ļ���ò˵�id
				int id = Integer.parseInt(doc1.getElementsByTagName("id")
						.item(i).getFirstChild().getNodeValue());
				// ����
				String num = doc1.getElementsByTagName("num")
						.item(i).getFirstChild().getNodeValue();
				// ����
				String description = doc1.getElementsByTagName("description")
						.item(i).getFirstChild().getNodeValue();
				// ��ӵ�ContenValues����
				values.put("_id", id);
				values.put("num",num);
				values.put("description",description);
				// ���뵽���ݿ�
				cr.insert(uri1, values);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        Toast.makeText(UpdateActivity.this,"ͬ��������ɹ�",Toast.LENGTH_SHORT).show();
	}
}
