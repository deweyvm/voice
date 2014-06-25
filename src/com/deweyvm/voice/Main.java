package com.deweyvm.voice;
import com.deweyvm.voice.audio.AudioManager;
import com.deweyvm.voice.audio.Listener;

import javax.sound.sampled.*;

public class Main {
    public static void main(String[] args) {
        final SourceDataLine lineout = AudioManager.getOutputLine(new Matcher() {
            @Override
            public boolean match(String s) {
                String lower = s.toLowerCase();
                return s.contains("SourceDataLine")
                        && lower.contains("speakers");
            }
        });

        TargetDataLine linein = AudioManager.getInputLine(new Matcher() {
            @Override
            public boolean match(String s) {
                return s.toLowerCase().contains("webcam");
            }
        });

        Listener l = new Listener(linein, new Function<byte[]>() {
            @Override
            public void eval(byte[] bytes) {
                lineout.write(bytes, 0, bytes.length);
                System.out.println(sumBytes(bytes));
            }
        });
        try {
            lineout.open();
        } catch (LineUnavailableException lue) {
            throw new RuntimeException(lue);
        }
        lineout.start();
        l.run();



    }

    private static int sumBytes(byte[] bytes) {
        int res = 0;
        for (byte b : bytes) {
            res += b;
        }
        return res;
    }
}
