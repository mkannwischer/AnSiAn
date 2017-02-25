package de.tu.darmstadt.seemoo.ansian.control.events.tx.speech.lsb;

import de.tu.darmstadt.seemoo.ansian.control.events.tx.speech.SpeechTransmitEvent;

/**
 * Created by MATZE on 25.02.2017.
 */

public class LSBTransmitEvent extends SpeechTransmitEvent {
    private final int filterBandwidth;

    public LSBTransmitEvent(State state, Sender sender, int filterBandwidth) {
        super(state, sender);
        this.filterBandwidth = filterBandwidth;
    }

    public int getFilterBandwidth() {
        return filterBandwidth;
    }
}