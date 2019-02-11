package org.zero.brotli;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import org.zero.brotlilib.dec.BrotliInputStream;
import org.zero.brotlilib.enc.Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.sample_text);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    byte[] compress = Encoder.compress("我是中国人".getBytes());
                    writeToTxt(compress);
                    tv.setText(decode());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String decode() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "test");
        BrotliInputStream brotliInputStream = null;
        byte[] result = new byte[20480];
        try {
            brotliInputStream = new BrotliInputStream(new FileInputStream(file));
            brotliInputStream.read(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (brotliInputStream != null) {
                try {
                    brotliInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new String(result).trim();
    }

    private void writeToTxt(byte[] compress) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "test");
        FileOutputStream fileOutputStream = null;
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(compress);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
