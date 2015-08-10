/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.pivotaldesign.howzthisbuddy.HBOpinionGivenDetailActivity;
import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.adapter.HBGivenAdapter;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;
import com.pivotaldesign.howzthisbuddy.bean.ItemBO;
import com.pivotaldesign.howzthisbuddy.bean.ItemSelfieDetailsBO;
import com.pivotaldesign.howzthisbuddy.bean.OpinionBO;
import com.pivotaldesign.howzthisbuddy.bean.OpinionsGivenDetailsBO;
import com.pivotaldesign.howzthisbuddy.bean.UserItemOpinionBO;
import com.pivotaldesign.howzthisbuddy.model.HBConstants;
import com.pivotaldesign.howzthisbuddy.model.HBGiven;
import com.pivotaldesign.howzthisbuddy.model.HBNotifier;
import com.pivotaldesign.howzthisbuddy.util.AppUtilities;
import com.pivotaldesign.howzthisbuddy.util.CheckInternet;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Path.Op;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Satish Kolawale
 *
 */
public class HBGivenFragment extends Fragment {

	private SharedPreferences hbh_spf_login_details;
	private HBNotifier _notifier = null;
    private AppUtilities au;
    private CheckInternet ci;
    private Gson hbgf_gson_req;
    private HashMap<String,String> hbgf_hm_givenfragment_req_params;
    private String number;
    private String hbgf_str_resp_givenfragment;
    private ListView listGivenBuddy;
    private JSONArray hbgf_ja_resp=null;
    private String listdetail;
    public static ArrayList<ItemBO> al_itm_given;
    public static ArrayList<ItemBO> al_itm_pending;
    public static ArrayList<OpinionBO>al_opinion_given;
    public static ArrayList<OpinionBO>al_opinion_pending;
    public static ArrayList<ItemSelfieDetailsBO>al_selfie_given;
    public static ArrayList<ItemSelfieDetailsBO>al_selfie_pending;
    public static ArrayList<OpinionsGivenDetailsBO> al_opiniongivendetal;
	public HBGivenFragment(HBNotifier notifier) {
		this._notifier = notifier;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_given, container, false);
        hbgf_hm_givenfragment_req_params=new HashMap<String, String>();
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
        
        TextView txtScreenTitle = (TextView) rootView.findViewById(R.id.txt_screen_title);
        txtScreenTitle.setText(getResources().getString(R.string.str_opinion_given_title));
        txtScreenTitle.setTypeface(HBApplication.getInstance().getBoldFont());
        
        TextView txtBuddyName = (TextView) rootView.findViewById(R.id.txt_user_profile_name);
        txtBuddyName.setTypeface(HBApplication.getInstance().getBoldFont());
         
        listGivenBuddy = (ListView) rootView.findViewById(R.id.list_given_buddy);
        listGivenBuddy.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				JSONObject jobj=new JSONObject();
				try {
					jobj.put("requestPhoneNumber", hbgf_ja_resp.getJSONObject(position).getString("requestPhoneNumber"));
					jobj.put("responsePhoneNumber", number);
					listdetail=jobj.toString();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new HBgetOpinionsToBeGivenListDetail().execute("");
				//showGivenDetailActivity();
			}
		});
        new HBgetOpinionsToBeGivenList().execute("");
        
        
        return rootView;
    }
	
	
	private void showGivenDetailActivity() {
		startActivityForResult(new Intent(getActivity(), HBOpinionGivenDetailActivity.class), 101);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 101) {
			_notifier.notifier(3);
		}
	}


	class HBgetOpinionsToBeGivenList extends AsyncTask<String, String, String> {
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
			hbgf_gson_req = new Gson();
			hbgf_hm_givenfragment_req_params.put("phoneNumber",number);

		    String reg_params = hbgf_gson_req.toJson(hbgf_hm_givenfragment_req_params);
		    hbgf_str_resp_givenfragment=au.makePostRequest(HBConstants.getOpinionsToBeGivenList, reg_params);
				return hbgf_str_resp_givenfragment;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.dismiss();
			try {
				hbgf_ja_resp=new JSONArray(hbgf_str_resp_givenfragment.toString());
				HBGiven givens[] = new HBGiven[hbgf_ja_resp.length()];
		        for (int i = 0; i < givens.length; i++) {
					HBGiven given = new HBGiven();
					given.setId(i);
					given.setMobilenumber(hbgf_ja_resp.getJSONObject(i).getString("requestPhoneNumber"));
					given.setGivenName("Walter White " + i);
					given.setGivenCount(hbgf_ja_resp.getJSONObject(i).getInt("opinionGivenCount"));
					given.setPendingCount(hbgf_ja_resp.getJSONObject(i).getInt("opinionPendingCount"));
					givens[i] = given;
				}
		        
		        HBGivenAdapter givenAdapter = new HBGivenAdapter(getActivity(), givens);
		        listGivenBuddy.setAdapter(givenAdapter);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}


	class HBgetOpinionsToBeGivenListDetail extends AsyncTask<String, String, String> {
		ProgressDialog progress;
		JSONArray hbgf_ja_resp=null;
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
			
		    hbgf_str_resp_givenfragment=au.makePostRequest(HBConstants.getOpinionsToBeGivenDetail, listdetail);
				return hbgf_str_resp_givenfragment;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.dismiss();
			JSONObject hbgf_ga_resp;
			OpinionsGivenDetailsBO opi_giv_det_bo=new OpinionsGivenDetailsBO();
			UserItemOpinionBO usr_itm_opi_bo=new UserItemOpinionBO();
			ItemBO item_bo=new ItemBO();
			OpinionBO opinion_bo=new OpinionBO();
			ItemSelfieDetailsBO item_selfie_det_bo=new ItemSelfieDetailsBO();
			al_opiniongivendetal=new ArrayList<OpinionsGivenDetailsBO>();
			al_itm_given=new ArrayList<ItemBO>();
			al_itm_pending=new ArrayList<ItemBO>();
			al_opinion_given=new ArrayList<OpinionBO>();
			al_opinion_pending=new ArrayList<OpinionBO>();
			al_selfie_given=new ArrayList<ItemSelfieDetailsBO>();
			al_selfie_pending=new ArrayList<ItemSelfieDetailsBO>();
			JSONArray hbgf_ja_opinionsgiven;
			try {
				hbgf_ga_resp=new JSONObject(hbgf_str_resp_givenfragment.toString());
				
				opi_giv_det_bo.setRequestPhoneNumber(hbgf_ga_resp.getLong("requestPhoneNumber"));
				opi_giv_det_bo.setResponsePhoneNumber(hbgf_ga_resp.getLong("responsePhoneNumber"));
				if(!hbgf_ga_resp.get("opinionsGiven").toString().equalsIgnoreCase("null")){
					try {
						hbgf_ja_opinionsgiven = hbgf_ga_resp.getJSONArray("opinionsGiven");
						for(int i=0;i<hbgf_ja_opinionsgiven.length();i++){
							item_bo.setItemId(hbgf_ja_opinionsgiven.getJSONObject(i).getLong("itemId"));
							item_bo.setItemDesc(hbgf_ja_opinionsgiven.getJSONObject(i).getString("itemDesc"));
							item_bo.setItemTitle(hbgf_ja_opinionsgiven.getJSONObject(i).getString("itemTitle"));
							item_bo.setVendorId(hbgf_ja_opinionsgiven.getJSONObject(i).getLong("vendorId"));
							item_bo.setVendorProductId(hbgf_ja_opinionsgiven.getJSONObject(i).getString("vendorProductId"));
							item_bo.setProductUrl(hbgf_ja_opinionsgiven.getJSONObject(i).getString("productUrl"));
							item_bo.setPrice(hbgf_ja_opinionsgiven.getJSONObject(i).getString("price"));
						    al_itm_given.add(item_bo);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						hbgf_ja_opinionsgiven=null;
						e.printStackTrace();
					}
				}
				/*HBGiven givens[] = new HBGiven[hbgf_ja_resp.length()];
		        for (int i = 0; i < givens.length; i++) {
					HBGiven given = new HBGiven();
					given.setId(i);
					given.setMobilenumber(hbgf_ja_resp.getJSONObject(i).getString("requestPhoneNumber"));
					given.setGivenName("Walter White " + i);
					given.setGivenCount(hbgf_ja_resp.getJSONObject(i).getInt("opinionGivenCount"));
					given.setPendingCount(hbgf_ja_resp.getJSONObject(i).getInt("opinionPendingCount"));
					givens[i] = given;
				}*/
		        
		        /*HBGivenAdapter givenAdapter = new HBGivenAdapter(getActivity(), givens);
		        listGivenBuddy.setAdapter(givenAdapter);*/
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}






}
