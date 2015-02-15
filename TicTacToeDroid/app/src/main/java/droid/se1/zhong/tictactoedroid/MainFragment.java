package droid.se1.zhong.tictactoedroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainFragment extends Fragment {

    static final int LOGIN_REGISTER_CODE = 2;

    @Override
    public void onActivityResult(int requestcode, int resultCode, Intent data)
    {
        if (requestcode == LOGIN_REGISTER_CODE) {

            final ViewGroup viewGroup = (ViewGroup) ( getActivity()
                    .findViewById(android.R.id.content)).getRootView();

            View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_main, viewGroup, false);
            TextView loginStatusText = (TextView) v.findViewById(R.id.textView_loginStatus);

            CredentialSingleton credentialSingleton = CredentialSingleton.get(getActivity());
            Button loginButton = (Button)v.findViewById(R.id.login_button);
            Button logoutButton = (Button)v.findViewById(R.id.logout_button);

            Button registerButton = (Button)v.findViewById(R.id.register_button);

            if (credentialSingleton.getLoginStatus()) {
                loginStatusText.setText(credentialSingleton.getUsername());

                logoutButton.setVisibility(View.VISIBLE);

                loginButton.setVisibility(View.GONE);
                registerButton.setVisibility(View.GONE);

            } else {
                loginStatusText.setText("You aren't logged in");

                logoutButton.setVisibility(View.GONE);

                loginButton.setVisibility(View.VISIBLE);
                registerButton.setVisibility(View.VISIBLE);
            }

            getActivity().recreate();
        } else {
            // unknown, do nothing
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, parent, false);

        AdView mAdView = (AdView) v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        final CredentialSingleton credentialSingleton = CredentialSingleton.get(getActivity());
        final TextView loginStatusText = (TextView) v.findViewById(R.id.textView_loginStatus);
        final Button loginButton = (Button)v.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), LoginActivity.class), LOGIN_REGISTER_CODE);
            }
        });

        final Button registerButton = (Button)v.findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), RegisterActivity.class), LOGIN_REGISTER_CODE);
            }
        });

        final Button logoutButton = (Button)v.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               credentialSingleton.setLoginStatus(false);
                credentialSingleton.setPassword("");
                credentialSingleton.setUsername("");

                // TODO - prevent duplication

                    loginStatusText.setText("You aren't logged in");

                    logoutButton.setVisibility(View.GONE);

                    loginButton.setVisibility(View.VISIBLE);
                    registerButton.setVisibility(View.VISIBLE);

            }
        });

        Button scoresButton = (Button)v.findViewById(R.id.scores_button);
        scoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ScoresActivity.class));
            }
        });


        Button playButton = (Button)v.findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PlayActivity.class));
            }
        });


        if (credentialSingleton.getLoginStatus()) {
            loginStatusText.setText(credentialSingleton.getUsername());
            logoutButton.setVisibility(View.VISIBLE);

            loginButton.setVisibility(View.GONE);
            registerButton.setVisibility(View.GONE);

        } else {
            loginStatusText.setText("You aren't logged in");

            logoutButton.setVisibility(View.GONE);

            loginButton.setVisibility(View.VISIBLE);
            registerButton.setVisibility(View.VISIBLE);
        }

        return v;
    }
}
