/**
 * 
 */
package com.pivotaldesign.howzthisbuddy;

import com.pivotaldesign.howzthisbuddy.application.HBApplication;
import com.pivotaldesign.howzthisbuddy.fragments.HBReceivedFragment;
import com.pivotaldesign.howzthisbuddy.fragments.HBReceivedNoResponseFragment;
import com.pivotaldesign.howzthisbuddy.fragments.HBReceivedRespondedFragment;
import com.pivotaldesign.howzthisbuddy.util.AppUtilities;
import com.pivotaldesign.howzthisbuddy.util.HBCustomShapeDrawable;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Satish Kolawale
 *
 */
public class HBOpinionReceivedDetailActivity extends FragmentActivity {
	
	HBReceivedFragment hbrf=new HBReceivedFragment();
    private AppUtilities au;
    SharedPreferences spfcreds;
	
	 int respondedcount,pendingcount,reqcount;
    
     //private Long itemid,mobnum;

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
        au=new AppUtilities(getApplicationContext());
       // Bundle b=getIntent().getExtras();
      //  itemid=b.getLong("ItemId");
       // mobnum=b.getLong("MobNum");
       // int pos=b.getInt("position");
        spfcreds=getSharedPreferences("loginprefs", 0);
		setContentView(R.layout.activity_opinion_received_detail);
		TextView hbr_txt_user_profile_name=(TextView)findViewById(R.id.txt_user_profile_name);
    	ImageView hbr_iv_user_pic=(ImageView)findViewById(R.id.img_user_profile_photo);
    	hbr_txt_user_profile_name.setTypeface(HBApplication.getInstance().getRegularFont());
    	String name=spfcreds.getString("username", "");
    	String pic=spfcreds.getString("profilepic", "");
    	if(pic.length()!=0){
    		Bitmap bm=au.StringToBitMap(pic);
        	hbr_iv_user_pic.setImageBitmap(bm);
    	}
    	
    	hbr_txt_user_profile_name.setText(name);
		
		ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        actionBar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent))); 
        actionBar.setCustomView(R.layout.layout_actionbar);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setHomeAsUpIndicator(R.drawable.back_arrow);
        
        
		
        TextView tv_txt_user_profile_name=(TextView) findViewById(R.id.txt_user_profile_name);
        TextView tv_txt_product_detail_name=(TextView) findViewById(R.id.txt_product_detail_name);
        TextView tv_txt_product_detail_product_id=(TextView) findViewById(R.id.txt_product_detail_product_id);
        TextView tv_txt_product_detail_product_description=(TextView) findViewById(R.id.txt_product_detail_product_description);
        TextView tv_txt_product_detail_size=(TextView) findViewById(R.id.txt_product_detail_size);
        TextView tv_txt_product_detail_color=(TextView) findViewById(R.id.txt_product_detail_color);
        TextView tv_txt_product_detail_price=(TextView) findViewById(R.id.txt_product_detail_price);
        TextView tv_txt_opinion_received_detail_invite_more_buddy=(TextView) findViewById(R.id.txt_opinion_received_detail_invite_more_buddy);
        tv_txt_user_profile_name.setTypeface(HBApplication.getInstance().getRegularFont());
        tv_txt_product_detail_name.setTypeface(HBApplication.getInstance().getRegularFont());
        tv_txt_product_detail_product_id.setTypeface(HBApplication.getInstance().getRegularFont());
        tv_txt_product_detail_product_description.setTypeface(HBApplication.getInstance().getNormalFont());
        tv_txt_product_detail_size.setTypeface(HBApplication.getInstance().getNormalFont());
        tv_txt_product_detail_color.setTypeface(HBApplication.getInstance().getNormalFont());
        tv_txt_product_detail_price.setTypeface(HBApplication.getInstance().getBoldFont());
        tv_txt_opinion_received_detail_invite_more_buddy.setTypeface(HBApplication.getInstance().getRegularFont());
        TextView txtViewSelfie = (TextView) findViewById(R.id.txt_product_detail_selfie);
       
        txtViewSelfie.setTypeface(HBApplication.getInstance().getRegularFont());
        TextView txtOldPrice = (TextView) findViewById(R.id.txt_product_detail_product_origin_price);
        txtOldPrice.setPaintFlags(txtOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        txtOldPrice.setTypeface(HBApplication.getInstance().getRegularFont());
        
        TextView txtFirstColorCode = (TextView) findViewById(R.id.txt_product_detail_first_color_code);
        TextView txtSecondColorCode = (TextView) findViewById(R.id.txt_product_detail_second_color_code);
        TextView txtThirdColorCode = (TextView) findViewById(R.id.txt_product_detail_thord_color_code);
        
        
        reqcount=Integer.parseInt(""+hbrf.al_completeresp1.get(0).getOpinionRequestCount());
        respondedcount=Integer.parseInt(""+hbrf.al_completeresp1.get(0).getOpinionResponseCount());
        pendingcount=reqcount-respondedcount;
        tv_txt_product_detail_name.setText(hbrf.al_completeresp1.get(0).getItemBO().getItemTitle());
       // tv_txt_product_detail_product_id.setText(""+hbrf.al_itembo.get(pos).getItemId());
        tv_txt_product_detail_product_description.setText(hbrf.al_completeresp1.get(0).getItemBO().getItemDesc());
        tv_txt_product_detail_price.setText("Now:$"+hbrf.al_completeresp1.get(0).getItemBO().getPrice());
        if(hbrf.al_completeresp1.get(0).getItemSelfieDetailsBO().getSelfiePic().toString().equalsIgnoreCase("null")){
        	 txtViewSelfie.setText("Capture Selfie");
        }else{
        	 txtViewSelfie.setText("View Selfie");
        }
        /*for (int a =0; a<hbrf.completeresp.size();a++)
        {
            HashMap<String, String> tmpData = (HashMap<String, String>) hbrf.completeresp.get(a);
            Set<String> key = tmpData.keySet();
            Iterator it = key.iterator();
            while (it.hasNext()) {
                String hmKey = (String)it.next();
                String hmData = (String) tmpData.get(hmKey);
                System.out.println("Key: "+hmKey +" & Data: "+hmData);

                if(hmKey.equalsIgnoreCase("itemID")&&hmData.equalsIgnoreCase(itemid)){
                }
                it.remove(); // avoids a ConcurrentModificationException
            }

        }  */  
        
        RoundRectShape rs = new RoundRectShape(null/*new float[] { 10, 10, 10, 10, 10, 10, 10, 10 }*/, null, null);
        ShapeDrawable sd = new HBCustomShapeDrawable(rs, Color.RED, Color.WHITE, 0);
        txtFirstColorCode.setBackgroundDrawable(sd);
        
        //RoundRectShape rs = new RoundRectShape(null, null, null);
        ShapeDrawable secondDrawable = new HBCustomShapeDrawable(rs, Color.GRAY, Color.WHITE, 0);
        txtSecondColorCode.setBackgroundDrawable(secondDrawable);
        
        
        ShapeDrawable thirdDrawable = new HBCustomShapeDrawable(rs, Color.YELLOW, Color.WHITE, 0);
        txtThirdColorCode.setBackgroundDrawable(thirdDrawable);
         
        ViewPager pager = (ViewPager) findViewById(R.id.viewPager_opinion_received_detail);
		pager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));
		
		PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_opinion_received_title_strip);
		//pagerTabStrip.setDrawFullUnderline(true);
		pagerTabStrip.setTabIndicatorColor(Color.BLUE);
        
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
	    switch (menuItem.getItemId()) {
	        case android.R.id.home:
	            finish();
	    }
	    return (super.onOptionsItemSelected(menuItem));
	}
	
	 
	class SectionsPagerAdapter extends FragmentStatePagerAdapter {
		String[] _sections = new String[]{ "Responded("+respondedcount+")", "No Response("+pendingcount+")" };
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position) {
				case 0:
					fragment = new HBReceivedRespondedFragment();
					break;
				case 1:
					fragment = new HBReceivedNoResponseFragment();
	
				default:
					break;
			}
			Bundle args = new Bundle();
			fragment.setArguments(args);			
			return fragment;
		}

		@Override
		public int getCount() {
			// Show total pages.
			return _sections.length;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			//Locale locale = Locale.getDefault();
			return _sections[position];
		}
	}
	
	
}