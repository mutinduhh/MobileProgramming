package com.example.speechapplication;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button convertButton;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        convertButton = findViewById(R.id.convertButton);

        textToSpeech = new TextToSpeech(
                this,
                status -> {
                    if (status == TextToSpeech.SUCCESS) {
                        int result = textToSpeech.setLanguage(Locale.UK);

                        if (result == TextToSpeech.LANG_MISSING_DATA ||
                                result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Toast.makeText(this,
                                    "UK English is not supported",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        convertButton.setOnClickListener(v -> convert());
    }

    private void convert() {
        String text = editText.getText().toString().trim();

        if (text.isEmpty()) {
            Toast.makeText(this,
                    "Please enter some text",
                    Toast.LENGTH_SHORT).show();
        } else {
            textToSpeech.speak(
                    text,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "TTS_ID"
            );
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}