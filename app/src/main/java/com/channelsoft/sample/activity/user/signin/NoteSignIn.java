package com.channelsoft.sample.activity.user.signin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.channelsoft.sample.R;
import com.channelsoft.sample.activity.BaseActivity;
import com.channelsoft.sample.model.user.SignStatus;
import com.channelsoft.sample.util.StatusBarColor;

public class NoteSignIn extends BaseActivity implements View.OnClickListener {
    private EditText mEditNoteTel;
    private EditText mEditNote;
    private Button mBtnGetNote;
    private Button mBtnNoteSignIn;
    private ImageView mImgNoteClose;
    private TextView mTextPasswordSignIn;
    private static final int MAX_PHONE_LENGTH = 11;
    private String mUserID;
    private boolean mIsSignIn;
    private boolean mIsExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_sign_in);
        StatusBarColor.transStatusBarAlpha(getWindow());
        initView();
        onClickListener();
    }

    /**
     * 监听
     */
    private void onClickListener() {
        mBtnGetNote.setOnClickListener(this);
        mBtnNoteSignIn.setOnClickListener(this);
        mImgNoteClose.setOnClickListener(this);
        mTextPasswordSignIn.setOnClickListener(this);
        mEditNoteTel.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("date", "onTextChanged 被执行---->s=" + s + "----start=" + start
                        + "----before=" + before + "----count" + count);
                temp = s;
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("date", "beforeTextChanged 被执行----> s=" + s + "----start=" + start
                        + "----after=" + after + "----count" + count);
            }

            public void afterTextChanged(Editable s) {
                Log.d("date", "afterTextChanged 被执行---->" + s);
                selectionStart = mEditNoteTel.getSelectionStart();
                selectionEnd = mEditNoteTel.getSelectionEnd();
                if (temp.length() > MAX_PHONE_LENGTH) {
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    mEditNoteTel.setText(s);
                    mEditNoteTel.setSelection(tempSelection);
                }
                Log.d("date", "   用户的工作：" + s.toString());
            }
        });
        mEditNote.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("date", "onTextChanged 被执行---->s=" + s + "----start=" + start
                        + "----before=" + before + "----count" + count);
                temp = s;
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("date", "beforeTextChanged 被执行----> s=" + s + "----start=" + start
                        + "----after=" + after + "----count" + count);
            }

            public void afterTextChanged(Editable s) {
                Log.d("date", "afterTextChanged 被执行---->" + s);
                selectionStart = mEditNote.getSelectionStart();
                selectionEnd = mEditNote.getSelectionEnd();
                if (temp.length() > MAX_PHONE_LENGTH) {
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    mEditNote.setText(s);
                    mEditNote.setSelection(tempSelection);
                }
                Log.d("date", "   用户的工作：" + s.toString());
            }
        });
    }

    /**
     * 初始化布居
     */
    private void initView() {
        mEditNote = (EditText) findViewById(R.id.edit_note_sign_in_note);
        mEditNote.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        mEditNoteTel = (EditText) findViewById(R.id.edit_note_sign_in_tel);
        mEditNoteTel.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        mBtnGetNote = (Button) findViewById(R.id.button_note_sign_in_get_note);
        mBtnNoteSignIn = (Button) findViewById(R.id.button_note_sign_in);
        mImgNoteClose = (ImageView) findViewById(R.id.img_note_close);
        mTextPasswordSignIn = (TextView) findViewById(R.id.text_note_sign_in_turn_password);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_note_sign_in:
//              判断验证码是否输入正确
                if(true){
//                    判断号码之前是否登录过
                    if(mIsExist){
//                        找到那条记录并且将isSignIn的状态设为true
                    }else{
//                        添加用户并且经isSignIn的状态设为true
                        addUserInfo(mEditNoteTel.getText().toString());
                    }
                }else {
                    Toast.makeText(NoteSignIn.this, "验证码错误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_note_sign_in_get_note:

                break;
            case R.id.img_note_close:

                break;
            case R.id.text_note_sign_in_turn_password:

                break;
        }
    }

    /**
     * 添加用户信息
     * @param userID 用户的电话
     */
    private void addUserInfo(String userID) {
        SignStatus signStatus = new SignStatus();
        signStatus.setIsSignIn(true);
        signStatus.setUserID(userID);
        signStatus.setUserPassword(null);
        signStatus.save(getApplicationContext());
    }
}
