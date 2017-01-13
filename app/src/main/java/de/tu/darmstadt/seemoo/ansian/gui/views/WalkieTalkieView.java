package de.tu.darmstadt.seemoo.ansian.gui.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import de.tu.darmstadt.seemoo.ansian.R;
import de.tu.darmstadt.seemoo.ansian.model.preferences.Preferences;

/**
 * @author Matthias Kannwischer
 */

public class WalkieTalkieView extends LinearLayout {
    public static final String LOGTAG = "WalkieTalkieView";

    private int selectedFrequencyBand = 0;
    private boolean isTransmitting = false;
    private boolean isReceiving = false;

    public WalkieTalkieView(Context context) {
        this(context, null);
    }

    public WalkieTalkieView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WalkieTalkieView(Context context, AttributeSet attrs, int defaultStyle) {
        super(context, attrs, defaultStyle);
        init();
        setBackgroundColor(Color.BLACK);
    }

    protected void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.walkietalkie_view, this);


        final EditText frequenyEditText = (EditText) this.findViewById(R.id.et_frequency);
        final SeekBar frequencySeekbar = (SeekBar) this.findViewById(R.id.sb_frequencySeekBar);
        Spinner frequencyBandSpinner = (Spinner) this.findViewById(R.id.sp_frequencyBands);
        SeekBar vgaGainSeekBar = (SeekBar) this.findViewById(R.id.vgaGainSeekBar);
        final Button receiveButton = (Button) this.findViewById(R.id.receiveButton);
        final Button transmitButton = (Button) this.findViewById(R.id.transmitButton);

        frequencyBandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int previousFrequencyBand = selectedFrequencyBand;
                selectedFrequencyBand = i;
                String[] frequencyBands = getResources().getStringArray(R.array.frequency_bands);
                int[] frequencyBandsMin = getResources().getIntArray(R.array.frequency_bands_min);
                int[] frequencyBandsMax = getResources().getIntArray(R.array.frequency_bands_max);

                if(i==frequencyBands.length-1){
                    // free frequency selection "other"
                    frequenyEditText.setEnabled(true);
                    frequencySeekbar.setEnabled(false);
                } else {
                    frequenyEditText.setEnabled(false);
                    frequencySeekbar.setEnabled(true);
                    frequencySeekbar.setMax(frequencyBandsMax[i] - frequencyBandsMin[i]);
                    frequencySeekbar.setProgress(0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        frequencySeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int[] frequencyBandsMin = getResources().getIntArray(R.array.frequency_bands_min);
                int frequencyHz = progress+frequencyBandsMin[selectedFrequencyBand];
                frequenyEditText.setText(Integer.toString(frequencyHz));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        receiveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isReceiving){
                    isReceiving = false;
                    receiveButton.setText("START RECEPTION");

                } else {
                    isReceiving = true;
                    receiveButton.setText("STOP RECEPTION");
                }

            }
        });

        transmitButton.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View view) {

                if(isTransmitting){
                    isTransmitting = false;
                    transmitButton.setText("STOP");
                    // stop the reception

                    // start the transmission

                } else {
                    isTransmitting = true;
                    transmitButton.setText("TRANSMIT");

                    // stop the transmission


                    if(isReceiving){
                        // start the reception again
                    }

                }
            }
        });






        // this code is mainly copied over from transmit view
        // TODO: find better solution to avoid code duplication
        vgaGainSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    Preferences.MISC_PREFERENCE.setSend_vgaGain(progress);
                }
                updateVgaGainLabel();
            }


        });





        updateVgaGainLabel();
    }

    private void updateVgaGainLabel() {
        TextView vgaGainLabel = (TextView) this.findViewById(R.id.vgaGainLabel);
        vgaGainLabel.setText(String.format(getContext().getString(R.string.vga_gain_label), Preferences.MISC_PREFERENCE.getSend_vgaGain()));
    }
}
