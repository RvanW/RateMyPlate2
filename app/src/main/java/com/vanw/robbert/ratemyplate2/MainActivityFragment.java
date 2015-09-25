package com.vanw.robbert.ratemyplate2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements AdapterView.OnItemClickListener {

    static ArrayList<Plate> plateArray = new ArrayList<>();
    ListView listView;
    CustomListViewAdapter customListViewAdapter;
    OnItemClickListener mCallback;

    // the fragment initialization parameter
    static final String ARG_PLATES = "plates";

    // TODO: Rename and change types of parameters
    private ArrayList<Plate> mPlates;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param plates Plate currently selected.
     * @return A new instance of fragment DisplayActivityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainActivityFragment newInstance(ArrayList<Plate> plates) {
        MainActivityFragment fragment = new MainActivityFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PLATES, plates);
        fragment.setArguments(args);
        return fragment;
    }

    // Container Activity must implement this interface
    public interface OnItemClickListener {
        public void onItemSelected(Plate plate);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity;

        if (context instanceof Activity){
            activity=(Activity) context;
            // This makes sure that the container activity has implemented
            // the callback interface. If not, it throws an exception
            try {
                mCallback = (OnItemClickListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnItemClickListener activity");
            }
        }
        else {
            try {
                mCallback = (OnItemClickListener) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString()
                        + " must implement OnItemClickListener context");
            }
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // dummy data for testing.. should be passed to this fragment's parameters in final version but couldn't get it working
        plateArray.add(new Plate(getActivity(),"Hamburger with bacon"));
        plateArray.add(new Plate(getActivity(),"Cheeseburger"));
        plateArray.add(new Plate(getActivity(),"Pizza quatro formaggi"));
        plateArray.add(new Plate(getActivity(),"Chickenburger"));
        plateArray.add(new Plate(getActivity(),"Pizza chicken"));
        plateArray.add(new Plate(getActivity(),"Jibberish"));
        plateArray.add(new Plate(getActivity(),"Chicken bacon burger"));
        plateArray.add(new Plate(getActivity(),"Pizza bacon"));
        plateArray.add(new Plate(getActivity(),"Jibberish with bacon"));

    }
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        customListViewAdapter = new CustomListViewAdapter(getActivity(),R.layout.list_item_style,plateArray);
        listView = (ListView) v.findViewById(R.id.listView);
        listView.setAdapter(customListViewAdapter);
        listView.setOnItemClickListener(this);
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Plate clickedPlate = plateArray.get(position);
        mCallback.onItemSelected(clickedPlate);
    }


}
