package com.darksoul.chatapp.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.darksoul.chatapp.Fragments.ChatListFragment;
import com.darksoul.chatapp.Fragments.FriendsFragment;
import com.darksoul.chatapp.Fragments.NotificationsFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0: return new ChatListFragment();
            case 1: return new NotificationsFragment();
            case 2: return new FriendsFragment();
            default: return new ChatListFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title=null;
        if(position==0){
            title="Chats";
        }
        if(position==1){
            title="Notifications";
        }
        if(position==2){
            title="Friends";
        }
        return title;
    }
}
