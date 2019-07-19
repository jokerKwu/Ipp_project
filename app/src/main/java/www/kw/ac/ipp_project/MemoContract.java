package www.kw.ac.ipp_project;
import android.provider.BaseColumns;
public class MemoContract {
    private MemoContract(){}
    public static class MemoEntry implements BaseColumns{
        public static final String TABLE_NAME="memo";
        public static final String COLUMN_NAME_TITLE="title";
        public static final String COLUMN_NAME_CONTENT="content";
        public static final String COLUMN_NAME_DATE="date";
    }
}