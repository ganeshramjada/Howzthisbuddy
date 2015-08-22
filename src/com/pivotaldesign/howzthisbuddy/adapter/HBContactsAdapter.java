/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.adapter;

import java.util.ArrayList;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Satish Kolawale
 *
 */
public class HBContactsAdapter extends ArrayAdapter<String> {

	private Context _context = null;
	private Typeface _typefaceRegular = null;
	
	public HBContactsAdapter(Context context, int resource,  String[] objects) {
		super(context, resource, objects);
		this._context = context;
		_typefaceRegular = HBApplication.getInstance().getRegularFont();
	}

	private static class ViewHolder
	{
		TextView txtBuddyName, txtBuddyPhoneNumber;
		ImageView imgBuddyPhoto;
		CheckBox checkBuddySelected;
	}
	
	ViewHolder holder = null;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String buddyName = getItem(position);
		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.layout_received_buddy_contact_item, null);
			
			holder = new  ViewHolder();
			holder.txtBuddyName = (TextView) convertView.findViewById(R.id.txt_received_buddy_contact_name);
			holder.txtBuddyPhoneNumber = (TextView) convertView.findViewById(R.id.txt_received_buddy_contact_phone);
			holder.checkBuddySelected = (CheckBox) convertView.findViewById(R.id.check_received_buddy_contact);
			holder.imgBuddyPhoto = (ImageView) convertView.findViewById(R.id.img_received_buddy_contact_photo);
				
			holder.txtBuddyName.setTypeface(_typefaceRegular);
			holder.txtBuddyPhoneNumber.setTypeface(_typefaceRegular);
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		
		 
		
		 
			
		if (holder.txtBuddyName != null)
			holder.txtBuddyName.setText(buddyName);
			 
		if(holder.txtBuddyPhoneNumber != null)
			holder.txtBuddyPhoneNumber.setText("123-456-7890");
		return convertView;
	}
}
