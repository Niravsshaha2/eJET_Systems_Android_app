package com.example.sonaai_11;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.kommunicate.KmConversationBuilder;
import io.kommunicate.Kommunicate;
import io.kommunicate.callbacks.KmCallback;


public class chatbot_list extends AppCompatActivity {

    ListView listView;
    String mHotel[]={"Mental Health Chat","Interview","Onboarding","General Enquiry","Survey/Feedback","Training"};
    Button logout;
    private static final String APP_ID ="319fa8f15d3befbfd136b4c4417233d1e" ;

    @Override
    public void onBackPressed() {

    }
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot_list);
         getSupportActionBar().hide();
         Kommunicate.init(getApplicationContext(), APP_ID);

        listView=(ListView)findViewById(R.id.listview);

        MyAdapter adapter=new MyAdapter(this,mHotel);
        listView.setAdapter(adapter);

        //logout button
         logout=findViewById(R.id.logout);
         logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v)
             {
                 Toast.makeText(getApplicationContext(), "logout button", Toast.LENGTH_SHORT).show();

                 FirebaseAuth.getInstance().signOut();
                 LoginManager.getInstance().logOut();
                 Intent i=new Intent(getApplicationContext(),signIn.class);
                 startActivity(i);
             }
         });




    }

    class MyAdapter extends ArrayAdapter<String>
    {
        Context context;
        String mHotel[];

        MyAdapter(Context c,String Hotel[])
        {
            super(c,R.layout.row,R.id.hotel,Hotel);
            this.context=c;
            this.mHotel=Hotel;
         }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutInflater.inflate(R.layout.row,parent,false);
            TextView myHotel=row.findViewById(R.id.hotel);

            myHotel.setText(mHotel[position]);


            Button btn=(Button)row.findViewById(R.id.check);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position==0)
                    {
                        //dEPRESSION - MENTAL HEALTH
                        final List<String> botIdd = new ArrayList<>();
                        botIdd.add("depression-jw8gy");
                        final ProgressDialog pd = new ProgressDialog(chatbot_list.this);
                        pd.setMessage("loading");
                        pd.show();

                        new KmConversationBuilder(chatbot_list.this)
                                .setConversationAssignee("Depression")
                                .setConversationTitle("Depression")
                                .setSingleConversation(false)
                                .setBotIds(botIdd)
                                .launchConversation(new KmCallback() {
                                    @Override
                                    public void onSuccess(Object message) {
                                        pd.dismiss();

                                        Toast.makeText(chatbot_list.this, "Success", Toast.LENGTH_SHORT).show();
                                        Log.d("Conversation", "Success : " + message);
                                    }

                                    @Override
                                    public void onFailure(Object error) {
                                        pd.dismiss();

                                        Log.d("Conversation", "Failure : " + error);
                                        Toast.makeText(chatbot_list.this, "Success", Toast.LENGTH_SHORT).show();

                                    }
                                });


                    }
                    else if (position==1)
                    {

                        final List<String> botIdd = new ArrayList<>();
                        botIdd.add("");
                        final ProgressDialog pd = new ProgressDialog(chatbot_list.this);
                        pd.setMessage("loading");
                        pd.show();

                        new KmConversationBuilder(chatbot_list.this)
                                .setConversationAssignee("")
                                .setConversationTitle(" ")
                                .setSingleConversation(false)
                                .setBotIds(botIdd)
                                .launchConversation(new KmCallback() {
                                    @Override
                                    public void onSuccess(Object message) {
                                        pd.dismiss();

                                        Toast.makeText(chatbot_list.this, "Success", Toast.LENGTH_SHORT).show();
                                        Log.d("Conversation", "Success : " + message);
                                    }

                                    @Override
                                    public void onFailure(Object error) {
                                        pd.dismiss();

                                        Log.d("Conversation", "Failure : " + error);
                                        Toast.makeText(chatbot_list.this, "Success", Toast.LENGTH_SHORT).show();

                                    }
                                });

                    }
                    else if (position==2)
                    {
                        //ONBOARDING
                        List<String> botIda = new ArrayList<>();
                        botIda.add("onboarding-yy7lk");
                        final ProgressDialog pd = new ProgressDialog(chatbot_list.this);
                        pd.setMessage("loading");
                        pd.show();


                        new KmConversationBuilder(chatbot_list.this)
                                .setConversationTitle("Onboarding")
                                .setConversationAssignee("Onboarding")
                                .setSingleConversation(false)
                                .setBotIds(botIda)
                                .launchConversation(new KmCallback() {
                                    @Override
                                    public void onSuccess(Object message) {
                                        pd.dismiss();

                                        Toast.makeText(chatbot_list.this, "Success", Toast.LENGTH_SHORT).show();
                                        Log.d("Conversation", "Success : " + message);
                                    }

                                    @Override
                                    public void onFailure(Object error) {
                                        pd.dismiss();

                                        Log.d("Conversation", "Failure : " + error);
                                        Toast.makeText(chatbot_list.this, "Success", Toast.LENGTH_SHORT).show();

                                    }
                                });
                    }
                    else if (position==3)
                    {
                        Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),chatBot.class);
                        i.putExtra("number", 4);
                        startActivity(i);
                    }
                   else if (position==4)
                    {
                        Toast.makeText(getApplicationContext(), "5", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),chatBot.class);
                        i.putExtra("number", 5);
                        startActivity(i);
                    }

                    else
                    {
                        Toast.makeText(getApplicationContext(), "6", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),chatBot.class);
                        i.putExtra("number", 6);
                        startActivity(i);
                    }

                }
            });

            return row;
        }
    }
}
