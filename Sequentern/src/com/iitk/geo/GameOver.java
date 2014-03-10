package com.iitk.geo;
import com.iitk.report.DisplayRecord;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
public class GameOver extends Activity implements OnClickListener 
{
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.gameover);
        
        View play = findViewById(R.id.play);
        play.setOnClickListener(this);
        
        View score= findViewById(R.id.score);
        score.setOnClickListener(this);
        
        View exit = findViewById(R.id.exit);
        exit.setOnClickListener(this);
    }
    public void onClick(View v) 
    {
    	switch (v.getId()) 
    	{
    	case R.id.play:
        	finish();
        	break;
    	case R.id.score:
    	    Intent gamescore= new Intent(this,DisplayRecord.class);
    	    startActivity(gamescore);
    	    finish();
    	break;
    	case R.id.exit:
    		GeometryActivity.activityMain.finish();
    	    SelectLevel.activityLevel.finish();
            finish();
            System.exit(0);
       	break;
    	}
    }
    @Override
    public void onBackPressed() 
    {
            super.onBackPressed();
            this.finish();
    }
}