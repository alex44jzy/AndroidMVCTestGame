package com.ao.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private Button mTrueButton;
    private Button mFalseButton;//添加成员变量
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private TrueFalse[] mQuestionBank = new TrueFalse[]{
    	new TrueFalse(R.string.question_oceans, true),
    	new TrueFalse(R.string.question_mideast, false),
    	new TrueFalse(R.string.question_africa, false),
    	new TrueFalse(R.string.question_americas, true),
    	new TrueFalse(R.string.question_asia, true),
    };
    private int mCurrentIndex = 0;
    private boolean mIsCheater;
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	if (data == null){
    		return;
    	}
    	mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }
    //先定义了一个update的方法来进行问题写入，每一次update只改变所引用的指针调用下一个问题
	private void updateQuestion(){
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(question);
	}
	//验证答案正确的一种方法 checkanswer
	private void checkAnswer(boolean userPressedTrue){
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		int messageResId = 0;
		if (mIsCheater){
			messageResId = R.string.judgment_toast;
		}else{
			if (userPressedTrue == answerIsTrue){
			messageResId = R.string.correct_toast;
			}else{
			messageResId = R.string.incorrect_toast;
			}
		}
		Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
	}
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TextView的设置
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        
        //回答true按钮设置
        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkAnswer(true);				
			}
		});
        
        //回答false按钮设置
		mFalseButton = (Button)findViewById(R.id.false_button);
		mFalseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});
		
		//next按钮设置
		mNextButton = (Button)findViewById(R.id.next_button);
		mNextButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;//求余数反复
				mIsCheater = false;
				updateQuestion();
			}
		});
		
		mCheatButton = (Button)findViewById(R.id.cheat_button);
		mCheatButton.setOnClickListener(new View.OnClickListener() {
		//启动CheatActivity
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, CheatActivity.class);
				boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
				i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
				startActivityForResult(i,0);
			}
		});
		updateQuestion();
    }
}
