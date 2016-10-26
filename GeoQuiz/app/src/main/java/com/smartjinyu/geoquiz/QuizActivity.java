package com.smartjinyu.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private ImageButton mNextImgButton;
    private ImageButton mPrevImgButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_africa,true),
            new Question(R.string.question_americans,false),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_asia,true)
    };//create an array of question of objects
    private int mCurrenIndex = 0;

    private void updateQuestion() {
        int question = mQuestionBank[mCurrenIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrenIndex].isAnswerTrue();
        int messageResId = 0;
        if(userPressedTrue == answerIsTrue){
            messageResId = R.string.correct_toast;
        }else{
            messageResId = R.string.false_button;
        }
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrenIndex=(mCurrenIndex+1)%mQuestionBank.length;
                updateQuestion();
            }
        });
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(QuizActivity.this,R.string.correct_toast,Toast.LENGTH_SHORT).show();
                // must pass QuizActivity.this, a simple this refers to View.OnClickListener
                checkAnswer(true);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(QuizActivity.this,R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
                checkAnswer(false);
            }
        });
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrenIndex = (mCurrenIndex+1)%mQuestionBank.length;
                updateQuestion();
            }
        });
        mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrenIndex == 0){
                    mCurrenIndex = 5;
                }
                mCurrenIndex = (mCurrenIndex-1)%mQuestionBank.length;
                updateQuestion();
            }
        });

        mPrevImgButton = (ImageButton) findViewById(R.id.prev_imgbutton);
        mPrevImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrenIndex == 0){
                    mCurrenIndex = 5;
                }
                mCurrenIndex = (mCurrenIndex-1)%mQuestionBank.length;
                updateQuestion();
            }
        });
        mNextImgButton = (ImageButton) findViewById(R.id.next_imgbutton);
        mNextImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrenIndex = (mCurrenIndex+1)%mQuestionBank.length;
                updateQuestion();
            }
        });

        updateQuestion();
    }
}
