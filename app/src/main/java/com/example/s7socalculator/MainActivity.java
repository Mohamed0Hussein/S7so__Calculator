package com.example.s7socalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText input;
    String n11="";
    String n22="";
    EditText result;
    char op = '.';
    boolean opPressed = false;
    MediaPlayer click,error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        click = MediaPlayer.create(this,R.raw.clicks8);
        error = MediaPlayer.create(this,R.raw.error);
        input = findViewById(R.id.inputText);
        result = findViewById(R.id.resultText);

    }
    public void squarePressed(View v){
        if(!opPressed){
            double n1=Double.parseDouble(n11);
            n1 = Math.pow(n1,2);
            String n111 = String.valueOf(n1);
            if(n111.length()<35) {
                input.setText(n111);
                n11=n111;
            }
            else
                input.setText(n11);
        }
        else if(opPressed) {
            if (n22.contains("0") || n22.contains("1") || n22.contains("2") || n22.contains("3") || n22.contains("4") || n22.contains("5") || n22.contains("6") || n22.contains("7") || n22.contains("8") || n22.contains("9")) {
                double n2 = Double.parseDouble(n22);
                n2 = Math.pow(n2, 2);
                String n222 = String.valueOf(n2);
                if(n11.length()+n222.length()+1<35) {
                    input.setText(n11 + op + n222);
                    n22= n222;
                }
                else
                    input.setText(n11 + op + n22);
            }
        }
    }
    public double execute(){
        if(n11.endsWith("E")){
            n11+="1";
        }
        if(n22.endsWith("E")){
            n22+="1";
        }
        if(n11.equals("-0"))
            n11 = "0";
        if(n22.equals("-0"))
            n22 = "0";
        if(n11.equals("."))
            n11="0.0";
        if(n22.equals("."))
            n22 = "0.0";
        double n1=Double.parseDouble(n11),n2=Double.parseDouble(n22),nResult = 0;
        switch (op)
        {
            case '+':
                nResult = n1+n2;
                result.setHint(String.valueOf(nResult));
                break;
            case '-':
                nResult = n1-n2;
                result.setHint(String.valueOf(nResult));
                break;
            case '*':
                nResult = n1*n2;
                result.setHint(String.valueOf(nResult));
                break;
            case '/':
                if(n2 != 0) {
                    nResult = n1/n2;
                    result.setHint(String.valueOf(nResult));
                }
                else{
                    Toast.makeText(getApplicationContext(), "Are You Crazy !!!!!", Toast.LENGTH_SHORT).show();
                    error.start();

                }
                break;

        }
        return nResult;
    }
    public void dotPressed(View v){
        if(!opPressed){
            if(!n11.contains(".")){
                n11 +=".";
                input.setText(n11);
            }
        }
        else
        {
            if(!n22.contains(".")) {
                n22 += ".";
                input.setText(n11+op+n22);
            }
        }
    }
    public void zeroPressed(View v){
        if(!opPressed) {
            if (n11.length() < 35) {
                if (n11.isEmpty()) {
                    n11 += '0';
                } else if (n11.equals("-")) {
                    n11 += "0";
                } else if (n11.contains(".")) {
                    n11 += "0";
                } else if (!n11.isEmpty() && Double.parseDouble(n11) > 0) {
                    n11 += 0;
                }
                input.setText(n11);

            }
        }
        else {
            if (n11.length() + n22.length() + 1 < 35) {
                if (n22.isEmpty()) {
                    n22 += '0';
                } else if (n22.equals("-")) {
                    n22 += "0";

                } else if (n22.contains(".")) {
                    n22 += "0";
                } else if (!n22.isEmpty() && Double.parseDouble(n22) > 0) {
                    n22 += 0;
                }
                input.setText(n11 + op + n22);
                if (!n22.equals("0") && op != '/') {
                    execute();
                }
            }
        }
    }
    public void addNegative(View v){
        click.start();
        if(!opPressed && n11.length()==0){
            n11+='-';
            input.setText(n11);
        }
        else if(opPressed && n22.length()==0) {
            n22 += '-';
            input.setText(n11+op+n22);
        }
    }

    public void numberPressed(View v){
        click.start();
        if(!opPressed){
            if(n11.length()<35) {
                if (v.getTag().toString().equals(".")) {
                    if (!n11.contains(".")) {
                        n11 += v.getTag().toString();
                        input.setText(n11);

                    }
                } else {
                    if (n11.equals("0"))
                        n11 = "";
                    else if (n11.equals("-0"))
                        n11 = "-";
                    n11 += v.getTag().toString();
                    input.setText(n11);
                }
            }
        }
        else {
            if (n11.length() + n22.length() + 1 < 35) {
                if (v.getTag().toString().equals(".")) {
                    if (!n22.contains(".")) {
                        n22 += v.getTag().toString();
                        input.setText(n11 + op + n22);
                    }
                } else {
                    if (n22.equals("0"))
                        n22 = "";
                    else if (n22.equals("-0"))
                        n22 = "-";
                    n22 += v.getTag().toString();
                    input.setText(n11 + op + n22);
                    execute();
                }
            }
        }
    }
    public void delete(View v){
        click.start();
        if(n22.length()!=0) {
            n22 = n22.substring(0,n22.length()-1);
            input.setText(n11+op+n22);
            if(n22.length()!= 0) {
                if (!n22.equals("-")) {
                    if(!n22.equals("0."))
                        execute();
                }
            }

        }
        else if(op != '.'){
            op = '.';
            opPressed = false;
            input.setText(n11);
            result.setHint(" ");

        }
        else if(n11.length()!=0){
            n11 = n11.substring(0,n11.length()-1);
            input.setText(n11);
            result.setHint("");
        }
        else {
            input.setText("");
            result.setHint("");
            n11 = "";
            opPressed = false;
            op = '.';
        }
    }

    public void operationPressed(View v){
        click.start();
        if(!(n11.length()+1>35)) {
            if (!opPressed && n11.length() > 0) {
                op = v.getTag().toString().charAt(0);
                opPressed = true;
                input.setText(n11 + op);
                result.setHint("");
            } else if ((n22.length() > 0 && n22.charAt(0) != '-') || (n22.length() > 1 && n22.charAt(0) == '-')) {
                double x = execute();
                op = v.getTag().toString().charAt(0);
                input.setText(String.valueOf(x) + op);
                result.setHint("");
                opPressed = true;
                n22 = "";
                n11 = String.valueOf(x);
            }
        }
    }

    public void clear(View v){
        click.start();

        n11 = "";
        n22 = "";
        opPressed=false;
        op = '.';
        input.setText("");
        result.setHint("");
    }
    public void equalPressed(View v){
        click.start();
        if(n22.equals("."))
            n22 = "0.0";
        if(n22.equals("-"))
            n22 = "0.0";
        if(op == '.' || n22.equals("")) {
            Toast.makeText(getApplicationContext(), "Are You Serious !!!!!", Toast.LENGTH_SHORT).show();
            error.start();
        }

        else{
            double x = execute();
            if(x ==-0.0){
                x= 0.0;
            }
            input.setText(String.valueOf(x));
            result.setHint("");
            opPressed=false;
            n22="";
            op = '.';
            n11 = String.valueOf(x);
        }

    }
}
