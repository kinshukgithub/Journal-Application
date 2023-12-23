package androidsamples.java.journalapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JournalEntryListAdapter extends RecyclerView.Adapter<JournalEntryListAdapter.EntryViewHolder> {
    private final LayoutInflater mInflater;
    private List<JournalEntry> mEntries;
    public JournalEntryListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                              int viewType) {
        View itemView = mInflater.inflate(R.layout.fragment_entry,
                parent,
                false);
        return new EntryViewHolder(itemView);
    }
   class EntryViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTxtTitle;
        private final TextView mTxtDate;
        private final TextView mTxtStart;
        private final TextView mTxtEnd;
        private JournalEntry mEntry;

        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtTitle = itemView.findViewById(R.id.txt_item_title);
            mTxtDate = itemView.findViewById(R.id.txt_item_date);
            mTxtStart = itemView.findViewById(R.id.txt_item_start_time);
            mTxtEnd = itemView.findViewById(R.id.txt_item_end_time);
            itemView.setOnClickListener(this::launchJournalEntryFragment);
        }

        private void launchJournalEntryFragment(View v) {
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

        void bind(JournalEntry entry) {
            mEntry = entry;
            this.mTxtTitle.setText(mEntry.getTitle());
            this.mTxtDate.setText(mEntry.getDate());
            this.mTxtStart.setText(mEntry.getStartTime());
            this.mTxtEnd.setText(mEntry.getEndTime());
        }
    }
    @Override
    public int getItemCount() {
        // Return the number of items in your dataset
        return mEntries != null ? mEntries.size() : 0;
    }
    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder,
                                 int position) {
        if (mEntries != null) {
            JournalEntry current = mEntries.get(position);
            holder.bind(current);
//            holder.mTxtTitle.setText(current.getTitle());
//            holder.mTxtStart.setText(current.getStartTime());
//            holder.mTxtEnd.setText(current.getEndTime());
//            holder.mTxtDate.setText(current.getDate());
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setEntries(List<JournalEntry> entries) {
        mEntries = entries;
        notifyDataSetChanged();
    }
}
