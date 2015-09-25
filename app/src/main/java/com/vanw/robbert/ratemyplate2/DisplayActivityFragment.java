package com.vanw.robbert.ratemyplate2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DisplayActivityFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    static final String ARG_PLATE = "plate";

    // TODO: Rename and change types of parameters
    private Plate mPlate;

    private OnFragmentInteractionListener mListener;

    SharedPreferences pref;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param plate Plate currently selected.
     * @return A new instance of fragment DisplayActivityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayActivityFragment newInstance(Plate plate) {
        DisplayActivityFragment fragment = new DisplayActivityFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PLATE, plate);
        fragment.setArguments(args);
        return fragment;
    }

    public DisplayActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPlate = (Plate) getArguments().getSerializable(ARG_PLATE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_display, container, false);

            TextView title = (TextView) v.findViewById(R.id.titleDisplay);
            ImageView image = (ImageView) v.findViewById(R.id.imageDisplay);
            RatingBar ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
            title.setText(mPlate.getTitle());
            image.setImageBitmap(mPlate.getImage());

            // get shared prefs
            pref = getActivity().getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            float rating = pref.getFloat(mPlate.getId()+"", 0);
            ratingBar.setRating(rating);

            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){

                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating,
                                            boolean fromUser) {
                    // set shared prefs
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putFloat(mPlate.getId()+"", rating);
                    editor.apply();
                }

            });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    public void updateDisplayView(Plate plate) {
        TextView title = (TextView) getActivity().findViewById(R.id.titleDisplay);
        title.setText(plate.getTitle());
    }
}
