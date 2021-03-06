package droid.se1.zhong.tictactoedroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, parent, false);

        AdView mAdView = (AdView) v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        final EditText usernameText = (EditText) v.findViewById(R.id.editText_username);
        final EditText passwordText = (EditText) v.findViewById(R.id.editText_password);
        final TextView errorText = (TextView) v.findViewById(R.id.textView_error);
        final CredentialSingleton credentialSingleton = CredentialSingleton.get(getActivity());

        Button backButton = (Button)v.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        Button submitButton = (Button)v.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {

                            String username = usernameText.getText().toString();
                            String pw = passwordText.getText().toString();

                            try {

                                String URL = "http://www.tictactoenetwork.appspot.com/login?";

                                HttpClient httpclient = new DefaultHttpClient();
                                HttpPost httppost = new HttpPost(URL);

                                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                                nameValuePairs.add(new BasicNameValuePair("username", username));
                                nameValuePairs.add(new BasicNameValuePair("password", pw));

                                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                                HttpResponse response = httpclient.execute(httppost);

                                response.getEntity().getContent().close(); // close the connection

                                if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){

                                    credentialSingleton.setUsername(username);
                                    credentialSingleton.setPassword(pw);
                                    credentialSingleton.setLoginStatus(true);

                                    getActivity().finish();

                                } else{
                                    throw new IOException(response.getStatusLine().getReasonPhrase());
                                }
                            } catch (IOException e) {

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        errorText.setText("Invalid input."); // notify user
                                    }
                                });
                            }

                        } catch (Exception ex) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    errorText.setText("Unable to reach server."); // notify user
                                }
                            });
                        }
                    }
                }).start();
                }

        });

        return v;
    }
}
