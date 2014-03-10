package com.iitk.geo;
import java.util.ArrayList;

import com.iitk.database.TestAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
public class LevelHard extends Activity
{
	StringBuilder sb = new StringBuilder();
	TestAdapter mDbHelper;
	String wrongQuestion=null;
	int wrongCounter=0;
	int totalWrongCounter=0;
	
	int cropSize;
	SoundManager snd;
	MediaPlayer mediaPlayer;
	int right,wrong;
	
	private ArrayList<ImageView> mButtons=null ;
	LayoutAnimationController layoutcontroller;
	Animation animAlpha;
	int screenCounter=0;
	Intent intent;
	Intent gameover;
	GridView gridView;
	ImageView source,target;
	CustomAdapter ca;
	MyDragEventListener myDragEventListener = new MyDragEventListener();
	
	public Integer[][] gameBoard={ 
	{R.drawable.cpentagon,R.drawable.ctrepazium2,R.drawable.crectangle2,R.drawable.cnext,R.drawable.ctrepazium2,R.drawable.crectangle2,  R.drawable.cpentagon,R.drawable.cpentagon2,R.drawable.cpentagon1,R.drawable.cpentagon},
	{R.drawable.ctrepazium2,R.drawable.chexagon,R.drawable.cpentagon,R.drawable.cnext,R.drawable.chexagon,R.drawable.cpentagon,  R.drawable.ctrepazium2,R.drawable.ctrepazium21,R.drawable.ctrepazium2,R.drawable.ctrepazium22},
	{R.drawable.crectangle2,R.drawable.ctriangle2,R.drawable.cdiamond2,R.drawable.crectangle2,R.drawable.cnext,R.drawable.cdiamond2,  R.drawable.ctriangle2,R.drawable.ctriangle2,R.drawable.ctriangle21,R.drawable.ctriangle22},
	{R.drawable.crhombus,R.drawable.ctrepazium,R.drawable.crectangle2,R.drawable.crhombus,R.drawable.cnext,R.drawable.crectangle2,  R.drawable.ctrepazium,R.drawable.trepazium1c,R.drawable.ctrepazium,R.drawable.trepazium2c},
	{R.drawable.ctrepazium2,R.drawable.crectangle,R.drawable.cdiamond2,R.drawable.ctrepazium2,R.drawable.cnext,R.drawable.cdiamond2,  R.drawable.crectangle,R.drawable.crectangle,R.drawable.crectangle1c,R.drawable.crectangle2c},
					
	{R.drawable.ccircle,R.drawable.crectangle2,R.drawable.chexagon,R.drawable.cnext,R.drawable.crectangle2,R.drawable.chexagon,  R.drawable.ccircle,R.drawable.ccircle1c,R.drawable.ccircle,R.drawable.ccircle2c},
	{R.drawable.crhombus,R.drawable.cpentagon,R.drawable.cdiamond,R.drawable.cnext,R.drawable.cpentagon,R.drawable.cdiamond,  R.drawable.crhombus,R.drawable.crhombus1c,R.drawable.crhombus2c,R.drawable.crhombus},
	{R.drawable.cheart,R.drawable.ctriangle2,R.drawable.csquare,R.drawable.cnext,R.drawable.ctriangle2,R.drawable.csquare,  R.drawable.cheart,R.drawable.cheart1,R.drawable.cheart2,R.drawable.cheart},
	{R.drawable.cstar,R.drawable.cheart,R.drawable.cpentagon,R.drawable.cnext,R.drawable.cheart,R.drawable.cpentagon,  R.drawable.cstar,R.drawable.cstar1,R.drawable.cstar,R.drawable.cstar2},
	{R.drawable.ctrepazium,R.drawable.cheart,R.drawable.cstar,R.drawable.cnext,R.drawable.cheart,R.drawable.cstar,  R.drawable.ctrepazium,R.drawable.trepazium1c,R.drawable.trepazium2c,R.drawable.ctrepazium}
	};	
					
	public String[][] shapeName={
	{"Pentagon","Trapezium","Rectangle","Next","Trapezium","Rectangle", "Pentagon","Mistake","Mistake","Pentagon"},
	{"Trapezium","Hexagon","Pentagon","Next","Hexagon","Pentagon", "Trapezium","Mistake","Trapezium","Mistake"},
	{"Rectangle","Triangle","Diamond","Rectangle","Next","Diamond", "Triangle","Triangle","Mistake","Mistake"},
	{"Rhombus","Trapezium","Rectangle","Rhombus","Next","Rectangle", "Trapezium","Mistake","Trapezium","Mistake"},
	{"Trapezium","Rectangle","Diamond","Trapezium","Next","Diamond", "Rectangle","Rectangle","",""},
					
	{"Circle","Rectangle","Hexagon","Next","Rectangle","Hexagon", "Circle","Mistake","Circle","Mistake"},
	{"Rhombus","Pentagon","Diamond","Next","Pentagon","Diamond", "Rhombus","Mistake","Mistake","Rhombus"},
	{"Heart","Triangle","Square","Next","Triangle","Square", "Heart","Mistake","Mistake","Heart"},
	{"Star","Heart","Pentagon","Next","Heart","Pentagon", "Star","Mistake","Star","Mistake"},
	{"Trapezium","Heart","Star","Next","Heart","Star", "Trapezium","Mistake","Mistake","Trapezium"},
	};
    @SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.level_medium);
        
        mDbHelper = new TestAdapter(this);
        
        cropSize= (int) getResources().getDimension(R.dimen.crop_size);
        
        snd = new SoundManager(getApplicationContext());
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        right=snd.load(R.raw.right);
        wrong=snd.load(R.raw.wrong);
        
        mediaPlayer = MediaPlayer.create(LevelHard.this, R.raw.instruction4);//play on load
        mediaPlayer.start();//play on load
        
        Animation androidAnimation = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        animAlpha=AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
    	layoutcontroller = new LayoutAnimationController(androidAnimation);
    	mButtons=new ArrayList<ImageView>();
        ImageView button= null;
 	    for (int i=0; i<10; i++) 
 	    {
 	     if(i<6)
 	 	 {
 	 	 button =new ImageView(this);
 	 	 button.setId(i);
 	 	 button.setTag(gameBoard[screenCounter][i]);
 	 	 button.setImageResource(gameBoard[screenCounter][i]);
 	 	 button.setBackgroundResource(R.drawable.shape_border1);
 	 	 button.setOnDragListener(myDragEventListener);
 	 	 }
 	     else if(i==6)
 	     {
 	     button =new ImageView(this);
 	 	 button.setId(i);
 	 	 button.setTag(gameBoard[screenCounter][i]);
 	 	 button.setBackgroundColor(Color.TRANSPARENT);
 	     }
 	     else
 	     {
 	     button =new ImageView(this);
 	     button.setId(i);
 	     button.setTag(gameBoard[screenCounter][i]);
 	     button.setImageResource(gameBoard[screenCounter][i]);
 	     button.setOnTouchListener(new MyTouchListener());
		 button.setOnDragListener(myDragEventListener);
 	     }
 	     mButtons.add(button);
 	     }
 	     gridView =(GridView)findViewById(R.id.gridview2);
 	     gridView.setAdapter(new CustomAdapter(mButtons,cropSize));
 	     gridView.setLayoutAnimation(layoutcontroller);
 	     
 	    Button back=(Button)findViewById(R.id.back);
        
        intent =new Intent(this,GeometryActivity.class);
        gameover =new Intent(this,GameOver.class);
        back.setOnClickListener(new Button.OnClickListener()
        {
			public void onClick(View v) 
			{
				finish();
		}});
    }
	private final class MyTouchListener implements OnTouchListener 
	{
	    @SuppressLint("NewApi")
		public boolean onTouch(View view, MotionEvent motionEvent) 
	    {
	    	if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
	    		//ImageView imgview=(ImageView)view;
				source=(ImageView) view;
	            ClipData clipData = ClipData.newPlainText("", "");
	            View.DragShadowBuilder dsb = new View.DragShadowBuilder(view);
	            view.startDrag(clipData, dsb, view, 0);
	            //ca.notifyDataSetChanged();
	            return true;
	        } else {
	            return false;
	        }
	    }
	 }
	@SuppressLint("NewApi")
	private static class MyDragShadowBuilder extends View.DragShadowBuilder 
	{
	    private static Drawable shadow;
	    @SuppressLint("NewApi")
		public MyDragShadowBuilder(View v) 
	    {
	      super(v);
	      shadow = new ColorDrawable(Color.LTGRAY);
	    }
	    @SuppressLint("NewApi")
		@Override
	    public void onProvideShadowMetrics (Point size, Point touch)
	    {
	      int width = getView().getWidth();
	      int height = getView().getHeight();
	      shadow.setBounds(0, 0, width, height);
	      size.set(width, height);
	      touch.set(width / 2, height / 2);
	    }
	    @Override
	    public void onDrawShadow(Canvas canvas) 
	    {
	      shadow.draw(canvas);
	    }
	}
	
	@SuppressLint("NewApi")
	protected class MyDragEventListener implements View.OnDragListener 
	 {
	   public boolean onDrag(View v, DragEvent event) 
	   {
	   target=(ImageView) v;
	   final int action = event.getAction();
	   View dragView = (View) event.getLocalState();
	   switch(action) 
	   {
	   case DragEvent.ACTION_DRAG_STARTED:
       return true;
	   case DragEvent.ACTION_DRAG_ENTERED:
	    return true;
	   case DragEvent.ACTION_DRAG_LOCATION:
	    return true;
	   case DragEvent.ACTION_DRAG_EXITED:
	    return true;
	   case DragEvent.ACTION_DROP:
		   if(target.getId()==3&&screenCounter<=1)
		   {
			   if(source.getTag().equals(gameBoard[screenCounter][6]))
			   {
			   //
				if(wrongQuestion!=null&&wrongQuestion.equalsIgnoreCase(shapeName[screenCounter][6]))
				{
				sb.append(","+shapeName[screenCounter][source.getId()]);
				wrongQuestion=null;
				}
				else
				sb.append(shapeName[screenCounter][6]+"-"+shapeName[screenCounter][source.getId()]);
				sb.append("\n");
			   //			   
				   
			   ((ImageView)target).setImageResource(gameBoard[screenCounter][source.getId()]);
			   changeBorderColor();
			   snd.play(right);
			   screenCounter=screenCounter+1;
			   if(screenCounter<=9)
				 createNextScreen();
			   else
			   {
				  //update score
				  if(sb.toString().length()==0)
				  sb.append("No mistakes");
				  savescore(sb.toString());
				  gameEnd();
			    }
			   }
			   else
			   {
			       ((ImageView)target).startAnimation(animAlpha);
			       snd.play(wrong);
			       totalWrongCounter=totalWrongCounter+1;
			   
			       if(wrongQuestion==null)
				   {
					  wrongQuestion=shapeName[screenCounter][6];
					  sb.append(shapeName[screenCounter][6]+"-"+shapeName[screenCounter][source.getId()]);
				   }
				   else if(wrongQuestion.equalsIgnoreCase(shapeName[screenCounter][6]))
				   {
					   sb.append(","+shapeName[screenCounter][source.getId()]);
				   }
				   else if(!wrongQuestion.equalsIgnoreCase(shapeName[screenCounter][6]))
				   {
					   //sb.append("\n");
					   wrongQuestion=shapeName[screenCounter][6];
					   sb.append(shapeName[screenCounter][6]+"-"+shapeName[screenCounter][source.getId()]); 
				   }
			   }
		   }
		   else if(target.getId()==4&&screenCounter>1&&screenCounter<5)
		   {
			   if(source.getTag().equals(gameBoard[screenCounter][6]))
			   {
			   //
				if(wrongQuestion!=null&&wrongQuestion.equalsIgnoreCase(shapeName[screenCounter][6]))
				{
				sb.append(","+shapeName[screenCounter][source.getId()]);
				wrongQuestion=null;
				}
				else
				sb.append(shapeName[screenCounter][6]+"-"+shapeName[screenCounter][source.getId()]);
				sb.append("\n");
				//			   
				   
			   ((ImageView)target).setImageResource(gameBoard[screenCounter][source.getId()]);
			   changeBorderColor();
			   snd.play(right);
			   screenCounter=screenCounter+1;
			   if(screenCounter<=9)
			   createNextScreen();
			   else
			   {
				  //update score
				  if(sb.toString().length()==0)
				  sb.append("No mistakes");
				  savescore(sb.toString());
				  gameEnd();
			    }
			   }
			   else
			   {
			       ((ImageView)target).startAnimation(animAlpha);
			       snd.play(wrong);
			       totalWrongCounter=totalWrongCounter+1;
			   
			       if(wrongQuestion==null)
				   {
					  wrongQuestion=shapeName[screenCounter][6];
					  sb.append(shapeName[screenCounter][6]+"-"+shapeName[screenCounter][source.getId()]);
				   }
				   else if(wrongQuestion.equalsIgnoreCase(shapeName[screenCounter][6]))
				   {
					   sb.append(","+shapeName[screenCounter][source.getId()]);
				   }
				   else if(!wrongQuestion.equalsIgnoreCase(shapeName[screenCounter][6]))
				   {
					   //sb.append("\n");
					   wrongQuestion=shapeName[screenCounter][6];
					   sb.append(shapeName[screenCounter][6]+"-"+shapeName[screenCounter][source.getId()]); 
				   }
			   }
		   }
		   
		   if(target.getId()==3&&screenCounter>4)
		   {
			   if(source.getTag().equals(gameBoard[screenCounter][6]))
			   {
			    //
				if(wrongQuestion!=null&&wrongQuestion.equalsIgnoreCase(shapeName[screenCounter][6]))
				{
				sb.append(","+shapeName[screenCounter][source.getId()]);
				wrongQuestion=null;
				}
				else
				sb.append(shapeName[screenCounter][6]+"-"+shapeName[screenCounter][source.getId()]);
				sb.append("\n");
				//				   
				   
			   ((ImageView)target).setImageResource(gameBoard[screenCounter][source.getId()]);
			   changeBorderColor();
			   snd.play(right);
			   screenCounter=screenCounter+1;
			   if(screenCounter<=9)
				 createNextScreen();
			   else
			   {
			      //Update score
				  if(sb.toString().length()==0)
				  sb.append("No mistakes");
				  savescore(sb.toString());
				  //saveTotalWrong(String.valueOf(totalWrongCounter));
				  gameEnd();
			    }
			   }
			   else
			   {
			       ((ImageView)target).startAnimation(animAlpha);
			       snd.play(wrong);
			       totalWrongCounter=totalWrongCounter+1;
			   
			       if(wrongQuestion==null)
				   {
					  wrongQuestion=shapeName[screenCounter][6];
					  sb.append(shapeName[screenCounter][6]+"-"+shapeName[screenCounter][source.getId()]);
				   }
				   else if(wrongQuestion.equalsIgnoreCase(shapeName[screenCounter][6]))
				   {
					   sb.append(","+shapeName[screenCounter][source.getId()]);
				   }
				   else if(!wrongQuestion.equalsIgnoreCase(shapeName[screenCounter][6]))
				   {
					   //sb.append("\n");
					   wrongQuestion=shapeName[screenCounter][6];
					   sb.append(shapeName[screenCounter][6]+"-"+shapeName[screenCounter][source.getId()]); 
				   }
			   }
		   }
	    return true;
	   case DragEvent.ACTION_DRAG_ENDED:
	    return true;
	  
	   default:
	    return false;
	   }
	  }
	 }
	
	/****************************************************create gridview with timer******************************************************/
	@SuppressLint("NewApi")
	public void createNextScreen()
	{   
		mButtons=new ArrayList<ImageView>();
		ImageView button= null;
 	    for (int i=0; i<10; i++) 
 	    {
 	     if(i<6)
 	 	 {
 	 	 button =new ImageView(this);
 	 	 button.setId(i);
 	 	 button.setTag(gameBoard[screenCounter][i]);
 	 	 button.setImageResource(gameBoard[screenCounter][i]);
 	 	 button.setBackgroundResource(R.drawable.shape_border1);
 	 	 button.setOnDragListener(myDragEventListener);
 	 	 }
 	     else if(i==6)
 	     {
 	     button =new ImageView(this);
 	 	 button.setId(i);
 	 	 button.setTag(gameBoard[screenCounter][i]);
 	 	 button.setBackgroundColor(Color.TRANSPARENT);
 	     }
 	     else
 	     {
 	     button =new ImageView(this);
 	     button.setId(i);
 	     button.setTag(gameBoard[screenCounter][i]);
 	     button.setImageResource(gameBoard[screenCounter][i]);
 	     button.setOnTouchListener(new MyTouchListener());
		 button.setOnDragListener(myDragEventListener);
 	     }
 	     mButtons.add(button);
 	     }
 	    
 	    int DELAY = 1000;
	    Handler handler = new Handler();
	    handler.postDelayed(new Runnable() 
	    {            
	        public void run() 
	        {
	        	gridView = (GridView) findViewById(R.id.gridview2);
	    	    gridView.setAdapter(new CustomAdapter(mButtons,cropSize));
	            CustomAdapter ca=new CustomAdapter(mButtons,cropSize);
	            ca.notifyDataSetChanged();
	    	    gridView.setLayoutAnimation(layoutcontroller);
	        }
	    }, DELAY);
	}
	
/***************************************************************Change Color****************************************/
	
	public void changeBorderColor()
	{
		for(int i=0;i<6;i++)
		   {
			   ImageView image=(ImageView) gridView.getItemAtPosition(i);
			   ((ImageView)image).setBackgroundResource(R.drawable.shape_border2);
		   }
	}
	/***************************************************************Game Over*********************************************************************/
	public void gameEnd()
	{
		int DELAY = 1000;
	    Handler handler = new Handler();
	    handler.postDelayed(new Runnable() 
	    {            
	        public void run() 
	        {
	        	startActivity(gameover);
				finish();
	        }
	    }, DELAY);
	}
	
	public void savescore(String data)
    {
    	int id;
    	SharedPreferences sharedPref= getSharedPreferences("mypref", 0);
    	String playerName= sharedPref.getString("playerName", null);
    	id= sharedPref.getInt("playerID",0);
    	//id=Integer.parseInt(playerID);

    	if(playerName!=null&&playerName.length()>0)
    	{
    		mDbHelper.createDatabase();       
    		mDbHelper.open(); 
    		mDbHelper.updateLevel(id, data,"level6"); 
    		mDbHelper.close();
    	}
    }
	
	/*public void saveTotalWrong(String data)
    {
    	int id;
    	SharedPreferences sharedPref= getSharedPreferences("mypref", 0);
    	String playerName= sharedPref.getString("playerName", null);
    	id= sharedPref.getInt("playerID",0);
    	//id=Integer.parseInt(playerID);

    	if(playerName!=null&&playerName.length()>0)
    	{
    		mDbHelper.createDatabase();       
    		mDbHelper.open(); 
    		mDbHelper.updateLevel(id, data,"level7"); 
    		mDbHelper.close();
    	}
    }*/
}

