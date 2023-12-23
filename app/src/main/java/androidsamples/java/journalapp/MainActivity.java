package androidsamples.java.journalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements EntryListFragment.Callbacks {

  public static String KEY_ENTRY_ID;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Fragment currentFragment = getSupportFragmentManager()
            .findFragmentById(R.id.nav_host_fragment);
    if (currentFragment == null) {
      Fragment fragment = new EntryListFragment();
      getSupportFragmentManager()
              .beginTransaction()
              .add(R.id.nav_host_fragment, fragment)
              .commit();
    }
    Toolbar actionBarToolBar = findViewById(R.id.toolbar);
    setSupportActionBar(actionBarToolBar);
  }

  @Override
  public void onEntrySelected(UUID entryId) {
    Bundle args = new Bundle();
    args.putSerializable(KEY_ENTRY_ID, entryId);
    Fragment fragment = new EntryDetailsFragment();
    fragment.setArguments(args);
    getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack(null)
            .commit();
  }
}