package com.ao.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {
	//绑定answer的值到extra中
	public static final String EXTRA_ANSWER_IS_TRUE = 
			"com.ao.helloworld.answer_is_true";
	public static final String EXTRA_ANSWER_SHOWN =
			"com.ao.helloworld.answer_shown";
	private boolean mAnswerIsTrue;//设置boolean的值来存储传来的正确答案
	private TextView mAnswerTextView;
	
	private Button mShowAnswer;
	private void setAnswerShownResult(boolean isAnswerShown) {
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);
	}
	@Override
	//Intent的值在cheat页面显示
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
		mAnswerTextView = (TextView)findViewById(R.id.answerTextView);
		mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
		setAnswerShownResult(false);
		mShowAnswer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mAnswerIsTrue) {
					mAnswerTextView.setText(R.string.true_button);
				}else{
					mAnswerTextView.setText(R.string.false_button);
				}
				setAnswerShownResult(true);
			}
		});
	}
}
