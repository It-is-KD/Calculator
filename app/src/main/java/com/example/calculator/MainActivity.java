package com.example.calculator;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable; //using a dependency file for these imports (in gradle build)

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private GestureDetectorCompat gestureDetectorCompat = null;
    MaterialButton btnadd,btnsub,btnmul,btndiv,btnequals;
    MaterialButton zero,one,two,three,four,five,six,seven,eight,nine,dot,ac,c,sign;
    TextView solTv,valueTv;
    String calc;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        solTv=(TextView) findViewById(R.id.sol);
        valueTv=(TextView) findViewById(R.id.textfield);

        assignId(btnadd,R.id.button_add);
        assignId(btnsub,R.id.button_sub);
        assignId(btnmul,R.id.button_mul);
        assignId(btndiv,R.id.button_div);
        assignId(btnequals,R.id.button_equals);
        assignId(zero,R.id.btn0);
        assignId(one,R.id.btn1);
        assignId(two,R.id.btn2);
        assignId(three,R.id.btn3);
        assignId(four,R.id.btn4);
        assignId(five,R.id.btn5);
        assignId(six,R.id.btn6);
        assignId(seven,R.id.btn7);
        assignId(eight,R.id.btn8);
        assignId(nine,R.id.btn9);
        assignId(dot,R.id.btnDot);
        assignId(ac,R.id.btnAc);
        assignId(c,R.id.btnBack);
//        assignId(sign,R.id.btnSign); //going to use this for a button not related to swipe

        DetectSwipeGestureListener gestureListener  = new DetectSwipeGestureListener();
        gestureListener.setActivity(this);
        gestureDetectorCompat = new GestureDetectorCompat(this, gestureListener);
    }

    void assignId(MaterialButton btn, int id)
    {
        btn=findViewById(id);
        btn.setOnClickListener((View.OnClickListener) this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        gestureDetectorCompat.onTouchEvent(event);
        return true;
    }

    @Override
    public void onClick(View view)
    {
        MaterialButton button =(MaterialButton) view;
        String btnText = button.getText().toString();
        calc = solTv.getText().toString();

        if(btnText.equals("÷")) btnText = "/";

        if(btnText.equals("×")) btnText = "*";

        if(btnText.equals("AC"))
        {
            solTv.setText("");
            valueTv.setText("0");
            return;
        }

        if(btnText.equals("="))
        {
            solTv.setText(valueTv.getText());
            return;
        }

        if (btnText.equals("⌫")) //used for backspace button
        {
            if(calc.length()>1) calc = calc.substring(0, calc.length() - 1);
            else
            {
                solTv.setText("");
                valueTv.setText("0");
                return;
            }
        }

        else calc = calc + btnText;

        solTv.setText(calc);
        String finalResult = getResult(calc);

        if(!finalResult.equals("ErroR"))
        {
            valueTv.setText(finalResult);
        }

    }

    String getResult(String data) //using JS to easily convert and evaluate String
    {
        try
        {
            Context context  = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();

            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();

            if(finalResult.endsWith(".0")) finalResult = finalResult.replace(".0","");
            return finalResult;
        }
        catch (Exception e)
        {
            return "ErroR";
        }
    }

    public void backspace(String data)
    {
        valueTv.setText(data); //using this for debugging
        if(calc.length()>1) calc = calc.substring(0, calc.length() - 1);
        else
        {
            solTv.setText("");
            valueTv.setText("0");
        }
    }
}