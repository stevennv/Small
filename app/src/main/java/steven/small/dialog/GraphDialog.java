package steven.small.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;
import steven.small.R;

/**
 * Created by Admin on 10/24/2017.
 */

public class GraphDialog extends Dialog {
    private PieChartView chart;
    private PieChartData data;
    private boolean hasLabels = false;
    private boolean hasLabelsOutside = false;
    private boolean hasCenterCircle = false;
    private boolean hasCenterText1 = false;
    private boolean hasCenterText2 = false;
    private boolean isExploded = false;
    private boolean hasLabelForSelected = false;

    public GraphDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_graph);
        iniUI();
    }

    protected void iniUI() {
        chart = findViewById(R.id.pieChartView);
        int numValues = 4;

        List<SliceValue> values = new ArrayList<SliceValue>();
//        for (int i = 0; i < numValues; ++i) {
//            SliceValue sliceValue = new SliceValue(25, ChartUtils.pickColor());
//            values.add(sliceValue);
//        }
        SliceValue value1= new SliceValue(23, Color.RED);
        SliceValue value2= new SliceValue(10, Color.GREEN);
        SliceValue value3= new SliceValue(40, Color.YELLOW);
        SliceValue value4= new SliceValue(27, Color.CYAN);
        values.add(value1);
        values.add(value2);
        values.add(value3);
        values.add(value4);
        data = new PieChartData(values);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);

        if (isExploded) {
            data.setSlicesSpacing(24);
        }

        if (hasCenterText1) {
            data.setCenterText1("Hello!");

            // Get roboto-italic font.
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Roboto-Italic.ttf");
            data.setCenterText1Typeface(tf);

            // Get font size from dimens.xml and convert it to sp(library uses sp values).
            data.setCenterText1FontSize(ChartUtils.px2sp(getContext().getResources().getDisplayMetrics().scaledDensity,
                    (int) getContext().getResources().getDimension(R.dimen.padding_20)));
        }

        if (hasCenterText2) {
            data.setCenterText2("Charts (Roboto Italic)");

            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Roboto-Italic.ttf");

            data.setCenterText2Typeface(tf);
            data.setCenterText2FontSize(ChartUtils.px2sp(getContext().getResources().getDisplayMetrics().scaledDensity,
                    (int) getContext().getResources().getDimension(R.dimen.padding_20)));
        }

        chart.setPieChartData(data);
    }
}
