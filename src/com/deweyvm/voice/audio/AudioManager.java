package com.deweyvm.voice.audio;

import com.deweyvm.voice.Matcher;

import javax.sound.sampled.*;

public class AudioManager {
    public static SourceDataLine getOutputLine(Matcher matcher) {
        try {
            Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
            for (Mixer.Info info: mixerInfos) {
                Mixer m = AudioSystem.getMixer(info);
                Line.Info[] lineInfos = m.getSourceLineInfo();
                for (Line.Info lineInfo : lineInfos) {
                    String s = info.getName() + "---" + lineInfo;
                    if (matcher.match(s)) {
                        return (SourceDataLine)AudioSystem.getLine(lineInfo);
                    }
                }
            }
        } catch (LineUnavailableException lue) {

        }
        return null;
    }

    public static TargetDataLine getInputLine(Matcher matcher) {
        try {
            Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
            for (Mixer.Info info: mixerInfos) {
                Mixer m = AudioSystem.getMixer(info);
                Line.Info[] lineInfos = m.getTargetLineInfo();
                for (Line.Info lineInfo : lineInfos) {
                    String s = info.getName();
                    if (matcher.match(s)) {
                        return (TargetDataLine)AudioSystem.getLine(lineInfo);
                    }
                }
            }
        } catch (LineUnavailableException lue) {

        }
        return null;
    }
}
