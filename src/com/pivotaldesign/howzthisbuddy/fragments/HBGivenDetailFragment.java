/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.adapter.HBGivenResponseAdapter;
import com.pivotaldesign.howzthisbuddy.model.HBGivenResponse;

/**
 * @author Satish Kolawale
 *
 */
public class HBGivenDetailFragment extends Fragment {

	private ListView _listGiven = null;	 
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_given_detail, container, false);
        _listGiven = (ListView) rootView.findViewById(R.id.list_opinion_given_detail_given);
        
        HBGivenResponse responses[] = new HBGivenResponse[10];
		for (int index = 0; index < responses.length; index++) {
			HBGivenResponse response = new HBGivenResponse();
			response.setPrice("45.99");
			response.setProductName("Jumba Shoes");
			
			responses[index] = response;
		}
		
		HBGivenResponseAdapter adapter = new HBGivenResponseAdapter(getActivity(), responses);
		_listGiven.setAdapter(adapter);
        
        return rootView;
    }
}