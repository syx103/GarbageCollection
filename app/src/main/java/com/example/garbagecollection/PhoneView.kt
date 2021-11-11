package com.example.garbagecollection

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import java.lang.Exception


//注册/登录页
//AppCompatEditText是EditText的子类，可以支持旧平台上的兼容功能
class PhoneView : AppCompatEditText, TextWatcher {

    //主构造器中不能含有任何代码，如果需要可以放在init块中
    //如果子类没有主构造函数，则全部使用super初始化基类，或者使用前面的另一个构造函数

    companion object {
        const val PHONE_INDEX_3 = 3
        const val PHONE_INDEX_4 = 4
        const val PHONE_INDEX_8 = 8
        const val PHONE_INDEX_9 = 9

    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        if (text == null || text.isEmpty()) {
            return
        }
        var sb = StringBuilder()
        for (i in text.indices) {
            if (i != PHONE_INDEX_3 && i != PHONE_INDEX_8 && text[i] == ' ') {
                continue
            } else {
                sb.append(text[i]);
                if ((sb.length == PHONE_INDEX_4 || sb.length == PHONE_INDEX_9) && sb[sb.length - 1] != ' ') {
                    sb.insert(sb.length - 1, ' ');
                }
            }
        }
        if (sb.toString() != text.toString()) {
            var index = start + 1;
            if (sb[start] == ' ') {
                if (lengthBefore == 0) {
                    index++;
                } else {
                    index--;
                }
            } else {
                if (lengthBefore == 1) {
                    index--;
                }
            }
            setText(sb.toString());
            setSelection(index);
        }
    }

    override fun afterTextChanged(s: Editable?) {

    }
}