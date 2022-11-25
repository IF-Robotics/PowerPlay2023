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

    public OneClick (BooleanSupplier event) {
        this.event = event;
    }

    public void updateState() {
        if (event.getAsBoolean()) {
            if (state == State.off) {
                state = State.change;
            } else {
                state = State.on;
            }
        } else {
            state = State.off;
        }
    }

    public State getState() {
        updateState();
        return state;
    }

    public boolean isChangeState() {
        updateState();
        return state == State.change;
    }
    public boolean isOnState() {
        updateState();
        return state == State.on;
    }
    public boolean isOffState() {
        updateState();
        return state == State.off;
    }
}
