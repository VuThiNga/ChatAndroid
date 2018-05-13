package com.t3h.chatandroid;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.t3h.chatandroid.databinding.ActivityMainBinding;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private Socket socket;
    private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initChatSoket();
        binding.btnSend.setOnClickListener(this);

    }

    private void initChatSoket() {
        try {
                socket = IO.socket("http://192.168.137.2:9092");
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(TAG, "EVENT CONNECT: " + args);
                }
            });

            socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(TAG, "EVENT DISCONNECT: " + args);
                }
            });
            socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(TAG, "EVENT_CONNECT_ERROR: " + args);
                }
            });
            socket.on(Socket.EVENT_MESSAGE, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d(TAG, "EVENT_MESSAGE: " + args);
                }
            });
            socket.on("rep", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    //Log.d(TAG, "rep: " + args[0]);
                    String jsonString = args[0].toString();
                    final ChatObject chat = new Gson().fromJson(jsonString, ChatObject.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.edtContent.setText(chat.getUserName() + chat.getMessage() + format.format(chat.getCreatedDate()));
                        }
                    });
                }
            });
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (socket == null) {
            Toast.makeText(this, "socket null", Toast.LENGTH_LONG).show();
            return;
        }

        socket.emit("events", new Gson().toJson(new ChatObject("uet.nguyenduc@gmail.com",
                binding.edtContent.getText().toString(), new Date())));
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}
