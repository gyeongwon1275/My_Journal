
package com.example.my_journal.Chart;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.my_journal.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Example of a heavily customized {@link LineChart} with limit lines, custom line shapes, etc.
 *
 * @version 3.1.0
 * @since 1.7.4
 */
public class Line_chart_activity extends AppCompatActivity {

    private LineChart chart;

    private static final String PAUSE = "pause_save";


    private ArrayList<String> date_xAxis = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        setTitle("기분변화 그래프");

        // x축에 날짜를 표시하기 위한 메서드
        setDate();


        {
            chart = findViewById(R.id.mood_chart);
            chart.setDescription(null);
            // 배경색 설정
            chart.setBackgroundColor(Color.WHITE);

            // 터치설정
            chart.setTouchEnabled(true);

            // 배경에 격자무늬 그리기 설정
            chart.setDrawGridBackground(false);

            chart.setDragEnabled(true);

            chart.setScaleEnabled(true);

            chart.setScaleXEnabled(true);

            chart.setScaleYEnabled(true);
            // 손가락으로 펼처서 확대설정
            chart.setPinchZoom(true);
        }

        // x축에 설정한 날짜를 그래프에 반영
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return date_xAxis.get((int) value);
            }
        };


        XAxis xAxis;

        {
            xAxis = chart.getXAxis();
            // x축 최소간격 Af = 최소간격 A 씩 변화
            xAxis.setGranularity(1f);
            // x축에 설정한 날짜를 그래프에 반영
            xAxis.setValueFormatter(formatter);

        }

        YAxis yAxis;
        {
            yAxis = chart.getAxisLeft();

            // 우측에 y값 표시 설정
            chart.getAxisRight().setEnabled(false);


            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // y 축 범위
            yAxis.setAxisMaximum(6f);
            yAxis.setAxisMinimum(0f);
        }


        {   // // Create Limit Lines // //
            LimitLine llXAxis = new LimitLine(9f, "Index 10");
            llXAxis.setLineWidth(4f);
            llXAxis.enableDashedLine(10f, 10f, 0f);
            llXAxis.setLabelPosition(LimitLabelPosition.RIGHT_BOTTOM);
            llXAxis.setTextSize(10f);


            LimitLine ll1 = new LimitLine(7f, "Upper Limit");
            ll1.setLineWidth(4f);
            ll1.enableDashedLine(10f, 10f, 0f);
            ll1.setLabelPosition(LimitLabelPosition.RIGHT_TOP);
            ll1.setTextSize(10f);


            LimitLine ll2 = new LimitLine(-1f, "Lower Limit");
            ll2.setLineWidth(4f);
            ll2.enableDashedLine(10f, 10f, 0f);
            ll2.setLabelPosition(LimitLabelPosition.RIGHT_BOTTOM);
            ll2.setTextSize(10f);


            // draw limit lines behind data instead of on top
            yAxis.setDrawLimitLinesBehindData(true);
            xAxis.setDrawLimitLinesBehindData(true);

            // add limit lines
            yAxis.addLimitLine(ll1);
            yAxis.addLimitLine(ll2);
            xAxis.addLimitLine(llXAxis);
        }

        // 데이터 설정
        setData();

        // draw points over time
        chart.animateX(1500);

        // 범례설정
        Legend legend = chart.getLegend();
        legend.setTextSize(30);

        legend.setForm(LegendForm.CIRCLE);
    }

    private void setData() {

        ArrayList<Entry> values = new ArrayList<>();

        ArrayList<String> list_date = new ArrayList<>();
        SharedPreferences preferences = getSharedPreferences(PAUSE, MODE_PRIVATE);
        String pause_save = preferences.getString(PAUSE, "");

        try {

            // Preference 에서 받은 JSON Array String data 를 JSONArray 에 넣는다.

            // 수정 -> modify_save
            // 추가 -> save_json

            JSONArray load_journal_json = new JSONArray(pause_save);


            for (int i = 0; i < load_journal_json.length(); i++) {
                JSONObject jsonObject = load_journal_json.getJSONObject(i);


                String date = jsonObject.getString("journal_date");
                String mood = jsonObject.getString("journal_mood");
                int mood_point = Integer.parseInt(mood);
                list_date.add(i, date);
                values.add(new Entry(i, mood_point));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        LineDataSet dataSet;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            dataSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
            dataSet.setValues(values);
            dataSet.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            dataSet = new LineDataSet(values, "기분점수");

            dataSet.setDrawIcons(false);

            // draw dashed line
            dataSet.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            dataSet.setColor(Color.BLACK);
            dataSet.setCircleColor(Color.BLACK);

            // line thickness and point size
            dataSet.setLineWidth(1f);
            dataSet.setCircleRadius(3f);

            // draw points as solid circles
            dataSet.setDrawCircleHole(false);

            // customize legend entry
            dataSet.setFormLineWidth(1f);
            dataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            dataSet.setFormSize(15.f);

            // text size of values
            dataSet.setValueTextSize(9f);

            // draw selection line as dashed
            dataSet.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            dataSet.setDrawFilled(true);
            dataSet.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area

            // drawables only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
            dataSet.setFillDrawable(drawable);


            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);
        }
    }


    public void setDate() {

        SharedPreferences preferences = getSharedPreferences(PAUSE, MODE_PRIVATE);
        String pause_save = preferences.getString(PAUSE, "");


        try {

            // Preference 에서 받은 JSON Array String data 를 JSONArray 에 넣는다.

            // 수정 -> modify_save
            // 추가 -> save_json

            JSONArray load_journal_json = new JSONArray(pause_save);


            for (int i = 0; i < load_journal_json.length(); i++) {

                JSONObject jsonObject = load_journal_json.getJSONObject(i);
                String date = jsonObject.getString("journal_date");
                date_xAxis.add(i, date);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<String> month_day = new ArrayList<>();
        String replace_text;

        for (int i = 0; i < date_xAxis.size(); i++) {
            replace_text = date_xAxis.get(i);
            replace_text = replace_text.replace("2020-", "");
            month_day.add(i, replace_text);

            Log.i("replace_test", replace_text);

        }

        for (int i = 0; i < date_xAxis.size(); i++) {
            date_xAxis.set(i, month_day.get(i));

        }

    }
}
