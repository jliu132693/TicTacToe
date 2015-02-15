package droid.se1.zhong.tictactoedroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class ScoresFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scores, parent, false);

        AdView mAdView = (AdView) v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        final ListView scoresListView = (ListView)v.findViewById(R.id.listView);

        CredentialSingleton credentialSingleton = CredentialSingleton.get(getActivity());

        TextView loginStatusText = (TextView) v.findViewById(R.id.textView_loginStatus);

        if (credentialSingleton.getLoginStatus()) {
            loginStatusText.setText(credentialSingleton.getUsername());
        } else {
            loginStatusText.setText("You aren't logged in");
        }

        // TODO - add list of strings
        final ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);


        new Thread(new Runnable(){
            @Override
            public void run() {
                final  String scoresString = getScoresString();

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        String currentUsername = "";
                        String currentPassword = "";
                        boolean isCollectingUsername = true;

                        if (scoresString.equals("")) {
                            adapter.add("Unable to reach server. Try again later.");
                        } else {
                            for (int i = 0; i < scoresString.length(); i++) {
                                if (isCollectingUsername) {

                                    // '|' signifies break between username and password
                                    if (scoresString.charAt(i) == '|') {
                                        isCollectingUsername = false;
                                    } else {
                                        currentUsername += scoresString.charAt(i);
                                    }

                                } else {
                                    // '+' signifies break between info for two users
                                    if (scoresString.charAt(i) == '+') {
                                        isCollectingUsername = true;
                                        adapter.add( "Username: " + currentUsername + " AND Points: " + currentPassword);

                                        currentPassword = "";
                                        currentUsername = "";
                                    } else {
                                        currentPassword += scoresString.charAt(i);
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }).start();

        scoresListView.setAdapter(adapter);

        Button backButton = (Button)v.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return v;
    }

    private String getScoresString()
    {
        String responseString;
        try {

        String URL = "http://tictactoenetwork.appspot.com/topscores?specialFormat=true";

        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = httpclient.execute(new HttpGet(URL));
        StatusLine statusLine = response.getStatusLine();

        if(statusLine.getStatusCode() == HttpStatus.SC_OK){
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            response.getEntity().writeTo(out);
            out.close();
            responseString = out.toString();

        } else{
            //Closes the connection.
            response.getEntity().getContent().close();
            throw new IOException(statusLine.getReasonPhrase());
        }
        } catch (IOException e) {

            return "";
        }
        return responseString;
    }

}
