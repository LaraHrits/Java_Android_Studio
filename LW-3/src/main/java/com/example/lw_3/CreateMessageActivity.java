package com.example.lw_3;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class CreateMessageActivity extends AppCompatActivity {

    EditText messageView;
    String messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_create_message);

    }

    public void onSendMessage(View view) {
        messageView = (EditText) findViewById(R.id.message);
        messageText = messageView.getText().toString();

        Intent intent = new Intent(this, ReceiveMessageActivity.class);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, messageText);

        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Intent.EXTRA_TEXT, messageText);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "CreateMessageActivity: onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "CreateMessageActivity: onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "CreateMessageActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "CreateMessageActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "CreateMessageActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "CreateMessageActivity: onDestroy()");
    }

}
