package tuomolaukkanen.example.com.finalproject.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import tuomolaukkanen.example.com.finalproject.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText mEditTextStart;
    private EditText mEditTextEnd;
    private EditText mEditTextKeyword;
    private Button mButtonSearch;
    private DatePickerDialog mDatePickerDialog;
    private DatePickerDialog.OnDateSetListener mOnDateSetListenerStart;
    private DatePickerDialog.OnDateSetListener mOnDateSetListenerEnd;
    private int mCurrentYear;
    private int mCurrentMonth;
    private int mCurrentDayOfMonth;
    private String mStartDate;
    private String mEndDate;
    private String mKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setMemberVariables();
        setListeners();
        getCurrentDate();
    }

    private void setMemberVariables() {
        mEditTextStart = findViewById(R.id.mainEditText_StartDate);
        mEditTextEnd = findViewById(R.id.mainEditText_EndDate);
        mEditTextKeyword = findViewById(R.id.mainEditText_Keyword);
        mButtonSearch = findViewById(R.id.mainButton_Search);
    }

    private void setListeners() {
        mOnDateSetListenerStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                Log.d(TAG, "OnDateSetListenerStart: " + year + "-" + month + "-" + dayOfMonth);
                mStartDate = year + "-" + month + "-" + dayOfMonth;
                mEditTextStart.setText(dayOfMonth + "." + month + "." + year);
                mDatePickerDialog.dismiss();
            }
        };
        mOnDateSetListenerEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                Log.d(TAG, "OnDateSetListenerEnd: " + year + "-" + month + "-" + dayOfMonth);
                mEndDate = year + "-" + month + "-" + dayOfMonth;
                mEditTextEnd.setText(dayOfMonth + "." + month + "." + year);
                mDatePickerDialog.dismiss();
            }
        };
        mEditTextStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerDialog = new DatePickerDialog(MainActivity.this,
                        mOnDateSetListenerStart, mCurrentYear, mCurrentMonth, mCurrentDayOfMonth);
                mDatePickerDialog.show();
            }
        });
        mEditTextEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerDialog = new DatePickerDialog(MainActivity.this,
                        mOnDateSetListenerEnd, mCurrentYear, mCurrentMonth, mCurrentDayOfMonth);
                mDatePickerDialog.show();
            }
        });
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKeyword = mEditTextKeyword.getText().toString();
                if (!mKeyword.isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("startDate", mStartDate);
                    bundle.putString("endDate", mEndDate);
                    bundle.putString("keyword", mKeyword);
                    Intent intent = new Intent(MainActivity.this, EventListActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Anna jokin hakusana!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        mCurrentYear = calendar.get(Calendar.YEAR);
        mCurrentMonth = calendar.get(Calendar.MONTH);
        mCurrentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    }

    // OPTIONS MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItem_ImplementedFeatures:
                Intent intent = new Intent(MainActivity.this, FeaturesActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}