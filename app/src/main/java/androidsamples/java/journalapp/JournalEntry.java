package androidsamples.java.journalapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "journal_table")
public class JournalEntry {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private UUID mUid;
    @ColumnInfo(name = "title")
    private String mTitle;

    private String mDate;
    @ColumnInfo(name = "duration")
    private String mStartTime;
    private String mEndTime;
    public JournalEntry(@NonNull String title,String date, String startTime, String endTime) {
        mUid = UUID.randomUUID();
        mDate = date;
        mTitle = title;
        mStartTime = startTime;
        mEndTime = endTime;
    }
    public UUID getUid() {
        return mUid;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() { return mDate; }

    public String getStartTime() {
        return mStartTime;
    }
    public String getEndTime() {
        return mEndTime;
    }
    public void setUid(UUID uid) {
        mUid = uid;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDate(String Date) { mDate =Date ; }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

    public void setEndTime(String endTime) {
        mEndTime = endTime;
    }
}
