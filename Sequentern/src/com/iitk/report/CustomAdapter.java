package com.iitk.report;
import java.util.ArrayList;
import com.iitk.database.Student;
import com.iitk.geo.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
	 
    private ArrayList<Student> student;
    Context c;
    
    CustomAdapter(ArrayList<Student> data1, Context c){
    	student = data1;
    }
    public int getCount() 
    {
        // TODO Auto-generated method stub
        return student.size();
    }
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return student.get(position);
    }
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
   
    public View getView(int position, View convertView, ViewGroup parent) 
    {
        final Student st= student.get(position);
        
         View v = convertView;

         if (v == null) 
         {
             LayoutInflater inflater = LayoutInflater.from(parent.getContext());
             v = inflater.inflate(R.layout.list_item_message, parent, false);
         }
 
           //ImageView image = (ImageView) v.findViewById(R.id.icon);
           TextView name = (TextView)v.findViewById(R.id.name);
           TextView date = (TextView)v.findViewById(R.id.date);
           TextView level1 = (TextView)v.findViewById(R.id.level1);
           TextView level2 = (TextView)v.findViewById(R.id.level2);
           TextView level3 = (TextView)v.findViewById(R.id.level3);
           TextView level4 = (TextView)v.findViewById(R.id.level4);
           TextView level5 = (TextView)v.findViewById(R.id.level5);
           TextView level6 = (TextView)v.findViewById(R.id.level6);
           TextView level1status = (TextView)v.findViewById(R.id.level1status);
           TextView level2status = (TextView)v.findViewById(R.id.level2status);
           TextView level3status = (TextView)v.findViewById(R.id.level3status);
           TextView level4status = (TextView)v.findViewById(R.id.level4status);
           TextView level5status = (TextView)v.findViewById(R.id.level5status);
           TextView level6status = (TextView)v.findViewById(R.id.level6status);
           //TextView level4 = (TextView)v.findViewById(R.id.level4);
           //TextView level5 = (TextView)v.findViewById(R.id.date);
           ImageView delete=(ImageView)v.findViewById(R.id.delete);
           delete.setTag(st);
 
           //image.setImageResource(msg.icon);
           name.setText(st.getName().toUpperCase());
           date.setText(st.getDate()); 
           level1.setText(st.getLevel1());
           level2.setText(st.getLevel2());
           level3.setText(st.getLevel3());
           level4.setText(st.getLevel4());
           level5.setText(st.getLevel5());
           level6.setText(st.getLevel6());
           if(st.getLevel1()==null||st.getLevel1().equals(""))
        	   level1status.setText("Not Played");
           else
        	   level1status.setText("Played");
           
           if(st.getLevel2()==null||st.getLevel2().equals(""))
        	   level2status.setText("Not Played");
           else
        	   level2status.setText("Played");
           
           if(st.getLevel3()==null||st.getLevel3().equals(""))
        	   level3status.setText("Not Played");
           else
        	   level3status.setText("Played");
           
           if(st.getLevel4()==null||st.getLevel4().equals(""))
        	   level4status.setText("Not Played");
           else
        	   level4status.setText("Played");
           
           if(st.getLevel5()==null||st.getLevel5().equals(""))
        	   level5status.setText("Not Played");
           else
        	   level5status.setText("Played");
           
           if(st.getLevel6()==null||st.getLevel6().equals(""))
        	   level6status.setText("Not Played");
           else
        	   level6status.setText("Played");
           
           //subView.setText("Subject: "+msg.sub);
           //descView.setText(msg.desc);                      
                        
        return v;
       }
    
    public void deleteRow(Student st) 
	{
		if(this.student.contains(st)) 
		{
			this.student.remove(st);
		}
	}
}