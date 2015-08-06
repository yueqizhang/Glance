package com.appspot.glancesocial.glance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Jonah on 8/5/15.
 */
public class IntroScreenFragment extends Fragment {
    private final String LOG_TAG = IntroScreenFragment.class.getSimpleName();

    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
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
        Log.v(LOG_TAG, "Number " + mPageNumber);
        switch (mPageNumber) {
            case 0:
                // Set the title view to show the page number.
                ((TextView) rootView.findViewById(R.id.intro_title)).setText(
                        getString(R.string.app_name, mPageNumber));
                ((TextView) rootView.findViewById(R.id.text_instructions)).setText(
                        getString(R.string.intro_text_1));
                rootView.findViewById(R.id.slide_1).setBackgroundColor(
                        getResources().getColor(R.color.blue));
                break;
            case 1:
                // Set the title view to show the page number.
                ((TextView) rootView.findViewById(R.id.intro_title)).setText(
                        getString(R.string.intro_relevant));
                ((TextView) rootView.findViewById(R.id.text_instructions)).setText(
                        getString(R.string.intro_text_2));
                rootView.findViewById(R.id.slide_1).setBackgroundColor(
                        getResources().getColor(R.color.teal));
                break;
            case 2:
                // Set the title view to show the page number.
                ((TextView) rootView.findViewById(R.id.intro_title)).setText(
                        getString(R.string.intro_tailored));
                ((TextView) rootView.findViewById(R.id.text_instructions)).setText(
                        getString(R.string.intro_text_3));
                rootView.findViewById(R.id.slide_1).setBackgroundColor(
                        getResources().getColor(R.color.deep_purple));
                break;
            default:
                Intent intent = new Intent(getActivity(), MainPage.class);
                startActivity(intent);
                break;
        }

        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}
