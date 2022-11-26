package org.firstinspires.ftc.teamcode;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.function.BooleanSupplier;

public class OneClick {
    private enum State {
        change,
        on,
        off
    }
    private State state = State.off;
    private BooleanSupplier event;
    private BooleanSupplier previousEvent;

    public OneClick (BooleanSupplier event, BooleanSupplier previousEvent) {
        this.event = event;
        this.previousEvent = previousEvent;
    }

    /*public void updateState() {
        if (event.getAsBoolean()) {
            if (state == State.off) {
                state = State.change;
            } else if (state == State.on || state == State.change){
                state = State.on;
            }
        } else if(!event.getAsBoolean()){
            state = State.off;
        }
    }*/

    public void updateState() {
        if (event.getAsBoolean() && !previousEvent.getAsBoolean()) {
            state = State.change;
        } else if (event.getAsBoolean() && previousEvent.getAsBoolean()) {
            state = State.on;
        } else {
            state = State.off;
        }
    }

    public State getState() {
        updateState();
        return state;
    }

    public boolean isChangeState() {
        //updateState();
        return state == State.change;
    }
    public boolean isOnState() {
        //updateState();
        return state == State.on;
    }
    public boolean isOffState() {
        //updateState();
        return state == State.off;
    }
}
