/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;
import com.pivotaldesign.howzthisbuddy.model.HBGivenResponse;

/**
 * @author Satish Kolawale
 *
 */
public class HBGivenResponseAdapter extends ArrayAdapter<HBGivenResponse> {

	private Context _context = null;
	private Typeface _typefaceRegular = null;
	
	public HBGivenResponseAdapter(Context context, HBGivenResponse[] given) {
		super(context, R.layout.layout_opinion_response_given_list_item, given);
		this._context = context;
		_typefaceRegular = HBApplication.getInstance().getRegularFont();
	}
	
	
	private static class ViewHolder
	{
		TextView txtGivenName, txtPrice, txtGivenResponseSelfie;
	}
	
	ViewHolder holder = null;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HBGivenResponse given = getItem(position);
		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.layout_opinion_response_given_list_item, null);
			
			holder = new  ViewHolder();
			holder.txtGivenName = (TextView) convertView.findViewById(R.id.txt_opinion_response_given_list_item_title);
			holder.txtPrice = (TextView) convertView.findViewById(R.id.txt_opinion_response_given_list_item_price);
			holder.txtGivenResponseSelfie = (TextView) convertView.findViewById(R.id.txt_opinion_response_given_list_item_view_selfie);
				
			holder.txtGivenName.setTypeface(_typefaceRegular);
			holder.txtPrice.setTypeface(_typefaceRegular);
			holder.txtGivenResponseSelfie.setTypeface(_typefaceRegular);
			
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		
		if (holder.txtGivenName != null)
			holder.txtGivenName.setText(given.getProductName());
			 
		if(holder.txtPrice != null)
			holder.txtPrice.setText("$" + given.getPrice());
		
	 
		return convertView;
	}
}
