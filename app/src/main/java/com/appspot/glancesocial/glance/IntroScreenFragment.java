package com.appspot.glancesocial.glance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Jonah on 8/5/15.
 */
public class IntroScreenFragment extends Fragment {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = IntroScreenFragment.class.getSimpleName();

    public static final String ARG_PAGE = "page";
    private int mPageNumber;

    // Fragment constructor to take arguments at creation
    public static IntroScreenFragment create(int pageNumber) {
        IntroScreenFragment fragment = new IntroScreenFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public IntroScreenFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.intro_screen_fragment, container, false);
        switch (mPageNumber) {
            case 0:
                // The First Introduction Page
                ((TextView) rootView.findViewById(R.id.intro_title)).setText(
                        getString(R.string.app_name, mPageNumber));
                ((TextView) rootView.findViewById(R.id.text_instructions)).setText(
                        getString(R.string.intro_text_1));
                rootView.findViewById(R.id.slide_1).setBackgroundColor(
                        getResources().getColor(R.color.blue));
                break;
            case 1:
                // The Second Introduction Page
                ((TextView) rootView.findViewById(R.id.intro_title)).setText(
                        getString(R.string.intro_relevant));
                ((TextView) rootView.findViewById(R.id.text_instructions)).setText(
                        getString(R.string.intro_text_2));
                rootView.findViewById(R.id.slide_1).setBackgroundColor(
                        getResources().getColor(R.color.teal));
                break;
            case 2:
                // The Third Introduction Page
                ((TextView) rootView.findViewById(R.id.intro_title)).setText(
                        getString(R.string.intro_tailored));
                ((TextView) rootView.findViewById(R.id.text_instructions)).setText(
                        getString(R.string.intro_text_3));
                rootView.findViewById(R.id.slide_1).setBackgroundColor(
                        getResources().getColor(R.color.deep_purple));
                rootView.findViewById(R.id.continue_button).setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        //Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/josefinsanslight.ttf");
        //((TextView) rootView.findViewById(R.id.intro_title)).setTypeface(typeFace);
        //((TextView) rootView.findViewById(R.id.text_instructions)).setTypeface(typeFace);
        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}
