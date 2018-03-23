package worldontheotherside.wordpress.com.autismapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.autismapp.API.Info;
import worldontheotherside.wordpress.com.autismapp.R;

public class InfoMainActivity extends AppCompatActivity {

    ArrayList<Info> infoList; // will store list of the data we want to display in next page

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.info_main_activity);

        Button btnUrls = findViewById (R.id.btn_info_urls);
        Button btnBooks = findViewById (R.id.btn_info_books);
        Button btnCenters = findViewById (R.id.btn_info_centers);
        Button btnVideos = findViewById (R.id.btn_info_videos);

        btnUrls.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {

                setUsefulURLsInfo ();
            }
        });

        btnBooks.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {

                setBooksInfo ();
            }
        });

        btnCenters.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {

                setCentersInfo ();
            }
        });

        btnVideos.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {

                setVideosInfo ();
            }
        });


    }


    private void setVideosInfo () {
    }

    private void setCentersInfo () {

    }

    private void setBooksInfo () {
        // you can write all the useful web sites urls here as the following
        infoList = new ArrayList<> ();


        // item number 1

        Info info = new Info ();// this class will help us to save data easily !
        //set the  title >> it can be Book name or Topic title ...
        info.setInfoTitle ("ما هي اضطرابات طيف التوحد؟");

        //set the description of the Topic or the website ....
        info.setInfoDescription ("سيساعدك هذا المستند على معرفة اضطراب طيف التوحد بشكل عام");

        // we can add book url or download >>
        info.setInfoPageURL ("https://firebasestorage.googleapis.com/v0/b/autismapp-b0b6a.appspot.com/o/material%2FInfo-Pack-for"
                + "-translation-Arabic.pdf?alt=media&token=69b19a8d-8b4c-400a-a0eb-bb5c4e5324e6");

        infoList.add (info); //adding item 1 to list


/*        // item number 2
        info = new Info ();// save the second Book
        //set the  title >> it can be Book name or Topic title ...
        info.setInfoTitle ("Solve My Autism");

        //set the description of the Topic or the website ....
        info.setInfoDescription ("this Book has will help patient  of Autism to get better by communicate with experts  ...");

        // we can add the url >>
        info.setInfoPageURL ("https://material.io/");

        infoList.add (info);//adding item number 2 to list


        // item 3
        info = new Info ();// save the third Book
        //set the  title >> it can be website name or Topic title ...
        info.setInfoTitle ("Autism Community");

        //set the description of the Topic or the website ....
        info.setInfoDescription ("this an Book about   patient of Autism and how to help them get better  ...");

        // we can add the url >>
        info.setInfoPageURL ("https://www.tutorialspoint.com/android/index.html");
        infoList.add (info);// adding item 3 to list

        // item number 4
        info = new Info ();// save the second Book
        //set the  title >> it can be Book name or Topic title ...
        info.setInfoTitle ("Solve My Autism part 2");

        //set the description of the Topic or the website ....
        info.setInfoDescription ("this Book has will help patient  of Autism to get better by communicate with experts  ...");

        // we can add the url >>
        info.setInfoPageURL ("https://material.io/");

        infoList.add (info);//adding item number 4 to list


        // item 5
        info = new Info ();// save the third Book
        //set the  title >> it can be website name or Topic title ...
        info.setInfoTitle ("Autism Community 2");

        //set the description of the Topic or the website ....
        info.setInfoDescription ("this an Book about   patient of Autism and how to help them get better  ...");

        // we can add the url >>
        info.setInfoPageURL ("https://www.tutorialspoint.com/android/index.html");
        infoList.add (info);// adding item 5 to list*/


        displayInfo (infoList);

    }

    private void setUsefulURLsInfo () {

        // you can write all the useful web sites urls here as the following
        infoList = new ArrayList<> ();


        // item number 1

        Info info = new Info ();// this class will help us to save data easily !
        //set the  title >> it can be website name or Topic title ...
        info.setInfoTitle ("Autism.com");

        //set the description of the Topic or the website ....
        info.setInfoDescription ("this website has will help you identify the level of Autism of a patient ...");

        // we can add the url >>
        info.setInfoPageURL ("https://stackoverflow.com/");

        infoList.add (info); //adding item 1 to list


        // item number 2
        info = new Info ();// save the second Url
        //set the  title >> it can be website name or Topic title ...
        info.setInfoTitle ("We Can help solve Autism");

        //set the description of the Topic or the website ....
        info.setInfoDescription ("this website has will help patient  of Autism to get better by communicate with experts  ...");

        // we can add the url >>
        info.setInfoPageURL ("https://www.androidauthority.com/");

        infoList.add (info);//adding item number 2 to list


        // item 3
        info = new Info ();// save the third Url
        //set the  title >> it can be website name or Topic title ...
        info.setInfoTitle ("Say no for Autism");

        //set the description of the Topic or the website ....
        info.setInfoDescription ("this an article about   patient of Autism and how to help them get better  ...");

        // we can add the url >>
        info.setInfoPageURL ("https://developer.android.com/index.html");
        infoList.add (info);// adding item 3 to list


        displayInfo (infoList);

    }

    private void displayInfo (ArrayList<Info> infoList) {
        //this function will take us to next page and will pass data to it!
        Intent go = new Intent (InfoMainActivity.this, InfoDetailsActivity.class);
        go.putExtra ("infoList", infoList);
        startActivity (go);

    }
}
