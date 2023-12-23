package androidsamples.java.journalapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryDetailsFragment # newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryDetailsFragment extends Fragment {

  private static final String TAG = "EntryDetailsFragment";
  private EditText mEditTitle;
  private Button mDateBtn, mStartBtn, mEndBtn;
  static final String Title="Title";

  static final String StartKey="StartKey";

  static final String EndKey="EndKey";

  static final String DateKey="DateKey";
  static String title = "";
  private EntryDetailsViewModel mEntryDetailsViewModel;
  private JournalEntry mEntry;
  private Boolean changed=false;
  private String EditTxtVal;
  private String EditStartVal;
  private String EditEndVal;

  private String EditDateVal;


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    if(savedInstanceState != null) {
      EditTxtVal=savedInstanceState.getString("Title");
      EditStartVal=savedInstanceState.getString("StartKey");
      EditEndVal=savedInstanceState.getString("EndKey");
      EditDateVal=savedInstanceState.getString("DateKey");
    }

    mEntryDetailsViewModel = new ViewModelProvider(requireActivity()).get(EntryDetailsViewModel.class);

    UUID entryId = UUID.fromString(EntryDetailsFragmentArgs.fromBundle(getArguments()).getEntryId());
    Log.d(TAG, "Loading entry: " + entryId);
    mEntryDetailsViewModel.loadEntry(entryId);


  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_entry_details, container, false);
    mEditTitle = view.findViewById(R.id.edit_title);
    mDateBtn = view.findViewById(R.id.btn_entry_date);
    mStartBtn = view.findViewById(R.id.btn_start_time);
    mEndBtn = view.findViewById(R.id.btn_end_time);
    if(EditTxtVal!=null){
      Log.d(TAG, EditTxtVal);
      mEditTitle.setText(EditTxtVal);
    }
    if(EditStartVal!=null){
      mStartBtn.setText(EditStartVal);
    }
    if(EditEndVal!=null){
      mEndBtn.setText(EditEndVal);
    }
    if(EditDateVal!=null){
      mDateBtn.setText(EditDateVal);
    }





    view.findViewById(R.id.btn_save).setOnClickListener(this::saveEntry);
    view.findViewById(R.id.btn_entry_date).setOnClickListener(this::pickUserDate);
    view.findViewById(R.id.btn_start_time).setOnClickListener(this::pickUserStartTime);
    view.findViewById(R.id.btn_end_time).setOnClickListener(this::pickUserEndTime);

    return view;
  }
  @Override
  public void onSaveInstanceState(@NonNull Bundle outState){
    super.onSaveInstanceState(outState);
    EditTxtVal= mEditTitle.getText().toString();
    Log.d(TAG, "onSaveInstanceState: ");
    EditStartVal=mStartBtn.getText().toString();
    EditEndVal=mEndBtn.getText().toString();
    EditDateVal=mDateBtn.getText().toString();
    outState.putString("Title",EditTxtVal);
    outState.putString("StartKey",EditStartVal);
    outState.putString("EndKey",EditEndVal);
    outState.putString("DateKey",EditDateVal);

  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mEntryDetailsViewModel.getEntryLiveData().observe(requireActivity(),
            entry -> {
              this.mEntry = entry;
              if (entry != null) updateUI();

            });
  }


  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_entry_detail, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.delete) {
      Log.d(TAG, "Delete button clicked");

      new AlertDialog.Builder(requireActivity())
              .setTitle("Delete Entry")
              .setMessage("This entry will be deleted. Proceed?")
              .setIcon(android.R.drawable.ic_menu_delete)
              .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                mEntryDetailsViewModel.deleteEntry(mEntry);
                requireActivity().onBackPressed();
              })
              .setNegativeButton(android.R.string.no, null).show();

    }

    else if (item.getItemId() == R.id.share) {
      Log.d(TAG, "Share button clicked");

      Intent sendIntent = new Intent();
      sendIntent.setAction(Intent.ACTION_SEND);
      String text = "Look what I have been up to: " + mEntry.getTitle() + " on " + mEntry.getDate() + ", " + mEntry.getStartTime() + " to " + mEntry.getEndTime();
      sendIntent.putExtra(Intent.EXTRA_TEXT, text);
      sendIntent.setType("text/plain");
      Intent.createChooser(sendIntent,"Share via");
      startActivity(sendIntent);
    }

    return super.onOptionsItemSelected(item);
  }

  /**
   * To update EditText and Button elements with new text
   */
  @SuppressLint("SetTextI18n")
  private void updateUI() {

    if(EditTxtVal!=null){
      mEditTitle.setText(EditTxtVal);
    }
    else{
      mEditTitle.setText(mEntry.getTitle());
    }

    if (!mEntry.getDate().isEmpty()) {
      mDateBtn.setText(mEntry.getDate());
    }
    else if(EditDateVal!=null){
      mDateBtn.setText(EditDateVal);
    }
    else{
      mDateBtn.setText("Date");
    }



    if (!mEntry.getStartTime().isEmpty()) {
      mStartBtn.setText(mEntry.getStartTime());
    }
    else if(EditStartVal!=null){
      mStartBtn.setText(EditStartVal);
    }
    else {
      mStartBtn.setText("Start Time");
    }

    if (!mEntry.getEndTime().isEmpty()) {
      mEndBtn.setText(mEntry.getEndTime());
    }
    else  if(EditEndVal!=null){
      mEndBtn.setText(EditEndVal);
    }
    else {
      mEndBtn.setText("End Time");
    }

  }

  /**
   * Implements navigation action for picking user date using DatePicker
   * @param v button view
   */
  private void pickUserDate(View v) {
    Navigation.findNavController(v).navigate(R.id.datePickerAction);
  }

  /**
   * Implements navigation action for picking user start time using TimePicker
   * @param v button view
   */
  private void pickUserStartTime(View v) {
    EntryDetailsFragmentDirections.TimePickerAction action = EntryDetailsFragmentDirections.timePickerAction();
    action.setIsStart(true);
    Navigation.findNavController(v).navigate(action);
  }

  /**
   * Implements navigation action for picking user end time using TimePicker
   * @param v button view
   */
  private void pickUserEndTime(View v) {
    EntryDetailsFragmentDirections.TimePickerAction action = EntryDetailsFragmentDirections.timePickerAction();
    action.setIsStart(false);
    Navigation.findNavController(v).navigate(action);
  }

  /**
   * Saves the entry in the Room database
   * @param v button view
   */
  private void saveEntry(View v) {
    Log.d(TAG, "Save button clicked");

    mEntry.setTitle(mEditTitle.getText().toString());
    mEntry.setDate(mDateBtn.getText().toString());
    mEntry.setStartTime(mStartBtn.getText().toString());
    mEntry.setEndTime(mEndBtn.getText().toString());
   String start1=mStartBtn.getText().toString().substring(0, 2);
   String end1=mEndBtn.getText().toString().substring(0,2);
    String start2=mStartBtn.getText().toString().substring(3, 5);
    String end2=mEndBtn.getText().toString().substring(3,5);
    Log.d(TAG,start1);
    Log.d(TAG,end1);
    Log.d(TAG,start2);
    Log.d(TAG,end2);
   if(mEditTitle.getText().toString().isEmpty()|| mDateBtn.getText().toString()=="Date" || mStartBtn.getText().toString()=="Start Time" || mEndBtn.getText().toString()=="End Time"){
     Toast.makeText(this.getContext(), "Select all fields", Toast.LENGTH_SHORT).show();
   }

   else if(Integer.parseInt(start1)>Integer.parseInt(end1)){
     Toast.makeText(this.getContext(), "Input correct time", Toast.LENGTH_SHORT).show();
   }
   else if(Integer.parseInt(start1)==Integer.parseInt(end1) && Integer.parseInt(start2)>Integer.parseInt(end2)){
        Toast.makeText(this.getContext(), "Input correct time", Toast.LENGTH_SHORT).show();
    }
   else{
     Log.d(TAG, "saved");
     Log.d(TAG,mStartBtn.getText().toString());
     Log.d(TAG,mEndBtn.getText().toString());

     mEntryDetailsViewModel.saveEntry(mEntry);
     requireActivity().onBackPressed();
   }


  }

}