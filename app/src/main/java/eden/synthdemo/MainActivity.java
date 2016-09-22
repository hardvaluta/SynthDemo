package eden.synthdemo;

import android.content.DialogInterface;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, OnClickListener{
    EditText input;
    Button button_clear, button_speak;
    TextToSpeech tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = (EditText) findViewById(R.id.input);
        button_clear = (Button) findViewById(R.id.button_clear);
        button_speak = (Button) findViewById(R.id.button_speak);
        button_clear.setOnClickListener(this);
        button_speak.setOnClickListener(this);

        tts = new TextToSpeech(this, this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_clear:
                input.setText("");
                break;
            case R.id.button_speak:
                String text = input.getText().toString();
                if (text.isEmpty()) {// isEmpty api ver = 9
                    Toast.makeText(this, "Field is empty", Toast.LENGTH_SHORT).show();
                } else {
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
                break;
        }
    }

    @Override
    public void onInit(int status){
        if (status == TextToSpeech.SUCCESS){
            Locale lang   = tts.getLanguage();
            int result = tts.setLanguage(lang);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "inget språkstöd");
            }else {
                //nothing
            }
        }else{
            Log.e("TTS", "uppstart kaputt");
        }
    }
}
