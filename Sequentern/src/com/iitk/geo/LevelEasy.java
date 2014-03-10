package com.iitk.geo;
import java.util.ArrayList;

import com.iitk.database.TestAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
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
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

public class LevelEasy extends Activity
{
	StringBuilder sb = new StringBuilder();
	TestAdapter mDbHelper;
	String wrongQuestion=null;
	
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
	{R.drawable.circle, R.drawable.square,R.drawable.circle, R.drawable.next, R.drawable.square,R.drawable.circle, R.drawable.square},
	{R.drawable.square, R.drawable.triangle,R.drawable.square, R.drawable.next, R.drawable.triangle,R.drawable.square, R.drawable.triangle},
	{R.drawable.trepazium, R.drawable.circle,R.drawable.trepazium, R.drawable.next, R.drawable.circle,R.drawable.trepazium, R.drawable.circle},
	{R.drawable.rectangle, R.drawable.trepazium,R.drawable.rectangle, R.drawable.next, R.drawable.trepazium,R.drawable.rectangle, R.drawable.trepazium},
	{R.drawable.diamond, R.drawable.triangle,R.drawable.diamond, R.drawable.next, R.drawable.triangle,R.drawable.diamond, R.drawable.triangle},
			
	{R.drawable.square, R.drawable.diamond,R.drawable.next, R.drawable.diamond, R.drawable.square,R.drawable.square, R.drawable.diamond},
	{R.drawable.circle,R.drawable.rectangle,R.drawable.next,R.drawable.rectangle,R.drawable.circle,R.drawable.circle,R.drawable.rectangle},
	{R.drawable.triangle,R.drawable.rectangle,R.drawable.next,R.drawable.rectangle,R.drawable.triangle,R.drawable.triangle,R.drawable.rectangle},
	{R.drawable.rhombus,R.drawable.triangle,R.drawable.next,R.drawable.triangle,R.drawable.rhombus,R.drawable.rhombus,R.drawable.triangle},
	{R.drawable.diamond,R.drawable.trepazium,R.drawable.next,R.drawable.trepazium,R.drawable.diamond,R.drawable.diamond,R.drawable.trepazium},
			
	{R.drawable.circle,R.drawable.next,R.drawable.circle,R.drawable.diamond,R.drawable.diamond,R.drawable.circle,R.drawable.diamond},
	{R.drawable.diamond,R.drawable.next,R.drawable.diamond,R.drawable.circle,R.drawable.circle,R.drawable.diamond,R.drawable.circle},
	{R.drawable.rectangle,R.drawable.square,R.drawable.next,R.drawable.square,R.drawable.rectangle,R.drawable.square,R.drawable.rectangle},
	{R.drawable.rhombus,R.drawable.square,R.drawable.next,R.drawable.square,R.drawable.rhombus,R.drawable.square,R.drawable.rhombus},
	{R.drawable.diamond,R.drawable.rhombus,R.drawable.next,R.drawable.rhombus,R.drawable.diamond,R.drawable.diamond,R.drawable.rhombus},
			
	{R.drawable.star,R.drawable.triangle2,R.drawable.next,R.drawable.triangle2, R.drawable.star,R.drawable.triangle2,R.drawable.star},
	{R.drawable.heart,R.drawable.pentagon,R.drawable.next,R.drawable.pentagon, R.drawable.heart,R.drawable.heart,R.drawable.pentagon},
	{R.drawable.star,R.drawable.heart,R.drawable.next,R.drawable.heart, R.drawable.star,R.drawable.heart,R.drawable.star}
	};
	
	public String[][] shapeName={
	{"Circle","Square","Circle","Next","Square","Circle","Square"},
	{"Square","Triangle","Square","Next","Triangle","Square","Triangle"},
	{"Trapezium","Circle","Trapezium","Next","Circle","Trapezium","Circle"},
	{"Rectangle","Trapezium","Rectangle","Next","Trapezium","Rectangle","Trapezium"},
	{"Diamond","Triangle","Diamond","Next","Triangle","Diamond","Triangle"},
							
	{"Square","Diamond","Next","Diamond","Square","Square","Diamond"},
	{"Circle","Rectangle","Next","Rectangle","Circle","Circle","Rectangle"},
	{"Triangle","Rectangle","Next","Rectangle","Triangle","Triangle","Rectangle"},
	{"Rhombus","Triangle","Next","Triangle","Rhombus","Rhombus","Triangle"},
	{"Diamond","Trapezium","Next","Trapezium","Diamond","Diamond","Trapezium"},
					
	{"Circle","Next","Circle","Diamond","Diamond","Circle","Diamond"},
	{"Diamond","Next","Diamond","Circle","Circle","Diamond","Circle"},
	{"Rectangle","Square","Next","Square","Rectangle","Square","Rectangle"},
	{"Rhombus","Square","Next","Square","Rhombus","Square","Rhombus"},
	{"Diamond","Rhombus","Next","Rhombus","Diamond","Diamond","Rhombus"},
					
	{"Star","Triangle","Next","Triangle","Star","Triangle","Star"},
	{"Heart","Pentagon","Next","Pentagon","Heart","Heart","Pentagon"},
	{"Star","Heart","Next","Heart","Star","Heart","Star"},
	};
	
    @SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.level_easy);
        
        mDbHelper = new TestAdapter(this);
        
        cropSize= (int) getResources().getDimension(R.dimen.crop_size);
        System.out.println("Crop Size="+cropSize);
        
        snd = new SoundManager(getApplicationContext());
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        right=snd.load(R.raw.right);
        wrong=snd.load(R.raw.wrong);
        
        mediaPlayer = MediaPlayer.create(LevelEasy.this, R.raw.instruction4);//play on load
        mediaPlayer.start();//play on load
        
        Animation androidAnimation = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        animAlpha=AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
    	layoutcontroller = new LayoutAnimationController(androidAnimation);
    	mButtons=new ArrayList<ImageView>();
    	ImageView button=null;
 	    for (int i=0; i<7; i++) 
 	    {
 	     if(i<4)
 	 	 {
 	 	 button =new ImageView(this);
 	 	 button.setId(i);
 	 	 button.setTag(gameBoard[screenCounter][i]);
 	 	 button.setImageResource(gameBoard[screenCounter][i]);
 	 	 button.setBackgroundResource(R.drawable.shape_border1);
 	 	 button.setOnDragListener(myDragEventListener);
 	 	 }
 	     else if(i==4)
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
 	     gridView = (GridView) findViewById(R.id.gridview1);
 	     gridView.setAdapter(new CustomAdapter(mButtons,cropSize));
 	     gridView.setLayoutAnimation(layoutcontroller);
 	     
 	    Button back=(Button)findViewById(R.id.back);
        //Button next=(Button)findViewById(R.id.next);
        
        //intent =new Intent(this,SelectLevel.class);
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
	   @SuppressLint("NewApi")
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
		   if(target.getId()==3&&screenCounter<5)
		   {
			   if(source.getTag().equals(gameBoard[screenCounter][4]))
			   {
			   //
				if(wrongQuestion!=null&&wrongQuestion.equalsIgnoreCase(shapeName[screenCounter][4]))
				{
				sb.append(","+shapeName[screenCounter][source.getId()]);
				wrongQuestion=null;
				}
				else
				sb.append(shapeName[screenCounter][4]+"-"+shapeName[screenCounter][source.getId()]);
				sb.append("\n");
			   //	
			
			   ((ImageView)target).setImageResource(gameBoard[screenCounter][source.getId()]);
			   changeBorderColor();
			   snd.play(right);
			   screenCounter=screenCounter+1;
			   if(screenCounter<=17)
				   createNextScreen();
				   else
				   {
				    //
					if(sb.toString().length()==0)
					sb.append("No mistakes");
					savescore(sb.toString());
					//
				    gameEnd();
				   }
			   }
			   else
			   {
			   ((ImageView)target).startAnimation(animAlpha);
			   snd.play(wrong);
			   
			   if(wrongQuestion==null)
			   {
				  wrongQuestion=shapeName[screenCounter][4];
				  sb.append(shapeName[screenCounter][4]+"-"+shapeName[screenCounter][source.getId()]);
			   }
			   else if(wrongQuestion.equalsIgnoreCase(shapeName[screenCounter][4]))
			   {
				   sb.append(","+shapeName[screenCounter][source.getId()]);
			   }
			   else if(!wrongQuestion.equalsIgnoreCase(shapeName[screenCounter][4]))
			   {
				   //sb.append("\n");
				   wrongQuestion=shapeName[screenCounter][4];
				   sb.append(shapeName[screenCounter][4]+"-"+shapeName[screenCounter][source.getId()]); 
			   }
			   }
		   }
		   else if(target.getId()==2&&screenCounter>=5&&screenCounter<=9)
		   {
			   if(source.getTag().equals(gameBoard[screenCounter][4]))
			   {
			   //
				if(wrongQuestion!=null&&wrongQuestion.equalsIgnoreCase(shapeName[screenCounter][4]))
				{
				sb.append(","+shapeName[screenCounter][source.getId()]);
				wrongQuestion=null;
				}
				else
				sb.append(shapeName[screenCounter][4]+"-"+shapeName[screenCounter][source.getId()]);
				sb.append("\n");
				//	
		   
				   
			   ((ImageView)target).setImageResource(gameBoard[screenCounter][source.getId()]);
			   snd.play(right);
			   changeBorderColor();
			   screenCounter=screenCounter+1;
			   if(screenCounter<=17)
				   createNextScreen();
				   else
				   {
					   //
					     if(sb.toString().length()==0)
					     sb.append("No mistakes");
					     savescore(sb.toString());
					     //
				   gameEnd();
				   }
			   }
			   else
			   {
			   ((ImageView)target).startAnimation(animAlpha);
			   snd.play(wrong);
			   
			   if(wrongQuestion==null)
			   {
				  wrongQuestion=shapeName[screenCounter][4];
				  sb.append(shapeName[screenCounter][4]+"-"+shapeName[screenCounter][source.getId()]);
			   }
			   else if(wrongQuestion.equalsIgnoreCase(shapeName[screenCounter][4]))
			   {
				   sb.append(","+shapeName[screenCounter][source.getId()]);
			   }
			   else if(!wrongQuestion.equalsIgnoreCase(shapeName[screenCounter][4]))
			   {
				   //sb.append("\n");
				   wrongQuestion=shapeName[screenCounter][4];
				   sb.append(shapeName[screenCounter][4]+"-"+shapeName[screenCounter][source.getId()]); 
			   }
			   }
		   }
		   else if(target.getId()==1&&screenCounter>9&&screenCounter<=11)
		   {
			   if(source.getTag().equals(gameBoard[screenCounter][4]))
			   {
			   //
				if(wrongQuestion!=null&&wrongQuestion.equalsIgnoreCase(shapeName[screenCounter][4]))
				{
				sb.append(","+shapeName[screenCounter][source.getId()]);
				wrongQuestion=null;
				}
				else
				sb.append(shapeName[screenCounter][4]+"-"+shapeName[screenCounter][source.getId()]);
				sb.append("\n");
				//	

				   
			   ((ImageView)target).setImageResource(gameBoard[screenCounter][source.getId()]);
			   snd.play(right);
			   changeBorderColor();
			   screenCounter=screenCounter+1;
			   if(screenCounter<=17)
			   createNextScreen();
			   else
			   {
				//
				 if(sb.toString().length()==0)
				 sb.append("No mistakes");
				 savescore(sb.toString());
				//
			   gameEnd();
			   }
			   }
			   else
			   {
			   ((ImageView)target).startAnimation(animAlpha);
			   snd.play(wrong);
			   
			   if(wrongQuestion==null)
			   {
				  wrongQuestion=shapeName[screenCounter][4];
				  sb.append(shapeName[screenCounter][4]+"-"+shapeName[screenCounter][source.getId()]);
			   }
			   else if(wrongQuestion.equalsIgnoreCase(shapeName[screenCounter][4]))
			   {
				   sb.append(","+shapeName[screenCounter][source.getId()]);
			   }
			   else if(!wrongQuestion.equalsIgnoreCase(shapeName[screenCounter][4]))
			   {
				   //sb.append("\n");
				   wrongQuestion=shapeName[screenCounter][4];
				   sb.append(shapeName[screenCounter][4]+"-"+shapeName[screenCounter][source.getId()]); 
			   }
			   }
		   }
		   else if(target.getId()==2&&screenCounter>=12&&screenCounter<=17)
		   {
			   if(source.getTag().equals(gameBoard[screenCounter][4]))
			   {
			    //
				if(wrongQuestion!=null&&wrongQuestion.equalsIgnoreCase(shapeName[screenCounter][4]))
				{
				sb.append(","+shapeName[screenCounter][source.getId()]);
				wrongQuestion=null;
				}
				else
				sb.append(shapeName[screenCounter][4]+"-"+shapeName[screenCounter][source.getId()]);
				sb.append("\n");
				//	
	   
			   ((ImageView)target).setImageResource(gameBoard[screenCounter][source.getId()]);
			   snd.play(right);
			   changeBorderColor();
			   screenCounter=screenCounter+1;
			   if(screenCounter<=17)
				   createNextScreen();
				   else
				   {
					   //
					     if(sb.toString().length()==0)
					     sb.append("No mistakes");
					     savescore(sb.toString());
					     //
				   gameEnd();
				   }
			   }
			   else
			   {
			   ((ImageView)target).startAnimation(animAlpha);
			   snd.play(wrong);
			   
			   if(wrongQuestion==null)
			   {
				  wrongQuestion=shapeName[screenCounter][4];
				  sb.append(shapeName[screenCounter][4]+"-"+shapeName[screenCounter][source.getId()]);
			   }
			   else if(wrongQuestion.equalsIgnoreCase(shapeName[screenCounter][4]))
			   {
				   sb.append(","+shapeName[screenCounter][source.getId()]);
			   }
			   else if(!wrongQuestion.equalsIgnoreCase(shapeName[screenCounter][4]))
			   {
				   //sb.append("\n");
				   wrongQuestion=shapeName[screenCounter][4];
				   sb.append(shapeName[screenCounter][4]+"-"+shapeName[screenCounter][source.getId()]); 
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
    	ImageView button=null;
 	    for (int i=0; i<7; i++) 
 	    {
 	     if(i<4)
 	 	 {
 	 	 button =new ImageView(this);
 	 	 button.setId(i);
 	 	 button.setTag(gameBoard[screenCounter][i]);
 	 	 button.setImageResource(gameBoard[screenCounter][i]);
 	 	 button.setBackgroundResource(R.drawable.shape_border1);
 	 	 button.setOnDragListener(myDragEventListener);
 	 	 }
 	     else if(i==4)
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
	        	gridView = (GridView) findViewById(R.id.gridview1);
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
		for(int i=0;i<4;i++)
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
	
	
	public class ImageAdapter extends BaseAdapter
	{
	private Context mContext;

	public ImageAdapter(Context c)
	{
	mContext = c;
	}
    public int getCount()
	{
    return 8;
    }
    public Object getItem(int position)
	{
	return null;
	}
	public long getItemId(int position)
	{
	return 0;
    }
	// create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent)
	{
	ImageView imageView;
    if (convertView == null)
	{
	imageView = new ImageView(mContext);
	imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
	imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	imageView.setPadding(8, 8, 8, 8);
	}
	else
	{
	imageView = (ImageView) convertView;
    }
	imageView.setImageResource(R.drawable.circle);	                                               
	return imageView;
    }
	}
	
	public void savescore(String data)
    {
    	int id;
    	SharedPreferences sharedPref= getSharedPreferences("mypref", 0);
    	String playerName= sharedPref.getString("playerName", null);
    	id= sharedPref.getInt("playerID",0);
    	//id=Integer.parseInt(playerID);
    	
    	System.out.println("Player Name at Level1:="+playerName);
    	System.out.println("Player Id="+id);
    	if(playerName!=null&&playerName.length()>0)
    	{
    		mDbHelper.createDatabase();       
    		mDbHelper.open(); 
    		mDbHelper.updateLevel(id, data,"level4"); 
    		mDbHelper.close();
    	}
    }
}

