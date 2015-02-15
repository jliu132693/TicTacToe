package droid.se1.zhong.tictactoedroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.Arrays;

public class PlayFragment extends Fragment {


    GridView mGridView;
    TextView mResultTextView;
    final int mGRID_SIZE = 9;  // tic-tac-toe is 3x3 (9)
    final int mEMPTY_UNIT = 0;
    final int mPLAYER_UNIT = 1;
    final int mOPPONENT_UNIT = 2;
    final String mPLAYER_ICON = "X";
    final String mOPPONENT_ICON = "0";
    private boolean opponentChooseCorner = false;
    private boolean opponentChooseMiddle = false;

    int [] mGridArray = new int[mGRID_SIZE];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_play, parent, false);


        CredentialSingleton credentialSingleton = CredentialSingleton.get(getActivity());

        TextView loginStatusText = (TextView) v.findViewById(R.id.textView_loginStatus);

        if (credentialSingleton.getLoginStatus()) {
            loginStatusText.setText(credentialSingleton.getUsername());
        } else {
            loginStatusText.setText("You aren't logged in");
        }

        // TODO - look to update login/logout status

        Arrays.fill(mGridArray, mEMPTY_UNIT); // initialize with EMPTY_UNIT

        Button backButton = (Button)v.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mResultTextView = (TextView)v.findViewById(R.id.resultTextView);

        mGridView = (GridView)v.findViewById(R.id.gridView);
        mGridView.setAdapter(new BoxAdapter(getActivity()));
        mGridView.setVerticalSpacing(1);
        mGridView.setHorizontalSpacing(1);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                TextView mTextView = (TextView)mGridView.getAdapter().getView(position, v, parent);

                if (mTextView.getText() == "") { // if choice is empty, execute move and counter move

                    mTextView.setText(mPLAYER_ICON); // set empty square to player icon
                    mGridArray[position] = mPLAYER_UNIT; // update array



                    if (position==0) {
                        //System.out.println("0th");
                        opponentChooseCorner=true;
                        opponentChooseMiddle=false;
                    } else if (position==1) {
                        //System.out.println("1th");
                        opponentChooseCorner=false;
                        opponentChooseMiddle=false;
                    } else if (position==2) {
                        //System.out.println("2th");
                        opponentChooseCorner=true;
                        opponentChooseMiddle=false;
                    } else if (position==3) {
                        //System.out.println("3th");
                        opponentChooseCorner=false;
                        opponentChooseMiddle=false;
                    } else if (position==4) {
                        opponentChooseCorner=true;
                        opponentChooseMiddle=false;
                    } else if (position==5) {
                        opponentChooseCorner=false;
                        opponentChooseMiddle=true;
                    } else if (position==6) {
                        opponentChooseCorner=true;
                        opponentChooseMiddle=false;
                    } else if (position==7) {
                        opponentChooseCorner=true;
                        opponentChooseMiddle=false;
                    } else if (position==8) {
                        opponentChooseCorner=true;
                        opponentChooseMiddle=false;
                    }


                    generateOpponentMove();
                    if (isGameOver(true) || isFullBoard(true)) {

                        // generate dialog prompting user to issue next command

                        new AlertDialog.Builder(getActivity())
                                .setMessage("Play again?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getActivity().finish();
                                startActivity(getActivity().getIntent());  // refresh the activity
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getActivity().finish(); // return to main
                            }
                        }).show();

                        return;
                    }
                }
            }
        });

        return v;
    }

    private void allocatePoint()
    {
        final CredentialSingleton credentialSingleton = CredentialSingleton.get(getActivity());

        if (credentialSingleton.getLoginStatus()) { // user is logged in; allocate point now

            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        try {

                            String URL = "http://tictactoenetwork.appspot.com/play?";

                            URL += "username=" + credentialSingleton.getUsername() + "&";
                            URL += "password=" + credentialSingleton.getPassword();

                            HttpClient httpclient = new DefaultHttpClient();
                            httpclient.execute(new HttpGet(URL));


                        } catch (IOException e) {
                            // point couldn't be allocated; do nothing
                        }

                    } catch (Exception ex) {
                        // point couldn't be allocated; do nothing

                    }
                }
            }).start();
        }
    }

    private void generateOpponentMove() {
       /*ArrayList<Integer> possibleValues = new ArrayList<Integer>(); // stores all empty squares

        for (int i = 0; i < mGridArray.length; i++) {
            if (mGridArray[i] == mEMPTY_UNIT) {
                possibleValues.add(i);  // add empty square to possible
            }
        }

        int randChoice = possibleValues // choose random from possible moves
                .get(new Random().nextInt(possibleValues.size()));*/

        int i = 0;
        boolean winMoveFound = false;
        boolean tieMoveFound = false;


        while (i < mGRID_SIZE) {

            if  (mGridArray[i] == mEMPTY_UNIT) {

                // test possible move
                mGridArray[i] = mOPPONENT_UNIT;
                tieMoveFound |= isFullBoard(false); // test move; false to generate no text
                winMoveFound |= isGameOver(false); // test move; false to generate no text

                mGridArray[i] = mEMPTY_UNIT;

            }
            i++;
        }

        if (winMoveFound) {
            //System.out.println("ME WIN MOVE FOUND");
            // choose win move
            i = 0;
            winMoveFound = false;
            while (i < mGRID_SIZE) {

                if  (mGridArray[i] == mEMPTY_UNIT) {

                    // test possible move
                    mGridArray[i] = mOPPONENT_UNIT;
                    if(isGameOver(false)) {  // test move; false to generate no text
                        generateMove(mOPPONENT_UNIT, i);
                        break;
                    } else {
                        mGridArray[i] = mEMPTY_UNIT;
                    }


                }
                i++;
            }

        } else if (tieMoveFound) {
            //System.out.println("ME TIE MOVE FOUND");
            // choose tie move
            i = 0;
            tieMoveFound = false;
            while (i < mGRID_SIZE) {

                if  (mGridArray[i] == mEMPTY_UNIT) {

                    // test possible move
                    mGridArray[i] = mOPPONENT_UNIT;
                    if(isFullBoard(false)) {  // test move; false to generate no text
                        generateMove(mOPPONENT_UNIT, i);
                        break;
                    } else {
                        mGridArray[i] = mEMPTY_UNIT;
                    }

                }
                i++;
            }
        } else {
            //System.out.println("ELSE");
            // now check for opponent victory
            i = 0;
            tieMoveFound = false;
            winMoveFound = false;
            while (i < mGRID_SIZE) {

                if  (mGridArray[i] == mEMPTY_UNIT) {

                    // test possible move
                    mGridArray[i] = mPLAYER_UNIT;
                    tieMoveFound |= isFullBoard(false);
                    winMoveFound |= isGameOver(false);

                    mGridArray[i] = mEMPTY_UNIT;

                }
                i++;
            }

            if (winMoveFound) {
                //System.out.println("OPP WIN MOVE FOUND");
                // choose win move
                i = 0;
                winMoveFound = false;
                while (i < mGRID_SIZE) {

                    if  (mGridArray[i] == mEMPTY_UNIT) {

                        // test possible move
                        mGridArray[i] = mPLAYER_UNIT;
                        if(isGameOver(false)) {  // test move; false to generate no text
                            mGridArray[i] = mOPPONENT_UNIT; // prevent player from win

                            generateMove(mOPPONENT_UNIT, i);
                            break;
                        } else {
                            mGridArray[i] = mEMPTY_UNIT;
                        }


                    }
                    i++;
                }

            } else {
                //System.out.println("ADJ SQAURE ONLY");
                // here, choose adjacent square


                // first check middle if opponent hasn't chosen the corner
                if (opponentChooseCorner) {
                    if (mGridArray[4] == mEMPTY_UNIT) {
                        generateMove(mOPPONENT_UNIT, 4);
                        return;
                    } else if (mGridArray[0] == mPLAYER_UNIT) {
                        if (mGridArray[1] == mEMPTY_UNIT && mGridArray[7] != mPLAYER_UNIT) {
                            // choose and return
                            generateMove(mOPPONENT_UNIT, 1);
                            return;
                        } else if (mGridArray[3] == mEMPTY_UNIT && mGridArray[7] == mPLAYER_UNIT) {
                            // choose and return
                            generateMove(mOPPONENT_UNIT, 3);
                            return;
                        }
                    } else if (mGridArray[2] == mPLAYER_UNIT) {
                        if (mGridArray[1] == mEMPTY_UNIT && mGridArray[7] != mPLAYER_UNIT) {
                            // choose and return
                            generateMove(mOPPONENT_UNIT, 1);
                            return;
                        } else if (mGridArray[5] == mEMPTY_UNIT && mGridArray[7] == mPLAYER_UNIT) {
                            // choose and return
                            generateMove(mOPPONENT_UNIT, 5);
                            return;
                        }
                    } else if (mGridArray[6] == mPLAYER_UNIT) {
                        if (mGridArray[3] == mEMPTY_UNIT) {
                            // choose and return
                            generateMove(mOPPONENT_UNIT, 3);
                            return;
                        } else if (mGridArray[7] == mEMPTY_UNIT) {
                            // choose and return
                            generateMove(mOPPONENT_UNIT, 7);
                            return;
                        }
                    } else if (mGridArray[8] == mPLAYER_UNIT) {
                        if (mGridArray[5] == mEMPTY_UNIT) {
                            // choose and return
                            generateMove(mOPPONENT_UNIT, 5);
                            return;
                        } else if (mGridArray[7] == mEMPTY_UNIT) {
                            // choose and return
                            generateMove(mOPPONENT_UNIT, 7);
                            return;
                        }
                    }

                } else if (opponentChooseMiddle) {
                    if (mGridArray[0] == mEMPTY_UNIT) {
                        // then check top left corner
                        generateMove(mOPPONENT_UNIT, 0);
                        return;
                    }  else if (mGridArray[2] == mEMPTY_UNIT) {
                        // then check top right
                        generateMove(mOPPONENT_UNIT, 2);
                        return;
                    } else if (mGridArray[6] == mEMPTY_UNIT) {
                        // then check bottom left
                        generateMove(mOPPONENT_UNIT, 6);
                        return;
                    } else if (mGridArray[8] == mEMPTY_UNIT) {
                        // then check bottom right
                        generateMove(mOPPONENT_UNIT, 8);
                        return;
                    }
                } else {
                    if (mGridArray[3] == mPLAYER_UNIT) {
                        if (mGridArray[0] == mEMPTY_UNIT) {
                            // choose and return
                            generateMove(mOPPONENT_UNIT, 0);
                            return;
                        } else if (mGridArray[6] == mEMPTY_UNIT) {
                            // choose and return
                            generateMove(mOPPONENT_UNIT, 6);
                            return;
                        }
                    } else if (mGridArray[2] == mPLAYER_UNIT) {
                        if (mGridArray[0] == mEMPTY_UNIT) {
                            // choose and return
                            generateMove(mOPPONENT_UNIT, 0);
                            return;
                        } else if (mGridArray[3] == mEMPTY_UNIT) {
                            // choose and return
                            generateMove(mOPPONENT_UNIT, 3);
                            return;
                        }
                    } else if (mGridArray[5] == mPLAYER_UNIT) {
                        if (mGridArray[2] == mEMPTY_UNIT) {
                            // choose and return
                            generateMove(mOPPONENT_UNIT, 2);
                            return;
                        } else if (mGridArray[8] == mEMPTY_UNIT) {
                            // choose and return
                            generateMove(mOPPONENT_UNIT, 8);
                            return;
                        }
                    } else if (mGridArray[7] == mPLAYER_UNIT) {
                        if (mGridArray[6] == mEMPTY_UNIT) {
                            // choose and return
                            generateMove(mOPPONENT_UNIT, 6);
                            return;
                        } else if (mGridArray[8] == mEMPTY_UNIT) {
                            // choose and return
                            generateMove(mOPPONENT_UNIT, 8);
                            return;
                        }
                    }
                }

                //System.out.println("AT THE END");

                i = 0;
                winMoveFound = false;
                boolean previousSquareWasOpponent = false;

                while (i < mGRID_SIZE) {

                    if (mGridArray[i] == mPLAYER_UNIT) {
                        previousSquareWasOpponent = true;
                    } else if (mGridArray[i] == mOPPONENT_UNIT) {
                        previousSquareWasOpponent = false;
                    }
                    else {
                        if (previousSquareWasOpponent) {
                            generateMove(mOPPONENT_UNIT, i);
                            break;
                        }
                    }

                    i++;
                }

                // if you make it to end, chose random
            }

        }


    }

    private void generateMove(int playerUnit, int index) {

        TextView textView = (TextView)mGridView.getChildAt(index);

        if (playerUnit==mOPPONENT_UNIT) {
            textView.setText(mOPPONENT_ICON);
            mGridArray[index] = mOPPONENT_UNIT; // update array    
        } else {
            textView.setText(mPLAYER_ICON);
            mGridArray[index] = mPLAYER_UNIT; // update array
        }
        
        
    }

    private boolean isGameOver(boolean actualEndGame) {

        boolean gameOver = false;
        int[][]endCombinations = new int[][]{{0,1,2}, {3,4,5}, {6,7,8}, {0, 3, 6},
                {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

        for (int i = 0; i < endCombinations.length; i++) {

            // check for equality among squares that constitute a winning combination
            if (mGridArray[endCombinations[i][0]] == mGridArray[endCombinations[i][1]]
                    && mGridArray[endCombinations[i][0]] == mGridArray[endCombinations[i][2]]) {

                if ( mGridArray[endCombinations[i][0]] == mOPPONENT_UNIT) {  // opponent match

                    if (actualEndGame) {
                        mResultTextView.setText("YOU LOOSE!");
                    }


                    gameOver = true;
                } else if (mGridArray[endCombinations[i][0]] == mPLAYER_UNIT) { // player match

                    if (actualEndGame) {
                        mResultTextView.setText("YOU WIN!");
                    }


                    allocatePoint(); // user managed to beat AI; allocate point

                    gameOver = true;
                } else {
                    // empty match; do nothing
                }
            }
        }



        return gameOver ; // gameOver OR fullBoard constitutes end game
    }

    private boolean isFullBoard(boolean actualEndGame) {
        boolean emptyPieceFound = false;
        for (int i = 0; i < mGridArray.length; i++) {
            emptyPieceFound |= (mGridArray[i] == mEMPTY_UNIT); // check for full board
        }

        if (actualEndGame && !emptyPieceFound) {
            mResultTextView.setText("BOARD IS FULL. DRAW!");

            allocatePoint(); // user managed to draw against AI; allocate point
        }

        return !emptyPieceFound;
    }

    private class BoxAdapter extends BaseAdapter {

        private Context mContext;

        public BoxAdapter(Context c) {
            mContext = c;
        }

        @Override
        public int getCount() {
            return mGRID_SIZE;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView mTextView;

            if (convertView == null) { // from developer.android: recycle is possible
                mTextView = new TextView(mContext);
            } else {
                mTextView = (TextView)convertView;
            }

            // initialize the textview
            mTextView.setBackgroundColor(Color.BLACK);
            mTextView.setTextColor(Color.WHITE);
            mTextView.setGravity(Gravity.CENTER);
            mTextView.setTextSize(20);
            mTextView.setHeight(200); // chosen somewhat arbitrarily; change possible
            mTextView.setWidth(50); // chosen somewhat arbitrarily; change possible
            return mTextView;
        }

        @Override
        public Object getItem(int position)
        {
            return null; // TODO - implement
        }

        @Override
        public long getItemId(int position)
        {
            return 0; // TODO - implement
        }
    }

}