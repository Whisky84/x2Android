
package it.wm.perdue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnActionExpandListener;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import it.wm.android.adaptor.EsercentiPagerAdapter;

public class EsercentiBaseActivity extends SherlockFragmentActivity implements OnQueryTextListener {
    
    private String                category = "";
    private EsercentiListFragment currentEsercentiFrag;
    private EsercentiPagerAdapter mAdapter;
    private ViewPager             mPager;
    private PageIndicator         mIndicator;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.esercenti_base_activity);
        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            category = extras.getString("category");
        }
        
        ActionBar bar = getSupportActionBar();
        bar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP
                | ActionBar.DISPLAY_SHOW_HOME
                | ActionBar.DISPLAY_SHOW_TITLE);
        
        bar.setTitle(category);
        
        // FragmentManager fragmentManager = getSupportFragmentManager();
        // FragmentTransaction fragmentTransaction =
        // fragmentManager.beginTransaction();
        
        // currentEsercentiFrag = new EsercentiListFragment();
        // currentEsercentiFrag.setCategory(category.toLowerCase());
        // fragmentTransaction.replace(R.id.fragmentDistance,
        // currentEsercentiFrag);
        // fragmentTransaction.commit();
        
        // currentEsercentiFrag = (EsercentiListFragment)
        // getSupportFragmentManager().
        // findFragmentById(R.id.esercentiFragmentDistance);
        // currentEsercentiFrag.setCategory(category.toLowerCase());
        
        mAdapter = new EsercentiPagerAdapter(getSupportFragmentManager(), category);
        
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        
        mIndicator = (TitlePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        
    }
    
    /* *** BEGIN: OptionsMenu Methods **************** */
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        getSupportMenuInflater().inflate(R.menu.esercenti_menu, menu);
        // mSearchView.setOnCloseListener(this);
        setupSearchView(menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.putExtra(Intent.EXTRA_TEXT, MainActivity.DOVE_USARLA_TAB_TAG);
                NavUtils.navigateUpTo(this, intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void setupSearchView(Menu menu) {
        
        SearchView mSearchView = (SearchView) menu.findItem(R.id.abSearch)
                .getActionView();
        mSearchView.setOnQueryTextListener(this);
        menu.findItem(R.id.abSearch).setOnActionExpandListener(new OnActionExpandListener() {
            
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // TODO Auto-generated method stub
                return true;
            }
            
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // do what you want to when close the sesarchview
                // remember to return true;
                Log.d("*******", "onMenuItemActionCollapse");
                // currentEsercentiFrag.clearSearchingResults();
                for (int i = 0; i < mAdapter.getCount(); i++) {
                    EsercentiListFragment f = (EsercentiListFragment) mAdapter.getItem(i);
                    f.clearSearchingResults();
                }
                return true;
            }
        });
    }
    
    /* *** END: OptionsMenu Methods **************** */
    
    /* *** BEGIN: OnQueryTextListener Methods **************** */
    @Override
    public boolean onQueryTextSubmit(String query) {
        
        // TODO: dismettere la tastiera quando si preme "cerca" sulla tastiera
        
        // Hide keyboard
        // InputMethodManager imm = (InputMethodManager) this.getSystemService(
        // SherlockListActivity.INPUT_METHOD_SERVICE);
        // SearchView mSearchView = (SearchView) findViewById(R.id.abSearch);
        // imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
        // mSearchView.setFocusable(false);
        // mSearchView.setFocusableInTouchMode(false);
        return true;
    }
    
    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("EsercentiBaseActivity", "TEXT CHANGE query ");
        for (int i = 0; i < mAdapter.getCount(); i++) {
            EsercentiListFragment f = (EsercentiListFragment) mAdapter.getItem(i);
            f.setDataForQuery(newText);
        }
        return true;
    }
    /* *** END: OnQueryTextListener Methods **************** */
    
}
