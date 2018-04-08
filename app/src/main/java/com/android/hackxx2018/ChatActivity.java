package com.android.hackxx2018;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.concurrent.ExecutionException;

public class ChatActivity extends AppCompatActivity {
    private FirebaseListAdapter<ChatMessage> adapter;
    String firstLang = "";
    String secondLang = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        FloatingActionButton fab =
                (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);
                String textToBeTranslated = input.getText().toString();
                String languagePair = firstLang + "-" + secondLang; //English to French ("<source_language>-<target_language>")
                //Executing the translation function
                String translatedMessage = Translate(textToBeTranslated,languagePair);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference()
                        .push()
                        .setValue(new ChatMessage(translatedMessage,
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName())
                        );

                // Clear the input
                input.setText("");
            }
        });
    }

    private String Translate(String textToBeTranslated,String languagePair){
        TranslatorBackgroundTask translatorBackgroundTask= new TranslatorBackgroundTask(this);
        //String translationResult = translatorBackgroundTask.execute(textToBeTranslated,languagePair); // Returns the translated text as a String
        //String translationResult = translatorBackgroundTask.execute(textToBeTranslated,languagePair).get(); // Returns the translated text as a String
        String translationResult = null; // Returns the translated text as a String
        try {
            translationResult = translatorBackgroundTask.execute(textToBeTranslated,languagePair).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return translationResult;
    }

    private void displayChatMessages() {
        Query query = FirebaseDatabase.getInstance().getReference().child("messages");
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);
        
        FirebaseListOptions<ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>()
                .setQuery(query, ChatMessage.class)
                .setLayout(R.layout.message)
                .build();
        //Finally you pass them to the constructor here:
        adapter = new FirebaseListAdapter<ChatMessage>(options){
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                String textToBeTranslated = model.getMessageText();
                String languagePair = secondLang + "-" + firstLang; //English to French ("<source_language>-<target_language>")
                //Executing the translation function
                String translatedMessage = Translate(textToBeTranslated,languagePair);
                messageText.setText(translatedMessage);
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);
    }

}
