package com.iitk.geo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
public class ShapeToName extends Activity
{
	StringBuilder sb = new StringBuilder();
	TestAdapter mDbHelper;
	String wrongQuestion=null;
	
	SoundManager snd;
	int right,wrong;
	MediaPlayer mediaPlayer;
	
	TableRow tableRow;
	TextView second,target;
	ImageView first;
	Button image1,image2,image3,image4,image5,image6,image7,image8,image9,image10;
	Integer[] playShapes;
	LayoutAnimationController layoutcontroller;
	Animation animAlpha;
	int screenCounter=0,sequenceNumber=0;
	Intent intent;
	Intent gameover;
	Button source;
	MyDragEventListener myDragEventListener = new MyDragEventListener();
	public String[] shapeName={"Circle","Oval","Diamond","Hexagon","Pentagon","Rectangle","Rhombus","Square","Trapezium","Triangle"};
	public Integer[] gameBoard={R.drawable.circle,R.drawable.oval,R.drawable.diamond,R.drawable.hexagon,R.drawable.pentagon,R.drawable.rectangle,R.drawable.rhombus,R.drawable.square,R.drawable.trepazium,R.drawable.triangle};
    @SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.shape_to_name_match);
        
        mDbHelper = new TestAdapter(this);
        
        snd = new SoundManager(getApplicationContext());
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        right=snd.load(R.raw.right);
        wrong=snd.load(R.raw.wrong);
        
        mediaPlayer = MediaPlayer.create(ShapeToName.this, R.raw.instruction3);//play on load
        mediaPlayer.start();//play on load
        
        tableRow=(TableRow) findViewById(R.id.tableRow2);
        playShapes=randomShapes();   
        Animation androidAnimation = AnimationUtils.loadAnimation(this,R.anim.translate);
        animAlpha=AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
    	layoutcontroller = new LayoutAnimationController(androidAnimation);
    	
 	    image1=(Button) findViewById(R.id.imageView1);
 	    image1.setOnTouchListener(new MyTouchListener());
 	    image1.setOnDragListener(myDragEventListener);
 	    
 	    image2=(Button) findViewById(R.id.imageView2);
	    image2.setOnTouchListener(new MyTouchListener());
	    image2.setOnDragListener(myDragEventListener);
	    
	    image3=(Button) findViewById(R.id.imageView3);
	    image3.setOnTouchListener(new MyTouchListener());
	    image3.setOnDragListener(myDragEventListener);
	    
	    image4=(Button) findViewById(R.id.imageView4);
	    image4.setOnTouchListener(new MyTouchListener());
	    image4.setOnDragListener(myDragEventListener);
	    
	    image5=(Button) findViewById(R.id.imageView5);
	    image5.setOnTouchListener(new MyTouchListener());
	    image5.setOnDragListener(myDragEventListener);
	    createNextScreen();
       
        intent =new Intent(this,GeometryActivity.class);
        gameover =new Intent(this,GameOver.class);
        
        Button back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new Button.OnClickListener()
        {
			public void onClick(View v) 
			{
					finish();
		    }
	    });
    }
	private final class MyTouchListener implements OnTouchListener 
	{
	    @SuppressLint("NewApi")
		public boolean onTouch(View view, MotionEvent motionEvent) 
	    {
	    	if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				source=(Button)view;
	            ClipData clipData = ClipData.newPlainText("","");
	            View.DragShadowBuilder dsb = new View.DragShadowBuilder(view);
	            view.startDrag(clipData, dsb, view, 0);
	            //ca.notifyDataSetChanged();
	            return true;
	        } else
	            return false;
	    }
	 }
	@SuppressLint("NewApi")
	private static class MyDragShadowBuilder extends View.DragShadowBuilder 
	{
	    private static Drawable shadow;
	    public MyDragShadowBuilder(View v) 
	    {
	      super(v);
	      shadow = new ColorDrawable(Color.LTGRAY);
	    }
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
	   target=(TextView) v;
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
		   if(target.getTag().equals(11))
		   {
			   if(source.getTag().equals(first.getTag()))
			   {
			   //
			   if(wrongQuestion!=null&&wrongQuestion.equalsIgnoreCase(shapeName[sequenceNumber]))
				sb.append(","+shapeName[source.getId()]);
			   else
				sb.append(shapeName[sequenceNumber]+"-"+shapeName[source.getId()]);
				sb.append("\n");
			   //	
					
			   second.setBackgroundResource(R.drawable.shape_border2);
			   second.setText(shapeName[sequenceNumber]);
			   first.setBackgroundResource(R.drawable.shape_border2);
			   snd.play(right);
			   screenCounter=screenCounter+1;
			   if(screenCounter<=9)
				 createNextScreen();
				 else{
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
			   second.startAnimation(animAlpha);
			   snd.play(wrong);
			   
			   if(wrongQuestion==null){
				  wrongQuestion=shapeName[sequenceNumber];
				  sb.append(shapeName[sequenceNumber]+"-"+shapeName[source.getId()]);
			   }
			   else if(wrongQuestion.equalsIgnoreCase(shapeName[sequenceNumber])){
				   sb.append(","+shapeName[source.getId()]);
			   }
			   else if(!wrongQuestion.equalsIgnoreCase(shapeName[sequenceNumber])){
				   //sb.append("\n");
				   wrongQuestion=shapeName[sequenceNumber];
				   sb.append(shapeName[sequenceNumber]+"-"+shapeName[source.getId()]);  
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
	public void createNextScreen()
	{   
		sequenceNumber=playShapes[screenCounter];  
		if(screenCounter<5)
		{  
			image1.setId(0);
			image1.setTag(shapeName[0]);
			image1.setText(shapeName[0]);
			image1.setBackgroundResource(R.drawable.shape_border1);
			
			image2.setId(1);
			image2.setTag(shapeName[1]);
			image2.setText(shapeName[1]);
			image2.setBackgroundResource(R.drawable.shape_border1);
			
			image3.setId(2);
			image3.setTag(shapeName[2]);
			image3.setText(shapeName[2]);
			image3.setBackgroundResource(R.drawable.shape_border1);
			
			image4.setId(3);
			image4.setTag(shapeName[3]);
			image4.setText(shapeName[3]);
			image4.setBackgroundResource(R.drawable.shape_border1);
			
			image5.setId(4);
			image5.setTag(shapeName[4]);
			image5.setText(shapeName[4]);
			image5.setBackgroundResource(R.drawable.shape_border1);
		}
		else if(screenCounter>=5&&screenCounter<10)
		{
			image1.setId(5);
			image1.setTag(shapeName[5]);
			image1.setText(shapeName[5]);
			image1.setBackgroundResource(R.drawable.shape_border1);
			
			image2.setId(6);
			image2.setTag(shapeName[6]);
			image2.setText(shapeName[6]);
			image2.setBackgroundResource(R.drawable.shape_border1);
			
			image3.setId(7);
			image3.setTag(shapeName[7]);
			image3.setText(shapeName[7]);
			image3.setBackgroundResource(R.drawable.shape_border1);
			
			image4.setId(8);
			image4.setTag(shapeName[8]);
			image4.setText(shapeName[8]);
			image4.setBackgroundResource(R.drawable.shape_border1);
			
			image5.setId(9);
			image5.setTag(shapeName[9]);
			image5.setText(shapeName[9]);
			image5.setBackgroundResource(R.drawable.shape_border1);
		} 
 	    int DELAY = 1000;
	    Handler handler = new Handler();
	    handler.postDelayed(new Runnable() 
	    {            
	        @SuppressLint("NewApi")
			public void run() 
	        {
	        	//tableRow.invalidate();
	     	    //tableRow.refreshDrawableState();
	     	    Integer tno=11;
	     	    
	     	    
	        	first=(ImageView)findViewById(R.id.first);
	        	first.setBackgroundResource(R.drawable.shape_border1);
	     	    first.setTag(shapeName[sequenceNumber]);
	     	    first.setImageResource(gameBoard[sequenceNumber]);

	     	    second=(TextView)findViewById(R.id.second);
	     	    second.setBackgroundResource(R.drawable.next);
	     	    second.setTag(tno);
	     	    second.setText("");
	     	    second.setOnDragListener(myDragEventListener);
	     	    tableRow.setLayoutAnimation(layoutcontroller);
	        }
	    }, DELAY);
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
	/*************************************************************random numbers****************************************************************/
	public Integer[] randomShapes()
	{
		Random generator = new Random();
        ArrayList<Integer> finalScreenGameData=new ArrayList<Integer>();
        Integer randomList1[]={0,1,2,3,4};
        Integer randomList2[]={5,6,7,8,9};
       	List<Integer> intList1 = new ArrayList<Integer>(Arrays.asList(randomList1)); 
       	List<Integer> intList2 = new ArrayList<Integer>(Arrays.asList(randomList2));
        	for(int i=0;i<5;i++)
            {
         	int position = generator.nextInt(intList1.size());
         	finalScreenGameData.add(intList1.get(position));
         	intList1.remove(position);
            }
        	for(int i=0;i<5;i++)
            {
         	int position = generator.nextInt(intList2.size());
         	finalScreenGameData.add(intList2.get(position));
         	intList2.remove(position);
            }
        	Integer shapes[]=finalScreenGameData.toArray(new Integer[0]);
        	return shapes;
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
    		mDbHelper.updateLevel(id, data,"level3"); 
    		mDbHelper.close();
    	}
    }
}

