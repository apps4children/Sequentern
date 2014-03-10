package com.iitk.geo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
public class SelectLevel extends Activity implements OnClickListener 
{
	Intent intentlevel1,intentlevel2,intentlevel3,intentlevel4,intentlevel5,intentlevel6,intentlevel7;
	static SelectLevel activityLevel;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        activityLevel=this;
        setContentView(R.layout.select_level);
        
        View level1= findViewById(R.id.learn_shapes);
        level1.setOnClickListener(this);
        
        View level2= findViewById(R.id.shape_to_shape);
        level2.setOnClickListener(this);
        
        View level3= findViewById(R.id.name_to_shape);
        level3.setOnClickListener(this);
        
        View level4= findViewById(R.id.match_type1);
        level4.setOnClickListener(this);
        
        View level5= findViewById(R.id.match_type2);
        level5.setOnClickListener(this);
        
        View level6= findViewById(R.id.match_type3);
        level6.setOnClickListener(this);
        
        View level7= findViewById(R.id.shape_to_name);
        level7.setOnClickListener(this);
        
        
        intentlevel1= new Intent(this,LearnShapes.class);
        intentlevel2=new Intent(this,ShapeToShape.class);
        intentlevel3=new Intent(this,NameToShape.class);
        intentlevel4= new Intent(this,LevelEasy.class);
        intentlevel5=new Intent(this,LevelMedium.class);
        intentlevel6= new Intent(this,LevelHard.class);
        intentlevel7= new Intent(this,ShapeToName.class);
        
        Button back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new Button.OnClickListener()
        {
			public void onClick(View v) 
			{
					finish();
		}});
    }
    public void onClick(View v) 
    {
    	switch (v.getId()) 
    	{
    	case R.id.learn_shapes:
    		startActivity(intentlevel1);
        	break;
    	case R.id.shape_to_shape:
    		startActivity(intentlevel2);
    	break;
    	case R.id.name_to_shape:
    		startActivity(intentlevel3);
       	break;
       	case R.id.match_type1:
    		startActivity(intentlevel4);
    	break;
    	case R.id.match_type2:
    		startActivity(intentlevel5);
    	break;
    	case R.id.match_type3:
    		startActivity(intentlevel6);
    	break;
    	case R.id.shape_to_name:
    		startActivity(intentlevel7);
    	break;
    	}
    }
}