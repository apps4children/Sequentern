package com.iitk.geo;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
public class LearnShapes extends Activity implements OnClickListener{
	Button next;
	Button previous;
	ViewPager viewPager;
	private int focusedPage = 0;
	SoundManager snd;
	int circle,square,rectangle,triangle,diamond,oval,trapezium,rhombus,pentagon,hexagon,star,heart;
	MediaPlayer mediaPlayer;
	Timer timer;
	int delay = 1000;
	  @Override
	  public void onCreate(Bundle savedInstanceState) 
	  {
	    super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.display_shapes);
	    viewPager = (ViewPager) findViewById(R.id.view_pager);
	    ImageAdapter adapter = new ImageAdapter(this);
	    viewPager.setAdapter(adapter);
	    next = (Button) findViewById(R.id.next);
        previous = (Button) findViewById(R.id.back);
        
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        
        previous.setVisibility(View.GONE);   
        
        viewPager.setOnPageChangeListener(new MyPageChangeListener()); 
        
        //sound managing
        mediaPlayer = MediaPlayer.create(LearnShapes.this, R.raw.circle);//play on load
        timer();//play on load
        
        
        snd = new SoundManager(getApplicationContext());
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        circle=snd.load(R.raw.circle);
        square=snd.load(R.raw.square);
        rectangle=snd.load(R.raw.rectangle);
        triangle=snd.load(R.raw.triangle);
        diamond=snd.load(R.raw.diamond);
        oval=snd.load(R.raw.oval);
        trapezium=snd.load(R.raw.trapezium);
        rhombus=snd.load(R.raw.rhombus);
        pentagon=snd.load(R.raw.pentagon);
        hexagon=snd.load(R.raw.hexagon);
        star=snd.load(R.raw.star);
        heart=snd.load(R.raw.heart);    
        
        Button backtohome=(Button)findViewById(R.id.backtohome);
        backtohome.setOnClickListener(new Button.OnClickListener()
        {
			public void onClick(View v) 
			{
					finish();
		}});
     }
    public void onClick(View v){
    	if (v == next) {
    		viewPager.setCurrentItem(getItem(+1), true);
    	}
    	else if (v == previous) {
    		viewPager.setCurrentItem(getItem(-1), true);
    	}
    	hideAndShowbutton();
    }
    private int getItem(int i) {
        int a = viewPager.getCurrentItem();
        i += a;
        return i;
    }
    
    //hide and show previous and next button
    public void hideAndShowbutton()
    {
    	
    	int b=viewPager.getCurrentItem();
    	if(b>0)
    	{
    		previous.setVisibility(View.VISIBLE);
    	}
    	else if(b==0)
    	{
    		previous.setVisibility(View.GONE);
    	}
    	
    	if(b==11)
    	{
    		next.setVisibility(View.GONE);
    	}
    	else
    	{
    		next.setVisibility(View.VISIBLE);
    	}
    }       
    //current page detector
    private class MyPageChangeListener extends ViewPager.SimpleOnPageChangeListener 
    {
        @Override
        public void onPageSelected(int position) 
        {
        	playSounds(position);//play sounds
            focusedPage = position;
            System.out.println("SimpleOnPageChangeListener=:"+focusedPage);
            if(focusedPage>0)
        	{
        		previous.setVisibility(View.VISIBLE);
        	}
        	else if(focusedPage==0)
        	{
        		previous.setVisibility(View.GONE);
        	}
        	
        	if(focusedPage==11)
        	{
        		next.setVisibility(View.GONE);
        	}
        	else
        	{
        		next.setVisibility(View.VISIBLE);
        	}
        }
    }
    
    public void playSounds(int id)
	{
    	int position=id;
		if(position==0)
			snd.play(circle);
		else if(position==1)
			snd.play(square);
		else if(position==2)
			snd.play(rectangle);
		else if(position==3)
			snd.play(triangle);
		else if(position==4)
			snd.play(diamond);
		else if(position==5)
			snd.play(oval);
		else if(position==6)
			snd.play(trapezium);
		else if(position==7)
			snd.play(rhombus);
		else if(position==8)
			snd.play(pentagon);
		else if(position==9)
			snd.play(hexagon);
		else if(position==10)
			snd.play(star);
		else if(position==11)
			snd.play(heart);
	}
    public void timer()
	{
		timer= new Timer();
        timer.schedule( new TimerTask()
        {
           public void run() 
           { 
        	   mediaPlayer.start();
           }
         },delay);
	}
}

