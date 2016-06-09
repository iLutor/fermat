package com.bitdubai.android_core.app.common.version_1.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Parcelable;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragmentInterface;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Tab;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

import java.util.ArrayList;
import java.util.List;

/**
     * Tabs adapter
     */
    public class TabsPagerAdapter<F extends Fragment & AbstractFermatFragmentInterface> extends FragmentStatePagerAdapter implements FermatUIAdapter{

    private List<AbstractFermatFragmentInterface> lstCurrentFragments;

    private String onlyFragment;
    private String[] titles;

    private Context context;
    private FermatFragmentFactory fragmentFactory;
    private TabStrip tabStrip;
    private FermatSession referenceAppFermatSession;
    private ResourceProviderManager resourcesProviderManager;




    public TabsPagerAdapter(FragmentManager fm,Context context,FermatFragmentFactory walletFragmentFactory,String fragment ,FermatSession walletSession,ResourceProviderManager walletResourcesProviderManager) {
        super(fm);
        this.context=context;
        this.referenceAppFermatSession =walletSession;
        this.fragmentFactory = walletFragmentFactory;
        this.tabStrip=null;
        this.onlyFragment = fragment;
        this.resourcesProviderManager =walletResourcesProviderManager;
        lstCurrentFragments = new ArrayList<>();

    }

    public TabsPagerAdapter(FragmentManager fragmentManager,
                            Context applicationContext,
                            FermatFragmentFactory fermatFragmentFactory,
                            TabStrip tabStrip,
                            FermatSession referenceAppFermatSession,
                            ResourceProviderManager resourceProviderManager) {
        super(fragmentManager);
        this.context=applicationContext;
        this.referenceAppFermatSession = referenceAppFermatSession;
        this.fragmentFactory = fermatFragmentFactory;
        this.tabStrip=tabStrip;
        this.resourcesProviderManager =resourceProviderManager;
        lstCurrentFragments = new ArrayList<>();

        if(tabStrip != null){
            List<Tab> titleTabs = tabStrip.getTabs();
            titles = new String[titleTabs.size()];
            for (int i = 0; i < titleTabs.size(); i++) {
                Tab tab = titleTabs.get(i);
                titles[i] = tab.getLabel();
            }
        }
    }

    public void destroyItem(android.view.ViewGroup container, int position, Object object) {
            FragmentManager manager = ((Fragment) object).getFragmentManager();
            if(manager != null) {
                FragmentTransaction trans = manager.beginTransaction();
                trans.detach((Fragment) object);
                trans.remove((Fragment) object);
                trans.commit();


            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            if(titles.length>0) {
                title = titles[position];
            }
            return title;

        }

        @Override
        public int getCount() {

            if (titles != null)
                return titles.length;
            else if (onlyFragment!=null){
                return 1;
            } else
                return 0;
        }

        @Override
        public Fragment getItem(int position) {
            String fragmentCodeType = null;
            Fragment currentFragment = null;
            if(tabStrip!=null) {
                List<Tab> titleTabs = tabStrip.getTabs();
                for (int j = 0; j < titleTabs.size(); j++) {
                    if (j == position) {
                        Tab tab = titleTabs.get(j);
                        fragmentCodeType = tab.getFragment().getType();
                        break;
                    }
                }
            }else{
                fragmentCodeType = onlyFragment;
            }
            try {
                if(fragmentFactory !=null){
                    currentFragment= fragmentFactory.getFragment(fragmentCodeType, referenceAppFermatSession, resourcesProviderManager);
                    if(currentFragment instanceof AbstractFermatFragmentInterface){
                        lstCurrentFragments.add((AbstractFermatFragmentInterface) currentFragment);
                    }

                }
            } catch (FragmentNotFoundException e) {
                e.printStackTrace();
            }
            return currentFragment;
        }


    @Override
    public Parcelable saveState() {
        return null;
    }


    public List<AbstractFermatFragmentInterface> getLstCurrentFragments() {
        return lstCurrentFragments;
    }

    public void destroyCurrentFragments(){
        for(AbstractFermatFragmentInterface fragment : lstCurrentFragments){
            Fragment fragment1 =(Fragment)fragment;
            FragmentManager manager = fragment1.getFragmentManager();
            if(manager != null) {
                FragmentTransaction trans = manager.beginTransaction();
                trans.detach(fragment1);
                trans.remove(fragment1);
                trans.commit();


            }
        }
        lstCurrentFragments.clear();
    }
}