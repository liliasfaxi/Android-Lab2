package tn.medtech.lab2;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class GradesActivity extends AppCompatActivity{

    ListView gradesList;
    AutoCompleteTextView students;
    HashMap<String,String[]> allGrades = new HashMap<>();



    String[] myStudents = {"Chayma","Fatma", "Omar", "Soumaya", "Souad"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        allGrades.put("Chayma", new String[]{"12.5", "4.75", "15", "10.25", "7.5", "16.75"});
        allGrades.put("Fatma", new String[]{"10", "14.5", "11", "5.5", "19", "12.75"});
        allGrades.put("Omar", new String[]{"2.5", "14.75", "10", "18.25", "6.75", "13.25"});
        allGrades.put("Soumaya", new String[]{"11", "8.5", "17", "11", "14.25", "12.5"});
        allGrades.put("Souad", new String[]{"15.5", "18.5", "7", "4.25", "12.75", "10.5"});


        gradesList = (ListView)findViewById(R.id.gradesList);


        students = (AutoCompleteTextView)findViewById(R.id.students);
        students.setAdapter(new ArrayAdapter<String>(
                this,android.R.layout.simple_dropdown_item_1line, myStudents));

        students.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedStudent = ((TextView)view).getText().toString();
                toast("selected: "+selectedStudent);
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(GradesActivity.this, android.R.layout.simple_list_item_1, allGrades.get(selectedStudent));

                MyLineAdapter adapter = new MyLineAdapter(GradesActivity.this,allGrades.get(selectedStudent));
                gradesList.setAdapter(adapter);
            }
        });





        gradesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String studentName = students.getText().toString();
                Float grade = Float.parseFloat(allGrades.get(studentName)[i]);
                if (grade>=10){
                    toast("Pass!");
                }else{
                    toast("Fail!");
                }
            }
        });


    }


    class MyLineAdapter extends ArrayAdapter<String>{
        Activity context;
        String[] items;

        MyLineAdapter(Activity context, String[] items){
            super(context,R.layout.line, items);
            this.context = context;
            this.items = items;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            ViewHolder holder;
            if (convertView == null){
                convertView = inflater.inflate(R.layout.line, null);

                holder = new ViewHolder();
                holder.label = (TextView) convertView.findViewById(R.id.grade);
                holder.image = (ImageView)convertView.findViewById(R.id.image);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            holder.label.setText(items[position]);
            float grade = Float.valueOf(items[position]);
            holder.image.setImageResource((grade >= 10)?R.drawable.ic_mood: R.drawable.ic_mood_bad);

            return convertView;
        }
    }

    static class ViewHolder{
        TextView label;
        ImageView image;
    }



    public void toast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}



