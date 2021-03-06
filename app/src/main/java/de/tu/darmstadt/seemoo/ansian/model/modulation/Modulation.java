package de.tu.darmstadt.seemoo.ansian.model.modulation;

import de.tu.darmstadt.seemoo.ansian.model.SamplePacket;

/**
 * Created by dennis on 8/25/16.
 */
public abstract class Modulation {

    protected int sampleRate;

    public abstract void stop();


    public enum TxMode {
        RAWIQ, MORSE, PSK31, RDS, FM, USB, LSB;
    }

    public abstract SamplePacket getNextSamplePacket();

    public int getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }
}
