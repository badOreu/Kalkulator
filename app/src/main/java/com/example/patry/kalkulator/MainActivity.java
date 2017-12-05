package com.example.patry.kalkulator;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText n1ET, n2ET, resET;
    Button add, sub, mul, div, pi, clr;
    ProgressBar pBar;
    LogicService logicService;
    boolean mBound = false;

    @SuppressLint("StaticFieldLeak")
    private class AsynkTask extends AsyncTask<Integer, Void, Double> {
        @Override
        protected Double doInBackground(Integer... doubles) {
            Random generator = new Random();
            int nk = 0;
            double x, y;
            double s;
            for (int i = 1; i <= doubles[0]; i++)
            {
                x = generator.nextDouble();
                y = generator.nextDouble();
                if (x*x + y*y <= 1)
                {
                    nk++;
                }
            }
            s = 4. * nk / doubles[0];
            return s;
        }
        @Override
        protected void onPreExecute() {
            pBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(Double aDouble) {
            Toast.makeText(MainActivity.this, "Obliczono PI !", Toast.LENGTH_SHORT).show();
            resET.setText(String.valueOf(aDouble));
            pBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.about: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.author_layout, null);
                builder.setView(v);
                final AlertDialog dialog = builder.create();
                final Button btn = v.findViewById(R.id.okBtn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
            default:
                return false;
        }
    }

    private ServiceConnection logicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            LogicService.LocalBinder binder = (LogicService.LocalBinder)
                    service;
            logicService = binder.getService();
            mBound = true;
            Toast.makeText(MainActivity.this, "Logic Service Connected", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            logicService = null;
            mBound = false;
            Toast.makeText(MainActivity.this, "Logic Service Disconnected", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if (!mBound) {
            this.bindService(new Intent(MainActivity.this, LogicService.class), logicConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            mBound = false;
            this.unbindService(logicConnection);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        n1ET = findViewById(R.id.n1EditText);
        n2ET = findViewById(R.id.n2EditText);
        resET = findViewById(R.id.resEditText);
        add = findViewById(R.id.addButton);
        sub = findViewById(R.id.subButton);
        mul = findViewById(R.id.mulButton);
        div = findViewById(R.id.divButton);
        pi = findViewById(R.id.piButton);
        clr = findViewById(R.id.clrButton);
        pBar = findViewById(R.id.pBar);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double n1 = 0.0;
                Double n2 = 0.0;
                try {
                    n1 = Double.parseDouble(n1ET.getText().toString());
                    n2 = Double.parseDouble(n2ET.getText().toString());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                if (mBound) {
                    double ret = logicService.add(n1,n2);
                    resET.setText(String.valueOf(ret));
                }
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double n1 = 0.0;
                Double n2 = 0.0;
                try {
                    n1 = Double.parseDouble(n1ET.getText().toString());
                    n2 = Double.parseDouble(n2ET.getText().toString());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                if (mBound) {
                    double ret = logicService.sub(n1,n2);
                    resET.setText(String.valueOf(ret));
                }
            }
        });
        mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double n1 = 0.0;
                Double n2 = 0.0;
                try {
                    n1 = Double.parseDouble(n1ET.getText().toString());
                    n2 = Double.parseDouble(n2ET.getText().toString());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                if (mBound) {
                    double ret = logicService.mul(n1,n2);
                    resET.setText(String.valueOf(ret));
                }
            }
        });
        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double n1 = 0.0;
                Double n2 = 0.0;
                try {
                    n1 = Double.parseDouble(n1ET.getText().toString());
                    n2 = Double.parseDouble(n2ET.getText().toString());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                if (n1!=0.0) {
                    double ret = logicService.div(n1,n2);
                    resET.setText(String.valueOf(ret));
                }
            }
        });
        clr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Double s1 = 0.0;
                n1ET.setText(String.valueOf(s1));
                n2ET.setText(String.valueOf(s1));
                resET.setText(String.valueOf(s1));
            }
        });
        pi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsynkTask().execute(Integer.parseInt(n1ET.getText().toString()));
            }
        });
        }
    }

