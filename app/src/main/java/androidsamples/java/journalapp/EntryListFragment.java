package androidsamples.java.journalapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * A fragment representing a list of Items.
 */
public class EntryListFragment extends Fragment {
  private JournalEntry mEntry;
  private EntryListViewModel mEntryListViewModel;
  @NonNull
  public static EntryListFragment newInstance() {
    EntryListFragment fragment = new EntryListFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mEntryListViewModel = new ViewModelProvider(this).get(EntryListViewModel.class);
    setHasOptionsMenu(true);
  }
  interface Callbacks {
    void onEntrySelected(UUID id);
  }
  private Callbacks mCallbacks = null;

  private class EntryViewHolder extends RecyclerView.ViewHolder {

    public EntryViewHolder(@NonNull View itemView) {
      super(itemView);
      itemView.setOnClickListener(this::launchJournalEntryFragment);
    }
    private void launchJournalEntryFragment(View v) {
      mCallbacks.onEntrySelected(mEntry.getUid());
//      Log.d(TAG, "launchJournalEntryFragment with Entry: " + mEntry.title());
      EntryListFragmentDirections.AddEntryAction action = EntryListFragmentDirections.addEntryAction();
      action.setEntryId(mEntry.getUid().toString());

      @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd, yyyy");
      try {
        Date date = formatter.parse(mEntry.getDate());
        Calendar cal = Calendar.getInstance();
        assert date != null;
        cal.setTime(date);

        action.setSelectedDate(cal.get(Calendar.DATE));
        action.setSelectedMonth(cal.get(Calendar.MONTH));
        action.setSelectedYear(cal.get(Calendar.YEAR));
      } catch (ParseException e) {
        e.printStackTrace();
      }

      Navigation.findNavController(v).navigate(action);
    }
  }
  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_entry_list, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.info) {
//      Log.d(TAG, "Info button clicked");
      Navigation.findNavController(getView()).navigate(R.id.infoAction);
    }
    return super.onOptionsItemSelected(item);
  }


  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_entry_list, container, false);

    FloatingActionButton replaceButton = view.findViewById(R.id.btn_add_entry);
    RecyclerView entriesList = view.findViewById(R.id.recyclerView);
    entriesList.setLayoutManager(new LinearLayoutManager(getActivity()));
    JournalEntryListAdapter adapter = new JournalEntryListAdapter(getActivity());
    entriesList.setAdapter(adapter);

    mEntryListViewModel.getAllEntries().observe(requireActivity(), adapter::setEntries);
    replaceButton.setOnClickListener(this::addNewEntry);
    return view;
  }

  public void addNewEntry(View view) {
    JournalEntry entry = new JournalEntry("", "", "", "");
    mEntryListViewModel.insert(entry);

    EntryListFragmentDirections.AddEntryAction action = EntryListFragmentDirections.addEntryAction();
    action.setEntryId(entry.getUid().toString());

    Navigation.findNavController(view).navigate(action);
  }

}