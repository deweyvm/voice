package com.deweyvm.voice.audio;

import com.deweyvm.voice.Function;
import com.deweyvm.voice.Function2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import java.util.Arrays;

public class Listener<T> {
    private TargetDataLine in;
    private Function<byte[]> f;
    public Listener(TargetDataLine in, Function<byte[]> f) {
        this.in = in;
        this.f = f;
    }


    public void run() {
        try {
            in.open();

        } catch (LineUnavailableException lue) {
            throw new RuntimeException(lue);
        }
        in.start();
        byte[] data = new byte[in.getBufferSize() / 5];
        boolean stopped = false;
        while (!stopped) {
            int numBytesRead = in.read(data, 0, data.length);
            byte[] next = Arrays.copyOf(data, numBytesRead);
            f.eval(next);
        }

    }
}
