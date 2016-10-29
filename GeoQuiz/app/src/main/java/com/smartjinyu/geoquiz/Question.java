package com.smartjinyu.geoquiz;

import android.util.Log;

/**
 * Created by smart on 2016/10/26.
 */

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    public boolean isCheated() {
        return mIsCheated;
    }

    public void setCheated(boolean cheated) {
        mIsCheated = cheated;
    }

    private boolean mIsCheated;

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public Question(int TextResId, boolean AnswerTrue,boolean isCheated){
        mTextResId = TextResId;
        mAnswerTrue = AnswerTrue;
        mIsCheated = isCheated;
        Log.d("Test","QuestionCreated");
    }

}
