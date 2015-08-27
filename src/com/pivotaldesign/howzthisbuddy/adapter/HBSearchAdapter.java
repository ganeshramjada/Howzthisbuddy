package com.pivotaldesign.howzthisbuddy.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;
import com.pivotaldesign.howzthisbuddy.bean.ResponseContactInfBO;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class HBSearchAdapter extends BaseAdapter{

	
	// Declare Variables
		Context mContext;
		LayoutInflater inflater;
		private List<ResponseContactInfBO> worldpopulationlist = null;
		private ArrayList<ResponseContactInfBO> arraylist;

		public HBSearchAdapter(Context context,
				List<ResponseContactInfBO> worldpopulationlist) {
			mContext = context;
			this.worldpopulationlist = worldpopulationlist;
			inflater = LayoutInflater.from(mContext);
			this.arraylist = new ArrayList<ResponseContactInfBO>();
			this.arraylist.addAll(worldpopulationlist);
		}

		public class ViewHolder {
			TextView rank;
			TextView country;
			TextView population;
			ImageView flag;
		}

		@Override
		public int getCount() {
			return worldpopulationlist.size();
		}

		@Override
		public ResponseContactInfBO getItem(int position) {
			return worldpopulationlist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View view, ViewGroup parent) {
			/*final ViewHolder holder;
			if (view == null) {
				holder = new ViewHolder();
				view = inflater.inflate(R.layout.listview_item, null);
				// Locate the TextViews in listview_item.xml
				holder.rank = (TextView) view.findViewById(R.id.rank);
				holder.country = (TextView) view.findViewById(R.id.country);
				holder.population = (TextView) view.findViewById(R.id.population);
				// Locate the ImageView in listview_item.xml
				holder.flag = (ImageView) view.findViewById(R.id.flag);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			// Set the results into TextViews
			holder.rank.setText(worldpopulationlist.get(position).getRank());
			holder.country.setText(worldpopulationlist.get(position).getCountry());
			holder.population.setText(worldpopulationlist.get(position)
					.getPopulation());
			// Set the results into ImageView
			holder.flag.setImageResource(worldpopulationlist.get(position)
					.getFlag());
			// Listen for ListView Item Click

			return view;*/
	        View vi=view;
            if(view==null)
             vi = inflater.inflate(R.layout.layout_received_buddy_contact_item, null); 
           TextView txtBuddyName = (TextView) vi.findViewById(R.id.txt_received_buddy_contact_name);
           TextView txtBuddyPhoneNumber = (TextView) vi.findViewById(R.id.txt_received_buddy_contact_phone);
           CheckBox checkBuddySelected = (CheckBox) vi.findViewById(R.id.check_received_buddy_contact);
			ImageView imgBuddyPhoto = (ImageView) vi.findViewById(R.id.img_received_buddy_contact_photo);
				
			txtBuddyName.setTypeface(HBApplication.getInstance().getRegularFont());
			txtBuddyPhoneNumber.setTypeface(HBApplication.getInstance().getRegularFont());
			checkBuddySelected.setTag(position);
			//checkBuddySelected.setOnCheckedChangeListener(this);
			//checkBuddySelected.setChecked(mCheckStates.get(position, false));
            
			/*txtBuddyName.setText(cn.get(position).getContactname());
			txtBuddyPhoneNumber.setText(cn.get(position).getContactnumber());*/
			
			txtBuddyName.setText(worldpopulationlist.get(position).getName().toString());
			txtBuddyPhoneNumber.setText(worldpopulationlist.get(position).getPhonenum().toString());
            
            /*
            
            
             TextView tv= (TextView) vi.findViewById(R.id.contact_name);
             tv1= (TextView) vi.findViewById(R.id.phone_number);
             cb = (CheckBox) vi.findViewById(R.id.checkBox_id);
             tv.setText(name1.get(position));
             tv1.setText(phno1.get(position));
             cb.setTag(position);
             cb.setChecked(mCheckStates.get(position, false));
             cb.setOnCheckedChangeListener(this);
*/
            return vi;
		}

		
		// Filter Class
		public void filter(String charText) {
			charText = charText.toLowerCase(Locale.getDefault());
			worldpopulationlist.clear();
			if (charText.length() == 0) {
				worldpopulationlist.addAll(arraylist);
			} else {
				for (ResponseContactInfBO wp : arraylist) {
					if (wp.getName().toLowerCase(Locale.getDefault())
							.contains(charText)) {
						worldpopulationlist.add(wp);
					}
				}
			}
			notifyDataSetChanged();
		}

	
	
	
	
	
}
