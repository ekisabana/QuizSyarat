package com.example.yds.quizsyarat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mSubmit, mHelp;
    private TextView mSoal, mClue, mJawab;

    private String mAnswer;
    private int operation = 0;

    private Question[] mQuestionLibrary = new Question[]{
            new Question(R.string.soal, "Kancing")
    };

    private Clues[] mCluesCorrect = new Clues[]{
            new Clues(R.string.clues)
    };

    private int mQuestionNumber = 0;
    private int mCluesNumber = 0;

    private void checkAnswer(String answerTrue) {
        String answerIsTrue = mQuestionLibrary[mQuestionNumber].isCorrectAnswer();
        if (answerTrue.equalsIgnoreCase(answerIsTrue)) {
            int clue = mCluesCorrect[mCluesNumber].getClues();
            mClue.setText(clue);
            Toast.makeText(MainActivity.this, "BENAR", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "SALAH", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSoal = (TextView) findViewById(R.id.soal);
        mClue = (TextView) findViewById(R.id.hasil);
        mSubmit = (Button) findViewById(R.id.submit);
        mHelp = (Button) findViewById(R.id.help);
        mJawab = (EditText) findViewById(R.id.jawab);

        int question = mQuestionLibrary[mQuestionNumber].getQuestion();
        mSoal.setText(question);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswer = mJawab.getText().toString();
                checkAnswer(mAnswer);
            }
        });

        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = MainActivity.this;
                Class destinationActivity = Help.class;
                Intent intent = new Intent(context, destinationActivity);
                startActivity(intent);
            }
        });

        Intent bantuan = getIntent();
        if (bantuan.hasExtra("helps")) {
            int textBantuan = bantuan.getIntExtra("helps", 0);
            mClue.setText(textBantuan);
        }

    }

    public void clear(View V) {
        mJawab.setText("");
        operation = 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                onAction("share");
                return true;
            case R.id.quit:
                onAction("exit");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //notifikasi manu aplikasi
    public void onAction(final String pilih) {
        AlertDialog.Builder notifikasi = new AlertDialog.Builder(this);
        notifikasi.setMessage("Apakah Anda yakin")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (pilih.equals("share")) {
                            mJawab = (EditText) findViewById(R.id.jawab);
                            mAnswer = mJawab.getText().toString();
                            Intent share = new Intent(Intent.ACTION_SEND);
                            share.setAction(Intent.ACTION_SEND);
                            share.setType("text/plain");
                            share.putExtra(Intent.EXTRA_TEXT, mAnswer);
                            startActivity(Intent.createChooser(share, "Pilih Aplikasi"));
                        } else if (pilih.equals("exit")) {
                            finish();
                        }
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog al = notifikasi.create();
        if (pilih.equals("exit")) {
            al.setTitle("Konfirmasi Exit");
        } else if (pilih.equals("share")) {
            al.setTitle("Konfirmasi Share");
        }
        al.show();
    }
}
