package com.nmadcreations.newsmarthomev2.ui.main;

import android.content.Context;
import android.util.Log;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nmadcreations.newsmarthomev2.DisableUserFrag;
import com.nmadcreations.newsmarthomev2.GasListFrag;
import com.nmadcreations.newsmarthomev2.NbulbListFrag;
import com.nmadcreations.newsmarthomev2.PlugListFrag;
import com.nmadcreations.newsmarthomev2.R;
import com.nmadcreations.newsmarthomev2.RgbListFrag;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,R.string.tab_text_3,R.string.tab_text_4};
    private final Context mContext;


    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;

    }


    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
       Fragment fragment;
                    switch(position) {
                        case 0:
                            fragment = new RgbListFrag();
                            break;
                        case 1:
                            fragment = new NbulbListFrag();
                            break;
                        case 2:
                            fragment = new PlugListFrag();
                            break;
                        case 3:
                            fragment = new GasListFrag();
                            break;
                        default:
                            fragment = null;
                            break;

                    }
    return fragment;



    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return TAB_TITLES.length;
    }
}