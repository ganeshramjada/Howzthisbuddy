
/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pivotaldesign.howzthisbuddy.HBHomeActivity;
import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.adapter.HBSearchAdapter;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;
import com.pivotaldesign.howzthisbuddy.bean.Localcontactbo;
import com.pivotaldesign.howzthisbuddy.bean.ResponseContactInfBO;
import com.pivotaldesign.howzthisbuddy.model.HBConstants;
import com.pivotaldesign.howzthisbuddy.util.AppUtilities;
import com.pivotaldesign.howzthisbuddy.util.CheckInternet;
import com.pivotaldesign.howzthisbuddy.util.HBCustomShapeDrawable;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Satish Kolawale
 *
 */
public class HBRequestLaunchFragment extends Fragment implements OnClickListener,OnItemClickListener{
	
	private SharedPreferences hbh_spf_login_details;
	 private CheckInternet ci=new CheckInternet(getActivity());
	private AppUtilities au=new AppUtilities(getActivity());
	private static Localcontactbo[] contactnumbers=null;
	private Localcontactbo lcb;
	private TextView tv_txt_received_invite_buddy;
	private ArrayList<Localcontactbo> cn=new ArrayList<Localcontactbo>();
	private ArrayList<String> hbrf_al_selected_buddies;
	MyAdapter madapter;
	private String number;
	private String hbrf_str_req_opinion;
	private HBHomeActivity hbha=new HBHomeActivity();
	private HBSearchAdapter hbsa;//new HBSearchAdapter(getActivity(), hbrf_al_selected_buddies);
	
	@SuppressLint("NewApi")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		
 
		madapter=new MyAdapter();
        View rootView = inflater.inflate(R.layout.fragment_request_launch, container, false);
        hbh_spf_login_details=getActivity().getSharedPreferences("loginprefs",0);
        TextView hbr_txt_user_profile_name=(TextView)rootView.findViewById(R.id.txt_user_profile_name);
    	ImageView hbr_iv_user_pic=(ImageView)rootView.findViewById(R.id.img_user_profile_photo);
    	hbr_txt_user_profile_name.setTypeface(HBApplication.getInstance().getRegularFont());
        String pic=hbh_spf_login_details.getString("profilepic", "");
    	String name=hbh_spf_login_details.getString("username", "");
    	number=hbh_spf_login_details.getString("mobilenum", "");
        au=new AppUtilities(getActivity());
        ci=new CheckInternet(getActivity());
    	if(pic.length()!=0){
    		Bitmap bm=au.StringToBitMap(pic);
    		hbr_iv_user_pic.setImageBitmap(bm);
    	}
    	hbr_txt_user_profile_name.setText(name);

        
        
        
        
        ((TextView) rootView.findViewById(R.id.txt_user_profile_name)).setTypeface(HBApplication.getInstance().getRegularFont());
        
        ((TextView) rootView.findViewById(R.id.txt_product_detail_name)).setTypeface(HBApplication.getInstance().getRegularFont());
        ((TextView) rootView.findViewById(R.id.txt_product_detail_product_id)).setTypeface(HBApplication.getInstance().getRegularFont());
        ((TextView) rootView.findViewById(R.id.txt_product_detail_product_description)).setTypeface(HBApplication.getInstance().getNormalFont());
        ((TextView) rootView.findViewById(R.id.txt_product_detail_size)).setTypeface(HBApplication.getInstance().getNormalFont());
        ((TextView) rootView.findViewById(R.id.txt_product_detail_color)).setTypeface(HBApplication.getInstance().getNormalFont());
        ((TextView) rootView.findViewById(R.id.txt_product_detail_price)).setTypeface(HBApplication.getInstance().getBoldFont());
        ((TextView) rootView.findViewById(R.id.txt_product_detail_selfie)).setTypeface(HBApplication.getInstance().getRegularFont());
        tv_txt_received_invite_buddy=(TextView) rootView.findViewById(R.id.txt_received_invite_buddy);
        tv_txt_received_invite_buddy.setTypeface(HBApplication.getInstance().getRegularFont());
        tv_txt_received_invite_buddy.setOnClickListener(this);
        
        TextView txtOldPrice = (TextView) rootView.findViewById(R.id.txt_product_detail_product_origin_price);
        txtOldPrice.setPaintFlags(txtOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        txtOldPrice.setTypeface(HBApplication.getInstance().getRegularFont());
        
        final EditText et_edt_received_search_contact=(EditText) rootView.findViewById(R.id.edt_received_search_contact);
        et_edt_received_search_contact.setTypeface(HBApplication.getInstance().getRegularFont());
        
        
        TextView txtFirstColorCode = (TextView) rootView.findViewById(R.id.txt_product_detail_first_color_code);
        TextView txtSecondColorCode = (TextView) rootView.findViewById(R.id.txt_product_detail_second_color_code);
        TextView txtThirdColorCode = (TextView) rootView.findViewById(R.id.txt_product_detail_thord_color_code);
        
        RoundRectShape rs = new RoundRectShape(null/*new float[] { 10, 10, 10, 10, 10, 10, 10, 10 }*/, null, null);
        ShapeDrawable sd = new HBCustomShapeDrawable(rs, Color.RED, Color.WHITE, 0);
        txtFirstColorCode.setBackgroundDrawable(sd);
        
        //RoundRectShape rs = new RoundRectShape(null, null, null);
        ShapeDrawable secondDrawable = new HBCustomShapeDrawable(rs, Color.GRAY, Color.WHITE, 0);
        txtSecondColorCode.setBackgroundDrawable(secondDrawable);
        
        
        ShapeDrawable thirdDrawable = new HBCustomShapeDrawable(rs, Color.YELLOW, Color.WHITE, 0);
        txtThirdColorCode.setBackgroundDrawable(thirdDrawable);
        
        
        getAllContacts(getActivity().getContentResolver());
      
        try {
			if (getActivity()!=null) {
				ListView listBuddy = (ListView) rootView.findViewById(R.id.list_received_buddy_contact);
				 /*adapter = new HBContactsAdapter(getActivity(),
						R.layout.layout_received_buddy_contact_item, contactnumbers);*/
				hbsa=new HBSearchAdapter(getActivity(), hbha.al_rcib);
				listBuddy.setAdapter(hbsa);
				
				listBuddy.setItemsCanFocus(false);
				listBuddy.setOnItemClickListener(this);
				listBuddy.setTextFilterEnabled(true);
				et_edt_received_search_contact.addTextChangedListener(new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
						// When user changed the Text
						//((Filterable) hbsa).getFilter().filter(cs.toString());	
						hbsa.filter(et_edt_received_search_contact.getText().toString());
					}
					
					@Override
					public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
							int arg3) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void afterTextChanged(Editable arg0) {
						// TODO Auto-generated method stub							
					}
				});
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return rootView;
    }
	
	
	private Drawable createRectanagleShape() {
		ShapeDrawable shape = new ShapeDrawable();
		shape.getPaint().setStyle(Style.FILL);
		shape.getPaint().setColor(Color.RED/*getActivity().getResources().getColor(R.color.color_transparent)*/);
		
		shape.getPaint().setStyle(Style.STROKE);
		shape.getPaint().setStrokeWidth(4);
		shape.getPaint().setColor(Color.GREEN/*getActivity().getResources().getColor(R.color.category_green_stroke)*/);

		ShapeDrawable shapeD = new ShapeDrawable();
		shapeD.getPaint().setStyle(Style.FILL);
		shapeD.getPaint().setColor(Color.GREEN);
		ClipDrawable clipDrawable = new ClipDrawable(shapeD, Gravity.CENTER, ClipDrawable.HORIZONTAL);

		LayerDrawable layerDrawable = new LayerDrawable(new Drawable[] { clipDrawable, shape });
		return layerDrawable;
	}
	
	 public  void getAllContacts(ContentResolver cr) {

	        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
	        int i=0;
	        contactnumbers=new Localcontactbo[phones.getCount()];
	        while (phones.moveToNext())
	        {
	          String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
	          String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	          System.out.println(".................."+phoneNumber); 
	          //if(phoneNumber.length()>=10){
	        	  lcb=new Localcontactbo();
	        	  lcb.setId(i);
	        	  lcb.setContactname(name);
	        	  lcb.setContactnumber(phoneNumber);
	        	  contactnumbers[i]=lcb;
	        	  cn.add(lcb);
	        	  i++;
	          }
	        phones.close();
	     }
	@Override
	public void onClick(View v) {
      StringBuilder checkedcontacts= new StringBuilder();
      hbrf_al_selected_buddies=new ArrayList<String>();
      System.out.println(".............."+madapter.mCheckStates.size());
      for(int i = 0; i < hbha.list_response_numbers.size(); i++)

          {
          if(madapter.mCheckStates.get(i)==true)
          {
               checkedcontacts.append(cn.get(i).getContactname());
               checkedcontacts.append("\n");
               hbrf_al_selected_buddies.add(hbha.list_response_numbers.get(i).toString().replaceAll("\\s", ""));

          }
          else
          {
              System.out.println("Not Checked......"+cn.get(i).getContactname());
          }


      }

      new HBRRequestopinion().execute("");
    //  Toast.makeText(getActivity(), checkedcontacts,1000).show();
  }


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		madapter.toggle(position);
	}
	
    class MyAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener,Filterable
    {  
	  private SparseBooleanArray mCheckStates;
       LayoutInflater mInflater;
        TextView tv1,tv;
        CheckBox cb;
        MyAdapter()
        {
            mCheckStates = new SparseBooleanArray(hbha.list_response_numbers.size());
            mInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        
        
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return hbha.al_rcib.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
        	//Localcontactbo buddyName = (Localcontactbo) getItem(position);
            View vi=convertView;
            if(convertView==null)
             vi = mInflater.inflate(R.layout.layout_received_buddy_contact_item, null); 
           TextView txtBuddyName = (TextView) vi.findViewById(R.id.txt_received_buddy_contact_name);
           TextView txtBuddyPhoneNumber = (TextView) vi.findViewById(R.id.txt_received_buddy_contact_phone);
           CheckBox checkBuddySelected = (CheckBox) vi.findViewById(R.id.check_received_buddy_contact);
			ImageView imgBuddyPhoto = (ImageView) vi.findViewById(R.id.img_received_buddy_contact_photo);
				
			txtBuddyName.setTypeface(HBApplication.getInstance().getRegularFont());
			txtBuddyPhoneNumber.setTypeface(HBApplication.getInstance().getRegularFont());
			checkBuddySelected.setTag(position);
			checkBuddySelected.setOnCheckedChangeListener(this);
			checkBuddySelected.setChecked(mCheckStates.get(position, false));
            
			/*txtBuddyName.setText(cn.get(position).getContactname());
			txtBuddyPhoneNumber.setText(cn.get(position).getContactnumber());*/
			
			txtBuddyName.setText(hbha.al_rcib.get(position).getName().toString());
			txtBuddyPhoneNumber.setText(hbha.al_rcib.get(position).getPhonenum().toString());
            
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
   
        public boolean isChecked(int position) {
                return mCheckStates.get(position, false);
            }

     
        public void setChecked(int position, boolean isChecked) {
                mCheckStates.put(position, isChecked);
                System.out.println("hello...........");
                notifyDataSetChanged();
            }

          
        public void toggle(int position) {
                setChecked(position, !isChecked(position));
            }
        
        @Override
        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
            // TODO Auto-generated method stub
             mCheckStates.put((Integer) buttonView.getTag(), isChecked);         
        }


		

		@Override
		public Filter getFilter() {
			// TODO Auto-generated method stub
			return new Filter() {

	            @Override
	            protected void publishResults(CharSequence constraint, FilterResults results) {
	                // TODO Auto-generated method stub
	                if (results.count == 0) {
	                    notifyDataSetInvalidated();
	                }else{
	                    hbha.al_rcib = (ArrayList<ResponseContactInfBO>) results.values;
	                    notifyDataSetChanged();
	                }
	            }

	            @Override
	            protected FilterResults performFiltering(CharSequence constraint) {
	                // TODO Auto-generated method stub
	                FilterResults results = new FilterResults();

	                if (constraint == null || constraint.length() == 0) {
	                    results.values = hbha.al_rcib;
	                    results.count = hbha.al_rcib.size();
	                }else{
	                    ArrayList<ResponseContactInfBO> filter_items = new ArrayList<ResponseContactInfBO>(); 
	                    for (ResponseContactInfBO item : hbha.al_rcib) {
	                        if (item.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
	                            filter_items.add(item);
	                        }
	                    }
	                    results.values =  filter_items ;
	                    results.count = filter_items.size();
	                }
	                return results;
	            }
	        };
		}   
  
    
    
    
    }   

    
	class HBRRequestopinion extends AsyncTask<String, String, String> {
		ProgressDialog progress;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progress = new ProgressDialog(getActivity());
			progress.setMessage("please wait.....");
			progress.setCancelable(false);
			progress.show();
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				JSONObject jobj_itembo=new JSONObject();
				jobj_itembo.put("itemDesc", "A&F Holister T- Shirt white color");
				jobj_itembo.put("itemTitle", "Blue Colored T -Shirt ");
				jobj_itembo.put("vendorId", "2");
				jobj_itembo.put("vendorProductId", "210");
				jobj_itembo.put("productUrl", "productUrl");
				jobj_itembo.put("price", "12");
				//String str_itembo=jobj_itembo.toString();
				JSONObject jobj_req_opinion=new JSONObject();
				jobj_req_opinion.put("itemBO", jobj_itembo);
				jobj_req_opinion.put("requestPhoneNumber", number);
				//JSONArray mJSONArray = new JSONArray(Arrays.asList(hbrf_al_selected_buddies));
				 JSONArray list = new JSONArray(hbrf_al_selected_buddies);
				jobj_req_opinion.put("responsePhoneNumber",list);// hbrf_al_selected_buddies.toString().replaceAll("\\s", ""));
				String req_opinion_params =jobj_req_opinion.toString();
				hbrf_str_req_opinion=au.makeRequeststatusline(HBConstants.requestOpinion, req_opinion_params);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				return hbrf_str_req_opinion;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.dismiss();
			if(hbrf_str_req_opinion.equalsIgnoreCase("HTTP/1.1 200 OK")){

				Toast.makeText(getActivity(), "Request Sent", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getActivity(), "Invalid request", Toast.LENGTH_SHORT).show();
			}
		}
	}

    
	
	
	/*public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(arraylist);
        } else {
            for (DrinksList wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    items.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }*/
	
	/*public void setSearchResult(String str) {
		madapter = new MyAdapter();
        for (String temp : hbha.list_response_name) {
            if (temp.toLowerCase().contains(str.toLowerCase())) {
            	madapter.addItem(temp);
            }
        }
        listBuddy.setAdapter(madapter);
    }*/
	
	
	
	
	
}
