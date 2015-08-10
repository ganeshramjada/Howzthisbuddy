/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.adapter.HBGivenResponseAdapter;
import com.pivotaldesign.howzthisbuddy.adapter.HBPendingResponseAdapter;
import com.pivotaldesign.howzthisbuddy.model.HBGivenResponse;
import com.pivotaldesign.howzthisbuddy.model.HBNotifier;

/**
 * @author Satish Kolawale
 *
 */
public class HBPendingDetailFragment extends Fragment {

	private ListView _listPending = null;
	private HBNotifier _notifier = null;
	
	public HBPendingDetailFragment(HBNotifier notifier) {
		this._notifier = notifier;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_given_detail, container, false);
        _listPending = (ListView) rootView.findViewById(R.id.list_opinion_given_detail_given);
        
        HBGivenResponse responses[] = new HBGivenResponse[10];
		for (int index = 0; index < responses.length; index++) {
			HBGivenResponse response = new HBGivenResponse();
			response.setPrice("45.99");
			response.setProductName("Jumba Shoes");
			
			responses[index] = response;
		}
		
		HBPendingResponseAdapter adapter = new HBPendingResponseAdapter(getActivity(), responses);
		_listPending.setAdapter(adapter);
		_listPending.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//showResponseActivity();
				_notifier.notifier(position);
			}
		});
		
		
        
        return rootView;
    }
}