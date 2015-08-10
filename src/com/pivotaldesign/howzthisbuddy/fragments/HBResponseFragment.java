/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;
import com.pivotaldesign.howzthisbuddy.util.HBCustomShapeDrawable;

/**
 * @author Satish Kolawale
 *
 */
public class HBResponseFragment extends Fragment{
	
	private ImageButton _imgLike = null;
	private ImageButton _imgNotSure = null;
	private ImageButton _imgDoNotLike = null;

	@SuppressLint("NewApi")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_response, container, false);
        
        ((TextView) rootView.findViewById(R.id.txt_user_profile_name)).setTypeface(HBApplication.getInstance().getRegularFont());
        
        ((TextView) rootView.findViewById(R.id.txt_product_detail_name)).setTypeface(HBApplication.getInstance().getRegularFont());
        ((TextView) rootView.findViewById(R.id.txt_product_detail_product_id)).setTypeface(HBApplication.getInstance().getRegularFont());
        ((TextView) rootView.findViewById(R.id.txt_product_detail_product_description)).setTypeface(HBApplication.getInstance().getNormalFont());
        ((TextView) rootView.findViewById(R.id.txt_product_detail_size)).setTypeface(HBApplication.getInstance().getNormalFont());
        ((TextView) rootView.findViewById(R.id.txt_product_detail_color)).setTypeface(HBApplication.getInstance().getNormalFont());
        
        ((TextView) rootView.findViewById(R.id.txt_product_detail_price)).setTypeface(HBApplication.getInstance().getBoldFont());
        TextView txtSelfie = (TextView) rootView.findViewById(R.id.txt_product_detail_selfie);
        txtSelfie.setTypeface(HBApplication.getInstance().getRegularFont());
        txtSelfie.setText("View Selfie!");
        ((TextView) rootView.findViewById(R.id.txt_opinion_response_send_your_opinions)).setTypeface(HBApplication.getInstance().getRegularFont());
        
        TextView txtOldPrice = (TextView) rootView.findViewById(R.id.txt_product_detail_product_origin_price);
        txtOldPrice.setPaintFlags(txtOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        txtOldPrice.setTypeface(HBApplication.getInstance().getRegularFont());
        
        ((EditText) rootView.findViewById(R.id.edt_opinion_response_describe)).setTypeface(HBApplication.getInstance().getRegularFont());
        
        
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
         
        _imgLike = (ImageButton) rootView.findViewById(R.id.img_opinion_response_like);
        _imgNotSure = (ImageButton) rootView.findViewById(R.id.img_opinion_response_not_sure);
        _imgDoNotLike = (ImageButton) rootView.findViewById(R.id.img_opinion_response_do_not_like);
        
        _imgLike.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.setSelected(true);
				_imgNotSure.setSelected(false);
				_imgDoNotLike.setSelected(false);
			}
		});
        
        _imgNotSure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.setSelected(true);
				_imgLike.setSelected(false);
				_imgDoNotLike.setSelected(false);
			}
		});
 
        _imgDoNotLike.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			v.setSelected(true);
			_imgNotSure.setSelected(false);
			_imgLike.setSelected(false);
		}
	});
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
}
