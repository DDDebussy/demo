package com.channelsoft.sample.activity.homepager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.channelsoft.sample.R;
import com.channelsoft.sample.activity.BaseActivity;
import com.channelsoft.sample.util.CommonUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;

public class PayActivity extends BaseActivity implements View.OnClickListener {
    TextView mTextView;
    MaterialEditText mEditTextPhoneNum;
    EditText mEditTextSecurityCode;
    Button mButtonGetSecurityCode;
    Button mButtonPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        mTextView = (TextView) findViewById(R.id.textview_price_pay);
        mEditTextPhoneNum = (MaterialEditText) findViewById(R.id.edittext_phone_num);
        mEditTextSecurityCode = (EditText) findViewById(R.id.edittext_security_code);
        mButtonGetSecurityCode = (Button) findViewById(R.id.button_get_security_code);
        mButtonPay = (Button) findViewById(R.id.button_pay);
        mButtonGetSecurityCode.setOnClickListener(this);
        mButtonPay.setOnClickListener(this);

        Intent intent = getIntent();
        String price = intent.getStringExtra("money");
        mTextView.setText("您共需支付" + price + "元");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_get_security_code:
                //获取验证码
                BmobSMS.requestSMSCode(this, mEditTextPhoneNum.getText().toString(), "获取手机验证码", new RequestSMSCodeListener() {
                    @Override
                    public void done(Integer smsId, BmobException ex) {
                        // TODO Auto-generated method stub
                        if (ex == null) {//验证码发送成功
                            Log.i("bmob", "短信id：" + smsId);//用于查询本次短信发送详情
                        }else {
                            CommonUtils.showToast("获取验证码失败");
                        }
                    }
                });
                break;
            //验证验证码
            case R.id.button_pay:
                BmobSMS.verifySmsCode(this,mEditTextPhoneNum.getText().toString(),
                        mEditTextSecurityCode.getText().toString(), new VerifySMSCodeListener() {
                    @Override
                    public void done(BmobException ex) {
                        // TODO Auto-generated method stub
                        if(ex==null){//短信验证码已验证成功
                            Log.i("bmob", "验证通过");
                            CommonUtils.showToast("验证通过");
                        }else{
                            Log.i("bmob", "验证失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
                            CommonUtils.showToast("验证失败");
                        }
                    }
                });
            default:
                break;
        }
    }
}
