package vn.note.samsung;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.text.TextWatcher;
import android.text.Editable;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;

public class MainActivity extends Activity {
    private static final String PREFS = "simple_note";
    private static final String KEY_NOTE = "content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setStatusBarColor(Color.WHITE);
        window.setNavigationBarColor(Color.WHITE);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(22), dp(18), dp(22), dp(12));
        root.setBackgroundColor(Color.WHITE);

        TextView heading = new TextView(this);
        heading.setText("Ghi chú");
        heading.setTextSize(25);
        heading.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        heading.setTextColor(Color.rgb(32, 32, 32));
        root.addView(heading, new LinearLayout.LayoutParams(-1, -2));

        TextView hint = new TextView(this);
        hint.setText("Nội dung được tự động lưu");
        hint.setTextSize(12);
        hint.setTextColor(Color.rgb(140, 140, 140));
        LinearLayout.LayoutParams hintParams = new LinearLayout.LayoutParams(-1, -2);
        hintParams.topMargin = dp(5);
        hintParams.bottomMargin = dp(18);
        root.addView(hint, hintParams);

        EditText editor = new EditText(this);
        editor.setTextSize(18);
        editor.setTextColor(Color.rgb(35, 35, 35));
        editor.setHint("Bắt đầu soạn ghi chú...");
        editor.setHintTextColor(Color.rgb(175, 175, 175));
        editor.setGravity(android.view.Gravity.TOP | android.view.Gravity.START);
        editor.setBackgroundColor(Color.TRANSPARENT);
        editor.setPadding(0, dp(8), 0, dp(8));
        editor.setSingleLine(false);
        editor.setHorizontallyScrolling(false);
        editor.setInputType(android.text.InputType.TYPE_CLASS_TEXT
                | android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE
                | android.text.InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        editor.setVerticalScrollBarEnabled(true);

        LinearLayout.LayoutParams editorParams = new LinearLayout.LayoutParams(-1, 0, 1f);
        root.addView(editor, editorParams);
        setContentView(root);

        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        editor.setText(prefs.getString(KEY_NOTE, ""));
        editor.setSelection(editor.length());

        editor.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                prefs.edit().putString(KEY_NOTE, s.toString()).apply();
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private int dp(float value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
