package com.iitk.geo;
import com.iitk.database.*;
import com.iitk.report.DisplayRecord;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
public class GeometryActivity extends Activity implements OnClickListener 
{
   static GeometryActivity activityMain;
   Dialog dialog;
   EditText name;
   String savename="";
   TestAdapter mDbHelper; 
   @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        activityMain=this;
        setContentView(R.layout.main);
        mDbHelper = new TestAdapter(this);
        
        View play = findViewById(R.id.play);
        play.setOnClickListener(this);
        
        View instruction =findViewById(R.id.instruction);
        instruction.setOnClickListener(this);
        
        View exit = findViewById(R.id.exit);
        exit.setOnClickListener(this);
        
        View score= findViewById(R.id.score);
        score.setOnClickListener(this);
        
        userForm();
    }
    public void onClick(View v) 
    {
    	switch (v.getId()) 
    	{
    	case R.id.play:
    		Intent playgame = new Intent(this,SelectLevel.class);
        	startActivity(playgame);
        	break;
    	case R.id.instruction:
    	    Intent gameInstruction= new Intent(this,Instruction.class);
    	    startActivity(gameInstruction);
    	break;
    	case R.id.score:
    	    Intent gamescore= new Intent(this,DisplayRecord.class);
    	    startActivity(gamescore);
    	break;
    	case R.id.exit:
            finish();
            System.exit(0);
       	break;
    	}
    }
    @SuppressLint("NewApi")
	public void userForm()
	{
		dialog = new Dialog(GeometryActivity.this);
		dialog.setContentView(R.layout.userdetail);
		dialog.setTitle("Player Details");
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);

        name=(EditText) dialog.findViewById(R.id.name);
        Button save= (Button)dialog.findViewById(R.id.save);
        Button cancel= (Button)dialog.findViewById(R.id.cancel);
		save.setOnClickListener(new OnClickListener() 
        {
        public void onClick(View v) 
        {
        savename=name.getText().toString();
        if (name ==null|| name.length() == 0) 
      	{
		name.setError("Enter Your Name");
		name.requestFocus();
		}
      	else
      	{
         int userid;
         userid=Integer.parseInt(savePlayerNameDB(savename));//inserting name in database and fetching id
         savePlayerNameApplication(savename, userid);//saving name and id to shared preference
      	 dialog.dismiss();
      	}
        }
        });
		cancel.setOnClickListener(new OnClickListener() 
        {
        public void onClick(View v) 
        {
        	savePlayerNameApplication("",0);//saving null in name and  0 in id to shared preference to ignore recording
        	dialog.dismiss();
        }
        });
		dialog.show();
	}
    
    public void savePlayerNameApplication(String name,int id)
    {
    SharedPreferences sharedPref= getSharedPreferences("mypref", MODE_PRIVATE);
    SharedPreferences.Editor editor= sharedPref.edit();
    editor.putString("playerName",name);
    editor.putInt("playerID",id);
    editor.commit();
    }
    
    public String savePlayerNameDB(String name) 
    {
    	String id;
		mDbHelper.createDatabase();       
		mDbHelper.open(); 
		id=mDbHelper.insertStudent(name); 
		mDbHelper.close();
		return id;
	}
}