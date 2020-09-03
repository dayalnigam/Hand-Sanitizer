package com.example.home_sanitizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
/* 
Dayal Nigam DEi(Internship project) Home made Sanitizer using IOT
*/  
public class Sanitizer2 extends AppCompatActivity {
    TextView a,b,c;
    DatabaseReference Ref;
    private LineChart lineChart;
    ArrayList<Entry> yData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanitizer2); a=(TextView)findViewById(R.id.mytext1);
        b=(TextView)findViewById(R.id.mytext2);
        c=(TextView)findViewById(R.id.mytext4);



        lineChart = (LineChart) findViewById(R.id.linechart);
        Ref = FirebaseDatabase.getInstance().getReference().child("Sanitizer2");
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a");
                String dateTime = simpleDateFormat.format(calendar.getTime());
                float i =0;
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    i=i+1;
                    String SV = ds.child("BatteryPercentage").getValue().toString();
                    Float SensorValue = Float.parseFloat(SV);
                    a.setText("Battery percentage ::"+SensorValue+" "+"%");

                }
                yData = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    i=i+1;
                    String SV1 = ds.child("LevelOfSanitizer").getValue().toString();
                    Float SensorValue1 = Float.parseFloat(SV1);
                    b.setText("Level Of Sanitizer ::"+SensorValue1+" "+"ml");
                    yData.add(new Entry(i,SensorValue1));

                }
                final LineDataSet set1 = new LineDataSet(yData,"Sanitizer2"+" "+dateTime);

                set1.setDrawIcons(true);
                set1.enableDashedLine(10f, 5f, 0f);
                set1.enableDashedHighlightLine(19f, 5f, 0f);
                set1.setColor(Color.BLACK);
                set1.setCircleColor(Color.CYAN);
                set1.setLineWidth(2f);
                set1.setCircleRadius(4f);
                set1.setDrawCircleHole(false);
                set1.setValueTextSize(10f);
                set1.setDrawFilled(true);
                set1.setFormLineWidth(1f);

                LineData data = new LineData(set1);
                LineData data2 = new LineData(set1);
                lineChart.setData(data);
                lineChart.setData(data2);
                lineChart.notifyDataSetChanged();
                lineChart.invalidate();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    i=i+1;
                    String SV2 = ds.child("TotalNumberOfCountsSanitizerUse").getValue().toString();
                    Float SensorValue2 = Float.parseFloat(SV2);
                    c.setText("Number Of Counts Sanitizer Use::"+SensorValue2+" ");


                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Sanitizer2.this, "Fail to load post", Toast.LENGTH_SHORT).show();


            }
        });





    }


}
