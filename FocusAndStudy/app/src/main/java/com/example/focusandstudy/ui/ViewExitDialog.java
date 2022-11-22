package com.example.focusandstudy.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.focusandstudy.R;
import com.example.focusandstudy.controller.FinishedSessionActivity;
import com.example.focusandstudy.controller.MainActivity;
import com.example.focusandstudy.controller.PomodoroActivity;
import com.example.focusandstudy.model.User;
import com.example.focusandstudy.model.database.DBHandler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ViewExitDialog extends Dialog {
    private Button m_exitsessiondialog_button_cancel;
    private Button m_exitsessiondialog_button_exit;
    private Activity mActivity;
    private Context mContext;

    public ViewExitDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public ViewExitDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ViewExitDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.exit_session_dialog);
        m_exitsessiondialog_button_cancel = (Button) findViewById(R.id.exitsessiondialog_button_cancel);
        m_exitsessiondialog_button_exit = (Button) findViewById(R.id.exitsessiondialog_button_exit);
    }

    @Override
    protected void onStart() {
        super.onStart();
        m_exitsessiondialog_button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        m_exitsessiondialog_button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent mainActivity = new Intent(mContext, MainActivity.class);
                mContext.startActivity(mainActivity);
                System.out.println("pomodoro exit");
                ((Activity)mContext).finish();
            }
        });
    }
}
